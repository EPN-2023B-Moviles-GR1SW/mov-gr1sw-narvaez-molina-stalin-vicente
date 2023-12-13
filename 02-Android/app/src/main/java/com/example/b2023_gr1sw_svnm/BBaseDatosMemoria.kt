package com.example.b2023_gr1sw_svnm

class BBaseDatosMemoria {
    //empieza el companion object
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1, "Stalin", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2, "Vicente", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3, "Belen", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(4, "Belen", "a@a.com")
                )

        }


    }
}