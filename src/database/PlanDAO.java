package database;

import java.util.ArrayList;
import classes.Plan;
import java.sql.*;

/**
 * Clase PlanDAO que proporciona métodos para interactuar con la base de datos
 */
public class PlanDAO {
    private Connection conn;

    /**
     * Constructor de PlanDAO.
     * Inicializa la conexión a la base de datos.
     * @throws SQLException si ocurre un error al obtener la conexión de la base de datos.
     */
    public PlanDAO() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Inserta un nuevo plan en la base de datos.
     * @param plan que contiene los datos del plan a insertar.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
    public boolean insertarPlan(Plan plan) {
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO Plans (name, price) VALUES (?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, plan.getNombre());
            stmt.setFloat(2, plan.getPrecio());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar plan: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los planes almacenados en la base de datos.
     * @return Lista de objetos que representan los planes encontrados.
     */
    public ArrayList<Plan> listarPlanes() {
        ArrayList<Plan> planes = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM Plans";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Plan plan = new Plan();
                plan.setCodigo(rs.getInt("id"));
                plan.setNombre(rs.getString("name"));
                plan.setPrecio(rs.getFloat("price"));

                planes.add(plan);
            }

        } catch (Exception e) {
            System.out.println("Error al listar planes: " + e.getMessage());
        }

        return planes;
    }

    /**
     * Actualiza el precio de un plan existente en la base de datos.
     * @param plan que contiene los nuevos datos del plan.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
    public boolean actualizarPlan(Plan plan) {
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE Plans SET price = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setFloat(1, plan.getPrecio());
            stmt.setInt(2, plan.getCodigo());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar plan: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un plan de la base de datos.
     * @param codigo ID del plan que se desea eliminar.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
    public boolean eliminarPlan(int codigo) {
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM Plans WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, codigo);
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar plan: " + e.getMessage());
            return false;
        }
    }
}
