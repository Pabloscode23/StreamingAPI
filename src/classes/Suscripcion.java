package classes;

/**
 * La clase Suscripcion: suscripción de un usuario a un servicio
 *
 *
 */
public class Suscripcion {
    int codigo;
    int codigoUsuario;
    int codigoProveedor;
    int codigoPlan;
    int meses;
    float precio;
    boolean activo;

    /**
     * Constructor Vacio
     */
    public Suscripcion() {
    }

    /**
     *
     *
     * @param codigo
     * @param codigoUsuario
     * @param codigoProveedor
     * @param codigoPlan
     * @param meses
     * @param precio
     * @param activo
     */
    public Suscripcion(int codigo, int codigoUsuario, int codigoProveedor, int codigoPlan, int meses, float precio, boolean activo) {
        this.codigo = codigo;
        this.codigoUsuario = codigoUsuario;
        this.codigoProveedor = codigoProveedor;
        this.codigoPlan = codigoPlan;
        this.meses = meses;
        this.precio = precio;
        this.activo = activo;
    }

    /**
     *
     *
     * @return código de suscripción
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     *
     *
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     *
     *
     * @return código de usuario.
     */
    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    /**
     *
     *
     * @param codigoUsuario código del usuario.
     */
    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    /**
     *
     *
     * @return código del proveedor.
     */
    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    /**
     *
     *
     * @param codigoProveedor código del proveedor.
     */
    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    /**
     *
     *
     * @return código del plan.
     */
    public int getCodigoPlan() {
        return codigoPlan;
    }

    /**
     *
     *
     * @param codigoPlan El nuevo código del plan.
     */
    public void setCodigoPlan(int codigoPlan) {
        this.codigoPlan = codigoPlan;
    }

    /**
     *
     *
     * @return duración de la suscripción .
     */
    public int getMeses() {
        return meses;
    }

    /**
     *
     *
     * @param meses duración de la suscripción
     */
    public void setMeses(int meses) {
        this.meses = meses;
    }

    /**
     *
     *
     * @return precio de la suscripción.
     */
    public float getPrecio() {
        return precio;
    }

    /**
     *
     *
     * @param precio precio
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     *
     *
     * @return suscripción está activa = true
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     *
     *
     * @param activo true si la suscripción debe ser activa
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public String toString() {
        return "Suscripcion{" +
                "codigo=" + codigo +
                ", codigoUsuario=" + codigoUsuario +
                ", codigoProveedor=" + codigoProveedor +
                ", codigoPlan=" + codigoPlan +
                ", meses=" + meses +
                ", precio=" + precio +
                ", activo=" + activo +
                '}';
    }
}
