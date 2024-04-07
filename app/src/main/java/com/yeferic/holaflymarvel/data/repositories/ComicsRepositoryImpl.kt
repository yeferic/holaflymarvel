package com.yeferic.holaflymarvel.data.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yeferic.holaflymarvel.data.sources.remote.ComicApi
import com.yeferic.holaflymarvel.data.sources.remote.dto.BaseResponseDto
import com.yeferic.holaflymarvel.data.sources.remote.dto.ComicDto
import com.yeferic.holaflymarvel.data.sources.remote.dto.mapToDomain
import com.yeferic.holaflymarvel.domain.models.Comic
import com.yeferic.holaflymarvel.domain.repositories.ComicsRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ComicsRepositoryImpl
    @Inject
    constructor(
        private val api: ComicApi,
        private val gson: Gson,
    ) : ComicsRepository {
        private suspend fun mapResponse(response: Response<BaseResponseDto>): List<Comic> {
            gson.run {
                return withContext(Dispatchers.IO) {
                    val listType = object : TypeToken<List<ComicDto>>() {}.type
                    fromJson<List<ComicDto>?>(
                        response.body()!!.data.results,
                        listType,
                    ).map { it.mapToDomain() }
                }
            }
        }

        override suspend fun getComics(
            id: Long,
            limit: Int,
            offset: Int,
        ): List<Comic> {
            return mapResponse(api.getComics(id = id, limit = limit, offset = offset))
        }
    }
