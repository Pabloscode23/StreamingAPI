package service;

import model.SearchResult;
import model.StreamingService;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Set;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * Servicio que interactúa con la API de Streaming Availability para realizar búsquedas de contenido
 * de streaming y obtener información sobre películas y series.
 *
 * Implementa la interfaz `StreamingService` y define métodos para realizar búsquedas
 * generales y avanzadas con filtros específicos.
 */
public class StreamingAvailabilityService implements StreamingService {

    private static final String API_KEY = "e25ca44cc9msh249c64cee83fd44p16d3a0jsnf4e10890a32f";
    private static final String BASE_URL = "https://streaming-availability.p.rapidapi.com/shows/search/";

    /**
     * Configura el servicio Streaming Availability utilizando los parámetros proporcionados.
     *
     * @param configParams Colección de parámetros de configuración.
     */
    @Override
    public void configurar(Collection<String> configParams) {
        System.out.println("Configurando Streaming Availability con los siguientes parámetros:");
        for (String param : configParams) {
            System.out.println("- " + param);
        }
    }

    /**
     * Realiza una búsqueda básica en la API de Streaming Availability utilizando un término de búsqueda
     * y parámetros de configuración.
     *
     * @param query        El término de búsqueda (nombre de la película o serie).
     * @param configParams Vector con parámetros de configuración (tipo de contenido, región, etc.).
     * @return Una colección de resultados de búsqueda representados como objetos `SearchResult`.
     */
    @Override
    public Collection<SearchResult> buscar(String query, Vector<String> configParams) {
        Collection<SearchResult> resultados = new ArrayList<>();
        String region = "us";
        String tipoContenido = "";
        String outputLanguage = "en";

        for (String param : configParams) {
            if (param.startsWith("type: ")) {
                tipoContenido = param.split(": ")[1].trim().toLowerCase();
            }
        }

        if (tipoContenido.isEmpty()) {
            System.out.println("Faltan parámetros necesarios: 'region' o 'type'.");
            return resultados;
        }

        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            String endpoint = BASE_URL + "title?" + "title=" + encodedQuery +
                    "&show_type=" + tipoContenido +
                    "&output_language=" + outputLanguage +
                    "&country=" + region;

            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-RapidAPI-Key", API_KEY);
            conn.setRequestProperty("X-RapidAPI-Host", "streaming-availability.p.rapidapi.com");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONArray jsonResponse = new JSONArray(response.toString());
                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject item = jsonResponse.getJSONObject(i);
                    String title = item.optString("title", "Título no disponible");
                    String overview = item.optString("overview", "Descripción no disponible");

                    Set<String> plataformas = new HashSet<>();
                    String enlace = "No disponible"; // Valor predeterminado para Enlace
                    JSONArray streamingOptions = item.optJSONObject("streamingOptions")
                            .optJSONArray(region);
                    if (streamingOptions != null) {
                        for (int j = 0; j < streamingOptions.length(); j++) {
                            JSONObject option = streamingOptions.getJSONObject(j);
                            JSONObject service = option.getJSONObject("service");
                            plataformas.add(service.getString("name"));

                            // Tomar el primer enlace disponible
                            if (enlace.equals("No disponible")) {
                                enlace = option.optString("link", "No disponible");
                            }
                        }
                    }

                    String plataformasUnicas = plataformas.isEmpty() ? "No disponible" : String.join(", ", plataformas);

                    // Crear un objeto SearchResult con los datos extraídos
                    SearchResult resultado = new SearchResult(
                            title.isEmpty() ? "Título no disponible" : title,
                            overview.isEmpty() ? "Descripción no disponible" : overview,
                            enlace, // Enlace del primer servicio disponible
                            plataformasUnicas,
                            "Suscripción" // Tipo de Pago fijo para este API
                    );

                    resultados.add(resultado);
                }
            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;

                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();

                System.out.println("Error en la respuesta de la API (código " + responseCode + "): " + errorResponse.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultados;
    }

    /**
     * Realiza una búsqueda avanzada en la API de Streaming Availability con filtros específicos.
     *
     * @param query         El término de búsqueda (nombre de la película o serie).
     * @param tipoContenido El tipo de contenido (e.g., "movie" o "series").
     * @param region        La región donde se buscará el contenido (e.g., "us", "es").
     * @param sourceId      El ID de la plataforma de streaming (opcional).
     * @return Una colección de resultados de búsqueda representados como objetos `SearchResult`.
     */
    @Override
    public Collection<SearchResult> buscarConFiltrosAvanzados(String query, String tipoContenido, String region, int sourceId) {
        Collection<SearchResult> resultados = new ArrayList<>();
        String outputLanguage = "en"; // Idioma de salida fijo

        if (region.isEmpty() || tipoContenido.isEmpty()) {
            System.out.println("Faltan parámetros necesarios: 'region' o 'tipoContenido'.");
            return resultados;
        }

        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            String endpoint = BASE_URL + "title?" + "title=" + encodedQuery +
                    "&show_type=" + tipoContenido +
                    "&output_language=" + outputLanguage +
                    "&country=" + region;

            System.out.println("URL generada: " + endpoint);

            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-RapidAPI-Key", API_KEY);
            conn.setRequestProperty("X-RapidAPI-Host", "streaming-availability.p.rapidapi.com");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Procesar el array de objetos JSON
                JSONArray jsonResponse = new JSONArray(response.toString());

                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject show = jsonResponse.getJSONObject(i);

                    String title = show.optString("title", "Título no disponible");
                    String overview = show.optString("overview", ""); // Si no hay overview, será una cadena vacía

                    // Saltar este elemento si no tiene overview
                    if (overview.isEmpty()) {
                        continue;
                    }

                    Set<String> plataformas = new HashSet<>();
                    String enlace = "No disponible"; // Valor predeterminado para Enlace

                    JSONObject streamingOptions = show.optJSONObject("streamingOptions");
                    if (streamingOptions != null) {
                        JSONArray options = streamingOptions.optJSONArray(region);
                        if (options != null) {
                            for (int j = 0; j < options.length(); j++) {
                                JSONObject option = options.getJSONObject(j);
                                JSONObject service = option.getJSONObject("service");

                                // Agregar el nombre de la plataforma
                                plataformas.add(service.getString("name"));

                                // Tomar el primer enlace disponible
                                if (enlace.equals("No disponible")) {
                                    enlace = option.optString("link", "No disponible");
                                }
                            }
                        }
                    }

                    String plataformasUnicas = plataformas.isEmpty() ? "No disponible" : String.join(", ", plataformas);

                    // Crear un objeto SearchResult con los datos extraídos
                    SearchResult resultado = new SearchResult(
                            title.isEmpty() ? "Título no disponible" : title,
                            overview, // Mostrar solo si tiene overview
                            enlace, // Enlace del primer servicio disponible
                            plataformasUnicas,
                            "Suscripción" // Tipo de Pago fijo para este API
                    );

                    resultados.add(resultado);

                    // Detener la búsqueda después de encontrar el primer resultado válido
                    break;
                }
            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;

                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();

                System.out.println("Error en la respuesta de la API (código " + responseCode + "): " + errorResponse.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultados;
    }
}
