package classes;

/**
 * Clase que representa a un Usuario en el sistema.
 * Esta clase contiene la información básica del usuario,
 * incluyendo su código, nombre, correo y contraseña.
 */
public class Usuario {
    private int codigo;
    private String nombre;
    private String correo;
    private String contrasena;

    /**
     * Constructor para crear un nuevo Usuario.
     *
     * @param codigo el código único del usuario.
     * @param nombre el nombre del usuario.
     * @param correo el correo electrónico del usuario.
     * @param contrasena la contraseña del usuario.
     */
    public Usuario(int codigo, String nombre, String correo, String contrasena) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el código del usuario.
     *
     * @return el código del usuario.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del usuario.
     *
     * @param codigo el nuevo código del usuario.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return el nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre el nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return el correo del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correo el nuevo correo del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contrasena la nueva contraseña del usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Devuelve una representación en forma de cadena del objeto Usuario.
     *
     * @return una cadena que representa al usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}
