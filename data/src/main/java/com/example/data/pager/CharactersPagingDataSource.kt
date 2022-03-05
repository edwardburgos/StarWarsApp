package com.example.data.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.data.database.CharactersDao
import com.example.data.database.CharactersDatabase
import com.example.data.database.model.CharacterEntity
import com.example.data.network.model.MapperForNetwork
import com.example.domain.utils.DomainMapper
import com.example.starwarsapp.CharactersListQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersPagingDataSource (
    private val apolloClient: ApolloClient,
    private val mapperForNetwork: DomainMapper<CharacterEntity, CharactersListQuery.Node>,
    private val database: CharactersDatabase,
    private val query: String
) : PagingSource<String, CharactersListQuery.Node>() {
    private val charactersDao = database.charactersDao

    override suspend fun load(params: LoadParams<String>): LoadResult<String, CharactersListQuery.Node> {
        val afterParam = params.key ?: ""
        return try {
            val response = apolloClient.query(
                CharactersListQuery(
                    Optional.presentIfNotNull(5),
                    Optional.presentIfNotNull(afterParam)
                )
            ).execute()
            CoroutineScope(Dispatchers.IO).launch {
                val existingCursors = charactersDao.getCursors()
                response.data?.allPeople?.edges?.forEach {
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
            }

            val pagedResponse = response.data
            val data = pagedResponse?.allPeople?.edges

            var nextAfterParam: String? = null
            if (pagedResponse?.allPeople?.pageInfo?.hasNextPage == true) {
                nextAfterParam = pagedResponse.allPeople.pageInfo.endCursor
            }

            val finalData = data?.mapNotNull { it?.node } ?: run {
                listOf(
                    CharactersListQuery.Node(
                        id = "",
                        name = null,
                        species = null,
                        homeworld = null
                    )
                )
            }

            LoadResult.Page(
                data = if (query != "") finalData.filter {
                    it.name?.lowercase()?.contains(query) == true
                } else finalData,
                prevKey = null,
                nextKey = nextAfterParam
            )
        } catch (e: Exception) {
            database.withTransaction {
                val finalDataNoFormatted = charactersDao.getAllOnce()
                var finalData = mapperForNetwork.fromEntityList(finalDataNoFormatted)
                val existingCursors = charactersDao.getCursors()
                if (finalDataNoFormatted.isEmpty() || afterParam == existingCursors.last()) {
                    LoadResult.Error(e)
                } else {
                    if (afterParam != "") {
                        val restOfCharacters =
                            finalDataNoFormatted.indexOfFirst { it.cursor == afterParam }
                        finalData = mapperForNetwork.fromEntityList(
                            finalDataNoFormatted.subList(
                                restOfCharacters + 1,
                                finalDataNoFormatted.size
                            )
                        )
                    }
                    LoadResult.Page(
                        data = if (query != "") finalData.filter {
                            it.name?.lowercase()?.contains(query) == true
                        } else finalData,
                        prevKey = null,
                        nextKey = existingCursors.last())
                }
            }
        }
    }

    override fun getRefreshKey(state: PagingState<String, CharactersListQuery.Node>): String {
        return state.anchorPosition.toString()
    }
}