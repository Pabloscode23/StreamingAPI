package classes;

import java.util.ArrayList;

/**
 *
 */
public class Proveedor implements ProveedorService {
    /**
     * atributos de la clase
     */
    private int codigo;
    private String nombre;
    private String url;

    /**
     * constructor sin parametros
     */
    public Proveedor() {

    }

    /**
     * constructor con parametros
     *
     * @param codigo
     * @param nombre
     * @param url
     */
    public Proveedor(int codigo, String nombre, String url) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.url = url;
    }

    /**
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @param usuario
     * @param password
     */
    public void autenticarUsuario(String usuario, String password) {
        System.out.println("Autenticando usuario...");
    }

    @Override
    /**
     * Imprime la lista quemada de peliculas
     */
    public ArrayList<String> devolverContenido(String query) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("Peli1");
        stringList.add("Peli2");
        stringList.add("Peli3");
        return stringList;
    }
}
