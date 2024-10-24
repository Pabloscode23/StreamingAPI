package search;

import java.util.Collection;
import java.util.Vector;

public class StreamingServiceManager {
    private static StreamingServiceManager instancia;
    private StreamingService servicioActual;

    private StreamingServiceManager() {
    }

    public static StreamingServiceManager getInstancia() {
        if (instancia == null) {
            instancia = new StreamingServiceManager();
        }

        return instancia;
    }

    public void setServicio(StreamingService servicio) {
        this.servicioActual = servicio;
    }

    public void configurarServicio(Collection<String> configParams) {
        if (this.servicioActual != null) {
            this.servicioActual.configurar (configParams);
        } else {
            System.out.println("No se ha seleccionado ningun servicio.");
        }

    }

    public void consultarServicio(String query, Vector<String> configParams) {
        if (this.servicioActual != null) {
            this.servicioActual.consultar(query,configParams);
        } else {
            System.out.println("No se ha seleccionado ningun servicio.");
        }

    }

    public Collection<SearchResult> buscarEnServicio(String query, Vector<String> configParams) {
        if (this.servicioActual != null) {
            return this.servicioActual.buscar(query, configParams);
        } else {
            System.out.println("No se ha seleccionado ningun servicio.");
            return new Vector<SearchResult>();
        }
    }
}
