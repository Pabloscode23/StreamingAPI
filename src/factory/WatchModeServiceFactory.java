package factory;

import model.StreamingService;
import service.WatchModeService;

/**
 * Esta clase es una implementación concreta de la interfaz StreamingServiceFactory,
 * que crea una instancia del servicio de streaming WatchModeService.
 *
 * El patrón de diseño utilizado en esta clase es el **Factory Method**.
 *
 * El propósito de esta clase es proporcionar una forma de crear un servicio de streaming
 * específico (en este caso, el servicio de streaming WatchModeService) sin que el cliente
 * tenga que preocuparse por cómo se crea este servicio.
 */
public class WatchModeServiceFactory implements StreamingServiceFactory {

    /**
     * Método que crea y devuelve una instancia del servicio de streaming WatchModeService.
     *
     * Este método implementa el patrón de diseño Factory Method, creando específicamente
     * una instancia de WatchModeService.
     *
     * @return Un objeto de tipo StreamingService, que representa el servicio de streaming
     *         WatchMode.
     */
    @Override
    public StreamingService crearServicio() {
        return new WatchModeService();
    }
}
