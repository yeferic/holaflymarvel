package com.yeferic.holaflymarvel.domain.usecases.comics

import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.ComicsErrorMapper
import com.yeferic.holaflymarvel.domain.models.Comic
import com.yeferic.holaflymarvel.domain.repositories.ComicsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetComicsUseCase
    @Inject
    constructor(
        private val repository: ComicsRepository,
        private val errorMapper: ComicsErrorMapper,
    ) {
        companion object {
            const val MAX_LIMIT = 30
        }

        suspend operator fun invoke(
            id: Long,
            currentOffset: Int,
        ): Flow<UseCaseStatus<List<Comic>>> =
            flow {
                try {
                    emit(UseCaseStatus.Loading)
                    val comics =
                        repository.getComics(
                            id = id,
                            limit = MAX_LIMIT,
                            offset = currentOffset + MAX_LIMIT,
                        )
                    emit(UseCaseStatus.Success(comics))
                } catch (e: Exception) {
                    emit(UseCaseStatus.Error(errorMapper.getError(e)))
                }
            }
    }
