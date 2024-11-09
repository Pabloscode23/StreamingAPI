package model;

/**
 * La clase SearchResult representa un resultado de búsqueda en un servicio de streaming.
 *
 * Contiene información relevante acerca del título, la descripción, el enlace y la plataforma
 * en la que se encuentra el contenido encontrado.
 *
 * Esta clase no emplea un patrón de diseño específico, pero es una clase sencilla que encapsula
 * los datos de un resultado de búsqueda.
 */
public class SearchResult {
    private String titulo;
    private String descripcion;
    private String enlace;
    private String plataforma;

    /**
     * Constructor por defecto de SearchResult. Inicializa el objeto sin parámetros.
     */
    public SearchResult() {
    }

    /**
     * Constructor que inicializa un resultado de búsqueda con los datos proporcionados.
     *
     * @param titulo El título del contenido encontrado.
     * @param descripcion La descripción del contenido.
     * @param enlace El enlace hacia el contenido.
     * @param plataforma La plataforma donde se encuentra el contenido.
     */
    public SearchResult(String titulo, String descripcion, String enlace, String plataforma) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.enlace = enlace;
        this.plataforma = plataforma;
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
                "  Plataforma: '" + plataforma + "'";
    }
}
