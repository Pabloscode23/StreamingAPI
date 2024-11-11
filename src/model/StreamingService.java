package model;

import java.util.Collection;
import java.util.Vector;

/**
 * Interfaz que representa los servicios de streaming para buscar y consultar contenido.
 *
 * Define los métodos generales que deben implementar los servicios de streaming
 * específicos para realizar búsquedas y consultas de películas o series, además de configurar
 * el servicio con parámetros determinados.
 *
 * Este diseño sigue el patrón **Strategy**, permitiendo intercambiar el comportamiento
 * del servicio de streaming según la implementación concreta elegida.
 */
public interface StreamingService {

    /**
     * Configura el servicio específico de streaming con los parámetros proporcionados.
     *
     * @param configParams Colección de parámetros de configuración del servicio.
     *                     Estos parámetros pueden ser específicos de cada implementación de servicio de streaming.
     */
    void configurar(Collection<String> configParams);

    /**
     * Realiza una búsqueda de contenido en el servicio de streaming.
     *
     * @param query        El nombre del contenido (película o serie) o parte del nombre para realizar la búsqueda.
     * @param configParams Parámetros adicionales para personalizar la búsqueda, específicos para cada servicio.
     * @return Una colección de resultados de búsqueda que coinciden con los criterios dados.
     */
    Collection<SearchResult> buscar(String query, Vector<String> configParams);

    /**
     * Realiza una búsqueda avanzada en el servicio de streaming, filtrando por tipo de contenido,
     * región y plataforma.
     *
     * @param query         El nombre del contenido a buscar.
     * @param tipoContenido El tipo de contenido, como "movie" para películas o "tv_movie" para series.
     * @param region        Código de región para limitar la búsqueda (ej., "US" para Estados Unidos).
     * @param sourceId      ID de la plataforma de streaming (ej., ID específico para Netflix, Hulu, etc.).
     * @return Una colección de resultados de búsqueda que cumplen con los filtros avanzados.
     */
    Collection<SearchResult> buscarConFiltrosAvanzados(String query, String tipoContenido, String region, int sourceId);
}
