package com.maderarasto.langwordmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CreateTopicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_topic)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        toolbar.title = applicationContext.getString(R.string.create_topic_title)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun onCreateTopicSubmit(view: View) {

    }
}