package com.maderarasto.langwordmobile

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.maderarasto.langwordmobile.models.Topic
import com.maderarasto.langwordmobile.services.ErrorResponse
import com.maderarasto.langwordmobile.services.TopicResponse
import com.maderarasto.langwordmobile.services.TopicService
import com.maderarasto.langwordmobile.views.ValidatableEdit
import org.json.JSONObject

class EditTopicActivity : AppCompatActivity() {
    private var topic: Topic? = null
    private val editBindings = HashMap<String, ValidatableEdit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_topic)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.title = applicationContext.getString(R.string.edit_topic_title)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editBindings["name"] = findViewById(R.id.edit_topic_name)

        val topicId = intent.getLongExtra("topicId", 0)
        TopicService.getInstance(this).find(topicId, onTopicFindResponse(), onTopicFindError())
    }

    private fun onTopicFindResponse() : TopicResponse {
        return TopicResponse { topic ->
            this.topic = topic

            val editTopicName: ValidatableEdit = findViewById(R.id.edit_topic_name)

            editTopicName.text = topic.name
        }
    }

    private fun onTopicFindError() : ErrorResponse {
        return ErrorResponse { error ->
            val errorData = JSONObject(String(error.networkResponse.data))

            Log.e("EditTopicActivity", errorData.getString("message"))
            Log.e("EditTopicActivity", errorData.toString())
        }
    }

    fun onEditTopicSubmit(view: View) {
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        clearErrorMessages()

        val editTopicName: ValidatableEdit = findViewById(R.id.edit_topic_name)

        topic?.name = editTopicName.text

        TopicService.getInstance(this).update(topic!!, onEditTopicResponse(), onEditTopicError())
    }

    private fun clearErrorMessages() {
        editBindings.forEach { iterator -> iterator.value.hideValidationError() }
    }

    private fun onEditTopicResponse() : TopicResponse {
        return TopicResponse { topic ->
            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this)
            val intent = Intent(this, DashboardActivity::class.java)

            intent.putExtra("message", "Edited ${topic.name} Topicw")

            startActivity(intent, activityOptions.toBundle())
        }
    }

    private fun onEditTopicError() : ErrorResponse {
        return ErrorResponse { error ->
            val errorData = JSONObject(String(error.networkResponse.data))
            val errorMessages: JSONObject = errorData.getJSONObject("errors")

            Log.e("EditTopicActivity", errorData.getString("message"))
            Log.e("EditTopicActivity", errorData.toString())

            errorMessages.keys().forEach { key ->
                editBindings[key]?.errorText = errorMessages.getJSONArray(key)[0].toString()
                editBindings[key]?.showValidationError()
            }
        }
    }
}