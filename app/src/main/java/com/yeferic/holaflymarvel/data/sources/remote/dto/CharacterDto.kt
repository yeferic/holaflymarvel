package com.yeferic.holaflymarvel.data.sources.remote.dto

import com.google.gson.annotations.SerializedName
import com.yeferic.holaflymarvel.domain.models.Character

data class CharacterDto(
    @SerializedName("id")
    var id: Long,
    @SerializedName("name")
    var name: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("thumbnail")
    var thumbnail: ThumbnailDto,
)

fun CharacterDto.mapToDomain(): Character {
    return Character(
        id = id,
        name = name,
        description = description,
        imageUrl = "${thumbnail.path}.${thumbnail.extension}",
    )
}
