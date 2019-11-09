import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainClient {

	// Swing GUI management
	private JFrame frame;
	private JLabel labelUsername;
	private JTextField fieldUsername;
	private JButton buttonUserLogin;
	private JLabel labelStudentSurname;
	private JTextField fieldStudentSurname;
	private JButton buttonStudentSearch;
	private JButton buttonClear;
	private JButton buttonPrevious;
	private JButton buttonNext;
	private JButton buttonExit;
	private JTextArea consoleScreen;
	
	// Server's connection socket and IO streams for reading/writing back to server
	private Socket outcomingSocket;
	private DataOutputStream outputToServer;
	private DataInputStream inputFromServer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainClient window = new MainClient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainClient() {
		initialize();
	}
	
	/**
	 * Sets up the GUI for the login and control panel screens.
	 */
	private void initializeGUI() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		labelUsername = new JLabel("Username");
		labelUsername.setBounds(50, 50, 100, 13);
		frame.getContentPane().add(labelUsername);
		
		fieldUsername = new JTextField();
		fieldUsername.setBounds(130, 50, 96, 19);
		frame.getContentPane().add(fieldUsername);
		fieldUsername.setColumns(10);
		
		buttonUserLogin = new JButton("Admin User Login");
		buttonUserLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = fieldUsername.getText();
				
				if (username.isBlank()) {
					return;
				};
				
				try {
					outputToServer.writeUTF("login-" + username);
					
					if (inputFromServer.readBoolean()) {
						initializeControlPanelForUser();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonUserLogin.setBounds(260, 50, 140, 21);
		frame.getContentPane().add(buttonUserLogin);
		
		consoleScreen = new JTextArea();
		consoleScreen.setEditable(false);
		consoleScreen.setWrapStyleWord(true);
		consoleScreen.setBounds(10, 150, 416, 103);
		frame.getContentPane().add(consoleScreen);
		
		labelStudentSurname = new JLabel("Surname");
		labelStudentSurname.setBounds(50, 50, 100, 13);
		frame.getContentPane().add(labelStudentSurname);
		
		fieldStudentSurname = new JTextField();
		fieldStudentSurname.setBounds(130, 50, 96, 19);
		frame.getContentPane().add(fieldStudentSurname);
		fieldStudentSurname.setColumns(10);
		
		buttonStudentSearch = new JButton("Search Student");
		buttonStudentSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonStudentSearch.setBounds(260, 50, 140, 21);
		frame.getContentPane().add(buttonStudentSearch);
		
		buttonClear = new JButton("Clear");
		buttonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonClear.setBounds(260, 81, 140, 21);
		frame.getContentPane().add(buttonClear);
		
		buttonPrevious = new JButton("<");
		buttonPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonPrevious.setBounds(50, 105, 85, 21);
		frame.getContentPane().add(buttonPrevious);
		
		buttonNext = new JButton(">");
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonNext.setBounds(141, 105, 85, 21);
		frame.getContentPane().add(buttonNext);
		
		buttonExit = new JButton("X");
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializeLoginScreen();
			}
		});
		buttonExit.setBounds(370, 10, 60, 21);
		frame.getContentPane().add(buttonExit);
	}
	
	/**
	 * Sets up the GUI for the starting login screen.
	 * Hides all the frame contents for the control panel screen.
	 */
	private void initializeLoginScreen() {
		labelUsername.setVisible(true);
		fieldUsername.setVisible(true);
		buttonUserLogin.setVisible(true);
		labelStudentSurname.setVisible(false);
		fieldStudentSurname.setVisible(false);
		buttonStudentSearch.setVisible(false);
		buttonClear.setVisible(false);
		buttonPrevious.setVisible(false);
		buttonNext.setVisible(false);
		buttonExit.setVisible(false);
	}
	
	/**
	 * Sets up the GUI for the main control panel view for a user.
	 * Hides all the frame contents for the login screen.
	 */
	private void initializeControlPanelForUser() {
		labelUsername.setVisible(false);
		fieldUsername.setVisible(false);
		buttonUserLogin.setVisible(false);
		labelStudentSurname.setVisible(true);
		fieldStudentSurname.setVisible(true);
		buttonStudentSearch.setVisible(true);
		buttonClear.setVisible(true);
		buttonPrevious.setVisible(true);
		buttonNext.setVisible(true);
		buttonExit.setVisible(true);
	}
	
	/**
	 * Makes a connection to the LocalHost server.
	 */
	public void initializeConnection() {
		try {
			outcomingSocket = new Socket("localhost", MainServer.PORT_NUMBER);
			outputToServer = new DataOutputStream(outcomingSocket.getOutputStream());
			inputFromServer = new DataInputStream(outcomingSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	/**
	 * Initializes the connection to the server and the default login screen.
	 */
	private void initialize() {
		initializeConnection();
		initializeGUI();
		initializeLoginScreen();
	}
}
