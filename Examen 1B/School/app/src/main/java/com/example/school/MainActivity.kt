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

        val btnListViewEstudiantes = findViewById<Button>(R.id.btn_lv_estudiantes)
        btnListViewEstudiantes.setOnClickListener {
            // mostrarSnackbar("Redirigiendo a lista de Estudiantes")
            irActividad(lista_estudiantes::class.java)
        }


        val btnListViewClases = findViewById<Button>(R.id.btn_lv_clases)
        btnListViewClases.setOnClickListener {
//            mostrarSnackbar("Redirigiendo a lista de Clases")
            irActividad(lista_clases::class.java)
        }
    }

    //mostrar mensajes
    fun mostrarSnackbar(texto: String) {
        Snackbar.make(
            findViewById(R.id.activity_view_main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
            .show()
    }

    //ir a la siguiente actividad
    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}

