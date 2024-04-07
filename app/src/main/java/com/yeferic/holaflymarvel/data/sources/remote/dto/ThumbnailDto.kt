package com.yeferic.holaflymarvel.data.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class ThumbnailDto(
    @SerializedName("path")
    var path: String,
    @SerializedName("extension")
    var extension: String,
)
