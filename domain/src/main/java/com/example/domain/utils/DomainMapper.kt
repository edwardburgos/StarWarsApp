package com.example.domain.utils

interface DomainMapper <T, DomainModel>{
    fun mapToDomainModel(model: T): DomainModel
    fun fromEntityList(initial: List<T>): List<DomainModel>
}