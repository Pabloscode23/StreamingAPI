package decorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorador que agrega la funcionalidad de calificación a una película.
 * Permite agregar calificaciones a una película y calcular el promedio de estas calificaciones.
 * Este decorador extiende la funcionalidad de la película sin modificar la clase original.
 */
public class CalificacionDecorator extends PeliculaDecorada {
    // Lista para almacenar las calificaciones de la película
    private List<Double> calificaciones;

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
     * Agrega una calificación a la lista de calificaciones de la película.
     * La calificación debe estar entre 0 y 10, de lo contrario, se lanza una excepción.
     *
     * @param calificacion la calificación a agregar (debe estar entre 0 y 10).
     * @throws IllegalArgumentException si la calificación no está entre 0 y 10.
     */
    public void agregarCalificacion(double calificacion) {
        if (calificacion >= 0 && calificacion <= 10) {
            calificaciones.add(calificacion);
        } else {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 10.");
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
