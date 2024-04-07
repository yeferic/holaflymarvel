package com.yeferic.holaflymarvel.data.sources.remote.exceptions

import com.yeferic.holaflymarvel.domain.exceptions.ErrorEntity
import com.yeferic.holaflymarvel.domain.exceptions.ErrorMapper
import javax.inject.Inject
import retrofit2.HttpException

class ComicsErrorMapper
    @Inject
    constructor() : ErrorMapper {
        override fun getError(throwable: Throwable): ErrorEntity {
            return when (throwable) {
                is HttpException -> {
                    CharacterApiError.ServiceError(
                        ComicsErrorMessage.HTTP_EXCEPTION.message,
                    )
                }

                else ->
                    CharacterApiError.ServiceError(
                        ComicsErrorMessage.GENERAL_EXCEPTION.message,
                    )
            }
        }
    }

sealed class ComicsApiError : ErrorEntity() {
    data class ServiceError(override val message: String) : CharacterApiError()
}

private enum class ComicsErrorMessage(val message: String) {
    HTTP_EXCEPTION("Hubo un error al obtener la inforamción"),
    GENERAL_EXCEPTION("No fue posible obtener información. Intenta de nuevo más tarde"),
}
