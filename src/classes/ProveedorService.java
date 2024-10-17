package classes;

import java.util.ArrayList;

public interface ProveedorService {
    /**
     * metodos de la interface
     * @param usuario
     * @param password
     */
    void autenticarUsuario(String usuario, String password);
    ArrayList<String> devolverContenido(String query);
}
