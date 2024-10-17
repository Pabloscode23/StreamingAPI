package classes;

/**
 * Interfaz para la búsqueda de contenido.
 * Esta interfaz define los métodos que deben ser implementados por cualquier
 * clase que realice búsquedas de contenido, permitiendo buscar y mostrar resultados.
 */
public interface Buscador {

    /**
     * Realiza una búsqueda del contenido basado en una consulta.
     *
     * @param query el término de búsqueda que se utilizará para buscar contenido.
     */
    void buscar(String query);

    /**
     * Muestra los resultados de la búsqueda.
     *
     * @return una cadena que representa los resultados de la búsqueda realizada.
     */
    String mostrar();
}
