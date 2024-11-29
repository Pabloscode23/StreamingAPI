package model;

/**
 * Clase que representa un resultado de búsqueda en un catálogo de streaming.
 *
 * Contiene información como el título, descripción, enlace, plataforma y tipo
 * de pago del contenido encontrado. Implementa la interfaz `Cloneable` para
 * permitir la creación de copias del objeto.
 */
public class SearchResult implements Cloneable {

    private String titulo;
    private String descripcion;
    private String enlace;
    private String plataforma;
    private String tipo;

    /**
     * Constructor vacío para la clase `SearchResult`.
     */
    public SearchResult() {
    }

    /**
     * Constructor que inicializa un objeto `SearchResult` con valores específicos.
     *
     * @param titulo     El título del contenido.
     * @param descripcion La descripción del contenido.
     * @param enlace      El enlace al contenido.
     * @param plataforma  La plataforma donde está disponible el contenido.
     * @param tipo        El tipo de pago asociado al contenido (e.g., "free", "buy", "rent").
     */
    public SearchResult(String titulo, String descripcion, String enlace, String plataforma, String tipo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.enlace = enlace;
        this.plataforma = plataforma;
        this.tipo = formatearTipoPago(tipo);
    }

    /**
     * Obtiene el título del contenido.
     *
     * @return El título del contenido.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Obtiene la descripción del contenido.
     *
     * @return La descripción del contenido.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene el enlace al contenido.
     *
     * @return El enlace al contenido.
     */
    public String getEnlace() {
        return enlace;
    }

    /**
     * Obtiene la plataforma donde está disponible el contenido.
     *
     * @return La plataforma donde está disponible el contenido.
     */
    public String getPlataforma() {
        return plataforma;
    }

    /**
     * Obtiene el tipo de pago asociado al contenido.
     *
     * @return El tipo de pago (e.g., "Gratis", "Compra", "Alquiler", "Suscripción").
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Formatea el tipo de pago para presentarlo de manera más amigable al usuario.
     *
     * @param tipo El tipo de pago original (e.g., "free", "buy", "rent").
     * @return Una cadena con el tipo de pago formateado (e.g., "Gratis", "Compra").
     */
    private String formatearTipoPago(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "free" -> "Gratis";
            case "buy" -> "Compra";
            case "rent" -> "Alquiler";
            default -> "Suscripción";
        };
    }

    /**
     * Representa el objeto `SearchResult` como una cadena de texto.
     *
     * Incluye el título, descripción, enlace, plataforma y tipo de pago.
     *
     * @return Una representación en texto del objeto `SearchResult`.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Título: ").append(titulo.isEmpty() ? "No disponible" : titulo).append("\n");
        sb.append("Descripción: ").append(descripcion.isEmpty() ? "Sin descripción disponible" : descripcion).append("\n");
        sb.append("Plataformas: ").append(plataforma.isEmpty() ? "No disponible" : plataforma).append("\n");
        sb.append("Enlace: ").append(enlace.isEmpty() ? "No disponible" : enlace).append("\n");
        sb.append("Tipo de Pago: ").append(tipo.isEmpty() ? "No disponible" : tipo).append("\n");
        sb.append("----------------------------------------");
        return sb.toString();
    }

    /**
     * Método que permite clonar el objeto actual.
     *
     * @return Un nuevo objeto `SearchResult` con los mismos valores.
     * @throws RuntimeException Si ocurre un error al clonar el objeto.
     */
    @Override
    public SearchResult clone() {
        try {
            return (SearchResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar el objeto SearchResult", e);
        }
    }
}
