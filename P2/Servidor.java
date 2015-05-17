import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.lang.Thread;
import java.util.HashMap;


public class Servidor implements InterfazServidor {

    private HashMap<String, InterfazCliente> clientes;
    private Registry registry;
    private String nombre;

    public Servidor (String nombre) {
        super();
        clientes = new HashMap<String, InterfazCliente>();
        this.nombre = nombre;
        try {
            registry = LocateRegistry.getRegistry();
            InterfazServidor stub = (InterfazServidor) UnicastRemoteObject.exportObject((InterfazServidor) this, 0);
            registry.rebind(this.nombre, stub);
        } catch (RemoteException e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }
    }

    public void registrar (String nombre, InterfazCliente cliente) {
        try {
            registry.rebind(nombre, cliente);
        } catch (RemoteException e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }
        difundirMensaje(this.nombre, nombre + " se conect�.");
        clientes.put(nombre, cliente);
    }

    public void difundirMensaje (String nombre, String mensaje) {
        for(String cliente: clientes.keySet()) {
            try {
                clientes.get(cliente).mostrarMensaje(nombre, mensaje);
            } catch (RemoteException e) {
                System.err.println("Servidor exception:");
                e.printStackTrace();
            }
        }
    }

    public void desconectar (String nombre) {
        try {
            registry.unbind(nombre);
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }
        clientes.remove(nombre);
        difundirMensaje(this.nombre, nombre + " se desconect�.");
    }

    /**************************************************************************/
    
    public static void main (String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Servidor servidor = new Servidor(args[0]);
    }

}