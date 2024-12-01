package observer;


/**
 * Observer que procesa las actualizaciones de las releases desde la API de StreamAvailability.
 * Este observer se encarga de recibir las notificaciones y extraer los detalles específicos de cada release.
 */
public class ReleaseObserverStreamingAv implements Observer {

    /**
     * Método que es llamado cuando se recibe una notificación de actualización.
     * Este método procesa el mensaje JSON y llama a un método para imprimir los detalles de las releases.
     *
     * @param message El mensaje JSON que contiene las releases desde WatchMode y StreamAvailability.
     */
    @Override
    public void update(String message) {
        // Procesamos el mensaje JSON
        System.out.println("=======================================");
        System.out.println("   Nuevas releases desde StreamAvailability:   ");
        System.out.println("=======================================");

        // Llamamos a un método para imprimir solo los datos que nos interesan
        printReleaseDetails(message);
    }

    /**
     * Método que imprime los detalles de las releases. Extrae los datos de cada release y los imprime.
     *
     * Este método está diseñado para procesar hasta las primeras 5 releases, extrayendo los campos de interés
     * como el título, la plataforma, la fecha de ingreso a la plataforma, el tipo y la URL de la imagen.
     *
     * @param message El mensaje JSON que contiene las releases desde WatchMode y StreamAvailability.
     */
    private void printReleaseDetails(String message) {
        // Suponiendo que el mensaje contiene un campo "releases" que es una lista de objetos JSON
        String[] releases = message.split("\\},\\{"); // Dividir las releases por el patrón '} {'

        // Asegurarnos de que no excedemos el número de releases disponibles
        int maxReleases = Math.min(5, releases.length);

        // Imprimir solo los primeros 5 releases con datos seleccionados
        for (int i = 0; i < maxReleases; i++) {
            String release = releases[i];

            // Extraer los campos específicos que necesitamos
            String type = extractField(release, "itemType");
            String showType = extractField(release, "showType");
            String changeType = extractField(release, "changeType");
            String title = extractField(release, "tittle");
            String link = extractField(release, "link");
            String overview = extractField(release, "overview");

            // Imprimir los datos extraídos
            System.out.println("""
                    ========= Release %d =========
                    """.formatted(i + 1));
            System.out.println("  Tipo programa/película: " + showType);
            System.out.println("  Clasificación: " + changeType);
            System.out.println("  Título: " + title);
            System.out.println("Link: " + link);
            System.out.println("Descripción: " + overview);
        }
    }

    /**
     * Método que extrae un campo específico de un objeto JSON representado como un string.
     * Este método busca el campo por su nombre y devuelve su valor como una cadena.
     *
     * @param release  El objeto release en formato JSON.
     * @param fieldName El nombre del campo que se quiere extraer.
     * @return El valor del campo como un string, o "No disponible" si no se encuentra el campo.
     */
    private String extractField(String release, String fieldName) {
        String pattern = "\"" + fieldName + "\":\"";
        int startIndex = release.indexOf(pattern);
        if (startIndex != -1) {
            startIndex += pattern.length();
            int endIndex = release.indexOf("\"", startIndex);
            if (endIndex != -1) {
                return release.substring(startIndex, endIndex);
            }
        }
        return "No disponible"; // Si no encontramos el campo
    }
}

