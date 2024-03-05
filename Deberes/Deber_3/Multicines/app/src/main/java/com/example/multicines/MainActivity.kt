package com.example.multicines

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import android.view.ViewGroup

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnNavInicio -> {
                    replaceFragment(inicioFragmentView())
                    true
                }

                R.id.btnNavCartelera -> {
                    replaceFragment(carteleraFragmentView())
                    true
                }

                R.id.btnNavDulceria -> {
                    replaceFragment(dulceriaFragmentView())
                    true
                }

//                R.id.btnNavMulticlub -> {
//                    replaceFragment(multiclubFragmentView())
//                    true
//                }

                R.id.btnNavPerfil -> {
                    replaceFragment(perfilFragmentView())
                    true
                }

                else -> false
            }
        }
        replaceFragment(inicioFragmentView())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit()

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

