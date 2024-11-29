package decorator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Decorador que agrega la funcionalidad de calificación a una película.
 * Permite agregar calificaciones a una película, calcular el promedio de estas calificaciones,
 * y guardar las calificaciones en un archivo.
 * Este decorador extiende la funcionalidad de la película sin modificar la clase original.
 */
public class CalificacionDecorator extends PeliculaDecorada {
    // Lista para almacenar las calificaciones de la película
    private List<Double> calificaciones;
    private static final String NOMBRE_ARCHIVO = "calificaciones.txt";

    /**
     * Constructor que recibe una película y la decora con la capacidad de calificación.
     *
     * @param pelicula la película que se va a decorar con la funcionalidad de calificación.
     */
    public CalificacionDecorator(PeliculaInterface pelicula) {
        super(pelicula);
        this.calificaciones = new ArrayList<>();
    }

    /**
     * Agrega una calificación a la lista de calificaciones de la película
     * y la guarda en el archivo.
     *
     * @param calificacion la calificación a agregar (debe estar entre 0 y 10).
     * @throws IllegalArgumentException si la calificación no está entre 0 y 10.
     */
    public void agregarCalificacion(double calificacion) {
        if (calificacion >= 0 && calificacion <= 10) {
            calificaciones.add(calificacion);
            guardarCalificacionEnArchivo(calificacion);
        } else {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 10.");
        }
    }

    /**
     * Guarda una calificación en el archivo de texto.
     *
     * @param calificacion la calificación a guardar.
     */
    private void guardarCalificacionEnArchivo(double calificacion) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            escritor.write("Película: " + pelicula.getNombre() + " - Calificación: " + calificacion);
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar la calificación en el archivo: " + e.getMessage());
        }
    }

    /**
     * Calcula y devuelve el promedio de las calificaciones de la película.
     *
     * @return el promedio de las calificaciones o 0.0 si no hay calificaciones.
     */
    public double getPromedioCalificaciones() {
        if (calificaciones.isEmpty()) {
            return 0.0;
        }
        return calificaciones.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /**
     * Obtiene el nombre de la película junto con su calificación promedio.
     *
     * @return el nombre de la película y su calificación promedio en formato "Pelicula - Calificación promedio".
     */
    @Override
    public String getNombre() {
        return pelicula.getNombre() + " - Calificación promedio: " + getPromedioCalificaciones();
    }
}
