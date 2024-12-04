package factory;

import model.StreamingService;

/**
 * Interfaz que define el método de creación para los servicios de streaming.
 *
 * El patrón de diseño utilizado en esta interfaz es **Abstract Factory**. Este patrón
 * permite la creación de diferentes tipos de servicios de streaming sin especificar
 * la clase exacta de la instancia que se creará. Las fábricas concretas implementan
 * esta interfaz para crear servicios específicos.
 */
public interface AbstractStreamingFactory {

    /**
     * Método para crear una instancia de un servicio de streaming específico.
     *
     * Este método es implementado por las fábricas concretas para crear un servicio
     * de streaming, como {@link StreamingAvailabilityService} o {@link WatchModeService}.
     *
     * @return Una instancia de {@link StreamingService}, representando el servicio de streaming creado.
     */
    StreamingService crearServicio();
}
