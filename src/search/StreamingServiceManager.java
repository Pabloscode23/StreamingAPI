package search;

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

    public void configurarServicio() {
        if (this.servicioActual != null) {
            this.servicioActual.configurar();
        } else {
            System.out.println("No se ha seleccionado ningun servicio.");
        }

    }

    public void consultarServicio(Collection<SearchResult> resultados) {
        if (this.servicioActual != null) {
            this.servicioActual.consultar(resultados);
        } else {
            System.out.println("No se ha seleccionado ningun servicio.");
        }

    }

    public void buscarEnServicio(String query, Vector<String> configParams) {
        if (this.servicioActual != null) {
            this.servicioActual.buscar(query, configParams);
        } else {
            System.out.println("No se ha seleccionado ningun servicio.");
        }

    }
}
