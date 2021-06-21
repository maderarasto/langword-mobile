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
import com.maderarasto.langwordmobile.utils.JsonRequestQueue
import com.maderarasto.langwordmobile.utils.Preferences
import com.maderarasto.langwordmobile.views.ValidatableEdit
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.typeOf

class RegisterActivity : AppCompatActivity() {
    private val editBindings = HashMap<String, ValidatableEdit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editBindings["name"] = findViewById(R.id.edit_name)
        editBindings["email"] = findViewById(R.id.edit_email)
        editBindings["password"] = findViewById(R.id.edit_password)
        editBindings["password_confirmation"] = findViewById(R.id.edit_password_confirm)
    }

    fun onRegisterSubmit(view: View) {
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)

        val editName: ValidatableEdit = findViewById(R.id.edit_name)
        val editEmail: ValidatableEdit = findViewById(R.id.edit_email)
        val editPassword: ValidatableEdit = findViewById(R.id.edit_password)
        val editPassConfirm: ValidatableEdit = findViewById(R.id.edit_password_confirm)

        val url = "http://langword-api.herokuapp.com/api/auth/register"
        val data = JSONObject()
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"

        data.put("name", editName.text)
        data.put("email", editEmail.text)
        data.put("password", editPassword.text)
        data.put("password_confirmation", editPassConfirm.text)

        JsonRequestQueue.getInstance(this).requestJsonObject(
            Request.Method.POST, url, data, headers,
            { response ->
                Preferences.getInstance(this).accessToken = response.getString("access_token")

                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent, activityOptions.toBundle())
            }, { error ->
                val errorData = JSONObject(String(error.networkResponse.data))
                val errorMessages: JSONObject = errorData.getJSONObject("errors")

                Log.e("RegisterActivity", errorData.getString("message"))
                Log.e("RegisterActivity", errorData.toString())

                errorMessages.keys().forEach { key ->
                    editBindings[key]?.errorText = errorMessages.getJSONArray(key)[0].toString()
                    editBindings[key]?.showValidationError()
                }
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