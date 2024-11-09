package model;

import java.util.Collection;
import java.util.Vector;

/**
 * Interface que representa los servicios de streaming para buscar y consultar contenido.
 *
 * Esta interfaz define los métodos generales que deben implementar los servicios de streaming
 * específicos para realizar búsquedas y consultas de películas o series, además de configurar
 * el servicio con parámetros determinados.
 *
 * Este diseño sigue el patrón **Strategy**, ya que permite que el comportamiento del servicio
 * de streaming pueda ser intercambiado según la implementación concreta que se elija.
 */
public interface StreamingService {

    /**
     * Configura el servicio específico de streaming con los parámetros proporcionados.
     *
     * @param configParams Colección de parámetros de configuración del servicio.
     * Estos parámetros pueden ser específicos de cada implementación de servicio de streaming.
     */
    void configurar(Collection<String> configParams);

    /**
     * Consulta una película específica en el servicio de streaming.
     *
     * @param query El nombre de la película a consultar.
     * @param configParams Parámetros de configuración adicionales para personalizar la consulta.
     * @return Una colección de resultados de búsqueda que coinciden con el nombre de la película.
     */
    Collection<SearchResult> consultar(String query, Vector<String> configParams);

    /**
     * Realiza una búsqueda de varias películas en el servicio de streaming.
     *
     * @param query El nombre de la película o parte del nombre para realizar la búsqueda.
     * @param configParams Parámetros de configuración adicionales para personalizar la búsqueda.
     * @return Una colección de resultados de búsqueda que coinciden con los criterios dados.
     */
    Collection<SearchResult> buscar(String query, Vector<String> configParams);
}
