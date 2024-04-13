package com.example.vibratebreath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class RegisterUserActivity : AppCompatActivity() {

    val validator = Validator();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_add_user = findViewById<Button>(R.id.btn_add_user);

        btn_add_user.setOnClickListener {
            if(validate()==0) {
                val intent = Intent(this@RegisterUserActivity, MainActivity::class.java);
                startActivity(intent);
            }
        }

    }   // FIN DE onCreate()

    fun validate() : Int {

        //References
        val til_vru_name = findViewById<TextInputLayout>(R.id.til_vru_name);
        val til_vru_email = findViewById<TextInputLayout>(R.id.til_vru_email);
        val til_vru_dob = findViewById<TextInputLayout>(R.id.til_vru_dob);
        val til_vru_pass = findViewById<TextInputLayout>(R.id.til_vru_pass);
        val til_vru_rpass = findViewById<TextInputLayout>(R.id.til_vru_rpass);

        var name = til_vru_name.editText?.text.toString();
        var email = til_vru_email.editText?.text.toString();
        var dateOfBirth = til_vru_dob.editText?.text.toString();
        var pass = til_vru_pass.editText?.text.toString();
        var rPass = til_vru_rpass.editText?.text.toString();
        var totalErrors : Int = 0;

        if(validator.isNull(name)) {
            til_vru_name.error = getString(R.string.null_field);
            totalErrors += 1;
        } else if(!validator.validateName(name)) {
            til_vru_name.error = getString(R.string.invalid_format_username);
            totalErrors += 1;
        } else {
            til_vru_name.error = "";
        }

        if(validator.isNull(email)) {
            til_vru_email.error = getString(R.string.null_field);
            totalErrors += 1;
        } else if(!validator.validateEmail(email)) {
            til_vru_email.error = getString(R.string.invalid_format_email);
            totalErrors += 1;
        } else {
            til_vru_email.error = "";
        }

        if(validator.isNull(dateOfBirth)) {
            til_vru_dob.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            til_vru_dob.error = "";
        }

        if(validator.isNull(pass)) {
            til_vru_pass.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            if(!validator.isEqualTo(pass, rPass)) {
                til_vru_pass.error = getString(R.string.passwords_not_match);
                til_vru_rpass.error = getString(R.string.passwords_not_match);
                totalErrors += 1;
            } else {
                til_vru_pass.error = "";
                til_vru_rpass.error = "";
            }
        }

        return totalErrors;
    }

    // Example for validate()
    // println("${name} - ${email} - ${dateOfBirth} - ${pass} - ${rPass}");

}