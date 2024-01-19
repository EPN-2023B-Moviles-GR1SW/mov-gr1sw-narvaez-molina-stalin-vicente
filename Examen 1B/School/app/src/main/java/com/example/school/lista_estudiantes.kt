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

class lista_estudiantes : AppCompatActivity() {
    val arregloEstudiante = BaseDeDatos.arregloEstudiantes
    val arregloClases = BaseDeDatos.arregloClases
    var posicionItemSeleccionado = -1
    var estudianteSeleccionado = -1

    private lateinit var adaptador: ArrayAdapter<Estudiante>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Recuperar la posición del ítem del intent
        setContentView(R.layout.activity_lista_estudiantes)
        //Referenia al boton añadir Estudiante
        val btn_anadir_estudiante = findViewById<FloatingActionButton>(R.id.btn_anadir_estudiante)

        val posicionItem: Int = intent.getIntExtra("posicionItem", -1)
        mostrarSnackbar("position item : " + posicionItem)
        this.posicionItemSeleccionado = posicionItem


        val listView = findViewById<ListView>(R.id.lv_lista_estudiantes)

        adaptador = ArrayAdapter(
            this,//contexto
            android.R.layout.simple_list_item_1, //como se va a ver XML
            if (posicionItem != -1) {
                // Filtrar estudiantes por idClase si posicionItem no es -1
                val claseSeleccionada = arregloClases[posicionItem]
                val idClase = claseSeleccionada.idClass
                btn_anadir_estudiante.isEnabled = true
                btn_anadir_estudiante.visibility =
                    if (btn_anadir_estudiante.isEnabled) View.VISIBLE else View.GONE
                BaseDeDatos.arregloEstudiantes.filter { it.idClase == idClase }
            } else {
                // Mostrar todos los estudiantes si posicionItem es -1
                btn_anadir_estudiante.isEnabled = false
                btn_anadir_estudiante.visibility =
                    if (btn_anadir_estudiante.isEnabled) View.VISIBLE else View.GONE
                arregloEstudiante
            }
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
        //buttons action
        btn_anadir_estudiante
            .setOnClickListener {
                mostrarSnackbar("Dirigiendo al formulario estudiante")
                val claseSeleccionada = arregloClases[this.posicionItemSeleccionado]
                val idClase = claseSeleccionada.idClass
                irActividadConParametroIdClase(Formulario_Estudiante::class.java, idClase)
            }
        val btn_home_lista_estudaintes = findViewById<Button>(R.id.btn_home_list_estudiantes)
        btn_home_lista_estudaintes
            .setOnClickListener {
                irActividad(MainActivity::class.java)
            }
        val btn_ver_lista_clases = findViewById<Button>(R.id.btn_ver_lista_clases)
        btn_ver_lista_clases
            .setOnClickListener {
                irActividad(lista_clases::class.java)
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
        inflater.inflate(R.menu.menu_estudiante, menu)

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
                val estudianteSeleccionado = adaptador.getItem(posicionItemSeleccionado)

                //ahora podemos acceder a los datos de ese estudiante
                val idEstudiante = estudianteSeleccionado?.id
                val nombreEstudiante = estudianteSeleccionado?.nombre
                val apellidoEstudiante = estudianteSeleccionado?.apellido
                val idClase = estudianteSeleccionado?.idClase
                if (idEstudiante != null) {
                    this.estudianteSeleccionado = idEstudiante
                    // Puedes imprimir o usar estos datos según tus necesidades
                    println("ID del estudiante seleccionado: $idEstudiante")
                    println("Nombre del estudiante seleccionado: $nombreEstudiante")
                    println("Apellido del estudiante seleccionado: $apellidoEstudiante")
                    println("Id de la clase que toma: $idClase")

                    if (idClase != null) {
                        irActividadConParametroIdEstudiante(
                            Formulario_Estudiante::class.java,
                            this.estudianteSeleccionado,
                            idClase
                        )
                    }
                    mostrarSnackbar("${this.estudianteSeleccionado}")
                    return true // Indica que se ha manejado la acción correctamente
                } else {
                    // Manejar el caso en el que el estudiante seleccionado sea nulo
                    return false
                }
            }

            R.id.mi_eliminar -> {
                val estudianteSeleccionado = arregloEstudiante[posicionItemSeleccionado]
                val idEstudiante = estudianteSeleccionado.id
                this.estudianteSeleccionado = idEstudiante
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
            BaseDeDatos.eliminarEstudiantePorId(adaptador, estudianteSeleccionado)
        }

        builder.setNegativeButton(
            "Cancelar"
        ) { dialog, which ->
            this.estudianteSeleccionado = -1
        }

        val dialogo = builder.create()
        dialogo.show()
    }

    //ir actividad
    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    //Ir actividad con parametros
    fun irActividadConParametroIdEstudiante(estudiante: Class<*>, idEstudiante: Int, idClase: Int) {
        val intentExplicito = Intent(this, estudiante)
        intentExplicito.putExtra("idEstudiante", idEstudiante)
        intentExplicito.putExtra("idClase", idClase)
        startActivity(intentExplicito)
    }

    fun irActividadConParametroIdClase(clase: Class<*>, idClase: Int) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("idClase", idClase)
        startActivity(intentExplicito)
    }

    //Mensajes en snackbar
    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.activity_view_lista_estudiantes), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show() //muestra el snackbar
    }
}