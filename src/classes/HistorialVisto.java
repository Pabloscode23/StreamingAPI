package classes;

/**
 * Clase que representa el historial de contenido visto por el usuario.
 * Esta clase almacena información sobre las películas y series vistas por el usuario,
 * incluyendo códigos relevantes para el usuario, el proveedor, la película y la serie.
 */
public class HistorialVisto {
    private int codigo;
    private int codigoUsuario;
    private int codigoProveedor;
    private int codigoPelicula;
    private int codigoSerie;

    /**
     * Constructor Vacio
     */
    public HistorialVisto() {
    }

    /**
     * Constructor para crear un nuevo historial de contenido visto.
     *
     * @param codigo el código único del historial.
     * @param codigoUsuario el código del usuario que ha visto el contenido.
     * @param codigoProveedor el código del proveedor del contenido.
     * @param codigoPelicula el código de la película vista.
     * @param codigoSerie el código de la serie vista.
     */
    public HistorialVisto(int codigo, int codigoUsuario, int codigoProveedor, int codigoPelicula, int codigoSerie) {
        this.codigo = codigo;
        this.codigoUsuario = codigoUsuario;
        this.codigoProveedor = codigoProveedor;
        this.codigoPelicula = codigoPelicula;
        this.codigoSerie = codigoSerie;
    }

    /**
     * Obtiene el código del historial.
     *
     * @return el código del historial.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del historial.
     *
     * @param codigo el nuevo código del historial.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el código del usuario que ha visto el contenido.
     *
     * @return el código del usuario.
     */
    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    /**
     * Establece el código del usuario que ha visto el contenido.
     *
     * @param codigoUsuario el nuevo código del usuario.
     */
    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    /**
     * Obtiene el código del proveedor del contenido.
     *
     * @return el código del proveedor.
     */
    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    /**
     * Establece el código del proveedor del contenido.
     *
     * @param codigoProveedor el nuevo código del proveedor.
     */
    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    /**
     * Obtiene el código de la película vista.
     *
     * @return el código de la película.
     */
    public int getCodigoPelicula() {
        return codigoPelicula;
    }

    /**
     * Establece el código de la película vista.
     *
     * @param codigoPelicula el nuevo código de la película.
     */
    public void setCodigoPelicula(int codigoPelicula) {
        this.codigoPelicula = codigoPelicula;
    }

    /**
     * Obtiene el código de la serie vista.
     *
     * @return el código de la serie.
     */
    public int getCodigoSerie() {
        return codigoSerie;
    }

    /**
     * Establece el código de la serie vista.
     *
     * @param codigoSerie el nuevo código de la serie.
     */
    public void setCodigoSerie(int codigoSerie) {
        this.codigoSerie = codigoSerie;
    }

    /**
     * Devuelve una representación en forma de cadena del objeto HistorialVisto.
     *
     * @return una cadena que representa el historial de contenido visto.
     */
    @Override
    public String toString() {
        return "HistorialVisto{" +
                "codigo=" + codigo +
                ", codigoUsuario=" + codigoUsuario +
                ", codigoProveedor=" + codigoProveedor +
                ", codigoPelicula=" + codigoPelicula +
                ", codigoSerie=" + codigoSerie +
                '}';
    }
}
