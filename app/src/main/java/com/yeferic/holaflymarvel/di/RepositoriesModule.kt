package com.yeferic.holaflymarvel.di

import com.yeferic.holaflymarvel.data.repositories.CharacterRepositoryImpl
import com.yeferic.holaflymarvel.data.repositories.ComicsRepositoryImpl
import com.yeferic.holaflymarvel.domain.repositories.CharacterRepository
import com.yeferic.holaflymarvel.domain.repositories.ComicsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun bindCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl,
    ): CharacterRepository

    @Binds
    abstract fun bindComicsRepository(comicsRepositoryImpl: ComicsRepositoryImpl): ComicsRepository
}
