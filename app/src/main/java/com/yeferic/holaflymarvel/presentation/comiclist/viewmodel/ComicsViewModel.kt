package com.yeferic.holaflymarvel.presentation.comiclist.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.domain.usecases.comics.GetComicsUseCase
import com.yeferic.holaflymarvel.presentation.comiclist.states.ComicsListUiState
import com.yeferic.holaflymarvel.presentation.comiclist.states.ComicsListUiState.Error
import com.yeferic.holaflymarvel.presentation.comiclist.states.ComicsListUiState.Loading
import com.yeferic.holaflymarvel.presentation.comiclist.states.ComicsListUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ComicsViewModel
    @Inject
    constructor(
        private val comicsUseCase: GetComicsUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<ComicsListUiState>(Loading)
        val uiState: StateFlow<ComicsListUiState> = _uiState

        private var idCharacter: Long = 0
        private var currentOffset: Int = 0

        fun setIdCharacter(id: Long) {
            idCharacter = id
        }

        fun getComics(loadMore: Boolean = false) {
            viewModelScope.launch(Dispatchers.IO) {
                comicsUseCase(idCharacter, currentOffset).collect { status ->
                    setUiState(
                        when (status) {
                            is UseCaseStatus.Success -> {
                                currentOffset += status.data.size
                                if (loadMore) {
                                    Success(
                                        (_uiState.value as Success).comics.apply {
                                            this.addAll(
                                                status.data,
                                            )
                                        },
                                        false,
                                    )
                                } else {
                                    Success(ArrayList(status.data), false)
                                }
                            }
                            is UseCaseStatus.Loading -> {
                                if (loadMore) {
                                    Success((_uiState.value as Success).comics, true)
                                } else {
                                    Loading
                                }
                            }
                            is UseCaseStatus.Error -> {
                                Error(status.errorEntity)
                            }
                        },
                    )
                }
            }
        }

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        fun setUiState(state: ComicsListUiState) {
            _uiState.value = state
        }
    }
