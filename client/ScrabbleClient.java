package client;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Map;

import remote.ClientInterface;
import remote.ServerInterface;

/**
 * This class retrieves a reference to the remote object from the RMI registry. It
 * invokes the methods on the remote object as if it was a local object of the type of the 
 * remote interface.
 *
 */
public class ScrabbleClient implements ClientInterface {
	String userName;
	public static ScrabbleClient player=new ScrabbleClient();
	public static ServerInterface remoteServer;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameLogin window = new GameLogin();
					window.getFrame().setVisible(true);
					try {
						//Connect to the rmiregistry that is running on localhost
						Registry registry = LocateRegistry.getRegistry("localhost");
			           
						//Retrieve the stub/proxy for the remote math object from the registry
						remoteServer = (ServerInterface) registry.lookup("Compute");
						}catch(Exception e) {
							e.printStackTrace();
						}
						
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String getUserName() {
		return userName;
	}
	
	
	protected void setUserName(String userName) {
		this.userName=userName;
	}



	@Override
	public void clientAdded(String playerUserName) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void roomCreated(String createplayerusername) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerInvited() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invitationResponse(String invitedplayer, boolean agree) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextPlayer(char character, int rowIndex, int colIndex, String currentPlayer) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pass(String nextUserName) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean beginVote(char character, int startRowIndex, int startColIndex, int endRowIndex, int endColIndex,
			String userName) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void voteSuccess(Map<String, Integer> player) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameOver() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientExited(String playerUserName) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initiateGame(boolean gameState, boolean roomState, ArrayList<String> playerUserName,
			ArrayList<String> gamerUserName, ArrayList<Integer> scores) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameStarted(ArrayList<String> gamers) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
