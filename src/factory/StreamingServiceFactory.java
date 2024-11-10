package factory;

import model.StreamingService;

/**
 * Esta interfaz define un método de fábrica para crear instancias de servicios de streaming.
 *
 * El patrón de diseño utilizado en esta interfaz es el **Factory Method**.
 *
 * El propósito de este patrón es proporcionar una interfaz para crear objetos sin especificar
 * la clase exacta del objeto que se creará. Las subclases implementan este método para decidir
 * qué tipo de objeto crear.
 */
public interface StreamingServiceFactory {

    /**
     * Método que crea y devuelve una instancia de un servicio de streaming específico.
     *
     * Este método sigue el patrón de diseño Factory Method, donde el proceso de creación
     * de un objeto es delegado a una subclase que decide qué tipo de objeto devolver.
     *
     * @return Un objeto de tipo StreamingService, que representa un servicio de streaming.
     */
    StreamingService crearServicio();
}
