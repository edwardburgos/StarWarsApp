package com.example.domain.utils

import java.util.*

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
    var detailsIncluded: Boolean,
    var updatedAt: Date
)