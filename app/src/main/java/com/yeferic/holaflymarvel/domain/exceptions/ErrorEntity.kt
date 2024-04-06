package com.yeferic.holaflymarvel.domain.exceptions

import com.yeferic.holaflymarvel.core.commons.Constants.EMPTY_STRING

open class ErrorEntity(open val message: String = EMPTY_STRING)

sealed class BaseErrorEntity : ErrorEntity() {
    data object Network : ErrorEntity()

    data object NotFound : ErrorEntity()

    data object AccessDenied : ErrorEntity()

    data object ServiceUnavailable : ErrorEntity()

    data object Unknown : ErrorEntity()
}
