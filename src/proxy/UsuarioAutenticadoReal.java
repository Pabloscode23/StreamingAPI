/**
 * Clase que implementa la interfaz UsuarioAutenticado, encargada de autenticar
 * usuarios y proporcionar su información. Utiliza un archivo txt como base
 * de datos para almacenar y recuperar la información de los usuarios.
 */
package proxy;

import classes.Usuario;

import java.io.*;

/**
 * Implementación real de la interfaz UsuarioAutenticado.
 * Se encarga de manejar la autenticación y la recuperación de datos de usuario.
 */
public class UsuarioAutenticadoReal implements UsuarioAutenticado {

    /** Correo electrónico del usuario. */
    private String correo;

    /** Contraseña del usuario. */
    private String contrasena;

    /** Objeto Usuario que almacena la información del usuario autenticado. */
    private Usuario usuario;

    /** Ruta al archivo de texto que contiene la información de los usuarios. */
    private static final String RUTA_USUARIOS = "usuarios.txt";

    /**
     * Constructor de UsuarioAutenticadoReal.
     * Inicializa los datos del usuario y los carga desde un archivo.
     *
     * @param correo    El correo electrónico del usuario.
     * @param contrasena La contraseña del usuario.
     */
    public UsuarioAutenticadoReal(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;

        // Intentamos obtener el nombre del usuario desde el archivo
        String nombreUsuario = obtenerNombreUsuario(correo);
        this.usuario = new Usuario(correo, nombreUsuario);
    }

    /**
     * Verifica las credenciales de inicio de sesión del usuario.
     *
     * @param correo    El correo electrónico proporcionado para iniciar sesión.
     * @param contrasena La contraseña proporcionada para iniciar sesión.
     * @return true si las credenciales coinciden, false de lo contrario.
     */
    @Override
    public boolean iniciarSesion(String correo, String contrasena) {
        // Verificamos si las credenciales coinciden
        return this.correo.equals(correo) && this.contrasena.equals(contrasena);
    }

    /**
     * Obtiene el objeto Usuario asociado con el usuario autenticado.
     *
     * @return El objeto Usuario que contiene la información del usuario.
     */
    @Override
    public Usuario obtenerUsuario() {
        return usuario;
    }

    /**
     * Busca el nombre del usuario en el archivo de texto con base en su correo electrónico.
     *
     * @param correo El correo electrónico del usuario que se está buscando.
     * @return El nombre del usuario si se encuentra, o null si no se encuentra.
     */
    private String obtenerNombreUsuario(String correo) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_USUARIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Separamos la línea por comas
                String[] datos = linea.split(",");
                // Verificamos si el correo coincide con el de la línea
                if (datos.length == 4 && datos[2].equals(correo)) {
                    return datos[1]; // Retorna el nombre del usuario
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios.");
        }
        return null; // Si no se encuentra el usuario
    }
}
