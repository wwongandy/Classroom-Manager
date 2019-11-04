import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MainServer {

	// Swing GUI management
	private JFrame frame;
	private JTextArea consoleScreen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainServer window = new MainServer();
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
	public MainServer() {
		initialize();
	}
	
	/**
	 * Sets up the main GUI console screen for the server.
	 */
	private void initializeConsoleScreen() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		consoleScreen = new JTextArea();
		consoleScreen.setEditable(false);
		consoleScreen.setWrapStyleWord(true);
		consoleScreen.setBounds(10, 10, 415, 240);
		frame.getContentPane().add(consoleScreen);
	}
	
	/**
	 * Sets up JDBC connection to the locally hosted SQL database.
	 */
	private void connectToSQLDatabase() {
		
	}
	
	/**
	 * Starts the server for handling user requests.
	 */
	private void initializeServer() {
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initializeConsoleScreen();
		connectToSQLDatabase();
		initializeServer();
	}
}
