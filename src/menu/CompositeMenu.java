package menu;

import java.util.ArrayList;
import java.util.List;

public class CompositeMenu implements ComponenteMenu {
    private List<ComponenteMenu> componentes = new ArrayList<>();
    private String nombre;

    public CompositeMenu(String nombre) {
        this.nombre = nombre;
    }

    public void agregarComponente(ComponenteMenu componente) {
        componentes.add(componente);
    }

    public void eliminarComponente(ComponenteMenu componente) {
        componentes.remove(componente);
    }

    @Override
    public void mostrar() {
        System.out.println(nombre);
        for (ComponenteMenu componente : componentes) {
            componente.mostrar();
        }
    }
}
