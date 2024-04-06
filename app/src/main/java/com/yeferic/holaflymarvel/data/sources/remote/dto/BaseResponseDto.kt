package com.yeferic.holaflymarvel.data.sources.remote.dto

import com.google.gson.JsonArray

data class BaseResponseDto(
    var data: Result,
)

data class Result(
    var results: JsonArray,
)
