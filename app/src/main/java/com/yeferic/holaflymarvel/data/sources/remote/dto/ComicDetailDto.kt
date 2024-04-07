package com.yeferic.holaflymarvel.data.sources.remote.dto

import com.google.gson.annotations.SerializedName
import com.yeferic.holaflymarvel.domain.models.ComicDetail

data class ComicDetailDto(
    @SerializedName("id")
    var id: Long,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("thumbnail")
    var thumbnail: ThumbnailDto,
)

fun ComicDetailDto.mapToDomain(): ComicDetail {
    return ComicDetail(
        id = id,
        title = title,
        description = description,
        imageUrl = "${thumbnail.path}.${thumbnail.extension}",
    )
}
