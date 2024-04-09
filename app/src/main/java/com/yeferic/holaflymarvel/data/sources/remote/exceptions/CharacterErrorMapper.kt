package com.yeferic.holaflymarvel.data.sources.remote.exceptions

import com.yeferic.holaflymarvel.domain.exceptions.ErrorEntity
import com.yeferic.holaflymarvel.domain.exceptions.ErrorMapper
import javax.inject.Inject
import retrofit2.HttpException

class CharacterErrorMapper
    @Inject
    constructor() : ErrorMapper {
        override fun getError(throwable: Throwable): ErrorEntity {
            return when (throwable) {
                is HttpException -> {
                    CharacterApiError.ServiceError(
                        CharacterErrorMessage.HTTP_EXCEPTION.message,
                    )
                }

                else ->
                    CharacterApiError.ServiceError(
                        CharacterErrorMessage.GENERAL_EXCEPTION.message,
                    )
            }
        }
    }

sealed class CharacterApiError : ErrorEntity() {
    data class ServiceError(override val message: String) : CharacterApiError()
}

enum class CharacterErrorMessage(val message: String) {
    HTTP_EXCEPTION("No fue posible obtener información. Por favor intenta nuevamente"),
    GENERAL_EXCEPTION("No fue posible obtener información. Por favor intenta nuevamente"),
}
