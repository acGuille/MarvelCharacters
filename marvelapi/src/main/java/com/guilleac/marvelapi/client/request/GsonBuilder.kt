package com.guilleac.marvelapi.client.request

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONArray

class GsonBuilder {
    companion object{
        fun buildGson(): Gson {
            val gsonBuilder = GsonBuilder()
            return gsonBuilder.create()
        }

        fun <T:Any>getListFromJsonArray(gson: Gson, jsonArray: JSONArray, clazz: Class<T>): List<T>{
            val response = mutableListOf<T>()
            for(i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                response.add(gson.fromJson(jsonObject.toString(), clazz))
            }
            return response
        }
    }
}