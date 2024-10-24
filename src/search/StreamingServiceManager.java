package search;

public class StreamingServiceManager {

    // Paso 1: Crear una variable estática privada para almacenar la única instancia
    private static StreamingServiceManager instance;

    // Paso 2: Hacer el constructor privado para prevenir la instanciación desde fuera de la clase
    private StreamingServiceManager() {
        // Código de inicialización, si es necesario
    }

    // Paso 3: Proveer un método estático público para obtener la instancia de la clase
    public static StreamingServiceManager getInstance() {
        if (instance == null) {
            instance = new StreamingServiceManager();
        }
        return instance;
    }

    // Método de ejemplo para demostrar la funcionalidad
    public void manageService() {
        System.out.println("Gestionando servicios de streaming...");
    }
}
