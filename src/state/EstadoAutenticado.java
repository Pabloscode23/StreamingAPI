package state;

/**
 * Implementa el estado de autenticación cuando un usuario ha iniciado sesión correctamente.
 * Esta clase representa el estado autenticado en el contexto de un sistema de autenticación,
 * permitiendo acceder a los servicios y gestionar el cierre de sesión.
 */
public class EstadoAutenticado implements EstadoAutenticacion {

    /**
     * Intenta iniciar sesión, pero no realiza ninguna acción ya que el usuario ya está autenticado.
     *
     * @param contexto   el contexto de autenticación que maneja el estado actual.
     * @param usuario    el nombre de usuario del intento de inicio de sesión.
     * @param contrasena la contraseña del intento de inicio de sesión.
     */
    @Override
    public void iniciarSesion(ContextoAutenticacion contexto, String usuario, String contrasena) {
        System.out.println("Ya hay una sesión activa.");
    }

    /**
     * Permite acceder al servicio si la sesión está activa y no ha expirado.
     * Si la sesión ha expirado, cierra la sesión actual y solicita un nuevo inicio de sesión.
     *
     * @param contexto el contexto de autenticación que maneja el estado actual.
     */
    @Override
    public void accederServicio(ContextoAutenticacion contexto) {
        if (contexto.isSesionExpirada()) {
            System.out.println("Sesión expirada, debe iniciar sesión nuevamente.");
            contexto.cerrarSesion();
        } else {
            System.out.println("Acceso al servicio permitido.");
            // Lógica adicional del servicio
        }
    }

    /**
     * Cierra la sesión actual, cambia el estado a no autenticado y elimina el token de autenticación.
     *
     * @param contexto el contexto de autenticación que maneja el estado actual.
     */
    @Override
    public void cerrarSesion(ContextoAutenticacion contexto) {
        contexto.setEstado(new EstadoNoAutenticado());
        contexto.setToken(null);
        System.out.println("Sesión cerrada.");
    }

    /**
     * Verifica la validez de un token de autenticación.
     * 
     * @param token el token de autenticación a verificar.
     * @return true si el token es válido; false en caso contrario.
     */
    private boolean verificarToken(String token) {
        // Simular verificación del token (en la práctica, realizar una verificación real)
        return token.equals("token123");
    }
}
