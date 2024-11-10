package observer;

/**
 * Implementación del observador en el patrón Observer.
 *
 * Esta clase actúa como un observador que recibe notificaciones de un sujeto.
 * Cuando un sujeto notifica un cambio, el método `actualizar()` es llamado
 * y el observador puede tomar la acción correspondiente con el mensaje recibido.
 *
 * En este caso, el observador simplemente imprime el mensaje recibido en la consola.
 */
public class ServicioNotificador implements Observer {

    /**
     * Este método es llamado cuando el sujeto notifica un cambio a sus observadores.
     *
     * En este ejemplo, el mensaje es simplemente impreso en la consola,
     * pero en una aplicación real, aquí podríamos realizar una acción más compleja,
     * como actualizar una interfaz de usuario o enviar una notificación a otro sistema.
     *
     * @param mensaje El mensaje que contiene la información que se quiere notificar.
     */
    @Override
    public void actualizar(String mensaje) {
        // Imprime el mensaje recibido, se puede cambiar por cualquier otra acción.
        System.out.println("Notificación: " + mensaje);
    }
}
