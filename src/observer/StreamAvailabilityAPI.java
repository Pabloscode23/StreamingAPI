package observer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase que interactúa con la API de StreamAvailabilityAPI para obtener información sobre nuevas releases.
 * Esta clase se encarga de realizar una solicitud HTTP a la API, recibir la respuesta
 * y notificar a los observadores con los datos obtenidos.
 */
public class StreamAvailabilityAPI {
    // URL de la API de StreamAvailability (sin la API Key, será añadida como encabezado)
    private static final String API_URL = "https://streaming-availability.p.rapidapi.com/changes?change_type=new&country=us&item_type=show&output_language=en&order_direction=asc&include_unknown_dates=false&show_type=movie";

    // Referencia al sujeto (StreamAvailabilitySubject) que notificará a los observadores
    private final StreamAvailabilitySubject subject;

    // Tu clave API de RapidAPI
    private static final String API_KEY = "e25ca44cc9msh249c64cee83fd44p16d3a0jsnf4e10890a32f"; // Reemplaza con tu clave real

    /**
     * Constructor que inicializa la instancia de StreamAvailability con un sujeto.
     * Este sujeto es el encargado de notificar a los observadores cuando se obtienen los datos de la API.
     *
     * @param subject El sujeto que notificará a los observadores con la información obtenida de la API.
     */
    public StreamAvailabilityAPI(StreamAvailabilitySubject subject) {
        this.subject = subject;
    }

    /**
     * Método que realiza una solicitud HTTP a la API de StreamAvailability para obtener las releases disponibles.
     * Una vez obtenida la respuesta, se notifica a todos los observadores registrados con la información en formato JSON.
     */
    public void fetchReleases() {
        try {
            // Crear la URL de la API
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Establecer el método HTTP como GET
            connection.setRequestProperty("Accept", "application/json"); // Aceptar solo respuestas en formato JSON
            connection.setRequestProperty("X-RapidAPI-Key", API_KEY); // Establecer el encabezado con la clave de la API
            connection.setRequestProperty("X-RapidAPI-Host", "streaming-availability.p.rapidapi.com"); // Host de RapidAPI

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