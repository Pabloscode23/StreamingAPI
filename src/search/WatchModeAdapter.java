package search;
import java.util.Collection;
import java.util.Vector;

public class WatchModeAdapter implements StreamingServiceAdapter {
    private WatchModeService servicio;

    public WatchModeAdapter(WatchModeService servicio) {
        this.servicio = servicio;
    }

    @Override
    public void configurar(Vector<String> configParams) {
        servicio.configurar(configParams);
    }

    @Override
    public Collection<SearchResult> buscar(String query, Vector<String> searchParams) {
        return servicio.buscar(query, searchParams);
    }

    @Override
    public Collection<SearchResult> consultar(String query, Vector<String> consultParams) {
        return servicio.consultar(query, consultParams);
    }
}
