import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JTextArea;

public class ClientInputController extends Thread {

	private Socket socket;
	private JTextArea consoleScreen;
	
	public ClientInputController(Socket incomingSocket, JTextArea consoleScreen) {
		this.setSocket(incomingSocket);
		this.setConsoleScreen(consoleScreen);
	}
	
	public void run() {
		
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public JTextArea getConsoleScreen() {
		return consoleScreen;
	}

	public void setConsoleScreen(JTextArea consoleScreen) {
		this.consoleScreen = consoleScreen;
	}
}
