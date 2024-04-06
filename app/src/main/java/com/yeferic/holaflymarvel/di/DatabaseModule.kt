package com.yeferic.holaflymarvel.di

import android.app.Application
import androidx.room.Room
import com.yeferic.holaflymarvel.data.sources.local.AppDatabase
import com.yeferic.holaflymarvel.data.sources.local.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCharacterDao(appDataBase: AppDatabase): CharacterDao {
        return appDataBase.characterDao()
    }
}
