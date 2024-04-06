package com.yeferic.holaflymarvel.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yeferic.holaflymarvel.data.sources.local.dao.CharacterDao
import com.yeferic.holaflymarvel.domain.models.Character

@Database(
    entities = [
        Character::class,
    ],
    version = AppDatabase.VERSION,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        const val DB_NAME = "holaflymarvel.db"
        const val VERSION = 1
    }
}
