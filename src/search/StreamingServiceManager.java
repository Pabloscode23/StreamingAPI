package search;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class StreamingServiceManager {
    private static StreamingServiceManager instancia;
    private StreamingService servicioActual;
    private List<Observer> observadores;

    private StreamingServiceManager() {
        this.observadores = new Vector<>();
    }

    public static StreamingServiceManager getInstancia() {
        if (instancia == null) {
            instancia = new StreamingServiceManager();
        }
        return instancia;
    }

    // Agregar un observador
    public void agregarObservador(Observer observador) {
        this.observadores.add(observador);
    }

    // Eliminar un observador
    public void eliminarObservador(Observer observador) {
        this.observadores.remove(observador);
    }

    // Notificar a los observadores
    public void notificarObservadores(String mensaje) {
        for (Observer observador : observadores) {
            observador.actualizar(mensaje);
        }
    }

    // Establecer el servicio usando la fábrica
    public void setServicio(String tipoServicio) {
        StreamingServiceFactory factory;

        switch (tipoServicio) {
            case "WatchMode":
                factory = new WatchModeServiceFactory();
                break;
            default:
                System.out.println("Servicio no reconocido.");
                return;
        }

        // Crear el servicio usando la fábrica
        this.servicioActual = factory.crearServicio();
        if (this.servicioActual != null) {
            notificarObservadores("Servicio seleccionado: " + tipoServicio);  // Notificar a los observadores
        } else {
            System.out.println("No se pudo crear el servicio: " + tipoServicio);
        }
    }

    // Configurar el servicio
    public void configurarServicio(Collection<String> configParams) {
        if (this.servicioActual != null) {
            this.servicioActual.configurar(configParams);
            notificarObservadores("Servicio configurado con parámetros: " + configParams);  // Notificar a los observadores
        } else {
            System.out.println("No se ha seleccionado ningún servicio.");
        }
    }

    // Consultar el servicio
    public Collection<SearchResult> consultarServicio(String query, Vector<String> configParams) {
        if (this.servicioActual != null) {
            return this.servicioActual.consultar(query, configParams);
        } else {
            System.out.println("No se ha seleccionado ningún servicio.");
            return new Vector<>();
        }
    }

    // Buscar en el servicio
    public Collection<SearchResult> buscarEnServicio(String query, Vector<String> configParams) {
        if (this.servicioActual != null) {
            return this.servicioActual.buscar(query, configParams);
        } else {
            System.out.println("No se ha seleccionado ningún servicio.");
            return new Vector<>();
        }
    }
}
