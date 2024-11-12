package menu;

import classes.Usuario;
import classes.Suscripcion;
import classes.Proveedor;
import classes.Plan;
import service.StreamingServiceManager;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

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

    private static final String archivoSuscripciones = "suscripcion.txt";
    private static final String archivoProveedores = "proveedores.txt";
    private static final String archivoPlanes = "plan.txt";



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

    private void verSuscripciones() {
        List<Suscripcion> suscripciones = cargarSuscripciones();
        if (suscripciones.isEmpty()) {
            System.out.println("No tienes suscripciones.");
        } else {
            System.out.println("\n=== Tus Suscripciones ===");
            for (Suscripcion s : suscripciones) {
                System.out.println(s);
            }
        }
    }

    private void agregarSuscripcion() {

        /**
         * Primero, mostramos los proveedores disponibles
         */
        List<Proveedor> proveedores = cargarProveedores();
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores disponibles.");
            return;
        }

        System.out.println("\n=== Proveedores Disponibles ===");
        for (int i = 0; i < proveedores.size(); i++) {
            System.out.println((i + 1) + ". " + proveedores.get(i).getNombre());
        }

        System.out.print("Seleccione el número del proveedor: ");
        int opcionProveedor = scanner.nextInt();
        scanner.nextLine();

        if (opcionProveedor < 1 || opcionProveedor > proveedores.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Proveedor proveedorSeleccionado = proveedores.get(opcionProveedor - 1);
        /**
         * Luego, mostramos los planes disponibles para el proveedor seleccionado
         */

        List<Plan> planes = cargarPlanes(proveedorSeleccionado.getCodigo());
        if (planes.isEmpty()) {
            System.out.println("No hay planes disponibles para este proveedor.");
            return;
        }

        System.out.println("\n=== Planes Disponibles para " + proveedorSeleccionado.getNombre() + " ===");
        for (int i = 0; i < planes.size(); i++) {
            Plan plan = planes.get(i);
            System.out.println((i + 1) + ". " + plan.getNombre() + " - Precio: " + plan.getPrecio());
        }

        System.out.print("Seleccione el número del plan: ");
        int opcionPlan = scanner.nextInt();
        scanner.nextLine();

        if (opcionPlan < 1 || opcionPlan > planes.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Plan planSeleccionado = planes.get(opcionPlan - 1);

        /**
         * Solicitar la duración de la suscripción (meses)
         */

        System.out.print("Ingrese la cantidad de meses para la suscripción: ");
        int meses = scanner.nextInt();
        scanner.nextLine();

        /**
         * Calcular el precio de la suscripción en función del plan y los meses
         */

        float precio = calcularPrecioSuscripcion(planSeleccionado.getCodigo(), meses, planSeleccionado.getPrecio());

        // Definir si la suscripción está activa
        boolean activo = true;

        /**
         * Crear la nueva suscripción
         */

        Suscripcion nuevaSuscripcion = new Suscripcion(
                usuario.getSuscripciones().size() + 1,  // Código único para la suscripción
                usuario.getCodigo(),  // Código del usuario
                proveedorSeleccionado.getCodigo(),  // Código del proveedor
                planSeleccionado.getCodigo(),  // Código del plan
                meses,
                precio,
                activo
        );

        /**
         * Guardar la nueva suscripción
         */

        guardarSuscripcion(nuevaSuscripcion);
        System.out.println("Suscripción agregada exitosamente.");
    }
    /**
     * Método para obtener el precio de un plan desde el archivo txt
     */
    private float obtenerPrecioPorPlan(int codigoPlan) {
        File archivoPlanes = new File("plan.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoPlanes))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                int codigo = Integer.parseInt(datos[0]);
                if (codigo == codigoPlan) {
                    return Float.parseFloat(datos[1]);  // Devolver el precio si el código coincide
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de planes: " + e.getMessage());
        }
        return 0.0f;  // Si no se encuentra el plan, devolver 0.0f
    }
    /**
     * Método para calcular el precio de la suscripción basado en el plan y los meses.
     */

    private float calcularPrecioSuscripcion(int codigoPlan, int meses, float precio) {
        // Calcular el precio multiplicando el precio base por los meses
        return precio * meses;
    }

    /**
     * Permite al usuario cancelar una de sus suscripciones activas.
     */


    private void cancelarSuscripcion() {
        List<Suscripcion> suscripciones = cargarSuscripciones();
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
            suscripciones.remove(suscripcion);  // Eliminar la suscripción de la lista
            guardarSuscripciones(suscripciones);  // Guardar la lista actualizada en el archivo
            System.out.println("Suscripción cancelada exitosamente.");
        }
    }
    /**
     * Método para cargar las suscripciones desde el archivo txt
     */

    private List<Suscripcion> cargarSuscripciones() {
        List<Suscripcion> suscripciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoSuscripciones))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 7) {
                    int codigo = Integer.parseInt(datos[0]);
                    int codigoUsuario = Integer.parseInt(datos[1]);
                    int codigoProveedor = Integer.parseInt(datos[2]);
                    int codigoPlan = Integer.parseInt(datos[3]);
                    float precio = Float.parseFloat(datos[4]);
                    int meses = Integer.parseInt(datos[5]);
                    boolean activo = Boolean.parseBoolean(datos[6]);

                    Suscripcion suscripcion = new Suscripcion(codigo, codigoUsuario, codigoProveedor, codigoPlan, meses, precio, activo);
                    suscripciones.add(suscripcion);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return suscripciones;
    }
    /**
     * Método para cargar los proveedores desde el archivo txt
     */

    private List<Proveedor> cargarProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoProveedores))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    int codigo = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String url = datos[2];

                    Proveedor proveedor = new Proveedor(codigo, nombre, url);
                    proveedores.add(proveedor);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de proveedores: " + e.getMessage());
        }
        return proveedores;
    }

    private List<Plan> cargarPlanes(int codigoProveedorSeleccionado) {
        List<Plan> planes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoPlanes))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    int codigo = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    float precio = Float.parseFloat(datos[2]);
                    int codigoProveedorArchivo = Integer.parseInt(datos[3]); // Cambié el nombre de la variable aquí

                    // Filtrar solo planes que coincidan con el código del proveedor
                    if (codigoProveedorArchivo == codigoProveedorSeleccionado) {
                        Plan plan = new Plan(codigo, nombre, precio, codigoProveedorArchivo);
                        planes.add(plan);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de planes: " + e.getMessage());
        }
        return planes;
    }

    /**
     * Método para guardar una suscripción en el archivo txt
     */

    private void guardarSuscripcion(Suscripcion suscripcion) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSuscripciones, true))) {
            writer.write(suscripcion.getCodigo() + ","
                    + suscripcion.getCodigoUsuario() + ","
                    + suscripcion.getCodigoProveedor() + ","
                    + suscripcion.getCodigoPlan() + ","
                    + suscripcion.getPrecio() + ","
                    + suscripcion.getMeses() + ","
                    + suscripcion.isActivo());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar la suscripción: " + e.getMessage());
        }
    }

    /**
     * Guardar las suscripciones actualizadas en el archivo txt
     */
    private void guardarSuscripciones(List<Suscripcion> suscripciones) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSuscripciones))) {
            for (Suscripcion suscripcion : suscripciones) {
                writer.write(suscripcion.getCodigo() + ","
                        + suscripcion.getCodigoUsuario() + ","
                        + suscripcion.getCodigoProveedor() + ","
                        + suscripcion.getCodigoPlan() + ","
                        + suscripcion.getPrecio() + ","
                        + suscripcion.getMeses() + ","
                        + suscripcion.isActivo());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las suscripciones: " + e.getMessage());
        }
    }
}
