package com.guilleac.marvelapi.model

data class ResponseHolder(
    var code: Int,
    var status: String,
    var data: CharacterListInfo
)