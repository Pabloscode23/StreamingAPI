package database;

import java.util.ArrayList;
import classes.Plan;
import java.sql.*;

public class PlanDAO {
    private Connection conn;

    public PlanDAO() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

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

    public boolean actualizarPlan(Plan plan) {
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE Planes SET price = ? WHERE id = ?";
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
