package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.GridLayout;
import javax.swing.JScrollPane;

public class ClientGUI implements ActionListener {

	protected JFrame frame;
	protected JTable gameTable;
	protected JButton btnSelectWord;
	protected JButton btnPass;
	protected JButton btnVote;
	protected JButton btnAgreeVote;
	protected JButton btnCreateRoom;
	protected JLabel lblPlayersTurn;
	protected JLabel lblCurrentGameState;
	protected JLabel InvitedPlayersLabel;
	protected JButton btnNext;
	protected  JButton btnDisagree;
	protected  JPanel btnPanel;
	protected  JPanel votePanel;
	protected  JPanel selectPenel;
	protected  JPanel passPanel;
	protected DefaultTableModel myModel;
	protected  JPanel voidPanel;
	private JPanel listPanel;
	private JPanel panel_1;
	private JScrollPane scrollPane_1;
	private JPanel panel_2;
	private JPanel visitingPanel;
	private JLabel visitingLabel;

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	}

	public static void main(String[] args) {
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 583, 593);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		JPanel gameRoomPanel = new JPanel();
		frame.getContentPane().add(gameRoomPanel, "name_891421322563457");
		gameRoomPanel.setLayout(null);

		JPanel gamersPanel = new JPanel();
		gamersPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		gamersPanel.setBounds(0, 0, 383, 93);
		gameRoomPanel.add(gamersPanel);
		gamersPanel.setLayout(null);

		JLabel lblCurrentPlayers = new JLabel("Current Players:");
		lblCurrentPlayers.setBounds(10, 10, 114, 15);
		gamersPanel.add(lblCurrentPlayers);

		JPanel GameTablePanel = new JPanel();
		GameTablePanel.setBorder(UIManager.getBorder("FileChooser.listViewBorder"));
		GameTablePanel.setBounds(10, 128, 363, 337);
		gameRoomPanel.add(GameTablePanel);
		GameTablePanel.setLayout(null);

		gameTable = new JTable();
		gameTable.addInputMethodListener(new InputMethodListener() {
			public void inputMethodTextChanged(InputMethodEvent arg0) {
				
			}

			@Override
			public void caretPositionChanged(InputMethodEvent arg0) {

				
			}
		});
		gameTable.setRowSelectionAllowed(false);
		gameTable.setCellSelectionEnabled(true);
		gameTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		gameTable.setBorder(UIManager.getBorder("ComboBox.border"));

		gameTable.setBounds(10, 10, 345, 319);
		GameTablePanel.add(gameTable);
		myModel= new MyDefaultTableModel(20,20); 
		gameTable.setModel(myModel);

		lblPlayersTurn = new JLabel("Player 's Turn");
		lblPlayersTurn.setBounds(10, 103, 254, 15);
		gameRoomPanel.add(lblPlayersTurn);

		btnPanel = new JPanel();
		btnPanel.setBounds(0, 469, 381, 85);
		gameRoomPanel.add(btnPanel);
		btnPanel.setLayout(new CardLayout(0, 0));
		
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
		btnNext.setBounds(140, 35, 99, 23);
		selectPenel.add(btnNext);

		btnSelectWord = new JButton("Select Word");
		btnSelectWord.setBounds(23, 35, 99, 23);
		selectPenel.add(btnSelectWord);

		btnVote = new JButton("Vote");
		btnVote.setBounds(254, 35, 99, 23);
		selectPenel.add(btnVote);
		btnVote.addActionListener(this);
		btnSelectWord.addActionListener(this);
		btnNext.addActionListener(this);
		
		voidPanel = new JPanel();
		btnPanel.add(voidPanel, "name_965239449619613");
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(383, 0, 184, 554);
		gameRoomPanel.add(scrollPane_1);
		
		panel_2 = new JPanel();
		scrollPane_1.setViewportView(panel_2);
		panel_2.setBackground(SystemColor.controlHighlight);
		panel_2.setLayout(null);
		
		visitingPanel = new JPanel();
		visitingPanel.setBounds(0, 0, 182, 40);
		panel_2.add(visitingPanel);
		visitingPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		visitingLabel = new JLabel("");
		visitingPanel.add(visitingLabel);

		JPanel gameLobbyPanel = new JPanel();
		frame.getContentPane().add(gameLobbyPanel, "name_891358432709494");
		gameLobbyPanel.setLayout(null);

		lblCurrentGameState = new JLabel("Current Game State");
		lblCurrentGameState.setBounds(128, 179, 115, 15);
		gameLobbyPanel.add(lblCurrentGameState);

		btnCreateRoom = new JButton("Create Room");
		btnCreateRoom.addActionListener(this);
		btnCreateRoom.setBackground(UIManager.getColor("Button.light"));
		btnCreateRoom.setBounds(133, 247, 110, 23);
		gameLobbyPanel.add(btnCreateRoom);

		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("ProgressBar.border"));
		panel.setBounds(0, 347, 381, 207);
		gameLobbyPanel.add(panel);
		panel.setLayout(null);

		InvitedPlayersLabel = new JLabel("");
		InvitedPlayersLabel.setBounds(10, 10, 165, 15);
		panel.add(InvitedPlayersLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(382, 0, 185, 554);
		gameLobbyPanel.add(scrollPane);
		
		panel_1 = new JPanel();
		panel_1.setBackground(UIManager.getColor("Button.light"));
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);
		
		listPanel = new JPanel();
		listPanel.setBounds(10, 10, 167, 38);
		panel_1.add(listPanel);
		listPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel invitePanel = new JPanel();
		listPanel.add(invitePanel);
		invitePanel.setLayout(null);
		
		JButton addButton = new JButton("+");
		addButton.setBounds(118, 10, 39, 23);
		invitePanel.add(addButton);
		
		JLabel playerLabel = new JLabel("");
		playerLabel.setBounds(10, 14, 98, 15);
		invitePanel.add(playerLabel);
	}

	public void actionPerformed(ActionEvent arg0) {
		
		
		if (arg0.getSource().equals(btnPass)) {
			//ScrabbleClient.remoteServer.passTurn(ScrabbleClient.player.getUserName());
		} else if (arg0.getSource().equals(btnVote)) {
			//char character=
			//int startRowIndex=
			//int startColIndex=
			//int endRowIndex=
			//int endColIndex=
			//ScrabbleClient.remoteServer.vote(startRowIndex,startColIndex, endRowIndex, endColIndex);
		} else if (arg0.getSource().equals(btnAgreeVote)) {			
			//ScrabbleClient.remoteServer.agreeVote(false,ScrabbleClient.player.getUserName());
		} else if (arg0.getSource().equals(btnSelectWord)) {
			//char character=
			//int rowIndex=
			//int colIndex=		
			//ScrabbleClient.remoteServer.changeTable(character,rowIndex,colIndex);
		}else if (arg0.getSource().equals(btnNext)) {
			//char character=
			//int rowIndex=
			//int colIndex=	
			//ScrabbleClient.remoteServer.nextTurn(character, rowIndex, colIndex, ScrabbleClient.player.getUserName());
		}else if (arg0.getSource().equals(btnDisagree)){
			//ScrabbleClient.remoteServer.agreeVote(false,ScrabbleClient.player.getUserName());
		}else if (arg0.getSource().equals(btnCreateRoom)){
			//ScrabbleClient.remoteServer.createRoom(ScrabbleClient.player.getUserName());
		}
	}

	public JFrame getFrame() {
		return frame;
	}
}
