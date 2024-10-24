package org.search;

import java.util.Collection;
import java.util.Vector;

public interface StreamingService {

    /**
     * Configuración para servicio específico de streaming
     * @param configParams Collection<String> Parámetros de configuración del servicio
     */
    void configurar(Collection<String> configParams);

    /**
     * Consultar una película en específico
     * @param query String Nombre de película
     * @param configParams Collection Contiene parametros para filtro
     * @return
     */
    Collection<SearchResult> consultar(String query, Vector<String> configParams);

    /**
     * Consultar para múltiples películas
     * @param query String Nombre de película o parte de nombre para buscar películas
     * @param configParams Collection Contiene parametros para filtro
     * @return
     */
    Collection<SearchResult> buscar(String query, Vector<String> configParams);
}