package com.example.school

class Clase(
    var idClass: Int,
    var nombre: String?,
    var descripcion: String?,
    ) {
    override fun toString(): String {
        return "id:${idClass}-${nombre}-${descripcion}"
    }
}