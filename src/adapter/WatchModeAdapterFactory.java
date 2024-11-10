package adapter;

import service.WatchModeService;

/**
 * Esta clase implementa la interfaz StreamingServiceAdapterFactory y es responsable
 * de crear una instancia de un servicio adaptado específico (WatchModeAdapter).
 *
 * El patrón de diseño utilizado en esta clase es el **Factory Method**.
 *
 * El propósito de este patrón es proporcionar una interfaz para crear objetos en una clase base,
 * pero permitir que las subclases alteren el tipo de objeto que se crea.
 */
public class WatchModeAdapterFactory implements StreamingServiceAdapterFactory {

    /**
     * Crea una instancia de WatchModeAdapter que adapta la interfaz de WatchModeService
     * a la interfaz estándar StreamingServiceAdapter.
     *
     * Este método sigue el patrón Factory Method, donde el método de creación es delegada
     * a una subclase que decide el tipo exacto de objeto a crear.
     *
     * @return Un objeto StreamingServiceAdapter, en este caso, un WatchModeAdapter.
     */
    @Override
    public StreamingServiceAdapter crearServicioAdaptado() {
        // Crea y devuelve un nuevo WatchModeAdapter, que se configura con una instancia de WatchModeService.
        return new WatchModeAdapter(new WatchModeService());
    }
}
