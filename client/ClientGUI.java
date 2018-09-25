package client;

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
import javax.swing.text.MaskFormatter;

import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.Color;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
	JScrollPane scrollPane_4;

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

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { ClientGUI window = new ClientGUI();
	 * window.getFrame().setVisible(true);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } }); }
	 */

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					ScrabbleClient.remoteServer.notify(ScrabbleClient.player);
					System.exit(0);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		frame.setBounds(100, 100, 583, 593);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardLayout = new CardLayout(0, 0);
		frame.getContentPane().setLayout(cardLayout);

		JPanel gameLobbyPanel = new JPanel();
		frame.getContentPane().add(gameLobbyPanel, "name_891358432709494");
		gameLobbyPanel.setLayout(null);

		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(0, 354, 384, 200);
		gameLobbyPanel.add(scrollPane_4);

		invitedTable = new JTable();
		scrollPane_4.setViewportView(invitedTable);
		invitedTable.setShowGrid(false);
		invitedTable.setBackground(SystemColor.control);
		invitedTable.setRowSelectionAllowed(false);
		invitedTable.setEnabled(false);

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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(382, 0, 185, 554);
		gameLobbyPanel.add(scrollPane);

		playerTable = new JTable();
		playerTable.setEnabled(false);
		playerTable.setRowSelectionAllowed(false);
		scrollPane.setViewportView(playerTable);

		lblCreatRoom = new JLabel("");
		lblCreatRoom.setBounds(29, 212, 332, 33);
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
					if (!Pattern.matches("\\s+", inputString) && !inputString.equals("")) {
						tableChanged = true;
						character = inputString.toCharArray()[0];
						btnLayout.show(btnPanel, "name_957948997083501");
					} else {
						JOptionPane.showMessageDialog(null, "You can only type one character without whitespace.");
						btnLayout.show(btnPanel, "name_958014637000495");
					}
				} else {
					if (gameTable.getValueAt(rowIndex, colIndex).equals(inputString)) {
						gameTable.setValueAt("", rowIndex, colIndex);
						if (gameTable.getValueAt(rowIndex, colIndex).equals("")) {
							rowIndex = e.getFirstRow();
							colIndex = e.getColumn();
							inputString = gameTable.getValueAt(rowIndex, colIndex).toString();
						}
						if (!Pattern.matches("\\s+", inputString) && !inputString.equals("")) {
							tableChanged = true;
							character = inputString.toCharArray()[0];
							btnLayout.show(btnPanel, "name_957948997083501");
						} else {
							JOptionPane.showMessageDialog(null, "You can only type one character without whitespace.");
							btnLayout.show(btnPanel, "name_958014637000495");
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
				setTableEditable(false);
				ScrabbleClient.remoteServer.passTurn(ScrabbleClient.player.getUserName());
				btnLayout.show(btnPanel, "name_965239449619613");
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
							setTableEditable(false);
							btnLayout.show(btnPanel, "name_965239449619613");
						}
					}
				}
			} else if (arg0.getSource().equals(btnAgreeVote)) {
				ScrabbleClient.remoteServer.agreeVote(true, ScrabbleClient.player.getUserName());
				btnLayout.show(btnPanel, "name_965239449619613");

			} else if (arg0.getSource().equals(btnNext)) {
				setTableEditable(false);
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
				ScrabbleClient.player.ClearGamerUserName();
				ScrabbleClient.player.ClearGamerScores();
				ScrabbleClient.player.setRoomState(false);
				ScrabbleClient.player.setGameState(false);
				ScrabbleClient.player.setCurrentPlayer("");
				showLobby();
			} else if (arg0.getSource().equals(btnStartGame)) {
				ScrabbleClient.remoteServer.startGame();
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
					&& gameTable.getValueAt(startRowIndex - 1, startColIndex) != "")
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
		ArrayList<String> players = ScrabbleClient.player.getPlayers();
		ArrayList<String> gamers = ScrabbleClient.player.getGamers();

		Vector<Object> visiting = new Vector<Object>();
		Vector<Object> invite = new Vector<Object>();
		Vector<String> vCol = new Vector<String>();
		vCol.add("Players");
		for (int i = 0; i < players.size(); i++) {
			String playersName = players.get(i);
			Vector<Object> visitRow = new Vector<Object>();
			visitRow.add(playersName);
			visiting.add(visitRow);
		}
		visitingTable.setModel(new MyDefaultTableModel(visiting, vCol));

		ArrayList<String> noGamers = new ArrayList<String>();
		for (int j = 0; j < players.size(); j++)
			if (!gamers.contains(players.get(j)))
				noGamers.add(players.get(j));

		ButtonRenderer renderer = new ButtonRenderer();
		//renderer.getTableCellRendererComponent(gameTable, value, isSelected, hasFocus, i, 1);
		for (int i = 0; i < noGamers.size(); i++) {
			Vector<Object> inviteRow = new Vector<Object>();
			String noGamerName = noGamers.get(i);
			inviteRow.add(noGamerName);
			JButton addButton = new JButton(noGamerName);
			addButton.setText("+");
			addButton.setBounds(118, 10, 39, 23);
			
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						ScrabbleClient.remoteServer.invitePlayer(noGamerName);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			});
			
			inviteRow.add(addButton);
			invite.add(inviteRow);
		}

		if (ScrabbleClient.player.getRoomState()
				&& ScrabbleClient.player.getRoomCreatorName().equals(ScrabbleClient.player.getUserName())) {
			vCol.add("Invite");
			
			//MyButtonEditor editor = new MyButtonEditor(e);			
			//gameTable.getColumnModel().getColumn(1).setCellEditor(editor);
			
			playerTable.setModel(new MyDefaultTableModel(invite, vCol));
		} else {
			playerTable.setModel(new MyDefaultTableModel(visiting, vCol));
		}

		if (vCol.contains("Invite")) {
			playerTable.getColumn("Invite").setCellRenderer(renderer);
			playerTable.addMouseListener(new ButtonMouseListener(playerTable));
		}
	}
	
	/*MyEvent e = new MyEvent() {
		@Override
		public void invoke(ActionEvent e) {
			MyButton button = (MyButton) e.getSource();
			button.getRow();
		}
	};*/

	public void freshGamerList() {
		ArrayList<String> gamers = ScrabbleClient.player.getGamers();
		ArrayList<Integer> scores = ScrabbleClient.player.getGamerScores();
		if (gamers.size() == 0) {
			scrollPane_2.setVisible(false);
			scrollPane_4.setVisible(false);
		} else {
			scrollPane_2.setVisible(true);
			scrollPane_4.setVisible(true);
			Vector<Object> game = new Vector<Object>();
			Vector<Object> invite = new Vector<Object>();
			Vector<String> vCol = new Vector<String>();
			vCol.add("Gamers");

			for (int i = 0; i < gamers.size(); i++) {
				Vector<Object> gameRow = new Vector<Object>();
				Vector<Object> inviteRow = new Vector<Object>();
				gameRow.add(gamers.get(i));
				gameRow.add(scores.get(i));
				inviteRow.add(gamers.get(i));

				game.add(gameRow);
				invite.add(inviteRow);
			}

			invitedTable.setModel(new DefaultTableModel(invite, vCol));

			vCol.add("Score");
			gamerListTable.setModel(new DefaultTableModel(game, vCol));
		}

	}

	public void freshTable() {
		try {
			char[][] grid = ScrabbleClient.player.getGrid();
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] != ' ') {
						gameTable.setValueAt(grid[i][j], i, j);
						myModel.setCellEditable(i, j, false);
					} else {
						myModel.setCellEditable(i, j, true);
					}
				}
			}
			tableChanged=false;
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
				if (ScrabbleClient.player.getGamers().size() > 1)
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
		ArrayList<String> gamers = ScrabbleClient.player.getGamers();
		ArrayList<Integer> scores = ScrabbleClient.player.getGamerScores();

		Vector<Object> information = new Vector<Object>();
		Vector<String> vCol = new Vector<String>();
		vCol.add("Gamers Ranking");
		vCol.add("Score");

		for (int i = 0; i < gamers.size(); i++) {
			Vector<Object> row = new Vector<Object>();
			row.add(gamers.get(i));
			row.add(scores.get(i));
			information.add(row);
		}

		gameResultTable.setModel(new MyDefaultTableModel(information, vCol));
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
			freshTable();
			gameTable.setColumnSelectionInterval(startColIndex, endColIndex);
			gameTable.setRowSelectionInterval(startRowIndex, endRowIndex);
			btnLayout.show(btnPanel, "name_957940911752561");
		}
	}

	public void voteResult(String beginVoteUserName, boolean accepted, int totalMark, String nextUserName) {
		TimeDialog d = new TimeDialog();
		if (accepted) {
			if (ScrabbleClient.player.getUserName().equals(beginVoteUserName))
				d.showDialog(null, "Vote successfully!You get " + totalMark + "points!", 2);
			else
				d.showDialog(null, "Vote successfully!" + beginVoteUserName + " gets " + totalMark + "points!", 2);
		} else
			d.showDialog(null, "Vote Failed! " + nextUserName + "'s turn!", 3);
	}

	private void setTableEditable(boolean editable) {
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 20; j++)
				myModel.setCellEditable(i, j, editable);
	}
}
