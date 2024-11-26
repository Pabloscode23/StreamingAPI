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

    private static final String API_KEY = "0zt8HW3qrm5NNzODmrBD69uxQ9ktJs67J7arVXKx";
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

            System.out.println("URL de Búsqueda: " + endpoint); // Imprimir URL de búsqueda
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

                    SearchResult searchResult = new SearchResult(name, description, urlLink, platform, subType);
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
     * Realiza una búsqueda avanzada en WatchMode con filtros específicos.
     *
     * @param query         Término de búsqueda, como el nombre de una película o serie.
     * @param tipoContenido Tipo de contenido (ej., "movie" para películas o "tv_movie" para series).
     * @param region        Código de región para limitar la búsqueda (ej., "US" para Estados Unidos).
     * @param sourceId      ID de la plataforma de streaming (ej., ID específico para Netflix, Hulu, etc.).
     * @return Una colección de objetos `SearchResult` que cumplen con los filtros avanzados especificados.
     */
    public Collection<SearchResult> buscarConFiltrosAvanzados(String query, String tipoContenido, String region, int sourceId) {
        Collection<SearchResult> resultados = new ArrayList<>();

        try {
            // Paso 1: Primera búsqueda para obtener el ID del título
            String endpoint = BASE_URL + "search/?apiKey=" + API_KEY +
                    "&search_field=name&search_value=" + URLEncoder.encode(query, StandardCharsets.UTF_8.toString()) +
                    "&types=" + tipoContenido;

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
                JSONArray titleResults = jsonResponse.getJSONArray("title_results");

                if (!titleResults.isEmpty()) {
                    JSONObject firstResult = titleResults.getJSONObject(0);
                    int id = firstResult.getInt("id");
                    String name = firstResult.getString("name");
                    int year = firstResult.getInt("year");

                    // Obtener la sinopsis (plot_overview) del título
                    String detailsEndpoint = BASE_URL + "title/" + id + "/details/?apiKey=" + API_KEY;
                    URL detailsUrl = new URL(detailsEndpoint);
                    HttpURLConnection detailsConn = (HttpURLConnection) detailsUrl.openConnection();
                    detailsConn.setRequestMethod("GET");

                    int detailsResponseCode = detailsConn.getResponseCode();
                    String plotOverview = "Sinopsis no disponible";
                    if (detailsResponseCode == 200) {
                        BufferedReader detailsIn = new BufferedReader(new InputStreamReader(detailsConn.getInputStream()));
                        StringBuilder detailsResponse = new StringBuilder();
                        while ((inputLine = detailsIn.readLine()) != null) {
                            detailsResponse.append(inputLine);
                        }
                        detailsIn.close();

                        JSONObject detailsJsonResponse = new JSONObject(detailsResponse.toString());
                        plotOverview = detailsJsonResponse.optString("plot_overview", plotOverview);
                    }

                    // Obtener las fuentes (sources) donde está disponible el título
                    String sourcesEndpoint = BASE_URL + "title/" + id + "/sources/?apiKey=" + API_KEY + "&regions=" + region;
                    URL sourcesUrl = new URL(sourcesEndpoint);
                    HttpURLConnection sourcesConn = (HttpURLConnection) sourcesUrl.openConnection();
                    sourcesConn.setRequestMethod("GET");

                    int sourcesResponseCode = sourcesConn.getResponseCode();
                    if (sourcesResponseCode == 200) {
                        BufferedReader sourcesIn = new BufferedReader(new InputStreamReader(sourcesConn.getInputStream()));
                        StringBuilder sourcesResponse = new StringBuilder();
                        while ((inputLine = sourcesIn.readLine()) != null) {
                            sourcesResponse.append(inputLine);
                        }
                        sourcesIn.close();

                        JSONArray sourcesArray = new JSONArray(sourcesResponse.toString());

                        for (int i = 0; i < sourcesArray.length(); i++) {
                            JSONObject source = sourcesArray.getJSONObject(i);
                            if (source.getInt("source_id") == sourceId) {
                                String platformName = source.getString("name");
                                String webUrl = source.optString("web_url", "URL no disponible");
                                String subType = source.getString("type");

                                SearchResult searchResult = new SearchResult(name, plotOverview, webUrl, platformName, subType);
                                resultados.add(searchResult);
                            }
                        }
                    }
                } else {
                    System.out.println("No se encontraron resultados para la búsqueda avanzada.");
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
