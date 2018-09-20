package remote;
import java.rmi.Remote;
import java.rmi.RemoteException;
import remote.IGameManager;

/**
 * RMI Remote interface - must be shared between client and server.
 * All methods must throw RemoteException.
 * All parameters and return types must be either primitives or Serializable.
 *  
 * Any object that is a remote object must implement this interface.
 * Only those methods specified in a "remote interface" are available remotely.
 */
public interface GameListener extends Remote {

	public void addCharacter(char character, int[] index) throws RemoteException;
	public void addClient(IGameManager player) throws RemoteException;
	public void removeClient(IGameManager player) throws RemoteException;
	
}
