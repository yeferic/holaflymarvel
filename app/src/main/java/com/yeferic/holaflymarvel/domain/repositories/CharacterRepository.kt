package com.yeferic.holaflymarvel.domain.repositories

import com.yeferic.holaflymarvel.domain.models.Character

interface CharacterRepository {
    suspend fun getCharacters(): List<Character>
}
