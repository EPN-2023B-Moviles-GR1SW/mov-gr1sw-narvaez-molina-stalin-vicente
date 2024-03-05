package com.example.multicines

class Pelicula (
    val nombre: String,
    val publico: String,
    val imagenURL: String
) {
    override fun toString(): String {
        return "$nombre - $publico"
    }
}