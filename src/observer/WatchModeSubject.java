package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que actúa como sujeto en el patrón Observer.
 * Mantiene una lista de observadores y les notifica cuando hay cambios.
 */
public class WatchModeSubject {
    // Lista que almacena los observadores registrados
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Agrega un nuevo observador a la lista de observadores.
     *
     * @param observer El observador que se desea agregar a la lista.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Elimina un observador de la lista de observadores.
     *
     * @param observer El observador que se desea eliminar de la lista.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica a todos los observadores registrados, enviándoles el mensaje proporcionado.
     *
     * @param message El mensaje que se enviará a todos los observadores.
     */
    public void notifyObservers(String message) {
        // Recorre la lista de observadores y llama a su método update
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
