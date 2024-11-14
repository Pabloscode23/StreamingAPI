import facade.AuthFacade;
import state.*;
import classes.Usuario;
import menu.MenuUsuario;
import service.StreamingServiceManager;
import java.util.Scanner;

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
            System.out.println("=== Menú Principal ===");

            if (contexto.haySesionActiva()) {
                System.out.println("1. Continuar como: ");
                System.out.println(contexto.getUsuarioAutenticado());
                System.out.println("2. Cerrar Sesión");
                System.out.println("3. Salir");
            } else {
                System.out.println("1. Iniciar sesión");
                System.out.println("2. Registrarse");
                System.out.println("3. Salir");
            }

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (contexto.haySesionActiva()) {
                switch (opcion) {
                    case 1:
                        iniciarSesionAutomatica(authFacade, serviceManager, contexto, scanner);
                        break;
                    case 2:
                        contexto.cerrarSesion();
                        break;
                    case 3:
                        System.out.println("Saliendo...");
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción inválida, intente nuevamente.");
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
                        System.out.println("Saliendo...");
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción inválida, intente nuevamente.");
                }
            }
        }
        scanner.close();
    }

    private static void iniciarSesion(AuthFacade authFacade, ContextoAutenticacion contexto, Scanner scanner) {
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        Usuario usuario = authFacade.iniciarSesion(correo, contrasena);
        if (usuario != null) {
            System.out.println("Inicio de sesión exitoso. ¡Bienvenido, " + usuario.getNombre() + "!");
            contexto.setEstado(new EstadoAutenticado());
            contexto.setUsuarioAutenticado(usuario); // Pasamos el objeto Usuario completo
            contexto.setToken("TOKEN_GENERADO"); // Genera un token real en una implementación completa
        } else {
            System.out.println("Correo o contraseña incorrectos.");
        }
    }


    private static void iniciarSesionAutomatica(AuthFacade authFacade, StreamingServiceManager serviceManager, ContextoAutenticacion contexto, Scanner scanner) {
        Usuario usuario = contexto.getUsuarioAutenticado(); // Obtener el objeto Usuario directamente
        if (usuario != null) {
            System.out.println("Continuando como " + usuario.getNombre() + "...");
            MenuUsuario menuUsuario = new MenuUsuario(authFacade, serviceManager, scanner, usuario, contexto);
            menuUsuario.mostrarMenu();
        } else {
            System.out.println("Error al recuperar el usuario. Por favor, vuelva a iniciar sesión.");
            contexto.cerrarSesion();
        }
    }


    private static void registrarse(AuthFacade authFacade, Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        String nombreCompleto = nombre + " " + apellidos;

        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        if (authFacade.registrarUsuario(nombreCompleto, correo, contrasena)) {
            System.out.println("Registro exitoso. Ahora puede iniciar sesión.");
        } else {
            System.out.println("El correo ya está registrado.");
        }
    }
}
