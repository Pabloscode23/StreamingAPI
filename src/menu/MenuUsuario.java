package menu;

import facade.AuthFacade;
import classes.Usuario;
import service.StreamingServiceManager;
import model.SearchResult;
import state.*;
import java.util.Collection;
import java.util.Scanner;
import java.util.Vector;

/**
 * Clase encargada de gestionar el menú de usuario, permitiendo opciones como administrar cuenta,
 * realizar búsquedas sencillas y avanzadas en el catálogo, y cerrar sesión.
 */
public class MenuUsuario {

    /**
     * Fachada de autenticación para manejar operaciones de usuario.
     */
    private AuthFacade authFacade;

    /**
     * Gestor de servicios de streaming para realizar búsquedas en el catálogo.
     */
    private StreamingServiceManager serviceManager;

    /**
     * Escáner para leer la entrada del usuario desde la consola.
     */
    private Scanner scanner;

    /**
     * Usuario actual que está interactuando con el menú.
     */
    private Usuario usuario;

    /**
     * Contexto de autenticación que gestiona el estado de la sesión del usuario.
     */
    private ContextoAutenticacion contextoAutenticacion;

    /**
     * Constructor que inicializa el menú de usuario con la fachada de autenticación,
     * el gestor de servicios de streaming, el escáner y el contexto de autenticación.
     *
     * @param authFacade            Fachada de autenticación para realizar operaciones de usuario.
     * @param serviceManager        Gestor de servicios de streaming.
     * @param scanner               Escáner para la entrada de usuario.
     * @param usuario               Usuario actual que está interactuando con el menú.
     * @param contextoAutenticacion Contexto de autenticación para gestionar el estado de sesión del usuario.
     */
    public MenuUsuario(AuthFacade authFacade, StreamingServiceManager serviceManager, Scanner scanner, Usuario usuario, ContextoAutenticacion contextoAutenticacion) {
        this.authFacade = authFacade;
        this.serviceManager = serviceManager;
        this.scanner = scanner;
        this.usuario = usuario;
        this.contextoAutenticacion = contextoAutenticacion;
    }

    /**
     * Muestra el menú principal del usuario, permitiéndole acceder a opciones para administrar la cuenta,
     * realizar búsquedas en el catálogo y cerrar sesión.
     * Verifica si la sesión ha expirado antes de ejecutar cualquier acción.
     */
    public void mostrarMenu() {
        boolean sesionActiva = true;
        while (sesionActiva) {
            // Verificamos si la sesión ha expirado antes de continuar
            contextoAutenticacion.accederServicio();

            if (contextoAutenticacion.getEstado() instanceof EstadoSesionExpirada) {
                System.out.println("Sesión expirada. Inicie sesión nuevamente.");
                sesionActiva = false;
                break;
            }

            System.out.println("\n=== Menú Usuario ===");
            System.out.println("1. Administrar Cuenta");
            System.out.println("2. Buscar en Catálogo (Sencilla)");
            System.out.println("3. Buscar en Catálogo (Avanzada)");
            System.out.println("4. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        CuentaManager cuentaManager = new CuentaManager(authFacade, serviceManager, scanner, usuario);
                        cuentaManager.administrarCuenta();
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                        System.out.println("Estado de Sesión: Expirada");
                    }
                    break;
                case 2:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        buscarEnCatalogoSencillo();
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                        System.out.println("Estado de Sesión: Expirada");
                    }
                    break;
                case 3:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        buscarEnCatalogoAvanzado();
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                        System.out.println("Estado de Sesión: Expirada");
                    }
                    break;
                case 4:
                    System.out.println("Cerrando sesión...");
                    contextoAutenticacion.cerrarSesion();
                    sesionActiva = false;
                    break;
                default:
                    System.out.println("Opción inválida, intente nuevamente.");
            }
        }
    }

    /**
     * Realiza una búsqueda sencilla en el catálogo de streaming, solicitando solo el término de búsqueda.
     */
    private void buscarEnCatalogoSencillo() {
        serviceManager.setServicio("WatchMode");

        System.out.println("\n=== Búsqueda Sencilla en el Catálogo ===");
        System.out.print("Ingrese el término de búsqueda (nombre de la película o serie): ");
        String query = scanner.nextLine();

        Vector<String> searchParams = new Vector<>();
        searchParams.add("region: US");  // Región predeterminada, puede ajustarse si es necesario

        Collection<SearchResult> resultados = serviceManager.buscarEnServicio(query, searchParams);
        mostrarResultados(resultados);
    }

    /**
     * Realiza una búsqueda avanzada en el catálogo de streaming, permitiendo especificar el tipo de contenido,
     * la región y la plataforma de streaming.
     */
    private void buscarEnCatalogoAvanzado() {
        serviceManager.setServicio("WatchMode");

        System.out.println("\n=== Búsqueda Avanzada en el Catálogo ===");

        System.out.print("Ingrese el término de búsqueda (nombre de la película o serie): ");
        String query = scanner.nextLine().toLowerCase();

        // Selección de tipo de contenido
        System.out.println("Seleccione el tipo de contenido:");
        System.out.println("1. Película");
        System.out.println("2. Serie");
        System.out.print("Ingrese una opción (1 o 2): ");
        int tipoOpcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        String tipoContenido = (tipoOpcion == 1) ? "movie" : "tv_movie";

        // Mostrar opciones de región
        System.out.println("\nRegiones disponibles:");
        System.out.println("1. USA (US)");
        System.out.println("2. Canada (CA)");
        System.out.println("3. Great Britain (GB)");
        System.out.println("4. France (FR)");
        System.out.println("5. Japan (JP)");
        System.out.println("6. Mexico (MX)");
        System.out.println("7. Spain (ES)");
        System.out.print("Seleccione una región (ingrese el código): ");
        String region = scanner.nextLine().toUpperCase();

        // Mostrar opciones de plataforma
        System.out.println("\nPlataformas disponibles:");
        System.out.println("203. Netflix");
        System.out.println("157. Hulu");
        System.out.println("387. Max");
        System.out.println("26. Prime Video");
        System.out.println("372. Disney+");
        System.out.println("371. AppleTV+");
        System.out.println("444. Paramount+");
        System.out.println("369. Youtube");
        System.out.println("368. Youtube Premium");
        System.out.println("80. Crunchyroll Premium");
        System.out.print("Ingrese el ID de la plataforma: ");
        int sourceId = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        Collection<SearchResult> resultados = serviceManager.buscarConFiltrosAvanzados(query, tipoContenido, region, sourceId);
        mostrarResultados(resultados);
    }

    /**
     * Muestra los resultados de búsqueda obtenidos.
     *
     * @param resultados Colección de resultados de búsqueda.
     */
    private void mostrarResultados(Collection<SearchResult> resultados) {
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron resultados para la búsqueda.");
        } else {
            System.out.println("\n=== Resultados de la Búsqueda ===");
            for (SearchResult resultado : resultados) {
                System.out.println(resultado);
            }
        }
    }
}
