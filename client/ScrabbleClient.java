package client;
import java.rmi.RemoteException;

import remote.IGameManager;

/**
 * This class retrieves a reference to the remote object from the RMI registry. It
 * invokes the methods on the remote object as if it was a local object of the type of the 
 * remote interface.
 *
 */
public class ScrabbleClient  implements IGameManager{
	String userName;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}

	@Override
	public void gameTableChanged(char character, int[] index) throws RemoteException {
		
		
	}

	@Override
	public void newClientJoined(String username) throws RemoteException {
		
		
	}

	@Override
	public void clientExit(String username) throws RemoteException {
		
		
	}

	public String getUserName() {
		return userName;
	}
	
	
	public void setUserName(String userName) {
		this.userName=userName;
	}

	
}
