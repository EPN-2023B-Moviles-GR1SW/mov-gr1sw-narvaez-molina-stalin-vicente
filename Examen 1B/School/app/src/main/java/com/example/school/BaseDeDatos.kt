package com.example.school

import android.widget.ArrayAdapter

class BaseDeDatos {
    companion object {
        val arregloEstudiantes = arrayListOf<Estudiante>()
        val arregloClases = arrayListOf<Clase>()

        init {
            //estudiantes
            arregloEstudiantes.add(
                Estudiante(
                    1, "Stalin", "Narváez", 1
                )
            )
            arregloEstudiantes.add(
                Estudiante(
                    2, "Vicente", "Narváez", 2
                )
            )
            arregloEstudiantes.add(
                Estudiante(
                    3, "Jose", "Narváez", 3
                )
            )

            //clases
            arregloClases.add(
                Clase(1, "Apps Moviles", "Desarrollo de aplicaciones ")
            )
            arregloClases.add(
                Clase(2, "Usabilidad y accecibilidad", "Leyes Ux")
            )
            arregloClases.add(
                Clase(3, "Fisica", "Fisica avanzada")
            )
            arregloClases.add(
                Clase(4, "Quimica", "Centrado en la quimica organica")
            )

        }

        //******************************funciones para manejar Estudiantes en la Base de datos local************************
        fun crearNuevoEstudiante(id: Int, nombre: String, apellido: String, idClase: Int) {
            val nuevoEstudiante = Estudiante(id, nombre, apellido, idClase)
            arregloEstudiantes.add(nuevoEstudiante)
        }

        fun editarEstudiantePorId(
            idEstudiante: Int,
            nuevoNombre: String,
            nuevoApellido: String,
            nuevoIdClase: Int
        ) {
            val estudianteAEditar = arregloEstudiantes.find { it.id == idEstudiante }

            if (estudianteAEditar != null) {
                estudianteAEditar.nombre = nuevoNombre
                estudianteAEditar.apellido = nuevoApellido
                estudianteAEditar.idClase = nuevoIdClase
            }
        }

        fun eliminarEstudiantePorId(adaptador: ArrayAdapter<Estudiante>, idEstudiante: Int) {
            val estudianteAEliminar = arregloEstudiantes.find { it.id == idEstudiante }

            if (estudianteAEliminar != null) {
                arregloEstudiantes.remove(estudianteAEliminar)
                actualizarIdsEstudiantesDespuesDeEliminar()
                adaptador.notifyDataSetChanged()
            }
        }

        fun obtenerEstudiantePorId(idEstudiante: Int): Estudiante? {
            return arregloEstudiantes.find { it.id == idEstudiante }
        }

        fun eliminarEstudiantesPorIdClase(idClase: Int) {
            // Filtrar la lista de estudiantes para mantener solo aquellos que no pertenecen a la clase con el ID proporcionado
            val estudiantesFiltrados = arregloEstudiantes.filter { it.idClase != idClase }

            // Actualizar la lista de estudiantes
            arregloEstudiantes.clear()
            arregloEstudiantes.addAll(estudiantesFiltrados)
        }

        fun actualizarIdsEstudiantesDespuesDeEliminar() {
            // Recorre el arreglo y asigna nuevos IDs en orden
            for (indice in arregloEstudiantes.indices) {
                arregloEstudiantes[indice].id = indice + 1
            }
        }
        fun actualizarIdsClaseEstudiantes(idClaseEliminado: Int) {
            // Filtra los estudiantes con idClase mayor al eliminado
            val estudiantesAActualizar = arregloEstudiantes.filter { it.idClase > idClaseEliminado }

            // Actualiza los idClase restando 1
            estudiantesAActualizar.forEach { estudiante ->
                estudiante.idClase -= 1
            }
        }
        fun obtenerUltimoIdEstudiante(): Int {
            return if (arregloEstudiantes.isEmpty()) {
                // Si el arreglo de estudiantes está vacío, devuelve 1 como el primer ID posible
                1
            } else {
                // Obtiene el último estudiante en el arreglo y devuelve su ID + 1
                val ultimoEstudiante = arregloEstudiantes.last()
                ultimoEstudiante.id
            }
        }


        //******************************funciones para manejar clases en la Base de datos local************************
        fun crearNuevaClase(
            idClase: Int,
            nombre: String,
            descripcion: String
        ) {
            // Verificar que el ID sea único
            val idExistente = arregloClases.any { it.idClass == idClase }

            if (!idExistente) {
                // Crear la nueva clase
                val nuevaClase = Clase(idClase, nombre, descripcion)
                arregloClases.add(nuevaClase)

            } else {
                // Manejar la situación donde el ID no es único (puedes lanzar un error o tomar otra acción)
                // Por ahora, simplemente imprimir un mensaje
                println("Error: El ID generado ya existe en la base de datos.")
            }
        }

        fun eliminarClasePorId(adaptador: ArrayAdapter<Clase>, idClase: Int) {
            val claseAEliminar = arregloClases.find { it.idClass == idClase }

            if (claseAEliminar != null) {
                arregloClases.remove(claseAEliminar)
                actualizarIdsDespuesDeEliminar()
                adaptador.notifyDataSetChanged()
                eliminarEstudiantesPorIdClase(idClase)
                actualizarIdsClaseEstudiantes(idClase)//asdasdaflnsd;
                actualizarIdsEstudiantesDespuesDeEliminar()

            }
        }

        fun actualizarIdsDespuesDeEliminar() {
            // Recorre el arreglo y asigna nuevos IDs en orden
            for (indice in arregloClases.indices) {
                arregloClases[indice].idClass = indice + 1
            }
        }

        fun obtenerUltimoIdClase(): Int {
            // Si el arreglo está vacío, devuelve null indicando que no hay clases
            // Devuelve el último ID del arreglo
            return arregloClases.last().idClass
        }

        fun actualizarClasePorId(
            idClase: Int,
            nuevoNombre: String,
            nuevaDescripcion: String
        ) {
            val claseAActualizar = arregloClases.find { it.idClass == idClase }

            if (claseAActualizar != null) {
                // Actualiza los atributos de la clase encontrada
                claseAActualizar.nombre = nuevoNombre
                claseAActualizar.descripcion = nuevaDescripcion


            }
        }

        fun obtenerClasePorId(idClase: Int): Clase? {
            return arregloClases.find { it.idClass == idClase }
        }

    }
}