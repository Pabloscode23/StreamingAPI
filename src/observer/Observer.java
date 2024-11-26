package observer;

/**
 * Interfaz que define el comportamiento de un observador.
 *
 * El patrón **Observer** se utiliza para permitir que un objeto (llamado sujeto o *subject*)
 * notifique a sus dependientes (observadores) sobre cambios en su estado sin que los observadores
 * necesiten estar directamente acoplados al sujeto.
 *
 * Esta interfaz permite a los objetos ser notificados mediante el método `actualizar()`.
 */
public interface Observer {

    /**
     * Método para actualizar el estado del observador con un mensaje recibido.
     *
     * @param message El mensaje que contiene la información que se va a comunicar al observador.
     * El mensaje puede representar cualquier tipo de cambio que haya ocurrido en el sujeto.
     */
    void update(String message); // Método para recibir actualizaciones
}
