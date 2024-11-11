import facade.AuthFacade;
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
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("=== Menú Principal ===");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    Usuario usuario = iniciarSesion(authFacade, scanner);
                    if (usuario != null) {
                        MenuUsuario menuUsuario = new MenuUsuario(authFacade, serviceManager, scanner, usuario);
                        menuUsuario.mostrarMenu();
                    }
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
        scanner.close();
    }

    /**
     * Método auxiliar para iniciar sesión en el sistema.
     * Solicita al usuario ingresar su correo y contraseña, y valida las credenciales.
     *
     * @param authFacade Fachada de autenticación utilizada para validar credenciales.
     * @param scanner    Escáner para leer la entrada del usuario.
     * @return El objeto Usuario si las credenciales son válidas, o null si son incorrectas.
     */
    private static Usuario iniciarSesion(AuthFacade authFacade, Scanner scanner) {
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        Usuario usuario = authFacade.iniciarSesion(correo, contrasena);
        if (usuario != null) {
            System.out.println("Inicio de sesión exitoso. ¡Bienvenido, " + usuario.getNombre() + "!");
            return usuario;
        } else {
            System.out.println("Correo o contraseña incorrectos.");
            return null;
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
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        if (authFacade.registrarUsuario(nombreCompleto, correo, contrasena)) {
            System.out.println("Registro exitoso. Ahora puede iniciar sesión.");
        } else {
            System.out.println("El correo ya está registrado.");
        }
    }
}
