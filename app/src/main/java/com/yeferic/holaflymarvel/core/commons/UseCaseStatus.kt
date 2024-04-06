package com.yeferic.holaflymarvel.core.commons

import com.yeferic.holaflymarvel.domain.exceptions.ErrorEntity

sealed class UseCaseStatus<out R> {
    data class Success<out T>(val data: T) : UseCaseStatus<T>()

    data class Error(val errorEntity: ErrorEntity) : UseCaseStatus<Nothing>()

    data object Loading : UseCaseStatus<Nothing>()
}
