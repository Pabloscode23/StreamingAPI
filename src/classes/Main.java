package classes;

public class Main {
    public static void main(String[] args) {
/**
 * Prueba del patron factory en el main
 */
        ProveedorFactory factory = new ProveedorFactory();
        System.out.println(factory.getProveedor("netflix").devolverContenido("query"));

    }
}
