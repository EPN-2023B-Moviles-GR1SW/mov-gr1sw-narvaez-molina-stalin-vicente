package com.example.b2023_gr1sw_svnm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar



class ACicloVida : AppCompatActivity() {
    var textoglobal = ""
    fun mostrarSnackbar(texto: String) {
        textoglobal = textoglobal + "" + texto
        Snackbar
            .make(
                findViewById(R.id.cl_ciclo_vida),
                textoglobal,
                Snackbar.LENGTH_INDEFINITE
            )
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackbar("onStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSnackbar("onResume")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackbar("onRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackbar("onPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSnackbar("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar("onDestroy")


    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            // GUARDAR LAS VARIABLES
            // PRIMITIVOS
            putString("textoGuardado", textoglobal)
            //putInt("numeroGuardado", numero)
        }
        super.onSaveInstanceState(outState)
    }


    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)

        //recuperar las variables
        val textoRecuperado: String?=savedInstanceState
            .getString("textoGuardado")
        if (textoRecuperado!= null){
            mostrarSnackbar(textoRecuperado)
            textoglobal = textoRecuperado
        }

    }


}