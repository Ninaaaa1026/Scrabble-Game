package remote;

import java.rmi.RemoteException;

public interface IGameManager {

	public void initiateGame(String gameState, String[] playerList) throws RemoteException;

	public void clientAdded(String[] playerList) throws RemoteException;

	public void roomCreated(String createplayerusername)throws RemoteException;

	public boolean playerInvited() throws RemoteException;

	public void invitationResponse(String invitedplayer,boolean isInvited)throws RemoteException;

	public void gameStarted	(String[] playerlistGame) throws RemoteException;

	public void gameTableChanged(char character, int[] index) throws RemoteException;
	
	public void currentPlayer(String currentPlayer) throws RemoteException;

	public boolean beginVote(int[] beginIndex, int[] endIndex) throws RemoteException;

	public void voteSuccess(int score,String[]playerlist)throws RemoteException;

	public void gameOver() throws RemoteException;

	public void clientExited(String[] playerList) throws RemoteException;

	public String getUserName() throws RemoteException;
}
