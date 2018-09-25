package client;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import remote.ClientInterface;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class GameLogin {

	private JFrame frame;
	private JTextField loginTextField;
	private JTextField IPTextField;
	private JTextField portTextField;
	private JLabel lblWelcomeToScrabble;
	ClientGUI window;

	/**
	 * Create the application.
	 */
	public GameLogin(ClientGUI gui) {
		window=gui;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 416, 259);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton loginButton = new JButton("Log in ");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				try {
					int port=Integer.parseInt(portTextField.getText());
					ScrabbleClient.player.setUserName(loginTextField.getText());
					ScrabbleClient.player.setIPAddress(IPTextField.getText());
					ScrabbleClient.player.setPortNumber(port);
					if(ScrabbleClient.remoteServer.addClient(loginTextField.getText(), (ClientInterface)ScrabbleClient.player,IPTextField.getText(), port)) {
						
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									if(ScrabbleClient.player.getGameState()) {
										window.showGame();
									}
									else {
										window.showLobby();
									}
									window.getFrame().setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
					else {
						JOptionPane.showMessageDialog(null,
								" Username already exists.Please input another user name.");
						loginTextField.setText(null);
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null,
							" port number should be numeric.");
				} catch ( RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		loginButton.setFont(new Font("Century", Font.PLAIN, 13));
		loginButton.setBounds(269, 150, 93, 23);
		frame.getContentPane().add(loginButton);
		
		loginTextField = new JTextField();
		loginTextField.setBounds(169, 67, 193, 21);
		frame.getContentPane().add(loginTextField);
		loginTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User Name :");
		lblNewLabel.setFont(new Font("Century", Font.PLAIN, 13));
		lblNewLabel.setBounds(61, 70, 86, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblIpAddress = new JLabel("IP Address :");
		lblIpAddress.setFont(new Font("Century", Font.PLAIN, 13));
		lblIpAddress.setBounds(61, 109, 86, 15);
		frame.getContentPane().add(lblIpAddress);
		
		IPTextField = new JTextField();
		IPTextField.setColumns(10);
		IPTextField.setBounds(169, 106, 193, 21);
		frame.getContentPane().add(IPTextField);
		
		JLabel lblPortNumber = new JLabel("Port :");
		lblPortNumber.setFont(new Font("Century", Font.PLAIN, 13));
		lblPortNumber.setBounds(98, 154, 39, 15);
		frame.getContentPane().add(lblPortNumber);
		
		portTextField = new JTextField();
		portTextField.setColumns(10);
		portTextField.setBounds(169, 150, 83, 21);
		frame.getContentPane().add(portTextField);
		
		lblWelcomeToScrabble = new JLabel("Welcome to Scrabble Game!");
		lblWelcomeToScrabble.setFont(new Font("Century", Font.PLAIN, 17));
		lblWelcomeToScrabble.setBounds(10, 20, 352, 19);
		frame.getContentPane().add(lblWelcomeToScrabble);
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
