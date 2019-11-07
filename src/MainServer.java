import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainServer extends JFrame {

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
		getContentPane().setLayout(new BorderLayout());
		consoleScreen.setEditable(false);
		getContentPane().add(new JScrollPane(consoleScreen), BorderLayout.CENTER);
		
		// Main frame setup
		setTitle("Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		try {
			ServerSocket serverSocket = new ServerSocket(8000);
			consoleScreen.append("Server started at " + new Date() + '\n');
			
			// Listening for connections, for each: new thread to handle individual requests concurrently
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