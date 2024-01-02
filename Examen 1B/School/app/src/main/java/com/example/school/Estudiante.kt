package com.example.school

class Estudiante(
    var id: Int,
    var nombre: String?,
    var apellido: String?
) {
    override fun toString(): String {
        return "id:${id}-${nombre}-${apellido}"
    }
}