import java.awt.GridLayout
import java.io.*
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JTextField

fun main() {
    var opcion: Int
    //val clase1 = Clase(2,"Lenguaje",5,"Chente",503)
    //crearClase(clase1)
    //val clase1 = Clase(2, "Lenguaje", 5, "Chente", 503)
    //crearClase(clase1)

    // Cargar las clases y estudiantes desde archivos
    val clasesCargadas = cargarClasesDesdeArchivo()
    val estudiantesCargados = cargarEstudiantesDesdeArchivo()

    // Verificar los datos cargados imprimiéndolos en la consola
    println("Clases cargadas:")
    for (clase in clasesCargadas) {
        println(clase.toString())
    }

    println("\nEstudiantes cargados:")
    for (estudiante in estudiantesCargados) {
        println(estudiante.toString())
    }
    //*******************************MENU****************************************
    do {
        val opciones = arrayOf(
            "Listar Estudiantes", "Listar Clases", "Crear Estudiante", "Crear Clase",
            "Actualizar estudiante por id", "Actualizar clase por id", "Eliminar estudiante por id",
            "Eliminar clase por id","Agregar Estudiantes a clase", "Salir"
        )

        val opcionElegida = JOptionPane.showInputDialog(
            null, "Selecciona una opción:",
            "Menú", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]
        )

        opcion = opciones.indexOf(opcionElegida)

        when (opcion) {
            0 -> listarEstudiantes(leerEstudiantes())
            1 -> listarClases(leerClases())
            2 -> crearEstudiante()
            3 -> crearClase()
            4 -> actualizarEstudiantePorId()
            5 -> actualizarClasePorId()
            6 -> eliminarEstudiantePorId()
            7 -> eliminarClasePorId()
            8 -> agregarEstudianteAClase()
            9 -> JOptionPane.showMessageDialog(null, "Saliendo de la aplicación")
            else -> JOptionPane.showMessageDialog(null, "Opción no válida")
        }
    } while (opcion != 9)


    //*******************************MENU****************************************
}

fun guardarClasesEnArchivo(clases: List<Clase>) {
    val file = File("clases.txt")
    val writer = BufferedWriter(FileWriter(file))

    for (clase in clases) {
        writer.write("${clase.id},${clase.materia},${clase.numeroHorasSemana},${clase.profesor},${clase.numeroAula}\n")
    }

    writer.close()
}

fun guardarEstudiantesEnArchivo(estudiantes: List<Estudiante>) {
    val file = File("estudiantes.txt")
    val writer = BufferedWriter(FileWriter(file))

    for (estudiante in estudiantes) {
        writer.write("${estudiante.id},${estudiante.nombre},${estudiante.apellido},${estudiante.edad},${estudiante.aprobado}\n")
    }

    writer.close()
}

fun cargarClasesDesdeArchivo(): MutableList<Clase> {
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

fun cargarEstudiantesDesdeArchivo(): MutableList<Estudiante> {
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
//********************************* Metodos del menu ***********************************

fun listarEstudiantes(estudiantes: List<Estudiante>) {
    val estudiantesFormateados = estudiantes.joinToString("\n") { estudiante ->
        "Estudiante(id=${estudiante.id}, nombre=${estudiante.nombre}, apellido=${estudiante.apellido}, edad=${estudiante.edad}, aprobado=${estudiante.aprobado})"
    }
    JOptionPane.showMessageDialog(null, "Listando Clases con estudiantes:\n $estudiantesFormateados")
}

fun listarClases(clases: List<Clase>) {
    val clasesFormateadas = clases.joinToString("\n") { clase ->
        val estudiantesClase = clase.estudiantes.joinToString(", ") { estudiante ->
            "Estudiante(id=${estudiante.id}, nombre=${estudiante.nombre}, apellido=${estudiante.apellido}, edad=${estudiante.edad}, aprobado=${estudiante.aprobado})"
        }
        "Clase(id=${clase.id}, materia='${clase.materia}', numeroHorasSemana=${clase.numeroHorasSemana}, profesor='${clase.profesor}', numeroAula=${clase.numeroAula}, estudiantes=[$estudiantesClase])"
    }
    JOptionPane.showMessageDialog(null, "Listando Clases con estudiantes:\n $clasesFormateadas")
}


fun crearEstudiante() {
    val panel = JPanel()
    panel.layout = GridLayout(0, 2)

    val idField = JTextField(10)
    val nombreField = JTextField(10)
    val apellidoField = JTextField(10)
    val edadField = JTextField(10)
    val aprobadoField = JTextField(10)

    panel.add(JLabel("ID del estudiante:"))
    panel.add(idField)
    panel.add(JLabel("Nombre:"))
    panel.add(nombreField)
    panel.add(JLabel("Apellido:"))
    panel.add(apellidoField)
    panel.add(JLabel("Edad:"))
    panel.add(edadField)
    panel.add(JLabel("¿Está aprobado? (true/false):"))
    panel.add(aprobadoField)

    val result = JOptionPane.showConfirmDialog(
        null, panel, "Ingrese los datos del estudiante",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    )

    if (result == JOptionPane.OK_OPTION) {
        try {
            val id = idField.text.toInt()
            val nombre = nombreField.text
            val apellido = apellidoField.text
            val edad = edadField.text.toInt()
            val aprobado = aprobadoField.text.toBoolean()

            val nuevoEstudiante = Estudiante(id, nombre, apellido, edad, aprobado)
            crearEstudiante(nuevoEstudiante)
            JOptionPane.showMessageDialog(
                null,
                "Se ha creado el estudiante:\n$nuevoEstudiante",
                "Estudiante Creado",
                JOptionPane.INFORMATION_MESSAGE
            )
        } catch (e: NumberFormatException) {
            JOptionPane.showMessageDialog(
                null,
                "Por favor, ingrese valores válidos",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }
}

fun crearClase() {
    val panel = JPanel()
    panel.layout = GridLayout(0, 2)

    val idField = JTextField(10)
    val materiaField = JTextField(10)
    val horasSemanaField = JTextField(10)
    val profesorField = JTextField(10)
    val numeroAulaField = JTextField(10)

    panel.add(JLabel("ID de la clase:"))
    panel.add(idField)
    panel.add(JLabel("Materia:"))
    panel.add(materiaField)
    panel.add(JLabel("Número de horas a la semana:"))
    panel.add(horasSemanaField)
    panel.add(JLabel("Profesor:"))
    panel.add(profesorField)
    panel.add(JLabel("Número de aula:"))
    panel.add(numeroAulaField)

    val result = JOptionPane.showConfirmDialog(
        null, panel, "Ingrese los datos de la clase",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    )

    if (result == JOptionPane.OK_OPTION) {
        try {
            val id = idField.text.toInt()
            val materia = materiaField.text
            val horasSemana = horasSemanaField.text.toInt()
            val profesor = profesorField.text
            val numeroAula = numeroAulaField.text.toInt()
            val nuevaClase = Clase(id, materia, horasSemana, profesor, numeroAula)
            crearClase(nuevaClase)
            JOptionPane.showMessageDialog(
                null,
                "Se ha creado la clase:\n$nuevaClase",
                "Clase Creada",
                JOptionPane.INFORMATION_MESSAGE
            )
        } catch (e: NumberFormatException) {
            JOptionPane.showMessageDialog(
                null,
                "Por favor, ingrese valores válidos",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }
}

fun actualizarEstudiantePorId() {
    val idField = JTextField(10)

    val panel = JPanel()
    panel.layout = GridLayout(0, 2)
    panel.add(JLabel("ID del estudiante a actualizar:"))
    panel.add(idField)

    val result = JOptionPane.showConfirmDialog(
        null, panel, "Ingrese el ID del estudiante a actualizar",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    )

    if (result == JOptionPane.OK_OPTION) {
        val id = idField.text.toInt()
        val estudianteActualizado = obtenerEstudianteParaActualizar(id)

        if (estudianteActualizado != null) {
            mostrarFormularioActualizacionEstudiante(estudianteActualizado)
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ningún estudiante con el ID proporcionado", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }
}

fun actualizarClasePorId() {
    val idField = JTextField(10)

    val panel = JPanel()
    panel.layout = GridLayout(0, 2)
    panel.add(JLabel("ID de la clase a actualizar:"))
    panel.add(idField)

    val result = JOptionPane.showConfirmDialog(
        null, panel, "Ingrese el ID de la clase a actualizar",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    )

    if (result == JOptionPane.OK_OPTION) {
        val id = idField.text.toInt()
        val claseActualizada = obtenerClaseParaActualizar(id)

        if (claseActualizada != null) {
            mostrarFormularioActualizacionClases(claseActualizada)
        } else {
            JOptionPane.showMessageDialog(
                null,
                "No se encontró ninguna clase con el ID proporcionado",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }
}

fun eliminarEstudiantePorId() {
    val input = JOptionPane.showInputDialog(
        null,
        "Por favor, ingresa el ID del alumno a eliminar:",
        "Eliminar Alumno por ID",
        JOptionPane.QUESTION_MESSAGE
    )

    try {
        val idAlumno = input?.toInt()

        if (idAlumno != null) {
            eliminarEstudiantePorId(idAlumno)
            JOptionPane.showMessageDialog(
                null,
                "Se eliminó al alumno con ID $idAlumno",
                "Alumno Eliminado",
                JOptionPane.INFORMATION_MESSAGE
            )
            cargarEstudiantesDesdeArchivo()
        } else {
            JOptionPane.showMessageDialog(
                null,
                "Por favor, ingresa un ID de alumno válido",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
        }
    } catch (e: NumberFormatException) {
        JOptionPane.showMessageDialog(
            null,
            "Por favor, ingresa un ID de alumno válido",
            "Error",
            JOptionPane.ERROR_MESSAGE
        )
    }
    JOptionPane.showMessageDialog(null, "Eliminando Estudiante por ID")
}

fun eliminarClasePorId() {
    val input = JOptionPane.showInputDialog(
        null,
        "Por favor, ingresa el ID de la clase a eliminar:",
        "Eliminar Clase por ID",
        JOptionPane.QUESTION_MESSAGE
    )

    try {
        val idClase = input?.toInt()

        if (idClase != null) {
            eliminarClasePorId(idClase)
            JOptionPane.showMessageDialog(
                null,
                "Se eliminó la clase con ID $idClase",
                "Clase Eliminada",
                JOptionPane.INFORMATION_MESSAGE
            )
        } else {
            JOptionPane.showMessageDialog(
                null,
                "Por favor, ingresa un ID de clase válido",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
        }
    } catch (e: NumberFormatException) {
        JOptionPane.showMessageDialog(
            null,
            "Por favor, ingresa un ID de clase válido",
            "Error",
            JOptionPane.ERROR_MESSAGE
        )
    }
}

fun mostrarFormularioActualizacionClases(claseActualizada: Clase) {
    val panel = JPanel()
    panel.layout = GridLayout(0, 2)

    val materiaField = JTextField(claseActualizada.materia, 10)
    val horasSemanaField = JTextField(claseActualizada.numeroHorasSemana.toString(), 10)
    val profesorField = JTextField(claseActualizada.profesor, 10)
    val numeroAulaField = JTextField(claseActualizada.numeroAula.toString(), 10)

    panel.add(JLabel("Nueva materia:"))
    panel.add(materiaField)
    panel.add(JLabel("Nuevo número de horas a la semana:"))
    panel.add(horasSemanaField)
    panel.add(JLabel("Nuevo profesor:"))
    panel.add(profesorField)
    panel.add(JLabel("Nuevo número de aula:"))
    panel.add(numeroAulaField)

    val result = JOptionPane.showConfirmDialog(
        null, panel, "Ingrese los nuevos datos de la clase",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    )

    if (result == JOptionPane.OK_OPTION) {
        try {
            val materia = materiaField.text
            val horasSemana = horasSemanaField.text.toInt()
            val profesor = profesorField.text
            val numeroAula = numeroAulaField.text.toInt()

            val claseActualizada = Clase(
                claseActualizada.id,
                materia,
                horasSemana,
                profesor,
                numeroAula
            )
            actualizarClase(claseActualizada)
            JOptionPane.showMessageDialog(
                null,
                "Se ha actualizado la clase:\n$claseActualizada",
                "Clase Actualizada",
                JOptionPane.INFORMATION_MESSAGE
            )
        } catch (e: NumberFormatException) {
            JOptionPane.showMessageDialog(
                null,
                "Por favor, ingrese valores válidos",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }
}
fun mostrarFormularioActualizacionEstudiante(estudianteActualizado: Estudiante) {
    val panel = JPanel()
    panel.layout = GridLayout(0, 2)

    val nombreField = JTextField(estudianteActualizado.nombre, 10)
    val apellidoField = JTextField(estudianteActualizado.apellido, 10)
    val edadField = JTextField(estudianteActualizado.edad.toString(), 10)
    val aprobadoField = JTextField(estudianteActualizado.aprobado.toString(), 10)

    panel.add(JLabel("Nuevo nombre:"))
    panel.add(nombreField)
    panel.add(JLabel("Nuevo apellido:"))
    panel.add(apellidoField)
    panel.add(JLabel("Nueva edad:"))
    panel.add(edadField)
    panel.add(JLabel("¿Está aprobado? (true/false):"))
    panel.add(aprobadoField)

    val result = JOptionPane.showConfirmDialog(
        null, panel, "Ingrese los nuevos datos del estudiante",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    )

    if (result == JOptionPane.OK_OPTION) {
        try {
            val nombre = nombreField.text
            val apellido = apellidoField.text
            val edad = edadField.text.toInt()
            val aprobado = aprobadoField.text.toBoolean()

            val estudianteActualizado = Estudiante(
                estudianteActualizado.id,
                nombre,
                apellido,
                edad,
                aprobado
            )
            actualizarEstudiante(estudianteActualizado)
            JOptionPane.showMessageDialog(null, "Se ha actualizado el estudiante:\n$estudianteActualizado", "Estudiante Actualizado", JOptionPane.INFORMATION_MESSAGE)
        } catch (e: NumberFormatException) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese valores válidos", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }
}

fun agregarEstudianteAClase() {
    val idClase = JOptionPane.showInputDialog(null, "Ingrese el ID de la clase:")
    val idEstudiante = JOptionPane.showInputDialog(null, "Ingrese el ID del estudiante:")

    val claseEncontrada = obtenerClaseParaActualizar(idClase.toInt())
    println("He llegado hasta aqui 0")
    val estudianteEncontrado = obtenerEstudianteParaActualizar(idEstudiante.toInt())
    println("He llegado hasta aqui 1")
    if (claseEncontrada != null && estudianteEncontrado != null) {
        println("He llegado hasta aqui 3")
        claseEncontrada.agregarEstudiante(estudianteEncontrado)
        println("He llegado hasta aqui 4")
    } else {
        if (claseEncontrada == null) {
            JOptionPane.showMessageDialog(null, "La clase con el ID $idClase no fue encontrada.")
        }
        if (estudianteEncontrado == null) {
            JOptionPane.showMessageDialog(null, "El estudiante con el ID $idEstudiante no fue encontrado.")
        }
    }
}
