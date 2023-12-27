package com.example.b2023_gr1sw_svnm

//Modelo que vamos a mostrar en pantalla
class BEntrenador(
    var id: Int,
    var nombre: String?,
    var descripcion: String?
) {
    override fun toString(): String {
        return "${nombre}-${descripcion}"
    }


}