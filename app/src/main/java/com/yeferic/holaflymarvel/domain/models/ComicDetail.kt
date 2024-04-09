package com.yeferic.holaflymarvel.domain.models

import com.yeferic.holaflymarvel.core.commons.Constants.EMPTY_STRING

data class ComicDetail(
    var id: Long = 0,
    var title: String = EMPTY_STRING,
    var description: String = EMPTY_STRING,
    var imageUrl: String = EMPTY_STRING,
)
