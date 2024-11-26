package menu;

import facade.AuthFacade;
import classes.Usuario;
import observer.Observer;
import observer.ReleaseObserver;
import observer.WatchModeAPI;
import service.StreamingServiceManager;
import model.SearchResult;
import state.*;
import java.io.*;
import java.util.Collection;
import java.util.Scanner;
import java.util.Vector;
import observer.WatchModeSubject;

public class MenuUsuario {

    // Atributos para WatchMode
    private WatchModeSubject watchModeSubject; // Para gestionar los observadores
    private WatchModeAPI watchModeAPI;         // Para obtener las notificaciones desde la API

    // Otros atributos
    private AuthFacade authFacade;
    private StreamingServiceManager serviceManager;
    private Scanner scanner;
    private Usuario usuario;
    private ContextoAutenticacion contextoAutenticacion;

    private static final String HISTORIAL_ARCHIVO = "historial_visto.txt"; // Ruta del archivo de historial

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

        // Inicializar WatchMode
        this.watchModeSubject = new WatchModeSubject(); // Crear el sujeto para las notificaciones
        ReleaseObserver observer = new ReleaseObserver(); // Crear el observador
        this.watchModeSubject.addObserver(observer); // Registrar el observador

        this.watchModeAPI = new WatchModeAPI(watchModeSubject); // Crear la API de WatchMode y asociarla con el sujeto
    }



    // Métodos para ver las notificaciones (llama a la API)


    // Métodos getters y setters (como ya tienes)
    public AuthFacade getAuthFacade() {
        return authFacade;
    }

    public void setAuthFacade(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    public StreamingServiceManager getServiceManager() {
        return serviceManager;
    }

    public void setServiceManager(StreamingServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ContextoAutenticacion getContextoAutenticacion() {
        return contextoAutenticacion;
    }

    public void setContextoAutenticacion(ContextoAutenticacion contextoAutenticacion) {
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
            System.out.println("4. Ver Mi Historial");
            System.out.println("5. Ver Notificaciones");
            System.out.println("6. Cerrar Sesión");
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
                    }
                    break;
                case 2:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        buscarEnCatalogoSencillo();
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                    }
                    break;
                case 3:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        buscarEnCatalogoAvanzado();
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                    }
                    break;
                case 4:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        verHistorial();  // Ver el historial del usuario
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                    }
                    break;
                case 5:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        verNotificaciones();  // Ver notificaciones / actualizaciones de contenido
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                    }
                    break;
                case 6:
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

    private void guardarEnHistorial(int codigoUsuario, int codigoProveedor, int codigoPelicula, int codigoSerie, String descripcion, String enlace, String plataforma, String titulo, String tipo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORIAL_ARCHIVO, true))) {
            String historial = String.format("Código Usuario: %d, Código Proveedor: %d, Código Película: %d, Código Serie: %d, Descripción: %s, Enlace: %s, Plataforma: %s, Título: %s, Tipo: %s\n",
                    codigoUsuario, codigoProveedor, codigoPelicula, codigoSerie, descripcion, enlace, plataforma, titulo, tipo);
            writer.write(historial);
            System.out.println("Historial guardado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar en el historial: " + e.getMessage());
        }
    }

    // Modificación en la función para mostrar resultados
    private void mostrarResultados(Collection<SearchResult> resultados) {
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron resultados para la búsqueda.");
        } else {
            System.out.println("\n=== Resultados de la Búsqueda ===");
            for (SearchResult resultado : resultados) {
                System.out.println(resultado); // Mostrar los resultados encontrados
            }

            // Preguntar al usuario si desea ver algún resultado

            System.out.print("\n¿Quieres ver alguna de estas opciones? (S/N): ");

            String respuesta = scanner.nextLine().trim().toLowerCase();


            if (respuesta.equals("s")) {
                // Suponiendo que el usuario quiere ver el primer resultado (esto puede cambiar)
                SearchResult resultadoSeleccionado = resultados.iterator().next();

                // Obtener los datos del resultado seleccionado (SearchResult)
                String descripcion = resultadoSeleccionado.getDescripcion();
                String enlace = resultadoSeleccionado.getEnlace();
                String plataforma = resultadoSeleccionado.getPlataforma();
                String titulo = resultadoSeleccionado.getTitulo();
                String tipo = resultadoSeleccionado.getTipo();

                // Obtener el nombre del usuario
                String nombreUsuario = usuario.getNombre();  // Suponiendo que el objeto usuario tiene el nombre
                int codigoUsuario = usuario.getCodigo();


                // Crear el registro de historial como una cadena de texto
                String historial = String.format("Usuario: %s, Codigo: %s, Título: %s, Tipo: %s, Descripción: %s, Enlace: %s, Plataforma: %s\n"
                        , nombreUsuario, codigoUsuario, titulo, tipo, descripcion, enlace, plataforma);

                // Guardar en el archivo de historial
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORIAL_ARCHIVO, true))) {
                    writer.write(historial);  // Escribe el historial en el archivo
                    System.out.println("Historial guardado exitosamente.");
                } catch (IOException e) {
                    System.out.println("Error al guardar en el historial: " + e.getMessage());
                }
            } else {
                System.out.println("No se guardó el historial.");
            }

        }
    }

    private void verHistorial() {
        int codigoUsuario = usuario.getCodigo(); // Obtenemos el código del usuario que ha hecho login
        boolean historialVacio = true;  // Inicializamos como verdadero, si no encontramos el historial cambiará a falso

        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORIAL_ARCHIVO))) {
            String linea;

            // Leemos el archivo línea por línea
            while ((linea = reader.readLine()) != null) {

                // Verificamos si la línea contiene "Codigo: " (que indica la información del usuario)
                if (linea.contains("Codigo: ")) {
                    // Separamos la línea en partes usando la coma como delimitador
                    String[] partes = linea.split(",");

                    // Buscamos el campo que contiene el código del usuario
                    for (String parte : partes) {
                        // Si encontramos "Codigo: ", extraemos el código
                        if (parte.trim().startsWith("Codigo: ")) {
                            // Extraemos el código eliminando el texto "Codigo: "
                            String codigoHistorialString = parte.trim().replace("Codigo: ", "").trim();

                            try {
                                // Convertimos el código del historial a número
                                int codigoHistorial = Integer.parseInt(codigoHistorialString);

                                // Comparamos el código del historial con el código del usuario actual
                                if (codigoHistorial == codigoUsuario) {
                                    // Si coinciden, mostramos el historial completo
                                    System.out.println("Historial encontrado: " + linea);  // Muestra toda la línea
                                    historialVacio = false;
                                    break;  // Rompe el bucle de partes porque ya encontramos el historial
                                }
                            } catch (NumberFormatException e) {
                                // Si ocurre un error al convertir el código, lo mostramos para depuración
                                System.out.println("Error al convertir el código de usuario: " + e.getMessage());
                            }
                        }
                    }

                    // Si ya encontramos el historial del usuario, no seguimos leyendo más líneas
                    if (!historialVacio) {
                        break;  // Rompe el bucle principal, ya encontramos el historial
                    }
                }
            }

            // Si no se encontró ningún historial para el usuario
            if (historialVacio) {
                System.out.println("No se ha encontrado historial para este usuario.");
            }

        } catch (IOException e) {
            // Manejo de errores en caso de problemas al leer el archivo
            System.out.println("Error al leer el historial: " + e.getMessage());
        }
    }
    public void verNotificaciones() {
        if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
            // Llamar al método para obtener las notificaciones desde la API
            watchModeAPI.fetchReleases();  // Este método hará la llamada a la API
        } else {
            System.out.println("Acceso denegado. Inicie sesión.");
        }
    }


}

