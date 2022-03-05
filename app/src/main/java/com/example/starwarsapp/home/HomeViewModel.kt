package com.example.starwarsapp.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.network.model.ResponseStatus
import com.example.data.repository.model.GetCharactersResponse
import com.example.starwarsapp.CharactersListQuery
import com.example.usecases.*
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
    private val getPagerUseCase: GetPagerUseCase
) : ViewModel() {

    var query = mutableStateOf("")

    var characters: Flow<PagingData<CharactersListQuery.Person>> = getPagerUseCase.invoke("").cachedIn(viewModelScope)
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

    fun setQuery(newValue: String) {
        query.value = newValue.replace("\n", "")
        characters = getPagerUseCase.invoke(newValue).cachedIn(viewModelScope)
    }
}