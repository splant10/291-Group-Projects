// Spencer Plant
// CMPUT 291 - Group Assignment 1
// This is gonna be one HUGE class. If it weren't for time constraints, would probably use MVC
// Actual classes for transactions, registrations, etc, will be handled themselves though

package bin;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*; // Java package for accessing Oracle

public class GroupProject1 {

	private JFrame frame;
	private JTextField textUserName;
	private JPasswordField textPassword;
	private JTextField textTransactionBuyer;
	private JTextField textTransactionSeller;
	private JTextField textTransactionDate;
	private JTextField textTransactionPrice;
	
	// The URL we are connecting to
    private String m_url = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";

    // The driver to use for connection
    private static String m_driverName = "oracle.jdbc.driver.OracleDriver";
    private static Connection m_con;
    private boolean loggedIn = false;
    
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupProject1 window = new GroupProject1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// load the Oracle driver
		try
		{
    	    Class drvClass = Class.forName(m_driverName); 
            // DriverManager.registerDriver((Driver)drvClass.newInstance());- not needed. 
            // This is automatically done by Class.forName().
		} catch(Exception e)
		{
			System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
		}
		
		// Make sure to close SQL connection when program exits!
		Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
				try {
					m_con.close();
					System.out.println("connection closed!");
				}
				catch(Exception e) {
					// only happens when not logged in and close the program.
					System.out.println("no connection to close!");
				}
            }        
		});
	}

	/**
	 * Create the application.
	 */
	public GroupProject1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 488, 441);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Username text field for logging in
		textUserName = new JTextField();
		textUserName.setBounds(12, 33, 177, 19);
		frame.getContentPane().add(textUserName);
		textUserName.setColumns(10);
		// Password text field for logging in
		textPassword = new JPasswordField();
		textPassword.setBounds(201, 33, 177, 19);
		frame.getContentPane().add(textPassword);
		
		// Labels for username and password fields
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(12, 12, 100, 15);
		frame.getContentPane().add(lblUsername);
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(201, 12, 100, 15);
		frame.getContentPane().add(lblPassword);
		
		// Make tabs for the different application programs
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 64, 462, 339);
		frame.getContentPane().add(tabbedPane);
		
		JPanel tabLicenceRegistration = new JPanel();
		tabbedPane.addTab("Driver Licence Registration", null, tabLicenceRegistration, null);
		
		JPanel tabViolationRecord = new JPanel();
		tabbedPane.addTab("Violation Record", null, tabViolationRecord, null);
		
		JPanel tabSearch = new JPanel();
		tabbedPane.addTab("Search", null, tabSearch, null);
		
		JPanel tabRegistration = new JPanel();
		tabbedPane.addTab("Vehicle Registration", null, tabRegistration, null);
		
		JPanel tabTransaction = new JPanel();
		tabbedPane.addTab("Auto Transaction", null, tabTransaction, null);
		tabTransaction.setLayout(null);
			//
			textTransactionBuyer = new JTextField();
			textTransactionBuyer.setBounds(12, 27, 209, 19);
			tabTransaction.add(textTransactionBuyer);
			textTransactionBuyer.setColumns(10);
			
			textTransactionSeller = new JTextField();
			textTransactionSeller.setBounds(233, 27, 212, 19);
			tabTransaction.add(textTransactionSeller);
			textTransactionSeller.setColumns(10);
			
			JLabel lblBuyer = new JLabel("Buyer SIN");
			lblBuyer.setBounds(12, 12, 70, 15);
			tabTransaction.add(lblBuyer);
			
			JLabel lblSeller = new JLabel("Seller SIN");
			lblSeller.setBounds(235, 12, 70, 15);
			tabTransaction.add(lblSeller);
			
			textTransactionDate = new JTextField();
			textTransactionDate.setBounds(12, 79, 209, 19);
			tabTransaction.add(textTransactionDate);
			textTransactionDate.setColumns(10);
			
			JLabel lblDate = new JLabel("Date");
			lblDate.setBounds(12, 64, 70, 15);
			tabTransaction.add(lblDate);
			
			textTransactionPrice = new JTextField();
			textTransactionPrice.setBounds(12, 129, 114, 19);
			tabTransaction.add(textTransactionPrice);
			textTransactionPrice.setColumns(10);
			
			JLabel lblPrice = new JLabel("Price");
			lblPrice.setBounds(12, 110, 70, 15);
			tabTransaction.add(lblPrice);
			
			JButton btnTransactionComplete = new JButton("Complete Transaction");
			btnTransactionComplete.setBounds(106, 221, 233, 61);
			tabTransaction.add(btnTransactionComplete);
		
		final JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Attempt to log in
				if (!loggedIn) {
					try {
						// Establish a connection
						String pw = new String(textPassword.getPassword());
						m_con = DriverManager.getConnection(m_url, textUserName.getText(), pw);
						loggedIn = true;
						btnLogin.setText("Logout");
						Color green = new Color(0,255,0);
						textUserName.setBackground(green);
						textPassword.setBackground(green);
					} catch(Exception e) {
						System.out.println(e);
						Color red = new Color(255,0,0);
						textUserName.setBackground(red);
						textPassword.setBackground(red);
					}
				} else {
					try {
						m_con.close();
					}
					catch(Exception e) {
						// shouldn't be reached
					}
					loggedIn = false;
					btnLogin.setText("Login");
					Color white = new Color(255,255,255);
					textUserName.setBackground(white);
					textPassword.setBackground(white);
					textUserName.setText("");
					textPassword.setText("");
				}
			}
		});
		btnLogin.setBounds(388, 33, 86, 19);
		frame.getContentPane().add(btnLogin);
	}
}
