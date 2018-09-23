package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import remote.ServerInterface;

import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.Color;
import java.awt.Desktop.Action;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;

public class ClientGUI implements ActionListener {

	protected JFrame frame;
	protected JButton btnPass;
	protected JButton btnVote;
	protected JButton btnAgreeVote;
	protected JButton btnCreateRoom;
	protected JLabel lblPlayersTurn;
	protected JLabel lblCurrentGameState;
	protected JButton btnNext;
	protected JButton btnDisagree;
	protected JPanel btnPanel;
	protected JPanel votePanel;
	protected JPanel selectPenel;
	protected JPanel passPanel;
	protected MyDefaultTableModel myModel = new MyDefaultTableModel(20, 20);
	protected JTable gameTable = new JTable(myModel);
	protected JPanel voidPanel;
	protected JPanel listPanel;
	protected JScrollPane scrollPane_1;
	protected JPanel visitingPanel;
	protected GridLayout visitingGridLayout;
	protected GridLayout listGridLayout;
	protected CardLayout cardLayout;
	protected CardLayout btnLayout;
	protected JButton btnReturnToGame;
	protected JScrollPane scrollPane_2;
	protected JPanel panel;
	char character;
	int rowIndex = 0;
	int colIndex = 0;
	boolean tableChanged = false;
	private JTable gamerListTable;
	private JTable invitedTable;
	private JTable playerTable;
	private JTable visitingTable;
	private JPanel gameOverPanel;
	private JScrollPane scrollPane_3;
	private JTable gameResultTable;
	private JLabel lblCreatRoom;
	private JButton btnStartGame;
	String inputString = "";

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	}

	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.getFrame().setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 583, 593);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardLayout = new CardLayout(0, 0);
		frame.getContentPane().setLayout(cardLayout);

		JPanel gameLobbyPanel = new JPanel();
		frame.getContentPane().add(gameLobbyPanel, "name_891358432709494");
		gameLobbyPanel.setLayout(null);

		lblCurrentGameState = new JLabel("Welcome to Scrabble Game!");
		lblCurrentGameState.setFont(new Font("Century", Font.PLAIN, 15));
		lblCurrentGameState.setBounds(105, 156, 198, 33);
		gameLobbyPanel.add(lblCurrentGameState);

		btnCreateRoom = new JButton("Create Room");
		btnCreateRoom.setFont(new Font("Century", Font.PLAIN, 12));
		btnCreateRoom.addActionListener(this);
		btnCreateRoom.setBackground(UIManager.getColor("Button.light"));
		btnCreateRoom.setBounds(143, 247, 110, 23);
		gameLobbyPanel.add(btnCreateRoom);

		panel = new JPanel();
		panel.setBorder(UIManager.getBorder("ProgressBar.border"));
		panel.setVisible(false);
		panel.setBounds(0, 347, 383, 207);
		gameLobbyPanel.add(panel);
		panel.setLayout(null);

		invitedTable = new JTable();
		invitedTable.setShowGrid(false);
		invitedTable.setBackground(SystemColor.control);
		invitedTable.setRowSelectionAllowed(false);
		invitedTable.setEnabled(false);
		invitedTable.setBounds(10, 10, 361, 187);
		panel.add(invitedTable);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(382, 0, 185, 554);
		gameLobbyPanel.add(scrollPane);

		playerTable = new JTable();
		playerTable.setRowSelectionAllowed(false);
		scrollPane.setViewportView(playerTable);

		lblCreatRoom = new JLabel("");
		lblCreatRoom.setBounds(28, 237, 332, 33);
		gameLobbyPanel.add(lblCreatRoom);

		btnStartGame = new JButton("Start Game");
		btnStartGame.setFont(new Font("Century", Font.PLAIN, 12));
		btnStartGame.addActionListener(this);
		btnStartGame.setBounds(144, 247, 109, 23);
		gameLobbyPanel.add(btnStartGame);

		JPanel gameRoomPanel = new JPanel();
		frame.getContentPane().add(gameRoomPanel, "name_891421322563457");
		gameRoomPanel.setLayout(null);

		JPanel gamersPanel = new JPanel();
		gamersPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		gamersPanel.setBounds(0, 0, 383, 93);
		gameRoomPanel.add(gamersPanel);
		gamersPanel.setLayout(null);

		JLabel lblCurrentPlayers = new JLabel("Current Players:");
		lblCurrentPlayers.setBounds(10, 0, 114, 15);
		gamersPanel.add(lblCurrentPlayers);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(0, 25, 383, 70);
		scrollPane_2.setVisible(false);
		gamersPanel.add(scrollPane_2);

		gamerListTable = new JTable();
		scrollPane_2.setViewportView(gamerListTable);
		gamerListTable.setBackground(SystemColor.control);
		gamerListTable.setRowSelectionAllowed(false);
		gamerListTable.setEnabled(false);

		JPanel GameTablePanel = new JPanel();
		GameTablePanel.setBorder(UIManager.getBorder("FileChooser.listViewBorder"));
		GameTablePanel.setBounds(10, 128, 363, 337);
		gameRoomPanel.add(GameTablePanel);
		GameTablePanel.setLayout(null);

		gameTable.setRowSelectionAllowed(false);
		gameTable.setCellSelectionEnabled(true);
		gameTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		gameTable.setBorder(UIManager.getBorder("ComboBox.border"));

		gameTable.setBounds(10, 10, 345, 319);
		GameTablePanel.add(gameTable);
		gameTable.setModel(myModel);

		lblPlayersTurn = new JLabel();
		lblPlayersTurn.setBounds(10, 103, 254, 15);
		gameRoomPanel.add(lblPlayersTurn);

		btnPanel = new JPanel();
		btnPanel.setBounds(0, 469, 381, 85);
		gameRoomPanel.add(btnPanel);
		btnLayout = new CardLayout(0, 0);
		btnPanel.setLayout(btnLayout);

		voidPanel = new JPanel();
		btnPanel.add(voidPanel, "name_965239449619613");

		passPanel = new JPanel();
		btnPanel.add(passPanel, "name_958014637000495");
		passPanel.setLayout(null);

		btnPass = new JButton("Pass");
		btnPass.setBounds(140, 31, 104, 23);
		passPanel.add(btnPass);
		btnPass.addActionListener(this);

		votePanel = new JPanel();
		btnPanel.add(votePanel, "name_957940911752561");
		votePanel.setLayout(null);

		btnAgreeVote = new JButton("Agree");
		btnAgreeVote.setBounds(71, 30, 92, 23);
		votePanel.add(btnAgreeVote);

		btnDisagree = new JButton("Disagree");
		btnDisagree.addActionListener(this);
		btnDisagree.setBounds(221, 30, 92, 23);
		votePanel.add(btnDisagree);
		btnAgreeVote.addActionListener(this);

		selectPenel = new JPanel();
		btnPanel.add(selectPenel, "name_957948997083501");
		selectPenel.setLayout(null);

		btnNext = new JButton("Next");
		btnNext.setBounds(50, 35, 99, 23);
		selectPenel.add(btnNext);

		btnVote = new JButton("Vote");
		btnVote.setBounds(213, 35, 99, 23);
		selectPenel.add(btnVote);
		btnVote.addActionListener(this);
		btnNext.addActionListener(this);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(383, 0, 184, 554);
		gameRoomPanel.add(scrollPane_1);

		visitingTable = new JTable();
		visitingTable.setEnabled(false);
		visitingTable.setRowSelectionAllowed(false);
		scrollPane_1.setViewportView(visitingTable);

		gameTable.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (!tableChanged) {
					rowIndex = e.getFirstRow();
					colIndex = e.getColumn();
					inputString = gameTable.getValueAt(rowIndex, colIndex).toString();
					tableChanged = true;
					if (!Pattern.matches("\\s+", inputString) && !inputString.equals("")) {
						character = inputString.toCharArray()[0];
						btnLayout.show(btnPanel, "name_957948997083501");
					} else {
						JOptionPane.showMessageDialog(null, "You can only type one character without whitespace.");
					}
				} else {
					if (gameTable.getValueAt(rowIndex, colIndex).equals(inputString)) {
						gameTable.setValueAt("", rowIndex, colIndex);
						if (gameTable.getValueAt(rowIndex, colIndex).equals("")) {
							rowIndex = e.getFirstRow();
							colIndex = e.getColumn();
							inputString = gameTable.getValueAt(rowIndex, colIndex).toString();
							tableChanged = true;
						}
						if (!Pattern.matches("\\s+", inputString) && !inputString.equals("")) {
							character = inputString.toCharArray()[0];
							btnLayout.show(btnPanel, "name_957948997083501");
						} else {
							JOptionPane.showMessageDialog(null, "You can only type one character without whitespace.");
						}
					}

				}
			}
		});
		myModel = new MyDefaultTableModel(20, 20);

		gameOverPanel = new JPanel();
		frame.getContentPane().add(gameOverPanel, "name_1037193997593739");
		gameOverPanel.setLayout(null);

		btnReturnToGame = new JButton("Return To Game Lobby");
		btnReturnToGame.addActionListener(this);
		btnReturnToGame.setBounds(203, 471, 175, 23);
		gameOverPanel.add(btnReturnToGame);

		scrollPane_3 = new JScrollPane();
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setBounds(84, 67, 404, 383);
		gameOverPanel.add(scrollPane_3);

		gameResultTable = new JTable();
		gameResultTable.setRowSelectionAllowed(false);
		gameResultTable.setEnabled(false);
		scrollPane_3.setViewportView(gameResultTable);

		JLabel lblGameOver = new JLabel("Game Over!");
		lblGameOver.setFont(new Font("Century", Font.BOLD, 15));
		lblGameOver.setEnabled(false);
		lblGameOver.setBounds(241, 24, 99, 15);
		gameOverPanel.add(lblGameOver);

		JFormattedTextField ftf;
		try {
			MaskFormatter format = new MaskFormatter("L");
			ftf = new JFormattedTextField(format);
			DefaultCellEditor cellEditor = new DefaultCellEditor(ftf);
			gameTable.setCellEditor(cellEditor);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			if (arg0.getSource().equals(btnPass)) {
				ScrabbleClient.remoteServer.passTurn(ScrabbleClient.player.getUserName());
				passPanel.setVisible(false);
				voidPanel.setVisible(true);
			} else if (arg0.getSource().equals(btnVote)) {
				if (gameTable.getSelectedColumnCount() == 0 && gameTable.getSelectedRowCount() == 0) {
					JOptionPane.showMessageDialog(null,
							" You cannot begin vote without selection.Please click 'Next' button or select a word.");
				} else {
					myModel.setCellEditable(rowIndex, colIndex, false);
					int startRowIndex = 0;
					int startColIndex = 0;
					int endRowIndex = 0;
					int endColIndex = 0;
					if (gameTable.getSelectedColumnCount() > 1 & gameTable.getSelectedRowCount() > 1) {
						JOptionPane.showMessageDialog(null,
								" You have to select a string in a single line.Please select again.");
						gameTable.changeSelection(-1, -1, false, false);
						myModel.setCellEditable(rowIndex, colIndex, true);
					} else {
						if (gameTable.getSelectedColumnCount() > 1 && gameTable.getSelectedRowCount() == 1) {
							startRowIndex = gameTable.getSelectedRow();
							endRowIndex = startRowIndex;
							int[] cols = gameTable.getSelectedColumns();
							startColIndex = cols[0];
							endColIndex = cols[cols.length - 1];
						} else if (gameTable.getSelectedColumnCount() == 1 && gameTable.getSelectedRowCount() > 1) {
							startColIndex = gameTable.getSelectedColumn();
							endColIndex = startColIndex;
							int[] cols = gameTable.getSelectedRows();
							startRowIndex = cols[0];
							endRowIndex = cols[cols.length - 1];
						} else if (gameTable.getSelectedColumnCount() == 1 && gameTable.getSelectedRowCount() == 1) {
							startColIndex = gameTable.getSelectedColumn();
							endColIndex = startColIndex;
							startRowIndex = gameTable.getSelectedRow();
							endRowIndex = startRowIndex;
						}
						if (!checkString(startRowIndex, startColIndex, endRowIndex, endColIndex)) {
							JOptionPane.showMessageDialog(null,
									" You have to select a complete string without empty cells.Please select again.");
							gameTable.changeSelection(-1, -1, false, false);
							myModel.setCellEditable(rowIndex, colIndex, true);
						} else {
							myModel.setCellEditable(rowIndex, colIndex, false);
							ScrabbleClient.remoteServer.vote(character, startRowIndex, startColIndex, endRowIndex,
									endColIndex, ScrabbleClient.player.getUserName(), rowIndex, colIndex);
						}
					}
				}
			} else if (arg0.getSource().equals(btnAgreeVote)) {
				ScrabbleClient.remoteServer.agreeVote(false, ScrabbleClient.player.getUserName());
				btnLayout.show(btnPanel, "name_965239449619613");

			} else if (arg0.getSource().equals(btnNext)) {
				myModel.setCellEditable(rowIndex, colIndex, false);
				btnLayout.show(btnPanel, "name_965239449619613");
				ScrabbleClient.remoteServer.nextTurn(character, rowIndex, colIndex,
						ScrabbleClient.player.getUserName());

			} else if (arg0.getSource().equals(btnDisagree)) {
				ScrabbleClient.remoteServer.agreeVote(false, ScrabbleClient.player.getUserName());
				btnLayout.show(btnPanel, "name_965239449619613");

			} else if (arg0.getSource().equals(btnCreateRoom)) {
				if (ScrabbleClient.remoteServer.createRoom(ScrabbleClient.player.getUserName())) {
					showLobby();
				}
			} else if (arg0.getSource().equals(btnReturnToGame)) {
				showLobby();
			} else if (arg0.getSource().equals(btnStartGame)) {
				showGame();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public JFrame getFrame() {
		return frame;
	}

	public boolean checkString(int startRowIndex, int startColIndex, int endRowIndex, int endColIndex) {
		if (rowIndex < startRowIndex || rowIndex > endRowIndex || colIndex < startColIndex || colIndex > endColIndex) {
			return false;
		}
		if (startRowIndex == endRowIndex) {
			if ((startColIndex > 0 && gameTable.getValueAt(startRowIndex, startColIndex - 1) != null
					&& gameTable.getValueAt(startRowIndex, startColIndex - 1) != "")
					|| (endColIndex > 19 && gameTable.getValueAt(startRowIndex, endColIndex + 1) != null
					&& gameTable.getValueAt(startRowIndex, endColIndex + 1) != "")) {
				return false;
			}
		} else if (startColIndex == endColIndex) {
			if ((startRowIndex > 0 && gameTable.getValueAt(startRowIndex - 1, startColIndex) != null 
					&& gameTable.getValueAt(startRowIndex-1, startColIndex) != "")
					|| (endRowIndex < 19 && gameTable.getValueAt(endRowIndex + 1, startColIndex) != null
					&& gameTable.getValueAt(endRowIndex + 1, startColIndex) != "")) {
				return false;
			}
		} else {
			return false;
		}
		for (int i = startRowIndex; i <= endRowIndex; i++) {
			for (int j = startColIndex; j <= endColIndex; j++) {
				if (null == gameTable.getValueAt(i, j)) {
					return false;
				}
			}
		}
		return true;
	}

	public void freshPlayerList() {
		/*
		 * visitingPanel = new JPanel(); panel_2.add(visitingPanel); visitingGridLayout
		 * = new GridLayout(0, 1, 0, 0);
		 * 
		 * listPanel = new JPanel(); panel_1.add(listPanel); listGridLayout = new
		 * GridLayout(0, 1, 0, 0);
		 */
		DefaultTableModel model;
		ArrayList<String> players = ScrabbleClient.player.getPlayers();
		Vector<String> vPlayers = new Vector<String>();
		Vector<JButton> inviteButton = new Vector<JButton>();
		Vector information = new Vector();
		Vector<String> vCol = new Vector<String>();
		vCol.add("Online Players");
		information.add(vPlayers);

		for (int i = 0; i < players.size(); i++) {
			String playersName = players.get(i);
			vPlayers.add(playersName);
			JButton addButton = new JButton("+");
			addButton.setBounds(118, 10, 39, 23);
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						ScrabbleClient.remoteServer.invitePlayer(playersName);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			});
			inviteButton.add(addButton);
		}

		if (ScrabbleClient.player.getRoomState()
				&& !ScrabbleClient.player.getRoomCreatorName().equals(ScrabbleClient.player.getUserName())) {
			vCol.add("Invite Player");
			information.add(inviteButton);
			model = new DefaultTableModel(information, vCol);
		} else {
			model = new DefaultTableModel(information, vCol);
			visitingTable.setModel(model);
		}
		playerTable.setModel(model);
		/*
		 * visitingPanel.setBounds(0, 0, 182, 40 * (i + 1));
		 * visitingGridLayout.setColumns(i + 1);
		 * visitingPanel.setLayout(visitingGridLayout);
		 * 
		 * JLabel visitingLabel = new JLabel(players.get(i)); visitingLabel.setFont(new
		 * Font("Century", Font.PLAIN, 12)); visitingLabel.setBounds(10, 10, 162, 21);
		 * visitingPanel.add(visitingLabel);
		 * 
		 * listPanel.setBounds(0, 0, 182, 40 * (i + 1)); listGridLayout.setColumns(i +
		 * 1); listPanel.setLayout(listGridLayout);
		 * 
		 * JPanel invitePanel = new JPanel(); listPanel.add(invitePanel);
		 * invitePanel.setLayout(null);
		 * 
		 * String playersName=players.get(i); if (ScrabbleClient.player.getRoomState())
		 * { if
		 * (!ScrabbleClient.player.getRoomCreatorName().equals(ScrabbleClient.player.
		 * getUserName())) {
		 * 
		 * invitePanel.add(addButton);
		 * 
		 * addButton.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent arg0) { try {
		 * ScrabbleClient.remoteServer.invitePlayer(playersName); } catch
		 * (RemoteException e) { e.printStackTrace(); } } }); } } JLabel playerLabel =
		 * new JLabel(playersName); playerLabel.setBounds(10, 14, 98, 15);
		 * invitePanel.add(playerLabel); listPanel.add(invitePanel);
		 */
	}

	public void freshGamerList() {
		DefaultTableModel model;
		ArrayList<String> gamers = ScrabbleClient.player.getGamers();
		ArrayList<Integer> scores = ScrabbleClient.player.getGamerScores();
		if (gamers.size() == 0) {
			scrollPane_2.setVisible(false);
			panel.setVisible(false);
		} else {
			scrollPane_2.setVisible(true);
			panel.setVisible(true);
			Vector<String> vGamers = new Vector<String>();
			Vector<Integer> vScores = new Vector<Integer>();
			Vector information = new Vector();
			Vector<String> vCol = new Vector<String>();
			vCol.add("Gamers");
			information.add(vGamers);

			for (int i = 0; i < gamers.size(); i++) {
				vGamers.add(gamers.get(i));
				vScores.add(scores.get(i));
			}

			model = new DefaultTableModel(information, vCol);
			invitedTable.setModel(model);

			vCol.add("Score");
			information.add(vScores);
			model = new DefaultTableModel(information, vCol);
			gamerListTable.setModel(model);
		}

	}

	public void freshTable() {
		try {
			char[][] grid = ScrabbleClient.player.getGrid();
			for (int i = 0; i < gameTable.getHeight(); i++) {
				for (int j = 0; j < gameTable.getWidth(); j++) {
					if (grid[i][j] != ' ') {
						gameTable.setValueAt(grid[i][j], i, j);
						myModel.setCellEditable(i, j, false);						
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error: ClientGUI -> freshTable");
		}
	}

	public void showLobby() {
		if (ScrabbleClient.player.getRoomState()) {
			if (!ScrabbleClient.player.getRoomCreatorName().equals(ScrabbleClient.player.getUserName())) {
				btnCreateRoom.setVisible(false);
				btnStartGame.setVisible(false);
				lblCreatRoom.setText("Room has been already created.Please wait for invitation or game starting.");
			} else {
				lblCreatRoom.setText("Create room successfully! Invite other players into a game");
				btnCreateRoom.setVisible(false);
				btnStartGame.setVisible(true);
			}
		} else {
			btnCreateRoom.setVisible(true);
			btnStartGame.setVisible(false);
			lblCreatRoom.setText(null);
		}
		freshPlayerList();
		freshGamerList();
		cardLayout.show(frame.getContentPane(), "name_891358432709494");
	}

	public void showGame() {
		lblPlayersTurn.setText(ScrabbleClient.player.getCurrentPlayer() + "'s turn.");
		freshTable();
		freshPlayerList();
		freshGamerList();
		cardLayout.show(frame.getContentPane(), "name_891421322563457");
		if (ScrabbleClient.player.getCurrentPlayer().equals(ScrabbleClient.player.getUserName()))
			btnLayout.show(btnPanel, "name_958014637000495");
		else
			btnLayout.show(btnPanel, "name_965239449619613");
	}

	public void showGameResult() {
		DefaultTableModel model;
		ArrayList<String> gamers = ScrabbleClient.player.getGamers();
		ArrayList<Integer> scores = ScrabbleClient.player.getGamerScores();

		Vector<String> vGamers = new Vector<String>();
		Vector<Integer> vScores = new Vector<Integer>();
		Vector information = new Vector();
		Vector<String> vCol = new Vector<String>();
		vCol.add("Gamers");
		information.add(vGamers);

		for (int i = 0; i < gamers.size(); i++) {
			vGamers.add(gamers.get(i));
			vScores.add(scores.get(i));
		}

		model = new DefaultTableModel(information, vCol);
		gameResultTable.setModel(model);
		cardLayout.show(frame.getContentPane(), "name_1037193997593739");
	}

	public void showInvitation() {
		int input = JOptionPane.showConfirmDialog(null,
				"Do you want to accept invitation from " + ScrabbleClient.player.getRoomCreatorName() + "?",
				"Select an Option...", JOptionPane.YES_NO_OPTION);
		try {
			if (input == 0)
				ScrabbleClient.remoteServer.respondToInvitation(true, ScrabbleClient.player.getUserName());
			else
				ScrabbleClient.remoteServer.respondToInvitation(false, ScrabbleClient.player.getUserName());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void beginVote(char character, int startRowIndex, int startColIndex, int endRowIndex, int endColIndex,
			String userName) {
		if (!ScrabbleClient.player.getUserName().equals(userName)) {
			btnLayout.show(btnPanel, "name_957940911752561");
		}
	}

	public void voteResult(String beginVoteUserName, boolean accepted, int totalMark, String nextUserName) {
		TimeDialog d = new TimeDialog();
		if (accepted) {
			if (ScrabbleClient.player.getUserName().equals(beginVoteUserName))
				d.showDialog(null, "Vote successfully!You get " + totalMark + "points!", 3);
			else
				d.showDialog(null, "Vote successfully!" + beginVoteUserName + " get " + totalMark + "points!", 3);
		} else
			d.showDialog(null, "Vote Failed! " + nextUserName + "'s turn!", 3);
	}
}
