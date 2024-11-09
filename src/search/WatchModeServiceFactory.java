package search;

public class WatchModeServiceFactory implements StreamingServiceFactory {
    @Override
    public StreamingService crearServicio() {
        return new WatchModeService();
    }
}
