package com.example.vibratebreath

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class BreathDetailActivity : AppCompatActivity() {

    private val validator = Validator()
    private val breathTypes = Persistence().findBreathTypes();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_breath_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cargar Datos
        loadingData();

        // REFERENCES
        val fab_edit_breath = findViewById<FloatingActionButton>(R.id.fat_edit_breath)
        val fab_delete_breath = findViewById<FloatingActionButton>(R.id.fat_delete_breath)

        fab_edit_breath.setOnClickListener {
            if(validate()==0) {
                adjustRegisters(1);
            }
        }

        fab_delete_breath.setOnClickListener {
            if(validate()==0) {
                adjustRegisters(2);
            }
        }

    }

    private fun loadingData() {
        // REFERENCES
        val til_vab_name = findViewById<TextInputLayout>(R.id.til_vab_name);
        val sp_vab_cat = findViewById<Spinner>(R.id.sp_vab_cat);

        val breathId = intent.getLongExtra("id", -1);
        println("VISTA(AJUSTE DE RESPIRACIÓN): El id del elemento en el arreglo es: ${breathId}")
        val breathName = intent.getStringExtra("name").toString();
        til_vab_name.editText?.setText(breathName);

        // Configurando y agregando adaptador a Spinner para los tipos de respiraciones
        val arrayAdapterSpinner : ArrayAdapter<*>;
        arrayAdapterSpinner = ArrayAdapter(this@BreathDetailActivity, android.R.layout.simple_dropdown_item_1line, breathTypes);
        sp_vab_cat.adapter = arrayAdapterSpinner;
    }

    fun validate() : Int {

        // TODO: Obtener valor de spinner para actualizar un tipo de respiración a la respiración
        //References
        val til_vab_name = findViewById<TextInputLayout>(R.id.til_vab_name)
        val til_vab_desc = findViewById<TextInputLayout>(R.id.til_vab_desc)
        val til_vab_benefits = findViewById<TextInputLayout>(R.id.til_vab_benefits)

        var name = til_vab_name.editText?.text.toString()
        var description = til_vab_desc.editText?.text.toString()
        var benefits = til_vab_benefits.editText?.text.toString()
        var totalErrors : Int = 0

        if(validator.isNullOrBlankLikeWhite(name)) {
            til_vab_name.error = getString(R.string.null_field)
            totalErrors += 1
        } else {
            til_vab_name.error = ""
        }

        if(validator.isNullOrBlankLikeWhite(description)) {
            til_vab_desc.error = getString(R.string.null_field)
            totalErrors += 1
        } else {
            til_vab_desc.error = ""
        }

        if(validator.isNullOrBlankLikeWhite(benefits)) {
            til_vab_benefits.error = getString(R.string.null_field)
            totalErrors += 1
        } else {
            til_vab_benefits.error = ""
        }

        return totalErrors
    }

    // Si están los datos correctos, se muestra una pantalla de verificación, ya sea, para eliminar o modificar
    private fun adjustRegisters(option : Int) {
        val builder = AlertDialog.Builder(this@BreathDetailActivity);
        var title : String = "";
        var message : String = "";
        var confirmation : String = "";

        when (option) {
            1 -> {
                title = getString(R.string.bdr_edition_title);
                message = getString(R.string.bdr_edition_ms);
                confirmation = getString(R.string.bdr_edit_confirmation);
            }
            2 -> {
                title = getString(R.string.bdr_elimination_title);
                message = getString(R.string.bdr_elimination_ms);
                confirmation = getString(R.string.bdr_delete_confirmation);
            }
        }

        if(!validator.isNullOrBlankLikeWhite(confirmation)) {
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                Toast.makeText(this@BreathDetailActivity, confirmation, Toast.LENGTH_SHORT).show()
                val userEmail = intent.getStringExtra("user_email").toString();
                val intent = Intent(this@BreathDetailActivity, ContextListActivity::class.java)
                intent.putExtra("user_email", userEmail)
                startActivity(intent)
            }
            builder.setNegativeButton(getString(R.string.bdr_negative_bottom), null)
            builder.setNeutralButton(getString(R.string.bdr_neutral_bottom), null)
            builder.show()
        } else {
            Toast.makeText(this@BreathDetailActivity, "Opción Incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

}