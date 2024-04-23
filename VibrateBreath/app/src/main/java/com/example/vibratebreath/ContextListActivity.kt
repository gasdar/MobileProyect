package com.example.vibratebreath

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class ContextListActivity : AppCompatActivity() {

    // Variables globales
    private var userEmail : String = "";
    private val breathTypes : ArrayList<String> = Persistence().findBreathTypes();
    private var breaths : ArrayList<String> = Persistence().findBreaths();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_context_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // REFERENCE
        val btn_plus_breath = findViewById<Button>(R.id.btn_plus_breath)

        // TODO: Al cargar la data del spinner traer todas las respiraciones relacionadas con el tipo de respiración
        loadingData();

        btn_plus_breath.setOnClickListener {
            val intent = Intent(this@ContextListActivity, RegisterBreathActivity::class.java);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        }

        /* EVENTO onItemClickListerner de ListView
        val lv_vcl_data = findViewById<ListView>(R.id.lv_vcl_data);
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
        */

    }

    private fun loadingData() {

        // REFERENCES
        val tv_welcome = findViewById<TextView>(R.id.tv_welcome);
        val sp_vcl_cat = findViewById<Spinner>(R.id.sp_vcl_cat);
        val rv_vcl_data = findViewById<RecyclerView>(R.id.rv_vcl_data);

        // Establecemos mensaje de bienvenida, obteniendo valor desde el Intent
        this.userEmail = intent.getStringExtra("user_email").toString();
        tv_welcome.setText("Bienvenido: ${userEmail}");

        // Configurando y agregando adaptador a Spinner para los tipos de respiraciones
        val arrayAdapterSpinner : ArrayAdapter<*>;
        arrayAdapterSpinner = ArrayAdapter(this@ContextListActivity, android.R.layout.simple_dropdown_item_1line, breathTypes);
        sp_vcl_cat.adapter = arrayAdapterSpinner;

        // INFLANCIÓN DE DATOS DEL RECYCLER VIEW
        rv_vcl_data.layoutManager = LinearLayoutManager(this@ContextListActivity)
        val items = mutableListOf(
            Item("Respiración Diafragmática", "Respiración que se ve involucrado los musculos abdominales", R.drawable.meditacion1),
            Item("Respiración Controlada", "Respiración enfocada en la acción misma y de ser consciente", R.drawable.meditacion2),
            Item("Respiración Cuadrada", "Respiración equilibrada en segundos de exhalar e inhalar", R.drawable.meditacion3),
            Item("Respiración Localizada", "Respiración enfocada en alguna parte del cuerpo para sanar", R.drawable.meditacion4),
            Item("Respiración 4/7/8", "Respiración que se inhala 4 seg, se aguanta 7 seg y se exhala 8 seg", R.drawable.meditacion5),
            Item("Respiración Diafragmática", "Respiración que se ve involucrado los musculos abdominales", R.drawable.meditacion1),
            Item("Respiración Controlada", "Respiración enfocada en la acción misma y de ser consciente", R.drawable.meditacion2),
            Item("Respiración Cuadrada", "Respiración equilibrada en segundos de exhalar e inhalar", R.drawable.meditacion3),
            Item("Respiración Localizada", "Respiración enfocada en alguna parte del cuerpo para sanar", R.drawable.meditacion4),
            Item("Respiración 4/7/8", "Respiración que se inhala 4 seg, se aguanta 7 seg y se exhala 8 seg", R.drawable.meditacion5)
        )
        val adapterRV = CustomAdapter(items) { item ->
            Toast.makeText(this@ContextListActivity, "Nombre: ${item.title}", Toast.LENGTH_SHORT).show()
        }
        rv_vcl_data.adapter = adapterRV

        //IMPLEMENTAMOS GESTOS
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {return false}
            //IMPLEMENTAMOS DECORADOR CON LA LIBRERIA RecyclerViewSwipeDecorator
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@ContextListActivity, R.color.colorDelete))
                    .addSwipeLeftActionIcon(R.drawable.baseline_delete_sweep_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@ContextListActivity, R.color.colorArchive))
                    .addSwipeRightActionIcon(R.drawable.baseline_archive_24)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        adapterRV.removeItem(position)
                        Toast.makeText(this@ContextListActivity, "Elemento eliminado", Toast.LENGTH_SHORT).show()
                    }
                    ItemTouchHelper.RIGHT -> {
                        adapterRV.removeItem(position)
                        Toast.makeText(this@ContextListActivity, "Elemento archivado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rv_vcl_data)

        /*
        // Configurando y agregando adaptador a ListView para las respiraciones
        val lv_vcl_data = findViewById<ListView>(R.id.lv_vcl_data);
        val arrayAdapterListView : ArrayAdapter<*>;
        arrayAdapterListView = ArrayAdapter(this@ContextListActivity, android.R.layout.simple_list_item_1, breaths);
        lv_vcl_data.adapter = arrayAdapterListView;
        */
    }

}