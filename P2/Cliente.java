import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente implements InterfazCliente {

    private String id;

    public Cliente (String id) {
        super();
        this.id = id;
    }

    public String getId () {
        return id;
    }

    public void mostrarMensaje (String cliente, String mensaje) {
        System.out.println(cliente + ": " + mensaje);
    }

    /**************************************************************************/

    public static void main (String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String nombreObjetoRemoto = "ServidorCentral";
            Cliente c = new Cliente(args[1]);

            System.out.println("Buscando el objeto remoto");
            Registry registry = LocateRegistry.getRegistry(args[0]);
            InterfazServidor instanciaLocal = (InterfazServidor) registry.lookup(nombreObjetoRemoto);
            System.out.println("Invocando el objeto remoto");

            instanciaLocal.registrar(c.getId());
            instanciaLocal.difundirMensaje(c.getId(), "Hola, esto es una prueba");
            instanciaLocal.desconectar(c.getId());
        } catch (Exception e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }

        System.out.println("Press any key to continue...");
        try {
            System.in.read();
        }  
        catch (Exception e) {

        }  
    }
}
