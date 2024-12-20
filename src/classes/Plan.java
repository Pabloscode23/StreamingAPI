package classes;

/**
 * La clase que representa un plan de suscripción disponible
 */
public class Plan {
    int codigo;
    String nombre;
    float precio;
    int codigoProveedorArchivo;

    /**
     * Constructor Vacio
     */
    public Plan() {
    }

    public Plan(int codigo, String nombre, float precio, int codigoProveedorArchivo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.codigoProveedorArchivo = codigoProveedorArchivo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public int getCodigoProveedorArchivo() {
        return codigoProveedorArchivo;
    }

    public void setCodigoProveedorArchivo(int codigoProveedorArchivo) {
        this.codigoProveedorArchivo = codigoProveedorArchivo;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", codigoProveedorArchivo=" + codigoProveedorArchivo +
                '}';
    }
}
