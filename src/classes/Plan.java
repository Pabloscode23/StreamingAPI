package classes;

/**
 * La clase que representa un plan de suscripción disponible
 *
 */
public class Plan {
    int codigo;
    String nombre;
    float precio;
    int codigoProveedor;
    /**
     * Constructor
     *
     * @param codigo código del plan.
     * @param nombre nombre del plan.
     * @param precio precio del plan
     * @param codigoProveedor de dónde pertenece
     */

    public Plan(int codigo, String nombre, float precio, int codigoProveedor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.codigoProveedor = codigoProveedor;
    }


    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     *
     *
     * @return código
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     *
     *
     * @param codigo código del plan.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     *
     *
     * @return nombre del plan.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     *
     * @param nombre del plan
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     *
     * @return
     */

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    /**
     *
     *
     * @return código del proveedor al que pertenece
     */

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", codigoProveedor=" + codigoProveedor +
                '}';
    }
}
