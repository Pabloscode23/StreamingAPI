package adapter;

/**
 * Esta interfaz define un método para crear un servicio de streaming adaptado.
 *
 * El patrón de diseño utilizado es el **Factory Method**.
 *
 * El objetivo de esta interfaz es proporcionar un mecanismo para que las subclases o clases que implementen
 * esta interfaz puedan crear instancias específicas de un servicio de streaming adaptado sin exponer
 * la lógica de creación al código cliente.
 */
public interface StreamingServiceAdapterFactory {

    /**
     * Crea y devuelve una instancia de un servicio de streaming adaptado.
     *
     * @return Una instancia de un servicio de streaming que implementa la interfaz StreamingServiceAdapter.
     */
    StreamingServiceAdapter crearServicioAdaptado();
}
