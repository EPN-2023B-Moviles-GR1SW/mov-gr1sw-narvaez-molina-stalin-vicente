package com.example.multicines

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

