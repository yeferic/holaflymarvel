package com.yeferic.holaflymarvel.domain.models

import com.yeferic.holaflymarvel.core.commons.Constants.EMPTY_STRING

data class Comic(
    var id: Long = 0,
    var imageUrl: String = EMPTY_STRING,
)
