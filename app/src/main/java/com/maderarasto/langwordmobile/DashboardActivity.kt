package com.maderarasto.langwordmobile

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.android.volley.Request
import com.maderarasto.langwordmobile.utils.JsonRequestQueue
import com.maderarasto.langwordmobile.utils.Preferences
import org.json.JSONObject

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        toolbar.title = ""
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.logout_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_logout) {
            val url = "http://langword-api.herokuapp.com/api/auth/logout"
            val headers = HashMap<String, String>()

            headers["Content-Type"] = "application/json"
            headers["Accept"] = "application/json"
            headers["Authorization"] = "Bearer ${Preferences.getInstance(this).accessToken}"

            JsonRequestQueue.getInstance(this)
                .requestJsonObject(Request.Method.POST, url, JSONObject(), headers, {
                    val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent, activityOptions.toBundle())
                }, { error ->
                    val errorData = JSONObject(String(error.networkResponse.data))

                    Log.e("DashboardActivity", errorData.getString("message"))
                    Log.e("DashboardActivity", errorData.toString())

                    val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent, activityOptions.toBundle())
                })
        }

        return super.onOptionsItemSelected(item)
    }
}