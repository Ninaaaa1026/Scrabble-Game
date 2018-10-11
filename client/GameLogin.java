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
import javax.swing.JButton;
import javax.swing.JTextField;

import remote.ClientInterface;
import remote.ServerInterface;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.awt.event.ActionEvent;

/***
 * This class defines the login window for clients.
 * It displays the GUI, takes the inputs of users, and then connects to the server.
 * It also keeps a thread checking the connection to the server regularly.
 */
public class GameLogin {

	private JFrame frame;
	private JTextField loginTextField; //username
	private JTextField IPTextField; //IP address
	private JTextField portTextField; //port number
	private JLabel lblWelcomeToScrabble;
	ClientGUI window;

	/**
	 * Create the application.
	 */
	public GameLogin(ClientGUI gui) {
		window = gui;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 416, 259);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton loginButton = new JButton("Log in ");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int port = Integer.parseInt(portTextField.getText());
					ScrabbleClient.player.setUserName(loginTextField.getText());
					// Connect to the rmi registry that is running on localhost
					Registry registry = LocateRegistry.getRegistry(IPTextField.getText(),port);

					// Retrieve the stub/proxy for the remote math object from the registry
					// A thread is created to regularly check the connection to the server
					try {
						ScrabbleClient.remoteServer = (ServerInterface) registry.lookup("Scrabble");

						Thread connectThread = new Thread() {
							public void run() {
								try {
									while (true) {
										ScrabbleClient.remoteServer.checkConnect();
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								} catch (RemoteException e) {
									JOptionPane.showMessageDialog(null, "Server disconnected. Please try later.");
									System.exit(0);
								}
							}
						};
						connectThread.start();

						if (ScrabbleClient.remoteServer.addClient(loginTextField.getText(),
								(ClientInterface) ScrabbleClient.player)) {

							EventQueue.invokeLater(new Runnable() {
								public void run() {
									try {
										if (ScrabbleClient.player.getGameState()) {
											window.showGame(ScrabbleClient.player.getCurrentPlayer());
										} else {
											window.showLobby();
										}
										window.getFrame().setVisible(true);
										ScrabbleClient.window.getFrame().setVisible(false);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						} else {
							JOptionPane.showMessageDialog(null,
									" Username already exists. Please input another user name.");
							loginTextField.setText(null);
						}

					} catch (NotBoundException e1) {
						e1.printStackTrace();
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "port number should be numeric.");
				} catch(java.rmi.UnknownHostException e){
					JOptionPane.showMessageDialog(null, "Unkown IP address.");
					IPTextField.setText(null);
					portTextField.setText(null);
				} catch (RemoteException e) {
					//e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot connect to server. Please check the port number or try later.");
					System.exit(0);
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
