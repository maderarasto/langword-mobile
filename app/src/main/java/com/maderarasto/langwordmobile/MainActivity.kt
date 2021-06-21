package com.maderarasto.langwordmobile

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.maderarasto.langwordmobile.utils.JsonRequestQueue
import com.maderarasto.langwordmobile.utils.Preferences
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val url = "http://langword-api.herokuapp.com/api/auth/user"
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"
        headers["Authorization"] = "Bearer ${Preferences.getInstance(this).accessToken}"

        JsonRequestQueue.getInstance(this).requestJsonObject(
            Request.Method.GET, url, null, headers,
            {
                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent, activityOptions.toBundle())
            }, { error ->
                val errorData = JSONObject(String(error.networkResponse.data))
                Log.e("MainActivity", errorData.getString("message"))

                if (error.networkResponse.statusCode == 401) {
                    val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
                    val intent = Intent(this, IntroActivity::class.java)
                    startActivity(intent, activityOptions.toBundle())
                }
            }
        )
    }
}