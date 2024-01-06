import java.io.*

class Clase(
    var id: Int,
    var materia: String,
    var numeroHorasSemana: Int,
    var profesor: String,
    var numeroAula: Int,
    var estudiantes: MutableList<Estudiante> = mutableListOf() // Lista de estudiantes
) {
    override fun toString(): String {
        return "Clase(id=$id, materia='$materia', numeroHorasSemana=$numeroHorasSemana, profesor='$profesor', numeroAula=$numeroAula, estudiantes=$estudiantes)"
    }

}
fun Clase.agregarEstudiante(estudiante: Estudiante) {
    this.estudiantes.add(estudiante)
    println("Estudiante agregado exitosamente")
   print(toString())
}
fun crearClase(clase: Clase) {
    val file = File("clases.txt")
    val writer = BufferedWriter(FileWriter(file, true))

    writer.write("${clase.id},${clase.materia},${clase.numeroHorasSemana},${clase.profesor},${clase.numeroAula}\n")

    writer.close()
}

fun leerClases(): List<Clase> {
    val clases = mutableListOf<Clase>()
    val file = File("clases.txt")

    if (file.exists()) {
        val reader = BufferedReader(FileReader(file))
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            val data = line!!.split(",")
            val clase = Clase(data[0].toInt(), data[1], data[2].toInt(), data[3], data[4].toInt())
            clases.add(clase)
        }

        reader.close()
    }

    return clases
}

fun actualizarClase(claseActualizada: Clase) {
    val file = File("clases.txt")
    val tempFile = File.createTempFile("temp", null)
    val reader = BufferedReader(FileReader(file))
    val writer = BufferedWriter(FileWriter(tempFile))

    var line: String?
    while (reader.readLine().also { line = it } != null) {
        val currentLine = line!!.split(",")
        if (currentLine.isNotEmpty() && currentLine[0].toInt() == claseActualizada.id) {
            writer.write("${claseActualizada.id},${claseActualizada.materia},${claseActualizada.numeroHorasSemana},${claseActualizada.profesor},${claseActualizada.numeroAula}\n")
        } else {
            writer.write("$line\n")
        }
    }

    writer.close()
    reader.close()
    file.delete()
    tempFile.renameTo(file)
}

fun eliminarClasePorId(id: Int) {
    val file = File("clases.txt")
    val tempFile = File.createTempFile("temp", null)
    val reader = BufferedReader(FileReader(file))
    val writer = BufferedWriter(FileWriter(tempFile))

    var line: String?
    while (reader.readLine().also { line = it } != null) {
        val currentLine = line!!.split(",")
        if (currentLine.isNotEmpty() && currentLine[0].toInt() != id) {
            writer.write("$line\n")
        }
    }

    writer.close()
    reader.close()
    file.delete()
    tempFile.renameTo(file)
}

//obtneer el Id de la clase a actualizar
fun obtenerClaseParaActualizar(id: Int): Clase? {
    val file = File("clases.txt")
    val reader = BufferedReader(FileReader(file))

    var line: String?
    while (reader.readLine().also { line = it } != null) {
        val currentLine = line!!.split(",")
        if (currentLine.isNotEmpty() && currentLine[0].toInt() == id) {
            return Clase(
                currentLine[0].toInt(),
                currentLine[1],
                currentLine[2].toInt(),
                currentLine[3],
                currentLine[4].toInt()
            )
        }
    }

    reader.close()
    return null
}

