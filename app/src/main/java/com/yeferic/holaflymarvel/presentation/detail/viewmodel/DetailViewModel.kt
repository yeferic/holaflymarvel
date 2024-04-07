package com.yeferic.holaflymarvel.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.domain.usecases.comics.GetComicDetailUseCase
import com.yeferic.holaflymarvel.presentation.detail.states.ComicDetailUiState
import com.yeferic.holaflymarvel.presentation.detail.states.ComicDetailUiState.DataLoaded
import com.yeferic.holaflymarvel.presentation.detail.states.ComicDetailUiState.Error
import com.yeferic.holaflymarvel.presentation.detail.states.ComicDetailUiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val comicDetailUseCase: GetComicDetailUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<ComicDetailUiState>(Loading)
        val uiState: StateFlow<ComicDetailUiState> = _uiState

        private var idComic: Long = 0

        fun setIdCharacter(id: Long) {
            idComic = id
        }

        fun getComicInfo() {
            viewModelScope.launch(Dispatchers.IO) {
                comicDetailUseCase(idComic).collect { status ->
                    setUiState(
                        when (status) {
                            is UseCaseStatus.Success -> DataLoaded(status.data)
                            is UseCaseStatus.Loading -> Loading
                            is UseCaseStatus.Error -> Error(status.errorEntity)
                        },
                    )
                }
            }
        }

        private fun setUiState(state: ComicDetailUiState) {
            _uiState.value = state
        }
    }
