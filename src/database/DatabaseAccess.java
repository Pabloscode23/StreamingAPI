package database;

import java.sql.SQLException;
import java.util.ArrayList;
import classes.*;

/**
 * Clase DatabaseAccess que centraliza el acceso a los diferentes DAOs de la aplicación.
 */
public class DatabaseAccess {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private PlanDAO planDAO = new PlanDAO();
    private ProveedorDAO proveedorDAO = new ProveedorDAO();
    private SuscripcionDAO suscripcionDAO = new SuscripcionDAO();
    private HistorialVistoDAO historialVistoDAO = new HistorialVistoDAO();

    /**
     * Constructor de la clase DatabaseAccess.
     * @throws SQLException si ocurre un error al inicializar las conexiones a los DAOs.
     */
    public DatabaseAccess() throws SQLException {
    }

    /**
     * Verifica las credenciales de un usuario para iniciar sesión.
     * @param correo Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    public boolean iniciarSesion(String correo, String contrasena) {
        return usuarioDAO.iniciarSesion(correo, contrasena);
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * @param usuario Objeto que contiene los datos del usuario.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean registrarUsuario(Usuario usuario) {
        return usuarioDAO.insertarUsuario(usuario);
    }

    /**
     * Obtiene la lista de todos los usuarios en la base de datos.
     * @return Lista de objetos.
     */
    public ArrayList<Usuario> obtenerUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    /**
     * Obtiene los datos de un usuario específico.
     * @param codigoUsuario ID del usuario a consultar.
     * @return Lista de objetos con los datos del usuario.
     */
    public ArrayList<Usuario> obtenerUsuario(int codigoUsuario) {
        return usuarioDAO.listarUsuario(codigoUsuario);
    }

    /**
     * Actualiza los datos de un usuario en la base de datos.
     * @param usuario Objeto con los datos actualizados.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean actualizarUsuario(Usuario usuario) {
        return usuarioDAO.actualizarUsuario(usuario);
    }

    /**
     * Elimina un usuario de la base de datos.
     * @param codigo ID del usuario a eliminar.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean eliminarUsuario(int codigo) {
        return usuarioDAO.eliminarUsuario(codigo);
    }

    /**
     * Registra un nuevo plan en la base de datos.
     * @param plan Objeto que contiene los datos del plan.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean registrarPlan(Plan plan) {
        return planDAO.insertarPlan(plan);
    }

    /**
     * Obtiene la lista de todos los planes en la base de datos.
     * @return Lista de objetos.
     */
    public ArrayList<Plan> obtenerPlanes() {
        return planDAO.listarPlanes();
    }

    /**
     * Actualiza los datos de un plan en la base de datos.
     * @param plan Objeto con los datos actualizados.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean actualizarPlan(Plan plan) {
        return planDAO.actualizarPlan(plan);
    }

    /**
     * Elimina un plan de la base de datos.
     * @param codigo ID del plan a eliminar.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean eliminarPlan(int codigo) {
        return planDAO.eliminarPlan(codigo);
    }

    /**
     * Registra un nuevo proveedor en la base de datos.
     * @param proveedor Objeto que contiene los datos del proveedor.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean registrarProveedor(Proveedor proveedor) {
        return proveedorDAO.insertarProveedor(proveedor);
    }

    /**
     * Obtiene la lista de todos los proveedores en la base de datos.
     * @return Lista de objetos.
     */
    public ArrayList<Proveedor> obtenerProveedores() {
        return proveedorDAO.listarProveedores();
    }

    /**
     * Actualiza los datos de un proveedor en la base de datos.
     * @param proveedor Objeto con los datos actualizados.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean actualizarProveedor(Proveedor proveedor) {
        return proveedorDAO.actualizarProveedor(proveedor);
    }

    /**
     * Elimina un proveedor de la base de datos.
     * @param codigo ID del proveedor a eliminar.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean eliminarProveedor(int codigo) {
        return proveedorDAO.eliminarProveedor(codigo);
    }

    /**
     * Registra una nueva suscripción en la base de datos.
     * @param suscripcion Objeto que contiene los datos de la suscripción.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean registrarSuscripcion(Suscripcion suscripcion) {
        return suscripcionDAO.insertarSuscripcion(suscripcion);
    }

    /**
     * Obtiene la lista de todas las suscripciones en la base de datos.
     * @return Lista de objetos.
     */
    public ArrayList<Suscripcion> obtenerSuscripciones() {
        return suscripcionDAO.listarSuscripciones();
    }

    /**
     * Obtiene las suscripciones de un usuario específico.
     * @param codigoUsuario ID del usuario.
     * @return Lista de objetos.
     */
    public ArrayList<Suscripcion> obtenerSuscripcion(int codigoUsuario) {
        return suscripcionDAO.listarSuscripcion(codigoUsuario);
    }

    /**
     * Actualiza los datos de una suscripción en la base de datos.
     * @param suscripcion Objeto con los datos actualizados.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean actualizarSuscripcion(Suscripcion suscripcion) {
        return suscripcionDAO.actualizarSuscripcion(suscripcion);
    }

    /**
     * Elimina una suscripción de la base de datos.
     * @param codigo ID de la suscripción a eliminar.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean eliminarSuscripcion(int codigo) {
        return suscripcionDAO.eliminarSuscripcion(codigo);
    }

    /**
     * Registra un nuevo historial de visualización en la base de datos.
     * @param historialVisto Objeto que contiene los datos del historial.
     * @return true si la operación fue exitosa, false en caso de error.
     */
    public boolean registrarHistorialVisto(HistorialVisto historialVisto) {
        return historialVistoDAO.insertarHistorialVisto(historialVisto);
    }

    /**
     * Obtiene el historial de visualización de un usuario.
     * @param codigoUsuario ID del usuario.
     * @return Lista de objetos.
     */
    public ArrayList<HistorialVisto> obtenerHistorialVisto(int codigoUsuario) {
        return historialVistoDAO.listarHistorialVisto(codigoUsuario);
    }
}
