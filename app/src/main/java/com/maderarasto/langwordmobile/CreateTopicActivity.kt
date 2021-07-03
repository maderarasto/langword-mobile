package com.maderarasto.langwordmobile

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.maderarasto.langwordmobile.services.ErrorResponse
import com.maderarasto.langwordmobile.services.TopicResponse
import com.maderarasto.langwordmobile.services.TopicService
import com.maderarasto.langwordmobile.views.ValidatableEdit
import org.json.JSONObject

class CreateTopicActivity : AppCompatActivity() {
    private val editBindings = HashMap<String, ValidatableEdit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_topic)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        toolbar.title = applicationContext.getString(R.string.create_topic_title)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editBindings["name"] = findViewById(R.id.edit_topic_name)
    }

    fun onCreateTopicSubmit(view: View) {
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        clearErrorMessages()

        val editTopicName: ValidatableEdit = findViewById(R.id.edit_topic_name)

        val topicData = JSONObject()

        topicData.put("name", editTopicName.text)

        TopicService.getInstance(this).store(topicData, onCreateTopicResponse(), onCreateTopicError())
    }

    private fun clearErrorMessages() {
        editBindings.forEach { iterator -> iterator.value.hideValidationError() }
    }

    private fun onCreateTopicResponse() : TopicResponse {
        return TopicResponse { topic ->
            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this)
            val intent = Intent(this, DashboardActivity::class.java)

            intent.putExtra("message", "Created Topic ${topic.name}")

            startActivity(intent, activityOptions.toBundle())
        }
    }

    private fun onCreateTopicError() : ErrorResponse {
        return ErrorResponse { error ->
            val errorData = JSONObject(String(error.networkResponse.data))
            val errorMessages: JSONObject = errorData.getJSONObject("errors")

            Log.e("CreateTopicActivity", errorData.getString("message"))
            Log.e("CreateTopicActivity", errorData.toString())

            errorMessages.keys().forEach { key ->
                editBindings[key]?.errorText = errorMessages.getJSONArray(key)[0].toString()
                editBindings[key]?.showValidationError()
            }
        }
    }
}