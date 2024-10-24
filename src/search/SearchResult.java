package search;

public class SearchResult {
    private String titulo;
    private String descripcion;
    private String enlace;
    private String plataforma;

    public SearchResult(String titulo, String descripcion, String enlace, String plataforma) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.enlace = enlace;
        this.plataforma = plataforma;
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

    @Override
    public String toString() {
        return "Título: " + titulo + "\nDescripción: " + descripcion + "\nEnlace: " + enlace;
    }
}
