package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un usuario con sus datos personales y suscripciones.
 */
public class Usuario {
    /**
     * Código único que identifica al usuario.
     */
    private int codigo;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Correo electrónico del usuario.
     */
    private String correo;

    /**
     * Contraseña del usuario.
     */
    private String contrasena;

    /**
     * Lista de suscripciones del usuario.
     */
    private List<Suscripcion> suscripciones;

    /**
     * Constructor Vacio
     */
    public Usuario() {
    }
    public Usuario(String correo, String nombre) {
        this.correo = correo;
        this.nombre = nombre;
    }

    /**
     * Constructor para crear un nuevo usuario con su código, nombre, correo y contraseña.
     *
     * @param codigo     Código único del usuario.
     * @param nombre     Nombre del usuario.
     * @param correo     Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     */
    public Usuario(int codigo, String nombre, String correo, String contrasena) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.suscripciones = new ArrayList<>();
    }

    /**
     * Obtiene el código del usuario.
     *
     * @return Código único del usuario.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return Correo electrónico del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return Contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Obtiene la lista de suscripciones del usuario.
     *
     * @return Lista de suscripciones del usuario.
     */
    public List<Suscripcion> getSuscripciones() {
        return suscripciones;
    }

    /**
     * Establece un codigo para el usuario.
     *
     * @param codigo Codigo del usuario.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece un nuevo nombre para el usuario.
     *
     * @param nombre Nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece una nuevo correo para el usuario.
     *
     * @param correo Nuevo correo del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Establece una nueva contraseña para el usuario.
     *
     * @param nuevaContrasena Nueva contraseña del usuario.
     */
    public void setContrasena(String nuevaContrasena) {
        this.contrasena = nuevaContrasena;
    }

    /**
     * Agrega una nueva suscripción a la lista de suscripciones del usuario.
     *
     * @param suscripcion La suscripción que se agregará al usuario.
     */
    public void agregarSuscripcion(Suscripcion suscripcion) {
        suscripciones.add(suscripcion);
    }

    /**
     * Elimina una suscripción de la lista de suscripciones del usuario.
     *
     * @param suscripcion La suscripción que se eliminará del usuario.
     */
    public void eliminarSuscripcion(Suscripcion suscripcion) {
        suscripciones.remove(suscripcion);
    }

    /**
     * Representación en formato de cadena del usuario, mostrando el nombre y correo.
     *
     * @return Cadena de texto con el nombre y correo del usuario.
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nCorreo: " + correo;
    }
}
