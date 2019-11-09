import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	/**
	 * Note: This function runs when the thread is triggered to run.
	 * Continuously listens for new requests from the client.
	 */
	public void run() {
		while (true) {
			try {
				String inputString = inputFromClient.readUTF();
				
				// Compiling request body from the client
				// Expected input: [request]-[dataObject] e.g. login-jbloggs
				String[] requestBody = inputString.split("-");
				String request = requestBody[0];
				String dataObject = requestBody[1];
				
				writeToConsole("Request received: " + request);
				writeToConsole("Data received: " + dataObject);
				
				switch(request) {
					case "login":
						loginHandler(dataObject);
						break;
						
					case "studentSurnameSearch":
						studentSearchHandler(dataObject);
						break;
						
					case "studentAll":
						previousOrNextStudentHandler();
						break;
						
					default:
						break;
				}
				
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Appends the given message to the console GUI.
	 * 
	 * @param message
	 */
	private void writeToConsole(String message) {
		InetAddress netData = socket.getInetAddress();
		String ipAddress = netData.getHostAddress();
		
		String out = "[Client " + clientNumber +  "] [" + ipAddress + "] " + message + '\n';
		consoleScreen.append(out);
	}
	
	/**
	 * Handles the login request from the client.
	 * 
	 * @param username
	 * @throws IOException
	 */
	private void loginHandler(String username) throws IOException {
		writeToConsole("Attempting to login user..");
		
		boolean canLogin = db.userLogin(username);
		outputToClient.writeBoolean(canLogin);
		
		if (canLogin) {
			writeToConsole("User " + username + " logged in.");
		} else {
			writeToConsole("User " + username + " was denied login attempt.");
		}
	}
	
	/**
	 * Handles the student search request from the client.
	 * 
	 * @param studentSurname
	 * @throws IOException
	 * @throws SQLException 
	 */
	private void studentSearchHandler(String studentSurname) throws IOException, SQLException {
		writeToConsole("Attempting to search for student..");
		
		ResultSet _studentsFound = db.searchStudentBySurname(studentSurname);
		ArrayList<Student> studentsFound = new ArrayList();
		
		if (_studentsFound.next() == false) {
			writeToConsole("No students with surname " + studentSurname + " found.");
		} else {
			writeToConsole("Students with surname " + studentSurname + " found.");
		
			do {
				Student studentFound = Student.fromResultSet(_studentsFound);
				studentsFound.add(studentFound);
			} while (_studentsFound.next());
		}
		
		ObjectOutputStream objOutputStreamToClient = new ObjectOutputStream(socket.getOutputStream());
		objOutputStreamToClient.writeUnshared(studentsFound);
	}
	
	private void previousOrNextStudentHandler() throws SQLException, IOException {
		writeToConsole("Attempting to retrieve all students..");
		
		ResultSet _allStudents = db.getAllStudentRecords();
		ArrayList<Student> allStudents = new ArrayList();
		
		if (_allStudents.next() == false) {
			writeToConsole("No students found.");
		} else {
			writeToConsole("Students found.");
			
			do {
				Student student = Student.fromResultSet(_allStudents);
				allStudents.add(student);
			} while (_allStudents.next());
		}
		
		ObjectOutputStream objOutputStreamToClient = new ObjectOutputStream(socket.getOutputStream());
		objOutputStreamToClient.writeUnshared(allStudents);
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
