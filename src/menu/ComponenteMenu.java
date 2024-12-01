/**
 * Interfaz que define la estructura común para los componentes de un menú.
 * Es utilizada como parte del patrón de diseño Composite, permitiendo tratar
 * tanto elementos individuales como menús compuestos de manera uniforme.
 */
package menu;

/**
 * Define el comportamiento básico de un componente de menú.
 * Cada implementación debe proporcionar una forma de mostrarse.
 */
public interface ComponenteMenu {

    /**
     * Muestra el componente del menú.
     * La implementación puede variar dependiendo de si es un componente simple
     * o un menú compuesto.
     */
    void mostrar();
}
