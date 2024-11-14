package facade;

import classes.Usuario;
import service.StreamingServiceManager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthFacade implements AuthService {

    private List<Usuario> usuarios;
    private static final String FILE_PATH = "usuarios.txt";

    public AuthFacade() {
        usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    @Override
    public Usuario iniciarSesion(String correo, String contrasena) {
        Usuario usuario = obtenerUsuarioPorCorreo(correo);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            return usuario;
        }
        return null;
    }

    @Override
    public boolean registrarUsuario(String nombre, String correo, String contrasena) {
        if (obtenerUsuarioPorCorreo(correo) != null) {
            return false;
        }
        int nuevoCodigo = usuarios.size() + 1;
        Usuario nuevoUsuario = new Usuario(nuevoCodigo, nombre, correo, contrasena);
        usuarios.add(nuevoUsuario);
        guardarUsuarios();
        return true;
    }

    @Override
    public boolean cambiarContrasena(Usuario usuario, String nuevaContrasena) {
        usuario.setContrasena(nuevaContrasena);
        guardarUsuarios();
        return true;
    }

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

    private void cargarUsuarios() {
        usuarios.clear();
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

    public Usuario obtenerUsuarioPorCorreo(String correo) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equals(correo)) {
                return usuario;
            }
        }
        return null;
    }

    public StreamingServiceManager getStreamingServiceManager() {
        return StreamingServiceManager.getInstancia();
    }
}
