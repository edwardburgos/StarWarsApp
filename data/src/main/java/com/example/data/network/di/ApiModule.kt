package com.example.data.network.di

import android.os.Looper
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.data.database.model.CharacterEntity
import com.example.data.network.model.MapperForNetwork
import com.example.domain.utils.DomainMapper
import com.example.starwarsapp.CharactersListQuery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun providesApolloClientBuilder(): ApolloClient {
        val okHttpClient = OkHttpClient.Builder().build()

        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the apolloClient instance"
        }

        return ApolloClient.Builder()
            .serverUrl("https://swapi-graphql.netlify.app/.netlify/functions/index")
            .okHttpClient(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesMapperForNetwork(mapperForNetwork: MapperForNetwork): DomainMapper<CharacterEntity, CharactersListQuery.Node> {
        return mapperForNetwork
    }
}