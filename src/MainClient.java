import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainClient {

	private JFrame frame;
	private JTextField fieldUsername;
	private JTextArea consoleScreen;

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
		initialize();
	}
	
	/**
	 * Sets up the GUI for the starting login screen.
	 */
	private void initializeLoginScreen() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel labelUsername = new JLabel("Username");
		labelUsername.setBounds(50, 50, 100, 13);
		frame.getContentPane().add(labelUsername);
		
		fieldUsername = new JTextField();
		fieldUsername.setBounds(130, 50, 96, 19);
		frame.getContentPane().add(fieldUsername);
		fieldUsername.setColumns(10);
		
		JButton buttonUserLogin = new JButton("Admin User Login");
		buttonUserLogin.setBounds(260, 50, 140, 21);
		frame.getContentPane().add(buttonUserLogin);
		
		consoleScreen = new JTextArea();
		consoleScreen.setEditable(false);
		consoleScreen.setWrapStyleWord(true);
		consoleScreen.setBounds(10, 150, 416, 103);
		frame.getContentPane().add(consoleScreen);
	}
	
	/**
	 * Sets up the GUI for the main control panel view for a user.
	 */
	private void initializeControlPanelForUser() {

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initializeLoginScreen();
	}

}
