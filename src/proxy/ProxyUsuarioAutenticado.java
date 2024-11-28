package proxy;

import classes.Usuario;
import facade.AuthFacade;

public class ProxyUsuarioAutenticado implements UsuarioAutenticado {
    private UsuarioAutenticadoReal usuarioAutenticadoReal;
    private String correo;
    private String contrasena;
    private boolean estaAutenticado;
    private AuthFacade authFacade; // Mantener AuthFacade

    public ProxyUsuarioAutenticado(String correo, String contrasena, AuthFacade authFacade) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.estaAutenticado = false;
        this.authFacade = authFacade;  // Usamos el AuthFacade
    }

    @Override
    public boolean iniciarSesion(String correo, String contrasena) {
        if (!estaAutenticado) {
            // Delegar la autenticación a AuthFacade a través del proxy
            Usuario usuario = authFacade.iniciarSesion(correo, contrasena);
            if (usuario != null) {
                this.usuarioAutenticadoReal = new UsuarioAutenticadoReal(correo, contrasena);
                estaAutenticado = true;
            }
        } else {
            System.out.println("Ya estás autenticado.");
        }
        return estaAutenticado;
    }

    @Override
    public Usuario obtenerUsuario() {
        if (estaAutenticado) {
            return usuarioAutenticadoReal.obtenerUsuario();
        } else {
            System.out.println("No se ha iniciado sesión.");
            return null;
        }
    }
}
