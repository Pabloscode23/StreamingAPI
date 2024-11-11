package adapter;

import model.SearchResult;
import service.WatchModeService;

import java.util.Collection;
import java.util.Vector;

/**
 * Clase adaptadora que convierte la interfaz del servicio de streaming específico (WatchModeService)
 * a una interfaz común definida por StreamingServiceAdapter.
 *
 * Utiliza el patrón de diseño **Adapter** para permitir que el servicio WatchModeService
 * funcione en conjunto con el sistema que usa StreamingServiceAdapter.
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
     * Este método delega la configuración al servicio de WatchMode.
     *
     * @param configParams Parámetros de configuración que serán pasados al servicio para ajustar sus opciones.
     */
    @Override
    public void configurar(Vector<String> configParams) {
        servicio.configurar(configParams);
    }

    /**
     * Realiza una búsqueda en el servicio de streaming utilizando la consulta y parámetros de búsqueda proporcionados.
     *
     * Este método delega la búsqueda general al servicio de WatchMode.
     *
     * @param query Consulta de búsqueda en forma de texto.
     * @param searchParams Parámetros adicionales de búsqueda.
     * @return Una colección de resultados de búsqueda obtenidos del servicio.
     */
    @Override
    public Collection<SearchResult> buscar(String query, Vector<String> searchParams) {
        return servicio.buscar(query, searchParams);
    }

    /**
     * Realiza una búsqueda con filtros avanzados en el servicio de streaming.
     *
     * Este método permite buscar contenido filtrado por título, tipo de contenido,
     * región y plataforma específica (ID de la fuente de contenido).
     *
     * @param query Título a buscar en el catálogo del servicio.
     * @param tipoContenido Tipo de contenido, como película o programa de TV.
     *                      Debe especificarse como "movie" o "tv_movie" para la compatibilidad con el servicio.
     * @param region Código de región, por ejemplo "US" para Estados Unidos o "GB" para el Reino Unido.
     * @param sourceId Identificador de la plataforma de streaming (fuente del contenido).
     * @return Una colección de resultados de búsqueda filtrados según los parámetros avanzados.
     */
    public Collection<SearchResult> buscarConFiltrosAvanzados(String query, String tipoContenido, String region, int sourceId) {
        return servicio.buscarConFiltrosAvanzados(query, tipoContenido, region, sourceId);
    }
}
