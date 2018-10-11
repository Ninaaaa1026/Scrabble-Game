/***
 * author: Chenjing Yu, Jinnan Li, Mochuan Wang, Mengwen Ma
 * email:
 * chenjingy@student.unimelb.edu.au
 * mochuanw@student.unimelb.edu.au
 * mengwenm@student.unimelb.edu.au
 * jinnanl@student.unimelb.edu.au
 */
package remote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.rmi.Remote;

/***
 * This interface provides callback methods for servers to invoke
 * so that clients are notified when events occur on server's side.
 */
public interface ClientInterface extends Remote {
	/***
	 * Invoked by server when the game is not started and this client logs in
	 * @param gameState is there a game in play
	 * @param roomState is a room created and the game is not started
	 * @param creator who create the room
	 * @param playerUserName a list of all players' names
	 * @param gamerUserName a list of gamers' (players who are playing the game or in the game room) names
	 * @throws RemoteException
	 */
	public void initiateGame(boolean gameState, boolean roomState, String creator, ArrayList<String> playerUserName,ArrayList<String> gamerUserName) throws RemoteException;

	/***
	 * Invoked by server when a game is in play and this client logs in.
	 * This client can only view the game.
	 * @param playerUserName
	 * @param gamerUserName
	 * @param scores
	 * @param currentPlayer
	 * @param table
	 * @throws RemoteException
	 */
	public void viewGame(ArrayList<String> playerUserName,
			ArrayList<String> gamerUserName, ArrayList<Integer> scores, String currentPlayer,char[][] table) throws RemoteException;

	/***
	 * Notify this player that a new player joined
	 * @param playerUserName
	 * @throws RemoteException
	 */
	public void clientAdded(String playerUserName) throws RemoteException;

	/***
	 * Notify that a room is created with the username of room creator
	 * @param createplayerusername
	 * @throws RemoteException
	 */
	public void roomCreated(String createplayerusername) throws RemoteException;

	/***
	 * Notify that this player has been invited to join the game room from the room creator
	 * @throws RemoteException
	 */
	public void playerInvited() throws RemoteException;

	/***
	 * Notify that a player's response to the invitation from the room creator
	 * @param invitedplayer
	 * @param agree
	 * @throws RemoteException
	 */
	public void invitationResponse(String invitedplayer, boolean agree) throws RemoteException;

	/***
	 * Notify that a game is just started with information of gamers and current player (first turn)
	 * @param gamers
	 * @param players
	 * @param currentPlayer
	 * @throws RemoteException
	 */
	public void gameStarted(ArrayList<String> gamers, ArrayList<String> players, String currentPlayer)
			throws RemoteException;

	/***
	 * Notify about a new turn with information of new turn's player
	 * and the character last player input
	 * @param character
	 * @param rowIndex
	 * @param colIndex
	 * @param nextUserName
	 * @throws RemoteException
	 */
	public void nextPlayer(char character, int rowIndex, int colIndex, String nextUserName) throws RemoteException;

	/***
	 * Notify about a new turn with information of new turn's player,
	 * last turn is passed
	 * @param nextUserName
	 * @throws RemoteException
	 */
	public void pass(String nextUserName) throws RemoteException;

	/***
	 * Notify a turn of vote with voted words and username of current voter,
	 * and last turn's input information
	 * @param character the character last turn's player input
	 * @param startRowIndex
	 * @param startColIndex
	 * @param endRowIndex
	 * @param endColIndex
	 * @param userName
	 * @param rowIndex the row index of last turn's input
	 * @param colIndex the col index of last turn's input
	 * @throws RemoteException
	 */
	public void beginVote(char character, int startRowIndex, int startColIndex, int endRowIndex, int endColIndex,
			String userName,int rowIndex,int colIndex) throws RemoteException;

	/***
	 * Notify the vote is successful with information of the vote beginner and his total mark,
	 * and the next turn's player
	 * @param beginVoteUserName
	 * @param accepted
	 * @param totalMark
	 * @param mark
	 * @param nextUserName
	 * @throws RemoteException
	 */
	public void voteSuccess(String beginVoteUserName, boolean accepted, int totalMark, int mark,String nextUserName)throws RemoteException;

	/***
	 * Notify end of game with player list and information of gamers and corresponding scores
	 * @param players
	 * @param gamers
	 * @param scores
	 * @throws RemoteException
	 */
	public void gameOver(ArrayList<String> players, ArrayList<String> gamers, ArrayList<Integer> scores) throws RemoteException;

	/***
	 * Notify that a player log off when there's no game in play or the player is a viewer
	 * @param playerUserName
	 * @throws RemoteException
	 */
	public void clientExited(String playerUserName) throws RemoteException;

	/***
	 * Get the username of this client
	 * @return
	 * @throws RemoteException
	 */
	public String getUserName() throws RemoteException;

	/***
	 * Invoked by server to refresh the player list, gamer list, scores and state of game
	 * @param gamers
	 * @param players
	 * @param scores
	 * @param gameState
	 * @param roomState
	 * @throws RemoteException
	 */
	public void freshGamer(ArrayList<String> gamers, ArrayList<String> players,ArrayList<Integer> scores, boolean gameState, boolean roomState) throws RemoteException;
}
