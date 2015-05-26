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
            registry = LocateRegistry.getRegistry(); // Se obtiene el registro de la m�quina donde se lanza
            InterfazServidor stub = (InterfazServidor) UnicastRemoteObject.exportObject((InterfazServidor) this, 0); // Se crea el stub
            registry.rebind(this.nombre, stub); // Se exporta y se da un nombre para identificarlo en el RMI registry
        } catch (RemoteException e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }
    }

    public void registrar (String nombre, InterfazCliente cliente) {
        try {
            registry.rebind(nombre, cliente); // Se exporta el stub del cliente y se le da un nombre para identificarlo en el RMI registry
        } catch (RemoteException e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }
        //difundirMensaje(this.nombre, nombre + " se conect�."); // Se difunde el mensaje de que se ha conectado un nuevo cliente
        clientes.put(nombre, cliente); // Se a�ade un nuevo cliente al map
        for (String nombreCliente: clientes.keySet()) { // Recorro todos los clientes
            try {
                clientes.get(nombreCliente).actualizarClientes(clientes); // Actualiza clientes
            } catch (RemoteException e) {
                System.err.println("Servidor exception:");
                e.printStackTrace();
            }
        }
    }
/*
    public void difundirMensaje (String nombre, String mensaje) {
        for (String cliente: clientes.keySet()) { // Se recorren todos los clientes
            try {
                clientes.get(cliente).mostrarMensaje(nombre, mensaje); // Avisa al cliente para que muestre el mensaje
            } catch (RemoteException e) {
                System.err.println("Servidor exception:");
                e.printStackTrace();
            }
        }
    }
*/
    public void desconectar (String nombre) {
        try {
            registry.unbind(nombre); // Se elimina el stub con el nombre indicado del RMI registry
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }
        clientes.remove(nombre); // Se elimina al cliente del map
        for (String nombreCliente: clientes.keySet()) { // Recorro todos los clientes
            try {
                clientes.get(nombreCliente).actualizarClientes(clientes); // Actualiza clientes
            } catch (RemoteException e) {
                System.err.println("Servidor exception:");
                e.printStackTrace();
            }
        }
        //difundirMensaje(this.nombre, nombre + " se desconect�."); // Se difunde el mensaje de que se ha desconectado
    }

    public boolean nombreCorrecto (String nombre) {
        if (nombre == null || nombre.length() < 3 || nombre.length() > 10) { // Nombres inv�lidos
            return false;
        }
        boolean correcto = true;
        for (String cliente: clientes.keySet()) { // Se recorren todos los clientes
            if (cliente.equals(nombre)) { // Si ya existe devolver� fallo
                correcto = false;
            }
        }
        return correcto;
    }

    /**************************************************************************/
    
    public static void main (String[] args) {
        if (System.getSecurityManager() == null) { // Instalaci�n del gestor de seguridad
            System.setSecurityManager(new SecurityManager());
        }
        Servidor servidor = new Servidor(args[0]); // Se crea el servidor
    }

}
