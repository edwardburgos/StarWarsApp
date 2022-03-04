package com.example.domain

data class Character (
    var id: String,
    var name: String?,
    var eyeColor: String?,
    var hairColor: String?,
    var skinColor: String?,
    var birthYear: String?,
    var vehicles: List<String>?,
    var species: String?,
    var homeworld: String?,
    var favorite: Boolean,
    var markedAsFavoriteAt: Long?
)