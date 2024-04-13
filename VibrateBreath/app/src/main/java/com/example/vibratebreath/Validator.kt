package com.example.vibratebreath

import android.util.Patterns
import java.util.regex.Pattern

class Validator {

    fun isNull(field : String) : Boolean {
        return field.trim().isEmpty() || field.trim().length == 0 || field.isNullOrBlank();
    }

    fun isEqualTo(field1 : String, field2 : String) : Boolean {
        return field1.trim().equals(field2.trim());
    }

    fun validateName(name : String) : Boolean {
        val pattern = Pattern.compile("^[a-zA-Z0-9]+\$");
        return pattern.matcher(name).matches();
    }

    fun validateEmail(email : String) : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}