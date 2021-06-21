package com.maderarasto.langwordmobile

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.ColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_intro)
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