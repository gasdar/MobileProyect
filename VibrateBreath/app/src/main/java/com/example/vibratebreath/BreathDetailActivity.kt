package com.example.vibratebreath

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BreathDetailActivity : AppCompatActivity() {

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
            val intent = Intent(this@BreathDetailActivity, ContextListActivity::class.java);
            startActivity(intent);
        }

        fab_delete_breath.setOnClickListener {
            val intent = Intent(this@BreathDetailActivity, ContextListActivity::class.java);
            startActivity(intent);
        }

    }

}