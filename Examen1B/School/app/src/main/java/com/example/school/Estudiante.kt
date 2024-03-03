package com.example.school

class Estudiante(
    var id: Int,
    var nombre: String?,
    var apellido: String?,
    var idClase: Int // Añadido para representar la relación 1 a n con la clase

) {
    override fun toString(): String {
        return "Id: ${id} | \t${nombre}\t${apellido}\t\t | Clase: ${idClase}"
    }
}