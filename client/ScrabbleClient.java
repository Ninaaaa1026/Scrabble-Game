/***
 * author: Chenjing Yu, Jinnan Li, Mochuan Wang, Mengwen Ma
 * email:
 * chenjingy@student.unimelb.edu.au
 * mochuanw@student.unimelb.edu.au
 * mengwenm@student.unimelb.edu.au
 * jinnanl@student.unimelb.edu.au
 */
package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.border.LineBorder;

import remote.ClientInterface;
import remote.ServerInterface;

/**
 * This class retrieves a reference to the remote object from the RMI registry.
 * It invokes the methods on the remote object as if it was a local object of
 * the type of the remote interface.
 *
 */
public class ScrabbleClient extends UnicastRemoteObject implements ClientInterface {

	protected ScrabbleClient() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	private ClientGUI gui = new ClientGUI();
	// mention the order of initiating the parameters and invoking them by gui
	private boolean gameState = false; // is a game in play
	private boolean roomState = false; // is room created (game is not started)
	private String roomCreatorName = "";
	private String currentPlayer = "";
	private ArrayList<String> playerUserName = new ArrayList<>(); // all the players
	private ArrayList<String> gamerUserName = new ArrayList<>(); // the players that are playing or in the game room
	private ArrayList<Integer> gamerScores = new ArrayList<>(); //correspond to gamerUserName
	private char[][] grid = new char[20][20];
	public static GameLogin window;
	String userName;
	public static ScrabbleClient player;
	public static ServerInterface remoteServer;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			player = new ScrabbleClient();
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						window = new GameLogin(player.gui);
						window.getFrame().setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	public String getUserName() {
		return userName;
	}

	protected void setUserName(String userName) {
		this.userName = userName;
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

	public void setRoomState(boolean roomState) {
		this.roomState = roomState;
	}

	public void setGameState(boolean gameState) {
		this.gameState = gameState;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void ClearGamerUserName() {
		this.gamerUserName.clear();
	}

	public void ClearGamerScores() {
		this.gamerScores.clear();
	}

	private void updateMark(String gamer, Integer score) {
		for (int i = 0; i < this.gamerUserName.size(); i++) {
			if (this.gamerUserName.get(i).equals(gamer)) {
				this.gamerScores.set(i, score);
				return;
			}
		}
		System.out.println("Vote beginner not in the game!!!");
	}

	@Override
	public void clientAdded(String playerUserName) throws RemoteException {
		// a new player logged in
		if (!playerUserName.equals(userName)) {
			this.playerUserName.add(playerUserName);
		}
		System.out.println(this.playerUserName.toString());
		gui.freshPlayerList();
	}

	@Override
	public void roomCreated(String createplayerusername) throws RemoteException {
		this.roomState = true;
		this.gamerUserName.clear();
		this.gamerScores.clear();
		this.roomCreatorName = createplayerusername;
		this.gamerUserName.add(createplayerusername);
		this.gamerScores.add(0);
		System.out.println("roomCreatorName:" + this.roomCreatorName);
		if (gui.returnLobby)
			gui.showLobby();
	}

	@Override
	public void playerInvited() throws RemoteException {
		if (!userName.equals(roomCreatorName)) {
			gui.showInvitation();
		}
	}

	@Override
	public void invitationResponse(String invitedplayer, boolean agree) throws RemoteException {
		if (agree) {
			if (!this.gamerUserName.contains(invitedplayer)) {
				this.gamerUserName.add(invitedplayer);
				this.gamerScores.add(0);
			}
			gui.lblInvitemessage.setText(invitedplayer + " joins the game.");
		} else {
			if (roomCreatorName.equals(userName))
				gui.lblInvitemessage.setText(invitedplayer + " refuses your invitation.");
		}
		gui.showLobby();
	}

	@Override
	public void nextPlayer(char character, int rowIndex, int colIndex, String currentPlayer) throws RemoteException {
		// next turn
		grid[rowIndex][colIndex] = character;
		this.currentPlayer = currentPlayer;
		if (userName.equals(currentPlayer))
			gui.GameTablePanel.setBorder(new LineBorder(Color.RED, 2, true));
		else
			gui.GameTablePanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		gui.showGame(this.currentPlayer);
		gui.lblMessage.setText("");
		gui.gameTable.clearSelection();
	}

	@Override
	public void pass(String nextUserName) throws RemoteException {
		// current player pass
		this.currentPlayer = nextUserName;
		if (userName.equals(nextUserName))
			gui.GameTablePanel.setBorder(new LineBorder(Color.RED, 2, true));
		else
			gui.GameTablePanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		gui.showGame(this.currentPlayer);
		gui.lblMessage.setText("");
		gui.gameTable.clearSelection();
	}

	@Override
	public void beginVote(char character, int startRowIndex, int startColIndex, int endRowIndex, int endColIndex,
			String userName, int rowIndex, int colIndex) throws RemoteException {
		grid[rowIndex][colIndex] = character;
		gui.beginVote(character, startRowIndex, startColIndex, endRowIndex, endColIndex, userName);
	}

	@Override
	public void voteSuccess(String beginVoteUserName, boolean accepted, int totalMark, int mark, String nextUserName)
			throws RemoteException {
		if (accepted) {
			updateMark(beginVoteUserName, totalMark);
		}
		this.currentPlayer = nextUserName;
		if (userName.equals(nextUserName))
			gui.GameTablePanel.setBorder(new LineBorder(Color.RED, 2, true));
		else
			gui.GameTablePanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		gui.showGame(nextUserName);
		gui.voteResult(beginVoteUserName, accepted, mark, nextUserName);
		gui.gameTable.clearSelection();
	}

	@Override
	public void gameOver(ArrayList<String> players, ArrayList<String> gamers, ArrayList<Integer> scores)
			throws RemoteException {
		this.playerUserName.clear();
		this.gamerUserName.clear();
		this.gamerScores.clear();
		emptyTalbe();
		this.playerUserName.addAll(players);
		this.gamerUserName.addAll(gamers);
		this.gamerScores.addAll(scores);
		gui.showGameResult();
		this.gamerUserName.clear();
		this.gamerScores.clear();
		emptyTalbe();
		gui.returnLobby = false;
		roomState = false;
		gameState = false;
		roomCreatorName = "";
		currentPlayer = "";
	}

	@Override
	public void clientExited(String playerUserName) throws RemoteException {
		this.playerUserName.remove(playerUserName);
		gui.freshPlayerList();
	}

	@Override
	public void initiateGame(boolean gameState, boolean roomState, String creator, ArrayList<String> players,
			ArrayList<String> gamers) {
		this.gameState = gameState;
		this.roomState = roomState;
		this.roomCreatorName = creator;
		this.playerUserName.clear();
		this.gamerUserName.clear();
		this.playerUserName.addAll(players);
		this.gamerUserName.addAll(gamers);
		this.gamerScores.ensureCapacity(gamers.size());
		initiateScores(gamers.size());
		emptyTalbe();
		gui.showLobby();
	}

	@Override
	public void viewGame(ArrayList<String> playerUserName, ArrayList<String> gamerUserName, ArrayList<Integer> scores,
			String currentPlayer, char[][] table) throws RemoteException {
		this.gameState = true;
		this.roomState = true;
		this.playerUserName.clear();
		this.gamerUserName.clear();
		this.gamerScores.clear();
		this.playerUserName.addAll(playerUserName);
		this.gamerUserName.addAll(gamerUserName);
		this.gamerScores.addAll(scores);
		this.currentPlayer = currentPlayer;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				grid[i][j] = table[i][j];
			}
		}
		gui.showGame(this.currentPlayer);
	}

	@Override
	public void gameStarted(ArrayList<String> gamers, ArrayList<String> players, String currentPlayer)
			throws RemoteException {
		this.playerUserName.clear();
		this.gamerUserName.clear();
		this.playerUserName.addAll(players);
		this.gamerUserName.addAll(gamers);
		this.gamerScores.ensureCapacity(gamers.size());
		emptyTalbe();
		initiateScores(gamers.size());
		this.currentPlayer = currentPlayer;
		if (userName.equals(currentPlayer))
			gui.GameTablePanel.setBorder(new LineBorder(Color.RED, 2, true));
		else
			gui.GameTablePanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		gui.showGame(this.currentPlayer);
		gui.lblInvitemessage.setText("");
		gui.lblMessage.setText("");
		gui.gameTable.clearSelection();
	}

	private void initiateScores(int size) {
		this.gamerScores.clear();
		for (int i = 0; i < size; i++) {
			this.gamerScores.add(0);
		}
	}

	public void freshGamer(ArrayList<String> gamers, ArrayList<String> players, ArrayList<Integer> scores,
			boolean gameState, boolean roomState) {
		this.playerUserName.clear();
		this.gamerUserName.clear();
		this.gamerScores.clear();
		this.playerUserName.addAll(players);
		this.gamerUserName.addAll(gamers);
		this.gamerScores.addAll(scores);
		this.gameState = gameState;
		this.roomState = roomState;
		gui.freshGamerList();
	}

	private void emptyTalbe() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				grid[i][j] = ' ';
			}
		}
	}
}
