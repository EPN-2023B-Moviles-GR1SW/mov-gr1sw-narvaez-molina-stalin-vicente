class Estudiante(
    var id: Int,
    var nombre: String?,
    var apellido: String?
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
        return "id: $id, nombre: $nombre, apellido: $apellido"
    }
}
