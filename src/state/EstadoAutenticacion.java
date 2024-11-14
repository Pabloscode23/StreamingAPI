package state;
// EstadoAutenticacion.java

public interface EstadoAutenticacion {
    void iniciarSesion(ContextoAutenticacion contexto, String usuario, String contrasena);
    void accederServicio(ContextoAutenticacion contexto);
    void cerrarSesion(ContextoAutenticacion contexto);
}