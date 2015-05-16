import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Cliente implements InterfazCliente {

    private String nombre;
    private InterfazServidor servidor;
    private ClienteView clienteView;

    public Cliente (String nombre, InterfazServidor servidor) {
        super();
        this.nombre = nombre;
        this.servidor = servidor;
        try {
            InterfazCliente stub = (InterfazCliente) UnicastRemoteObject.exportObject((InterfazCliente) this, 0);
            servidor.registrar(nombre, stub);
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

    public void setClienteView (ClienteView clienteView) {
        this.clienteView = clienteView;
    }

    public void mostrarMensaje (String cliente, String mensaje) {
        clienteView.mostrarMensaje(cliente, mensaje);
    }

    public void desconectar () {
        try {
            servidor.desconectar(nombre);
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void difundirMensaje (String mensaje) {
        try {
            servidor.difundirMensaje(nombre, mensaje);
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

    /**************************************************************************/

    public static void main (String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            System.out.println("Buscando el objeto remoto");
            Registry registry = LocateRegistry.getRegistry(args[0]);
            InterfazServidor instanciaLocal = (InterfazServidor) registry.lookup("ServidorCentral");
            System.out.println("Invocando el objeto remoto");

            ClienteView clienteView = new ClienteView();

            CapturarNombre capturarNombre = new CapturarNombre(clienteView, true);
            String nombre = capturarNombre.getNombre();

            Cliente cliente = new Cliente(nombre, instanciaLocal);
            clienteView.setNombre(nombre);
            clienteView.setCliente(cliente);
            cliente.setClienteView(clienteView);

            clienteView.showView();

        } catch (Exception e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }
}
