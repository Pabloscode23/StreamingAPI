package proxy;

import classes.Usuario;

public interface UsuarioAutenticado {
    boolean iniciarSesion(String correo, String contrasena);
    Usuario obtenerUsuario();
}
