package com.example.school

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class lista_estudientes : AppCompatActivity() {
    val arreglo = BaseDeDatos.arregloEstudiantes
    var posicionItemSeleccionado = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_estudientes)

        val listView = findViewById<ListView>(R.id.lv_lista_estudiantes)

        val adaptador = ArrayAdapter(
            this,//contexto
            android.R.layout.simple_list_item_1, //como se va a ver XML
            arreglo
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
        //buttons
        val btn_anadir_estudiante = findViewById<FloatingActionButton>(R.id.btn_anadir_estudiante)
        btn_anadir_estudiante
            .setOnClickListener{
                mostrarSnackbar("dirigiendo al formulario")
                irActividad(Formulario_Estudiante::class.java)
            }


    }

    //mostrar menu emergente en un elemnto de la lista
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        // Llama al método onCreateContextMenu de la clase base (superclase)
        super.onCreateContextMenu(menu, v, menuInfo)

        // Infla (crea) el menú desde un archivo XML de recursos (R.menu.menu) y lo muestra en el contexto
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        // Obtiene información sobre el elemento de lista que se ha mantenido presionado para mostrar el menú
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position

        // Guarda la posición del elemento de lista seleccionado para su uso posterior
        posicionItemSeleccionado = posicion
    }

    //Acciones al seleccional un item del menu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                // Acción cuando se selecciona "Editar" en el menú contextual
                // Muestra un Snackbar con la posición del elemento seleccionado
                mostrarSnackbar("${posicionItemSeleccionado}")
                return true // Indica que se ha manejado la acción correctamente
            }

            R.id.mi_eliminar -> {
                // Acción cuando se selecciona "Eliminar" en el menú contextual
                // Muestra un Snackbar con la posición del elemento seleccionado
                mostrarSnackbar("${posicionItemSeleccionado}")
                // Abre un diálogo (supuestamente) para confirmar la eliminación del elemento
                abrirDialogo()
                return true // Indica que se ha manejado la acción correctamente
            }

            else -> super.onContextItemSelected(item)
            // Si no se selecciona ninguna opción conocida, delega al comportamiento predeterminado
        }
    }

    //abrir dialogo de confirmaicon
    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")

        builder.setPositiveButton(
            "Aceptar"
        ) { dialog, which ->
            mostrarSnackbar("Eliminar aceptado")
            // Aquí deberías agregar la lógica para eliminar el elemento
            // Por ejemplo: eliminarElemento(posicionItemSeleccionado)
        }

        builder.setNegativeButton(
            "Cancelar"
        ) { dialog, which ->
            // Aquí puedes manejar alguna acción si se cancela la eliminación
        }

        val dialogo = builder.create()
        dialogo.show()
    }
    //dirigir a nueva actividad
    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    //Mensajes en snackbar
    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.lv_lista_estudiantes), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show() //muestra el snackbar
    }
}