package Decorator;

import java.util.ArrayList;
import java.util.List;

public class CalificacionDecorator extends PeliculaDecorada {
    private List<Double> calificaciones;

    public CalificacionDecorator(PeliculaInterface pelicula) {
        super(pelicula);
        this.calificaciones = new ArrayList<>();
    }

    public void agregarCalificacion(double calificacion) {
        if (calificacion >= 0 && calificacion <= 10) {
            calificaciones.add(calificacion);
        } else {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 10.");
        }
    }

    public double getPromedioCalificaciones() {
        if (calificaciones.isEmpty()) {
            return 0.0;
        }
        return calificaciones.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    @Override
    public String getNombre() {
        return pelicula.getNombre() + " - Calificación promedio: " + getPromedioCalificaciones();
    }
}
