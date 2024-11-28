package model;

public class SearchResult implements Cloneable{

    private String titulo;
    private String descripcion;
    private String enlace;
    private String plataforma;
    private String tipo;

    public SearchResult() {
    }

    public SearchResult(String titulo, String descripcion, String enlace, String plataforma, String tipo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.enlace = enlace;
        this.plataforma = plataforma;
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEnlace() {
        return enlace;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Resultados de la Búsqueda:\n" +
                "  Título: '" + titulo + "'\n" +
                "  Descripción: '" + descripcion + "'\n" +
                "  Enlace: '" + enlace + "'\n" +
                "  Plataforma: '" + plataforma + "'\n" +
                "  Tipo de Pago: '" + tipo + "'";
    }

    /**
     * Método que permite clonar el objeto actual.
     *
     * @return Un nuevo objeto SearchResult con los mismos valores.
     * @throws CloneNotSupportedException Si no se puede clonar el objeto.
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
