import search.Observer;
import search.SearchResult;
import search.StreamingServiceManager;

import java.util.Collection;
import java.util.Vector;

public class Main implements Observer {
    public static void main(String[] args) {
        System.out.println("\n------------------------------------------");
        System.out.println("                Búsquedas                ");
        System.out.println("------------------------------------------");

        // Crear la instancia del Main, que actúa como observador
        Main main = new Main();

        // Obtener la instancia del manager (Singleton)
        StreamingServiceManager manager = StreamingServiceManager.getInstancia();

        // Registrar el observador (Main) en el manager
        manager.agregarObservador(main);

        // Usar el servicio WatchMode (a través de la fábrica)
        manager.setServicio("WATCHMODE");

        // Configurar el servicio WatchMode
        Vector<String> configParams = new Vector<>();
        configParams.add("Región: US");
        manager.configurarServicio(configParams);

        // *****  BUSCAR *****
        // Realizar una búsqueda en WatchMode
        System.out.println("\n------------------------------------------");
        System.out.println("   Búsqueda con parámetros (método buscar)   ");
        System.out.println("------------------------------------------");
        Vector<String> searchParams = new Vector<>();
        searchParams.add("año: 2021");
        Collection<SearchResult> resultados = manager.buscarEnServicio("Inception", searchParams);

        // Mostrar los resultados de Buscar
        for (SearchResult result : resultados) {
            System.out.println(result.toString() + "\n");
        }

        System.out.println("\n------------------------------------------");
        System.out.println("   Búsqueda con parámetros (método consultar)   ");
        System.out.println("------------------------------------------");

        // *****  CONSULTAR  *****
        // Realizar una CONSULTA en WatchMode
        Vector<String> consultParams = new Vector<>();
        consultParams.add("año: 2021");
        Collection<SearchResult> consultados = manager.consultarServicio("Dune", consultParams);

        // Mostrar los resultados de Consultar
        for (SearchResult result : consultados) {
            System.out.println(result.toString());
        }
    }

    // Implementación del método actualizar del Observer
    @Override
    public void actualizar(String mensaje) {
        // Este método será llamado cuando el estado del manager cambie
        System.out.println("Notificación del Manager: " + mensaje);
    }
}

