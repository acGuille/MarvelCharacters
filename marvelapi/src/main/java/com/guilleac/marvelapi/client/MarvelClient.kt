package com.guilleac.marvelapi.client

import android.content.Context
import com.guilleac.marvelapi.EndpointsDefinition
import com.guilleac.marvelapi.MarvelApi
import com.guilleac.marvelapi.model.ResponseHolder
import io.reactivex.Observable

class MarvelClient(context: Context) : MarvelApi {
    companion object {
        const val PUBLIC_KEY = ""
        const val PRIVATE_KEY = ""
        const val TIME_SPAN = 1000
    }

    private val networking = Networking(context)

    override fun getCharacterList(offset: Int, limit: Int): Observable<ResponseHolder> {
        val hash = networking.generateHash(TIME_SPAN.toString()+PRIVATE_KEY+PUBLIC_KEY)
        val url = "${EndpointsDefinition.MARVEL_CHARACTERS}?ts=$TIME_SPAN&apikey=$PUBLIC_KEY&hash=$hash&offset=$offset&limit=$limit"
        return networking.getRequest(url, null, ResponseHolder::class.java)
    }
}
