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
	
	private DatabaseController db; // For handling client requests to the database
	private int clientNumber; // Used to differentiate between different clients for a server
	
	// For appending messages back to the server GUI
	private JTextArea consoleScreen;
	
	public ClientController(Socket incomingSocket, int clientNumber, JTextArea consoleScreen) throws IOException {
		this.setSocket(incomingSocket);
		this.setInputFromClient(new DataInputStream(incomingSocket.getInputStream()));
		this.setOutputToClient(new DataOutputStream(incomingSocket.getOutputStream()));
		this.setClientNumber(clientNumber);
		this.setDB(new DatabaseController());
		this.setConsoleScreen(consoleScreen);
		
		if (db.isConnected()) {
			writeToConsole("Connected to server.");
		} else {
			writeToConsole("Attempted to connect to server but failed due to database controller.");
			this.interrupt();
		}
	}
	
	public void run() {
		while (true) {
			try {
				String inputString = inputFromClient.readUTF();
				
				String[] requestBody = inputString.split("-");
				String request = requestBody[0];
				String dataObject = requestBody[1];
				
				writeToConsole("Request received: " + request);
				writeToConsole("Data received: " + dataObject);
				
				switch(request) {
					case "login":
						writeToConsole("Attempting to login user..");
						
						boolean canLogin = db.userLogin(dataObject);
						// outputToClient.writeUTF("login-" + canLogin);
						outputToClient.writeBoolean(canLogin);
						
						if (canLogin) {
							writeToConsole("User " + dataObject + " logged in.");
						} else {
							writeToConsole("User " + dataObject + " was denied login attempt.");
						}
						
						break;
						
					default:
						break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void writeToConsole(String message) {
		InetAddress netData = socket.getInetAddress();
		String ipAddress = netData.getHostAddress();
		
		String out = "[Client " + clientNumber +  "] [" + ipAddress + "] " + message + '\n';
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
	
	public DatabaseController getDB() {
		return db;
	}

	public void setDB(DatabaseController db) {
		this.db = db;
	}
	
	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}
	
	public JTextArea getConsoleScreen() {
		return consoleScreen;
	}

	public void setConsoleScreen(JTextArea consoleScreen) {
		this.consoleScreen = consoleScreen;
	}
}
