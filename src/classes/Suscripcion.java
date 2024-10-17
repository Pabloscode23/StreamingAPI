public class Suscripcion {
    int codigo;
    int codigoUsuario;
    int codigoProveedor;
    int codigoPlan;
    int meses;
    float precio;
    boolean activo;

    public Suscripcion(int codigo, int codigoUsuario, int codigoProveedor, int codigoPlan, int meses, float precio, boolean activo) {
        this.codigo = codigo;
        this.codigoUsuario = codigoUsuario;
        this.codigoProveedor = codigoProveedor;
        this.codigoPlan = codigoPlan;
        this.meses = meses;
        this.precio = precio;
        this.activo = activo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public int getCodigoPlan() {
        return codigoPlan;
    }

    public void setCodigoPlan(int codigoPlan) {
        this.codigoPlan = codigoPlan;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

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

