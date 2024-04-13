package com.example.vibratebreath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class RegisterBreathActivity : AppCompatActivity() {

    private val validator = Validator();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_breath)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obteneción de WIDGETS de la vista
        val btn_add_breath = findViewById<Button>(R.id.btn_add_breath);

        btn_add_breath.setOnClickListener {
            if(validate()==0) {
                val intent = Intent(this@RegisterBreathActivity, ContextListActivity::class.java);
                startActivity(intent);
            }
        }

    }

    fun validate() : Int {

        // References
        val til_vrb_name = findViewById<TextInputLayout>(R.id.til_vrb_name);
        val til_vrb_desc = findViewById<TextInputLayout>(R.id.til_vrb_desc);
        val til_vrb_benefits = findViewById<TextInputLayout>(R.id.til_vrb_benefits);

        var name = til_vrb_name.editText?.text.toString();
        var description = til_vrb_desc.editText?.text.toString();
        var benefits = til_vrb_benefits.editText?.text.toString();
        var totalErrors : Int = 0;

        if(validator.isNull(name)) {
            til_vrb_name.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            til_vrb_name.error = "";
        }

        if(validator.isNull(description)) {
            til_vrb_desc.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            til_vrb_desc.error = "";
        }

        if(validator.isNull(benefits)) {
            til_vrb_benefits.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            til_vrb_benefits.error = "";
        }

        return totalErrors;
    }

}
