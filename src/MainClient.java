import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

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
	
	private ArrayList<Student> currentStudents;
	private int currentStudentIndex;

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
		currentStudents = new ArrayList();
		currentStudentIndex = 0;
		
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
				loginHandler();
			}
		});
		buttonUserLogin.setBounds(260, 50, 140, 21);
		frame.getContentPane().add(buttonUserLogin);
		
		buttonClear = new JButton("Clear");
		buttonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearTextFields();
			}
		});
		buttonClear.setBounds(260, 81, 140, 21);
		frame.getContentPane().add(buttonClear);
		
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
				studentSearchHandler();
			}
		});
		buttonStudentSearch.setBounds(260, 50, 140, 21);
		frame.getContentPane().add(buttonStudentSearch);
		
		buttonPrevious = new JButton("<");
		buttonPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousStudentHandler();
			}
		});
		buttonPrevious.setBounds(50, 105, 85, 21);
		frame.getContentPane().add(buttonPrevious);
		
		buttonNext = new JButton(">");
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextStudentHandler();
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
		
		consoleScreen = new JTextArea();
		consoleScreen.setEditable(false);
		consoleScreen.setWrapStyleWord(true);
		consoleScreen.setBounds(10, 150, 416, 103);
		
		JScrollPane scrollPane = new JScrollPane(consoleScreen);
		scrollPane.setBounds(10, 150, 416, 103);
		frame.getContentPane().add(scrollPane);
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
		buttonPrevious.setVisible(true);
		buttonNext.setVisible(true);
		buttonExit.setVisible(true);
	}
	
	/**
	 * Appends the given message to the console GUI.
	 * 
	 * @param message
	 */
	private void writeToConsole(String message) {
		String out = "[" + new Date() + "] " + message + '\n';
		consoleScreen.append(out);
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
	
	/**
	 * Sends an authentication request to the server and attempts to logs in to the control panel view
	 */
	private void loginHandler() {
		String username = fieldUsername.getText();
		
		if (username.isBlank()) {
			writeToConsole("The username field must not be empty.");
			
			return;
		};
		
		try {
			outputToServer.writeUTF("login-" + username);
			
			if (inputFromServer.readBoolean()) {
				writeToConsole("User " + username + " found in server, login successful.");
				initializeControlPanelForUser();
			} else {
				writeToConsole("User " + username + " not found in server, login failed.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Clears out all text fields from the GUI.
	 */
	private void clearTextFields() {
		fieldUsername.setText("");
		fieldStudentSurname.setText("");
	}
	
	/**
	 * Attempts to search for an existing student from the database via the server
	 * 
	 * @throws ClassNotFoundException 
	 */
	private void studentSearchHandler() {
		String studentSurname = fieldStudentSurname.getText();
		
		if (studentSurname.isBlank()) {
			writeToConsole("The student surname field must not be empty.");
			
			return;
		}
		
		try {
			outputToServer.writeUTF("studentSurnameSearch-" + studentSurname);
			
			// Reading in an object response from the server
			ObjectInputStream objInputStreamFromServer = new ObjectInputStream(outcomingSocket.getInputStream());
			currentStudents = (ArrayList) objInputStreamFromServer.readUnshared();
			
			if (currentStudents.isEmpty()) {
				writeToConsole("No students found with surname " + studentSurname + ".");
			} else {
				writeToConsole(currentStudents.size() + " students found with surname " + studentSurname + ".");
				
				currentStudentIndex = 0;
				writeToConsole(currentStudents.get(currentStudentIndex).toString());
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves the previous student from the current selected list (searched student, or all students) via the server
	 */
	private void previousStudentHandler() {
		if (currentStudents.isEmpty()) {
			// TODO: Load all students from database
			return;
		};
		
		currentStudentIndex -= 1;
		if (currentStudentIndex < 0) {
			currentStudentIndex = currentStudents.size() - 1;
		}
		
		writeToConsole(currentStudents.get(currentStudentIndex).toString());
	}
	
	/**
	 * Retrieves the next student from the current selected list (searched student, or all students) via the server
	 */
	private void nextStudentHandler() {
		if (currentStudents.isEmpty()) {
			// TODO: Load all students from database
			return;
		};
		
		currentStudentIndex += 1;
		if (currentStudentIndex >= currentStudents.size()) {
			currentStudentIndex = 0;
		}
		
		writeToConsole(currentStudents.get(currentStudentIndex).toString());
	}
}
