package state;

import java.time.LocalDateTime;
import java.time.Duration;
import classes.Usuario;

/**
 * Esta clase representa el contexto de autenticación de un usuario.
 * Gestiona el estado de autenticación, el token de sesión, y el usuario autenticado.
 * Permite realizar operaciones como iniciar sesión, acceder a un servicio, cerrar sesión, y verificar la expiración de la sesión.
 */
public class ContextoAutenticacion {
    private EstadoAutenticacion estadoActual;
    private Usuario usuarioAutenticado;
    private String token;
    private LocalDateTime tiempoAutenticacion; // Hora de autenticación para verificar expiración

    /**
     * Constructor de la clase ContextoAutenticacion. Inicializa el estado de autenticación a no autenticado.
     */
    public ContextoAutenticacion() {
        this.estadoActual = new EstadoNoAutenticado();
    }

    /**
     * Establece el nuevo estado de autenticación del contexto.
     *
     * @param nuevoEstado El nuevo estado de autenticación que se establecerá.
     */
    public void setEstado(EstadoAutenticacion nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    /**
     * Obtiene el estado actual de autenticación.
     *
     * @return El estado actual de autenticación.
     */
    public EstadoAutenticacion getEstado() {
        return estadoActual;
    }

    /**
     * Obtiene el token de autenticación actual.
     *
     * @return El token de autenticación.
     */
    public String getToken() {
        return token;
    }

    /**
     * Establece el token de autenticación y registra la hora de autenticación.
     *
     * @param token El nuevo token de autenticación.
     */
    public void setToken(String token) {
        this.token = token;
        this.tiempoAutenticacion = LocalDateTime.now(); // Registrar tiempo de autenticación al establecer token
    }

    /**
     * Obtiene el usuario autenticado actualmente.
     *
     * @return El usuario autenticado.
     */
    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    /**
     * Establece el usuario autenticado.
     *
     * @param usuario El usuario autenticado que se establecerá.
     */
    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuarioAutenticado = usuario;
    }

    /**
     * Inicia sesión con el nombre de usuario y la contraseña proporcionados.
     * Delegará la autenticación al estado actual de autenticación.
     *
     * @param usuario El nombre de usuario.
     * @param contrasena La contraseña del usuario.
     */
    public void iniciarSesion(String usuario, String contrasena) {
        estadoActual.iniciarSesion(this, usuario, contrasena);
    }

    /**
     * Permite al usuario acceder a un servicio, delegando la acción al estado actual de autenticación.
     */
    public void accederServicio() {
        estadoActual.accederServicio(this);
    }

    /**
     * Cierra la sesión del usuario actual, delegando la acción al estado actual de autenticación.
     */
    public void cerrarSesion() {
        estadoActual.cerrarSesion(this);
    }

    /**
     * Verifica si hay una sesión activa.
     * Una sesión está activa si el estado actual es de autenticación y la sesión no ha expirado.
     *
     * @return true si la sesión está activa, false si no lo está.
     */
    public boolean haySesionActiva() {
        return estadoActual instanceof EstadoAutenticado && !isSesionExpirada();
    }

    /**
     * Verifica si la sesión ha expirado. La sesión expira después de 5 minutos de inactividad.
     *
     * @return true si la sesión ha expirado, false si no lo ha hecho.
     */
    public boolean isSesionExpirada() {
        if (tiempoAutenticacion == null) return true;
        Duration duracion = Duration.between(tiempoAutenticacion, LocalDateTime.now());
        return duracion.toMinutes() > 5; // Expiración de sesión en 5 minutos
    }
}
