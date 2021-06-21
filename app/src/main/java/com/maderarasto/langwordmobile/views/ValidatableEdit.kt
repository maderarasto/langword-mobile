package com.maderarasto.langwordmobile.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.maderarasto.langwordmobile.R

/**
 * TODO: document your custom view class.
 */
class ValidatableEdit(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    var errorText: String = ""
    var text: String
        get() {
            val editText: EditText = findViewById(R.id.inner_edit_text)
            return editText.text.toString()
        }
        set(value) {
            val editText: EditText = findViewById(R.id.inner_edit_text)
            editText.setText(value)
        }

    init {
        inflate(context, R.layout.validatable_edit, this)

        val editText: EditText = findViewById(R.id.inner_edit_text)
        val validationError: TextView = findViewById(R.id.validation_error)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ValidatableEdit)

        editText.inputType = getFlagsCombination(attributes.getInt(R.styleable.ValidatableEdit_inputType, InputType.TYPE_CLASS_TEXT))
        editText.hint = attributes.getString(R.styleable.ValidatableEdit_hint)

        attributes.recycle()
    }

    fun showValidationError() {
        val validationError: TextView = findViewById(R.id.validation_error)

        validationError.text = errorText
        validationError.visibility = View.VISIBLE
    }

    fun hideValidationError() {
        val validationError: TextView = findViewById(R.id.validation_error)

        validationError.text = ""
        validationError.visibility = View.GONE
    }

    private fun getFlagsCombination(flag: Int): Int {
        val textFlags = (flag == InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS || flag == InputType.TYPE_TEXT_FLAG_CAP_WORDS
                || flag == InputType.TYPE_TEXT_FLAG_MULTI_LINE || flag == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                || flag == InputType.TYPE_TEXT_VARIATION_PASSWORD || flag == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
        val numberFlags = flag == InputType.TYPE_NUMBER_FLAG_SIGNED || flag == InputType.TYPE_NUMBER_FLAG_DECIMAL
                || flag == InputType.TYPE_NUMBER_VARIATION_PASSWORD

        if (textFlags)
            return InputType.TYPE_CLASS_TEXT or flag
        else if (numberFlags)
            return InputType.TYPE_CLASS_NUMBER or flag

        return flag
    }
}