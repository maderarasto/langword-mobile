package com.maderarasto.langwordmobile

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.maderarasto.langwordmobile.services.AuthService
import com.maderarasto.langwordmobile.utils.JsonRequestQueue
import com.maderarasto.langwordmobile.utils.Preferences
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        AuthService.getInstance(this).authUser(onAuthUserResponse(), onAuthUserError())
    }

    private fun onAuthUserResponse() : Response.Listener<JSONObject> {
        return Response.Listener {
            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent, activityOptions.toBundle())
        }
    }

    private fun onAuthUserError() : Response.ErrorListener {
        return Response.ErrorListener { error ->
            val errorData = JSONObject(String(error.networkResponse.data))
            Log.e("MainActivity", errorData.getString("message"))

            if (error.networkResponse.statusCode == 401) {
                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
                val intent = Intent(this, IntroActivity::class.java)
                startActivity(intent, activityOptions.toBundle())
            }
        }
    }
}