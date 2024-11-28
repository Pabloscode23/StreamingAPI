package decorator;

import classes.Pelicula;

/**
 * Clase adaptadora que implementa la interfaz {@link PeliculaInterface} y adapta un objeto
 * de tipo {@link Pelicula} para que pueda ser utilizado con el patrón Decorator.
 * Permite que una instancia de la clase {@link Pelicula} funcione como un objeto de tipo
 * {@link PeliculaInterface}, para que pueda ser decorada o manipulada como tal.
 */
public class PeliculaAdapter implements PeliculaInterface {
    // Instancia de la clase Pelicula que se adapta
    private Pelicula pelicula;

    /**
     * Constructor que recibe una película que será adaptada a la interfaz {@link PeliculaInterface}.
     *
     * @param pelicula la película que se adapta a la interfaz.
     */
    public PeliculaAdapter(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    /**
     * Obtiene el código único de la película adaptada.
     *
     * @return el código de la película adaptada.
     */
    @Override
    public int getCodigo() {
        return pelicula.getCodigo();
    }

    /**
     * Obtiene el nombre de la película adaptada.
     *
     * @return el nombre de la película adaptada.
     */
    @Override
    public String getNombre() {
        return pelicula.getNombre();
    }

    /**
     * Establece el código de la película adaptada.
     *
     * @param codigo el nuevo código de la película adaptada.
     */
    @Override
    public void setCodigo(int codigo) {
        pelicula.setCodigo(codigo);
    }

    /**
     * Establece el nombre de la película adaptada.
     *
     * @param nombre el nuevo nombre de la película adaptada.
     */
    @Override
    public void setNombre(String nombre) {
        pelicula.setNombre(nombre);
    }
}
