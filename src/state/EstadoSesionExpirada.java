package state;

/**
 * Implementación del estado "Sesión Expirada" para el contexto de autenticación.
 * Representa el estado en el que la sesión de un usuario ha expirado y requiere
 * que se inicie sesión nuevamente para acceder a los servicios.
 */
public class EstadoSesionExpirada implements EstadoAutenticacion {

    /**
     * Intenta iniciar sesión cambiando al estado "No Autenticado" y delegando
     * la operación de inicio de sesión al nuevo estado.
     *
     * @param contexto   el contexto de autenticación que maneja el estado actual.
     * @param usuario    el nombre de usuario para iniciar sesión.
     * @param contrasena la contraseña correspondiente al usuario.
     */
    @Override
    public void iniciarSesion(ContextoAutenticacion contexto, String usuario, String contrasena) {
        contexto.setEstado(new EstadoNoAutenticado());
        contexto.iniciarSesion(usuario, contrasena);
    }

    /**
     * Intenta acceder a un servicio, pero informa al usuario que la sesión ha expirado.
     *
     * @param contexto el contexto de autenticación que maneja el estado actual.
     */
    @Override
    public void accederServicio(ContextoAutenticacion contexto) {
        System.out.println("La sesión ha expirado, debe iniciar sesión nuevamente.");
    }

    /**
     * Intenta cerrar sesión, pero informa al usuario que no hay una sesión activa
     * debido a que la sesión ya expiró.
     *
     * @param contexto el contexto de autenticación que maneja el estado actual.
     */
    @Override
    public void cerrarSesion(ContextoAutenticacion contexto) {
        System.out.println("Sesión expirada, no hay sesión activa.");
    }
}
