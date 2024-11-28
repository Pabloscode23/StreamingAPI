package menu;

public class ElementoMenu implements ComponenteMenu {
    private String nombre;

    public ElementoMenu(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void mostrar() {
        System.out.println(nombre);
    }
}
