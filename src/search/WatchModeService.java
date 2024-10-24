package search;

import java.util.Collection;
import java.util.Vector;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class WatchModeService implements StreamingService {
    private static final String API_KEY = "XYC7tTUpWat5eJzmlbMgJyKaMKbenW42g0Hamtoh";
    private static final String BASE_URL = "https://api.watchmode.com/v1/";

    @Override
    public void configurar(Collection<String> configParams) {
        System.out.println("Configurando WatchMode con los siguientes parámetros:");
        for (String param : configParams) {
            System.out.println("- " + param);
        }
    }

    @Override
    public Collection<SearchResult> consultar(String query, Vector<String> configParams) {
        System.out.println("Consultando película '" + query + "' en WatchMode con los parámetros: " + configParams);
        return buscar(query, configParams); // En este caso, reutilizamos la lógica de búsqueda
    }

    @Override
    public Collection<SearchResult> buscar(String query, Vector<String> configParams) {
        Collection<SearchResult> resultados = new ArrayList<>();

        try {
            String endpoint = BASE_URL + "search/?apiKey=" + API_KEY + "&search_field=name&search_value=" + query;
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray titles = jsonResponse.getJSONArray("title_results");

                for (int i = 0; i < titles.length(); i++) {
                    JSONObject title = titles.getJSONObject(i);
                    Integer id = title.getInt("id");

                    String detailsUrl = BASE_URL + "title/" + id + "/details/?apiKey=" + API_KEY + "&append_to_response=sources";
                    URL detailurl = new URL(detailsUrl);
                    HttpURLConnection connDetail = (HttpURLConnection) detailurl.openConnection();
                    connDetail.setRequestMethod("GET");

                    int detailResponseCode = connDetail.getResponseCode();
                    if (detailResponseCode == 200) {
                        BufferedReader detailsIn = new BufferedReader(new InputStreamReader(connDetail.getInputStream()));
                        StringBuffer detailResponse = new StringBuffer();
                        String detailsInputLine;

                        while ((detailsInputLine = detailsIn.readLine()) != null) {
                            detailResponse.append(detailsInputLine);
                        }
                        detailsIn.close();

                        JSONObject detailsJsonResponse = new JSONObject(detailResponse.toString());
                        String name = detailsJsonResponse.getString("title");
                        String description = detailsJsonResponse.optString("plot_overview", "Descripción no disponible");

                        JSONArray sources = detailsJsonResponse.getJSONArray("sources");
                        String urlLink = "No disponible";
                        String platform = "Desconocida";

                        if(sources != null && sources.length() > 0){
                            JSONObject source = sources.getJSONObject(0);
                            urlLink = source.optString("web_url", "No disponible");
                            platform = source.optString("name", "No disponible");
                        }

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
