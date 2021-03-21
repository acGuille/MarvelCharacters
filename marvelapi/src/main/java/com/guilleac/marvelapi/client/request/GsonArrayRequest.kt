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
import org.json.JSONArray
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

open class GsonArrayRequest(method: Int, url: String,
                            dataToSend: String?,
                            private val headers: MutableMap<String, String>?,
                            private val listener: Response.Listener<JSONArray>,
                            errorListener: Response.ErrorListener) : JsonRequest<JSONArray>(method, url, dataToSend, listener ,errorListener) {

    companion object{
        const val TAG = "GSONArrayRequest"
    }

    private val gson: Gson

    init{
        this.retryPolicy = DefaultRetryPolicy(Networking.DEFAULT_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        gson = GsonBuilder.buildGson()
    }

    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: JSONArray) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONArray> {
        return try {
            val json = String(response?.data ?: ByteArray(0), Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
            Log.d(TAG, json)
            Response.success(JSONArray(json), HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            Log.e(TAG, "Error in parseNetworkResponse: $e")
            e.printStackTrace()
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, "Error in parseNetworkResponse: $e")
            e.printStackTrace()
            Response.error(ParseError(e))
        }
    }
}