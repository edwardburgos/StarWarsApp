package com.example.data.repository

import com.apollographql.apollo3.ApolloClient
import com.example.data.database.CharactersDao
import com.example.data.database.model.CharacterEntity
import com.example.data.database.model.CharacterMapper
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharacterResponse
import com.example.data.repository.model.GetCharactersResponse
import com.example.data.repository.utils.refreshIntervalMsLong
import com.example.data.repository.utils.refreshIntervalMsShort
import com.example.starwarsapp.CharacterQuery
import com.example.starwarsapp.CharactersListQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val charactersDao: CharactersDao
) : CharactersRepository {

    override fun getCharacters(): Flow<GetCharactersResponse> {
        return flow {
            var finalResponse = GetCharactersResponse(ResponseStatus.INITIAL, null)
            val errorResponse = GetCharactersResponse(ResponseStatus.ERROR, null)
            while (true) {
                try {
                    val result = apolloClient.query(CharactersListQuery()).execute()
                    if (result.data != null) {
                        result.dataAssertNoErrors.allPeople?.let {
                            it.people?.let { people ->
                                finalResponse = GetCharactersResponse(
                                    if (people.isNotEmpty()) ResponseStatus.FILLED else ResponseStatus.EMPTY,
                                    people
                                )
                            }
                        }
                    } else {
                        finalResponse = errorResponse
                    }
                } catch (e: Exception) {
                    finalResponse = errorResponse
                }
                emit(finalResponse)
                if (finalResponse.status == ResponseStatus.INITIAL) {
                    kotlinx.coroutines.delay(refreshIntervalMsShort)
                } else {
                    kotlinx.coroutines.delay(refreshIntervalMsLong)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getCharacter(id: String): Flow<GetCharacterResponse> {
        return flow {
            var finalResponse = GetCharacterResponse(ResponseStatus.INITIAL, null)
            val errorResponse = GetCharacterResponse(ResponseStatus.ERROR, null)
            while (true) {
                try {
                    val result = apolloClient.query(CharacterQuery(id)).execute()
                    if (result.data != null) {
                        result.dataAssertNoErrors.person?.let {
                            finalResponse = GetCharacterResponse(ResponseStatus.SUCCESSFUL, it)
                        }
                    } else {
                        finalResponse = errorResponse
                    }
                } catch (e: Exception) {
                    finalResponse = errorResponse
                }
                emit(finalResponse)
                if (finalResponse.status == ResponseStatus.INITIAL) {
                    kotlinx.coroutines.delay(refreshIntervalMsShort)
                } else {
                    kotlinx.coroutines.delay(refreshIntervalMsLong)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun checkUncheckAsFavorite(id: String): Flow<ResponseStatus> {
        return flow {
            if (charactersDao.getFavoritesOnce().map { character -> character.id }.indexOf(id) == -1) {
                charactersDao.favoriteTrue(id, System.currentTimeMillis())
            } else {
                charactersDao.favoriteFalse(id)
            }
            emit(ResponseStatus.SUCCESSFUL)
        }.flowOn(Dispatchers.IO)
    }

    override fun getFavoriteCharacters(): Flow<List<CharacterEntity>> {
        return charactersDao.getFavorites()
    }
}