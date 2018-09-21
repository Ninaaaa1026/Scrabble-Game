package remote;

import java.rmi.RemoteException;

public interface IGameManager {
	public void newClientJoined(String username) throws RemoteException;
	
	public void clientExit(String username) throws RemoteException;
	
	public void gameTableChanged(char character, int[] index) throws RemoteException;
	
	public String getUserName();
	
}
