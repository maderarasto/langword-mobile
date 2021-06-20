package com.maderarasto.langwordmobile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import com.maderarasto.langwordmobile.views.ValidatableEdit

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }

    fun onLoginSubmit(view: View) {
        val validatableEdit: ValidatableEdit = findViewById(R.id.edit_email)

        validatableEdit.errorText = "Wrong credentials"
        validatableEdit.showValidationError()
    }
}