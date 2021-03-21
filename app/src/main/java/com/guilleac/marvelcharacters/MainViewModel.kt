package com.guilleac.marvelcharacters

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.guilleac.marvelapi.client.MarvelClient
import com.guilleac.marvelapi.client.exception.ApiException
import com.guilleac.marvelapi.model.MarvelCharacter
import com.guilleac.marvelapi.model.ResponseHolder
import com.guilleac.marvelcharacters.base.BaseEvent
import com.guilleac.marvelcharacters.base.MarvelCharacterApp
import com.guilleac.marvelcharacters.base.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

class MainViewModel : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
        private const val DEFAULT_LIMIT = 20
    }

    private val marvelApi: MarvelClient = MarvelCharacterApp.marvelApi()
    private val singleLiveEvent : SingleLiveEvent<BaseEvent> = SingleLiveEvent()
    private val disposables = CompositeDisposable()

    val characterList = MutableLiveData<ResponseHolder>()

    fun getCharacters(offSet: Int? = 0) {
        val response = marvelApi.getCharacterList(offSet ?: 0, DEFAULT_LIMIT).subscribe({
            characterList.value = it
        }, {
            Log.d(TAG, "Error: ${it.message}")
            processErrorResponse(it)
        })
        disposables.add(response)
    }

    private fun processErrorResponse(error: Throwable) {
        when (error) {
            is ApiException.ConnectionErrorException -> this.pushEvent(BaseEvent.ConnectionError(R.string.connection_error, error.statusCode))
            is ApiException.NoInternetConnectionException -> this.pushEvent(BaseEvent.ConnectionError(R.string.connection_error, error.statusCode))
            is ApiException.TimeoutErrorException -> this.pushEvent(BaseEvent.ConnectionError(R.string.generic_error, error.statusCode))
            else -> this.pushEvent(BaseEvent.GenericConnectionError)
        }
    }

    private fun pushEvent(event: BaseEvent){
        singleLiveEvent.value = event
    }

    fun observeEvents(owner: LifecycleOwner, observer: Observer<BaseEvent>){
        singleLiveEvent.observe(owner, observer)
    }
}