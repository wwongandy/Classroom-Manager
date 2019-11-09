import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JTextArea;

public class ClientController extends Thread {

	// Client's socket and IO streams for reading/writing back to client
	private Socket socket;
	private DataInputStream inputFromClient;
	private DataOutputStream outputToClient;
	
	// For appending messages back to the server GUI
	private JTextArea consoleScreen;
	
	public ClientController(Socket incomingSocket, JTextArea consoleScreen) throws IOException {
		this.setSocket(incomingSocket);
		this.setInputFromClient(new DataInputStream(incomingSocket.getInputStream()));
		this.setOutputToClient(new DataOutputStream(incomingSocket.getOutputStream()));
		this.setConsoleScreen(consoleScreen);
		writeToConsole("Connected to server.");
	}
	
	public void run() {
		while (true) {
			try {
				String inputString = inputFromClient.readUTF();
				
				String[] requestBody = inputString.split("-");
				String request = requestBody[0];
				String dataObject = requestBody[1];
				
				System.out.println(request);
				System.out.println(dataObject);
				writeToConsole("Request received: " + request);
				writeToConsole("Data received: " + dataObject);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void writeToConsole(String message) {
		InetAddress netData = socket.getInetAddress();
		String ipAddress = netData.getHostAddress();
		
		String out = "[" + ipAddress + "] " + message + '\n';
		consoleScreen.append(out);
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public DataInputStream getInputFromClient() {
		return inputFromClient;
	}

	public void setInputFromClient(DataInputStream inputFromClient) {
		this.inputFromClient = inputFromClient;
	}

	public DataOutputStream getOutputToClient() {
		return outputToClient;
	}

	public void setOutputToClient(DataOutputStream outputToClient) {
		this.outputToClient = outputToClient;
	}

	public JTextArea getConsoleScreen() {
		return consoleScreen;
	}

	public void setConsoleScreen(JTextArea consoleScreen) {
		this.consoleScreen = consoleScreen;
	}
}
