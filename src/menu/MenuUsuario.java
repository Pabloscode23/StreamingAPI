package menu;

import service.StreamingServiceManager;
import observer.WatchModeSubject;
import observer.ReleaseObserver;
import observer.WatchModeAPI;
import model.SearchResult;
import facade.AuthFacade;
import classes.Usuario;
import java.util.*;
import java.io.*;
import state.*;

/**
 * Clase que representa el menú principal de interacción para un usuario autenticado.
 *
 * Proporciona opciones para administrar la cuenta, realizar búsquedas de contenido
 * en diferentes catálogos de streaming, ver notificaciones y gestionar el historial
 * de visualizaciones.
 *
 * También integra funcionalidades avanzadas como recomendaciones basadas en el historial
 * del usuario y notificaciones en tiempo real de nuevos lanzamientos utilizando observadores
 * de WatchMode.
 */

public class MenuUsuario {

    // Atributos para WatchMode
    private WatchModeSubject watchModeSubject; // Para gestionar los observadores
    private WatchModeAPI watchModeAPI;         // Para obtener las notificaciones desde la API
    private CompositeMenu menuPrincipal;
    // Otros atributos
    private AuthFacade authFacade;
    private StreamingServiceManager serviceManager;
    private Scanner scanner;
    private Usuario usuario;
    private ContextoAutenticacion contextoAutenticacion;

    private static final String HISTORIAL_ARCHIVO = "historial_visto.txt"; // Ruta del archivo de historial

    /**
     * Constructor de la clase `MenuUsuario`.
     *
     * Inicializa los servicios necesarios para la autenticación, el manejo del catálogo
     * de streaming, y la gestión de notificaciones, así como los elementos para interactuar
     * con el usuario.
     *
     * @param authFacade            Fachada de autenticación para las operaciones relacionadas con usuarios.
     * @param serviceManager        Gestor de servicios de streaming para manejar las búsquedas.
     * @param scanner               Objeto `Scanner` para capturar la entrada del usuario.
     * @param usuario               Instancia del usuario autenticado.
     * @param contextoAutenticacion Contexto para gestionar el estado de autenticación del usuario.
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

            menuPrincipal = new CompositeMenu("\n=== Menú Usuario ===");

            // Crear opciones de menú
            ComponenteMenu administrarCuenta = new ElementoMenu("1. Administrar Cuenta");
            ComponenteMenu buscarCatalogoSencillo = new ElementoMenu("2. Busqueda Sencilla - WatchMode");
            ComponenteMenu buscarCatalogoAvanzado = new ElementoMenu("3. Busqueda Avanzada - WatchMode");
            ComponenteMenu buscarCatalogoSencillo2 = new ElementoMenu("4. Busqueda Sencilla - Streaming Availability");
            ComponenteMenu buscarCatalogoAvanzado2 = new ElementoMenu("5. Busqueda Avanzada - Streaming Availability");
            ComponenteMenu verHistorial = new ElementoMenu("6. Ver Mi Historial");
            ComponenteMenu verNotificaciones = new ElementoMenu("7. Ver Notificaciones");
            ComponenteMenu cerrarSesion = new ElementoMenu("8. Cerrar Sesión");

            // Crear un submenú si es necesario
            CompositeMenu subMenuCuenta = new CompositeMenu("Submenú de Cuenta");
            subMenuCuenta.agregarComponente(administrarCuenta);

            // Agregar todos los elementos al menú principal
            menuPrincipal.agregarComponente(subMenuCuenta);
            menuPrincipal.agregarComponente(buscarCatalogoSencillo);
            menuPrincipal.agregarComponente(buscarCatalogoAvanzado);
            menuPrincipal.agregarComponente(buscarCatalogoSencillo2);
            menuPrincipal.agregarComponente(buscarCatalogoAvanzado2);
            menuPrincipal.agregarComponente(verHistorial);
            menuPrincipal.agregarComponente(verNotificaciones);
            menuPrincipal.agregarComponente(cerrarSesion);
            menuPrincipal.mostrar();

            System.out.print("Seleccione una opción: ");

            int opcion = -1; // Iniciar la variable en un valor que no sea una opción válida
            boolean opcionValida = false;

            // Mientras la opción no sea válida, seguir pidiendo la entrada
            while (!opcionValida) {
                try {
                    opcion = scanner.nextInt(); // Intentar leer la opción como un número entero
                    scanner.nextLine(); // Limpiar el buffer

                    // Validar que la opción esté dentro del rango esperado
                    if (opcion < 1 || opcion > 8) {
                        System.out.println("Opción inválida, intente nuevamente.");
                    } else {
                        opcionValida = true; // Opción válida, salir del bucle
                    }

                } catch (InputMismatchException e) {
                    // Capturar el error si el usuario ingresa algo que no sea un número
                    System.out.println("Por favor, ingrese una opción válida.");
                    scanner.nextLine(); // Limpiar el buffer de entrada para evitar bucles infinitos
                }
            }

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
                        mostrarSugerencias();
                        buscarEnCatalogoSencillo();
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                    }
                    break;
                case 3:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        mostrarSugerencias();
                        buscarEnCatalogoAvanzado();
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                    }
                    break;
                case 4:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        mostrarSugerencias();
                        buscarEnCatalogoSencillo2();
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                    }
                    break;
                case 5:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        mostrarSugerencias();
                        buscarEnCatalogoAvanzado2();
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                    }
                    break;
                case 6:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        verHistorial();  // Ver el historial del usuario
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                    }
                    break;
                case 7:
                    if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
                        verNotificaciones();  // Ver notificaciones / actualizaciones de contenido
                    } else {
                        System.out.println("Acceso denegado. Inicie sesión.");
                    }
                    break;
                case 8:
                    System.out.println("\nCerrando sesión...");
                    contextoAutenticacion.cerrarSesion();
                    sesionActiva = false;
                    break;
                default:
                    System.out.println("\nOpción inválida, intente nuevamente.");
            }
        }
    }

    /**
     * Realiza una búsqueda sencilla en el catálogo de WatchMode.
     *
     * Solicita al usuario un término de búsqueda y muestra los resultados encontrados.
     */
    private void buscarEnCatalogoSencillo() {
        serviceManager.setServicio("WatchMode");

        System.out.println("\n=== Búsqueda Sencilla en el Catálogo de WatchMode ===");
        System.out.print("Ingrese el término de búsqueda (nombre de la película o serie): ");
        String query = scanner.nextLine();

        Vector<String> searchParams = new Vector<>();
        searchParams.add("region: US");  // Región predeterminada, puede ajustarse si es necesario

        Collection<SearchResult> resultados = serviceManager.buscarEnServicio(query, searchParams);
        mostrarResultados(resultados);
    }

    /**
     * Realiza una búsqueda avanzada en el catálogo de WatchMode.
     *
     * Permite al usuario especificar filtros como tipo de contenido, región y plataforma de streaming.
     */
    private void buscarEnCatalogoAvanzado() {
        serviceManager.setServicio("WatchMode");

        System.out.println("\n=== Búsqueda Avanzada en el Catálogo de WatchMode ===");

        System.out.print("Ingrese el término de búsqueda (nombre de la película o serie): ");
        String query = scanner.nextLine().toLowerCase();

        // Selección de tipo de contenido
        System.out.println("Seleccione el tipo de contenido:");
        System.out.println("1. Película");
        System.out.println("2. Serie");
        System.out.print("Ingrese una opción (1 o 2): ");
        int tipoOpcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        String tipoContenido = (tipoOpcion == 1) ? "movie" : "tv";

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
     * Realiza una búsqueda sencilla en el catálogo de Streaming Availability.
     *
     * Solicita al usuario un término de búsqueda y muestra los resultados encontrados.
     */

    private void buscarEnCatalogoSencillo2() {
        serviceManager.setServicio("StreamingAvailability");

        System.out.println("\n=== Búsqueda Sencilla en el Catálogo de Streaming Availability ===");

        // Solicitar el término de búsqueda
        System.out.print("Ingrese el término de búsqueda (nombre de la película o serie): ");
        String query = scanner.nextLine();

        // Solicitar el tipo de contenido
        System.out.println("Seleccione el tipo de contenido:");
        System.out.println("1. Película");
        System.out.println("2. Serie");
        System.out.print("Ingrese una opción (1 o 2): ");
        String tipoContenido = "";
        while (true) {
            try {
                int opcionTipo = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                if (opcionTipo == 1) {
                    tipoContenido = "movie";
                    break;
                } else if (opcionTipo == 2) {
                    tipoContenido = "series";
                    break;
                } else {
                    System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese una opción válida (1 o 2).");
                scanner.nextLine(); // Limpiar el buffer
            }
        }

        // Preparar los parámetros de búsqueda
        Vector<String> searchParams = new Vector<>();
        searchParams.add("type: " + tipoContenido); // Tipo de contenido ingresado por el usuario

        // Realizar la búsqueda y mostrar los resultados
        Collection<SearchResult> resultados = serviceManager.buscarEnServicio(query, searchParams);
        mostrarResultados(resultados);
    }

    /**
     * Realiza una búsqueda avanzada en el catálogo de Streaming Availability.
     *
     * Permite al usuario especificar filtros como tipo de contenido, región y plataforma de streaming.
     */

    private void buscarEnCatalogoAvanzado2() {
        serviceManager.setServicio("StreamingAvailability");

        System.out.println("\n=== Búsqueda Avanzada en el Catálogo de Streaming Availability ===");

        // Solicitar el término de búsqueda
        System.out.print("Ingrese el término de búsqueda (nombre de la película o serie): ");
        String query = scanner.nextLine().toLowerCase();

        // Selección de tipo de contenido
        System.out.println("Seleccione el tipo de contenido:");
        System.out.println("1. Película");
        System.out.println("2. Serie");
        System.out.print("Ingrese una opción (1 o 2): ");
        String tipoContenido = "";
        while (true) {
            try {
                int opcionTipo = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                if (opcionTipo == 1) {
                    tipoContenido = "movie";
                    break;
                } else if (opcionTipo == 2) {
                    tipoContenido = "series";
                    break;
                } else {
                    System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese una opción válida (1 o 2).");
                scanner.nextLine(); // Limpiar el buffer
            }
        }

        // Mostrar opciones de región
        System.out.println("\nRegiones disponibles:");
        System.out.println("US: Estados Unidos");
        System.out.println("CA: Canadá");
        System.out.println("GB: Gran Bretaña");
        System.out.println("FR: Francia");
        System.out.println("JP: Japón");
        System.out.println("MX: México");
        System.out.println("ES: España");
        System.out.print("Seleccione una región (ingrese el código): ");
        String region = scanner.nextLine().toLowerCase();

        int sourceId = 0;


        // Realizar la búsqueda avanzada
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
                                    // No rompemos el bucle, seguimos buscando más entradas para este usuario
                                }
                            } catch (NumberFormatException e) {
                                // Si ocurre un error al convertir el código, lo mostramos para depuración
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
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

    public void mostrarSugerencias() {
        System.out.println("\n=== Generando Recomendaciones Personalizadas ===");

        String nombreUsuario = usuario.getNombre(); // Código del usuario autenticado
        List<String> titulosVistos = new ArrayList<>(); // Para almacenar los títulos del historial

        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORIAL_ARCHIVO))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                if (linea.contains("Usuario: ")) {
                    String[] partes = linea.split(",");
                    for (String parte : partes) {
                        if (parte.trim().startsWith("Usuario: ")) {
                            String usuarioHistorialString = parte.trim().replace("Usuario: ", "").trim();

                            try {
                                if (usuarioHistorialString.equals(nombreUsuario)) {
                                    // Extraemos el título del contenido visto
                                    for (String detalle : partes) {
                                        if (detalle.trim().startsWith("Título: ")) {
                                            String titulo = detalle.trim().replace("Título: ", "").trim();
                                            titulosVistos.add(titulo);
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error al procesar el historial: " + e.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el historial: " + e.getMessage());
        }

        // Si no hay títulos vistos, mostramos un mensaje
        if (titulosVistos.isEmpty()) {
            System.out.println("No se encontraron títulos en el historial del usuario.");
            return;
        }

        // Buscar recomendaciones para cada título del historial
        for (String titulo : titulosVistos) {
            System.out.println("\nBuscando contenido relacionado con: " + titulo);
            buscarRecomendacionesPorTitulo(titulo);
        }
    }

    /**
     * Realiza una búsqueda avanzada utilizando un título del historial.
     *
     * @param titulo El título del contenido para buscar recomendaciones relacionadas.
     */
    public void buscarRecomendacionesPorTitulo(String titulo) {
        // Configurar el servicio
        serviceManager.setServicio("WatchMode");

        int[] proveedores = {203, 157, 387, 26, 372, 371, 444, 369, 368, 80};
        Random random = new Random();
        int indiceAleatorio = random.nextInt(proveedores.length);

        String tipoContenido = "movie"; // Buscar tanto películas como series
        String region = "US"; // Región predeterminada
        int sourceId = proveedores[indiceAleatorio];// Filtro de plataforma

        // Realizar búsqueda avanzada con el título
        Collection<SearchResult> resultados = serviceManager.buscarConFiltrosAvanzados(titulo, tipoContenido, region, sourceId);

        // Mostrar resultados
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron recomendaciones para: " + titulo);
        } else {
            System.out.println("\nRecomendaciones basadas en " + titulo + ":");
            mostrarResultados(resultados);
        }
    }

}


