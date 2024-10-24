import classes.*;
import search.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * Clase principal que ejecuta la simulación del sistema de gestión de streaming.
 */
public class Main {
    /**
     * Método principal que inicializa el sistema y muestra su funcionamiento.
     */
    public static void main(String[] args) {
        /*
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
        System.out.println("==========================================");
        System.out.println("          Contenido en Netflix           ");
        System.out.println("==========================================");
        for (String contenido : contenidoNetflix) {
            System.out.println("- " + contenido);
        }

        System.out.println("\n==========================================");
        System.out.println("          Contenido en HBO Max           ");
        System.out.println("==========================================");
        for (String contenido : contenidoHBO) {
            System.out.println("- " + contenido);
        }

        // Creación de suscripciones
        Suscripcion suscripcion1 = new Suscripcion(1, usuario1.getCodigo(), proveedorNetflix.getCodigo(), planPremium.getCodigo(), 12, 200.0f, true);
        Suscripcion suscripcion2 = new Suscripcion(2, usuario2.getCodigo(), proveedorHBO.getCodigo(), planBasico.getCodigo(), 6, 100.0f, true);

        // Mostrar información de las suscripciones
        System.out.println("\n==========================================");
        System.out.println("               Suscripciones              ");
        System.out.println("==========================================");
        System.out.println(suscripcion1);
        System.out.println(suscripcion2);
        */

        System.out.println("\n------------------------------------------");
        System.out.println("                Búsquedas                ");
        System.out.println("------------------------------------------");

        // Obtenemos la instancia del manager (Singleton)
        StreamingServiceManager manager = StreamingServiceManager.getInstancia();

        // Usar el servicio WatchMode
        manager.setServicio(new WatchModeService());

        // Configurar el servicio WatchMode
        Vector<String> configParams = new Vector<>();
        configParams.add("Región: US");
        manager.configurarServicio(configParams);

        // *****  BUSCAR *****
        // Realizar una búsqueda en WatchMode
        System.out.println("\n------------------------------------------");
        System.out.println("   Búsqueda con parámetros (método buscar)   ");
        System.out.println("------------------------------------------");
        Vector<String> searchParams = new Vector<>();
        searchParams.add("año: 2021");
        Collection<SearchResult> resultados = manager.buscarEnServicio("Inception", searchParams);

        // Mostrar los resultados de Buscar
        for (SearchResult result : resultados) {
            System.out.println(result.toString() + "\n");
        }

        System.out.println("\n------------------------------------------");
        System.out.println("   Búsqueda con parámetros (método consultar)   ");
        System.out.println("------------------------------------------");

        // *****  CONSULTAR  *****
        // Realizar una CONSULTA en WatchMode
        Vector<String> consultParams = new Vector<>();
        consultParams.add("año: 2021");
        Collection<SearchResult> consultados = manager.consultarServicio("Dune", consultParams);

        // Mostrar los resultados de Consultar
        for (SearchResult result : consultados) {
            System.out.println(result.toString());
        }
    }
}
