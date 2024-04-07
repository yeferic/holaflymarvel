package com.yeferic.holaflymarvel.domain.usecases.comics

import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.ComicsErrorMapper
import com.yeferic.holaflymarvel.domain.models.ComicDetail
import com.yeferic.holaflymarvel.domain.repositories.ComicsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetComicDetailUseCase
    @Inject
    constructor(
        private val repository: ComicsRepository,
        private val errorMapper: ComicsErrorMapper,
    ) {
        suspend operator fun invoke(id: Long): Flow<UseCaseStatus<ComicDetail>> =
            flow {
                try {
                    emit(UseCaseStatus.Loading)
                    val detail =
                        repository.getComicDetail(id)
                    emit(UseCaseStatus.Success(detail))
                } catch (e: Exception) {
                    emit(UseCaseStatus.Error(errorMapper.getError(e)))
                }
            }
    }
