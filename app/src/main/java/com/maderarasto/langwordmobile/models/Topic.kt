package com.maderarasto.langwordmobile.models

import org.json.JSONObject

class Topic (val id: Long, var name: String) {

    companion object {
        fun fromJSON(json: JSONObject) : Topic {
            val id: Long = json.getLong("id")
            val name: String = json.getString("name")

            return Topic(id, name)
        }
    }

    fun toJSON(except: Array<String>): JSONObject {
        val json = JSONObject()

        if (!except.contains("id"))
            json.put("id", id)
        if (!except.contains("name"))
            json.put("name", name)

        return json;
    }
}