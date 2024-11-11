package menu;

import classes.Usuario;
import classes.Suscripcion;
import service.StreamingServiceManager;
import java.util.Scanner;


/* Hola Isabela, este queda más en vos y como querás que funcione, esto lo hice más que nada para no ver algo
vacio cuando lo estaba haciendo, puedes cambiar lo que gustes menos la estetica del menú, eso es para que los menús se ven similares
entre sí

Yo estoy guardando los usuarios en txt, puedes hacerlo tambien así con las suscripciones, es un tipo de base de datos
y si no me equivoco quedan guardados en el proyecto

Vaya al final de la clase StreamingServiceManager que ahi fue donde puse un poco de cosas de las suscripciones pero creo que no era el mejor lugar para colocarlo*/


/**
 * Clase encargada de gestionar las suscripciones de un usuario, permitiendo ver, agregar o cancelar
 * suscripciones activas.
 */
public class SuscripcionManager {

    /**
     * Escáner para leer la entrada del usuario desde la consola.
     */
    private Scanner scanner;

    /**
     * Usuario que está gestionando sus suscripciones.
     */
    private Usuario usuario;

    /**
     * Gestor de servicios de streaming para manejar las suscripciones.
     */
    private StreamingServiceManager serviceManager;

    /**
     * Constructor que inicializa el gestor de suscripciones con el usuario actual,
     * el escáner y el gestor de servicios de streaming.
     *
     * @param scanner       Escáner para la entrada del usuario.
     * @param usuario       Usuario actual que está gestionando sus suscripciones.
     * @param serviceManager Gestor de servicios de streaming.
     */
    public SuscripcionManager(Scanner scanner, Usuario usuario, StreamingServiceManager serviceManager) {
        this.scanner = scanner;
        this.usuario = usuario;
        this.serviceManager = serviceManager;
    }

    /**
     * Muestra el menú para gestionar suscripciones, permitiendo al usuario ver, agregar o cancelar suscripciones.
     */
    public void manejarSuscripciones() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Manejar Suscripciones ===");
            System.out.println("1. Ver Suscripciones");
            System.out.println("2. Agregar Suscripción");
            System.out.println("3. Cancelar Suscripción");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    verSuscripciones();
                    break;
                case 2:
                    agregarSuscripcion();
                    break;
                case 3:
                    cancelarSuscripcion();
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
     * Muestra la lista de suscripciones activas del usuario.
     */
    private void verSuscripciones() {
        var suscripciones = serviceManager.obtenerSuscripciones(usuario);
        if (suscripciones.isEmpty()) {
            System.out.println("No tienes suscripciones.");
        } else {
            System.out.println("\n=== Tus Suscripciones ===");
            for (Suscripcion s : suscripciones) {
                System.out.println(s);
            }
        }
    }

    /**
     * Agrega una nueva suscripción al usuario, solicitando información básica del proveedor y el plan.
     */
    private void agregarSuscripcion() {
        System.out.println("Ingrese el nombre del proveedor de la suscripción:");
        String nombreProveedor = scanner.nextLine();
        System.out.println("Ingrese el nombre del plan de la suscripción:");
        String nombrePlan = scanner.nextLine();

        Suscripcion nuevaSuscripcion = new Suscripcion(
                usuario.getSuscripciones().size() + 1,
                usuario.getCodigo(),
                1,
                1,
                1,
                9.99f,
                true
        );

        serviceManager.agregarSuscripcion(usuario, nuevaSuscripcion);
        System.out.println("Suscripción agregada exitosamente.");
    }

    /**
     * Permite al usuario cancelar una de sus suscripciones activas.
     */
    private void cancelarSuscripcion() {
        var suscripciones = serviceManager.obtenerSuscripciones(usuario);
        if (suscripciones.isEmpty()) {
            System.out.println("No tienes suscripciones para cancelar.");
            return;
        }
        System.out.println("\n=== Tus Suscripciones ===");
        for (int i = 0; i < suscripciones.size(); i++) {
            System.out.println((i + 1) + ". " + suscripciones.get(i));
        }
        System.out.print("Seleccione el número de la suscripción a cancelar: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion < 1 || opcion > suscripciones.size()) {
            System.out.println("Opción inválida.");
        } else {
            Suscripcion suscripcion = suscripciones.get(opcion - 1);
            serviceManager.cancelarSuscripcion(usuario, suscripcion);
            System.out.println("Suscripción cancelada exitosamente.");
        }
    }


}
