package com.yeferic.holaflymarvel.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeferic.holaflymarvel.domain.models.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Character): Long

    @Query("SELECT * from character")
    suspend fun getCharacters(): List<Character>
}
