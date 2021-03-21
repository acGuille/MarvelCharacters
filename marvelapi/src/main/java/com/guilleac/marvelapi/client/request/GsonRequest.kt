package com.guilleac.marvelapi.client.request

import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.guilleac.marvelapi.client.Networking
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

open class GsonRequest<T>(method: Int, url: String,
                          private val clazz: Class<T>,
                          dataToSend: String?,
                          private val headers: MutableMap<String, String>?,
                          private val listener: Response.Listener<T>,
                          errorListener: Response.ErrorListener) : JsonRequest<T>(method, url, dataToSend, listener ,errorListener) {

    private val gson: Gson

    init{
        this.retryPolicy = DefaultRetryPolicy(Networking.DEFAULT_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        gson =  GsonBuilder.buildGson()
    }

    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(response?.data ?: ByteArray(0), Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
            Log.d("GSONRequest", json)
            Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
    }
}