package Decorator;

import classes.Pelicula;

public class PeliculaAdapter implements PeliculaInterface {
    private Pelicula pelicula;

    public PeliculaAdapter(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    @Override
    public int getCodigo() {
        return pelicula.getCodigo();
    }

    @Override
    public String getNombre() {
        return pelicula.getNombre();
    }

    @Override
    public void setCodigo(int codigo) {
        pelicula.setCodigo(codigo);
    }

    @Override
    public void setNombre(String nombre) {
        pelicula.setNombre(nombre);
    }
}
