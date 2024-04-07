package com.yeferic.holaflymarvel.presentation.detail.states

import com.yeferic.holaflymarvel.domain.exceptions.ErrorEntity
import com.yeferic.holaflymarvel.domain.models.ComicDetail

sealed class ComicDetailUiState {
    data object Loading : ComicDetailUiState()

    data class Error(val error: ErrorEntity) : ComicDetailUiState()

    data class DataLoaded(val detail: ComicDetail) : ComicDetailUiState()
}
