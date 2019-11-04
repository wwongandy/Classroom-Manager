import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MainServer {
	
	// Local SQL database credentials
	private final String SQL_USERNAME = "root";
	private final String SQL_PASSWORD = "";
	private final String SERVER_NAME = "localhost";
	private final int PORT_NUMBER = 3306;
	private final String DATABASE_NAME = "test";
	private final String[] TABLE_NAMES = { "users", "students" };
	private Connection sql; // Main connection variable to the database

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
		Properties connectionProps = new Properties();
		
		// Logging in using the SQL credentials
		connectionProps.put("user", this.SQL_USERNAME);
		connectionProps.put("password", this.SQL_PASSWORD);
		
		try {
			// Making connection to the chosen database
			sql = DriverManager.getConnection(
					"jdbc:mysql://" + this.SERVER_NAME + ":" + this.PORT_NUMBER + "/" + this.DATABASE_NAME + "?serverTimezone=UTC",
					connectionProps
			);
			
			consoleScreen.append("Connected to SQL database " + DATABASE_NAME + ".\n");
		} catch (Exception e) {
		}
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
