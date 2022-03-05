package com.example.data.network.model

import com.example.data.database.model.CharacterEntity
import com.example.domain.utils.DomainMapper
import com.example.starwarsapp.CharactersListQuery
import javax.inject.Inject

class MapperForNetwork @Inject constructor(): DomainMapper<CharacterEntity, CharactersListQuery.Node> {
    override fun mapToDomainModel(model: CharacterEntity): CharactersListQuery.Node {
        return CharactersListQuery.Node(
            model.id,
            model.name,
            CharactersListQuery.Species(name = model.species),
            CharactersListQuery.Homeworld(name = model.homeworld)
        )
    }

    override fun fromEntityList(initial: List<CharacterEntity>): List<CharactersListQuery.Node>{
        return initial.map { mapToDomainModel(it) }
    }
}