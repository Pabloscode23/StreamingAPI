package factory;

import model.StreamingService;
import service.StreamingAvailabilityService;

/**
 * Esta clase es una implementación concreta de la interfaz StreamingServiceFactory,
 * que crea una instancia del servicio de streaming StreamingAvailabilityService.
 *
 * El patrón de diseño utilizado en esta clase es el **Factory Method**.
 *
 * El propósito de esta clase es proporcionar una forma de crear un servicio de streaming
 * específico (en este caso, el servicio de streaming StreamingAvailabilityService) sin que el cliente
 * tenga que preocuparse por cómo se crea este servicio.
 */
public class StreamingAvailabilityServiceFactory implements StreamingServiceFactory {

    /**
     * Método que crea y devuelve una instancia del servicio de streaming StreamingAvailabilityService.
     *
     * Este método implementa el patrón de diseño Factory Method, creando específicamente
     * una instancia de StreamingAvailabilityService.
     *
     * @return Un objeto de tipo StreamingService, que representa el servicio de streaming
     *         Streaming Availability.
     */
    @Override
    public StreamingService crearServicio() {
        return new StreamingAvailabilityService();
    }
}
