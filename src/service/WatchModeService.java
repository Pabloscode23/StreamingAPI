package service;

import java.util.Collection;
import java.util.Vector;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.SearchResult;
import model.StreamingService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Implementa la interfaz StreamingService y se encarga de interactuar con el servicio de WatchMode.
 * Utiliza la API pública de WatchMode para realizar búsquedas y consultas sobre títulos de películas o series.
 * La clase incluye métodos para configurar el servicio y realizar búsquedas o consultas basadas en parámetros proporcionados.
 *
 * Patrones de Diseño Empleados:
 * 1. Singleton: Asegura que haya una única instancia del servicio de WatchMode en toda la aplicación.
 * 2. Adapter: Sirve de adaptador entre el sistema de búsqueda de la aplicación y la API de WatchMode, encapsulando la lógica de interacción con la API.
 * 3. Facade: Proporciona una interfaz simplificada para interactuar con los complejos detalles de la API de WatchMode, ocultando la complejidad del acceso a la API.
 */
public class WatchModeService implements StreamingService {

    // Claves y URL base para la API de WatchMode.
    private static final String API_KEY = "XYC7tTUpWat5eJzmlbMgJyKaMKbenW42g0Hamtoh";
    private static final String BASE_URL = "https://api.watchmode.com/v1/";

    /**
     * Configura el servicio de WatchMode con los parámetros proporcionados.
     *
     * @param configParams Colección de parámetros de configuración.
     */
    @Override
    public void configurar(Collection<String> configParams) {
        System.out.println("Configurando WatchMode con los siguientes parámetros:");
        for (String param : configParams) {
            System.out.println("- " + param);
        }
    }

    /**
     * Realiza una consulta en WatchMode para obtener detalles de un título específico.
     *
     * @param query Consulta de búsqueda (nombre de la película o serie).
     * @param configParams Parámetros adicionales para configurar la consulta.
     * @return Una colección de objetos SearchResult con los resultados de la consulta.
     */
    @Override
    public Collection<SearchResult> consultar(String query, Vector<String> configParams) {
        System.out.println("Consultando película '" + query + "' en WatchMode con los parámetros: " + configParams);
        Collection<SearchResult> consultados = new ArrayList<>();

        try {
            // Construir la URL de búsqueda
            String endpoint = BASE_URL + "search/?apiKey=" + API_KEY + "&search_field=name&search_value=" + query;
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Agregar la API Key en los encabezados
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // Leer la respuesta de la API
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parsear la respuesta JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray titles = jsonResponse.optJSONArray("title_results");

                // Verifica si hay resultados
                if (titles != null && titles.length() > 0) {
                    JSONObject title = titles.getJSONObject(0);
                    Integer id = title.getInt("id");

                    // Realizar una consulta detallada sobre el título
                    String detailsUrl = BASE_URL + "title/" + id + "/details/?apiKey=" + API_KEY + "&append_to_response=sources";
                    URL detailUrl = new URL(detailsUrl);
                    HttpURLConnection connDetail = (HttpURLConnection) detailUrl.openConnection();
                    connDetail.setRequestMethod("GET");

                    // Repetir el mismo proceso con el detalle
                    connDetail.setRequestProperty("Authorization", "Bearer " + API_KEY);

                    int detailResponseCode = connDetail.getResponseCode();
                    if (detailResponseCode == 200) {
                        BufferedReader detailsIn = new BufferedReader(new InputStreamReader(connDetail.getInputStream()));
                        StringBuilder detailResponse = new StringBuilder();
                        String detailsInputLine;

                        while ((detailsInputLine = detailsIn.readLine()) != null) {
                            detailResponse.append(detailsInputLine);
                        }
                        detailsIn.close();

                        // Parsear la respuesta de detalles y extraer información
                        JSONObject detailsJsonResponse = new JSONObject(detailResponse.toString());
                        String name = detailsJsonResponse.getString("title");
                        String description = detailsJsonResponse.optString("plot_overview", "Descripción no disponible");

                        JSONArray sources = detailsJsonResponse.optJSONArray("sources");
                        String urlLink = "No disponible";
                        String platform = "Desconocida";

                        if (sources != null && sources.length() > 0) {
                            JSONObject source = sources.getJSONObject(0);
                            urlLink = source.optString("web_url", "No disponible");
                            platform = source.optString("name", "No disponible");
                        }

                        // Crear un objeto SearchResult con los detalles
                        SearchResult searchResult = new SearchResult(name, description, urlLink, platform);
                        consultados.add(searchResult);
                    } else {
                        System.out.println("Error en la respuesta de detalles: " + detailResponseCode);
                    }
                } else {
                    System.out.println("No se encontraron títulos para la búsqueda: " + query);
                }

            } else {
                System.out.println("Error en la respuesta de la API: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return consultados;
    }

    /**
     * Realiza una búsqueda en WatchMode para obtener una lista de resultados de un título específico.
     *
     * @param query Consulta de búsqueda (nombre de la película o serie).
     * @param configParams Parámetros adicionales para configurar la búsqueda.
     * @return Una colección de objetos SearchResult con los resultados de la búsqueda.
     */
    @Override
    public Collection<SearchResult> buscar(String query, Vector<String> configParams) {
        Collection<SearchResult> resultados = new ArrayList<>();

        try {
            // Construir la URL de búsqueda
            String endpoint = BASE_URL + "search/?apiKey=" + API_KEY + "&search_field=name&search_value=" + query;
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Agregar la API Key en los encabezados
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // Leer la respuesta de la API
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parsear la respuesta JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray titles = jsonResponse.getJSONArray("title_results");

                // Recorrer los resultados y obtener detalles adicionales
                for (int i = 0; i < titles.length(); i++) {
                    JSONObject title = titles.getJSONObject(i);
                    Integer id = title.getInt("id");

                    String detailsUrl = BASE_URL + "title/" + id + "/details/?apiKey=" + API_KEY + "&append_to_response=sources";
                    URL detailUrl = new URL(detailsUrl);
                    HttpURLConnection connDetail = (HttpURLConnection) detailUrl.openConnection();
                    connDetail.setRequestMethod("GET");

                    // Repetir el mismo proceso con el detalle
                    connDetail.setRequestProperty("Authorization", "Bearer " + API_KEY);

                    int detailResponseCode = connDetail.getResponseCode();
                    if (detailResponseCode == 200) {
                        BufferedReader detailsIn = new BufferedReader(new InputStreamReader(connDetail.getInputStream()));
                        StringBuilder detailResponse = new StringBuilder();
                        String detailsInputLine;

                        while ((detailsInputLine = detailsIn.readLine()) != null) {
                            detailResponse.append(detailsInputLine);
                        }
                        detailsIn.close();

                        // Parsear la respuesta de detalles y extraer información
                        JSONObject detailsJsonResponse = new JSONObject(detailResponse.toString());
                        String name = detailsJsonResponse.getString("title");
                        String description = detailsJsonResponse.optString("plot_overview", "Descripción no disponible");

                        JSONArray sources = detailsJsonResponse.getJSONArray("sources");
                        String urlLink = "No disponible";
                        String platform = "Desconocida";

                        if (sources != null && sources.length() > 0) {
                            JSONObject source = sources.getJSONObject(0);
                            urlLink = source.optString("web_url", "No disponible");
                            platform = source.optString("name", "No disponible");
                        }

                        // Crear un objeto SearchResult con los detalles
                        SearchResult searchResult = new SearchResult(name, description, urlLink, platform);
                        resultados.add(searchResult);
                    }
                }
            } else {
                System.out.println("Error en la respuesta de la API: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultados;
    }
}
