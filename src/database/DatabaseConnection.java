package database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * Clase DatabaseConnection que implementa el patrón Singleton para gestionar una conexión única a la base de datos.
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/patrones";
    private final String username = "root";
    private final String password = "";

    /**
     * Constructor privado que establece la conexión con la base de datos.
     * @throws SQLException si ocurre un error al cargar el driver o al conectar con la base de datos.
     */
    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver no encontrado", ex);
        } catch (SQLException ex) {
            throw new SQLException("Sin Conexion: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene el objeto Connection asociado a la instancia de DatabaseConnection.
     * @return connection que representa la conexión con la base de datos.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Obtiene la instancia única de DatabaseConnection.
     * @return Instancia única.
     * @throws SQLException si ocurre un error al crear una nueva instancia o al verificar la conexión.
     */
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }

        return instance;
    }
}
