package classes;

/**
 * Clase que representa una serie en el sistema.
 * Esta clase contiene la información básica de una serie,
 * incluyendo su código y nombre.
 */
public class Serie {
    private int codigo;
    private String nombre;

    /**
     * Constructor para crear una nueva serie.
     * @param codigo el código único de la serie.
     * @param nombre el nombre de la serie.
     */
    public Serie(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    /**
     * Obtiene el código de la serie.
     *
     * @return el código de la serie.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre de la serie.
     *
     * @return el nombre de la serie.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el código de la serie.
     *
     * @param codigo el nuevo código de la serie.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece el nombre de la serie.
     *
     * @param nombre el nuevo nombre de la serie.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve una representación en forma de cadena del objeto Serie.
     *
     * @return una cadena que representa a una Serie.
     */
    @Override
    public String toString() {
        return "Serie{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}