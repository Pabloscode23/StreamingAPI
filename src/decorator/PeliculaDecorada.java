package decorator;

/**
 * Clase abstracta que implementa la interfaz {@link PeliculaInterface} y proporciona una
 * estructura base para los decoradores de películas.
 * Esta clase permite agregar funcionalidades adicionales a las películas sin modificar
 * directamente la clase original.
 * Los decoradores deben extender esta clase y sobrecargar los métodos según sea necesario.
 */
public abstract class PeliculaDecorada implements PeliculaInterface {
    // Instancia de la película que se va a decorar
    protected PeliculaInterface pelicula;

    /**
     * Constructor que recibe una película que será decorada.
     *
     * @param pelicula la película que se va a decorar.
     */
    public PeliculaDecorada(PeliculaInterface pelicula) {
        this.pelicula = pelicula;
    }

    /**
     * Obtiene el código único de la película decorada.
     *
     * @return el código de la película decorada.
     */
    @Override
    public int getCodigo() {
        return pelicula.getCodigo();
    }

    /**
     * Obtiene el nombre de la película decorada.
     *
     * @return el nombre de la película decorada.
     */
    @Override
    public String getNombre() {
        return pelicula.getNombre();
    }

    /**
     * Establece el código de la película decorada.
     *
     * @param codigo el nuevo código de la película decorada.
     */
    @Override
    public void setCodigo(int codigo) {
        pelicula.setCodigo(codigo);
    }

    /**
     * Establece el nombre de la película decorada.
     *
     * @param nombre el nuevo nombre de la película decorada.
     */
    @Override
    public void setNombre(String nombre) {
        pelicula.setNombre(nombre);
    }
}
