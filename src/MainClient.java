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
	private JTextField fieldStudentSurname;
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
		buttonUserLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel labelStudentSurname = new JLabel("Surname");
		labelStudentSurname.setBounds(50, 50, 100, 13);
		frame.getContentPane().add(labelStudentSurname);
		
		fieldStudentSurname = new JTextField();
		fieldStudentSurname.setBounds(130, 50, 96, 19);
		frame.getContentPane().add(fieldStudentSurname);
		fieldStudentSurname.setColumns(10);
		
		JButton buttonStudentSearch = new JButton("Search Student");
		buttonStudentSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonStudentSearch.setBounds(260, 50, 140, 21);
		frame.getContentPane().add(buttonStudentSearch);
	
		consoleScreen = new JTextArea();
		consoleScreen.setEditable(false);
		consoleScreen.setWrapStyleWord(true);
		consoleScreen.setBounds(10, 150, 416, 103);
		frame.getContentPane().add(consoleScreen);
		
		JButton buttonClear = new JButton("Clear");
		buttonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonClear.setBounds(260, 81, 140, 21);
		frame.getContentPane().add(buttonClear);
		
		JButton buttonPrevious = new JButton("<");
		buttonPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonPrevious.setBounds(50, 105, 85, 21);
		frame.getContentPane().add(buttonPrevious);
		
		JButton buttonNext = new JButton(">");
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonNext.setBounds(141, 105, 85, 21);
		frame.getContentPane().add(buttonNext);
		
		JButton buttonExit = new JButton("X");
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonExit.setBounds(370, 10, 60, 21);
		frame.getContentPane().add(buttonExit);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initializeControlPanelForUser();
	}
}
