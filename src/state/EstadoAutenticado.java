package state;

public class EstadoAutenticado implements EstadoAutenticacion {

    @Override
    public void iniciarSesion(ContextoAutenticacion contexto, String usuario, String contrasena) {
        System.out.println("Ya hay una sesión activa.");
    }

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

    @Override
    public void cerrarSesion(ContextoAutenticacion contexto) {
        contexto.setEstado(new EstadoNoAutenticado());
        contexto.setToken(null);
        System.out.println("Sesión cerrada.");
    }

    private boolean verificarToken(String token) {
        // Simular verificación del token (en la práctica, realizar una verificación real)
        return token.equals("token123");
    }
}