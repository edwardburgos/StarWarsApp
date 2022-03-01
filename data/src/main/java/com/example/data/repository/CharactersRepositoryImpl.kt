package com.example.data.repository

import com.apollographql.apollo3.ApolloClient
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharactersResponse
import com.example.data.repository.utils.refreshIntervalMsLong
import com.example.data.repository.utils.refreshIntervalMsShort
import com.example.starwarsapp.CharactersListQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : CharactersRepository {

    override fun getCharacters(): Flow<GetCharactersResponse> {
        return flow {
            var finalResponse = GetCharactersResponse(ResponseStatus.INITIAL, null)
            var errorResponse = GetCharactersResponse(ResponseStatus.ERROR, null)
            while (true) {
                try {
                    var result = apolloClient.query(CharactersListQuery()).execute()
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
}