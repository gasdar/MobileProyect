package com.example.vibratebreath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // WIDGETS REFERENCES
        val btn_login = findViewById<Button>(R.id.btn_login);
        val btn_register_user = findViewById<Button>(R.id.btn_register_user);
        val btn_we_are = findViewById<Button>(R.id.btn_we_are);

        btn_login.setOnClickListener {
            // Toast.makeText(this, "Hola a todos!!", Toast.LENGTH_SHORT).show();
            val intent = Intent(this@MainActivity, ContextListActivity::class.java);
            startActivity(intent);
        }

        btn_register_user.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterUserActivity::class.java);
            startActivity(intent);
        }

        btn_we_are.setOnClickListener {
            val intent = Intent(this@MainActivity, WeAreActivity::class.java);
            startActivity(intent);
        }

    }

}