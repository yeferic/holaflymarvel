package com.yeferic.holaflymarvel.presentation.comiclist.states

import com.yeferic.holaflymarvel.domain.exceptions.ErrorEntity
import com.yeferic.holaflymarvel.domain.models.Comic

sealed class ComicsListUiState {
    data object Loading : ComicsListUiState()

    data class Error(val error: ErrorEntity) : ComicsListUiState()

    data class Success(val comics: ArrayList<Comic>, val loadingMore: Boolean) : ComicsListUiState()
}
