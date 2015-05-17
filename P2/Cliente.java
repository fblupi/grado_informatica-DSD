import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Cliente implements InterfazCliente {

    private String nombre;
    private String nombreServidor;
    private InterfazServidor servidor;
    private ClienteView clienteView;

    public Cliente (String nombre, String ipServidor, String nombreServidor) {
        super();
        this.nombre = nombre;
        this.nombreServidor = nombreServidor;
        try {
            Registry registry = LocateRegistry.getRegistry(ipServidor); // Se obtiene el RMI registry de la ip del servidor
            this.servidor = (InterfazServidor) registry.lookup(nombreServidor); // Se busca el servidor
            InterfazCliente stub = (InterfazCliente) UnicastRemoteObject.exportObject((InterfazCliente) this, 0); // Se crea el stub
            servidor.registrar(nombre, stub); // Avisa al servidor para que registre al cliente
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

    public void mostrarMensaje (String cliente, String mensaje) {
        clienteView.mostrarMensaje(cliente, mensaje); // Se envía el mensaje a la GUI para que lo muestre
    }

    public void desconectar () {
        try {
            servidor.desconectar(nombre); // Se avisa al servidor para que elimine al cliente
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void difundirMensaje (String mensaje) {
        try {
            servidor.difundirMensaje(nombre, mensaje); // Se avisa al servidor para que difunda un mensaje
        } catch (RemoteException e) {
            System.err.println("Cliente exception:");
            e.printStackTrace();
        }
    }

    public void setClienteView (ClienteView clienteView) {
        this.clienteView = clienteView;
    }

    public String getNombre () {
        return nombre;
    }

    public String getNombreServidor () {
        return nombreServidor;
    }

    /**************************************************************************/

    public static void main (String args[]) {
        if (System.getSecurityManager() == null) { // Instalación del gestor de seguridad
            System.setSecurityManager(new SecurityManager());
        }

        ClienteView clienteView = new ClienteView(); // Se crea la GUI del cliente

        CapturarNombre capturarNombre = new CapturarNombre(clienteView, true); // Se lanza el capturador de nombres
        String nombre = capturarNombre.getNombre(); // Se obtiene el nombre

        Cliente cliente = new Cliente(nombre, args[0], args[1]); // Se crea el cliente

        clienteView.setCliente(cliente);  // Se asigna la GUI al cliente y el cliente a la GUI
        cliente.setClienteView(clienteView);  // Se asigna el cliente a la GUI

        clienteView.showView(); // Se inicia la GUI
    }
}
