package com.yeferic.holaflymarvel.presentation.menu.states

import com.yeferic.holaflymarvel.domain.exceptions.ErrorEntity
import com.yeferic.holaflymarvel.domain.models.Character

sealed class MenuUiStates {
    data object Loading : MenuUiStates()

    data class Error(val error: ErrorEntity) : MenuUiStates()

    data class DataLoaded(val characters: List<Character>) : MenuUiStates()
}
