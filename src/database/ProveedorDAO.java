package database;

import java.util.ArrayList;
import classes.Proveedor;
import java.sql.*;

public class ProveedorDAO {
    private Connection conn;

    public ProveedorDAO() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public boolean insertarProveedor(Proveedor proveedor) {
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO Providers (name, url) VALUES (?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getUrl());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar proveedor: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Proveedor> listarProveedores() {
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM Providers";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setCodigo(rs.getInt("id"));
                proveedor.setNombre(rs.getString("name"));
                proveedor.setUrl(rs.getString("url"));

                proveedores.add(proveedor);
            }

        } catch (Exception e) {
            System.out.println("Error al listar proveedores: " + e.getMessage());
        }

        return proveedores;
    }

    public boolean actualizarProveedor(Proveedor proveedor) {
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE Proveedor SET name = ?, url = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getUrl());
            stmt.setInt(3, proveedor.getCodigo());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProveedor(int codigo) {
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM Providers WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, codigo);
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }
}
