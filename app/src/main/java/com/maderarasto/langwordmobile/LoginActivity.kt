package com.maderarasto.langwordmobile

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.android.volley.Request
import com.android.volley.Response
import com.maderarasto.langwordmobile.services.AuthService
import com.maderarasto.langwordmobile.utils.JsonRequestQueue
import com.maderarasto.langwordmobile.utils.Preferences
import com.maderarasto.langwordmobile.views.ValidatableEdit
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private val editBindings = HashMap<String, ValidatableEdit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editBindings["email"] = findViewById(R.id.edit_email)
        editBindings["password"] = findViewById(R.id.edit_password)
    }

    fun onLoginSubmit(view: View) {
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        clearErrorMessages()

        val editEmail: ValidatableEdit = findViewById(R.id.edit_email)
        val editPassword: ValidatableEdit = findViewById(R.id.edit_password)

        AuthService.getInstance(this).login(editEmail.text, editPassword.text,
            onLoginResponse(), onLoginError())
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

    private fun clearErrorMessages() {
        editBindings.forEach { iterator -> iterator.value.hideValidationError() }
    }

    private fun onLoginResponse() : Response.Listener<JSONObject> {
        return Response.Listener<JSONObject> { response ->
            Preferences.getInstance(this).accessToken = response.getString("access_token")

            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent, activityOptions.toBundle())
        }
    }

    private fun onLoginError() : Response.ErrorListener {
        return Response.ErrorListener { error ->
            val errorData = JSONObject(String(error.networkResponse.data))
            val errorMessages: JSONObject = errorData.getJSONObject("errors")

            Log.e("LoginActivity", errorData.getString("message"))
            Log.e("LoginActivity", errorData.toString())

            errorMessages.keys().forEach { key ->
                editBindings[key]?.errorText = errorMessages.getJSONArray(key)[0].toString()
                editBindings[key]?.showValidationError()
            }
        }
    }
}