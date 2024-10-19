package classes;

public class Notificacion {
    private int codigo;
    private int codigoUsuario;
    private String mensaje;

    /**
     * Constructor vacio
     */
    public Notificacion() {
    }

    /**
     * Constructor con parametros
     * @param codigo
     * @param codigoUsuario
     * @param mensaje
     */
    public Notificacion(int codigo, int codigoUsuario, String mensaje) {
        this.codigo = codigo;
        this.codigoUsuario = codigoUsuario;
        this.mensaje = mensaje;
    }

    /**
     *  Estabelce codigo de notificacion
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     *  Estabelce codigo del usuario
     * @param codigoUsuario
     */
    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    /**
     *  Estabelce mensaje de notificacion
     * @param mensaje
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Devuelve el codigo de la notificaicon
     * @return
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Devuelve el codigo del usuario
     * @return
     */
    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    /**
     * Devuelve el mensaje de la notificaicon
     *
     */
    public String getMensaje() {
        return mensaje;
    }
}
