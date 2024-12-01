/**
 * Define los métodos básicos para la autenticación de usuarios
 * y la obtención de información del usuario autenticado.
 */
package proxy;

import classes.Usuario;

/**
 * Proporciona la estructura para clases encargadas de autenticar usuarios
 * y manejar su información.
 */
public interface UsuarioAutenticado {

    /**
     * Verifica las credenciales del usuario para iniciar sesión.
     *
     * @param correo    El correo electrónico del usuario que intenta iniciar sesión.
     * @param contrasena La contraseña del usuario que intenta iniciar sesión.
     * @return true si las credenciales son correctas, false de lo contrario.
     */
    boolean iniciarSesion(String correo, String contrasena);

    /**
     * Obtiene la información del usuario autenticado.
     *
     * @return Un objeto {@link Usuario} que contiene los datos del usuario autenticado.
     */
    Usuario obtenerUsuario();
}
