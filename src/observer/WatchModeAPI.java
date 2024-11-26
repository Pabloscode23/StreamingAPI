package observer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase que interactúa con la API de WatchMode para obtener información sobre nuevas releases.
 * Esta clase se encarga de realizar una solicitud HTTP a la API, recibir la respuesta
 * y notificar a los observadores con los datos obtenidos.
 */
public class WatchModeAPI {
    // URL de la API de WatchMode (incluye el API Key)
    private static final String API_URL = "https://api.watchmode.com/v1/releases/?apiKey=0zt8HW3qrm5NNzODmrBD69uxQ9ktJs67J7arVXKx";

    // Referencia al sujeto (WatchModeSubject) que notificará a los observadores
    private final WatchModeSubject subject;

    /**
     * Constructor que inicializa la instancia de WatchModeAPI con un sujeto.
     * Este sujeto es el encargado de notificar a los observadores cuando se obtienen los datos de la API.
     *
     * @param subject El sujeto que notificará a los observadores con la información obtenida de la API.
     */
    public WatchModeAPI(WatchModeSubject subject) {
        this.subject = subject;
    }

    /**
     * Método que realiza una solicitud HTTP a la API de WatchMode para obtener las releases disponibles.
     * Una vez obtenida la respuesta, se notifica a todos los observadores registrados con la información en formato JSON.
     */
    public void fetchReleases() {
        try {
            // Crear la URL de la API
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Establecer el método HTTP como GET
            connection.setRequestProperty("Accept", "application/json"); // Aceptar solo respuestas en formato JSON

            // Obtener el código de respuesta de la conexión HTTP
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // Código 200 indica éxito
                // Leer la respuesta de la API
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                // Leer todo el contenido de la respuesta
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Notificar a los observadores con la respuesta obtenida (en formato JSON)
                subject.notifyObservers(response.toString());
            } else {
                // Si la llamada a la API falla, imprimir el código de respuesta del error
                System.out.println("Error en la llamada a la API. Código de respuesta: " + responseCode);
            }
        } catch (Exception e) {
            // Imprimir el error si ocurre alguna excepción
            e.printStackTrace();
        }
    }
}
