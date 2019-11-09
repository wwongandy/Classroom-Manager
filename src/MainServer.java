import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainServer extends JFrame {
	
	public static int PORT_NUMBER = 8000;

	// Swing GUI management
	private JTextArea consoleScreen = new JTextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new MainServer();
	}

	/**
	 * Starts the server and sets up the console screen content.
	 */
	public MainServer() {
		initialize();
	}
	
	/**
	 * Initializes the server and sets up the console screen GUI.
	 */
	public void initialize() {
		// Main frame setup.
		getContentPane().setLayout(new BorderLayout());
		consoleScreen.setEditable(false);
		getContentPane().add(new JScrollPane(consoleScreen), BorderLayout.CENTER);
		
		setTitle("Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		// Server setup.
		try {
			ServerSocket serverSocket = new ServerSocket(MainServer.PORT_NUMBER);
			consoleScreen.append("Server started at " + new Date() + '\n');
			
			// Listening for connections, for each: new thread to handle individual requests concurrently.
			while (true) {
				Socket incomingSocket = serverSocket.accept();
				ClientController clientIn = new ClientController(incomingSocket, consoleScreen);
				
				clientIn.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}