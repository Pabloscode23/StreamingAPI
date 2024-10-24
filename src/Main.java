import classes.*;
import search.StreamingServiceManager;

import java.util.ArrayList;

/**
 * Clase principal que ejecuta la simulación del sistema de gestión de streaming.
 */
public class Main {
    /**
     * Método principal que inicializa el sistema y muestra su funcionamiento.
     */
    public static void main(String[] args) {
        // Creación de usuarios
        Usuario usuario1 = new Usuario(1, "Carlos Mendoza", "carlos.mendoza@example.com", "contrasena123");
        Usuario usuario2 = new Usuario(2, "Sofia López", "sofia.lopez@example.com", "contrasena456");

        // Creación de proveedores
        Proveedor proveedorNetflix = new Proveedor(1, "Netflix", "https://www.netflix.com");
        Proveedor proveedorHBO = new Proveedor(2, "HBO Max", "https://www.hbomax.com");

        // Creación de planes
        Plan planBasico = new Plan(1, "Plan Básico");
        Plan planPremium = new Plan(2, "Plan Premium");

        // Creación de películas
        Pelicula pelicula1 = new Pelicula(1, "El Hoyo");
        Pelicula pelicula2 = new Pelicula(2, "Cazadores de Sombras");

        // Creación de series
        Serie serie1 = new Serie(1, "La Casa de Papel");
        Serie serie2 = new Serie(2, "Luis Miguel: La Serie");

        // Autenticación de usuario
        proveedorNetflix.autenticarUsuario(usuario1.getCorreo(), usuario1.getContrasena());
        proveedorHBO.autenticarUsuario(usuario2.getCorreo(), usuario2.getContrasena());

        // Devolver contenido del proveedor
        ArrayList<String> contenidoNetflix = proveedorNetflix.devolverContenido("peli");
        ArrayList<String> contenidoHBO = proveedorHBO.devolverContenido("serie");

        // Mostrar contenido devuelto
        System.out.println("Contenido en Netflix:");
        for (String contenido : contenidoNetflix) {
            System.out.println("- " + contenido);
        }

        System.out.println("\nContenido en HBO Max:");
        for (String contenido : contenidoHBO) {
            System.out.println("- " + contenido);
        }

        // Creación de suscripciones
        Suscripcion suscripcion1 = new Suscripcion(1, usuario1.getCodigo(), proveedorNetflix.getCodigo(), planPremium.getCodigo(), 12, 200.0f, true);
        Suscripcion suscripcion2 = new Suscripcion(2, usuario2.getCodigo(), proveedorHBO.getCodigo(), planBasico.getCodigo(), 6, 100.0f, true);

        // Mostrar información de las suscripciones
        System.out.println("\nSuscripciones:");
        System.out.println(suscripcion1);
        System.out.println(suscripcion2);


        StreamingServiceManager manager = StreamingServiceManager.getInstance();
        // Usar la instancia para gestionar los servicios de streaming
        manager.manageService();

    }
}