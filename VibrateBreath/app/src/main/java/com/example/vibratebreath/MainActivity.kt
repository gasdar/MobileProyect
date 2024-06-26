package com.example.vibratebreath

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.vibratebreath.room.Db
import com.example.vibratebreath.room.entities.User
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val validator = Validator();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val room = Room.databaseBuilder(this@MainActivity, Db::class.java,"database-ciisa").allowMainThreadQueries().build()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // WIDGETS REFERENCES
        val btn_login = findViewById<Button>(R.id.btn_login);
        val btn_register_user = findViewById<Button>(R.id.btn_register_user);
        val btn_we_are = findViewById<Button>(R.id.btn_we_are);
        val sw_reminder = findViewById<Switch>(R.id.sw_reminder);
        val til_email = findViewById<TextInputLayout>(R.id.til_email);
        val til_pass = findViewById<TextInputLayout>(R.id.til_pass);

        // Obtención de SHARED PREFERENCES
        val preferences = getSharedPreferences("login_data", Context.MODE_PRIVATE);

        // CARGAR DATOS EN CASO DE HABERSE LOGEADO ANTES
        if(intent.getBooleanExtra("u_validate", false)) {
            til_email.editText?.setText(intent.getStringExtra("u_email"))
            til_pass.editText?.setText(intent.getStringExtra("u_pass"))
        } else {
            til_email.editText?.setText(preferences.getString("user_email", ""))
            til_pass.editText?.setText(preferences.getString("user_pass", ""))
        }

        // Botón de Login
        btn_login.setOnClickListener {
            if(validateFields(preferences)==0) {
                // Todos los datos se encuentran validados y se verifica si se deben guardar
                keepCredentials(sw_reminder.isChecked, til_email, til_pass, preferences)
                val email = til_email.editText?.text.toString()
                val pass = til_pass.editText?.text.toString()

                lifecycleScope.launch {
                    val user = room.daoUser().login(email, pass)
                    println(user != null)
                    if(user != null) {
                        val intent = Intent(this@MainActivity, ContextListActivity::class.java)
                        intent.putExtra("user_email", email)
                        startActivity(intent)
                    } else {
                        til_email.error = "Usuario o contraseña incorrecto!!"
                        til_pass.error = "Usuario o contraseña incorrecto!!"
                    }
                }
            }
        }

        // btn register user
        btn_register_user.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterUserActivity::class.java);
            startActivity(intent);
        }

        // btn context
        btn_we_are.setOnClickListener {
            val intent = Intent(this@MainActivity, WeAreActivity::class.java);
            startActivity(intent);
        }

    }

    private fun keepCredentials(keepCredentials: Boolean, tilEmail: TextInputLayout?, tilPass: TextInputLayout?, preferences: SharedPreferences?) {
        val editor = preferences?.edit();
        val email : String = tilEmail?.editText?.text.toString();
        val pass : String = tilPass?.editText?.text.toString();

        /* MANERA PARA GUARDAR RECORDAR CONTRASEÑA, PERO, QUE NECESITA EL NOMBRE DE USUARIO
        if(keepCredentials) {
            editor?.putString(email, email);
            editor?.putString("${email}_password", pass);
        } else {
            editor?.putString(email, "");
            editor?.putString("${email}_password", "");
        }
        */
        if(keepCredentials) {
            editor?.putString("user_email", email);
            editor?.putString("user_pass", pass);
        } else {
            editor?.putString("user_email", "");
            editor?.putString("user_pass", "");
        }

        editor?.commit();
    }

    fun validateFields(preferences: SharedPreferences?) : Int {

        // References
        val til_email = findViewById<TextInputLayout>(R.id.til_email);
        val til_pass = findViewById<TextInputLayout>(R.id.til_pass);

        var email = til_email.editText?.text.toString();
        var pass = til_pass.editText?.text.toString();
        var totalErrors : Int = 0;

        if (validator.isNullOrBlankLikeWhite(email)) {
            til_email.error = getString(R.string.null_field);
            totalErrors += 1;
        } else if(!validator.validateEmail(email)) {
            til_email.error = getString(R.string.invalid_format_email);
            totalErrors += 1;
        } else {
            /* MANERA PARA GUARDAR RECORDAR CONTRASEÑA, PERO, QUE NECESITA EL NOMBRE DE USUARIO
            // En caso de estar correcto el email y que la contraseña este vacía, se busca la data que recuerda a los usuarios
            if(validator.isNullOrBlankLikeWhite(pass)) {
                val loginDataMail : String = preferences?.getString(email, "").toString();
                if(!validator.isNullOrBlankLikeWhite(loginDataMail)) {
                    val loginDataPassword = preferences?.getString("${email}_password", "");
                    til_pass.editText?.setText(loginDataPassword);
                    pass = til_pass.editText?.text.toString();
                }
            }
            */
            til_email.error = "";
        }

        if (validator.isNullOrBlankLikeWhite(pass)) {
           til_pass.error = getString(R.string.null_field);
            totalErrors += 1;
        } else {
            til_pass.error = "";
        }

        return totalErrors;
    }


    // ESTADOS DE CICLOS DE VIDA DE LA ACTIVIDAD

    override fun onStart() {
        super.onStart()
        println("onStart()")
        // Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        println("onResume()")
        // Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart()")
        // Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        println("onPause()")
        // Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        println("onStop()")
        // Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy()")
        // Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }

    // Examples

    // validateFields()
    // Toast.makeText(this, "${email} - ${pass}", Toast.LENGTH_SHORT).show();
    // println("${email} - ${pass}");
    // println("El email es correcto: " + validator.validateEmail(email));
    // println("La password es nula: " + validator.isNull(pass));

}