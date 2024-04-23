package com.example.vibratebreath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WeAreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_we_are)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // WIDGETS REFERENCES
        val btn_return_login = findViewById<Button>(R.id.btn_return_login);
        val fab_vwa_location = findViewById<FloatingActionButton>(R.id.fab_vwa_location);

        btn_return_login.setOnClickListener {
            val intent = Intent(this@WeAreActivity, MainActivity::class.java);
            startActivity(intent);
        }

        fab_vwa_location.setOnClickListener {
            startActivity(Intent(this@WeAreActivity, MapsActivity::class.java))
        }

    }

}