package com.guilleac.marvelapi.model

data class CharacterListInfo(
    var offset: Int,
    var limit: Int,
    var total: Int,
    var count: Int,
    var results: List<MarvelCharacter>
)