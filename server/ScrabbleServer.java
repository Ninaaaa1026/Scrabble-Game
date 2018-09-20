package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Vector;

import remote.GameListener;
import remote.IGameManager;

/**
 * Creates an instance of the RemoteMath class and publishes it in the
 * rmiregistry
 * 
 */
public class ScrabbleServer extends UnicastRemoteObject implements GameListener {
	char character;
	int[] index = new int[2];

	private Vector<IGameManager> list = new Vector<IGameManager>();

	private static final long serialVersionUID = 1L;

	protected ScrabbleServer() throws RemoteException {
		super();
	}

	public static void main(String[] args) {

		try {

			GameListener scrabbleGame = new ScrabbleServer();

			// Publish the remote object's stub in the registry under the name "Compute"
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Compute", scrabbleGame);

			System.out.println("Scrabble server ready");

			// The server will continue running as long as there are remote objects exported
			// into
			// the RMI runtime, to remove remote objects from the
			// RMI runtime so that they can no longer accept RMI calls you can use:
			// UnicastRemoteObject.unexportObject(remoteMath, false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addCharacter(char character, int[] index) throws RemoteException {
		this.character=character;
		this.index=index;
		// Notify every player in the registered list
				for (Enumeration<IGameManager> e = list.elements(); e.hasMoreElements();) {
					IGameManager player = (IGameManager) e.nextElement();
					// Notify, if possible a player
					try {
						player.gameTableChanged(character, index);
					} catch (RemoteException re) {
						System.out.println("removing listener -" + player);
						// Remove the player
						list.remove(player);
					}
				}
	}

	@Override
	public void addClient(IGameManager player) throws RemoteException {
		System.out.println("adding monitor -" + player);
		list.add(player);
		for (Enumeration<IGameManager> e = list.elements(); e.hasMoreElements();) {
			IGameManager players = (IGameManager) e.nextElement();
			// Notify, if possible a player
			try {
				players.newClientJoined(players.getUserName());
			} catch (RemoteException re) {
				System.out.println("removing listener -" + players);
				// Remove the player
				list.remove(players);
			}
		}
	}

	@Override
	public void removeClient(IGameManager player) throws RemoteException {
		System.out.println("removing monitor -" + player);
		list.remove(player);
		for (Enumeration<IGameManager> e = list.elements(); e.hasMoreElements();) {
			IGameManager players = (IGameManager) e.nextElement();
			// Notify, if possible a player
			try {
				players.clientExit(players.getUserName());
			} catch (RemoteException re) {
				System.out.println("removing listener -" + players);
				// Remove the player
				list.remove(players);
			}
		}

	}

}
