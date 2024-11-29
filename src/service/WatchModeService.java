package service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
 * Servicio que interactúa con la API de WatchMode para realizar búsquedas de contenido
 * de streaming y obtener información sobre películas y series.
 *
 * Implementa la interfaz `StreamingService` y define métodos para realizar búsquedas
 * generales y avanzadas con filtros específicos.
 */
public class WatchModeService implements StreamingService {

    private static final String API_KEY = "SLG2ee4P9rV0wO746kfKydrAus9ccZA6UfVjtATO";
    private static final String BASE_URL = "https://api.watchmode.com/v1/";

    /**
     * Configura el servicio WatchMode utilizando los parámetros proporcionados.
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
     * Realiza una búsqueda general de contenido en WatchMode utilizando la consulta y parámetros especificados.
     *
     * @param query        Término de búsqueda, como el nombre de una película o serie.
     * @param configParams Parámetros de configuración para personalizar la búsqueda (ej., tipo de contenido).
     * @return Una colección de objetos `SearchResult` que representan los resultados de búsqueda obtenidos.
     */
    @Override
    public Collection<SearchResult> buscar(String query, Vector<String> configParams) {
        Collection<SearchResult> resultados = new ArrayList<>();
        String tipoContenido = "movie"; // Valor predeterminado

        for (String param : configParams) {
            if (param.startsWith("type: ")) {
                tipoContenido = param.split(": ")[1].trim();
                break;
            }
        }

        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            String endpoint = BASE_URL + "search/?apiKey=" + API_KEY +
                    "&search_field=name&search_value=" + encodedQuery +
                    "&types=" + tipoContenido;

            //System.out.println("URL de Búsqueda: " + endpoint); // Imprimir URL de búsqueda

            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray titles = jsonResponse.getJSONArray("title_results");

                for (int i = 0; i < titles.length(); i++) {
                    JSONObject title = titles.getJSONObject(i);
                    String name = title.getString("name");
                    String description = title.optString("plot_overview", "Descripción no disponible");
                    String platform = "Desconocida";
                    String urlLink = "No disponible";
                    String subType = "No disponible";

                    // Crear una instancia de SearchResult
                    SearchResult baseResult = new SearchResult(name, description, urlLink, platform, subType);

                    // Clonar si es necesario (no es redundante aquí)
                    SearchResult searchResult = baseResult.clone();

                    resultados.add(searchResult);
                }
            } else {
                System.out.println("Error en la respuesta de la API: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultados;
    }

    /**
     * Realiza una búsqueda avanzada en la API de WatchMode con filtros específicos.
     *
     * @param query         Término de búsqueda, como el nombre de una película o serie.
     * @param tipoContenido Tipo de contenido (e.g., "movie", "tv_show").
     * @param region        Región del contenido (e.g., "US", "ES").
     * @param sourceId      ID de la plataforma de streaming.
     * @return Una colección de objetos `SearchResult` que representan los resultados de búsqueda obtenidos.
     */

    public Collection<SearchResult> buscarConFiltrosAvanzados(String query, String tipoContenido, String region, int sourceId) {
        Collection<SearchResult> resultados = new ArrayList<>();

        try {
            // Formatear la consulta
            String encodedQuery = URLEncoder.encode(capitalizeWords(query), StandardCharsets.UTF_8.toString());

            // Construcción de la URL de búsqueda
            String searchEndpoint = BASE_URL + "search/?apiKey=" + API_KEY +
                    "&search_field=name&search_value=" + encodedQuery +
                    "&types=" + tipoContenido;

            System.out.println("URL de Búsqueda: " + searchEndpoint);
            JSONObject searchResponse = realizarPeticion(searchEndpoint);

            if (searchResponse != null) {
                JSONArray titleResults = searchResponse.optJSONArray("title_results");

                if (titleResults != null && !titleResults.isEmpty()) {
                    // Obtener el ID del primer resultado
                    JSONObject firstResult = titleResults.getJSONObject(0);
                    int id = firstResult.getInt("id");
                    String title = firstResult.getString("name");

                    // Construcción de la URL de detalles
                    String detailsEndpoint = BASE_URL + "title/" + id + "/details/?apiKey=" + API_KEY + "&append_to_response=sources";
                    System.out.println("URL de Detalles: " + detailsEndpoint);
                    JSONObject detailsResponse = realizarPeticion(detailsEndpoint);

                    if (detailsResponse != null) {
                        // Extraer información relevante
                        String plotOverview = detailsResponse.optString("plot_overview", "Sinopsis no disponible");
                        JSONArray sourcesArray = detailsResponse.optJSONArray("sources");

                        if (sourcesArray != null) {
                            for (int i = 0; i < 1; i++) {
                                JSONObject source = sourcesArray.getJSONObject(i);
                                if (source.getInt("source_id") == sourceId) {
                                    resultados.add(new SearchResult(
                                            title, // Título del contenido
                                            plotOverview, // Descripción (sinopsis)
                                            source.optString("web_url", "URL no disponible"), // Enlace
                                            source.optString("name", "Plataforma desconocida"), // Plataforma
                                            source.optString("type", "Tipo desconocido") // Tipo de pago
                                    ));
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("No se encontraron resultados para la búsqueda avanzada.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultados;
    }

    /**
     * Realiza una petición HTTP a la API de WatchMode y retorna la respuesta como un objeto JSON.
     *
     * @param endpoint URL de la petición a realizar.
     * @return Un objeto `JSONObject` que representa la respuesta de la API.
     * @throws Exception Si ocurre algún error durante la petición.
     */

    private JSONObject realizarPeticion(String endpoint) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return new JSONObject(response.toString());
            }
        } else {
            System.out.println("Error en la respuesta de la API: " + responseCode);
        }
        return null;
    }

    // Método para capitalizar las palabras de una cadena
    private String capitalizeWords(String str) {
        String[] words = str.split(" ");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return capitalized.toString().trim();
    }
}