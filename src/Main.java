import java.util.InputMismatchException;
import service.StreamingServiceManager;
import proxy.ProxyUsuarioAutenticado;
import proxy.UsuarioAutenticado;
import facade.AuthFacade;
import java.util.Scanner;
import menu.MenuUsuario;
import classes.Usuario;
import state.*;

/**
 * Clase principal que inicia la aplicación, proporcionando opciones de inicio de sesión,
 * registro de usuarios y navegación en el menú principal.
 *
 * Administra la interacción con el usuario a través de la consola.
 */
public class Main {

    /**
     * Método principal que ejecuta el ciclo de la aplicación, mostrando el menú principal y
     * permitiendo al usuario iniciar sesión, registrarse o salir.
     *
     * @param args Argumentos de línea de comando (no utilizados).
     */
    public static void main(String[] args) {
        AuthFacade authFacade = new AuthFacade();
        StreamingServiceManager serviceManager = StreamingServiceManager.getInstancia();
        ContextoAutenticacion contexto = new ContextoAutenticacion();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Menú Principal ===");

            if (contexto.haySesionActiva()) {
                System.out.println("Estado de Sesión: Activa");
                System.out.println("\n1. Continuar como: ");
                System.out.println(contexto.getUsuarioAutenticado());
                System.out.println("\n2. Cerrar Sesión");
                System.out.println("3. Salir");
            } else {
                System.out.println("Estado de Sesión: Inactiva");
                System.out.println("\n1. Iniciar sesión");
                System.out.println("2. Registrarse");
                System.out.println("3. Salir");
            }

            System.out.print("Seleccione una opción: ");

            int opcion = -1; // Iniciar la variable en un valor que no sea una opción válida
            boolean opcionValida = false;

            // Mientras la opción no sea válida, seguir pidiendo la entrada
            while (!opcionValida) {
                try {
                    opcion = scanner.nextInt(); // Intentar leer la opción como un número entero
                    scanner.nextLine(); // Limpiar el buffer

                    // Validar que la opción esté dentro del rango esperado
                    if (opcion < 1 || opcion > 3) {
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

            if (contexto.haySesionActiva()) {
                switch (opcion) {
                    case 1:
                        iniciarSesionAutomatica(authFacade, serviceManager, contexto, scanner);
                        break;
                    case 2:
                        contexto.cerrarSesion();
                        break;
                    case 3:
                        System.out.println("\nSaliendo...");
                        continuar = false;
                        break;
                    default:
                        System.out.println("\nOpción inválida, intente nuevamente.");
                }
            } else {
                switch (opcion) {
                    case 1:
                        iniciarSesion(authFacade, contexto, scanner);
                        break;
                    case 2:
                        registrarse(authFacade, scanner);
                        break;
                    case 3:
                        System.out.println("\nSaliendo...");
                        continuar = false;
                        break;
                    default:
                        System.out.println("\nOpción inválida, intente nuevamente.");
                }
            }
        }
        scanner.close();
    }

    /**
     * Método auxiliar para iniciar sesión en el sistema.
     * Solicita al usuario ingresar su correo y contraseña, y valida las credenciales.
     * Actualiza el contexto de autenticación si las credenciales son correctas.
     *
     * @param authFacade Fachada de autenticación utilizada para validar credenciales.
     * @param contexto   Contexto de autenticación para gestionar el estado de la sesión.
     * @param scanner    Escáner para leer la entrada del usuario.
     */
    private static void iniciarSesion(AuthFacade authFacade, ContextoAutenticacion contexto, Scanner scanner) {
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        // Usamos el Proxy, pero ahora pasamos el authFacade
        UsuarioAutenticado proxyUsuario = new ProxyUsuarioAutenticado(correo, contrasena, authFacade);

        if (proxyUsuario.iniciarSesion(correo, contrasena)) {
            Usuario usuario = proxyUsuario.obtenerUsuario();  // Obtenemos al usuario autenticado
            if (usuario != null) {
                System.out.println("\nInicio de sesión exitoso. ¡Bienvenido, " + usuario.getNombre() + "!");
                contexto.setEstado(new EstadoAutenticado());
                contexto.setUsuarioAutenticado(usuario); // Pasamos el objeto Usuario completo
                contexto.setToken("TOKEN_GENERADO"); // Genera un token real en una implementación completa
            }
        } else {
            System.out.println("\nCorreo o contraseña incorrectos.");
        }
    }

    /**
     * Método auxiliar para continuar automáticamente la sesión del usuario autenticado
     * sin necesidad de volver a ingresar sus credenciales.
     *
     * @param authFacade    Fachada de autenticación.
     * @param serviceManager Administrador de servicios de streaming.
     * @param contexto       Contexto de autenticación que almacena el estado de sesión del usuario.
     * @param scanner        Escáner para leer la entrada del usuario.
     */
    private static void iniciarSesionAutomatica(AuthFacade authFacade, StreamingServiceManager serviceManager, ContextoAutenticacion contexto, Scanner scanner) {
        Usuario usuario = contexto.getUsuarioAutenticado(); // Obtener el objeto Usuario directamente

        if (usuario != null) {
            System.out.println("\nContinuando como " + usuario.getNombre() + "...");
            MenuUsuario menuUsuario = new MenuUsuario(authFacade, serviceManager, scanner, usuario, contexto);
            menuUsuario.mostrarMenu();
        } else {
            System.out.println("Error al recuperar el usuario. Por favor, vuelva a iniciar sesión.");
            contexto.cerrarSesion();
        }
    }

    /**
     * Método auxiliar para registrar un nuevo usuario en el sistema.
     * Solicita al usuario ingresar su nombre, apellidos, correo y contraseña.
     *
     * @param authFacade Fachada de autenticación utilizada para registrar al usuario.
     * @param scanner    Escáner para leer la entrada del usuario.
     */
    private static void registrarse(AuthFacade authFacade, Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        String nombreCompleto = nombre + " " + apellidos;

        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        while (!correo.contains("@") || !correo.contains(".")) {
            System.out.println("Correo inválido. Asegúrese de que incluye '@' y un dominio válido.");
            System.out.print("Correo: ");
            correo = scanner.nextLine();
        }
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        if (authFacade.registrarUsuario(nombreCompleto, correo, contrasena)) {
            System.out.println("\nRegistro exitoso. Ahora puede iniciar sesión.");
        } else {
            System.out.println("\nEl correo ya está registrado.");
        }
    }
}
