package com.example.vibratebreath

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class RegisterUserActivity : AppCompatActivity() {

    private val validator = Validator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_add_user = findViewById<Button>(R.id.btn_add_user)
        val til_vru_dob = findViewById<TextInputLayout>(R.id.til_vru_dob)

        // EVENTO DE SELECCIÓN DE FECHA, AL PRESIONAR SOBRE EL TIL DE LA FECHA
        til_vru_dob.editText?.setOnClickListener {
            showDatePickerDialog();
        }

        btn_add_user.setOnClickListener {
            if(validate()==0) {
                val intent = Intent(this@RegisterUserActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }   // FIN DE onCreate()

    private fun showDatePickerDialog() {
        // CREAMOS UNA INSTANCIA DEL CALENDARIO DEL S.O. ANDROID, O SEA, CON LA FECHA DEL CELULAR
        val cal : Calendar = Calendar.getInstance()

        // EJEMPLO FORMATO DE TIMEPICKER
        /*
        val listenerOfTime = TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
            val sHourOfDay = numberLessThanTen(hourOfDay);
            val sMinute = numberLessThanTen(minute);
            findViewById<TextInputLayout>(R.id.til_vru_dob).editText?.setText("${sHourOfDay}:${sMinute}");
        }
        TimePickerDialog(this, listenerOfTime, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();*/

        // IMPLEMENTAMOS UN FORMATO PARA UN EVENTO LISTERNER DE UNA FECHA
        val listenerOfDate = DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->

            val selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);

            val currentDate = Calendar.getInstance();
            val age = calculateAge(selectedDate);
            val monthFixed = month + 1;

            if(selectedDate.after(currentDate)) {
                Toast.makeText(this@RegisterUserActivity, "La fecha de nacimiento, no puede ser mayor que la actual", Toast.LENGTH_SHORT).show();
            } else if (age < 18) {
                Toast.makeText(this@RegisterUserActivity, "El usuario debe ser mayor de edad (tener al menos 18 años)", Toast.LENGTH_SHORT).show();
            } else {
                val sDay : String = numberLessThanTen(dayOfMonth);
                val sMonth : String = numberLessThanTen(monthFixed);
                findViewById<TextInputLayout>(R.id.til_vru_dob).editText?.setText("${sDay}/${sMonth}/${year}");
            }

        }
        DatePickerDialog(this@RegisterUserActivity, listenerOfDate, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private fun calculateAge(birthDate: Calendar): Int {
        val today = Calendar.getInstance();
        var age : Int = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    private fun numberLessThanTen(number: Int): String {
        var format : String = "${number}";
        if(number < 10) {
            format = "0${number}";
        }
        return format;
    }

    private fun validate() : Int {

        // TODO: Obtener valor del rol del usuario, para asignarselo
        //References
        val til_vru_name = findViewById<TextInputLayout>(R.id.til_vru_name)
        val til_vru_email = findViewById<TextInputLayout>(R.id.til_vru_email)
        val til_vru_dob = findViewById<TextInputLayout>(R.id.til_vru_dob)
        val til_vru_pass = findViewById<TextInputLayout>(R.id.til_vru_pass)
        val til_vru_rpass = findViewById<TextInputLayout>(R.id.til_vru_rpass)

        var name = til_vru_name.editText?.text.toString()
        var email = til_vru_email.editText?.text.toString()
        var dateOfBirth = til_vru_dob.editText?.text.toString()
        var pass = til_vru_pass.editText?.text.toString()
        var rPass = til_vru_rpass.editText?.text.toString()
        var totalErrors : Int = 0

        if(validator.isNullOrBlankLikeWhite(name)) {
            til_vru_name.error = getString(R.string.null_field)
            totalErrors += 1
        } else if(!validator.validateName(name)) {
            til_vru_name.error = getString(R.string.invalid_format_username)
            totalErrors += 1
        } else {
            til_vru_name.error = ""
        }

        if(validator.isNullOrBlankLikeWhite(email)) {
            til_vru_email.error = getString(R.string.null_field)
            totalErrors += 1
        } else if(!validator.validateEmail(email)) {
            til_vru_email.error = getString(R.string.invalid_format_email)
            totalErrors += 1
        } else {
            til_vru_email.error = ""
        }

        if(validator.isNullOrBlankLikeWhite(dateOfBirth)) {
            til_vru_dob.error = getString(R.string.null_field)
            totalErrors += 1
        } else {
            til_vru_dob.error = ""
        }

        if(validator.isNullOrBlankLikeWhite(pass)) {
            til_vru_pass.error = getString(R.string.null_field)
            totalErrors += 1
        } else {
            if(!validator.isEqualTo(pass, rPass)) {
                til_vru_pass.error = getString(R.string.passwords_not_match)
                til_vru_rpass.error = getString(R.string.passwords_not_match)
                totalErrors += 1
            } else {
                til_vru_pass.error = ""
                til_vru_rpass.error = ""
            }
        }

        return totalErrors
    }

    // Example for validate()
    // println("${name} - ${email} - ${dateOfBirth} - ${pass} - ${rPass}");

}