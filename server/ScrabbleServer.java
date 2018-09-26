package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import remote.ClientInterface;
import remote.ServerInterface;

public class ScrabbleServer extends UnicastRemoteObject implements ServerInterface {
	private String nextPlayer;
	private int wordLength;
	private char[][] table = new char[20][20];
	private String voteBeginner;
	private int passGamerNumber = 0;
	private int totalVote = 0;
	private int agreeVote = 0;
	boolean gameState = false;
	boolean roomState = false;
	private static final long serialVersionUID = 1L;
	private String creator = null;
	private ArrayList<ClientInterface> players = new ArrayList<ClientInterface>();
	private ArrayList<String> playerNames = new ArrayList<String>();
	private ArrayList<String> gamers = new ArrayList<String>();
	private ArrayList<Integer> gamerScores = new ArrayList<Integer>();

	// private Map<String, Integer> gamerScores = new HashMap<String, Integer>();

	public static void main(String[] args) {

		try {
			ScrabbleServer scrabble = new ScrabbleServer();

			// Publish the remote object's stub in the registry under the name "Compute"
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("Scrabble", scrabble);
			System.out.println("Scrabble Server ready.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected ScrabbleServer() throws RemoteException {
		super();
	}

	@Override
	// ipaddress and port number is not used so far.
	public synchronized boolean addClient(String userName, ClientInterface clientinstance, String ipAddress,
			int portNumber) throws RemoteException {

		// Examine if username already exists
		if (playerNames.contains(userName)) {
			return false;
		}

		// If not exists, add
		players.add(clientinstance);
		playerNames.add(userName);

		// callback the newplayer
		try {
			if (gameState) {
				clientinstance.viewGame(playerNames, gamers, gamerScores, nextPlayer, table);
			} else {
				clientinstance.initiateGame(gameState, roomState, creator, playerNames, gamers);
			}
		} catch (RemoteException re) {
			notify(clientinstance);
		}

		// callback all players.
		for (ClientInterface e : players) {
			ClientInterface player = e;
			try {
				player.clientAdded(userName);
			} catch (RemoteException re) {
				notify(player);
			}
		}

		return true;
	}

	@Override
	/*
	 * boolean gameState means if the game is start, if it is true, player can't
	 * create room. boolean roomState means if the room is create, if it is true,
	 * the room is already created but the game has'not start. if the roomState is
	 * false, the room has not been create, so the player could create a room.
	 * 
	 * @see remote.ServerInterface#createRoom(java.lang.String)
	 */
	public synchronized boolean createRoom(String userName) throws RemoteException {
		/*
		 * if (gameState) { System.out.println("the game is playing"); for
		 * (ClientInterface e : players) { ClientInterface player = e; if
		 * (player.getUserName().equals(userName)) { ClientInterface newPlayer = player;
		 * return false; } } return false; } else
		 */
		//
		if (roomState) {

			System.out.println("the room is created");
			return false;

		} else {
			System.out.println("you can create room");
			this.creator = userName;
			gamers.add(userName);
			gamerScores.add(0);
			roomState = true;
			// Call back to all clients
			for (ClientInterface e : players) {
				ClientInterface player = e;
				try {
					player.roomCreated(creator);
				} catch (RemoteException re) {
					System.out.println("creatroom error");
					notify(player);
				}
			}
			return true;
		}

	}

	@Override
	// invite player one by one
	public void invitePlayer(String userName) throws RemoteException {
		for (ClientInterface e : players) {
			ClientInterface player = e;
			if (player.getUserName().equals(userName)) {
				try {
					player.playerInvited();
				} catch (RemoteException re) {
					System.out.println("inviteplayer error");
					notify(player);
				}

			}
		}
	}

	@Override
	// clients choose if he/she agrees to play a game
	public void respondToInvitation(boolean agree, String userName) throws RemoteException {
		if (agree) {
			if (!gamers.contains(userName)) {
				gamers.add(userName);
				gamerScores.add(0);
			}			
		}
		for (ClientInterface e : players) {
			ClientInterface player = e;
			try {
				player.invitationResponse(userName, agree);
			} catch (RemoteException re) {
				notify(player);
			}
		}
	}

	@Override
	// creater start the game and let
	public void startGame() throws RemoteException {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				table[i][j] = ' ';
			}
		}
		// Arrays.fill(this.table, ' ');
		String currentPlayer = gamers.get(0);
		/*
		 * for(int i = 0; i < gamers.size(); i++) { gamerScores.add(0); }
		 */
		for (ClientInterface e : players) {
			ClientInterface player = e;
			try {
				nextPlayer = currentPlayer;
				player.gameStarted(gamers, playerNames, currentPlayer);// ArrayList<String> gamers, ArrayList<String>
																		// players, String currentPlayer
			} catch (RemoteException re) {
				System.out.println("startgame error");
				notify(player);
			}
		}
		gameState = true;

	}

	@Override
	public void passTurn(String userName) throws RemoteException {
		passGamerNumber = passGamerNumber + 1;
		if (passGamerNumber == gamers.size()) {

			for (int i = 0; i < gamers.size(); i++) {
				for (int j = i; j > 0; j--) {
					// Switch 2 continous players
					if (gamerScores.get(j) > gamerScores.get(j - 1)) {
						gamerScores.add(j + 1, gamerScores.get(j - 1));
						gamerScores.remove(j - 1);
						gamers.add(j + 1, gamers.get(j - 1));
						gamers.remove(j - 1);

					} else {
						break;
					}
				}
			}

			for (ClientInterface e : players) {
				ClientInterface player = e;
				try {
					player.gameOver(playerNames, gamers, gamerScores);
				} catch (RemoteException re) {
					System.out.println("game over question");
					notify(player);
				}
			}

			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					table[i][j] = ' ';
				}
			}

			passGamerNumber = 0;
			gamers.clear();
			gamerScores.clear();
			this.roomState = false;
			this.gameState = false;
		} else {
			int current = gamers.indexOf(userName);
			if (current == gamers.size() - 1) {
				current = -1;
			}
			nextPlayer = gamers.get(current + 1);

			for (ClientInterface e : players) {
				ClientInterface player = e;
				try {
					player.pass(nextPlayer);
				} catch (RemoteException re) {
					notify(player);
				}
			}
		}
	}

	@Override
	public void nextTurn(char character, int rowIndex, int colIndex, String userName) throws RemoteException {
		passGamerNumber = 0;
		table[rowIndex][colIndex] = character;
		int current = gamers.indexOf(userName);
		System.out.println(userName);
		System.out.println("current is " + current);
		if (current == gamers.size() - 1) {
			current = -1;
		}
		System.out.println("current is " + current);
		nextPlayer = gamers.get(current + 1);

		for (ClientInterface e : players) {
			ClientInterface player = e;
			try {
				player.nextPlayer(character, rowIndex, colIndex, nextPlayer);
			} catch (RemoteException re) {
				notify(player);
			}
		}
	}

	@Override
	// client should handle if the user equals himself, he won't do the same thing
	// as others
	public void vote(char character, int startRowIndex, int startColIndex, int endRowIndex, int endColIndex,
			String userName, int rowIndex, int colIndex) throws RemoteException {
		this.totalVote = 0;
		this.agreeVote = 0;
		this.table[rowIndex][colIndex] = character;
		if (startRowIndex == endRowIndex) {
			this.wordLength = endColIndex - startColIndex + 1;
		} else {
			this.wordLength = endRowIndex - startRowIndex + 1;
		}
		passGamerNumber = 0;
		voteBeginner = userName;
		for (ClientInterface e : players) {
			ClientInterface player = e;
			try {
				player.beginVote(character, startRowIndex, startColIndex, endRowIndex, endColIndex, userName, rowIndex,
						colIndex);
			} catch (RemoteException re) {
				System.out.println("vote error");
				notify(player);
			}
		}

	}

	@Override
	//
	public void agreeVote(boolean agree, String userName) throws RemoteException {
		if (agree) {
			this.totalVote = totalVote + 1;
			this.agreeVote = agreeVote + 1;

		} else {
			this.totalVote = totalVote + 1;
		}

		if (totalVote == gamers.size() - 1) {
			if (agreeVote == gamers.size() - 1) {
				boolean accepted = true;
				int current = gamers.indexOf(voteBeginner);
				int currentScore = gamerScores.get(current);
				gamerScores.set(current, currentScore + wordLength);

				if (current == gamers.size() - 1) {
					current = -1;
				}
				this.nextPlayer = gamers.get(current + 1);

				for (ClientInterface e : players) {
					ClientInterface player = e;

					try {
						player.voteSuccess(voteBeginner, accepted, currentScore + wordLength, wordLength, nextPlayer);// voteSuccess(String
						// beginVoteUserName,
						// boolean
						// accepted,
						// int
						// totalMark,
						// String
						// nextUserName)throws
						// RemoteException;

					} catch (RemoteException re) {
						System.out.println("vote error");
						notify(player);
					}
				}

			} else {
				boolean accepted = false;
				int current = gamers.indexOf(voteBeginner);
				int currentScore = gamerScores.get(current);
				if (current == gamers.size() - 1) {
					current = -1;
				}
				this.nextPlayer = gamers.get(current + 1);
				for (ClientInterface e : players) {
					ClientInterface player = e;

					try {
						player.voteSuccess(voteBeginner, accepted, currentScore, 0, nextPlayer);

					} catch (RemoteException re) {
						System.out.println("vote error");
						notify(player);
					}
				}
			}
			wordLength = 0;
		}
	}

	@Override
	public void notify(ClientInterface delete) {
		int currentPlayer = players.indexOf(delete);
		String deleteName = playerNames.get(currentPlayer);
		players.remove(delete);
		playerNames.remove(deleteName);

		if (gamers.contains(deleteName) && gameState) {
			for (int i = 0; i < gamers.size(); i++) {
				for (int j = i; j > 0; j--) {
					// Switch 2 continous players
					if (gamerScores.get(j) > gamerScores.get(j - 1)) {
						gamerScores.add(j + 1, gamerScores.get(j - 1));
						gamerScores.remove(j - 1);
						gamers.add(j + 1, gamers.get(j - 1));
						gamers.remove(j - 1);

					} else {
						break;
					}
				}
			}

			for (ClientInterface e : players) {
				ClientInterface player = e;
				try {
					player.gameOver(playerNames, gamers, gamerScores);// gameOver(ArrayList<String> players,
																		// ArrayList<String> gamers, ArrayList<Integer>
																		// scores)throws
				} catch (RemoteException re) {
					System.out.println("game over question");
					notify(player);
				}
			}
			this.roomState = false;
			this.gameState = false;
			gamers.clear();
			gamerScores.clear();
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					table[i][j] = ' ';
				}
			}
		} else {
			if (roomState) {
				if (deleteName.equals(creator)) {
					roomState = false;
					gamers.clear();
					gamerScores.clear();
					for (ClientInterface e : players) {
						ClientInterface player = e;
						try {
							player.initiateGame(gameState, roomState, creator, playerNames, gamers);
						} catch (RemoteException re) {
							System.out.println("removing player -");
							// Remove the listener
							notify(player);
						}
					}
				} else if (gamers.contains(deleteName)) {
					int currentGamer = gamers.indexOf(deleteName);
					gamers.remove(currentGamer);
					gamerScores.remove(currentGamer);
				}
			}
		}

		for (ClientInterface e : players) {
			ClientInterface player = e;
			try {
				player.clientExited(deleteName);
				player.freshGamer(gamers, playerNames, gamerScores, gameState, roomState);
			} catch (RemoteException re) {
				System.out.println("removing player -");
				// Remove the listener
				notify(player);
			}
		}
	}

}
