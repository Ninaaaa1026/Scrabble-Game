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
import java.awt.FlowLayout;
import javax.swing.table.DefaultTableModel;

import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import java.awt.SystemColor;
import java.awt.GridLayout;
import javax.swing.JTextField;

public class ClientGUI implements ActionListener {

	private JFrame frame;
	private JTable gameTable;
	JButton btnSelectWord;
	JPanel visitPlayerdPanel;
	JButton btnPass;
	JButton btnVote;
	JButton btnAgreeVote;
	JButton btnCreateRoom;
	JLabel lblPlayersTurn;
	JPanel playersPanel;
	JLabel lblCurrentGameState;
	JLabel InvitedPlayersLabel;
	JButton btnNext;
	private JButton btnDisagree;
	private JPanel btnPanel;
	private JPanel votePanel;
	private JPanel selectPnel;
	private JPanel passPanel;
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
		frame.setBounds(100, 100, 542, 593);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		JPanel gameRoomPanel = new JPanel();
		frame.getContentPane().add(gameRoomPanel, "name_891421322563457");
		gameRoomPanel.setLayout(null);

		visitPlayerdPanel = new JPanel();
		visitPlayerdPanel.setBackground(SystemColor.scrollbar);
		visitPlayerdPanel.setForeground(UIManager.getColor("Button.shadow"));
		visitPlayerdPanel.setBorder(UIManager.getBorder("FileChooser.listViewBorder"));
		visitPlayerdPanel.setBounds(380, 0, 146, 554);
		gameRoomPanel.add(visitPlayerdPanel);
		visitPlayerdPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel gamersPanel = new JPanel();
		gamersPanel.setBounds(0, 0, 381, 93);
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
		gameTable.setRowSelectionAllowed(false);
		gameTable.setCellSelectionEnabled(true);
		gameTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		gameTable.setBorder(UIManager.getBorder("ComboBox.border"));
		gameTable.setModel(new DefaultTableModel(
				new Object[][] {
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
								null, null, null, null, null }, },
				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
						"17", "18", "19", "20" }));
		gameTable.setBounds(10, 10, 345, 319);
		GameTablePanel.add(gameTable);

		lblPlayersTurn = new JLabel("Player 's Turn");
		lblPlayersTurn.setBounds(10, 103, 254, 15);
		gameRoomPanel.add(lblPlayersTurn);
		
		btnPanel = new JPanel();
		btnPanel.setBounds(0, 469, 381, 85);
		gameRoomPanel.add(btnPanel);
		btnPanel.setLayout(new CardLayout(0, 0));
		
		votePanel = new JPanel();
		btnPanel.add(votePanel, "name_957940911752561");
				votePanel.setLayout(null);
		
				btnAgreeVote = new JButton("Agree");
				btnAgreeVote.setBounds(71, 30, 92, 23);
				votePanel.add(btnAgreeVote);
				
				btnDisagree = new JButton("Disagree");
				btnDisagree.setBounds(221, 30, 92, 23);
				votePanel.add(btnDisagree);
				btnAgreeVote.addActionListener(this);
		
		selectPnel = new JPanel();
		btnPanel.add(selectPnel, "name_957948997083501");
		selectPnel.setLayout(null);
		
		btnNext = new JButton("Next");
		btnNext.setBounds(140, 35, 99, 23);
		selectPnel.add(btnNext);
		
				btnSelectWord = new JButton("Select Word");
				btnSelectWord.setBounds(23, 35, 99, 23);
				selectPnel.add(btnSelectWord);
				
						btnVote = new JButton("Vote");
						btnVote.setBounds(254, 35, 99, 23);
						selectPnel.add(btnVote);
						btnVote.addActionListener(this);
				btnSelectWord.addActionListener(this);
		btnNext.addActionListener(this);
		
		passPanel = new JPanel();
		btnPanel.add(passPanel, "name_958014637000495");
				passPanel.setLayout(null);
		
				btnPass = new JButton("Pass");
				btnPass.setBounds(140, 31, 104, 23);
				passPanel.add(btnPass);
				btnPass.addActionListener(this);

		JPanel gameLobbyPanel = new JPanel();
		frame.getContentPane().add(gameLobbyPanel, "name_891358432709494");
		gameLobbyPanel.setLayout(null);

		playersPanel = new JPanel();
		playersPanel.setBackground(UIManager.getColor("Button.light"));
		playersPanel.setBorder(UIManager.getBorder("ScrollPane.border"));
		playersPanel.setBounds(380, 0, 146, 554);
		gameLobbyPanel.add(playersPanel);
		playersPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblCurrentGameState = new JLabel("Current Game State");
		lblCurrentGameState.setBounds(128, 179, 115, 15);
		gameLobbyPanel.add(lblCurrentGameState);

		btnCreateRoom = new JButton("Create Room");
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
			
			//ScrabbleClient.remoteServer.agreeVote(agree);
		} else if (arg0.getSource().equals(btnSelectWord)) {
			//char character=
			//int rowIndex=
			//int colIndex=		
			//ScrabbleClient.remoteServer.changeTable(character,rowIndex,colIndex);
		}else if (arg0.getSource().equals(btnNext)) {
			
		}
	}

	public JFrame getFrame() {
		return frame;
	}
}
