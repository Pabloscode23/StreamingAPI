package facade;

import classes.Usuario;
import service.StreamingServiceManager;

/**
 * Interfaz de servicio de autenticación que define las operaciones de gestión de usuarios.
 */
public interface AuthService {

    /**
     * Inicia sesión de un usuario validando su correo y contraseña.
     *
     * @param correo     Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @return El objeto Usuario si las credenciales son válidas, de lo contrario, null.
     */
    Usuario iniciarSesion(String correo, String contrasena);

    /**
     * Registra un nuevo usuario si el correo no está registrado previamente.
     *
     * @param nombre     Nombre del usuario.
     * @param correo     Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @return true si el usuario fue registrado correctamente, false si el correo ya existe.
     */
    boolean registrarUsuario(String nombre, String correo, String contrasena);

    /**
     * Cambia la contraseña de un usuario existente.
     *
     * @param usuario          El usuario que cambiará su contraseña.
     * @param nuevaContrasena La nueva contraseña del usuario.
     * @return true si el cambio fue exitoso.
     */
    boolean cambiarContrasena(Usuario usuario, String nuevaContrasena);

    /**
     * Guarda los datos de los usuarios en un archivo.
     * Este método persiste la lista de usuarios.
     */
    void guardarUsuarios();

    /**
     * Obtiene la instancia del gestor de servicios de streaming.
     *
     * @return La instancia de StreamingServiceManager.
     */
    StreamingServiceManager getStreamingServiceManager();
}
