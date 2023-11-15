fun main() {
    println("Hola Mundo")

    //tipos de vairbales
    //inmutables

    var inmutable: String = "Stalin";
    //inmutable= "talin"
    //mutables
    var mutable: String = "Vicente";
    mutable = "Chente"
    var num = 1

    // Switch
    val estado = "C"
    when (estado) {
        ("C") -> {
            print("Casado")
        }

        "S" -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }

    }

    //funciones
    //void --> unit


    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(sueldo = 10.00, tasa = 12.00, bonoEspecial = 20.00) // Parametros nombrados
    calcularSueldo(10.00, bonoEspecial = 20.00) // Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) // Parametros nombrados


}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int, dos: Int
    ) { // Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros(
    // Constructor PRIMARIO
// Ejemplo:
// uno: Int, // (Parametro (sin modificador de acceso))
// private var uno: Int, // Propiedad Publica Clase numeros.uno
// var uno: Int, // Propiedad de la clase (por defecto es PUBLIC)
// public var uno: Int,
// Propiedad de la clase protected numeros.numeroUno
    protected val numeroUno: Int,
// Propiedad de la clase protected numeros.numeroDos
    protected val numeroDos: Int,
// var cedula: string = "" (public es por defecto)
// private valorCalculado: Int = 0 (private)
) { init {
    this.numeroUno; this.numeroDos; // this es opcional
    numeroUno; numeroDos; // sin el "this", es lo mismo|

    println("Inicializando")
}
}



class suma(
    Uno: Int,
    Dos: Int
): Numeros(Uno,Dos) {
    init {
        this.numeroUno;numeroUno;
        this.numeroDos;numeroDos;
    }

    constructor(// Segundo constructor
        uno: Int?, // parametros
        dos: Int // parametros
    ) : this( // llamada constructor primario
        if (uno == null) 0 else uno, dos
        1
    ) 1// si necesitamos bloque de codigo lo usamos
    numeroUno;
}

    constructor(// tercer constructor
        uno: Int, // parametros
        dos: Int? // parametros
    ) : this( // llamada constructor primario
        uno,
        if (dos == null) 0 else uno
    )


}

fun imprimirNombre(nombre: String): Unit {
    println("nombre :${nombre}") //contactenacion con ${} se le llama template String
}

fun calcularSueldo(
    sueldo: Double, //requerido
    tasa: Double = 12.00, //opcional
    bonoEspecial: Double? = null
): Double {

    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        bonoEspecial.dec()
        return sueldo * (100 / tasa) + bonoEspecial
    }
}