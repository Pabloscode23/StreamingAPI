package adapter;

import model.SearchResult;
import java.util.Collection;
import java.util.Vector;

/**
 * Esta interfaz define los métodos para interactuar con un servicio de streaming adaptado.
 * El patrón de diseño utilizado es el **Adapter**.
 *
 * El propósito de esta interfaz es permitir que clases que implementen distintos servicios de streaming
 * puedan ser utilizados de forma consistente por otras partes del sistema, sin que el código dependiente
 * necesite conocer los detalles específicos de cada implementación del servicio de streaming.
 *
 * El patrón Adapter permite que clases con interfaces incompatibles puedan trabajar juntas al
 * proporcionar una interfaz común que adapte las diferencias.
 */
public interface StreamingServiceAdapter {

    /**
     * Configura el servicio de streaming con los parámetros dados.
     *
     * @param configParams Los parámetros de configuración necesarios para el servicio.
     */
    void configurar(Vector<String> configParams);

    /**
     * Realiza una búsqueda en el servicio de streaming utilizando los parámetros de búsqueda proporcionados.
     *
     * @param query La cadena de búsqueda que se desea realizar.
     * @param searchParams Los parámetros adicionales para la búsqueda.
     * @return Una colección de objetos SearchResult que representan los resultados de la búsqueda.
     */
    Collection<SearchResult> buscar(String query, Vector<String> searchParams);

    /**
     * Realiza una consulta en el servicio de streaming utilizando los parámetros de consulta proporcionados.
     *
     * @param query La consulta que se desea realizar.
     * @param consultParams Los parámetros adicionales para la consulta.
     * @return Una colección de objetos SearchResult que representan los resultados de la consulta.
     */
    Collection<SearchResult> consultar(String query, Vector<String> consultParams);
}









