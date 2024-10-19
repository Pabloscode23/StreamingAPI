package classes;

public class Main {
    public static void main(String[] args) {

        /**
         * Instancia de classes
         */

        Usuario usuario = new Usuario(1,"Pedro","pedro@mail.com","123");
        Proveedor proveedor = new Proveedor(1,"Netflix","netflix.com");
        Plan plan = new Plan(1,"Basico");
        Suscripcion suscripcion = new Suscripcion(1,1,1,1,1,5000,true);
        Notificacion notificacion = new Notificacion(1,1,"Nuevo contenido agregado");
        Pelicula pelicula = new Pelicula(1,"Deadpool & Wolverine");
        Serie serie = new Serie(1,"Prision Break");
        HistorialVisto historialVisto = new HistorialVisto(1,1,1,1,1);
        BuscadorPorNombre buscador = new BuscadorPorNombre();

        /**
         * Prueba del patron factory en el main
         */
        ProveedorFactory factory = new ProveedorFactory();
        System.out.println(factory.getProveedor("netflix").devolverContenido("query"));

    }
}
