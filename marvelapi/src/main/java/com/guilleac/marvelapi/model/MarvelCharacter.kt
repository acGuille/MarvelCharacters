package com.guilleac.marvelapi.model

import java.io.Serializable
import java.util.*

data class MarvelCharacter(
    var id: Int,
    var name: String,
    var description: String,
    var modified: Date,
    var thumbnail: CharacterImage,
    var resourceURI: String
): Serializable