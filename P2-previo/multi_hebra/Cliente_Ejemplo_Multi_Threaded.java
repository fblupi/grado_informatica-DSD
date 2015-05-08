import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Cliente_Ejemplo_Multi_Threaded
                    implements Runnable {
                    
  public String nombre_objeto_remoto = "Ejemplo_I";
  public String server;
  
  public Cliente_Ejemplo_Multi_Threaded (String server) {
    //Almacenamos el nombre del host servidor
    this.server = server;
  }
  
  public void run() {
    System.out.println("Buscando el objeto remoto");
    try {
      Registry registry = LocateRegistry.getRegistry(server);
      Ejemplo_I instancia_local = (Ejemplo_I) registry.lookup(nombre_objeto_remoto);
      System.out.println("Invocando el objeto remoto");
      instancia_local.escribir_mensaje(Thread.currentThread().getName());
    } catch (Exception e) {
      System.err.println("Ejemplo_I exception:");
      e.printStackTrace();
    }
  }

  public static void main(String args[]) {

    if (System.getSecurityManager() == null) {
        System.setSecurityManager(new SecurityManager());
    }
    int n_hebras = Integer.parseInt(args[1]);
    //Seguimos el mismo esquema de creación de vectores de hebras visto en prácticas anteriores    
    Cliente_Ejemplo_Multi_Threaded[] v_clientes = new Cliente_Ejemplo_Multi_Threaded[n_hebras];
    Thread[] v_hebras = new Thread[n_hebras];
    for (int i=0;i<n_hebras;i++) {
      //A cada hebra le pasamos el nombre el servidor
      v_clientes [i] = new Cliente_Ejemplo_Multi_Threaded(args[0]);
      v_hebras[i] = new Thread(v_clientes[i],"Cliente "+i);
      v_hebras[i].start();
    }
  }
}
