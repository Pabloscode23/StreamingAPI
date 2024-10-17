package classes;


public class ProveedorFactory {
    /**
     *
     * @param proveedor
     * @return clase Proveedor o un error
     */
    public ProveedorService getProveedor(String proveedor) {

        switch (proveedor.toLowerCase()) {
            case "netflix":
                return new Proveedor();
            default:
                throw new IllegalArgumentException("Plataforma no soportada: " + proveedor);
        }
    }
}
