package client;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
public class ScrabbleClient extends UnicastRemoteObject implements ClientInterface {
	protected ScrabbleClient() throws RemoteException  {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private ClientGUI gui = new ClientGUI();
	//mention the order of initiating the parameters and invoking them by gui
	private boolean gameState = false;
	private boolean roomState = false;
	private String roomCreatorName = "";
	private String currentPlayer = "";
	private ArrayList<String> playerUserName = new ArrayList<>(); //all the players
	private ArrayList<String> gamerUserName = new ArrayList<>(); //the players that are playing or in the game room
	private ArrayList<Integer> gamerScores = new ArrayList<>();
	private char[][] grid = new char[20][20];
	private String IPAddress;
	private int portNumber;
	public static GameLogin window;
	String userName;
	public static ScrabbleClient player; //= new ScrabbleClient();
	public static ServerInterface remoteServer;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			player = new ScrabbleClient();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					window = new GameLogin(player.gui);
					window.getFrame().setVisible(true);
					try {
						//Connect to the rmiregistry that is running on localhost
						Registry registry = LocateRegistry.getRegistry("localhost");
			           
						//Retrieve the stub/proxy for the remote math object from the registry
						remoteServer = (ServerInterface) registry.lookup("Scrabble");
						}catch(Exception e) {
							System.out.print(e.getMessage());
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

	public String getIPAddress() {
	    return this.IPAddress;
    }

    public int getPortNumber() {
	    return this.portNumber;
    }

    public void setIPAddress(String ip) {
	    this.IPAddress = ip;
    }

    public void setPortNumber(int port) {
	    this.portNumber = port;
    }

	private void updateMark(String gamer, Integer score) {
		for (int i=0; i<this.gamerUserName.size(); i++) {
			if (this.gamerUserName.get(i) == gamer) {
				this.gamerScores.set(i, score);
				return;
			}
		}
		System.out.println("Vote beginner not in the game!!!");
	}
	@Override
	public void clientAdded(String playerUserName)
			throws RemoteException {
		// a new player logged in
        if (!playerUserName.equals(userName)) {
            this.playerUserName.add(playerUserName);
        }
		System.out.println(this.playerUserName.toString());
		gui.freshPlayerList();
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
		System.out.println("roomCreatorName:" + this.roomCreatorName);
		gui.showLobby();
	}

	@Override
	public void playerInvited() throws RemoteException {
		if (userName.equals(roomCreatorName)) {
			gui.showInvitation();
		}
	}

	@Override
	public void invitationResponse(String invitedplayer, boolean agree)
			throws RemoteException {
		if (!agree) return;
		this.gamerUserName.add(this.userName);
		this.gamerScores.add(0);
		gui.showLobby();
	}

	@Override
	public void nextPlayer(char character, int rowIndex, int colIndex, String currentPlayer)
			throws RemoteException {
		// next turn
		grid[rowIndex][colIndex] = character;
		this.currentPlayer = currentPlayer;
		gui.showGame();
	}

	@Override
	public void pass(String nextUserName)
			throws RemoteException {
		// current player pass
		this.currentPlayer = nextUserName;
		gui.showGame();
	}

	@Override
	public void beginVote(char character, int startRowIndex, int startColIndex, int endRowIndex, int endColIndex,
			String userName)
			throws RemoteException {
		gui.beginVote(character, startRowIndex, startColIndex, endRowIndex, endColIndex,userName);
	}

	@Override
	public void voteSuccess(String beginVoteUserName, boolean accepted, int totalMark, String nextUserName)
			throws RemoteException {
		if (accepted) {
			updateMark(beginVoteUserName, totalMark);
		}
		//TODO GUI displays message
		gui.voteResult(beginVoteUserName,accepted,totalMark,nextUserName);
		this.currentPlayer = nextUserName;
		gui.showGame();
	}

	@Override
	public void gameOver(ArrayList<String> players, ArrayList<String> gamers, ArrayList<Integer> scores)
			throws RemoteException {
		this.playerUserName.clear();
		this.gamerUserName.clear();
		this.gamerScores.clear();
		this.playerUserName.addAll(players);
		this.gamerUserName.addAll(gamers);
		this.gamerScores.addAll(scores);
		gui.showGameResult();
	}

	@Override
	public void clientExited(String playerUserName)
			throws RemoteException {
		this.playerUserName.remove(playerUserName);
		gui.freshPlayerList();
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
		//Arrays.fill(this.grid, ' ');
		for(int i = 0; i < 20;i++) {
			for(int j = 0; j < 20; j++) {
				grid[i][j]=' ';
			}
		}
		gui.showLobby();
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
		gui.showGame();
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
		gui.showGame();
	}
}
