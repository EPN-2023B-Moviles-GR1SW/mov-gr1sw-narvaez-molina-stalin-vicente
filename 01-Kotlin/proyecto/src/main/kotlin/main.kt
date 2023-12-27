import java.util.*
import kotlin.collections.ArrayList

fun main() {
    println("Hola mundo");
    // Tipos de variables
    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Adrian";
    // inmutable = "Vicente";

    // Mutables (Re asignar)
    var mutable: String = "Vicente";
    mutable = "Adrian";


    //  val > var
    // Duck Typing
    var ejemploVariable = " Adrian Eguez "
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo;

    // Variables primitivas
    val nombreProfesor: String = "Stalin Vicente Narvaez Molina"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    // Clases Java
    val fechaNacimiento: Date = Date()


    // SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }

        "S" -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }
    }
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"


    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(sueldo = 10.00, tasa = 12.00, bonoEspecial = 20.00) //  Parametros nombrados
    calcularSueldo(10.00, bonoEspecial = 20.00) // Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) //  Parametros nombrados

    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)


    //***********Arreglos ************
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    println("Arreglo Estatico : ${arregloEstatico}")

    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    println("Arreglo dinamico: ${arregloDinamico}")


    //FOR EACH --> UNIT
    //ITERAR UN ARREGLO

    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: ${valorActual}")

        }

    //podemos iterar una rreglo con (it) es l abreviatura en ingles para
    // elemento iterado
    arregloDinamico.forEach { println(it) }

    arregloEstatico
        .forEachIndexed { indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")

        }

    //otros Operadors
    // *****    MAP     *****
    //al utilizar un map crea un nuevo arreglo de los que nosotros le enviemos

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }

    println("Respuesta del MAP ${respuestaMap}")
    //***** Filter *****
    // este dara un nuevo arreglo pero filtrando algun parametro que nosotros indiquemos
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

    println("Respuesta del MAP ${respuestaFilter}")
    val respuestaFilter2 = arregloDinamico.filter { it <= 5 }
    println("Resputa del filter dos : ${respuestaFilter2}")


    //por si queremos obtener un verdadero o falso
    // de un arreglo vajo algun parametro de condicion
//operadoreslogicos
    //OR -> ANY (alguno cumple la condición)
    //AND -> ANY (todos cumplen la condicion )

    //ANY
    val respuetaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 5)
        }
    println("Respueta del operador logico Any: ${respuetaAny}")
    //ALL
    val respuetaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println("Respueta del operador logico ALL: ${respuetaAll}")


    //operador reduce como un tipo de acumulador lo que hacer es
    //acumulando el valor que nosotros queremos como nosotros queremos
    //algunos casos como
    //el acumulador siempre va a empezar en cero

    val respuestaReduce: Int = arregloDinamico
        .reduce { acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual)
        }
    println("Valore del reduce: ${respuestaReduce}")
    //lo que ahce es iterar un arreglo y acumular lo guardado.


}//****************fin del proceso principal MAIN********************

// **************************** CLASES **********************************
abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
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
) {
    // var cedula: string = "" (public es por defecto)
    // private valorCalculado: Int = 0 (private)
    init {
        this.numeroUno; this.numeroDos; // this es opcional
        numeroUno; numeroDos; // sin el "this", es lo mismo
    }
}


class Suma( // Constructor Primario Suma
    uno: Int, // Parametro
    dos: Int // Parametro
) : Numeros(uno, dos) { // <- Constructor del Padre

    init { // Bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor(//  Segundo constructor
        uno: Int?, // parametros
        dos: Int // parametros
    ) : this(  // llamada constructor primario
        if (uno == null) 0 else uno,
        dos
    ) { // si necesitamos bloque de codigo lo usamos
        numeroUno;
    }

    constructor(//  tercer constructor
        uno: Int, // parametros
        dos: Int? // parametros
    ) : this(  // llamada constructor primario
        uno,
        if (dos == null) 0 else uno
    ) // Si no lo necesitamos al bloque de codigo "{}" lo omitimos

    constructor(
        uno: Int?,
        dos: Int?
    ) : this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else uno

    )

    public fun sumar(): Int {
        val total = numeroDos + numeroUno
        agregarHistorial(total)
        return total
    }

    companion object {
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num

        }

        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int) {
            historialSumas.add(valorNuevaSuma)
        }
    }
}


// void -> Unit
fun imprimirNombre(nombre: String): Unit {
    // template strings
    // "Bienvenido: " + nombre + " "
    println("Nombre : ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null, // Opcion null -> nullable
): Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        bonoEspecial.dec()
        return sueldo * (100 / tasa) + bonoEspecial
    }
}

