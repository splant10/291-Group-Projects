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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.JRadioButton; // Java package for accessing Oracle

public class GroupProject1 {

	private JFrame frame;
	private JTextField textUserName;
	private JPasswordField textPassword;
	private JTextField textTransactionBuyer;
	private JTextField textTransactionSeller;
	private JTextField textTransactionDate;
	private JTextField textTransactionPrice;
	

    public static Connection m_con;
    private boolean loggedIn = false;
    private JTextField textVRegistrationVID;
    private JTextField textVRegistrationMake;
    private JTextField textVRegistrationModel;
    private JTextField textVRegistrationYear;
    private JTextField textVRegistrationColor;
    private JTextField textVRegistrationOwnerSIN;
    private JTextField textVRegistrationOName;
    private JTextField textVRegistrationOHeight;
    private JTextField textField;
    private JTextField textVRegistrationOEye;
    private JTextField textVRegistrationOHair;
    private JTextField textVRegistrationOAddress;
    
	
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
    	    Class drvClass = Class.forName(Constants.drivername); 
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
		frame.setBounds(100, 100, 488, 488);
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
		tabbedPane.setBounds(12, 64, 462, 386);
		frame.getContentPane().add(tabbedPane);
		
		JPanel tabRegistration = new JPanel();
		tabbedPane.addTab("Vehicle Registration", null, tabRegistration, null);
		tabRegistration.setLayout(null);
		
		// Following is layout code for the Vehicle Registration Tab //
			textVRegistrationVID = new JTextField();
			textVRegistrationVID.setBounds(12, 28, 114, 19);
			tabRegistration.add(textVRegistrationVID);
			textVRegistrationVID.setColumns(10);
			
			JLabel lblVRegistrationVID = new JLabel("Vehicle Serial #");
			lblVRegistrationVID.setBounds(12, 12, 114, 15);
			tabRegistration.add(lblVRegistrationVID);
			
			textVRegistrationMake = new JTextField();
			textVRegistrationMake.setBounds(158, 28, 114, 19);
			tabRegistration.add(textVRegistrationMake);
			textVRegistrationMake.setColumns(10);
			
			JLabel lblVRegistrationMake = new JLabel("Make");
			lblVRegistrationMake.setBounds(158, 12, 70, 15);
			tabRegistration.add(lblVRegistrationMake);
			
			textVRegistrationModel = new JTextField();
			textVRegistrationModel.setBounds(302, 28, 114, 19);
			tabRegistration.add(textVRegistrationModel);
			textVRegistrationModel.setColumns(10);
			
			JLabel lblVRegistrationModel = new JLabel("Model");
			lblVRegistrationModel.setBounds(302, 12, 70, 15);
			tabRegistration.add(lblVRegistrationModel);
			
			textVRegistrationYear = new JTextField();
			textVRegistrationYear.setBounds(12, 73, 114, 19);
			tabRegistration.add(textVRegistrationYear);
			textVRegistrationYear.setColumns(10);
			
			JLabel lblVRegistrationYear = new JLabel("Year");
			lblVRegistrationYear.setBounds(12, 59, 70, 15);
			tabRegistration.add(lblVRegistrationYear);
			
			textVRegistrationColor = new JTextField();
			textVRegistrationColor.setBounds(158, 73, 114, 19);
			tabRegistration.add(textVRegistrationColor);
			textVRegistrationColor.setColumns(10);
			
			JLabel lblVRegistrationColor = new JLabel("Color");
			lblVRegistrationColor.setBounds(158, 59, 70, 15);
			tabRegistration.add(lblVRegistrationColor);
			
			JLabel lblVRegistrationTypeID = new JLabel("Type");
			lblVRegistrationTypeID.setBounds(302, 59, 70, 15);
			tabRegistration.add(lblVRegistrationTypeID);
			
			JSpinner spinVRegistrationTypeID = new JSpinner();
			spinVRegistrationTypeID.setModel(new SpinnerListModel(new String[] {"1, Sedan", "2, Coupe", "3, Sport", "4, Hatchback", "5, Truck", "6, Mini Van", "7, Semi", "8, SUV"}));
			spinVRegistrationTypeID.setBounds(302, 73, 114, 20);
			tabRegistration.add(spinVRegistrationTypeID);
			
			textVRegistrationOwnerSIN = new JTextField();
			textVRegistrationOwnerSIN.setBounds(12, 147, 114, 19);
			tabRegistration.add(textVRegistrationOwnerSIN);
			textVRegistrationOwnerSIN.setColumns(10);
			
			JLabel lblVRegistrationOwnerSIN = new JLabel("SIN");
			lblVRegistrationOwnerSIN.setBounds(12, 130, 114, 15);
			tabRegistration.add(lblVRegistrationOwnerSIN);
			
			textVRegistrationOName = new JTextField();
			textVRegistrationOName.setEditable(false);
			textVRegistrationOName.setBounds(158, 147, 114, 19);
			tabRegistration.add(textVRegistrationOName);
			textVRegistrationOName.setColumns(10);
			
			JLabel lblOwnerName = new JLabel("Name");
			lblOwnerName.setBounds(158, 130, 114, 15);
			tabRegistration.add(lblOwnerName);
			
			textVRegistrationOHeight = new JTextField();
			textVRegistrationOHeight.setEditable(false);
			textVRegistrationOHeight.setBounds(302, 147, 114, 19);
			tabRegistration.add(textVRegistrationOHeight);
			textVRegistrationOHeight.setColumns(10);
			
			JLabel lblVRegistrationOHeight = new JLabel("Height");
			lblVRegistrationOHeight.setBounds(302, 130, 70, 15);
			tabRegistration.add(lblVRegistrationOHeight);
			
			JLabel lblNewLabel = new JLabel("Owner Information:");
			lblNewLabel.setBounds(12, 104, 233, 15);
			tabRegistration.add(lblNewLabel);
			
			textField = new JTextField();
			textField.setEditable(false);
			textField.setBounds(12, 194, 114, 19);
			tabRegistration.add(textField);
			textField.setColumns(10);
			
			JLabel lblVRegistrationOWeight = new JLabel("Weight");
			lblVRegistrationOWeight.setBounds(12, 178, 70, 15);
			tabRegistration.add(lblVRegistrationOWeight);
			
			textVRegistrationOEye = new JTextField();
			textVRegistrationOEye.setEditable(false);
			textVRegistrationOEye.setBounds(158, 194, 114, 19);
			tabRegistration.add(textVRegistrationOEye);
			textVRegistrationOEye.setColumns(10);
			
			JLabel lblVRegistrationOEye = new JLabel("Eye Color");
			lblVRegistrationOEye.setBounds(158, 178, 70, 15);
			tabRegistration.add(lblVRegistrationOEye);
			
			textVRegistrationOHair = new JTextField();
			textVRegistrationOHair.setEditable(false);
			textVRegistrationOHair.setBounds(302, 194, 114, 19);
			tabRegistration.add(textVRegistrationOHair);
			textVRegistrationOHair.setColumns(10);
			
			JLabel lblVRegistrationOHair = new JLabel("Hair Color");
			lblVRegistrationOHair.setBounds(302, 178, 92, 15);
			tabRegistration.add(lblVRegistrationOHair);
			
			textVRegistrationOAddress = new JTextField();
			textVRegistrationOAddress.setEditable(false);
			textVRegistrationOAddress.setBounds(12, 241, 114, 19);
			tabRegistration.add(textVRegistrationOAddress);
			textVRegistrationOAddress.setColumns(10);
			
			JLabel lblVRegistrationOAddress = new JLabel("Address");
			lblVRegistrationOAddress.setBounds(12, 225, 70, 15);
			tabRegistration.add(lblVRegistrationOAddress);
			
			JLabel lblVRegistrationOGender = new JLabel("Gender");
			lblVRegistrationOGender.setBounds(158, 225, 70, 15);
			tabRegistration.add(lblVRegistrationOGender);
			
			JRadioButton rdbtnVRegistrationMale = new JRadioButton("M");
			rdbtnVRegistrationMale.setEnabled(false);
			rdbtnVRegistrationMale.setBounds(158, 239, 56, 23);
			tabRegistration.add(rdbtnVRegistrationMale);
			
			JRadioButton rdbtnVRegistrationFemale = new JRadioButton("F");
			rdbtnVRegistrationFemale.setEnabled(false);
			rdbtnVRegistrationFemale.setBounds(223, 239, 149, 23);
			tabRegistration.add(rdbtnVRegistrationFemale);
			
			ButtonGroup genderGroup = new ButtonGroup();
			genderGroup.add(rdbtnVRegistrationMale);
			genderGroup.add(rdbtnVRegistrationFemale);
			
			// Make a calendar picker for owner birthday
			// Retrieved from http://stackoverflow.com/questions/26794698/how-do-i-implement-jdatepicker
			// March 17
			SqlDateModel model = new SqlDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
			JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
			 
			tabRegistration.add(datePicker);
			
			JButton btnRegister = new JButton("Register");
			btnRegister.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VehicleRegistration vehicleRegistration = new VehicleRegistration("1114", "Toyota", "1999", "3", "red", "1");
					vehicleRegistration.Run();
				}
			});
			btnRegister.setBounds(158, 287, 117, 25);
			tabRegistration.add(btnRegister);
			datePicker.setBounds(305, 239, 120, 30);

		// End layout code for the Vehicle Registration Tab //
		
		JPanel tabLicenceRegistration = new JPanel();
		tabbedPane.addTab("Driver Licence Registration", null, tabLicenceRegistration, null);
		
		JPanel tabViolationRecord = new JPanel();
		tabbedPane.addTab("Violation Record", null, tabViolationRecord, null);
		
		JPanel tabSearch = new JPanel();
		tabbedPane.addTab("Search", null, tabSearch, null);
		
		JPanel tabTransaction = new JPanel();
		tabbedPane.addTab("Auto Transaction", null, tabTransaction, null);
		tabTransaction.setLayout(null);
			
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
						m_con = DriverManager.getConnection(Constants.dbstring, textUserName.getText(), pw);
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
