package com.yeferic.holaflymarvel.data.repositories

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.yeferic.holaflymarvel.data.sources.local.dao.CharacterDao
import com.yeferic.holaflymarvel.data.sources.remote.CharacterApi
import com.yeferic.holaflymarvel.data.sources.remote.dto.BaseResponseDto
import com.yeferic.holaflymarvel.data.sources.remote.dto.CharacterDto
import com.yeferic.holaflymarvel.data.sources.remote.dto.Result
import com.yeferic.holaflymarvel.data.sources.remote.dto.ThumbnailDto
import com.yeferic.holaflymarvel.data.sources.remote.dto.mapToDomain
import com.yeferic.holaflymarvel.domain.models.Character
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class CharacterRepositoryImplTest {
    private lateinit var repositoryImpl: CharacterRepositoryImpl

    @MockK
    private lateinit var apiMock: CharacterApi

    @MockK
    private lateinit var daoMock: CharacterDao

    @MockK(relaxed = true, relaxUnitFun = true)
    private lateinit var gsonMock: Gson

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repositoryImpl =
            CharacterRepositoryImpl(
                api = apiMock,
                characterDao = daoMock,
                gson = gsonMock,
            )
    }

    @After
    fun tearDown() {
        confirmVerified(
            apiMock,
            daoMock,
            gsonMock,
        )
    }

    @Test
    fun `map any response from api`() =
        runTest {
            // given
            val response = mockk<Response<BaseResponseDto>>()
            val baseDto = mockk<BaseResponseDto>()
            val resultDto = mockk<Result>()
            val characterDto =
                CharacterDto(
                    0,
                    "Name",
                    "Description",
                    ThumbnailDto("Path", "JPG"),
                )

            val jsonElement = mockk<JsonElement>()
            val arrayJson = JsonArray().apply { add(jsonElement) }

            val character = characterDto.mapToDomain()

            coEvery {
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
            } returns characterDto

            coEvery {
                resultDto.results
            } returns arrayJson

            coEvery {
                baseDto.data
            } returns resultDto

            coEvery {
                response.body()
            } returns baseDto

            // when
            val responseCharacter = repositoryImpl.mapResponse(response)

            // then
            coVerify(exactly = 1) {
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
                baseDto.data
                response.body()
                resultDto.results
            }
            Assert.assertEquals(responseCharacter, character)
            confirmVerified()
        }

    @Test
    fun `call to get iron man info then return data`() =
        runTest {
            // given
            val response = mockk<Response<BaseResponseDto>>()
            val baseDto = mockk<BaseResponseDto>()
            val resultDto = mockk<Result>()
            val characterDto =
                CharacterDto(
                    0,
                    "Name",
                    "Description",
                    ThumbnailDto("Path", "JPG"),
                )

            val jsonElement = mockk<JsonElement>()
            val arrayJson = JsonArray().apply { add(jsonElement) }

            val character = characterDto.mapToDomain()

            coEvery {
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
            } returns characterDto

            coEvery {
                resultDto.results
            } returns arrayJson

            coEvery {
                baseDto.data
            } returns resultDto

            coEvery {
                response.body()
            } returns baseDto

            coEvery {
                apiMock.getIronManInfo()
            } returns response

            coEvery {
                daoMock.insert(character)
            } returns 0L

            // when
            val responseCharacter = repositoryImpl.getIronManInfo()

            // then
            coVerify(exactly = 1) {
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
                baseDto.data
                response.body()
                resultDto.results
                daoMock.insert(character)
                apiMock.getIronManInfo()
            }
            Assert.assertEquals(responseCharacter, character)
            confirmVerified()
        }

    @Test
    fun `call to get hulk info then return data`() =
        runTest {
            // given
            val response = mockk<Response<BaseResponseDto>>()
            val baseDto = mockk<BaseResponseDto>()
            val resultDto = mockk<Result>()
            val characterDto =
                CharacterDto(
                    0,
                    "Name",
                    "Description",
                    ThumbnailDto("Path", "JPG"),
                )

            val jsonElement = mockk<JsonElement>()
            val arrayJson = JsonArray().apply { add(jsonElement) }

            val character = characterDto.mapToDomain()

            coEvery {
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
            } returns characterDto

            coEvery {
                resultDto.results
            } returns arrayJson

            coEvery {
                baseDto.data
            } returns resultDto

            coEvery {
                response.body()
            } returns baseDto

            coEvery {
                apiMock.getHulkInfo()
            } returns response

            coEvery {
                daoMock.insert(character)
            } returns 0L

            // when
            val responseCharacter = repositoryImpl.getHulkInfo()

            // then
            coVerify(exactly = 1) {
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
                baseDto.data
                response.body()
                resultDto.results
                daoMock.insert(character)
                apiMock.getHulkInfo()
            }
            Assert.assertEquals(responseCharacter, character)
            confirmVerified()
        }

    @Test
    fun `call to captain america info then return data`() =
        runTest {
            // given
            val response = mockk<Response<BaseResponseDto>>()
            val baseDto = mockk<BaseResponseDto>()
            val resultDto = mockk<Result>()
            val characterDto =
                CharacterDto(
                    0,
                    "Name",
                    "Description",
                    ThumbnailDto("Path", "JPG"),
                )

            val jsonElement = mockk<JsonElement>()
            val arrayJson = JsonArray().apply { add(jsonElement) }

            val character = characterDto.mapToDomain()

            coEvery {
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
            } returns characterDto

            coEvery {
                resultDto.results
            } returns arrayJson

            coEvery {
                baseDto.data
            } returns resultDto

            coEvery {
                response.body()
            } returns baseDto

            coEvery {
                apiMock.getCaptainAmericaInfo()
            } returns response

            coEvery {
                daoMock.insert(character)
            } returns 0L

            // when
            val responseCharacter = repositoryImpl.getCaptainAmericaInfo()

            // then
            coVerify(exactly = 1) {
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
                baseDto.data
                response.body()
                resultDto.results
                daoMock.insert(character)
                apiMock.getCaptainAmericaInfo()
            }
            Assert.assertEquals(responseCharacter, character)
            confirmVerified()
        }

    @Test
    fun `call to thor info then return data`() =
        runTest {
            // given
            val response = mockk<Response<BaseResponseDto>>()
            val baseDto = mockk<BaseResponseDto>()
            val resultDto = mockk<Result>()
            val characterDto =
                CharacterDto(
                    0,
                    "Name",
                    "Description",
                    ThumbnailDto("Path", "JPG"),
                )

            val jsonElement = mockk<JsonElement>()
            val arrayJson = JsonArray().apply { add(jsonElement) }

            val character = characterDto.mapToDomain()

            coEvery {
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
            } returns characterDto

            coEvery {
                resultDto.results
            } returns arrayJson

            coEvery {
                baseDto.data
            } returns resultDto

            coEvery {
                response.body()
            } returns baseDto

            coEvery {
                apiMock.getThorInfo()
            } returns response

            coEvery {
                daoMock.insert(character)
            } returns 0L

            // when
            val responseCharacter = repositoryImpl.getThorInfo()

            // then
            coVerify(exactly = 1) {
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
                baseDto.data
                response.body()
                resultDto.results
                daoMock.insert(character)
                apiMock.getThorInfo()
            }
            Assert.assertEquals(responseCharacter, character)
            confirmVerified()
        }

    @Test
    fun `call to get characters then return a character list`() =
        runTest {
            // given
            val characters = listOf(Character(), Character())
            coEvery {
                daoMock.getCharacters()
            } returns characters

            // when
            val response = repositoryImpl.getCharacters()

            // then
            coVerify(exactly = 1) {
                daoMock.getCharacters()
            }
            Assert.assertEquals(characters, response)
        }

    @Test
    fun `call to get characters then return a empty list`() =
        runTest {
            // given

            val response = mockk<Response<BaseResponseDto>>()
            val baseDto = mockk<BaseResponseDto>()
            val resultDto = mockk<Result>()
            val characterDto =
                CharacterDto(
                    0,
                    "Name",
                    "Description",
                    ThumbnailDto("Path", "JPG"),
                )

            val jsonElement = mockk<JsonElement>()
            val arrayJson = JsonArray().apply { add(jsonElement) }

            val character = characterDto.mapToDomain()

            coEvery {
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
            } returns characterDto

            coEvery {
                resultDto.results
            } returns arrayJson

            coEvery {
                baseDto.data
            } returns resultDto

            coEvery {
                response.body()
            } returns baseDto

            coEvery {
                apiMock.getThorInfo()
            } returns response

            coEvery {
                apiMock.getIronManInfo()
            } returns response

            coEvery {
                apiMock.getCaptainAmericaInfo()
            } returns response

            coEvery {
                apiMock.getHulkInfo()
            } returns response

            coEvery {
                daoMock.insert(character)
            } returns 0L

            val emptyList = listOf<Character>()
            coEvery {
                daoMock.getCharacters()
            } returns emptyList

            // when
            val responseList = repositoryImpl.getCharacters()

            // then
            coVerify(exactly = 1) {
                apiMock.getIronManInfo()
                apiMock.getCaptainAmericaInfo()
                apiMock.getHulkInfo()
                apiMock.getThorInfo()
            }
            coVerify(exactly = 2) {
                daoMock.getCharacters()
            }

            coVerify(exactly = 4) {
                daoMock.insert(character)
                gsonMock.fromJson(arrayJson.first(), CharacterDto::class.java)
                baseDto.data
                response.body()
                resultDto.results
            }
            Assert.assertEquals(emptyList, responseList)
        }
}
