package state;

/**
 * Define el contrato para los estados de autenticación en el sistema.
 * Esta interfaz establece los métodos necesarios para manejar el inicio de sesión,
 * el acceso a servicios y el cierre de sesión según el estado actual de autenticación.
 */
public interface EstadoAutenticacion {

    /**
     * Intenta iniciar sesión con las credenciales proporcionadas.
     *
     * @param contexto   el contexto de autenticación que maneja el estado actual.
     * @param usuario    el nombre de usuario proporcionado para iniciar sesión.
     * @param contrasena la contraseña proporcionada para iniciar sesión.
     */
    void iniciarSesion(ContextoAutenticacion contexto, String usuario, String contrasena);

    /**
     * Permite acceder a un servicio si el usuario está autenticado.
     *
     * @param contexto el contexto de autenticación que maneja el estado actual.
     */
    void accederServicio(ContextoAutenticacion contexto);

    /**
     * Cierra la sesión actual y cambia el estado de autenticación a no autenticado.
     *
     * @param contexto el contexto de autenticación que maneja el estado actual.
     */
    void cerrarSesion(ContextoAutenticacion contexto);
}
