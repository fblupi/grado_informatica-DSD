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

  public Servidor () {
    super();
    clientes = new HashMap<String, InterfazCliente>();
    try {
      registry = LocateRegistry.getRegistry();
      InterfazServidor stub = (InterfazServidor) UnicastRemoteObject.exportObject((InterfazServidor) this, 0);
      registry.rebind("ServidorCentral", stub);
    } catch (RemoteException e) {
      System.err.println("Servidor exception:");
      e.printStackTrace();
    }
  }

  public void registrar (String nombre, InterfazCliente cliente) {
    System.out.println("Recibida petición de registrar: " + nombre);
    try {
      registry.rebind(nombre, cliente);
    } catch (RemoteException e) {
      System.err.println("Servidor exception:");
      e.printStackTrace();
    }
    clientes.put(nombre, cliente);
  }

  public void difundirMensaje (String nombre, String mensaje) {
    System.out.println("Recibida petición de difundir mensaje: " + nombre);
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
    System.out.println("Recibida petición de desconectar: " + nombre);
    try {
      registry.unbind(nombre);
    } catch (NotBoundException | RemoteException e) {
      System.err.println("Servidor exception:");
      e.printStackTrace();
    }
    clientes.remove(nombre);
  }

  public static void main (String[] args) {
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }
    Servidor s = new Servidor();
  }
  
}
