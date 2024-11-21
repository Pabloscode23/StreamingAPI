package database;

import classes.Suscripcion;
import java.util.ArrayList;
import java.sql.*;

public class SuscripcionDAO {
    private Connection conn;

    public SuscripcionDAO() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public boolean insertarSuscripcion(Suscripcion suscripcion) {
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO Subscriptions (id_user, id_provider, id_plan, months, active) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, suscripcion.getCodigoUsuario());
            stmt.setInt(2, suscripcion.getCodigoProveedor());
            stmt.setInt(3, suscripcion.getCodigoPlan());
            stmt.setInt(4, suscripcion.getMeses());
            stmt.setBoolean(5, suscripcion.isActivo());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar suscripcion: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Suscripcion> listarSuscripciones() {
        ArrayList<Suscripcion> suscripciones = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM Subscriptions";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Suscripcion suscripcion = new Suscripcion();
                suscripcion.setCodigo(rs.getInt("id"));
                suscripcion.setCodigoUsuario(rs.getInt("id_user"));
                suscripcion.setCodigoProveedor(rs.getInt("id_provider"));
                suscripcion.setCodigoPlan(rs.getInt("id_plan"));
                suscripcion.setMeses(rs.getInt("months"));
                suscripcion.setActivo(rs.getBoolean("active"));

                suscripciones.add(suscripcion);
            }

        } catch (Exception e) {
            System.out.println("Error al listar suscripciones: " + e.getMessage());
        }

        return suscripciones;
    }

    public ArrayList<Suscripcion> listarSuscripcion(int codigoUsuario) {
        ArrayList<Suscripcion> suscripciones = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM Subscriptions WHERE id_user = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, codigoUsuario);
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Suscripcion suscripcion = new Suscripcion();
                suscripcion.setCodigo(rs.getInt("id"));
                suscripcion.setCodigoUsuario(rs.getInt("id_user"));
                suscripcion.setCodigoProveedor(rs.getInt("id_provider"));
                suscripcion.setCodigoPlan(rs.getInt("id_plan"));
                suscripcion.setMeses(rs.getInt("months"));
                suscripcion.setActivo(rs.getBoolean("active"));

                suscripciones.add(suscripcion);
            }

        } catch (Exception e) {
            System.out.println("Error al listar suscripcion: " + e.getMessage());
        }

        return suscripciones;
    }

    public boolean actualizarSuscripcion(Suscripcion suscripcion) {
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE Subscriptions SET active = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setBoolean(1, suscripcion.isActivo());
            stmt.setInt(2, suscripcion.getCodigo());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar suscripcion: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarSuscripcion(int codigo) {
        PreparedStatement stmt = null;
        try {
            String query = "DELETE FROM Subscriptions WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, codigo);
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar suscripcion: " + e.getMessage());
            return false;
        }
    }
}
