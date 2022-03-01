package com.example.starwarsapp.detail

import androidx.lifecycle.ViewModel
import com.example.data.repository.model.GetCharacterResponse
import com.example.usecases.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val characterUseCase: GetCharacterUseCase
) : ViewModel() {

    lateinit var getCharacter: Flow<GetCharacterResponse>

    fun setCharacterId(newValue: String) {
        getCharacter = characterUseCase.invoke(newValue)
    }
}
