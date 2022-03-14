package com.example.starwarsapp.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.database.model.CharacterEntity
import com.example.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getFavoriteCharactersIdsUseCase: GetFavoriteCharactersIdsUseCase,
    private val checkUncheckAsFavoriteUseCase: CheckUncheckAsFavoriteUseCase,
    private val getPagerUseCase: GetPagerUseCase
) : ViewModel() {

    var query = mutableStateOf("") // TODO: mutble states shud be privates
    var previousNetworkState = mutableStateOf(true)

    var characters: Flow<PagingData<CharacterEntity>> = getPagerUseCase.invoke("").cachedIn(viewModelScope)

    val getFavoriteCharactersIds = getFavoriteCharactersIdsUseCase.invoke()

    fun checkUncheckAsFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            checkUncheckAsFavoriteUseCase.invoke(id)
        }
    }

    fun setQuery(newValue: String) {
        query.value = newValue.replace("\n", "") // TODO old values hsould string
        characters = getPagerUseCase.invoke(newValue).cachedIn(viewModelScope)
    }

    fun setPreviousNetworkState(newValue: Boolean) {
        previousNetworkState.value = newValue
    }
}