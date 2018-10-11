/***
 * author: Chenjing Yu, Jinnan Li, Mochuan Wang, Mengwen Ma
 * email:
 * chenjingy@student.unimelb.edu.au
 * mochuanw@student.unimelb.edu.au
 * mengwenm@student.unimelb.edu.au
 * jinnanl@student.unimelb.edu.au
 */
package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/***
 * This interface provides methods for client GUIs to invoke
 */
public interface ServerInterface extends Remote{
	/***
	 * Register in the game server with username and a reference of ClientInterface
	 * for server to invoke callback methods through the interface
	 * @param userName
	 * @param clientinstance
	 * @return (boolean) true if username is valid
	 * @throws RemoteException
	 */
	public boolean addClient(String userName, ClientInterface clientinstance)throws RemoteException;

	/***
	 * Notify the server that a player created the room with the player's username
	 * @param userName
	 * @return (boolean) true if room successfully created
	 * @throws RemoteException
	 */
	public boolean createRoom(String userName)throws RemoteException;

	/***
	 * Invoked by the room creator with username of the invited player
	 * @param userName player that is invited
	 * @throws RemoteException
	 */
	public void invitePlayer(String userName )throws RemoteException;

	/***
	 * Respond to the invitation with invited player's username
	 * @param agree
	 * @param userName player that responds
	 * @throws RemoteException
	 */
	public void respondToInvitation(boolean agree,String userName)throws RemoteException;

	/***
	 * Invoked by the room creator who want to start the game
	 * @throws RemoteException
	 */
	public void startGame()throws RemoteException;

	/***
	 * Invoked by the current player to notify a pass turn
	 * @param userName current turn's player
	 * @throws RemoteException
	 */
	public void passTurn(String userName)throws RemoteException;

	/***
	 * Notify to go to next turn with information of this turn's action
	 * @param character
	 * @param rowIndex
	 * @param colIndex
	 * @param userName current player who invokes this method
	 * @throws RemoteException
	 */
	public void nextTurn(char character, int rowIndex, int colIndex, String userName)throws RemoteException;

	/***
	 * Notify to start a vote with information of voted word and this turn's action
	 * @param character inputted in this turn
	 * @param startRowIndex
	 * @param startColIndex
	 * @param endRowIndex
	 * @param endColIndex
	 * @param userName current player who wants to start the vote
	 * @param rowIndex row index of the character inputted in this turn
	 * @param colIndex col index of the character inputted in this turn
	 * @throws RemoteException
	 */
	public void vote(char character, int startRowIndex, int startColIndex, int endRowIndex, int endColIndex,
			String userName,int rowIndex, int colIndex)throws RemoteException;

	/***
	 * Respond to server with player's username and whether agree to the vote
	 * @param agree
	 * @param userName player who voted
	 * @throws RemoteException
	 */
	public void agreeVote(boolean agree,String userName)throws RemoteException;

	/***
	 * Notify the server that the player exits the game system
	 * @param clientinstance
	 * @throws RemoteException
	 */
	public void notify(ClientInterface clientinstance)throws RemoteException;

	/***
	 * Invoked by the client (GameLogin) to check connection
	 * @throws RemoteException
	 */
	public void checkConnect() throws RemoteException;

}
