import search.Observer;
import search.SearchResult;
import search.StreamingServiceManager;
import search.StreamingService;
import search.WatchModeServiceFactory;

import java.util.Collection;
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

        // Obtener la instancia de StreamingServiceManager (Singleton)
        StreamingServiceManager manager = StreamingServiceManager.getInstancia();

        // Registrar el observador (Main) en el manager
        manager.agregarObservador(new Main());

        // Configurar el servicio seleccionado y realizar operaciones
        configurarYRealizarOperaciones(manager);
    }

    private static void configurarYRealizarOperaciones(StreamingServiceManager manager) {
        // Seleccionar servicio y configurarlo
        manager.setServicio("WatchMode");

        // Configurar parámetros del servicio
        Vector<String> configParams = new Vector<>();
        configParams.add("Región: US");
        manager.configurarServicio(configParams);

        // Realizar búsqueda general
        realizarBusquedaGeneral(manager);

        // Realizar consulta general
        realizarConsultaGeneral(manager);
    }

    // Implementación del método para realizar la búsqueda general
    private static void realizarBusquedaGeneral(StreamingServiceManager manager) {
        System.out.println("\n------------------------------------------");
        System.out.println("   Búsqueda con parámetros   ");
        System.out.println("------------------------------------------");

        // Parámetros para la búsqueda general
        Vector<String> searchParams = new Vector<>();
        searchParams.add("año: 2021");

        // Llamar al método de búsqueda
        Collection<SearchResult> resultados = manager.buscarEnServicio("Inception", searchParams);

        // Mostrar los resultados de la búsqueda
        mostrarResultados(resultados);
    }

    // Implementación del método para realizar la consulta general
    private static void realizarConsultaGeneral(StreamingServiceManager manager) {
        System.out.println("\n------------------------------------------");
        System.out.println("   Consulta con parámetros   ");
        System.out.println("------------------------------------------");

        // Parámetros para la consulta
        Vector<String> consultParams = new Vector<>();
        consultParams.add("año: 2021");

        // Llamar al método de consulta
        Collection<SearchResult> consultados = manager.consultarServicio("Dune", consultParams);

        // Mostrar los resultados de la consulta
        mostrarResultados(consultados);
    }

    // Método común para mostrar resultados
    private static void mostrarResultados(Collection<SearchResult> resultados) {
        if (resultados != null && !resultados.isEmpty()) {
            for (SearchResult result : resultados) {
                System.out.println(result.toString() + "\n");
            }
        } else {
            System.out.println("No se encontraron resultados.");
        }
    }

    // Implementación del método actualizar del Observer
    @Override
    public void actualizar(String mensaje) {
        System.out.println("Notificación del Manager: " + mensaje);
    }
}
