package com.example.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.data.database.CharactersDao
import com.example.starwarsapp.CharactersListQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersPagingDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val charactersDao: CharactersDao,
    private val query: String
) : PagingSource<String, CharactersListQuery.Person>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, CharactersListQuery.Person> {
        val afterParam = params.key ?: ""
        return try {
            var response = apolloClient.query(CharactersListQuery(Optional.presentIfNotNull(5), Optional.presentIfNotNull(afterParam))).execute()
            CoroutineScope(Dispatchers.IO).launch {
                val existingRows = charactersDao.getAllOnce().map { character -> character.id }
                response.data?.allPeople?.people?.forEach {
                    it?.let { character ->
                        if (existingRows.indexOf(character.id) == -1) {
                            charactersDao.insertCharacter(
                                character.id,
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

            val pagedResponse = response.data
            val data = pagedResponse?.allPeople

            var nextAfterParam: String? = null
            if (pagedResponse?.allPeople?.pageInfo?.hasNextPage == true) {
                nextAfterParam = pagedResponse.allPeople.pageInfo.endCursor
            }

            val finalData = data?.let {
                it.people?.filterNotNull() ?: run { listOf(CharactersListQuery.Person(id = "", name = null, species = null, homeworld = null)) }
            } ?: run { listOf(CharactersListQuery.Person(id = "", name = null, species = null, homeworld = null)) }

            LoadResult.Page(
                data = if (query != "") finalData.filter { it?.name?.lowercase()?.contains(query) == true } else finalData,
                prevKey = null,
                nextKey = nextAfterParam
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, CharactersListQuery.Person>): String {
        return state.anchorPosition.toString()
    }
}