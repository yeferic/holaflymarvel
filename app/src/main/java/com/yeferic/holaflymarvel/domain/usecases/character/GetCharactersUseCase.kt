package com.yeferic.holaflymarvel.domain.usecases.character

import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.core.commons.UseCaseStatus.Error
import com.yeferic.holaflymarvel.core.commons.UseCaseStatus.Loading
import com.yeferic.holaflymarvel.core.commons.UseCaseStatus.Success
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.CharacterErrorMapper
import com.yeferic.holaflymarvel.domain.models.Character
import com.yeferic.holaflymarvel.domain.repositories.CharacterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class GetCharactersUseCase
    @Inject
    constructor(
        private val repository: CharacterRepository,
        private val errorMapper: CharacterErrorMapper,
    ) {
        suspend operator fun invoke(): Flow<UseCaseStatus<List<Character>>> =
            flow {
                try {
                    emit(Loading)
                    val characters = repository.getCharacters()
                    emit(Success(characters))
                } catch (e: Exception) {
                    emit(Error(errorMapper.getError(e)))
                }
            }
    }
