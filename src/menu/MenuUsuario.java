package menu;

import classes.Pelicula;
import decorator.CalificacionDecorator;
import decorator.PeliculaAdapter;
import decorator.PeliculaInterface;
import observer.*;
import service.StreamingServiceManager;
import model.SearchResult;
import facade.AuthFacade;
import classes.Usuario;

import java.util.*;
import java.io.*;

import state.*;


/**
 * Clase que representa el menú principal de interacción para un usuario autenticado.
 * <p>
 * Proporciona opciones para administrar la cuenta, realizar búsquedas de contenido
 * en diferentes catálogos de streaming, ver nov y gestionar el historial
 * de visualizaciones.
 * <p>
 * También integra funcionalidades avanzadas como recomendaciones basadas en el historial
 * del usuario y notificaciones en tiempo real de nuevos lanzamientos utilizando observadores
 * de WatchMode.
 */

public class MenuUsuario {

    // Atributos para WatchMode
    /**
     * Gestor de observadores para WatchMode.
     */
    private WatchModeSubject watchModeSubject;

    /**
     * API para obtener notificaciones de WatchMode.
     */
    private WatchModeAPI watchModeAPI;

    /**
     * Menú principal de la aplicación.
     */
    private CompositeMenu menuPrincipal;

    /**
     * Gestor de observadores para StreamAvailability.
     */
    private StreamAvailabilitySubject streamAvailabilitySubject;

    /**
     * API para obtener notificaciones de disponibilidad de contenido.
     */
    private StreamAvailabilityAPI streamAvailabilityAPI;

    /**
     * Facade para manejar la autenticación del usuario.
     */
    private AuthFacade authFacade;

    /**
     * Gestor de servicios de streaming.
     */
    private StreamingServiceManager serviceManager;

    /**
     * Escáner para recibir entradas del usuario.
     */
    private Scanner scanner;

    /**
     * Objeto que representa al usuario autenticado.
     */
    private Usuario usuario;

    /**
     * Contexto de autenticación que maneja el estado de sesión.
     */
    private ContextoAutenticacion contextoAutenticacion;


    private static final String HISTORIAL_ARCHIVO = "historial_visto.txt"; // Ruta del archivo de historial

    /**
     * Constructor de la clase `MenuUsuario`.
     * <p>
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

        // Inicializar WatchMode y StreamAvailability
        this.watchModeSubject = new WatchModeSubject(); // Crear el sujeto para las notificaciones
        ReleaseObserver observer = new ReleaseObserver(); // Crear el observador
        ReleaseObserverStreamingAv observerSA = new ReleaseObserverStreamingAv();
        this.watchModeSubject.addObserver(observer); // Registrar el observador
        this.watchModeAPI = new WatchModeAPI(watchModeSubject); // Crear la API de WatchMode y asociarla con el sujeto
        this.streamAvailabilitySubject = new StreamAvailabilitySubject(); // Crear el sujeto para las notificaciones
        this.streamAvailabilityAPI = new StreamAvailabilityAPI(streamAvailabilitySubject); // Crear la API de StreamAvailability y asociarla con el sujeto
        this.streamAvailabilitySubject.addObserver(observerSA); // Registrar el observador

    }

    // Métodos para ver las notificaciones (llama a la API)


    // Métodos getters y setters
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
// Verificar si el usuario está autenticado antes de ingresar al bucle
        if (!(contextoAutenticacion.getEstado() instanceof EstadoAutenticado)) {
            contextoAutenticacion.cerrarSesion();
            return; // Salir del método si el usuario no está autenticado
        }

        while (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
            // Verificamos si la sesión ha expirado antes de continuar
            if (!(contextoAutenticacion.getEstado() instanceof EstadoAutenticado)) {
                contextoAutenticacion.cerrarSesion(); // Cerrar sesión
                return; // Salir del método si la sesión ha expirado
            }else{
            contextoAutenticacion.accederServicio();

            menuPrincipal = new CompositeMenu("\n=== Menú Usuario ===");

            // Crear opciones de menú
            ComponenteMenu administrarCuenta = new ElementoMenu("1. Administrar Cuenta");
            ComponenteMenu buscarCatalogoSencillo = new ElementoMenu("2. Busqueda Sencilla - WatchMode");
            ComponenteMenu buscarCatalogoAvanzado = new ElementoMenu("3. Busqueda Avanzada - WatchMode");
            ComponenteMenu buscarCatalogoSencillo2 = new ElementoMenu("4. Busqueda Sencilla - Streaming Availability");
            ComponenteMenu buscarCatalogoAvanzado2 = new ElementoMenu("5. Busqueda Avanzada - Streaming Availability");
            ComponenteMenu calificarPeliculaOSerie = new ElementoMenu("6. Calificar Pelicula o Serie");
            ComponenteMenu verHistorial = new ElementoMenu("7. Ver Mi Historial");
            ComponenteMenu verNotificaciones = new ElementoMenu("8. Ver Novedades");
            ComponenteMenu cerrarSesion = new ElementoMenu("9. Cerrar Sesión");

            // Crear un submenú si es necesario
            CompositeMenu subMenuCuenta = new CompositeMenu("Submenú de Cuenta");
            subMenuCuenta.agregarComponente(administrarCuenta);

            // Agregar todos los elementos al menú principal
            menuPrincipal.agregarComponente(subMenuCuenta);
            menuPrincipal.agregarComponente(buscarCatalogoSencillo);
            menuPrincipal.agregarComponente(buscarCatalogoAvanzado);
            menuPrincipal.agregarComponente(buscarCatalogoSencillo2);
            menuPrincipal.agregarComponente(buscarCatalogoAvanzado2);
            menuPrincipal.agregarComponente(calificarPeliculaOSerie);
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
                    if (opcion < 1 || opcion >9) {
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

            if (!(contextoAutenticacion.getEstado() instanceof EstadoAutenticado)) {
                contextoAutenticacion.cerrarSesion();
                return; // Salir si la sesión ha expirado
            }
            switch (opcion) {
                // Caso 1: Administrar cuenta
                case 1:

                    // Si está autenticado, crea un objeto CuentaManager y llama al método para administrar la cuenta
                    CuentaManager cuentaManager = new CuentaManager(authFacade, serviceManager, scanner, usuario);
                    cuentaManager.administrarCuenta();

                    break;

                // Caso 2: Mostrar sugerencias y buscar en catálogo sencillo
                case 2:

                    // Si está autenticado, muestra sugerencias y realiza una búsqueda sencilla en el catálogo
                    mostrarSugerencias();
                    buscarEnCatalogoSencillo();

                    break;

                // Caso 3: Mostrar sugerencias y buscar en catálogo avanzado
                case 3:

                    // Si está autenticado, muestra sugerencias y realiza una búsqueda avanzada en el catálogo
                    mostrarSugerencias();
                    buscarEnCatalogoAvanzado();

                    break;

                // Caso 4: Mostrar sugerencias y buscar en otro catálogo sencillo
                case 4:

                    // Si está autenticado, muestra sugerencias y realiza una búsqueda en otro catálogo sencillo
                    mostrarSugerencias();
                    buscarEnCatalogoSencillo2();
                    break;

                // Caso 5: Mostrar sugerencias y buscar en otro catálogo avanzado
                case 5:

                    // Si está autenticado, muestra sugerencias y realiza una búsqueda en otro catálogo avanzado
                    mostrarSugerencias();
                    buscarEnCatalogoAvanzado2();

                    break;
                case 6:
                    // Si está autenticado, llama al método para calificar una película o serie
                    calificarPeliculaOSerie();
                    break;

                // Caso 7 Ver historial
                case 7:

                    // Si está autenticado, llama al método para ver el historial
                    verHistorial();

                    break;

                // Caso 8 Ver novedades
                case 8:
                    verNotificaciones();

                    break;
                // Caso 9 Cerrar sesión
                case 9:
                    System.out.println("\nCerrando sesión...");
                    contextoAutenticacion.cerrarSesion();
                    break;

            }}
        }
    }

    /**
     * Realiza una búsqueda sencilla en el catálogo de WatchMode.
     * <p>
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
     * <p>
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
     * <p>
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
     * <p>
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
        // Verificamos el código del usuario antes de escribirlo
        System.out.println("Código Usuario al guardar en el historial: " + codigoUsuario);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORIAL_ARCHIVO, true))) {
            // Formateamos la cadena del historial para incluir los detalles
            String historial = String.format("Código Usuario: %d, Código Proveedor: %d, Código Película: %d, Código Serie: %d, Descripción: %s, Enlace: %s, Plataforma: %s, Título: %s, Tipo: %s\n",
                    codigoUsuario, codigoProveedor, codigoPelicula, codigoSerie, descripcion, enlace, plataforma, titulo, tipo);
            // Escribimos el historial en el archivo
            writer.write(historial);
            System.out.println("Historial guardado exitosamente.");
        } catch (IOException e) {
            // Capturamos cualquier error de escritura en el archivo
            System.out.println("Error al guardar en el historial: " + e.getMessage());
        }
    }

    // Método para mostrar los resultados de búsqueda y guardar el historial
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

                // Obtener el nombre del usuario (usamos nombre en vez de código)
                String nombreUsuario = usuario.getNombre();  // Suponiendo que el objeto usuario tiene el nombre

                // Crear el registro de historial como una cadena de texto (usamos nombre de usuario)
                String historial = String.format("Usuario: %s, Título: %s, Tipo: %s, Descripción: %s, Enlace: %s, Plataforma: %s\n",
                        nombreUsuario, titulo, tipo, descripcion, enlace, plataforma);

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

    // Metodo para calificar una pelicula o serie
    private void calificarPeliculaOSerie() {
        final String NOMBRE_ARCHIVO = "calificaciones.txt";
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("=== Calificar Película o Serie ===");
            System.out.println("1. Calificar una película o serie");
            System.out.println("2. Ver películas calificadas");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    // Solicitar nombre de la película o serie
                    System.out.print("Ingrese el nombre de la película o serie: ");
                    String nombrePelicula = scanner.nextLine();

                    // Crear instancia y decorar con funcionalidad de calificación
                    Pelicula peliculaBase = new Pelicula(0, nombrePelicula);
                    PeliculaInterface peliculaAdaptada = new PeliculaAdapter(peliculaBase);
                    CalificacionDecorator peliculaDecorada = new CalificacionDecorator(peliculaAdaptada);

                    System.out.print("Ingrese una calificación (0-10): ");
                    double calificacion = scanner.nextDouble();

                    try {
                        peliculaDecorada.agregarCalificacion(calificacion);
                        System.out.println("Calificación agregada con éxito.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    // Leer y mostrar las películas calificadas desde el archivo
                    System.out.println("=== Películas Calificadas ===");
                    try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
                        String linea;
                        while ((linea = lector.readLine()) != null) {
                            System.out.println(linea);
                        }
                    } catch (IOException e) {
                        System.out.println("No se pudieron cargar las películas calificadas: " + e.getMessage());
                    }
                    break;

                case 3:
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
            System.out.println();
        }
    }

    // Método para mostrar el historial de un usuario autenticado
    private void verHistorial() {
        String nombreUsuario = usuario.getNombre(); // Obtener el nombre del usuario que ha hecho login
        boolean historialVacio = true;  // Inicializamos como verdadero, si no encontramos el historial cambiará a falso
        int contador = 1;  // Contador para numerar las películas/series

        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORIAL_ARCHIVO))) {
            String linea;
            boolean encabezadoImpreso = false;  // Bandera para imprimir el encabezado solo una vez

            // Leemos el archivo línea por línea
            while ((linea = reader.readLine()) != null) {

                // Verificamos si la línea contiene "Usuario: " (que indica la información del usuario)
                if (linea.contains("Usuario: ")) {
                    // Separamos la línea en partes usando la coma como delimitador
                    String[] partes = linea.split(",");

                    // Buscamos el campo que contiene el nombre del usuario
                    for (String parte : partes) {
                        // Si encontramos "Usuario: ", extraemos el nombre
                        if (parte.trim().startsWith("Usuario: ")) {
                            // Extraemos el nombre eliminando el texto "Usuario: "
                            String nombreHistorial = parte.trim().replace("Usuario: ", "").trim();

                            // Comparamos el nombre del historial con el nombre del usuario actual
                            if (nombreHistorial.equals(nombreUsuario)) {
                                // Si no se ha impreso el encabezado aún, lo imprimimos
                                if (!encabezadoImpreso) {
                                    System.out.println("===============================================");
                                    System.out.println("Historial de: " + nombreUsuario);
                                    System.out.println("===============================================");
                                    encabezadoImpreso = true;  // Marcamos que el encabezado ya fue impreso
                                }

                                // Si coinciden, mostramos las películas/series con numeración
                                System.out.println("Pelicula/Serie " + contador + ":");
                                System.out.println("Título: " + getCampoDeHistorial(linea, "Título: "));
                                System.out.println("Tipo: " + getCampoDeHistorial(linea, "Tipo: "));
                                System.out.println("Descripción: " + getCampoDeHistorial(linea, "Descripción: "));
                                System.out.println("Enlace: " + getCampoDeHistorial(linea, "Enlace: "));
                                System.out.println("Plataforma: " + getCampoDeHistorial(linea, "Plataforma: "));
                                System.out.println("-----------------------------------------------");
                                contador++;  // Incrementamos el contador para el siguiente ítem
                                historialVacio = false;
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


    // Método auxiliar para extraer los campos del historial
    private String getCampoDeHistorial(String linea, String campo) {
        // Extrae el valor correspondiente a cada campo, usando el nombre del campo como referencia
        if (linea.contains(campo)) {
            String[] partes = linea.split(",");
            for (String parte : partes) {
                if (parte.trim().startsWith(campo)) {
                    return parte.trim().replace(campo, "").trim();
                }
            }
        }
        return "No disponible";
    }


    /**
     * Método para ver las notificaciones del usuario.
     * Comprueba si el usuario está autenticado antes de realizar llamadas a las APIs externas
     * para obtener las notificaciones relevantes.
     * Si el usuario no está autenticado, deniega el acceso.
     */

    public void verNotificaciones() {
        // Verifica si el usuario está autenticado comprobando su estado
        if (contextoAutenticacion.getEstado() instanceof EstadoAutenticado) {
            // Si está autenticado, se llaman a los métodos para obtener las notificaciones desde las API correspondientes
            watchModeAPI.fetchReleases();  // Este método hará la llamada a la API para obtener los lanzamientos de WatchMode
            streamAvailabilityAPI.fetchReleases(); // Este método hará la llamada a la API para obtener los lanzamientos de StreamAvailability
        } else {
            // Si no está autenticado, muestra un mensaje de acceso denegado
            System.out.println("Acceso denegado. Inicie sesión.");
        }
    }

    public void mostrarSugerencias() {
        // Muestra un mensaje indicando que se están generando recomendaciones personalizadas
        System.out.println("\n=== Generando Recomendaciones Personalizadas ===");

        // Obtiene el nombre del usuario autenticado
        String nombreUsuario = usuario.getNombre(); // Nombre del usuario autenticado
        // Crea una lista para almacenar los títulos del historial de visualización
        List<String> titulosVistos = new ArrayList<>(); // Lista que almacenará los títulos vistos por el usuario

        // Intenta leer el archivo que contiene el historial de visualización del usuario
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORIAL_ARCHIVO))) {
            // Declara una variable para leer cada línea del archivo
            String linea;

            // Lee cada línea del archivo hasta que no haya más
            while ((linea = reader.readLine()) != null) {
                // Si la línea contiene la palabra "Usuario: ", significa que es una línea relevante
                if (linea.contains("Usuario: ")) {
                    // Divide la línea en partes usando la coma como delimitador
                    String[] partes = linea.split(",");

                    // Itera sobre las partes de la línea para encontrar información sobre el usuario
                    for (String parte : partes) {
                        // Si la parte comienza con "Usuario: ", se extrae el nombre del usuario
                        if (parte.trim().startsWith("Usuario: ")) {
                            // Extrae el nombre del usuario, eliminando "Usuario: " al principio
                            String usuarioHistorialString = parte.trim().replace("Usuario: ", "").trim();

                            try {
                                // Si el nombre del usuario coincide con el del usuario autenticado, procesamos el historial
                                if (usuarioHistorialString.equals(nombreUsuario)) {
                                    // Busca los títulos de los contenidos vistos por el usuario
                                    for (String detalle : partes) {
                                        // Si la parte comienza con "Título: ", extrae el título
                                        if (detalle.trim().startsWith("Título: ")) {
                                            // Extrae el título, eliminando "Título: " al principio
                                            String titulo = detalle.trim().replace("Título: ", "").trim();
                                            // Agrega el título a la lista de títulos vistos
                                            titulosVistos.add(titulo);
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                // Si ocurre un error al procesar el historial, se muestra el mensaje de error
                                System.out.println("Error al procesar el historial: " + e.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Si ocurre un error al leer el archivo, se muestra el mensaje de error
            System.out.println("Error al leer el historial: " + e.getMessage());
        }

        // Si la lista de títulos vistos está vacía, significa que no hay títulos en el historial del usuario
        if (titulosVistos.isEmpty()) {
            System.out.println("No se encontraron títulos en el historial del usuario.");
            return; // Salir del método si no hay títulos
        }

        // Si hay títulos vistos, busca recomendaciones basadas en cada título
        for (String titulo : titulosVistos) {
            // Muestra un mensaje indicando que está buscando contenido relacionado con el título
            System.out.println("\nBuscando contenido relacionado con: " + titulo);
            // Llama a un método para buscar recomendaciones basadas en el título
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


