package com.yeferic.holaflymarvel.domain.exceptions

interface ErrorMapper {
    fun getError(throwable: Throwable): ErrorEntity
}
