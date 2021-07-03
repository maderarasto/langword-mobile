package com.maderarasto.langwordmobile

import com.maderarasto.langwordmobile.adapters.TopicAdapter
import com.maderarasto.langwordmobile.models.Topic
import com.maderarasto.langwordmobile.services.*

import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import org.json.JSONObject

import android.content.Intent
import android.widget.AdapterView
import android.widget.ListView
import android.view.MenuItem
import android.view.Menu
import android.view.View
import android.util.Log
import android.app.ActivityOptions
import android.os.Bundle

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        toolbar.title = "Topics"
        setSupportActionBar(toolbar)

        TopicService.getInstance(this).get(onGetTopicsResponse(), onGetTopicsError())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.dashboard_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_logout) {
            AuthService.getInstance(this).logout(onLogoutResponse(), onLogoutError())
        } else if (item.itemId == R.id.menu_item_create_topic) {
            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this)
            val intent = Intent(this, CreateTopicActivity::class.java)
            startActivity(intent, activityOptions.toBundle())
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }

    private fun onSelectedTopicItem() : AdapterView.OnItemClickListener {
        return AdapterView.OnItemClickListener { adapterView: AdapterView<*>, _: View, i: Int, _: Long ->
            val topic: Topic = adapterView.getItemAtPosition(i) as Topic

            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this)
            val intent = Intent(this, TopicActivity::class.java)

            intent.putExtra("topicName", topic.name)

            startActivity(intent, activityOptions.toBundle())
        }
    }

    private fun onLogoutResponse() : JSONObjectResponse {
        return JSONObjectResponse {
            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent, activityOptions.toBundle())
        }
    }

    private fun onLogoutError() : ErrorResponse {
        return ErrorResponse { error ->
            val errorData = JSONObject(String(error.networkResponse.data))

            Log.e("DashboardActivity", errorData.getString("message"))
            Log.e("DashboardActivity", errorData.toString())

            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent, activityOptions.toBundle())
        }
    }

    private fun onGetTopicsResponse() : TopicListResponse {
        return Response.Listener { topicList ->
            val listView: ListView = findViewById(R.id.list_view_topics)

            listView.adapter = TopicAdapter(this, topicList)
            listView.onItemClickListener = onSelectedTopicItem()
        }
    }

    private fun onGetTopicsError() : ErrorResponse {
        return ErrorResponse { error ->
            val errorData = JSONObject(String(error.networkResponse.data))

            Log.e("DashboardActivity", errorData.getString("message"))
            Log.e("DashboardActivity", errorData.toString())
        }
    }
}