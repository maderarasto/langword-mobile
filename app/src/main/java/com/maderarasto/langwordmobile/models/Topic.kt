package com.maderarasto.langwordmobile.models

import org.json.JSONObject

class Topic (val id: Long, val name: String) {

    companion object {
        fun fromJSON(json: JSONObject) : Topic {
            val id: Long = json.getLong("id")
            val name: String = json.getString("name")

            return Topic(id, name)
        }
    }

    fun toJSON(): JSONObject {
        val json = JSONObject()

        json.put("id", id)
        json.put("name", name)

        return json;
    }
}