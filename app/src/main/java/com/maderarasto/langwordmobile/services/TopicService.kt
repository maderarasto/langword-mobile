package com.maderarasto.langwordmobile.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.maderarasto.langwordmobile.models.Topic
import com.maderarasto.langwordmobile.utils.JsonRequestQueue
import com.maderarasto.langwordmobile.utils.Preferences
import org.json.JSONArray
import org.json.JSONObject

typealias TopicResponse = Response.Listener<Topic>
typealias TopicListResponse = Response.Listener<ArrayList<Topic>>

class TopicService(context: Context, private val apiUrl: String) {
    companion object {
        @Volatile
        private var INSTANCE: TopicService? = null

        fun getInstance(context: Context) =
            INSTANCE ?: TopicService(context, "http://langword-api.herokuapp.com/api/topics").also {
                INSTANCE = it
        }
    }

    private val requestQueue: JsonRequestQueue by lazy {
        JsonRequestQueue.getInstance(context)
    }

    private val preferences: Preferences by lazy {
        Preferences.getInstance(context)
    }

    fun get(responseListener: TopicListResponse, onError: ErrorResponse) {
        val url = "$apiUrl/"
        val headers = HashMap<String, String>()

        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"
        headers["Authorization"] = "Bearer ${preferences.accessToken}"

        requestQueue.requestJsonArray(Request.Method.GET, url, JSONArray(), headers, { response ->
            val topicList = ArrayList<Topic>()

            for (i in 0 until response.length()) {
                topicList.add(Topic.fromJSON(response[i] as JSONObject))
            }

            responseListener.onResponse(topicList)
        }, onError)
    }
}