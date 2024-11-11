package model;

/**
 * La clase SearchResult representa un resultado de búsqueda en un servicio de streaming.
 *
 * Contiene información relevante acerca del título, la descripción, el enlace y la plataforma
 * en la que se encuentra el contenido encontrado, así como el tipo de contenido.
 */
public class SearchResult {

    /**
     * Título del contenido encontrado.
     */
    private String titulo;

    /**
     * Descripción breve del contenido encontrado.
     */
    private String descripcion;

    /**
     * Enlace directo al contenido encontrado en la plataforma.
     */
    private String enlace;

    /**
     * Nombre de la plataforma donde se encuentra disponible el contenido.
     */
    private String plataforma;

    /**
     * Tipo de contenido (por ejemplo, si es de pago o gratuito).
     */
    private String tipo;

    /**
     * Constructor por defecto de SearchResult. Inicializa el objeto sin parámetros.
     */
    public SearchResult() {
    }

    /**
     * Constructor que inicializa un resultado de búsqueda con los datos proporcionados.
     *
     * @param titulo       El título del contenido encontrado.
     * @param descripcion  La descripción del contenido.
     * @param enlace       El enlace hacia el contenido.
     * @param plataforma   La plataforma donde se encuentra el contenido.
     * @param tipo         El tipo de contenido (ej. "gratuito" o "de pago").
     */
    public SearchResult(String titulo, String descripcion, String enlace, String plataforma, String tipo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.enlace = enlace;
        this.plataforma = plataforma;
        this.tipo = tipo;
    }

    /**
     * Obtiene el título del resultado de búsqueda.
     *
     * @return El título del contenido encontrado.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Obtiene la descripción del resultado de búsqueda.
     *
     * @return La descripción del contenido encontrado.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene el enlace hacia el resultado de búsqueda.
     *
     * @return El enlace al contenido encontrado.
     */
    public String getEnlace() {
        return enlace;
    }

    /**
     * Obtiene la plataforma en la que se encuentra el contenido.
     *
     * @return El nombre de la plataforma.
     */
    public String getPlataforma() {
        return plataforma;
    }

    /**
     * Obtiene el tipo de contenido.
     *
     * @return El tipo de contenido (por ejemplo, "gratuito" o "de pago").
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Devuelve una representación en cadena del resultado de la búsqueda.
     *
     * @return Una cadena con la información del resultado de búsqueda.
     */
    @Override
    public String toString() {
        return "Resultados de la Búsqueda:\n" +
                "  Título: '" + titulo + "'\n" +
                "  Descripción: '" + descripcion + "'\n" +
                "  Enlace: '" + enlace + "'\n" +
                "  Plataforma: '" + plataforma + "'\n" +
                "  Tipo de Pago: '" + tipo + "'";
    }
}
