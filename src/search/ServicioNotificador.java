package search;

public class ServicioNotificador implements Observer {
    @Override
    public void actualizar(String mensaje) {
        // Aquí puedes decidir cómo manejar el mensaje, por ejemplo, imprimirlo o realizar alguna acción
        System.out.println("Notificación: " + mensaje);
    }
}
