package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote{
	//transmit client instance and username and return if the username is valid
	public boolean addClient(String userName, ClientInterface clientinstance, String ipAddress, int portNumber)throws RemoteException;
	
	//transmit the username who createroom and return if he/she creates the room successfully
	public boolean createRoom(String userName)throws RemoteException;
	
	// invite player one by one 
	public void invitePlayer(String userName )throws RemoteException;
	
	//clients choose if he/she agrees to play a game
	public void respondToInvitation(boolean agree,String userName)throws RemoteException;
	
	//creater start a game
	public void startGame()throws RemoteException;
	
	//game player do nothing. if all players pass, the game is over
	public void passTurn(String userName)throws RemoteException;
	
	//player adds a character
	//public void changeTable(char character, int [] index)throws RemoteException;
	
	// after player adds a character, he/she needs to choose next then next people's turn
	public void nextTurn(char character, int rowIndex, int colIndex, String userName)throws RemoteException;
	
	//after player add a character, he/she could start a vote to get a score
	public void vote(char character, int startRowIndex, int startColIndex, int endRowIndex, int endColIndex,
			String userName,int rowIndex, int colIndex)throws RemoteException;
	
	//after a player start a vote, the rest player could choose to aggree or not
	public void agreeVote(boolean agree,String userName)throws RemoteException;
	
	//if a player who is playing a game exit, the game is over; if a player
	//who is not playing a game, just remove the usrname from the list
	public void notify(ClientInterface clientinstance)throws RemoteException;
	
	public void checkConnect() throws RemoteException;

}
