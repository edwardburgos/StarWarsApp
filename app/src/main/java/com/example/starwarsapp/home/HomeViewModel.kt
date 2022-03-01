package com.example.starwarsapp.home

import androidx.lifecycle.ViewModel
import com.example.data.repository.model.GetCharactersResponse
import com.example.usecases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
) : ViewModel() {

    var getCharacters = updateGetCharacters()

    private fun updateGetCharacters(): Flow<GetCharactersResponse> {
        return charactersUseCase.invoke()
    }
}