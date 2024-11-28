package proxy;
import classes.Usuario;

import java.io.*;

public class UsuarioAutenticadoReal implements UsuarioAutenticado {
    private String correo;
    private String contrasena;
    private Usuario usuario;

    // Ruta del archivo de usuarios
    private static final String RUTA_USUARIOS = "usuarios.txt";

    public UsuarioAutenticadoReal(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;

        // Intentamos obtener el nombre del usuario desde el archivo
        String nombreUsuario = obtenerNombreUsuario(correo);
            this.usuario = new Usuario(correo, nombreUsuario);
    }

    @Override
    public boolean iniciarSesion(String correo, String contrasena) {
        // Verificamos si las credenciales coinciden
        return this.correo.equals(correo) && this.contrasena.equals(contrasena);
    }

    @Override
    public Usuario obtenerUsuario() {
        return usuario;
    }

    // Método para obtener el nombre del usuario desde el archivo
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
