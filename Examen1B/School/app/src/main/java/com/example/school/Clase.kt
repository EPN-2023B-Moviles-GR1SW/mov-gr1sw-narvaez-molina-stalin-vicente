package com.example.school

class Clase(
    var idClass: Int,
    var nombre: String?,
    var descripcion: String?,
    var estudiantes: ArrayList<Estudiante> = arrayListOf() // Añadido para almacenar estudiantes
    ) {
    override fun toString(): String {
        return "ID Clase: ${idClass}  \n${nombre}\t : \t${descripcion}"
    }
}