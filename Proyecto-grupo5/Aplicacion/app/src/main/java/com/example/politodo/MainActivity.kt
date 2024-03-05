package com.example.politodo


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var auth: FirebaseAuth
    private lateinit var btnAddCategory: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAddCategory = findViewById(R.id.btn_add_category)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_nav_search -> {

                  true
                }

                R.id.menu_nav_home-> {
                    Toast.makeText(
                        baseContext,
                        "Home",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }

                R.id.menu_nav_settings-> {
                    irActividad(Settings::class.java)
                    true
                }
                else -> false
            }
        }

        btnAddCategory
            .setOnClickListener{
                irActividad(CrearCategoria::class.java)
            }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            irActividad(Login::class.java)
        }
    }

    fun abrirLogin() {
        irActividad(Login::class.java)
    }

    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
