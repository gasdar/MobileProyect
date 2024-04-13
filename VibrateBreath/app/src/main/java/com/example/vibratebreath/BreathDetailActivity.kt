package com.example.vibratebreath

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class BreathDetailActivity : AppCompatActivity() {

    private val validator = Validator();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_breath_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fab_edit_breath = findViewById<FloatingActionButton>(R.id.fat_edit_breath);
        val fab_delete_breath = findViewById<FloatingActionButton>(R.id.fat_delete_breath);

        fab_edit_breath.setOnClickListener {
            if(validate()==0) {
                val intent = Intent(this@BreathDetailActivity, ContextListActivity::class.java);
                startActivity(intent);
            }
        }

        fab_delete_breath.setOnClickListener {
            if(validate()==0) {
                val intent = Intent(this@BreathDetailActivity, ContextListActivity::class.java);
                startActivity(intent);
            }
        }

    }

    fun validate() : Int {

        //References
        val til_vab_name = findViewById<TextInputLayout>(R.id.til_vab_name);
        val til_vab_desc = findViewById<TextInputLayout>(R.id.til_vab_desc);
        val til_vab_benefits = findViewById<TextInputLayout>(R.id.til_vab_benefits);

        var name = til_vab_name.editText?.text.toString();
        var description = til_vab_desc.editText?.text.toString();
        var benefits = til_vab_benefits.editText?.text.toString();
        var totalErrors : Int = 0;

        if(validator.isNull(name)) {
            til_vab_name.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            til_vab_name.error = "";
        }

        if(validator.isNull(description)) {
            til_vab_desc.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            til_vab_desc.error = "";
        }

        if(validator.isNull(benefits)) {
            til_vab_benefits.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            til_vab_benefits.error = "";
        }

        return totalErrors;
    }

}