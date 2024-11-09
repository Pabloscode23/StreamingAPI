package search;

public class WatchModeAdapterFactory implements StreamingServiceAdapterFactory {
    @Override
    public StreamingServiceAdapter crearServicioAdaptado() {
        return new WatchModeAdapter(new WatchModeService());
    }
}