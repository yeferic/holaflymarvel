package com.yeferic.holaflymarvel.data.repositories

import com.google.gson.Gson
import com.yeferic.holaflymarvel.data.sources.local.dao.CharacterDao
import com.yeferic.holaflymarvel.data.sources.remote.CharacterApi
import com.yeferic.holaflymarvel.data.sources.remote.dto.BaseResponseDto
import com.yeferic.holaflymarvel.data.sources.remote.dto.CharacterDto
import com.yeferic.holaflymarvel.data.sources.remote.dto.mapToDomain
import com.yeferic.holaflymarvel.domain.models.Character
import com.yeferic.holaflymarvel.domain.repositories.CharacterRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CharacterRepositoryImpl
    @Inject
    constructor(
        private val api: CharacterApi,
        private val characterDao: CharacterDao,
        private val gson: Gson,
    ) : CharacterRepository {
        private suspend fun mapResponse(response: Response<BaseResponseDto>): Character {
            gson.run {
                return withContext(Dispatchers.IO) {
                    fromJson(
                        response.body()!!.data.results[0],
                        CharacterDto::class.java,
                    ).mapToDomain()
                }
            }
        }

        private suspend fun getIronManInfo(): Character {
            return mapResponse(api.getIronManInfo()).also {
                characterDao.insert(it)
            }
        }

        private suspend fun getThorInfo(): Character {
            return mapResponse(api.getThorInfo()).also {
                characterDao.insert(it)
            }
        }

        private suspend fun getHulkInfo(): Character {
            return mapResponse(api.getHulkInfo()).also {
                characterDao.insert(it)
            }
        }

        private suspend fun getCaptainAmericaInfo(): Character {
            return mapResponse(api.getCaptainAmericaInfo()).also {
                characterDao.insert(it)
            }
        }

        override suspend fun getCharacters(): List<Character> {
            return characterDao.getCharacters().ifEmpty {
                getHulkInfo()
                getCaptainAmericaInfo()
                getThorInfo()
                getIronManInfo()
                characterDao.getCharacters()
            }
        }
    }
