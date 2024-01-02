package com.example.school

class BaseDeDatos {
    companion object {
        val arregloEstudiantes = arrayListOf<Estudiante>()
        val arregloClases = arrayListOf<Clase>()
        init {
            arregloEstudiantes
                .add(
                    Estudiante(
                        1, "Stalin", "Narváez"
                    )
                )
            arregloClases
                .add(
                    Clase(1, "Apps Moviles", "Desarrollo de aplicaciones ")
                )
        }
    }
}