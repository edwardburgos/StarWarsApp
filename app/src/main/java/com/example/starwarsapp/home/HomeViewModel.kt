package com.example.starwarsapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.repository.CharactersPagingDataSource
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharactersResponse
import com.example.starwarsapp.CharactersListQuery
import com.example.usecases.CheckUncheckAsFavoriteUseCase
import com.example.usecases.GetCharactersUseCase
import com.example.usecases.GetFavoriteCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val checkUncheckAsFavoriteUseCase: CheckUncheckAsFavoriteUseCase,
    private val dataSource: CharactersPagingDataSource,
) : ViewModel() {

    val characters: Flow<PagingData<CharactersListQuery.Person>> =
        Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { dataSource }
        ).flow.cachedIn(viewModelScope)

    val getFavoriteCharacters = getFavoriteCharactersUseCase.invoke()

    val getCharacters =
        MutableStateFlow(GetCharactersResponse(status = ResponseStatus.INITIAL, characters = null))

    fun updateGetCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            getCharactersUseCase.invoke().collect {
                getCharacters.value = it
            }
        }
    }

    private val insertionResponse = MutableStateFlow(ResponseStatus.INITIAL)

    fun checkUncheckAsFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            checkUncheckAsFavoriteUseCase.invoke(id).collect {
                insertionResponse.value = it
            }
        }
    }
}