package factory;

import model.StreamingService;
import service.StreamingAvailabilityService;

/**
 * Fábrica concreta que implementa la interfaz {@link AbstractStreamingFactory}
 * para crear una instancia del servicio de streaming de tipo {@link StreamingAvailabilityService}.
 *
 * Esta clase sigue el patrón de diseño **Abstract Factory**, donde la fábrica concreta
 * se encarga de crear una instancia específica de un servicio de streaming, en este caso,
 * un servicio de tipo {@link StreamingAvailabilityService}.
 */
public class StreamingAvailabilityFactory implements AbstractStreamingFactory {

    /**
     * Crea e inicializa una nueva instancia del servicio de streaming de tipo {@link StreamingAvailabilityService}.
     *
     * Este método es parte del patrón **Factory Method** implementado en la interfaz {@link AbstractStreamingFactory}.
     *
     * @return Una nueva instancia de {@link StreamingAvailabilityService}, que es una implementación de {@link StreamingService}.
     */
    @Override
    public StreamingService crearServicio() {
        return new StreamingAvailabilityService();
    }
}
