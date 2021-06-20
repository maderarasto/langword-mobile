package com.maderarasto.langwordmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.maderarasto.langwordmobile.views.ValidatableEdit

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
    }

    fun onLoginSubmit(view: View) {
        val validatableEdit: ValidatableEdit = findViewById(R.id.edit_email);

        validatableEdit.errorText = "Wrong credentials";
        validatableEdit.showValidationError();
    }
}