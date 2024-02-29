package com.example.school

import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BaseDeDatos {
    companion object {
        val arregloEstudiantes = arrayListOf<Estudiante>()
        val arregloClases = arrayListOf<Clase>()
        private val db = FirebaseFirestore.getInstance() // instancia de firebase firestorage
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

            // Colección "estudiantes" en Firestore
            val estudiantesRef = db.collection("estudiantes")

            // Agregar un nuevo documento con el ID proporcionado y los campos del estudiante
            estudiantesRef.document(id.toString())
                .set(nuevoEstudiante)
                .addOnSuccessListener {
                    // Éxito al agregar el estudiante
                    println("Estudiante agregado correctamente a Firestore.")
                }
                .addOnFailureListener { e ->
                    // Error al agregar el estudiante
                    println("Error al agregar el estudiante a Firestore: $e")
                }

        }

        fun editarEstudiantePorId(
            idEstudiante: Int,
            nuevoNombre: String,
            nuevoApellido: String,
            nuevoIdClase: Int
        ) {
            // Referencia al documento del estudiante en Firestore
            val estudianteRef = db.collection("estudiantes").document(idEstudiante.toString())

            // Actualizar los campos del estudiante en Firestore
            estudianteRef.update(
                mapOf(
                    "nombre" to nuevoNombre,
                    "apellido" to nuevoApellido,
                    "idClase" to nuevoIdClase
                )
            )
                .addOnSuccessListener {
                    // Éxito al editar el estudiante
                    println("Estudiante con ID $idEstudiante editado correctamente en Firestore.")
                }
                .addOnFailureListener { e ->
                    // Error al editar el estudiante
                    println("Error al editar el estudiante con ID $idEstudiante en Firestore: $e")
                }
            val estudianteAEditar = arregloEstudiantes.find { it.id == idEstudiante }

            if (estudianteAEditar != null) {
                estudianteAEditar.nombre = nuevoNombre
                estudianteAEditar.apellido = nuevoApellido
                estudianteAEditar.idClase = nuevoIdClase
            }
        }

        fun eliminarEstudiantePorId(adaptador: ArrayAdapter<Estudiante>, idEstudiante: Int) {
            // Referencia al documento del estudiante en Firestore
            val estudianteRef = db.collection("estudiantes").document(idEstudiante.toString())

            // Eliminar el documento
            estudianteRef.delete()
                .addOnSuccessListener {
                    // Éxito al eliminar el estudiante
                    println("Estudiante eliminado correctamente de Firestore.")

                }
                .addOnFailureListener { e ->
                    // Error al eliminar el estudiante
                    println("Error al eliminar el estudiante de Firestore: $e")
                }
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
            // Consultar estudiantes que pertenecen a la clase con el ID proporcionado
            val estudiantesRef = db.collection("estudiantes")
                .whereEqualTo("idClase", idClase)

            // Ejecutar la consulta
            estudiantesRef.get()
                .addOnSuccessListener { querySnapshot ->
                    // Por cada estudiante encontrado, eliminar su documento
                    querySnapshot.documents.forEach { document ->
                        document.reference.delete()
                            .addOnSuccessListener {
                                // Éxito al eliminar el estudiante
                                println("Estudiante con ID ${document.id} eliminado correctamente de Firestore.")
                            }
                            .addOnFailureListener { e ->
                                // Error al eliminar el estudiante
                                println("Error al eliminar el estudiante con ID ${document.id} de Firestore: $e")
                            }
                    }
                }
                .addOnFailureListener { e ->
                    // Error al ejecutar la consulta
                    println("Error al consultar estudiantes en Firestore: $e")
                }
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
            // Verificar que el ID de clase sea único
            db.collection("clases").document(idClase.toString()).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Si el documento con el ID de clase ya existe, imprimir un mensaje de error
                        println("Error: El ID de clase $idClase ya existe en la base de datos.")
                    } else {
                        // Si el ID de clase es único, crear la nueva clase
                        val datosClase = hashMapOf(
                            "idClass" to idClase,
                            "nombre" to nombre,
                            "descripcion" to descripcion
                        )

                        // Agregar la clase a la colección "clases" en Firestore
                        db.collection("clases").document(idClase.toString())
                            .set(datosClase)
                            .addOnSuccessListener {
                                // Éxito al crear la clase
                                println("Clase con ID $idClase creada correctamente en Firestore.")
                            }
                            .addOnFailureListener { e ->
                                // Error al crear la clase
                                println("Error al crear la clase con ID $idClase en Firestore: $e")
                            }
                    }
                }
                .addOnFailureListener { e ->
                    // Error al verificar la existencia del ID de clase
                    println("Error al verificar la existencia del ID de clase en Firestore: $e")
                }



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
            // Eliminar la clase de Firestore
            db.collection("clases").document(idClase.toString())
                .delete()
                .addOnSuccessListener {
                    // Éxito al eliminar la clase de Firestore
                    println("Clase con ID $idClase eliminada correctamente de Firestore.")
                }
                .addOnFailureListener { e ->
                    // Error al eliminar la clase de Firestore
                    println("Error al eliminar la clase con ID $idClase de Firestore: $e")
                }

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
            // Referencia al documento de la clase en Firestore
            val claseRef = db.collection("clases").document(idClase.toString())

            // Actualizar los datos de la clase en Firestore
            claseRef.update(
                mapOf(
                    "nombre" to nuevoNombre,
                    "descripcion" to nuevaDescripcion
                )
            )
                .addOnSuccessListener {
                    // Éxito al actualizar la clase
                    println("Clase con ID $idClase actualizada correctamente en Firestore.")
                }
                .addOnFailureListener { e ->
                    // Error al actualizar la clase
                    println("Error al actualizar la clase con ID $idClase en Firestore: $e")
                }

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