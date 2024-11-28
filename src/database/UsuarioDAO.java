package database;

import java.util.ArrayList;
import classes.Usuario;
import java.sql.*;

/**
 * Clase UsuarioDAO que proporciona métodos para interactuar con la base de datos
 */
public class UsuarioDAO {
    private Connection conn;

    /**
     * Constructor de UsuarioDAO.
     * Inicializa la conexión a la base de datos.
     * @throws SQLException si ocurre un error al obtener la conexión de la base de datos.
     */
    public UsuarioDAO() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     * @param usuario Objeto que contiene los datos del usuario a insertar.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
    public boolean insertarUsuario(Usuario usuario) {
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO Users (name, email, password) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContrasena());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los usuarios almacenados en la base de datos.
     * @return Lista de objetos que representan los usuarios encontrados.
     */
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM Users";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setCodigo(rs.getInt("id"));
                usuario.setNombre(rs.getString("name"));
                usuario.setCorreo(rs.getString("email"));
                usuario.setContrasena(rs.getString("password"));

                usuarios.add(usuario);
            }

        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    /**
     * Obtiene un usuario específico según su ID.
     * @param codigoUsuario ID del usuario que se desea obtener.
     * @return Lista de objetos que representa el usuario encontrado.
     */
    public ArrayList<Usuario> listarUsuario(int codigoUsuario) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM Users WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, codigoUsuario);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setCodigo(rs.getInt("id"));
                usuario.setNombre(rs.getString("name"));
                usuario.setCorreo(rs.getString("email"));
                usuario.setContrasena(rs.getString("password"));

                usuarios.add(usuario);
            }

        } catch (Exception e) {
            System.out.println("Error al listar usuario: " + e.getMessage());
        }

        return usuarios;
    }

    /**
     * Actualiza la información de un usuario existente en la base de datos.
     * @param usuario Objeto con los datos actualizados.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
    public boolean actualizarUsuario(Usuario usuario) {
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE Users SET name = ?, email = ?, password = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContrasena());
            stmt.setInt(4, usuario.getCodigo());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un usuario de la base de datos según su ID.
     * @param codigo ID del usuario que se desea eliminar.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
    public boolean eliminarUsuario(int codigo) {
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM Users WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, codigo);
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica las credenciales de un usuario para iniciar sesión.
     * @param correo Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @return true si las credenciales son correctas, false en caso contrario o si ocurre un error.
     */
    public boolean iniciarSesion(String correo, String contrasena) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, correo);
            stmt.setString(2, contrasena);
            rs = stmt.executeQuery();

            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }
}
