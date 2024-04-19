package com.example.vibratebreath

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContextListActivity : AppCompatActivity() {

    // Variables globales
    private var userEmail : String = "";
    private val breathTypes = Persistence().findBreathTypes();
    private var breaths = Persistence().findBreaths();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_context_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // REFERENCES
        val btn_plus_breath = findViewById<Button>(R.id.btn_plus_breath);
        val lv_vcl_data = findViewById<ListView>(R.id.lv_vcl_data);

        loadingData();

        btn_plus_breath.setOnClickListener {
            val intent = Intent(this@ContextListActivity, RegisterBreathActivity::class.java);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        }

        lv_vcl_data.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Identificando elementos de la colección
                println("VISTA(CONTEXTO DE APP): Pos ${position} | Id ${id}")
                val intent = Intent(this@ContextListActivity, BreathDetailActivity::class.java);
                intent.putExtra("id", id)
                intent.putExtra("name", breaths[position])
                intent.putExtra("user_email", userEmail)
                startActivity(intent);
            }
        }

    }

    private fun loadingData() {

        // REFERENCES
        val tv_welcome = findViewById<TextView>(R.id.tv_welcome);
        val sp_vcl_cat = findViewById<Spinner>(R.id.sp_vcl_cat);
        val lv_vcl_data = findViewById<ListView>(R.id.lv_vcl_data);

        // Establecemos mensaje de bienvenida, obteniendo valor desde el Intent
        this.userEmail = intent.getStringExtra("user_email").toString();
        tv_welcome.setText("Bienvenido: ${userEmail}");

        // Configurando y agregando adaptador a Spinner para los tipos de respiraciones
        val arrayAdapterSpinner : ArrayAdapter<*>;
        arrayAdapterSpinner = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, breathTypes);
        sp_vcl_cat.adapter = arrayAdapterSpinner;

        // Configurando y agregando adaptador a ListView para las respiraciones
        val arrayAdapterListView : ArrayAdapter<*>;
        arrayAdapterListView = ArrayAdapter(this, android.R.layout.simple_list_item_1, breaths);
        lv_vcl_data.adapter = arrayAdapterListView;
    }

}