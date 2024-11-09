package adapter;

import model.SearchResult;
import service.WatchModeService;

import java.util.Collection;
import java.util.Vector;

/**
 * Esta clase adapta la interfaz de un servicio específico de streaming (WatchModeService)
 * a la interfaz común StreamingServiceAdapter.
 *
 * El patrón de diseño utilizado en esta clase es el **Adapter**.
 *
 * El propósito del patrón Adapter es permitir que dos interfaces incompatibles puedan trabajar juntas.
 * En este caso, adaptamos la interfaz de WatchModeService a la interfaz estándar StreamingServiceAdapter.
 */
public class WatchModeAdapter implements StreamingServiceAdapter {

    /**
     * Instancia del servicio WatchMode que será adaptado.
     */
    private WatchModeService servicio;

    /**
     * Constructor de la clase WatchModeAdapter.
     *
     * @param servicio Instancia de WatchModeService que será adaptada.
     */
    public WatchModeAdapter(WatchModeService servicio) {
        this.servicio = servicio;
    }

    /**
     * Configura el servicio de streaming utilizando los parámetros de configuración proporcionados.
     *
     * Delegamos la configuración al servicio de WatchMode.
     *
     * @param configParams Parámetros de configuración que serán pasados al servicio.
     */
    @Override
    public void configurar(Vector<String> configParams) {
        servicio.configurar(configParams);
    }

    /**
     * Realiza una búsqueda en el servicio de streaming utilizando la consulta y parámetros de búsqueda proporcionados.
     *
     * Delegamos la búsqueda al servicio de WatchMode.
     *
     * @param query Consulta de búsqueda.
     * @param searchParams Parámetros de búsqueda.
     * @return Una colección de resultados de búsqueda.
     */
    @Override
    public Collection<SearchResult> buscar(String query, Vector<String> searchParams) {
        return servicio.buscar(query, searchParams);
    }

    /**
     * Consulta información adicional en el servicio de streaming utilizando la consulta y parámetros de consulta proporcionados.
     *
     * Delegamos la consulta al servicio de WatchMode.
     *
     * @param query Consulta de información.
     * @param consultParams Parámetros de consulta.
     * @return Una colección de resultados de consulta.
     */
    @Override
    public Collection<SearchResult> consultar(String query, Vector<String> consultParams) {
        return servicio.consultar(query, consultParams);
    }
}
