package com.guilleac.marvelapi.client

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.guilleac.marvelapi.client.exception.ApiException
import com.guilleac.marvelapi.client.exception.ExceptionMapper
import com.guilleac.marvelapi.client.request.GsonArrayRequest
import com.guilleac.marvelapi.client.request.GsonBuilder
import com.guilleac.marvelapi.client.request.GsonRequest
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigInteger
import java.security.MessageDigest

class Networking(context: Context) {

    companion object {
        private const val TAG = "NETWORKING"
        private const val DEFAULT_RETRY = 6
        const val DEFAULT_TIME_OUT = 20000
    }

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private val gsonBuilder: Gson = GsonBuilder.buildGson()

    fun generateHash(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun <T : Any> getRequest(url: String, headers: MutableMap<String, String>?, clazz: Class<T>): Observable<T> {
        return request(Request.Method.GET, url, null, headers, clazz)
    }

    fun <T:Any> getRequestList(url: String, headers: MutableMap<String, String>?,clazz: Class<T>): Observable<List<T>>{
        return requestList(Request.Method.GET, url,null, headers ,clazz )
    }

    private fun <T : Any> request(method: Int, url: String, dataToSend: String?, headers: MutableMap<String, String>?, clazz: Class<T>): Observable<T> {
        return Observable.create { observer: ObservableEmitter<T> ->
            val request = GsonRequest(method, url, clazz, dataToSend, headers, { response ->
                if (response == null) {
                    observer.onError(ApiException.UnexpectedResponse())
                } else {
                    observer.onNext(response)
                    observer.onComplete()
                }
            }, {
                observer.onError(ExceptionMapper.mapVolleyErrorToApiException(it))
            })
            requestQueue.add(request)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun <T:Any> requestList(method: Int, url: String, dataToSend: String?, headers: MutableMap<String, String>?, clazz: Class<T>): Observable<List<T>>{
        return Observable.create {observer: ObservableEmitter<List<T>> ->
            val request = GsonArrayRequest(method, url, dataToSend, headers, { jsonArray ->
                val response = GsonBuilder.getListFromJsonArray(gsonBuilder, jsonArray, clazz)
                observer.onNext(response)
                observer.onComplete()
            }, {
                observer.onError(ExceptionMapper.mapVolleyErrorToApiException(it))
            })
            requestQueue.add(request)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}