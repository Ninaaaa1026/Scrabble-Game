package client;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
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
	private ClientGUI gui = new ClientGUI();
	//注意一下参数初始化和GUI获取参数的顺序
	private boolean gameState = false;
	private boolean roomState = false;
	private String roomCreatorName;
	private ArrayList<String> playerUserName;
	private ArrayList<String> gamerUserName;
	private ArrayList<Integer> gamerScores;

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
					GameLogin window = new GameLogin(gui);
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

	public boolean getGameState() {
		return this.gameState;
	}

	public boolean getRoomState() {
		return this.roomState;
	}

	public String getRoomCreatorName() {
		return this.roomCreatorName;
	}

	public ArrayList<String> getPlayers() {
		return this.playerUserName;
	}

	public ArrayList<String> getGamers() {
		return this.gamerUserName;
	}

	public ArrayList<Integer> getGamerScores() {
		return this.gamerScores;
	}


	@Override
	public void clientAdded(String playerUserName) throws RemoteException {
		// TODO Auto-generated method stub
		this.playerUserName.add(playerUserName);
	}

	@Override
	public void roomCreated(String createplayerusername) throws RemoteException {
		// TODO Auto-generated method stub
		this.roomState = true;
		this.playerUserName.remove(createplayerusername);
		//this.gamerUserName.add(createplayerusername);
		//this.gamerScores.add(0);
		this.roomCreatorName = createplayerusername;
	}

	@Override
	public void playerInvited() throws RemoteException {
		// TODO Auto-generated method stub
		//the player is invited to join the room
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
	public void beginVote(char character, int startRowIndex, int startColIndex, int endRowIndex, int endColIndex,
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
		this.gameState = gameState;
		this.roomState = roomState;
		this.playerUserName = new ArrayList<String>(playerUserName.size());
		this.playerUserName.addAll(playerUserName);
		this.gamerUserName = new ArrayList<String>(gamerUserName.size());
		this.gamerUserName.addAll(gamerUserName);
		this.gamerScores = new ArrayList<Integer>(scores.size());
		this.gamerScores.addAll(scores);
	}

	@Override
	public void gameStarted(ArrayList<String> gamers) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
