import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface InterfazCliente extends Remote {
	// public void mostrarMensaje (String cliente, String mensaje) throws RemoteException;
	public void actualizarClientes (HashMap<String, InterfazCliente> clientes) throws RemoteException;
}
