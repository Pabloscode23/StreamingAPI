package database;

import classes.HistorialVisto;
import java.util.ArrayList;
import java.sql.*;

/**
 * Clase HistorialVistoDAO que proporciona métodos para interactuar con la base de datos.
 */
public class HistorialVistoDAO {
    private Connection conn;

    /**
     * Constructor de HistorialVistoDAO.
     * Inicializa la conexión a la base de datos.
     * @throws SQLException si ocurre un error al obtener la conexión de la base de datos.
     */
    public HistorialVistoDAO() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Inserta un nuevo registro en el historial de visualizaciones.
     * @param historial que contiene los datos a insertar.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
    public boolean insertarHistorialVisto(HistorialVisto historial) {
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO Watcheds (id_user, id_provider, id_movie, id_serie) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, historial.getCodigoUsuario());
            stmt.setInt(2, historial.getCodigoProveedor());
            stmt.setInt(3, historial.getCodigoPelicula());
            stmt.setInt(4, historial.getCodigoSerie());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar historial: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista los registros del historial de visualizaciones de un usuario específico.
     * @param codigoUsuario ID del usuario cuyo historial se desea obtener.
     * @return Lista de objetos con los registros encontrados.
     */
    public ArrayList<HistorialVisto> listarHistorialVisto(int codigoUsuario) {
        ArrayList<HistorialVisto> historiales = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM Watcheds WHERE id_user = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, codigoUsuario);
            rs = stmt.executeQuery();

            while (rs.next()) {
                HistorialVisto historial = new HistorialVisto();
                historial.setCodigo(rs.getInt("id"));
                historial.setCodigoUsuario(rs.getInt("id_user"));
                historial.setCodigoProveedor(rs.getInt("id_provider"));
                historial.setCodigoPelicula(rs.getInt("id_movie"));
                historial.setCodigoSerie(rs.getInt("id_serie"));

                historiales.add(historial);
            }

        } catch (Exception e) {
            System.out.println("Error al listar historial: " + e.getMessage());
        }

        return historiales;
    }
}
