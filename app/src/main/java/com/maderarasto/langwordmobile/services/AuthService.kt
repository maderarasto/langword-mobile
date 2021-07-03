package com.maderarasto.langwordmobile.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.maderarasto.langwordmobile.utils.JsonRequestQueue
import com.maderarasto.langwordmobile.utils.Preferences
import org.json.JSONObject

typealias JSONObjectResponse = Response.Listener<JSONObject>
typealias ErrorResponse = Response.ErrorListener

class AuthService(context: Context, private val apiUrl: String) {
    companion object {
        @Volatile
        private var INSTANCE: AuthService? = null

        fun getInstance(context: Context) =
            INSTANCE ?: AuthService(context, "http://langword-api.herokuapp.com/api/auth").also {
                INSTANCE = it
        }
    }

    private val requestQueue: JsonRequestQueue by lazy {
        JsonRequestQueue.getInstance(context)
    }

    private val preferences: Preferences by lazy {
        Preferences.getInstance(context)
    }

    fun login(email: String, password: String, onResponse: JSONObjectResponse, onError: ErrorResponse) {
        val url = "$apiUrl/login"
        val headers = HashMap<String, String>()
        val data = JSONObject()

        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"

        data.put("email", email)
        data.put("password", password)

        requestQueue.requestJsonObject(Request.Method.POST, url, data, headers, onResponse, onError)
    }

    fun register(registerData: JSONObject, onResponse: JSONObjectResponse, onError: ErrorResponse) {
        val url = "$apiUrl/register"
        val headers = HashMap<String, String>()

        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"

        requestQueue.requestJsonObject(Request.Method.POST, url, registerData, headers, onResponse, onError)
    }

    fun authUser(onResponse: JSONObjectResponse, onError: ErrorResponse) {
        val url = "$apiUrl/user"
        val headers = HashMap<String, String>()
        val data = JSONObject()

        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"
        headers["Authorization"] = "Bearer ${preferences.accessToken}"

        requestQueue.requestJsonObject(Request.Method.GET, url, data, headers, onResponse, onError)
    }

    fun logout(onResponse: JSONObjectResponse, onError: ErrorResponse) {
        val url = "$apiUrl/logout"
        val headers = HashMap<String, String>()
        val data = JSONObject()

        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"
        headers["Authorization"] = "Bearer ${preferences.accessToken}"

        requestQueue.requestJsonObject(Request.Method.POST, url, data, headers, onResponse, onError)
    }
}