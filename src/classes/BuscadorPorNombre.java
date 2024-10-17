package classes;

/**
 * Implementación de un buscador que busca contenido por nombre.
 * Esta clase permite realizar búsquedas de contenido basadas en un nombre de consulta.
 */
public class BuscadorPorNombre implements Buscador {

    /**
     * Realiza una búsqueda del contenido basado en el nombre.
     *
     * @param query el nombre o término de búsqueda que se utilizará para buscar contenido.
     */
    @Override
    public void buscar(String query) {
        // Lógica de búsqueda por nombre
        System.out.println("Buscando contenido por nombre: " + query);
    }

    /**
     * Muestra los resultados de la búsqueda realizada.
     *
     * @return una cadena que describe los resultados de la búsqueda por nombre.
     */
    @Override
    public String mostrar() {
        return "Resultados de la búsqueda por nombre.";
    }
}
