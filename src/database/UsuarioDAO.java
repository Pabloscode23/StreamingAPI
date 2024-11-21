package database;

import java.util.ArrayList;
import classes.Usuario;
import java.sql.*;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

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

    public ArrayList<Usuario> listarUsuario(int codigoUsuario) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM Users WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, codigoUsuario);
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
            System.out.println("Error al listar usuario: " + e.getMessage());
        }

        return usuarios;
    }

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

    public boolean iniciarSesion(String correo, String contrasena){
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, correo);
            stmt.setString(2, contrasena);
            rs = stmt.executeQuery();

            return rs.next();
        }catch (Exception e){
            return false;
        }
    }
}
