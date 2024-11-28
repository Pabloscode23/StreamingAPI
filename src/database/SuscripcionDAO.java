package database;

import classes.Suscripcion;
import java.util.ArrayList;
import java.sql.*;

/**
 * Clase SuscripcionDAO que proporciona métodos para interactuar con la base de datos
 */
public class SuscripcionDAO {
    private Connection conn;

    /**
     * Constructor de SuscripcionDAO.
     * Inicializa la conexión a la base de datos.
     * @throws SQLException si ocurre un error al obtener la conexión de la base de datos.
     */
    public SuscripcionDAO() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Inserta una nueva suscripción en la base de datos.
     * @param suscripcion Objeto que contiene los datos de la suscripción a insertar.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
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

    /**
     * Obtiene una lista de todas las suscripciones almacenadas en la base de datos.
     * @return Lista de objetos que representan las suscripciones encontradas.
     */
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

    /**
     * Obtiene las suscripciones de un usuario específico.
     * @param codigoUsuario ID del usuario cuyas suscripciones se desean obtener.
     * @return Lista de objetos que representan las suscripciones del usuario encontrado.
     */
    public ArrayList<Suscripcion> listarSuscripcion(int codigoUsuario) {
        ArrayList<Suscripcion> suscripciones = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM Subscriptions WHERE id_user = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, codigoUsuario);
            rs = stmt.executeQuery();

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

    /**
     * Actualiza el estado de una suscripción en la base de datos.
     * @param suscripcion Objeto con el estado actualizado.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
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

    /**
     * Elimina una suscripción de la base de datos según su ID.
     * @param codigo ID de la suscripción que se desea eliminar.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
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
