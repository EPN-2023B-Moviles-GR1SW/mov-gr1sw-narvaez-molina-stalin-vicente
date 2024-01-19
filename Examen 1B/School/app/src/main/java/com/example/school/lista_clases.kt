package com.example.school

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class lista_clases : AppCompatActivity() {
    //Declaración de Variables a usar 
    val arreglo = BaseDeDatos.arregloClases
    var posicionItemSeleccionado = -1
    var claseSeleccionada = -1

    private lateinit var adaptador: ArrayAdapter<Clase>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clases)
        val listView = findViewById<ListView>(R.id.lv_lista_clases)

        adaptador = ArrayAdapter(
            this,//contexto
            android.R.layout.simple_list_item_1, //como se va a ver XML
            arreglo
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        //buttons
        val btn_anadir_clase = findViewById<FloatingActionButton>(R.id.btn_anadir_clase)
        btn_anadir_clase
            .setOnClickListener {
                mostrarSnackbar("Dirigiendo al formulario clase")
                irActividad(Formulario_Clase::class.java)
            }

        val btn_home_lista_clases = findViewById<Button>(R.id.btn_home_list_clases)
        btn_home_lista_clases
            .setOnClickListener{
                irActividad(MainActivity::class.java)
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
        inflater.inflate(R.menu.menu_clase, menu)

        // Obtiene información sobre el elemento de lista que se ha mantenido presionado para mostrar el menú
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position

        // Guarda la posición del elemento de lista seleccionado para su uso posterior
        posicionItemSeleccionado = posicion
    }

    //Acciones al seleccional un item del menu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_ver_estudiantes -> {

                mostrarSnackbar("${posicionItemSeleccionado}")
                irActividadConParametrosPosicionItem(
                    lista_estudiantes::class.java,
                    posicionItemSeleccionado
                )
                this.posicionItemSeleccionado = -1
                return true // Indica que se ha manejado la acción correctamente
            }

            R.id.mi_editar -> {
                val claseSeleccionada = arreglo[posicionItemSeleccionado]

                // Ahora puedes acceder a los datos de la clase seleccionada
                val idClase = claseSeleccionada.idClass
                val nombreClase = claseSeleccionada.nombre
                val descripcionClase = claseSeleccionada.descripcion
                this.claseSeleccionada = idClase

                // Puedes imprimir o usar estos datos según tus necesidades
                println("ID de la clase seleccionada: $idClase")
                println("Nombre de la clase seleccionada: $nombreClase")
                println("Descripción de la clase seleccionada: $descripcionClase")



                irActividadConParametros(Formulario_Clase::class.java, this.claseSeleccionada)
                mostrarSnackbar("${this.claseSeleccionada}")
                return true // Indica que se ha manejado la acción correctamente
            }

            R.id.mi_eliminar -> {
                val claseSeleccionada = arreglo[posicionItemSeleccionado]
                val idClase = claseSeleccionada.idClass
                this.claseSeleccionada = idClase
                mostrarSnackbar("${posicionItemSeleccionado}")
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
            BaseDeDatos.eliminarClasePorId(adaptador, claseSeleccionada)

        }

        builder.setNegativeButton(
            "Cancelar"
        ) { dialog, which ->
            this.claseSeleccionada = -1
        }

        val dialogo = builder.create()
        dialogo.show()
    }

    //Ir actividad
    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    //Ir actividad con parametros
    fun irActividadConParametros(clase: Class<*>, idClass: Int) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("idClase", idClass)
        startActivity(intentExplicito)
    }

    fun irActividadConParametrosPosicionItem(clase: Class<*>, posicionItem: Int) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("posicionItem", posicionItem)
        startActivity(intentExplicito)
    }


    //    Mensajes en snackbar
    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.activity_view_lista_clases), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show() //muestra el snackbar
    }


}