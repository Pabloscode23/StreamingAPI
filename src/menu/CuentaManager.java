package menu;

import facade.AuthFacade;
import classes.Usuario;
import service.StreamingServiceManager;

import java.util.Scanner;

/**
 * Clase encargada de administrar las opciones de la cuenta de usuario, incluyendo
 * la visualización de información, el cambio de contraseña y la gestión de suscripciones.
 */
public class CuentaManager {

    /**
     * Instancia de la fachada de autenticación para interactuar con los servicios de usuario.
     */
    private AuthFacade authFacade;

    /**
     * Escáner para leer la entrada del usuario desde la consola.
     */
    private Scanner scanner;

    /**
     * Usuario que ha iniciado sesión y está interactuando con la cuenta.
     */
    private Usuario usuario;

    /**
     * Constructor que inicializa CuentaManager con la fachada de autenticación, el gestor de servicios de streaming,
     * el escáner y el usuario actual.
     *
     * @param authFacade    Fachada de autenticación para realizar operaciones de usuario.
     * @param serviceManager Gestor de servicios de streaming.
     * @param scanner       Escáner para la entrada de usuario.
     * @param usuario       Usuario actual que está administrando su cuenta.
     */
    public CuentaManager(AuthFacade authFacade, StreamingServiceManager serviceManager, Scanner scanner, Usuario usuario) {
        this.authFacade = authFacade;
        this.scanner = scanner;
        this.usuario = usuario;
    }

    /**
     * Muestra el menú de administración de la cuenta y permite al usuario
     * elegir opciones para ver información de la cuenta, cambiar la contraseña
     * o gestionar sus suscripciones.
     */
    public void administrarCuenta() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Administrar Cuenta ===");
            System.out.println("1. Ver Información de la Cuenta");
            System.out.println("2. Cambiar Contraseña");
            System.out.println("3. Manejar Suscripciones");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    verInformacionCuenta();
                    break;
                case 2:
                    cambiarContrasena();
                    break;
                case 3:
                    SuscripcionManager suscripcionManager = new SuscripcionManager(scanner, usuario, authFacade.getStreamingServiceManager());
                    suscripcionManager.manejarSuscripciones();
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida, intente nuevamente.");
            }
        }
    }

    /**
     * Muestra la información básica de la cuenta del usuario, como nombre y correo.
     */
    private void verInformacionCuenta() {
        System.out.println("\n=== Información de la Cuenta ===");
        System.out.println("Nombre: " + usuario.getNombre());
        System.out.println("Correo: " + usuario.getCorreo());
    }

    /**
     * Permite al usuario cambiar su contraseña si proporciona correctamente la contraseña actual.
     * La nueva contraseña debe coincidir con la confirmación para que el cambio sea efectivo.
     */
    private void cambiarContrasena() {
        System.out.print("Ingrese su contraseña actual: ");
        String contrasenaActual = scanner.nextLine();
        if (!usuario.getContrasena().equals(contrasenaActual)) {
            System.out.println("La contraseña actual no es correcta.");
            return;
        }
        System.out.print("Ingrese su nueva contraseña: ");
        String nuevaContrasena = scanner.nextLine();
        System.out.print("Confirme su nueva contraseña: ");
        String confirmacion = scanner.nextLine();

        if (!nuevaContrasena.equals(confirmacion)) {
            System.out.println("La nueva contraseña y su confirmación no coinciden.");
            return;
        }

        authFacade.cambiarContrasena(usuario, nuevaContrasena);
        System.out.println("Contraseña actualizada exitosamente.");
    }
}
