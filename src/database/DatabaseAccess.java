package database;

import java.sql.SQLException;
import java.util.ArrayList;
import classes.*;

public class DatabaseAccess {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private PlanDAO planDAO = new PlanDAO();
    private ProveedorDAO proveedorDAO = new ProveedorDAO();
    private SuscripcionDAO suscripcionDAO = new SuscripcionDAO();
    private HistorialVistoDAO historialVistoDAO = new HistorialVistoDAO();

    public DatabaseAccess() throws SQLException {
    }

    public boolean iniciarSesion(String correo, String contrasena){
        if(usuarioDAO.iniciarSesion(correo, contrasena)){
            return true;
        }else{
            return false;
        }
    }

    public boolean registrarUsuario(Usuario usuario){
        return usuarioDAO.insertarUsuario(usuario);
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    public ArrayList<Usuario> obtenerUsuario(int codigoUsuario) {
        return usuarioDAO.listarUsuario(codigoUsuario);
    }

    public boolean actualizarUsuario(Usuario usuario){
        return usuarioDAO.actualizarUsuario(usuario);
    }

    public boolean eliminarUsuario(int codigo){
        return usuarioDAO.eliminarUsuario(codigo);
    }

    public boolean registrarPlan(Plan plan){
        return planDAO.insertarPlan(plan);
    }

    public ArrayList<Plan> obtenerPlanes() {
        return planDAO.listarPlanes();
    }

    public boolean actualizarPlan(Plan plan){
        return planDAO.actualizarPlan(plan);
    }

    public boolean eliminarPlan(int codigo){
        return planDAO.eliminarPlan(codigo);
    }

    public boolean registrarProveedor(Proveedor proveedor){
        return proveedorDAO.insertarProveedor(proveedor);
    }

    public ArrayList<Proveedor> obtenerProveedores() {
        return proveedorDAO.listarProveedores();
    }

    public boolean actualizarProveedor(Proveedor proveedor){
        return proveedorDAO.actualizarProveedor(proveedor);
    }

    public boolean eliminarProveedor(int codigo){
        return proveedorDAO.eliminarProveedor(codigo);
    }

    public boolean registrarSuscripcion(Suscripcion suscripcion){
        return suscripcionDAO.insertarSuscripcion(suscripcion);
    }

    public ArrayList<Suscripcion> obtenerSuscripciones() {
        return suscripcionDAO.listarSuscripciones();
    }

    public ArrayList<Suscripcion> obtenerSuscripcion(int codigoUsuario) {
        return suscripcionDAO.listarSuscripcion(codigoUsuario);
    }

    public boolean actualizarSuscripcion(Suscripcion suscripcion){
        return suscripcionDAO.actualizarSuscripcion(suscripcion);
    }

    public boolean eliminarSuscripcion(int codigo){
        return suscripcionDAO.eliminarSuscripcion(codigo);
    }

    public boolean registrarHistorialVisto(HistorialVisto historialVisto){
        return historialVistoDAO.insertarHistorialVisto(historialVisto);
    }

    public ArrayList<HistorialVisto> obtenerHistorialVisto(int codigoUsuario) {
        return historialVistoDAO.listarHistorialVisto(codigoUsuario);
    }
}