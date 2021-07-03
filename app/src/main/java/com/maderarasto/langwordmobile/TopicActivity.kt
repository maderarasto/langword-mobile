package com.maderarasto.langwordmobile

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.maderarasto.langwordmobile.models.Topic
import com.maderarasto.langwordmobile.services.ErrorResponse
import com.maderarasto.langwordmobile.services.JSONObjectResponse
import com.maderarasto.langwordmobile.services.TopicResponse
import com.maderarasto.langwordmobile.services.TopicService
import org.json.JSONObject
import java.lang.NullPointerException

class TopicActivity : AppCompatActivity() {
    private var topic: Topic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        val topicId = intent.getLongExtra("topicId", 0)

        TopicService.getInstance(this).find(topicId, onTopicFindResponse(), onTopicFindError())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.topic_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_edit_topic) {
        } else if (item.itemId == R.id.menu_item_delete_topic) {
            val dialogBuilder = AlertDialog.Builder(this)

            dialogBuilder.setTitle("Delete Topic")
            dialogBuilder.setMessage("Are you sure you want delete this topic?")
            dialogBuilder.setPositiveButton("Delete", onAlertDialogSubmit())
            dialogBuilder.setNegativeButton("Cancel", onAlertDialogCancel())
            dialogBuilder.show()

        }

        return super.onOptionsItemSelected(item)
    }

    private fun onTopicFindResponse() : TopicResponse {
        return TopicResponse { topic ->
            this.topic = topic

            val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
            toolbar.title = topic.name
            setSupportActionBar(toolbar)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun onTopicFindError() : ErrorResponse {
        return ErrorResponse { error ->
            val errorData = JSONObject(String(error.networkResponse.data))

            Log.e("TopicActivity", errorData.getString("message"))
            Log.e("TopicActivity", errorData.toString())
        }
    }

    private fun onAlertDialogSubmit() : DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog: DialogInterface, _: Int ->
            if (topic == null)
                throw NullPointerException("Topic not found")

            TopicService.getInstance(this).delete(topic!!.id, {
                dialog.dismiss()

                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this)
                val intent = Intent(this, DashboardActivity::class.java)

                intent.putExtra("message", "Deleted topic")

                startActivity(intent, activityOptions.toBundle())
            }, onTopicFindError())
        }
    }

    private fun onAlertDialogCancel() : DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
    }

    private fun onDeleteTopicError() : ErrorResponse {
        return ErrorResponse { error ->
            val errorData = JSONObject(String(error.networkResponse.data))

            Log.e("TopicActivity", errorData.getString("message"))
            Log.e("TopicActivity", errorData.toString())
        }
    }
}