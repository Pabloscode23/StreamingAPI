package state;

/**
 * La clase representa el estado en el que un usuario no está autenticado
 * Implementa la interfaz {@code EstadoAutenticacion} y proporciona métodos para iniciar sesión,
 * acceder a servicios y cerrar sesión.
 */
public class EstadoNoAutenticado implements EstadoAutenticacion {

    /**
     * Intenta iniciar sesión utilizando las credenciales proporcionadas.
     * Si las credenciales son correctas, genera un token, actualiza el estado de autenticación y asigna el token.
     *
     * @param contexto el contexto de autenticación que mantiene el estado y el token
     * @param usuario el nombre de usuario proporcionado para iniciar sesión
     * @param contrasena la contraseña proporcionada para iniciar sesión
     */
    @Override
    public void iniciarSesion(ContextoAutenticacion contexto, String usuario, String contrasena) {
        // Supongamos que validamos las credenciales y generamos un token.
        String token = generarToken(usuario, contrasena);
        if (token != null) {
            contexto.setToken(token);
            contexto.setEstado(new EstadoAutenticado());
            System.out.println("Sesión iniciada exitosamente.");
        } else {
            System.out.println("Error: Credenciales incorrectas.");
        }
    }

    /**
     * Muestra un mensaje indicando que el usuario debe iniciar sesión para acceder a los servicios.
     *
     * @param contexto el contexto de autenticación que mantiene el estado y el token
     */
    @Override
    public void accederServicio(ContextoAutenticacion contexto) {
        System.out.println("Debe iniciar sesión para acceder a los servicios.");
    }

    /**
     * Muestra un mensaje indicando que no hay ninguna sesión activa para cerrar.
     *
     * @param contexto el contexto de autenticación que mantiene el estado y el token
     */
    @Override
    public void cerrarSesion(ContextoAutenticacion contexto) {
        System.out.println("No hay sesión activa.");
    }

    /**
     * Método simulado para generar un token utilizando las credenciales proporcionadas.
     * Este método debe reemplazarse con un generador de token real en un caso de uso completo.
     *
     * @param usuario el nombre de usuario proporcionado para iniciar sesión
     * @param contrasena la contraseña proporcionada para iniciar sesión
     * @return un token de autenticación si las credenciales son válidas, {@code null} si no lo son
     */
    private String generarToken(String usuario, String contrasena) {
        // Método simulado para generar un token.
        return "token123"; // Generar un token real en un caso de uso completo
    }
}

