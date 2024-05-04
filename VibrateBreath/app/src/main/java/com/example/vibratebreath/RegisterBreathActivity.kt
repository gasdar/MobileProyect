package com.example.vibratebreath

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import android.Manifest
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.vibratebreath.room.Db
import com.example.vibratebreath.room.entities.Breath
import kotlinx.coroutines.launch

class RegisterBreathActivity : AppCompatActivity() {

    private val validator = Validator();
    private val breathTypes = Persistence().findBreathTypes();

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        private val CAMERA_PERMISSION_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_breath)
        val room = Room.databaseBuilder(this@RegisterBreathActivity, Db::class.java,"database-ciisa").allowMainThreadQueries().build()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cargando el spinner
        loadingBreathTypes();

        // Obtención de WIDGETS de la vista
        val btn_add_breath = findViewById<Button>(R.id.btn_add_breath);
        val fab_vrb_camera = findViewById<FloatingActionButton>(R.id.fab_vrb_camera);
        val til_vrb_name = findViewById<TextInputLayout>(R.id.til_vrb_name);
        val til_vrb_desc = findViewById<TextInputLayout>(R.id.til_vrb_desc);
        val til_vrb_benefits = findViewById<TextInputLayout>(R.id.til_vrb_benefits);

        // Obtención de nombre de usuario
        val userEmail = intent.getStringExtra("user_email").toString();

        btn_add_breath.setOnClickListener {
            if(validate()==0) {
                val name = til_vrb_name.editText?.text.toString()
                val desc = til_vrb_desc.editText?.text.toString()
                val benefits = til_vrb_benefits.editText?.text.toString()
                val breath : Breath = Breath(name, desc, benefits, "", userEmail)
                lifecycleScope.launch {
                    val id = room.daoBreath().save(breath)
                    if(id > 0) {
                        Toast.makeText(this@RegisterBreathActivity, "Respiración registrada!!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterBreathActivity, ContextListActivity::class.java);
                        intent.putExtra("user_email", userEmail);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this@RegisterBreathActivity, "Error al registrar respiración!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        fab_vrb_camera.setOnClickListener {
            checkCameraPermission()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso otorgado, inicia la cámara
                    dispatchTakePictureIntent()
                } else {
                    // Permiso denegado, muestra un mensaje o maneja según tus necesidades
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val imv_vrb_photo = findViewById<ImageView>(R.id.imv_vrb_photo)
            imv_vrb_photo.setImageBitmap(imageBitmap)
        }
    }

    // METODO QUE VALIDA EL PERMISO DE LA CAMARA EN CASO DE TENER PERMISO EJECUTARA EL INTENT DE LA FOTO
    fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permiso
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            // Si ya tienes el permiso, inicia la cámara
            dispatchTakePictureIntent()
        }
    }
    // METODO QUE GATILLARA LA CAPTURA DE LA IMAGEN
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun loadingBreathTypes() {
        //REFERENCE
        val sp_vrb_cat = findViewById<Spinner>(R.id.sp_vrb_cat);

        // Configurando y agregando adaptador a Spinner para los tipos de respiraciones
        val arrayAdapterSpinner : ArrayAdapter<*>;
        arrayAdapterSpinner = ArrayAdapter(this@RegisterBreathActivity, android.R.layout.simple_dropdown_item_1line, breathTypes);
        sp_vrb_cat.adapter = arrayAdapterSpinner;
    }

    fun validate() : Int {

        // TODO: Obtener valor de spinner para asignar un tipo de respiración a la respiración
        // TODO: Posible Spinner intercambiarlo por un Checkbox, para tener más de un tipo de respiración
        // References
        val til_vrb_name = findViewById<TextInputLayout>(R.id.til_vrb_name);
        val til_vrb_desc = findViewById<TextInputLayout>(R.id.til_vrb_desc);
        val til_vrb_benefits = findViewById<TextInputLayout>(R.id.til_vrb_benefits);

        var name = til_vrb_name.editText?.text.toString();
        var description = til_vrb_desc.editText?.text.toString();
        var benefits = til_vrb_benefits.editText?.text.toString();
        var totalErrors : Int = 0;

        if(validator.isNullOrBlankLikeWhite(name)) {
            til_vrb_name.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            til_vrb_name.error = "";
        }

        if(validator.isNullOrBlankLikeWhite(description)) {
            til_vrb_desc.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            til_vrb_desc.error = "";
        }

        if(validator.isNullOrBlankLikeWhite(benefits)) {
            til_vrb_benefits.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            til_vrb_benefits.error = "";
        }

        return totalErrors;
    }

}
