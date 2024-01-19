package com.example.school

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class Formulario_Estudiante : AppCompatActivity() {
    private var idEstudiante: Int = -1
    private var idClaseActual: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_estudiante)
        var estudianteId = BaseDeDatos.obtenerUltimoIdEstudiante()

        //recuperar clase
        val idClase: Int = intent.getIntExtra("idClase", -1)
        mostrarSnackbar("idClase : " + idClase)
        println("Id de la clase que se le paso por parametro: ${idClase}")
        this.idClaseActual = idClase
        //recuperar estudiante
        val idEstudianteRecuperado: Int = intent.getIntExtra("idEstudiante", -1)
        mostrarSnackbar("idEstudiante: $idEstudianteRecuperado")
        this.idEstudiante = idEstudianteRecuperado

        //obtenemos el valor de los componetes visuales
        val id = findViewById<EditText>(R.id.txt_id_estudiante)
        val nombre = findViewById<EditText>(R.id.txt_nombre_estudiante)
        val apellido = findViewById<EditText>(R.id.txt_apellido_estudiante)

        if (this.idEstudiante == -1) {
            println("No se le paso ningun id vamos a crear nuevo estudainte")
            println("Valor si es que NO se paso parametro de idEstudiante: ${this.idEstudiante}")
            id.setText("${estudianteId + 1}")
        } else {
            val estudianteAEditar = BaseDeDatos.obtenerEstudiantePorId(this.idEstudiante)
            println("Si se le paso id vamos a editar el Estudiante")
            println("Valor si es que SI se paso parametro de idEstudiante: ${this.idEstudiante}")
//            id.setText("${this.idClase}")
            if (estudianteAEditar != null) {
                // Puedes ajustar esto según la estructura de tu formulario
                id.setText(estudianteAEditar.id.toString())
                nombre.setText(estudianteAEditar.nombre)
                apellido.setText(estudianteAEditar.apellido)
            } else {
                // Manejar el caso en el que no se encuentre la clase con el ID proporcionado
                println("No se encontró la clase con el ID proporcionado")
            }

        }
        val btn_guardar_estudiante = findViewById<Button>(R.id.btn_guardar_estudiante)
        btn_guardar_estudiante
            .setOnClickListener {

                if (this.idEstudiante == -1) {
                    println("No se le paso ningun id vamos a crear nuevo Estudiante")
                    var idEstudiante = id.text.toString().toInt()
                    var nombreEstudiante = nombre.text.toString()
                    var apellidoEstudiante = apellido.text.toString()

                    BaseDeDatos.crearNuevoEstudiante(
                        idEstudiante,
                        nombreEstudiante,
                        apellidoEstudiante,
                        this.idClaseActual
                    )
                    id.setText("")
                    irActividad(lista_clases::class.java)
                } else {
                    println("Valor si esq se paso parametro de idEstudiante: ${this.idEstudiante}")
                    println("Aqui logica si esq se paso el id del estudiante ")
                    var idEstudiante = id.text.toString().toInt()
                    var nuevoNombreEstudiante = nombre.text.toString()
                    var nuevoApellidoEstudiante = apellido.text.toString()
                    BaseDeDatos.editarEstudiantePorId(
                        idEstudiante,
                        nuevoNombreEstudiante,
                        nuevoApellidoEstudiante,
                        this.idClaseActual
                    )
                    irActividad(lista_clases::class.java)
                }

            }
    }

    fun irActividadConParametroIdClase(clase: Class<*>, idClase: Int) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("idClase", idClase)
        startActivity(intentExplicito)
    }

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
                findViewById(R.id.id_layout_Form_Estudiante), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show() //muestra el snackbar
    }
}