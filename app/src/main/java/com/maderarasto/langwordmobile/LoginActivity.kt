package com.maderarasto.langwordmobile

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.android.volley.Request
import com.maderarasto.langwordmobile.utils.JsonRequestQueue
import com.maderarasto.langwordmobile.views.ValidatableEdit
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun onLoginSubmit(view: View) {
        val editEmail: ValidatableEdit = findViewById(R.id.edit_email)
        val editPassword: ValidatableEdit = findViewById(R.id.edit_password)

        val url = "http://langword-api.herokuapp.com/api/auth/login"
        val data = JSONObject("{\"email\": \"${editEmail.text}\", \"password\": \"${editPassword.text}\"}")
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"

        JsonRequestQueue.getInstance(this).requestJsonObject(Request.Method.POST, url, data, headers,
            { response ->
                Log.i("LoginActivity", response.toString())
            }, { error ->
                val errorData = JSONObject(String(error.networkResponse.data))
                Log.e("LoginActivity", errorData.getString("message"))
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent, activityOptions.toBundle())
        }

        return true
    }

    override fun onBackPressed() {
        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent, activityOptions.toBundle())
    }
}