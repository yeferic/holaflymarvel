package com.yeferic.holaflymarvel.data.sources.remote.dto

import com.google.gson.annotations.SerializedName
import com.yeferic.holaflymarvel.domain.models.Comic

data class ComicDto(
    @SerializedName("id")
    var id: Long,
    @SerializedName("thumbnail")
    var thumbnail: ThumbnailComic,
)

fun ComicDto.mapToDomain(): Comic {
    return Comic(
        id = id,
        imageUrl = "${thumbnail.path}.${thumbnail.extension}",
    )
}

data class ThumbnailComic(
    @SerializedName("path")
    var path: String,
    @SerializedName("extension")
    var extension: String,
)
