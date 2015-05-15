import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.lang.Thread;
import java.util.ArrayList;


public class Servidor implements InterfazServidor {

  private ArrayList<String> clientes;

  public Servidor () {
    super();
    clintes = new ArrayList<Cliente>;
  }

  public void registrar (String cliente) {
    System.out.println("Recibida petición de registrar: " + id);

    System.out.println("Buscando el cliente");
    Registry registry = LocateRegistry.getRegistry("localhost");
    InterfazServidor instanciaLocal = (InterfazServidor) registry.lookup(cliente);
    System.out.println("Cliente encontrado");

    clientes.add(cliente);
  }

  public void difundirMensaje (String cliente, String mensaje) {
    System.out.println("Recibida petición de difundir mensaje: " + id);
    for (Cliente cliente: clientes) {
      cliente.mostrarMensaje(cliente, mensaje);
    }
  }

  public void desconectar (String cliente) {
    System.out.println("Recibida petición de desconectar: " + id);
    clientes.remove(cliente);
  }

  public static void main (String[] args) {
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }
    try {
      String nombreObjetoRemoto = "ServidorCentral";

      InterfazServidor prueba = new Servidor();
      InterfazServidor stub = (InterfazServidor) UnicastRemoteObject.exportObject(prueba, 0);
      Registry registry = LocateRegistry.getRegistry();
      registry.rebind(nombreObjetoRemoto, stub);

      System.out.println("Servidor: " + nombreObjetoRemoto);
    } catch (Exception e) {
      System.err.println("Servidor exception:");
      e.printStackTrace();
    }
  }
  
}
