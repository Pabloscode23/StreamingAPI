package database;

import java.util.ArrayList;
import classes.Proveedor;
import java.sql.*;

/**
 * Clase ProveedorDAO que proporciona métodos para interactuar con la base de datos
 */
public class ProveedorDAO {
    private Connection conn;

    /**
     * Constructor de ProveedorDAO.
     * Inicializa la conexión a la base de datos.
     * @throws SQLException si ocurre un error al obtener la conexión de la base de datos.
     */
    public ProveedorDAO() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Inserta un nuevo proveedor en la base de datos.
     * @param proveedor que contiene los datos del proveedor a insertar.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
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

    /**
     * Obtiene una lista de todos los proveedores almacenados en la base de datos.
     * @return Lista de objetos que representan los proveedores encontrados.
     */
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

    /**
     * Actualiza los datos de un proveedor existente en la base de datos.
     * @param proveedor Objeto que contiene los nuevos datos del proveedor.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
    public boolean actualizarProveedor(Proveedor proveedor) {
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE Providers SET name = ?, url = ? WHERE id = ?";
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

    /**
     * Elimina un proveedor de la base de datos.
     * @param codigo ID del proveedor que se desea eliminar.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
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
