package com.maderarasto.langwordmobile.utils

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class  JsonRequestQueue constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: JsonRequestQueue? = null
        fun getInstance(context: Context) =
            INSTANCE ?: JsonRequestQueue(context).also {
                INSTANCE = it
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun requestJsonObject(method: Int, url: String, data: JSONObject?, headers: MutableMap<String, String>,
                          onResponse: Response.Listener<JSONObject>, onError: Response.ErrorListener) {
        val jsonRequest = object: JsonObjectRequest(method, url, data, onResponse, onError) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        requestQueue.add(jsonRequest)
    }

    fun requestJsonArray(method: Int, url: String, data: JSONArray?, headers: MutableMap<String, String>,
                         onResponse: Response.Listener<JSONArray>, onError: Response.ErrorListener) {
        val jsonRequest = object: JsonArrayRequest(method, url, data, onResponse, onError) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        requestQueue.add(jsonRequest)
    }
}