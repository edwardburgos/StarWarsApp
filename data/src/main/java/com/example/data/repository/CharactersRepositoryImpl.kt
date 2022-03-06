package com.example.data.repository

import androidx.paging.*
import com.apollographql.apollo3.ApolloClient
import com.example.data.database.dao.CharactersDao
import com.example.data.database.CharactersDatabase
import com.example.data.database.model.CharacterEntity
import com.example.data.network.model.ResponseStatus
import com.example.data.pager.CharactersPagingDataSource
import com.example.data.pager.CharactersRemoteMediator
import com.example.data.repository.model.GetCharacterResponse
import com.example.data.repository.utils.DEFAULT_PAGE_SIZE
import com.example.data.repository.utils.refreshIntervalMsLong
import com.example.data.repository.utils.refreshIntervalMsShort
import com.example.domain.utils.DomainMapper
import com.example.starwarsapp.CharacterQuery
import com.example.starwarsapp.CharactersListQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val charactersDao: CharactersDao,
    private val mapperForNetwork: DomainMapper<CharacterEntity, CharactersListQuery.Node>,
    private val database: CharactersDatabase
) : CharactersRepository {

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
                            charactersDao.updateCharacterDetails(
                                it.id,
                                it.eyeColor,
                                it.hairColor,
                                it.skinColor,
                                it.birthYear,
                                it.vehicleConnection?.vehicles?.mapNotNull { vehicle -> vehicle?.name }
                            )
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

    override fun checkUncheckAsFavorite(id: String) {
        if (charactersDao.getFavoritesIdsOnce().indexOf(id) == -1) {
            charactersDao.favoriteTrue(id)
        } else {
            charactersDao.favoriteFalse(id)
        }
    }

    override fun getFavoriteCharactersIds(): Flow<List<String>> {
        return charactersDao.getFavoritesIds()
    }

    @ExperimentalPagingApi
    override fun getPager(query: String): Flow<PagingData<CharacterEntity>> {
        return Pager(config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                charactersDao.getAllCharactersPaging(query)
            },
            remoteMediator = CharactersRemoteMediator(apolloClient, mapperForNetwork, database, query)
        ).flow
    }
}