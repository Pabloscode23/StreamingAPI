/**
 * Clase que representa un elemento individual de un menú.
 * Es una implementación de la interfaz {@link ComponenteMenu} y forma parte
 * del patrón de diseño Composite.
 */
package menu;

/**
 * Clase que implementa un elemento simple de un menú.
 * Un elemento de menú no puede contener otros elementos; representa una hoja en la jerarquía del patrón Composite.
 */
public class ElementoMenu implements ComponenteMenu {

    /** Nombre del elemento del menú. */
    private String nombre;

    /**
     * Constructor de ElementoMenu.
     *
     * @param nombre El nombre del elemento del menú.
     */
    public ElementoMenu(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Muestra el nombre del elemento del menú.
     * Es la implementación del método definido en {@link ComponenteMenu}.
     */
    @Override
    public void mostrar() {
        System.out.println(nombre);
    }
}
