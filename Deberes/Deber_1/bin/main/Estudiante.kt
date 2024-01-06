import java.io.*

class Estudiante(
    var id: Int,
    var nombre: String?,
    var apellido: String?,
    var edad: Int,
    var aprobado: Boolean
) {
    init {
        // Se inicializan las propiedades si no se proporcionan al momento de la creación del objeto
        if (nombre == null) {
            this.nombre = ""
        }
        if (apellido == null) {
            this.apellido = ""
        }
    }

    override fun toString(): String {
        return "Estudiante(id=$id, nombre=$nombre, apellido=$apellido, edad=$edad, aprobado=$aprobado)"
    }


}

fun crearEstudiante(estudiante: Estudiante) {
    val file = File("estudiantes.txt")
    val writer = BufferedWriter(FileWriter(file, true))

    writer.write("${estudiante.id},${estudiante.nombre},${estudiante.apellido},${estudiante.edad},${estudiante.aprobado}\n")

    writer.close()
}

fun leerEstudiantes(): List<Estudiante> {
    val estudiantes = mutableListOf<Estudiante>()
    val file = File("estudiantes.txt")

    if (file.exists()) {
        val reader = BufferedReader(FileReader(file))
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            val data = line!!.split(",")
            val estudiante = Estudiante(data[0].toInt(), data[1], data[2], data[3].toInt(), data[4].toBoolean())
            estudiantes.add(estudiante)
        }

        reader.close()
    }

    return estudiantes
}

fun actualizarEstudiante(estudianteActualizado: Estudiante) {
    val file = File("estudiantes.txt")
    val tempFile = File.createTempFile("temp", null)
    val reader = BufferedReader(FileReader(file))
    val writer = BufferedWriter(FileWriter(tempFile))

    var line: String?
    while (reader.readLine().also { line = it } != null) {
        val currentLine = line!!.split(",")
        if (currentLine.isNotEmpty() && currentLine[0].toInt() == estudianteActualizado.id) {
            writer.write("${estudianteActualizado.id},${estudianteActualizado.nombre},${estudianteActualizado.apellido},${estudianteActualizado.edad},${estudianteActualizado.aprobado}\n")
        } else {
            writer.write("$line\n")
        }
    }

    writer.close()
    reader.close()
    file.delete()
    tempFile.renameTo(file)
}

fun eliminarEstudiantePorId(id: Int) {
    val file = File("estudiantes.txt")
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
//Obtener el id del Estudiante  a actualizar
fun obtenerEstudianteParaActualizar(id: Int): Estudiante? {
    val file = File("estudiantes.txt")
    val reader = BufferedReader(FileReader(file))

    var line: String?
    while (reader.readLine().also { line = it } != null) {
        val currentLine = line!!.split(",")
        if (currentLine.isNotEmpty() && currentLine[0].toInt() == id) {
            return Estudiante(
                currentLine[0].toInt(),
                currentLine[1],
                currentLine[2],
                currentLine[3].toInt(),
                currentLine[4].toBoolean()
            )
        }
    }

    reader.close()
    return null
}