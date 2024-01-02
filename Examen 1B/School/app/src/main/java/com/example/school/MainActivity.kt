package com.example.school

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_listview_lista_estudiantes = findViewById<Button>(R.id.btn_lv_estudiantes)

        btn_listview_lista_estudiantes
            .setOnClickListener {
                mostrarSnackbar("Aplasto en el boton de ver lista estudientes")
                irActividad(lista_estudientes::class.java)
            }

        val btn_listview_lista_clases = findViewById<Button>(R.id.btn_lv_clases)
        btn_listview_lista_clases
            .setOnClickListener{
                mostrarSnackbar("Aplasto en el boton de ver lista de clases")
                irActividad(lista_clases::class.java)
            }
    }

    //mostrar mensajes
    fun mostrarSnackbar(texto: String) {
        Snackbar.make(
            findViewById(R.id.id_layout_activity_main),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}

