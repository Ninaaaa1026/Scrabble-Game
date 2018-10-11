/***
 * author: Chenjing Yu, Jinnan Li, Mochuan Wang, Mengwen Ma
 * email:
 * chenjingy@student.unimelb.edu.au
 * mochuanw@student.unimelb.edu.au
 * mengwenm@student.unimelb.edu.au
 * jinnanl@student.unimelb.edu.au
 */
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
import java.awt.event.MouseListener;
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
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingConstants;

/***
 * This class defines the game GUI for users
 * and methods for interaction with users and the server.
 * It also provides methods for client callback methods
 * to refresh the GUI.
 */
public class ClientGUI implements ActionListener {

	protected JFrame frame;
	protected JLabel lblMessage;
	protected JLabel lblInvitemessage;
	protected JPanel GameTablePanel;
	
	protected boolean returnLobby = true;
	
	private CardLayout cardLayout;
	private CardLayout btnLayout;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JScrollPane scrollPane_4;
	private JPanel btnPanel;
	private JPanel gameOverPanel;
	private JPanel votePanel;
	private JPanel selectPenel;
	private JPanel passPanel;
	private JPanel voidPanel;
	private JButton btnReturnToGame;
	private JButton btnStartGame;
	private JButton btnPass;
	private JButton btnVote;
	private JButton btnAgreeVote;
	private JButton btnCreateRoom;	
	private JButton btnNext;
	private JButton btnDisagree;
	private JTable gamerListTable;
	private JTable invitedTable;
	private JTable playerTable;
	private JTable visitingTable;
	private JTable gameResultTable;
	private JLabel lblCreatRoom;
	private JLabel lblPlayersTurn;
	private JLabel lblCurrentGameState;
	private MyDefaultTableModel myModel = new MyDefaultTableModel(20, 20);
	protected JTable gameTable = new JTable(myModel);
	
	private char character; //character that the player input
	private int rowIndex = 0;
	private int colIndex = 0; //indexes of the input character's position
	private boolean tableChanged = false;
	private boolean newTable = true;
	private boolean beginSetVoid = false;	
	private String inputString = "";
	
	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					ScrabbleClient.remoteServer.notify(ScrabbleClient.player);
					System.exit(0);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			}
		});
		frame.setBounds(100, 100, 571, 581);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardLayout = new CardLayout(0, 0);
		frame.getContentPane().setLayout(cardLayout);

		JPanel gameLobbyPanel = new JPanel();
		frame.getContentPane().add(gameLobbyPanel, "name_891358432709494");
		gameLobbyPanel.setLayout(null);

		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(0, 421, 384, 133);
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
		playerTable.setShowGrid(false);
		playerTable.setBackground(SystemColor.control);
		playerTable.setEnabled(false);
		playerTable.setRowSelectionAllowed(false);
		scrollPane.setViewportView(playerTable);

		lblCreatRoom = new JLabel("");
		lblCreatRoom.setFont(new Font("Century", Font.PLAIN, 12));
		lblCreatRoom.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreatRoom.setBounds(0, 214, 384, 23);
		gameLobbyPanel.add(lblCreatRoom);

		btnStartGame = new JButton("Start Game");
		btnStartGame.setFont(new Font("Century", Font.PLAIN, 12));
		btnStartGame.addActionListener(this);
		btnStartGame.setBounds(144, 247, 109, 23);
		gameLobbyPanel.add(btnStartGame);

		lblInvitemessage = new JLabel("");
		lblInvitemessage.setFont(new Font("Century", Font.PLAIN, 12));
		lblInvitemessage.setBounds(10, 396, 351, 23);
		gameLobbyPanel.add(lblInvitemessage);

		JPanel gameRoomPanel = new JPanel();
		frame.getContentPane().add(gameRoomPanel, "name_891421322563457");
		gameRoomPanel.setLayout(null);

		JPanel gamersPanel = new JPanel();
		gamersPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		gamersPanel.setBounds(0, 0, 383, 97);
		gameRoomPanel.add(gamersPanel);
		gamersPanel.setLayout(null);

		JLabel lblCurrentPlayers = new JLabel("Current Players:");
		lblCurrentPlayers.setBounds(0, 10, 114, 15);
		gamersPanel.add(lblCurrentPlayers);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(0, 25, 383, 72);
		scrollPane_2.setVisible(false);
		gamersPanel.add(scrollPane_2);

		gamerListTable = new JTable();
		gamerListTable.setBorder(new LineBorder(SystemColor.controlHighlight));
		scrollPane_2.setViewportView(gamerListTable);
		gamerListTable.setBackground(SystemColor.window);
		gamerListTable.setRowSelectionAllowed(false);
		gamerListTable.setEnabled(false);

		GameTablePanel = new JPanel();
		GameTablePanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		GameTablePanel.setBounds(10, 128, 363, 337);
		gameRoomPanel.add(GameTablePanel);
		GameTablePanel.setLayout(null);
		gameTable.setEnabled(false);

		gameTable.setRowSelectionAllowed(false);
		gameTable.setCellSelectionEnabled(true);
		gameTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		gameTable.setBorder(new LineBorder(Color.LIGHT_GRAY));

		gameTable.setBounds(10, 10, 345, 319);
		GameTablePanel.add(gameTable);

		lblPlayersTurn = new JLabel();
		lblPlayersTurn.setFont(new Font("Century", Font.PLAIN, 12));
		lblPlayersTurn.setBounds(10, 96, 254, 22);
		gameRoomPanel.add(lblPlayersTurn);

		btnPanel = new JPanel();
		btnPanel.setBounds(0, 469, 381, 54);
		gameRoomPanel.add(btnPanel);
		btnLayout = new CardLayout(0, 0);
		btnPanel.setLayout(btnLayout);

		voidPanel = new JPanel();
		btnPanel.add(voidPanel, "voidPanel");

		passPanel = new JPanel();
		btnPanel.add(passPanel, "passPanel");
		passPanel.setLayout(null);

		btnPass = new JButton("Pass");
		btnPass.setBounds(140, 21, 104, 23);
		passPanel.add(btnPass);
		btnPass.addActionListener(this);

		votePanel = new JPanel();
		btnPanel.add(votePanel, "votePanel");
		votePanel.setLayout(null);

		btnAgreeVote = new JButton("Agree");
		btnAgreeVote.setBounds(71, 21, 92, 23);
		votePanel.add(btnAgreeVote);

		btnDisagree = new JButton("Disagree");
		btnDisagree.addActionListener(this);
		btnDisagree.setBounds(221, 21, 92, 23);
		votePanel.add(btnDisagree);
		btnAgreeVote.addActionListener(this);

		selectPenel = new JPanel();
		btnPanel.add(selectPenel, "selectPenel");
		selectPenel.setLayout(null);

		btnNext = new JButton("Next");
		btnNext.setBounds(63, 21, 99, 23);
		selectPenel.add(btnNext);

		btnVote = new JButton("Vote");
		btnVote.setBounds(226, 21, 99, 23);
		selectPenel.add(btnVote);
		btnVote.addActionListener(this);
		btnNext.addActionListener(this);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(383, 0, 184, 554);
		gameRoomPanel.add(scrollPane_1);

		visitingTable = new JTable();
		visitingTable.setShowGrid(false);
		visitingTable.setBorder(null);
		visitingTable.setBackground(SystemColor.control);
		visitingTable.setEnabled(false);
		visitingTable.setRowSelectionAllowed(false);
		scrollPane_1.setViewportView(visitingTable);

		//gameTable.setModel(myModel);
		myModel.addTableModelListener(new TableModelListener() {
			/***
			 * Listen to user's action on displayed grid
			 * and validate the input
			 * @param e
			 */
			public void tableChanged(TableModelEvent e) {
				if (!newTable) {
					if (tableChanged)
						if (rowIndex == e.getFirstRow() && colIndex == e.getColumn() && !beginSetVoid)
							tableChanged = false;
						else
							tableChanged = true;
					if (!tableChanged) {
						rowIndex = e.getFirstRow();
						colIndex = e.getColumn();
						inputString = myModel.getValueAt(rowIndex, colIndex).toString();
						if (!Pattern.matches("\\s+", inputString) && !inputString.equals("")) {
							tableChanged = true;
							character = inputString.toCharArray()[0];
							btnLayout.show(btnPanel, "selectPenel");
							lblMessage.setText("");
						} else {
							JOptionPane.showMessageDialog(null, "You can only type one character without whitespace.");
							tableChanged = false;
							btnLayout.show(btnPanel, "passPanel");
						}
					} else {
						if (myModel.getValueAt(rowIndex, colIndex).equals(inputString)) {
							beginSetVoid = true;
							myModel.setValueAt("", rowIndex, colIndex);
							beginSetVoid = false;
							if (myModel.getValueAt(rowIndex, colIndex).equals("")) {
								rowIndex = e.getFirstRow();
								colIndex = e.getColumn();
								inputString = myModel.getValueAt(rowIndex, colIndex).toString();
							}
							if (!Pattern.matches("\\s+", inputString) && !inputString.equals("")) {
								tableChanged = true;
								character = inputString.toCharArray()[0];
								btnLayout.show(btnPanel, "selectPenel");
								lblMessage.setText("");
							} else {
								JOptionPane.showMessageDialog(null,
										"You can only type one character without whitespace.");
								tableChanged = false;
								btnLayout.show(btnPanel, "passPanel");
							}
						}
					}
				}
			}
		});
		//myModel = new MyDefaultTableModel(20, 20);

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

		lblMessage = new JLabel("");
		lblMessage.setFont(new Font("Century", Font.PLAIN, 12));
		lblMessage.setBounds(1, 525, 382, 23);
		gameRoomPanel.add(lblMessage);

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

	/***
	 * Listen to user's action on the game window
	 * such as next, pass, vote, and start game buttons.
	 * @param arg0
	 */
	public void actionPerformed(ActionEvent arg0) {
		try {
			if (arg0.getSource().equals(btnPass)) {
				setTableEditable(false);
				ScrabbleClient.remoteServer.passTurn(ScrabbleClient.player.getUserName());
				btnLayout.show(btnPanel, "voidPanel");
				gameTable.clearSelection();
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
							myModel.setCellEditable(rowIndex, colIndex, true);
						} else {
							myModel.setCellEditable(rowIndex, colIndex, false);
							ScrabbleClient.remoteServer.vote(character, startRowIndex, startColIndex, endRowIndex,
									endColIndex, ScrabbleClient.player.getUserName(), rowIndex, colIndex);
							gameTable.setEnabled(false);
							btnLayout.show(btnPanel, "voidPanel");
						}
					}
				}
			} else if (arg0.getSource().equals(btnAgreeVote)) {
				btnLayout.show(btnPanel, "voidPanel");
				gameTable.clearSelection();
				ScrabbleClient.remoteServer.agreeVote(true, ScrabbleClient.player.getUserName());
			} else if (arg0.getSource().equals(btnNext)) {
				setTableEditable(false);
				btnLayout.show(btnPanel, "voidPanel");
				ScrabbleClient.remoteServer.nextTurn(character, rowIndex, colIndex,
						ScrabbleClient.player.getUserName());

			} else if (arg0.getSource().equals(btnDisagree)) {
				btnLayout.show(btnPanel, "voidPanel");
				gameTable.clearSelection();
				ScrabbleClient.remoteServer.agreeVote(false, ScrabbleClient.player.getUserName());
			} else if (arg0.getSource().equals(btnCreateRoom)) {
				ScrabbleClient.remoteServer.createRoom(ScrabbleClient.player.getUserName());
			} else if (arg0.getSource().equals(btnReturnToGame)) {
				returnLobby = true;
				if (ScrabbleClient.player.getGameState())
					showGame(ScrabbleClient.player.getCurrentPlayer());
				else
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

	/**
	 * Validate the seleted word for vote
	 */
	public boolean checkString(int startRowIndex, int startColIndex, int endRowIndex, int endColIndex) {
		if (rowIndex < startRowIndex || rowIndex > endRowIndex || colIndex < startColIndex || colIndex > endColIndex) {
			JOptionPane.showMessageDialog(null,
					"You have to select a word including the character you put. Please select again.");
			return false;
		}
		if (startRowIndex == endRowIndex && startColIndex == endColIndex) {
			if (startRowIndex == 0) {
				if (startColIndex == 0) {
					if ((myModel.getValueAt(startRowIndex, startColIndex + 1) != null
							&& !myModel.getValueAt(startRowIndex, startColIndex + 1).equals(""))
							&& (myModel.getValueAt(startRowIndex + 1, startColIndex) != null
									&& !myModel.getValueAt(startRowIndex + 1, startColIndex).equals(""))) {
						JOptionPane.showMessageDialog(null, "You have to select a whole word . Please select again.");
						return false;
					}
				} else if (startColIndex == 19) {
					if ((myModel.getValueAt(startRowIndex, startColIndex - 1) != null
							&& !myModel.getValueAt(startRowIndex, startColIndex - 1).equals(""))
							&& (myModel.getValueAt(startRowIndex + 1, startColIndex) != null
									&& !myModel.getValueAt(startRowIndex + 1, startColIndex).equals(""))) {
						JOptionPane.showMessageDialog(null, "You have to select a whole word . Please select again.");
						return false;
					}
				} else {
					if (((myModel.getValueAt(startRowIndex, startColIndex - 1) != null
							&& !myModel.getValueAt(startRowIndex, startColIndex - 1).equals(""))
							|| (myModel.getValueAt(startRowIndex, startColIndex + 1) != null
									&& !myModel.getValueAt(startRowIndex, startColIndex + 1).equals("")))
							&& (myModel.getValueAt(startRowIndex + 1, startColIndex) != null
									&& !myModel.getValueAt(startRowIndex + 1, startColIndex).equals(""))) {
						JOptionPane.showMessageDialog(null, "You have to select a whole word . Please select again.");
						return false;
					}
				}

			} else if (startRowIndex == 19) {
				if (startColIndex == 0) {
					if ((myModel.getValueAt(startRowIndex, startColIndex + 1) != null
							&& !myModel.getValueAt(startRowIndex, startColIndex + 1).equals(""))
							&& (myModel.getValueAt(startRowIndex - 1, startColIndex) != null
									&& !myModel.getValueAt(startRowIndex - 1, startColIndex).equals(""))) {
						JOptionPane.showMessageDialog(null, "You have to select a whole word . Please select again.");
						return false;
					}
				} else if (startColIndex == 19) {
					if ((myModel.getValueAt(startRowIndex, startColIndex - 1) != null
							&& !myModel.getValueAt(startRowIndex, startColIndex - 1).equals(""))
							&& (myModel.getValueAt(startRowIndex - 1, startColIndex) != null
									&& !myModel.getValueAt(startRowIndex - 1, startColIndex).equals(""))) {
						JOptionPane.showMessageDialog(null, "You have to select a whole word . Please select again.");
						return false;
					}
				} else {
					if (((myModel.getValueAt(startRowIndex, startColIndex - 1) != null
							&& !myModel.getValueAt(startRowIndex, startColIndex - 1).equals(""))
							|| (myModel.getValueAt(startRowIndex, startColIndex + 1) != null
									&& !myModel.getValueAt(startRowIndex, startColIndex + 1).equals("")))
							&& (myModel.getValueAt(startRowIndex - 1, startColIndex) != null
									&& !myModel.getValueAt(startRowIndex - 1, startColIndex).equals(""))) {
						JOptionPane.showMessageDialog(null, "You have to select a whole word . Please select again.");
						return false;
					}
				}
			} else if (startColIndex == 0) {
				if (((myModel.getValueAt(startRowIndex - 1, startColIndex) != null
						&& !myModel.getValueAt(startRowIndex - 1, startColIndex).equals(""))
						|| (myModel.getValueAt(startRowIndex + 1, startColIndex) != null
								&& !myModel.getValueAt(startRowIndex + 1, startColIndex).equals("")))
						&& (myModel.getValueAt(startRowIndex, startColIndex + 1) != null
								&& !myModel.getValueAt(startRowIndex, startColIndex + 1).equals(""))) {
					JOptionPane.showMessageDialog(null, "You have to select a whole word . Please select again.");
					return false;
				}
			} else if (startColIndex == 19) {
				if (((myModel.getValueAt(startRowIndex - 1, startColIndex) != null
						&& !myModel.getValueAt(startRowIndex - 1, startColIndex).equals(""))
						|| (myModel.getValueAt(startRowIndex + 1, startColIndex) != null
								&& !myModel.getValueAt(startRowIndex + 1, startColIndex).equals("")))
						&& (myModel.getValueAt(startRowIndex, startColIndex - 1) != null
								&& !myModel.getValueAt(startRowIndex, startColIndex - 1).equals(""))) {
					JOptionPane.showMessageDialog(null, "You have to select a whole word . Please select again.");
					return false;
				}
			} else {
				if (((myModel.getValueAt(startRowIndex - 1, startColIndex) != null
						&& !myModel.getValueAt(startRowIndex - 1, startColIndex).equals(""))
						|| (myModel.getValueAt(startRowIndex + 1, startColIndex) != null
								&& !myModel.getValueAt(startRowIndex + 1, startColIndex).equals("")))
						&& ((myModel.getValueAt(startRowIndex, startColIndex - 1) != null
								&& !myModel.getValueAt(startRowIndex, startColIndex - 1).equals(""))
								|| (myModel.getValueAt(startRowIndex, startColIndex + 1) != null
										&& !myModel.getValueAt(startRowIndex, startColIndex + 1).equals("")))) {
					JOptionPane.showMessageDialog(null, "You have to select a whole word . Please select again.");
					return false;
				}
			}
		} else if (startRowIndex == endRowIndex) {
			if ((startColIndex > 0 && myModel.getValueAt(startRowIndex, startColIndex - 1) != null
					&& !myModel.getValueAt(startRowIndex, startColIndex - 1).equals(""))
					|| (endColIndex > 19 && myModel.getValueAt(startRowIndex, endColIndex + 1) != null
							&& !myModel.getValueAt(startRowIndex, endColIndex + 1).equals(""))) {
				JOptionPane.showMessageDialog(null, "You have to select a whole word . Please select again.");
				return false;
			}
		} else if (startColIndex == endColIndex) {
			if ((startRowIndex > 0 && myModel.getValueAt(startRowIndex - 1, startColIndex) != null
					&& !myModel.getValueAt(startRowIndex - 1, startColIndex).equals(""))
					|| (endRowIndex < 19 && myModel.getValueAt(endRowIndex + 1, startColIndex) != null
							&& !myModel.getValueAt(endRowIndex + 1, startColIndex).equals(""))) {
				JOptionPane.showMessageDialog(null, "You have to select a whole word. Please select again.");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null,
					" You have to select a word in a vertical or horizontal line. Please select again.");
			return false;
		}
		for (int i = startRowIndex; i <= endRowIndex; i++) {
			for (int j = startColIndex; j <= endColIndex; j++) {
				if (null == myModel.getValueAt(i, j) || myModel.getValueAt(i, j).equals("")) {
					JOptionPane.showMessageDialog(null,
							" You have to select a whole word without empty cells. Please select again.");
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

		vCol.add("Online Players");
		for (int i = 0; i < players.size(); i++) {
			String playersName = players.get(i);
			Vector<Object> visitRow = new Vector<Object>();
			visitRow.add(playersName);
			visiting.add(visitRow);
		}
		visitingTable.setModel(new MyDefaultTableModel(visiting, vCol));

		if (ScrabbleClient.player.getRoomState()
				&& ScrabbleClient.player.getRoomCreatorName().equals(ScrabbleClient.player.getUserName())) {
			vCol.add("Invite");
			ArrayList<String> notGamers = new ArrayList<String>();
			for (int j = 0; j < players.size(); j++)
				if (!gamers.contains(players.get(j)))
					notGamers.add(players.get(j));

			ButtonRenderer renderer = new ButtonRenderer();

			for (int i = 0; i < notGamers.size(); i++) {
				Vector<Object> inviteRow = new Vector<Object>();
				String noGamerName = notGamers.get(i);
				inviteRow.add(noGamerName);
				JButton addButton = new JButton("+");
				addButton.setBounds(118, 10, 39, 23);

				inviteRow.add(addButton);
				invite.add(inviteRow);
			}

			playerTable.setModel(new MyDefaultTableModel(invite, vCol));
			playerTable.getColumn("Invite").setCellRenderer(renderer);
			MouseListener[] listeners = playerTable.getMouseListeners();
			for (int i = 1; i < listeners.length; i++)
				playerTable.removeMouseListener(listeners[i]);
			playerTable.addMouseListener(new ButtonMouseListener(playerTable));
		} else {
			playerTable.setModel(new MyDefaultTableModel(visiting, vCol));
		}
	}

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
			newTable = true;
			char[][] grid = ScrabbleClient.player.getGrid();
			for (int i = 0; i < grid.length; i++)
				for (int j = 0; j < grid.length; j++)
					if (grid[i][j] != ' ')
						newTable = false;

			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] != ' ') {
						myModel.setValueAt(grid[i][j], i, j);
						myModel.setCellEditable(i, j, false);
					} else {
						if (newTable) {
							myModel.setValueAt("", i, j);
						}
						myModel.setCellEditable(i, j, true);
					}
				}
			}
			tableChanged = false;
			newTable = false;
		} catch (Exception e) {
			System.out.println("Error: ClientGUI -> freshTable");
		}
	}

	public void showLobby() {
		if (ScrabbleClient.player.getRoomState()) {
			if (!ScrabbleClient.player.getRoomCreatorName().equals(ScrabbleClient.player.getUserName())) {
				btnCreateRoom.setVisible(false);
				btnStartGame.setVisible(false);
				lblCreatRoom.setText("Room has been created. Please wait for invitation or game starting.");
			} else {
				lblCreatRoom.setText("Create room successfully! Invite other players into a game.");
				btnCreateRoom.setVisible(false);
				if (ScrabbleClient.player.getGamers().size() > 1)
					btnStartGame.setVisible(true);
			}
		} else {
			btnCreateRoom.setVisible(true);
			btnStartGame.setVisible(false);
			lblCreatRoom.setText(null);
			lblInvitemessage.setText(null);
		}
		freshPlayerList();
		freshGamerList();
		cardLayout.show(frame.getContentPane(), "name_891358432709494");
	}

	public void showGame(String currentPlayer) {
		lblPlayersTurn.setText(ScrabbleClient.player.getCurrentPlayer() + "'s turn.");
		freshTable();
		freshPlayerList();
		freshGamerList();
		if (currentPlayer.equals(ScrabbleClient.player.getUserName())) {
			btnLayout.show(btnPanel, "passPanel");
			gameTable.setEnabled(true);
		} else {
			btnLayout.show(btnPanel, "voidPanel");
			setTableEditable(false);
			gameTable.setEnabled(false);
		}
		cardLayout.show(frame.getContentPane(), "name_891421322563457");
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
		lblMessage.setText("");
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
			lblMessage.setText("");
			if (ScrabbleClient.player.getGamers().contains(ScrabbleClient.player.getUserName())) {
				btnLayout.show(btnPanel, "votePanel");
			} else
				btnLayout.show(btnPanel, "voidPanel");
		}
	}

	public void voteResult(String beginVoteUserName, boolean accepted, int mark, String nextUserName) {
		if (accepted) {
			if (ScrabbleClient.player.getUserName().equals(beginVoteUserName))
				lblMessage.setText("Vote successfully! You get " + mark + " point(s)!");
			else
				lblMessage.setText("Vote successfully! " + beginVoteUserName + " gets " + mark + " point(s)!");
		} else
			lblMessage.setText("Vote Failed! " + nextUserName + "'s turn!");
	}

	private void setTableEditable(boolean editable) {
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 20; j++)
				myModel.setCellEditable(i, j, editable);
	}
}
