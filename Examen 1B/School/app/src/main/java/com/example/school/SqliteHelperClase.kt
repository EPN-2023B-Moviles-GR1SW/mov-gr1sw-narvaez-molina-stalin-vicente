package com.example.school

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelperClase(contexto: Context?) : SQLiteOpenHelper(
    contexto,
    "school",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaClase =
            """
                CREATE TABLE Clase(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                descripcion TEXT
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaClase)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearNuevaClase(nombre: String, descripcion: String): Long {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues().apply {
            put("nombre", nombre)
            put("descripcion", descripcion)
        }
        val resultadoGuardar = basedatosEscritura.insert("Clase", null, valoresAGuardar)
        basedatosEscritura.close()
        return resultadoGuardar
    }

    fun eliminarClasePorId(idClase: Int): Int {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idClase.toString())
        val resultadoEliminacion =
            conexionEscritura.delete("Clase", "id=?", parametrosConsultaDelete)
        conexionEscritura.close()
        return resultadoEliminacion
    }

    fun obtenerClasePorId(idClase: Int): Clase? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM Clase WHERE id =?"
        val parametrosConsultaLectura = arrayOf(idClase.toString())
        val resultadoConsultaLectura =
            baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)
        var clase: Clase? = null
        if (resultadoConsultaLectura.moveToFirst()) {
            val nombre = resultadoConsultaLectura.getString(1)
            val descripcion = resultadoConsultaLectura.getString(2)
            clase = Clase(idClase, nombre, descripcion)
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return clase
    }


    fun actualizarClasePorId(idClase: Int, nuevoNombre: String, nuevaDescripcion: String): Int {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("nombre", nuevoNombre)
            put("descripcion", nuevaDescripcion)
        }
        val parametrosConsultaActualizar = arrayOf(idClase.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "Clase",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
        return resultadoActualizacion
    }

    fun obtenerUltimoIdClase(): Int {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT id FROM Clase ORDER BY id DESC LIMIT 1"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val ultimoId: Int
        if (resultadoConsultaLectura.moveToFirst()) {
            ultimoId = resultadoConsultaLectura.getInt(0)
        } else {
            ultimoId = 0
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return ultimoId
    }

}