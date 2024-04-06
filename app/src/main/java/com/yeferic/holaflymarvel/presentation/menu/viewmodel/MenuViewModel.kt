package com.yeferic.holaflymarvel.presentation.menu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.domain.usecases.character.GetCharactersUseCase
import com.yeferic.holaflymarvel.presentation.menu.states.MenuUiStates
import com.yeferic.holaflymarvel.presentation.menu.states.MenuUiStates.DataLoaded
import com.yeferic.holaflymarvel.presentation.menu.states.MenuUiStates.Error
import com.yeferic.holaflymarvel.presentation.menu.states.MenuUiStates.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MenuViewModel
    @Inject
    constructor(
        private val charactersUseCase: GetCharactersUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<MenuUiStates>(Loading)
        val uiState: StateFlow<MenuUiStates> = _uiState

        init {
            getCharacterInfo()
        }

        fun getCharacterInfo() {
            viewModelScope.launch(Dispatchers.IO) {
                charactersUseCase().collect { status ->
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

        private fun setUiState(state: MenuUiStates) {
            _uiState.value = state
        }
    }
