package com.example.school

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class Formulario_Clase : AppCompatActivity() {
    private var idClase: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_clase)
        var classId = BaseDeDatos.obtenerUltimoIdClase()

        val extras = intent.extras
        if (extras != null && extras.containsKey("idClase")) {
            this.idClase = extras.getInt("idClase")
        }

        //obtenemos el valor de los componetes visuales
        val id = findViewById<EditText>(R.id.txt_Id_Clase)
        val nombre = findViewById<EditText>(R.id.txt_Nombre_clase)
        val descripcion = findViewById<EditText>(R.id.txt_descripcion_clase)


        if (this.idClase == -1){
            println("No se le paso ningun id vamos a crer nueva clase")
            println("Valor si es que NO se paso parametro de idClase: ${this.idClase}")
            id.setText("${classId + 1}")
        }else{
            val claseAEditar = BaseDeDatos.obtenerClasePorId(this.idClase)
            println("Si se le paso id vamos a editar nueva clase")
            println("Valor si es que SI se paso parametro de idClase: ${this.idClase}")
//            id.setText("${this.idClase}")
            if (claseAEditar != null) {
                // Puedes ajustar esto según la estructura de tu formulario
                id.setText(claseAEditar.idClass.toString())
                nombre.setText(claseAEditar.nombre)
                descripcion.setText(claseAEditar.descripcion)
            } else {
                // Manejar el caso en el que no se encuentre la clase con el ID proporcionado
                println("No se encontró la clase con el ID proporcionado")
            }

        }
        //boton
        val botonGuardarClase = findViewById<Button>(R.id.btn_guardar_form_clase)
        botonGuardarClase
            .setOnClickListener {

                if (this.idClase == -1){
                    println("No se le paso ningun id vamos a crer nueva clase")
                    var idClase = id.text.toString().toInt()
                    var nombreClase = nombre.text.toString()
                    var descripcionClase = descripcion.text.toString()

                    BaseDeDatos.crearNuevaClase(idClase, nombreClase, descripcionClase)
                    id.setText("")
                    irActividad(lista_clases::class.java)
                }else{
                    println("Valor si esq se paso parametro de idClase: ${this.idClase}")
                    println("Aqui logica si esq se paso el id de la clase a editar ")
                    var idClase = id.text.toString().toInt()
                    var nuevoNombreClase = nombre.text.toString()
                    var nuevaDescripcionClase = descripcion.text.toString()
                    BaseDeDatos.actualizarClasePorId(idClase, nuevoNombreClase, nuevaDescripcionClase)
                    irActividad(lista_clases::class.java)
                }


            }
    }
    fun editarClase(){

    }

    //Ir actividad
    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    //Ir actividad con parametros
    fun irActividadConParametros(clase: Class<*>, posicionItem: Int) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("posicionItem", posicionItem)
        startActivity(intentExplicito)
    }

    //    Mensajes en snackbar
    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.id_layout_Form_Clase), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show() //muestra el snackbar
    }
}