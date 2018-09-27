package remote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.rmi.Remote;

public interface ClientInterface extends Remote {
	// gamestate means if the game is playing now and transmit the client instance
	// to server
	public void initiateGame(boolean gameState, boolean roomState, String creator, ArrayList<String> playerUserName,ArrayList<String> gamerUserName) throws RemoteException;

	public void viewGame(ArrayList<String> playerUserName,
			ArrayList<String> gamerUserName, ArrayList<Integer> scores, String currentPlayer,char[][] table) throws RemoteException;
	
	// add a new client to the list
	public void clientAdded(String playerUserName) throws RemoteException;

	// set people who create the room
	public void roomCreated(String createplayerusername) throws RemoteException;

	// check if the player accept the invitation
	public void playerInvited() throws RemoteException;

	// let the client know if the player accept the invitation
	public void invitationResponse(String invitedplayer, boolean agree) throws RemoteException;

	// start a game and transmit the players who wants to play this game
	public void gameStarted(ArrayList<String> gamers, ArrayList<String> players, String currentPlayer)
			throws RemoteException;

	// change the clients' game table set player's turn
	public void nextPlayer(char character, int rowIndex, int colIndex, String nextUserName) throws RemoteException;

	// pass this turn, the argument is the next player
	public void pass(String nextUserName) throws RemoteException;

	// let the players to vote if the string is valid
	public void beginVote(char character, int startRowIndex, int startColIndex, int endRowIndex, int endColIndex,
			String userName,int rowIndex,int colIndex) throws RemoteException;

	// if all players think the string is valid , the people get the score.
	public void voteSuccess(String beginVoteUserName, boolean accepted, int totalMark, int mark,String nextUserName)throws RemoteException;

	// gameover
	public void gameOver(ArrayList<String> players, ArrayList<String> gamers, ArrayList<Integer> scores) throws RemoteException;

	// remove the client in other clients'list
	public void clientExited(String playerUserName) throws RemoteException;

	// get the clients username
	public String getUserName() throws RemoteException;
	
	public void freshGamer(ArrayList<String> gamers, ArrayList<String> players,ArrayList<Integer> scores, boolean gameState, boolean roomState) throws RemoteException;
}
