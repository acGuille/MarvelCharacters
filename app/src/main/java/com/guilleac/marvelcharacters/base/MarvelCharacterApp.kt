package com.guilleac.marvelcharacters.base

import android.app.Application
import com.guilleac.marvelapi.client.MarvelClient

class MarvelCharacterApp : Application() {
    companion object {
        private var instance: MarvelCharacterApp? = null
        private var marvelApi: MarvelClient? = null
        fun marvelApi() = marvelApi ?: MarvelClient(instance!!.applicationContext)
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        marvelApi = MarvelClient(this)
    }
}