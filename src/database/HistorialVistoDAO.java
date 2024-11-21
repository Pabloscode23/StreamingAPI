package database;

import classes.HistorialVisto;
import java.util.ArrayList;
import java.sql.*;

public class HistorialVistoDAO {
    private Connection conn;

    public HistorialVistoDAO() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public boolean insertarHistorialVisto(HistorialVisto historial) {
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO Watcheds (id_user, id_provider, id_movie, id_serie) VALUES (?, ?, ?, ?, ?,)";
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

    public ArrayList<HistorialVisto> listarHistorialVisto(int codigoUsuario) {
        ArrayList<HistorialVisto> historiales = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM Watcheds WHERE id_user = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, codigoUsuario);
            rs = stmt.executeQuery(query);

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
