/**
 * La clase que representa un plan de suscripción disponible
 *
 */
public class Plan {
    int codigo;
    String nombre;

    /**
     * Constructor
     *
     * @param codigo código del plan.
     * @param nombre nombre del plan.
     */
    public Plan(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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
    @Override
    public String toString() {
        return "Plan{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
