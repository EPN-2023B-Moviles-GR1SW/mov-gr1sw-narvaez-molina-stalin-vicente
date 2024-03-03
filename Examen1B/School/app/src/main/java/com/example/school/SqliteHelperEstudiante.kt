package com.example.school

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelperEstudiante(contexto: Context?) : SQLiteOpenHelper(
    contexto,
    "school",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEstudiante =
            """
                CREATE TABLE ESTUDIANTE(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                apellido TEXT,
                idClase INTEGER,
                FOREIGN KEY (idClase) REFERENCES Clase(id)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEstudiante)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearEstudiante(nombre: String, apellido: String, idClase: Int): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("apellido", apellido)
        valoresAGuardar.put("idClase", idClase)
        val resultadoGuardar = basedatosEscritura.insert("ESTUDIANTE", null, valoresAGuardar)
        basedatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun editarEstudiantePorId(
        idEstudiante: Int,
        nuevoNombre: String,
        nuevoApellido: String,
        nuevoIdClase: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nuevoNombre)
        valoresAActualizar.put("apellido", nuevoApellido)
        valoresAActualizar.put("idClase", nuevoIdClase)
        val parametrosConsultaActualizar = arrayOf(idEstudiante.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "ESTUDIANTE",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun eliminarEstudiantePorId(idEstudiante: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idEstudiante.toString())
        val resultadoEliminacion =
            conexionEscritura.delete("ESTUDIANTE", "id=?", parametrosConsultaDelete)
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun obtenerEstudiantePorId(idEstudiante: Int): Estudiante? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM ESTUDIANTE WHERE id =?"
        val parametrosConsultaLectura = arrayOf(idEstudiante.toString())
        val resultadoConsultaLectura =
            baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)
        var estudiante: Estudiante? = null
        if (resultadoConsultaLectura.moveToFirst()) {
            val nombre = resultadoConsultaLectura.getString(1)
            val apellido = resultadoConsultaLectura.getString(2)
            val idClase = resultadoConsultaLectura.getInt(3)
            estudiante = Estudiante(idEstudiante, nombre, apellido, idClase)
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return estudiante
    }


    fun eliminarEstudiantesPorIdClase(idClase: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idClase.toString())
        val resultadoEliminacion =
            conexionEscritura.delete("ESTUDIANTE", "idClase=?", parametrosConsultaDelete)
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun actualizarIdsEstudiantesDespuesDeEliminar() {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM ESTUDIANTE"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        var nuevoId = 1
        val valoresAActualizar = ContentValues()
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                valoresAActualizar.put("id", nuevoId)
                val idEstudiante = resultadoConsultaLectura.getInt(0)
                val parametrosConsultaActualizar = arrayOf(idEstudiante.toString())
                baseDatosLectura.update(
                    "ESTUDIANTE",
                    valoresAActualizar,
                    "id=?",
                    parametrosConsultaActualizar
                )
                nuevoId++
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
    }


    fun actualizarIdsClaseEstudiantes(idClaseEliminado: Int) {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM ESTUDIANTE WHERE idClase > $idClaseEliminado"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val valoresAActualizar = ContentValues()
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val idEstudiante = resultadoConsultaLectura.getInt(0)
                val nuevoIdClase = resultadoConsultaLectura.getInt(3) - 1
                valoresAActualizar.put("idClase", nuevoIdClase)
                val parametrosConsultaActualizar = arrayOf(idEstudiante.toString())
                baseDatosLectura.update(
                    "ESTUDIANTE",
                    valoresAActualizar,
                    "id=?",
                    parametrosConsultaActualizar
                )
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
    }


    fun obtenerUltimoIdEstudiante(): Int {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT id FROM ESTUDIANTE ORDER BY id DESC LIMIT 1"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val ultimoId: Int
        ultimoId = if (resultadoConsultaLectura.moveToFirst()) {
            resultadoConsultaLectura.getInt(0)
        } else {
            0
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return ultimoId
    }

}
