package com.guilleac.marvelapi.model

import java.io.Serializable

data class CharacterImage(
    var path: String,
    var extension: String
): Serializable