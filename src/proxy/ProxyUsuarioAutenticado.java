/**
 * Clase Proxy que implementa la interfaz UsuarioAutenticado.
 * Controla el acceso a la autenticación real del usuario y utiliza una fachada (AuthFacade)
 * para delegar la autenticación inicial.
 */
package proxy;

import classes.Usuario;
import facade.AuthFacade;

/**
 * Proxy que controla el acceso a la autenticación de usuarios.
 * Utiliza AuthFacade para autenticar al usuario y delega operaciones posteriores
 * a una instancia de UsuarioAutenticadoReal una vez autenticado.
 */
public class ProxyUsuarioAutenticado implements UsuarioAutenticado {

    /** Instancia real del usuario autenticado. */
    private UsuarioAutenticadoReal usuarioAutenticadoReal;

    /** Correo electrónico del usuario. */
    private String correo;

    /** Contraseña del usuario. */
    private String contrasena;

    /** Indica si el usuario está autenticado. */
    private boolean estaAutenticado;

    /** Instancia de AuthFacade para delegar la autenticación inicial. */
    private AuthFacade authFacade;

    /**
     * Constructor de ProxyUsuarioAutenticado.
     * Inicializa los datos de acceso del usuario y configura la instancia de AuthFacade.
     *
     * @param correo    El correo electrónico del usuario.
     * @param contrasena La contraseña del usuario.
     * @param authFacade La instancia de AuthFacade utilizada para autenticar al usuario.
     */
    public ProxyUsuarioAutenticado(String correo, String contrasena, AuthFacade authFacade) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.estaAutenticado = false;
        this.authFacade = authFacade;
    }

    /**
     * Inicia sesión verificando las credenciales del usuario.
     * Si no está autenticado, delega la autenticación a AuthFacade y crea
     * una instancia de UsuarioAutenticadoReal si la autenticación es exitosa.
     *
     * @param correo    El correo electrónico proporcionado para iniciar sesión.
     * @param contrasena La contraseña proporcionada para iniciar sesión.
     * @return true si el usuario fue autenticado correctamente, false de lo contrario.
     */
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

    /**
     * Obtiene la información del usuario autenticado.
     * Si el usuario no está autenticado, muestra un mensaje y retorna null.
     *
     * @return El objeto {@link Usuario} del usuario autenticado o null si no se ha iniciado sesión.
     */
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
