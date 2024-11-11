package service;

import factory.StreamingServiceFactory;
import factory.WatchModeServiceFactory;
import model.SearchResult;
import model.StreamingService;
import observer.Observer;
import classes.Usuario;
import classes.Suscripcion;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * Clase que gestiona los servicios de streaming y las suscripciones.
 *
 * Esta clase se encarga de manejar un servicio de streaming, configurarlo,
 * realizar búsquedas y consultas, y administrar suscripciones de usuarios.
 * Implementa el patrón Singleton para asegurar que haya una única instancia
 * de `StreamingServiceManager` durante toda la aplicación.
 *
 * También notifica a los observadores cuando se selecciona un servicio o se
 * realiza una acción relevante, como configurar el servicio o realizar una búsqueda.
 */
public class StreamingServiceManager {

    /**
     * Instancia única para el patrón Singleton.
     */
    private static StreamingServiceManager instancia;

    /**
     * Servicio de streaming actual que se está utilizando.
     */
    private StreamingService servicioActual;

    /**
     * Lista de observadores que serán notificados de cambios o acciones.
     */
    private List<Observer> observadores;

    /**
     * Constructor privado para garantizar que la clase use el patrón Singleton.
     */
    private StreamingServiceManager() {
        this.observadores = new Vector<>();
    }

    /**
     * Método para obtener la instancia única de `StreamingServiceManager`.
     *
     * @return La instancia única de `StreamingServiceManager`.
     */
    public static StreamingServiceManager getInstancia() {
        if (instancia == null) {
            instancia = new StreamingServiceManager();
        }
        return instancia;
    }

    /**
     * Agrega un observador a la lista de observadores.
     *
     * @param observador El observador que desea recibir notificaciones.
     */
    public void agregarObservador(Observer observador) {
        this.observadores.add(observador);
    }

    /**
     * Elimina un observador de la lista de observadores.
     *
     * @param observador El observador que desea eliminar.
     */
    public void eliminarObservador(Observer observador) {
        this.observadores.remove(observador);
    }

    /**
     * Notifica a todos los observadores con un mensaje.
     *
     * @param mensaje El mensaje a enviar a los observadores.
     */
    public void notificarObservadores(String mensaje) {
        for (Observer observador : observadores) {
            observador.actualizar(mensaje);
        }
    }

    /**
     * Establece el servicio de streaming a utilizar, utilizando la fábrica correspondiente.
     *
     * @param tipoServicio El tipo de servicio de streaming (por ejemplo, "WatchMode").
     */
    public void setServicio(String tipoServicio) {
        StreamingServiceFactory factory;

        switch (tipoServicio) {
            case "WatchMode":
                factory = new WatchModeServiceFactory();
                break;
            default:
                System.out.println("Servicio no reconocido.");
                return;
        }

        // Crear el servicio utilizando la fábrica
        this.servicioActual = factory.crearServicio();
        if (this.servicioActual != null) {
            notificarObservadores("Servicio seleccionado: " + tipoServicio);  // Notificar a los observadores
        } else {
            System.out.println("No se pudo crear el servicio: " + tipoServicio);
        }
    }

    /**
     * Configura el servicio de streaming actual con los parámetros proporcionados.
     *
     * @param configParams Los parámetros de configuración para el servicio.
     */
    public void configurarServicio(Collection<String> configParams) {
        if (this.servicioActual != null) {
            this.servicioActual.configurar(configParams);
            notificarObservadores("Servicio configurado con parámetros: " + configParams);  // Notificar a los observadores
        } else {
            System.out.println("No se ha seleccionado ningún servicio.");
        }
    }

    /**
     * Realiza una búsqueda avanzada en el servicio de streaming actual con filtros específicos.
     *
     * @param query         El término de búsqueda (por ejemplo, el nombre de la película).
     * @param tipoContenido El tipo de contenido (ej., "movie" o "tv_movie").
     * @param region        La región para filtrar la búsqueda (ej., "US").
     * @param sourceId      El ID de la plataforma de streaming.
     * @return Una colección de resultados de búsqueda que cumplen con los filtros.
     */
    public Collection<SearchResult> buscarConFiltrosAvanzados(String query, String tipoContenido, String region, int sourceId) {
        if (servicioActual != null) {
            return ((WatchModeService) servicioActual).buscarConFiltrosAvanzados(query, tipoContenido, region, sourceId);
        } else {
            System.out.println("No se ha seleccionado ningún servicio.");
            return new Vector<>();
        }
    }


    /**
     * Realiza una búsqueda en el servicio de streaming actual.
     *
     * @param query        La consulta que se desea realizar (nombre de película, etc.).
     * @param configParams Los parámetros de configuración adicionales para la búsqueda.
     * @return La colección de resultados de búsqueda.
     */
    public Collection<SearchResult> buscarEnServicio(String query, Vector<String> configParams) {
        if (this.servicioActual != null) {
            return this.servicioActual.buscar(query, configParams);
        } else {
            System.out.println("No se ha seleccionado ningún servicio.");
            return new Vector<>();
        }
    }


    /*************************
     * DE AQUI PARA ABAJO DE FIJO NO VA ACÁ
     **************************************/


    /**
     * Agrega una nueva suscripción para el usuario especificado.
     *
     * @param usuario     El usuario al que se le agrega la suscripción.
     * @param suscripcion La suscripción a agregar.
     */
    public void agregarSuscripcion(Usuario usuario, Suscripcion suscripcion) {
        usuario.agregarSuscripcion(suscripcion);
        notificarObservadores("Nueva suscripción agregada para " + usuario.getNombre());
    }

    /**
     * Cancela una suscripción del usuario especificado.
     *
     * @param usuario     El usuario del que se cancelará la suscripción.
     * @param suscripcion La suscripción a cancelar.
     */
    public void cancelarSuscripcion(Usuario usuario, Suscripcion suscripcion) {
        usuario.eliminarSuscripcion(suscripcion);
        notificarObservadores("Suscripción cancelada para " + usuario.getNombre());
    }

    /**
     * Obtiene la lista de suscripciones del usuario.
     *
     * @param usuario El usuario del cual se obtendrán las suscripciones.
     * @return Lista de suscripciones del usuario.
     */
    public List<Suscripcion> obtenerSuscripciones(Usuario usuario) {
        return usuario.getSuscripciones();
    }

}
