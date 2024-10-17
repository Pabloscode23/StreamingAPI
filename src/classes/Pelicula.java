package classes;

/**
 * Clase que representa una pelicula en el sistema.
 * Esta clase contiene la información básica de pelicula,
 * incluyendo su código y nombre.
 */
public class Pelicula {
    private int codigo;
    private String nombre;

    /**
     * Constructor para crear una nueva pelicula.
     * @param codigo el código único de la pelicula.
     * @param nombre el nombre de la pelicula.
     */
    public Pelicula(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    /**
     * Obtiene el codigo pelicula
     *
     * @return el codigo pelicula
     */
    public int getCodigo() {
        return codigo;
    }
    /**
     * Obtiene el nombre pelicula
     *
     * @return el nombre pelicula
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Establece el codigo pelicula
     *
     * @param codigo el nuevo codigo pelicula
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    /**
     * Establece el nombresuario.
     *
     * @param nombre el nuevo nombre de la pelicula
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Devuelve una representación en forma de cadena del objeto Pelicula
     *
     * @return una cadena que representa a una Pelicula
     */
    @Override
    public String toString() {
        return "Pelicula{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}