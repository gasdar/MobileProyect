package com.example.vibratebreath

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContextListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_context_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_plus_breath = findViewById<Button>(R.id.btn_plus_breath);
        val lv_breath_registers = findViewById<ListView>(R.id.lv_breath_registers);
        
        btn_plus_breath.setOnClickListener {
            val intent = Intent(this@ContextListActivity, RegisterBreathActivity::class.java);
            startActivity(intent);
        }

        lv_breath_registers.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val intent = Intent(this@ContextListActivity, BreathDetailActivity::class.java);
                startActivity(intent);
            }

        }

    }

}