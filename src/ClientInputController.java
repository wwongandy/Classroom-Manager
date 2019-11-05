import java.net.InetAddress;
import java.net.Socket;

public class ClientInputController extends Thread {

	private InetAddress ipAddress;
	
	public ClientInputController(Socket incomingSocket) {
		ipAddress = incomingSocket.getInetAddress();
	}
	
	public void run() {
		
	}
}
