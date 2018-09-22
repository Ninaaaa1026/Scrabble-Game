package client;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
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
	//mention the order of initiating the parameters and invoking them by gui
	private boolean gameState = false;
	private boolean roomState = false;
	private String roomCreatorName;
	private String currentPlayer;
	private ArrayList<String> playerUserName;
	private ArrayList<String> gamerUserName;
	private ArrayList<Integer> gamerScores;
	private char[][] grid;

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
					GameLogin window = new GameLogin(player.gui);
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
		// TODO show room and the creator in the room
		this.roomState = true;
		this.playerUserName.remove(createplayerusername);
		//TODO GUI: remove from player list
		this.gamerUserName.add(createplayerusername);
		this.gamerScores.add(0);
		this.roomCreatorName = createplayerusername;
	}

	@Override
	public void playerInvited() throws RemoteException {
		// TODO Auto-generated method stub
		//TODO: GUI shows the dialog for the player respond to the invitaion
		//TODO: GUI get response and deal with it
		boolean accept = false;
		if (accept) {
			this.playerUserName.remove(this.userName);
			this.gamerUserName.add(this.userName);
			this.gamerScores.add(0);
		}
		remoteServer.respondToInvitation(accept, getUserName());
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
		return;
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
	public void initiateGame(boolean)
	@Override
	public void viewGame(ArrayList<String> playerUserName,
			ArrayList<String> gamerUserName, ArrayList<Integer> scores, String currentPlayer) throws RemoteException {
		// TODO GUI display: who's turn, current grid, players, gamers, scores
		this.gameState = true;
		this.roomState = true;
		this.currentPlayer = currentPlayer;
		this.playerUserName = new ArrayList<String>(playerUserName.size());
		this.playerUserName.addAll(playerUserName);
		this.gamerUserName = new ArrayList<String>(gamerUserName.size());
		this.gamerUserName.addAll(gamerUserName);
		this.gamerScores = new ArrayList<Integer>(scores.size());
		this.gamerScores.addAll(scores);
	}

	@Override
	public void gameStarted(ArrayList<String> gamers, ArrayList<String> players, String currentPlayer) throws RemoteException {
		// TODO Auto-generated method stub
		this.playerUserName = new ArrayList<String>(players.size());
		this.playerUserName.addAll(players);
		this.gamerUserName = new ArrayList<String>(gamers.size());
		this.gamerUserName.addAll(gamers);
		this.gamerScores = new ArrayList<Integer>(gamers.size());
		Collections.fill(this.gamerScores, 0);
		this.currentPlayer = currentPlayer;
	}

}
