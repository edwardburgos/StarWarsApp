package com.example.data.database.model

import com.example.domain.utils.DomainMapper
import com.example.domain.Character
import javax.inject.Inject

class CharacterMapper @Inject constructor(): DomainMapper<CharacterEntity, Character> {
    override fun mapToDomainModel(model: CharacterEntity): Character {
        return Character(
            model.id,
            model.name,
            model.eyeColor,
            model.hairColor,
            model.skinColor,
            model.birthYear,
            model.vehicles,
            model.species,
            model.homeworld,
            model.favorite,
            model.markedAsFavoriteAt
        )
    }

    override fun mapFromDomainModel(domainModel: Character): CharacterEntity {
        return CharacterEntity(
            domainModel.id,
            domainModel.name,
            domainModel.eyeColor,
            domainModel.hairColor,
            domainModel.skinColor,
            domainModel.birthYear,
            domainModel.vehicles,
            domainModel.species,
            domainModel.homeworld,
            domainModel.favorite,
            domainModel.markedAsFavoriteAt
        )
    }

    fun fromEntityList(initial: List<CharacterEntity>): List<Character>{
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Character>): List<CharacterEntity>{
        return initial.map { mapFromDomainModel(it) }
    }
}