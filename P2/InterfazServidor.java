import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazServidor extends Remote {
	public void registrar (String cliente) throws RemoteException;
	public void difundirMensaje (String cliente, String mensaje) throws RemoteException;
	public void desconectar (String cliente) throws RemoteException;
}
