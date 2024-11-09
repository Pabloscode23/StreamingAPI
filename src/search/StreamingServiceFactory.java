package search;

public class StreamingServiceFactory {

    public static StreamingService crearServicio(String tipoServicio) {
        if (tipoServicio == null) {
            return null;
        }
        if (tipoServicio.equalsIgnoreCase("WATCHMODE")) {
            return new WatchModeService();
        }
        // Agregar más servicios si es necesario en el futuro
        return null;
    }
}
