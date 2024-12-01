/**
 * Clase que representa un menú compuesto en un patrón Composite.
 * Un menú compuesto puede contener otros componentes del menú,
 * ya sean menús simples u otros menús compuestos.
 */
package menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa un menú compuesto como parte del patrón Composite.
 * Permite organizar elementos del menú en una jerarquía y gestionar componentes de manera recursiva.
 */
public class CompositeMenu implements ComponenteMenu {

    /** Lista de componentes hijos del menú compuesto. */
    private List<ComponenteMenu> componentes = new ArrayList<>();

    /** Nombre del menú compuesto. */
    private String nombre;

    /**
     * Constructor de CompositeMenu.
     *
     * @param nombre El nombre del menú compuesto.
     */
    public CompositeMenu(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Agrega un componente al menú compuesto.
     *
     * @param componente El componente que se agregará al menú.
     */
    public void agregarComponente(ComponenteMenu componente) {
        componentes.add(componente);
    }

    /**
     * Elimina un componente del menú compuesto.
     *
     * @param componente El componente que se eliminará del menú.
     */
    public void eliminarComponente(ComponenteMenu componente) {
        componentes.remove(componente);
    }

    /**
     * Muestra el menú compuesto y todos sus componentes hijos recursivamente.
     */
    @Override
    public void mostrar() {
        System.out.println(nombre);
        for (ComponenteMenu componente : componentes) {
            componente.mostrar();
        }
    }
}
