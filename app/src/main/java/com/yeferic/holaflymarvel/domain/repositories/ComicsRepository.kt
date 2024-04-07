package com.yeferic.holaflymarvel.domain.repositories

import com.yeferic.holaflymarvel.domain.models.Comic

interface ComicsRepository {
    suspend fun getComics(
        id: Long,
        limit: Int,
        offset: Int,
    ): List<Comic>
}
