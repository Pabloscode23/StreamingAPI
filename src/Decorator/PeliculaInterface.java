package Decorator;

/**
 * Interfaz que define los métodos básicos para la clase Pelicula.
 * Esta interfaz es implementada por la clase Pelicula y sus decoradores,
 * permitiendo que se puedan realizar operaciones sobre objetos de tipo Pelicula.
 */
public interface PeliculaInterface {

    /**
     * Obtiene el código único de la película.
     *
     * @return el código de la película.
     */
    int getCodigo();

    /**
     * Obtiene el nombre de la película.
     *
     * @return el nombre de la película.
     */
    String getNombre();

    /**
     * Establece el código de la película.
     *
     * @param codigo el nuevo código de la película.
     */
    void setCodigo(int codigo);

    /**
     * Establece el nombre de la película.
     *
     * @param nombre el nuevo nombre de la película.
     */
    void setNombre(String nombre);
}
