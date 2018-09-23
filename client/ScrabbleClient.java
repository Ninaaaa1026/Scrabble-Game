package client;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
	private ArrayList<String> playerUserName = new ArrayList<>(); //all the players
	private ArrayList<String> gamerUserName = new ArrayList<>(); //the players that are playing or in the game room
	private ArrayList<Integer> gamerScores = new ArrayList<>();
	private char[][] grid = new char[20][20];

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

	public char[][] getGrid() {
		return this.grid;
	}

	public String getCurrentPlayer() {
		return this.currentPlayer;
	}

	private void updateMark(String gamer, Integer score) {
		for (int i=0; i<this.gamerUserName.size(); i++) {
			if (this.gamerUserName.get(i) == gamer) {
				this.gamerScores.set(i, score);
				return;
			}
		}
		//log
		System.out.println("Vote beginner not in the game!!!");
	}
	@Override
	public void clientAdded(String playerUserName)
			throws RemoteException {
		// a new player logged in
		this.playerUserName.add(playerUserName);
		//TODO GUI refresh player list
	}

	@Override
	public void roomCreated(String createplayerusername)
			throws RemoteException {
		//this.gameState = false;
		this.roomState = true;
		this.gamerUserName.clear();
		this.gamerScores.clear();
		this.roomCreatorName = createplayerusername;
		this.gamerUserName.add(createplayerusername);
		this.gamerScores.add(0);
		// TODO GUI show room and the creator in the room
	}

	@Override
	public void playerInvited() throws RemoteException {
		//TODO: GUI shows the dialog for the player respond to the invitaion
	}

	@Override
	public void invitationResponse(String invitedplayer, boolean agree)
			throws RemoteException {
		if (!agree) return;
		this.gamerUserName.add(this.userName);
		this.gamerScores.add(0);
		// TODO: GUI shows the gamer in game room
	}

	@Override
	public void nextPlayer(char character, int rowIndex, int colIndex, String currentPlayer)
			throws RemoteException {
		// next turn
		grid[rowIndex][colIndex] = character;
		this.currentPlayer = currentPlayer;
		//TODO: GUI refresh grid and current player
		if (currentPlayer == getUserName()) {
			//TODO: GUI enable this player to play
		}
	}

	@Override
	public void pass(String nextUserName)
			throws RemoteException {
		// current player pass
		this.currentPlayer = nextUserName;
		//TODO: GUI displays a message that shows pass and refresh the current player
	}

	@Override
	public void beginVote(char character, int startRowIndex, int startColIndex, int endRowIndex, int endColIndex,
			String userName)
			throws RemoteException {
		// TODO: GUI displays vote dialog and highlight the voted word
		return;
	}

	@Override
	public void voteSuccess(String beginVoteUserName, boolean accepted, int totalMark, String nextUserName)
			throws RemoteException {
		// TODO Auto-generated method stub
		if (accepted) {
			updateMark(beginVoteUserName, totalMark);
		}
		//TODO GUI displays message and update mark if accepted
		this.currentPlayer = nextUserName;
	}

	@Override
	public void gameOver(ArrayList<String> players, ArrayList<String> gamers, ArrayList<Integer> scores)
			throws RemoteException {
		//TODO GUI displays result of the game
		this.gameState = false;
		this.roomState = false;
		this.playerUserName.clear();
		this.gamerUserName.clear();
		this.gamerScores.clear();
		Arrays.fill(this.grid, null);
		this.playerUserName.addAll(players);
		//TODO GUI shows initial state of the table and the player list
	}

	@Override
	public void clientExited(String playerUserName)
			throws RemoteException {
		this.playerUserName.remove(playerUserName);
		//TODO GUI refresh player list
	}

	@Override
	public void initiateGame(boolean gameState,
							 boolean roomState,
							 ArrayList<String> players,
							 ArrayList<String> gamers) {
		this.gameState = gameState;
		this.roomState = roomState;
		this.playerUserName.clear();
		this.gamerUserName.clear();
		this.gamerScores.clear();
		this.playerUserName.addAll(players);
		this.gamerUserName.addAll(gamers);
		this.gamerScores.ensureCapacity(gamers.size());
		Collections.fill(this.gamerScores, 0);
		//TODO GUI displays player list, if there's a room, displays the room and gamers
	}
	@Override
	public void viewGame(ArrayList<String> playerUserName,
						 ArrayList<String> gamerUserName,
						 ArrayList<Integer> scores,
						 String currentPlayer,
						 char[][] table)
			throws RemoteException {
		this.gameState = true;
		this.roomState = true;
		this.playerUserName.clear();
		this.gamerUserName.clear();
		this.gamerScores.clear();
		this.playerUserName.addAll(playerUserName);
		this.gamerUserName.addAll(gamerUserName);
		this.gamerScores.addAll(scores);
		this.currentPlayer = currentPlayer;
		Arrays.fill(this.grid, table);
		// TODO GUI display: who's turn, current grid, players, gamers, scores
	}

	@Override
	public void gameStarted(ArrayList<String> gamers, ArrayList<String> players, String currentPlayer)
			throws RemoteException {
		this.playerUserName.clear();
		this.gamerUserName.clear();
		this.gamerScores.clear();
		this.playerUserName.addAll(players);
		this.gamerUserName.addAll(gamers);
		this.gamerScores.ensureCapacity(gamers.size());
		Collections.fill(this.gamerScores, 0);
		this.currentPlayer = currentPlayer;
		//TODO: GUI refresh grid and current player
		if (currentPlayer == getUserName()) {
			//TODO: GUI enable this player to play
		}
	}
}
