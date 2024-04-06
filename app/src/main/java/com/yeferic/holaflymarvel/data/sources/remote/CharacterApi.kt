package com.yeferic.holaflymarvel.data.sources.remote

import com.yeferic.holaflymarvel.data.sources.remote.dto.BaseResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface CharacterApi {
    @GET("v1/public/characters/1009368")
    suspend fun getIronManInfo(): Response<BaseResponseDto>

    @GET("v1/public/characters/1009664")
    suspend fun getThorInfo(): Response<BaseResponseDto>

    @GET("v1/public/characters/1009351")
    suspend fun getHulkInfo(): Response<BaseResponseDto>

    @GET("v1/public/characters/1009220")
    suspend fun getCaptainAmericaInfo(): Response<BaseResponseDto>
}
