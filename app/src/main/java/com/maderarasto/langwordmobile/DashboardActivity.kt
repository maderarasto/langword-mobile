package com.maderarasto.langwordmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.maderarasto.langwordmobile.utils.Preferences

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        Log.i("DashboardActivity", "Access Token: ${Preferences.getInstance(this).accessToken}")
    }
}