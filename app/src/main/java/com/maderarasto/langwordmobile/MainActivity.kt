package com.maderarasto.langwordmobile

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }

    fun onLoginClicked(view: View) {
        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent, activityOptions.toBundle())
    }

    fun onRegisterClicked(view: View) {
        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent, activityOptions.toBundle())
    }
}