package facade;

import classes.Usuario;
import service.StreamingServiceManager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fachada de autenticación que implementa la interfaz AuthService para gestionar las operaciones de
 * registro, inicio de sesión y cambio de contraseña de los usuarios.
 * Los usuarios se almacenan y gestionan a través de un archivo de texto.
 */
public class AuthFacade implements AuthService {

    /**
     * Lista de usuarios registrados.
     */
    private List<Usuario> usuarios;

    /**
     * Ruta del archivo donde se almacenan los usuarios.
     */
    private static final String FILE_PATH = "usuarios.txt";

    /**
     * Constructor de la clase AuthFacade. Inicializa la lista de usuarios y carga
     * los datos de los usuarios desde el archivo al iniciar.
     */
    public AuthFacade() {
        usuarios = new ArrayList<>();
        cargarUsuarios(); // Cargar usuarios al iniciar AuthFacade
    }

    /**
     * Inicia sesión de un usuario validando su correo y contraseña.
     *
     * @param correo     Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @return El objeto Usuario si las credenciales son válidas, de lo contrario, null.
     */
    @Override
    public Usuario iniciarSesion(String correo, String contrasena) {
        Usuario usuario = buscarUsuarioPorCorreo(correo);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            return usuario;
        }
        return null;
    }

    /**
     * Registra un nuevo usuario si el correo no está registrado previamente.
     *
     * @param nombre     Nombre del usuario.
     * @param correo     Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @return true si el usuario fue registrado correctamente, false si el correo ya existe.
     */
    @Override
    public boolean registrarUsuario(String nombre, String correo, String contrasena) {
        if (buscarUsuarioPorCorreo(correo) != null) {
            return false;
        }
        int nuevoCodigo = usuarios.size() + 1;
        Usuario nuevoUsuario = new Usuario(nuevoCodigo, nombre, correo, contrasena);
        usuarios.add(nuevoUsuario);
        guardarUsuarios(); // Guardar usuarios al registrar uno nuevo
        return true;
    }

    /**
     * Cambia la contraseña de un usuario existente.
     *
     * @param usuario          El usuario que cambiará su contraseña.
     * @param nuevaContrasena La nueva contraseña del usuario.
     * @return true si el cambio fue exitoso.
     */
    @Override
    public boolean cambiarContrasena(Usuario usuario, String nuevaContrasena) {
        usuario.setContrasena(nuevaContrasena);
        guardarUsuarios(); // Guardar cambios al actualizar la contraseña
        return true;
    }

    /**
     * Guarda los datos de los usuarios en un archivo de texto.
     * Cada usuario se guarda en una línea con formato CSV.
     */
    @Override
    public void guardarUsuarios() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Usuario usuario : usuarios) {
                writer.write(usuario.getCodigo() + "," + usuario.getNombre() + "," +
                        usuario.getCorreo() + "," + usuario.getContrasena());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo de usuarios.");
        }
    }

    /**
     * Carga los datos de los usuarios desde un archivo de texto.
     * Si el archivo no existe, se crea una lista vacía de usuarios.
     */
    private void cargarUsuarios() {
        usuarios.clear(); // Limpiar lista antes de cargar

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");
                if (data.length != 4) continue;

                int codigo = Integer.parseInt(data[0].trim());
                String nombre = data[1].trim();
                String correo = data[2].trim();
                String contrasena = data[3].trim();
                usuarios.add(new Usuario(codigo, nombre, correo, contrasena));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de usuarios no encontrado. Se creará uno nuevo al guardar.");
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de usuarios.");
        }
    }

    /**
     * Busca un usuario en la lista por su correo electrónico.
     *
     * @param correo Correo electrónico del usuario a buscar.
     * @return El objeto Usuario si se encuentra, de lo contrario, null.
     */
    private Usuario buscarUsuarioPorCorreo(String correo) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equals(correo)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Obtiene la instancia del gestor de servicios de streaming.
     *
     * @return La instancia de StreamingServiceManager.
     */
    public StreamingServiceManager getStreamingServiceManager() {
        return StreamingServiceManager.getInstancia();
    }
}
