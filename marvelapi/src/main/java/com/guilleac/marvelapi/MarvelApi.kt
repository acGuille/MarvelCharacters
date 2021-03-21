package com.guilleac.marvelapi

import com.guilleac.marvelapi.model.ResponseHolder
import io.reactivex.Observable

interface MarvelApi {
    fun getCharacterList(offset:Int, limit:Int): Observable<ResponseHolder>
}