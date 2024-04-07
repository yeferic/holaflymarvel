package com.yeferic.holaflymarvel.domain.repositories

import com.yeferic.holaflymarvel.domain.models.Comic
import com.yeferic.holaflymarvel.domain.models.ComicDetail

interface ComicsRepository {
    suspend fun getComics(
        id: Long,
        limit: Int,
        offset: Int,
    ): List<Comic>

    suspend fun getComicDetail(id: Long): ComicDetail
}
