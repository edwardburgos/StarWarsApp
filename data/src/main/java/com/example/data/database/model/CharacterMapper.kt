package com.example.data.database.model

import com.example.domain.utils.DomainMapper
import com.example.starwarsapp.CharactersListQuery
import java.util.*
import javax.inject.Inject

class CharacterMapper @Inject constructor(): DomainMapper<CharacterEntity, CharactersListQuery.Person> {
    override fun mapToDomainModel(model: CharacterEntity): CharactersListQuery.Person {
        return CharactersListQuery.Person(
            model.id,
            model.name,
            model.species,
            model.homeworld
        )
    }

    override fun mapFromDomainModel(domainModel: CharactersListQuery.Person): CharacterEntity {
        return CharacterEntity(
            domainModel.id,
            domainModel.name,
            domainModel.species,
            domainModel.homeworld,
            Date()
        )
    }

    fun fromEntityList(initial: List<CharacterEntity>): List<CharactersListQuery.Person>{
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<CharactersListQuery.Person>): List<CharacterEntity>{
        return initial.map { mapFromDomainModel(it) }
    }
}