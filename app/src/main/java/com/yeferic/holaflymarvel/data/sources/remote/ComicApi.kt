package com.yeferic.holaflymarvel.data.sources.remote

import com.yeferic.holaflymarvel.data.sources.remote.dto.BaseResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicApi {
    @GET("v1/public/characters/{id}/comics")
    suspend fun getComics(
        @Path("id") id: Long,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<BaseResponseDto>

    @GET("v1/public/comics/{comicId}")
    suspend fun getComicDetail(
        @Path("comicId") id: Long,
    ): Response<BaseResponseDto>
}
