package com.example.data.pager

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.data.database.CharactersDatabase
import com.example.data.database.model.CharacterEntity
import com.example.data.database.model.RemoteKeysEntity
import com.example.data.repository.utils.DEFAULT_PAGE_INDEX
import com.example.domain.utils.DomainMapper
import com.example.starwarsapp.CharactersListQuery

@ExperimentalPagingApi
class CharactersRemoteMediator(
    private val apolloClient: ApolloClient,
    private val mapperForNetwork: DomainMapper<CharacterEntity, CharactersListQuery.Node>,
    private val database: CharactersDatabase,
    private val query: String
) : RemoteMediator<Int, CharacterEntity>() {

    private val charactersDao = database.charactersDao
    private val remoteKeysDao = database.remoteKeysDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {

        val afterElement = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as String
            }
        }

        try {
            val response = apolloClient.query(
                CharactersListQuery(
                    Optional.presentIfNotNull(state.config.pageSize),
                    Optional.presentIfNotNull(afterElement)
                )
            ).execute().data?.allPeople
            database.withTransaction {
                val existingCursors = charactersDao.getCursors()
                response?.edges?.forEach {
                    it?.let {
                        val cursor = it.cursor
                        it.node?.let { character ->
                            if (existingCursors.indexOf(cursor) == -1) {
                                charactersDao.insertCharacter(
                                    character.id,
                                    cursor,
                                    character.name,
                                    character.species?.name,
                                    character.homeworld?.name
                                )

                            } else {
                                charactersDao.updateCharacter(
                                    character.id,
                                    character.name,
                                    character.species?.name,
                                    character.homeworld?.name
                                )
                            }
                        }
                    }
                }
                val keys = response?.edges?.mapNotNull {
                    it?.node?.let { node ->
                        RemoteKeysEntity(
                            repoId = node.id,
                            nextKey = if (response.pageInfo.hasNextPage) response.pageInfo.endCursor else null
                        )
                    }
                }
                keys?.let { remoteKeysDao.insertAll(it) }
            }
            return MediatorResult.Success(
                endOfPaginationReached = response?.pageInfo?.hasNextPage?.let { !it } ?: true
            )
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, CharacterEntity>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                getLastRemoteKey(state)?.nextKey ?: ""
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, CharacterEntity>): RemoteKeysEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { character -> remoteKeysDao.remoteKeysCharacterId(character.id) }
    }
}