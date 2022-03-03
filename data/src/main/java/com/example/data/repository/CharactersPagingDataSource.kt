package com.example.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.starwarsapp.CharactersListQuery
import javax.inject.Inject

class CharactersPagingDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
) : PagingSource<String, CharactersListQuery.Person>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, CharactersListQuery.Person> {
        val afterParam = params.key ?: ""
        return try {
            val response = apolloClient.query(CharactersListQuery(Optional.presentIfNotNull(5), Optional.presentIfNotNull(afterParam))).execute()
            val pagedResponse = response.data
            val data = pagedResponse?.allPeople

            var nextAfterParam: String? = null
            if (pagedResponse?.allPeople?.pageInfo?.hasNextPage == true) {
                nextAfterParam = pagedResponse?.allPeople?.pageInfo?.endCursor
            }

            var finalData = data?.let {
                it.people?.let { people ->
                    people.filterNotNull()
                } ?: run { listOf(CharactersListQuery.Person(id = "", name = null, species = null, homeworld = null)) }
            } ?: run { listOf(CharactersListQuery.Person(id = "", name = null, species = null, homeworld = null)) }

            LoadResult.Page(
                data = finalData,
                prevKey = null,
                nextKey = nextAfterParam
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, CharactersListQuery.Person>): String? {
        return state.anchorPosition.toString()
    }
}