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
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.AbstractAction;
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

import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.JScrollBar; // Java package for accessing Oracle
import javax.swing.JScrollPane;
import java.awt.Font;

public class GroupProject1 {

	protected JFrame frame;
	private JTextField textUserName;
	private JPasswordField textPassword;
	private JButton btnLogin = new JButton("Login");
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	

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
    private JTextField textDLRegistrationOWeight;
    private JTextField textDLRegistrationOEye;
    private JTextField textDLRegistrationOHair;
    private JTextField textDLRegistrationOAddress;
    private JTextField textVRegistrationOWeight;
    private JTextField textVRegistrationOEye;
    private JTextField textVRegistrationOHair;
    private JTextField textVRegistrationOAddress;
    private JTextField textDLRegistrationNum;
    private JTextField textDLRegistrationClass;
    private JTextField textVRegistrationSecSIN;
    private JTextField textVRegistrationSecName;
    private JTextField textVRegistrationSecHeight;
    private JTextField textVRegistrationSecWeight;
    private JTextField textVRegistrationSecEye;
    private JTextField textVRegistrationSecHair;
    private JTextField textVRegistrationSecAddr;
    private JTextField textDLRegistrationOwnerSIN;
    private JTextField textDLRegistrationOName;
    private JTextField textDLRegistrationOHeight;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textTransactionVSerial;
    private JTextField textTransactionBuyerSIN;
    private JTextField textTransactionBuyerName;
    private JTextField textTransactionBuyerHeight;
    private JTextField textTransactionBuyerWeight;
    private JTextField textTransactionBuyerEye;
    private JTextField textTransactionBuyerHair;
    private JTextField textTransactionBuyerAddr;
    private JTextField textTransactionSellerSIN;
    private JTextField textTransactionSellerName;
    private JTextField textTransactionSellerHeight;
    private JTextField textTransactionSellerWeight;
    private JTextField textTransactionSellerEye;
    private JTextField textTransactionSellerHair;
    private JTextField textTransactionSellerAddr;
    private JTextField textSearchBar;
    private JTextField textTransactionPrice;
    private JTextField textTransactionID;
    
	
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
		frame.setBounds(100, 100, 488, 642);
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
		textPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Attempt to log in
				Login();
			}
		});
		
		// Labels for username and password fields
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(12, 12, 100, 15);
		frame.getContentPane().add(lblUsername);
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(201, 12, 100, 15);
		frame.getContentPane().add(lblPassword);
		
		// Make tabs for the different application programs
		
		tabbedPane.setEnabled(false);
		tabbedPane.setBounds(12, 64, 462, 540);
		frame.getContentPane().add(tabbedPane);
		
		JPanel tabMain = new JPanel();
		tabbedPane.addTab("Home", null, tabMain, null);
		tabMain.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome to our Project 1");
		lblNewLabel.setBounds(135, 28, 189, 15);
		tabMain.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Please log in above to use our app");
		lblNewLabel_1.setBounds(104, 102, 260, 15);
		tabMain.add(lblNewLabel_1);
		
		JLabel lblSpencerPlant = new JLabel("Spencer Plant");
		lblSpencerPlant.setBounds(173, 153, 121, 15);
		tabMain.add(lblSpencerPlant);
		
		JLabel lblAliMirza = new JLabel("Ali Mirza");
		lblAliMirza.setBounds(195, 180, 111, 15);
		tabMain.add(lblAliMirza);
		
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
			
			final JSpinner spinVRegistrationTypeID = new JSpinner();
			spinVRegistrationTypeID.setModel(new SpinnerListModel(new String[] {"1, Sedan", "2, Coupe", "3, Sport", "4, Hatchback", "5, Truck", "6, Mini Van", "7, Semi", "8, SUV"}));
			spinVRegistrationTypeID.setBounds(302, 73, 114, 20);
			tabRegistration.add(spinVRegistrationTypeID);
			
			textVRegistrationOwnerSIN = new JTextField();
			textVRegistrationOwnerSIN.setBounds(12, 147, 114, 19);
			tabRegistration.add(textVRegistrationOwnerSIN);
			textVRegistrationOwnerSIN.setColumns(10);
			
			// What to do when you hit enter on primary owner SIN box
			textVRegistrationOwnerSIN.addActionListener(new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					PreparedStatement stmt;
					ResultSet rs;
					try {
						System.out.println(textVRegistrationOwnerSIN.getText());
						stmt = m_con.prepareStatement("select * from people where sin = " +  textVRegistrationOwnerSIN.getText());
						rs = stmt.executeQuery();
						if (m_con != null && rs.next()){
	            			System.out.println("found");
							textVRegistrationOName.setEditable(false);
							textVRegistrationOHeight.setEditable(false);
							textVRegistrationOWeight.setEditable(false);
							textVRegistrationOEye.setEditable(false);
							textVRegistrationOHair.setEditable(false);
							textVRegistrationOAddress.setEditable(false);

	            			
	            			textVRegistrationOName.setText(rs.getString("name"));
	            			textVRegistrationOHeight.setText(rs.getString("height"));
	            			textVRegistrationOWeight.setText(rs.getString("weight"));
	            			textVRegistrationOEye.setText(rs.getString("eyecolor"));
	            			textVRegistrationOHair.setText(rs.getString("haircolor"));
	            			textVRegistrationOAddress.setText(rs.getString("addr"));
	            			//genderGroup. = rs.getString("gender");
	            			//textVRegistrationOBirthday = rs.getString("birthday");
						// try looking for and retrieving user info
						}
						else {
							textVRegistrationOName.setEditable(true);
							textVRegistrationOHeight.setEditable(true);
							textVRegistrationOWeight.setEditable(true);
							textVRegistrationOEye.setEditable(true);
							textVRegistrationOHair.setEditable(true);
							textVRegistrationOAddress.setEditable(true);
						}
					} catch (Exception err) {
						// make text boxes fillable
					}
				}
			});
			
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
			
			JLabel lblVRegistrationOwner = new JLabel("--------Primary Owner Information--------");
			lblVRegistrationOwner.setBounds(77, 104, 343, 15);
			tabRegistration.add(lblVRegistrationOwner);
			
			textVRegistrationOWeight = new JTextField();
			textVRegistrationOWeight.setEditable(false);
			textVRegistrationOWeight.setBounds(12, 194, 114, 19);
			tabRegistration.add(textVRegistrationOWeight);
			textVRegistrationOWeight.setColumns(10);
			
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
			rdbtnVRegistrationFemale.setBounds(223, 239, 61, 23);
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
					String serial = textVRegistrationVID.getText();
					String make = textVRegistrationMake.getText();
					String model = textVRegistrationModel.getText();
					String year = textVRegistrationYear.getText();
					String color = textVRegistrationColor.getText();
					int type_id = Character.getNumericValue(spinVRegistrationTypeID.getValue().toString().charAt(0));
					Person owner = null;
					Person secondaryOwner = null;
					if (textVRegistrationOName.isEditable()) {
						owner = new Person(textVRegistrationOwnerSIN.getText(),textVRegistrationOName.getText(), textVRegistrationOHeight.getText(), textVRegistrationOWeight.getText(), textVRegistrationOEye.getText(), textVRegistrationOHair.getText(), textVRegistrationOAddress.getText(), "", "");
					}
					if (textVRegistrationSecName.isEditable()){
						secondaryOwner = new Person(textVRegistrationSecSIN.getText(),textVRegistrationSecName.getText(), textVRegistrationSecHeight.getText(), textVRegistrationSecWeight.getText(), textVRegistrationSecEye.getText(), textVRegistrationSecHair.getText(), textVRegistrationSecAddr.getText(), "", "");

					}
					VehicleRegistration vehicleRegistration = new VehicleRegistration(serial,
							make,
							model,
							year,
							color,
							type_id,
							owner,
							secondaryOwner);
					vehicleRegistration.Run();
				}
			});
			btnRegister.setBounds(167, 458, 117, 25);
			tabRegistration.add(btnRegister);
			datePicker.setBounds(305, 239, 120, 30);
			tabRegistration.add(datePicker);
			
			///********** Second owner Layout things *****************
			textVRegistrationSecSIN = new JTextField();
			textVRegistrationSecSIN.setEditable(false);
			textVRegistrationSecSIN.setColumns(10);
			textVRegistrationSecSIN.setBounds(12, 322, 114, 19);
			tabRegistration.add(textVRegistrationSecSIN);
			
			// What to do when you hit enter on secondary owner SIN box
			textVRegistrationSecSIN.addActionListener(new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					PreparedStatement stmt;
					ResultSet rs;
					try {
						System.out.println(textVRegistrationSecSIN.getText());
						stmt = m_con.prepareStatement("select * from people where sin = " +  textVRegistrationSecSIN.getText());
						rs = stmt.executeQuery();
						if (m_con != null && rs.next()){
	            			System.out.println("found");
							textVRegistrationSecName.setEditable(false);
							textVRegistrationSecHeight.setEditable(false);
							textVRegistrationSecWeight.setEditable(false);
							textVRegistrationSecEye.setEditable(false);
							textVRegistrationSecHair.setEditable(false);
							textVRegistrationSecAddr.setEditable(false);

	            			
							textVRegistrationSecName.setText(rs.getString("name"));
	            			textVRegistrationSecHeight.setText(rs.getString("height"));
	            			textVRegistrationSecWeight.setText(rs.getString("weight"));
	            			textVRegistrationSecEye.setText(rs.getString("eyecolor"));
	            			textVRegistrationSecHair.setText(rs.getString("haircolor"));
	            			textVRegistrationSecAddr.setText(rs.getString("addr"));
	            			//genderGroup. = rs.getString("gender");
	            			//textVRegistrationOBirthday = rs.getString("birthday");
						// try looking for and retrieving user info
						}
						else {
							textVRegistrationSecName.setEditable(true);
							textVRegistrationSecHeight.setEditable(true);
							textVRegistrationSecWeight.setEditable(true);
							textVRegistrationSecEye.setEditable(true);
							textVRegistrationSecHair.setEditable(true);
							textVRegistrationSecAddr.setEditable(true);
						}
					} catch (Exception err) {
						// make text boxes fillable
					}
				}
			});
			
			JLabel label = new JLabel("SIN");
			label.setBounds(12, 306, 114, 15);
			tabRegistration.add(label);
			
			JLabel lblSecondarOwnerInformation = new JLabel("--------Secondary Owner Information--------");
			lblSecondarOwnerInformation.setBounds(77, 279, 339, 15);
			tabRegistration.add(lblSecondarOwnerInformation);
			
			textVRegistrationSecName = new JTextField();
			textVRegistrationSecName.setEditable(false);
			textVRegistrationSecName.setColumns(10);
			textVRegistrationSecName.setBounds(158, 322, 114, 19);
			tabRegistration.add(textVRegistrationSecName);
			
			textVRegistrationSecHeight = new JTextField();
			textVRegistrationSecHeight.setEditable(false);
			textVRegistrationSecHeight.setColumns(10);
			textVRegistrationSecHeight.setBounds(302, 322, 114, 19);
			tabRegistration.add(textVRegistrationSecHeight);
			
			JLabel label_1 = new JLabel("Name");
			label_1.setBounds(158, 306, 114, 15);
			tabRegistration.add(label_1);
			
			JLabel label_2 = new JLabel("Height");
			label_2.setBounds(302, 306, 70, 15);
			tabRegistration.add(label_2);
			
			JLabel label_3 = new JLabel("Weight");
			label_3.setBounds(12, 353, 70, 15);
			tabRegistration.add(label_3);
			
			textVRegistrationSecWeight = new JTextField();
			textVRegistrationSecWeight.setEditable(false);
			textVRegistrationSecWeight.setColumns(10);
			textVRegistrationSecWeight.setBounds(12, 375, 114, 19);
			tabRegistration.add(textVRegistrationSecWeight);
			
			JLabel label_4 = new JLabel("Eye Color");
			label_4.setBounds(158, 353, 70, 15);
			tabRegistration.add(label_4);
			
			textVRegistrationSecEye = new JTextField();
			textVRegistrationSecEye.setEditable(false);
			textVRegistrationSecEye.setColumns(10);
			textVRegistrationSecEye.setBounds(158, 375, 114, 19);
			tabRegistration.add(textVRegistrationSecEye);
			
			JLabel label_5 = new JLabel("Hair Color");
			label_5.setBounds(302, 353, 92, 15);
			tabRegistration.add(label_5);
			
			textVRegistrationSecHair = new JTextField();
			textVRegistrationSecHair.setEditable(false);
			textVRegistrationSecHair.setColumns(10);
			textVRegistrationSecHair.setBounds(302, 375, 114, 19);
			tabRegistration.add(textVRegistrationSecHair);
			
			JLabel label_6 = new JLabel("Address");
			label_6.setBounds(12, 405, 70, 15);
			tabRegistration.add(label_6);
			
			textVRegistrationSecAddr = new JTextField();
			textVRegistrationSecAddr.setEditable(false);
			textVRegistrationSecAddr.setColumns(10);
			textVRegistrationSecAddr.setBounds(12, 427, 114, 19);
			tabRegistration.add(textVRegistrationSecAddr);
			
			JLabel label_7 = new JLabel("Gender");
			label_7.setBounds(158, 405, 70, 15);
			tabRegistration.add(label_7);
			
			JRadioButton rdbtnVRegistrationSecM = new JRadioButton("M");
			rdbtnVRegistrationSecM.setEnabled(false);
			rdbtnVRegistrationSecM.setBounds(158, 425, 56, 23);
			tabRegistration.add(rdbtnVRegistrationSecM);
			
			JRadioButton rdbtnVRegistrationSecF = new JRadioButton("F");
			rdbtnVRegistrationSecF.setEnabled(false);
			rdbtnVRegistrationSecF.setBounds(211, 425, 61, 23);
			tabRegistration.add(rdbtnVRegistrationSecF);
			
			ButtonGroup genderGroupSecond = new ButtonGroup();
			genderGroupSecond.add(rdbtnVRegistrationSecM);
			genderGroupSecond.add(rdbtnVRegistrationSecF);
			
			///**************************************************

		// End layout code for the Vehicle Registration Tab //
		
		JPanel tabLicenceRegistration = new JPanel();
		tabbedPane.addTab("Driver Licence Registration", null, tabLicenceRegistration, null);
		tabLicenceRegistration.setLayout(null);
		
		// Begin layout code for Driver's Licence Registration Tab //
			JLabel lblDLRegistrationNum = new JLabel("Licence Number");
			lblDLRegistrationNum.setBounds(12, 12, 119, 15);
			tabLicenceRegistration.add(lblDLRegistrationNum);
			
			textDLRegistrationNum = new JTextField();
			textDLRegistrationNum.setBounds(12, 28, 114, 19);
			tabLicenceRegistration.add(textDLRegistrationNum);
			textDLRegistrationNum.setColumns(10);
			
			textDLRegistrationClass = new JTextField();
			textDLRegistrationClass.setBounds(158, 28, 114, 19);
			tabLicenceRegistration.add(textDLRegistrationClass);
			textDLRegistrationClass.setColumns(10);
			
			JLabel lblDLRegistrationClass = new JLabel("Class");
			lblDLRegistrationClass.setBounds(158, 12, 70, 15);
			tabLicenceRegistration.add(lblDLRegistrationClass);
			
			// Make calendar pickers for issuing date and expiry date
			// Retrieved from http://stackoverflow.com/questions/26794698/how-do-i-implement-jdatepicker
			// March 17 - p defined above
			SqlDateModel model2 = new SqlDateModel();
			JDatePanelImpl issuingDatePanel = new JDatePanelImpl(model2, p);
			JDatePickerImpl issuingPicker = new JDatePickerImpl(issuingDatePanel, new DateLabelFormatter());
			tabLicenceRegistration.add(issuingPicker);
			issuingPicker.setBounds(12, 80, 120, 30);
			
			SqlDateModel model3 = new SqlDateModel(); // need to specify new model for each calendar, else
													  // all the calendars will match.
			JDatePanelImpl expiryDatePanel = new JDatePanelImpl(model3, p);
			JDatePickerImpl expiringPicker = new JDatePickerImpl(expiryDatePanel, new DateLabelFormatter());
			tabLicenceRegistration.add(expiringPicker);
			expiringPicker.setBounds(165, 80, 120, 30);
			
			JLabel lblIssuingDate = new JLabel("Issuing Date");
			lblIssuingDate.setBounds(12, 65, 119, 15);
			tabLicenceRegistration.add(lblIssuingDate);
			
			JLabel lblExpiryDate = new JLabel("Expiry Date");
			lblExpiryDate.setBounds(165, 65, 114, 15);
			tabLicenceRegistration.add(lblExpiryDate);
			
			
			textDLRegistrationOwnerSIN = new JTextField();
			textDLRegistrationOwnerSIN.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					PreparedStatement stmt;
					ResultSet rs;
					try {
						System.out.println(textDLRegistrationOwnerSIN.getText());
						stmt = m_con.prepareStatement("select * from people where sin = " +  textDLRegistrationOwnerSIN.getText());
						rs = stmt.executeQuery();
						if (m_con != null && rs.next()){
	            			System.out.println("found");
	            			textDLRegistrationOName.setEditable(false);
							textDLRegistrationOHeight.setEditable(false);
							textDLRegistrationOWeight.setEditable(false);
							textDLRegistrationOEye.setEditable(false);
							textDLRegistrationOHair.setEditable(false);
							textDLRegistrationOAddress.setEditable(false);

	            			
							textDLRegistrationOName.setText(rs.getString("name"));
							textDLRegistrationOHeight.setText(rs.getString("height"));
							textDLRegistrationOWeight.setText(rs.getString("weight"));
							textDLRegistrationOEye.setText(rs.getString("eyecolor"));
							textDLRegistrationOHair.setText(rs.getString("haircolor"));
							textDLRegistrationOAddress.setText(rs.getString("addr"));
	            			//genderGroup. = rs.getString("gender");
	            			//textVRegistrationOBirthday = rs.getString("birthday");
						// try looking for and retrieving user info
						}
						else {
							textDLRegistrationOName.setEditable(true);
							textDLRegistrationOHeight.setEditable(true);
							textDLRegistrationOWeight.setEditable(true);
							textDLRegistrationOEye.setEditable(true);
							textDLRegistrationOHair.setEditable(true);
							textDLRegistrationOAddress.setEditable(true);
							
							textDLRegistrationOName.setText("");
							textDLRegistrationOHeight.setText("");
							textDLRegistrationOWeight.setText("");
							textDLRegistrationOEye.setText("");
							textDLRegistrationOHair.setText("");
							textDLRegistrationOAddress.setText("");
						}
					} catch (Exception err) {
						// make text boxes fillable
					}
				}
			});
			textDLRegistrationOwnerSIN.setBounds(12, 171, 114, 19);

			tabLicenceRegistration.add(textDLRegistrationOwnerSIN);
			textDLRegistrationOwnerSIN.setColumns(10);

			JLabel lblDLRegistrationOwnerSIN = new JLabel("SIN");
			lblDLRegistrationOwnerSIN.setBounds(12, 154, 114, 15);
			tabLicenceRegistration.add(lblDLRegistrationOwnerSIN);

			textDLRegistrationOName = new JTextField();
			textDLRegistrationOName.setEditable(false);
			textDLRegistrationOName.setBounds(158, 171, 114, 19);
			tabLicenceRegistration.add(textDLRegistrationOName);
			textDLRegistrationOName.setColumns(10);

			JLabel lblOwnerNameDL = new JLabel("Name");
			lblOwnerNameDL.setBounds(158, 154, 114, 15);
			tabLicenceRegistration.add(lblOwnerNameDL);

			textDLRegistrationOHeight = new JTextField();
			textDLRegistrationOHeight.setEditable(false);
			textDLRegistrationOHeight.setBounds(302, 171, 114, 19);
			tabLicenceRegistration.add(textDLRegistrationOHeight);
			textDLRegistrationOHeight.setColumns(10);

			JLabel lblDLRegistrationOHeight = new JLabel("Height");
			lblDLRegistrationOHeight.setBounds(302, 154, 70, 15);
			tabLicenceRegistration.add(lblDLRegistrationOHeight);

			JLabel lblNewLabelDL = new JLabel("Owner Information:");
			lblNewLabelDL.setBounds(12, 128, 233, 15);
			
			tabLicenceRegistration.add(lblNewLabelDL);


			textDLRegistrationOWeight = new JTextField();
			textDLRegistrationOWeight.setEditable(false);
			textDLRegistrationOWeight.setBounds(12, 194, 114, 19);
			tabLicenceRegistration.add(textDLRegistrationOWeight);
			textDLRegistrationOWeight.setColumns(10);


			JLabel lblDLRegistrationOWeight = new JLabel("Weight");
			lblDLRegistrationOWeight.setBounds(12, 202, 70, 15);
			tabLicenceRegistration.add(lblDLRegistrationOWeight);

			textDLRegistrationOEye = new JTextField();
			textDLRegistrationOEye.setEditable(false);
			textDLRegistrationOEye.setBounds(158, 218, 114, 19);
			tabLicenceRegistration.add(textDLRegistrationOEye);
			textDLRegistrationOEye.setColumns(10);

			JLabel lblDLRegistrationOEye = new JLabel("Eye Color");
			lblDLRegistrationOEye.setBounds(158, 202, 70, 15);
			tabLicenceRegistration.add(lblDLRegistrationOEye);

			textDLRegistrationOHair = new JTextField();
			textDLRegistrationOHair.setEditable(false);
			textDLRegistrationOHair.setBounds(302, 218, 114, 19);
			tabLicenceRegistration.add(textDLRegistrationOHair);
			textDLRegistrationOHair.setColumns(10);

			JLabel lblDLRegistrationOHair = new JLabel("Hair Color");
			lblDLRegistrationOHair.setBounds(302, 202, 92, 15);
			tabLicenceRegistration.add(lblDLRegistrationOHair);

			textDLRegistrationOAddress = new JTextField();
			textDLRegistrationOAddress.setEditable(false);
			textDLRegistrationOAddress.setBounds(12, 265, 114, 19);
			tabLicenceRegistration.add(textDLRegistrationOAddress);
			textDLRegistrationOAddress.setColumns(10);

			JLabel lblDLRegistrationOAddress = new JLabel("Address");
			lblDLRegistrationOAddress.setBounds(12, 249, 70, 15);
			tabLicenceRegistration.add(lblDLRegistrationOAddress);

			JLabel lblDLRegistrationOGender = new JLabel("Gender");
			lblDLRegistrationOGender.setBounds(158, 249, 70, 15);
			tabLicenceRegistration.add(lblDLRegistrationOGender);

			JRadioButton rdbtnDLRegistrationMale = new JRadioButton("M");
			rdbtnDLRegistrationMale.setEnabled(false);
			rdbtnDLRegistrationMale.setBounds(158, 263, 56, 23);
			tabLicenceRegistration.add(rdbtnDLRegistrationMale);

			JRadioButton rdbtnDLRegistrationFemale = new JRadioButton("F");
			rdbtnDLRegistrationFemale.setEnabled(false);
			rdbtnDLRegistrationFemale.setBounds(223, 263, 61, 23);
			tabLicenceRegistration.add(rdbtnDLRegistrationFemale);

			ButtonGroup genderGroupDL = new ButtonGroup();
			genderGroupDL.add(rdbtnDLRegistrationMale);
			genderGroupDL.add(rdbtnDLRegistrationFemale);

			// Make a calendar picker for owner birthday
			// Retrieved from http://stackoverflow.com/questions/26794698/how-do-i-implement-jdatepicker
			// March 17
			SqlDateModel model4 = new SqlDateModel();
			JDatePanelImpl birthdatePanelDL = new JDatePanelImpl(model4, p);
			JDatePickerImpl datePickerDL = new JDatePickerImpl(birthdatePanelDL, new DateLabelFormatter());
			datePickerDL.setBounds(305, 239, 120, 30);
			tabLicenceRegistration.add(datePickerDL);
			
			JButton licenceRegisterButton = new JButton("Register");
			licenceRegisterButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Person owner = null;
					if (textDLRegistrationOName.isEditable()) {
						
						owner = new Person(textDLRegistrationOwnerSIN.getText(), textDLRegistrationOName.getText(), textDLRegistrationOHeight.getText(), textDLRegistrationOWeight.getText(), textDLRegistrationOEye.getText(), textDLRegistrationOHair.getText(), textDLRegistrationOAddress.getText(), "m", "11-Nov-1999");
					}
					LicenceRegistration licenceRegistration = new LicenceRegistration(textDLRegistrationNum.getText(), textDLRegistrationOwnerSIN.getText(), textDLRegistrationClass.getText(), null, "11-Nov-1999", "11-Nov-2000", owner);
					licenceRegistration.Run();
				}
			});
			licenceRegisterButton.setBounds(168, 306, 117, 25);
			tabLicenceRegistration.add(licenceRegisterButton);
		
		// End layout code for Driver's Licence Registration Tab //
		JPanel tabViolationRecord = new JPanel();
		tabbedPane.addTab("Violation Record", null, tabViolationRecord, null);
		
		JPanel tabSearch = new JPanel();
		tabbedPane.addTab("Search", null, tabSearch, null);
		tabSearch.setLayout(null);
		
			// Search Layout things
			textSearchBar = new JTextField();
			textSearchBar.setBounds(12, 36, 297, 19);
			tabSearch.add(textSearchBar);
			textSearchBar.setColumns(10);
			
			JLabel lblNewLabel_2 = new JLabel("Search");
			lblNewLabel_2.setBounds(12, 12, 70, 15);
			tabSearch.add(lblNewLabel_2);
			
			final JTextArea textSearchResult = new JTextArea();
			textSearchResult.setWrapStyleWord(true);
			textSearchResult.setEditable(false);
			textSearchResult.setBounds(12, 61, 433, 422);
			JScrollPane scrollResults = new JScrollPane(textSearchResult, 
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollResults.setSize(430, 416);
			scrollResults.setLocation(15, 67);
			tabSearch.add(scrollResults);
			
			JButton btnSearch = new JButton("Search");
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Search s = new Search(textSearchBar.getText());
					String output = s.Run();
					textSearchResult.setText(output);
				}
			});
			btnSearch.setBounds(328, 33, 117, 25);
			tabSearch.add(btnSearch);
			// End Search Layout things
			
		JPanel tabTransaction = new JPanel();
		tabbedPane.addTab("Auto Transaction", null, tabTransaction, null);
		tabTransaction.setLayout(null);
		
			textTransactionVSerial = new JTextField();
			textTransactionVSerial.setColumns(10);
			textTransactionVSerial.setBounds(12, 28, 143, 19);
			tabTransaction.add(textTransactionVSerial);
			
			JLabel label_8 = new JLabel("Vehicle Serial #");
			label_8.setBounds(12, 12, 114, 15);
			tabTransaction.add(label_8);
			
			JLabel lblPrice = new JLabel("Price:");
			lblPrice.setBounds(170, 12, 70, 15);
			tabTransaction.add(lblPrice);
			
			SqlDateModel model5 = new SqlDateModel();
			JDatePanelImpl sellDatePanel = new JDatePanelImpl(model5, p);
			final JDatePickerImpl sellDatePicker = new JDatePickerImpl(sellDatePanel, new DateLabelFormatter());
			sellDatePicker.setBounds(305, 30, 120, 30);
			tabTransaction.add(sellDatePicker);
			
			JLabel lblNewLabel_3 = new JLabel("Sell Date");
			lblNewLabel_3.setBounds(302, 12, 70, 15);
			tabTransaction.add(lblNewLabel_3);
			
			textTransactionID = new JTextField();
			textTransactionID.setBounds(12, 78, 143, 19);
			tabTransaction.add(textTransactionID);
			textTransactionID.setColumns(10);
			
			JLabel lblNewLabel_4 = new JLabel("Transaction ID");
			lblNewLabel_4.setBounds(12, 56, 143, 15);
			tabTransaction.add(lblNewLabel_4);
			
			JLabel label_14 = new JLabel("SIN");
			label_14.setBounds(12, 126, 114, 15);
			tabTransaction.add(label_14);
			
			textTransactionBuyerSIN = new JTextField();
			textTransactionBuyerSIN.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					PreparedStatement stmt;
					ResultSet rs;
					try {
						System.out.println(textTransactionBuyerSIN.getText());
						stmt = m_con.prepareStatement("select * from people where sin = " +  textTransactionBuyerSIN.getText());
						rs = stmt.executeQuery();
						if (m_con != null && rs.next()){
	            			System.out.println("found");
	            			textTransactionBuyerName.setEditable(false);
	            			textTransactionBuyerHeight.setEditable(false);
	            			textTransactionBuyerWeight.setEditable(false);
	            			textTransactionBuyerEye.setEditable(false);
	            			textTransactionBuyerHair.setEditable(false);
	            			textTransactionBuyerAddr.setEditable(false);

	            			
							textTransactionBuyerName.setText(rs.getString("name"));
							textTransactionBuyerHeight.setText(rs.getString("height"));
							textTransactionBuyerWeight.setText(rs.getString("weight"));
							textTransactionBuyerEye.setText(rs.getString("eyecolor"));
							textTransactionBuyerHair.setText(rs.getString("haircolor"));
							textTransactionBuyerAddr.setText(rs.getString("addr"));
	            			//genderGroup. = rs.getString("gender");
	            			//textVRegistrationOBirthday = rs.getString("birthday");
						// try looking for and retrieving user info
						}
						else {
							textTransactionBuyerName.setEditable(true);
							textTransactionBuyerHeight.setEditable(true);
							textTransactionBuyerWeight.setEditable(true);
							textTransactionBuyerEye.setEditable(true);
							textTransactionBuyerHair.setEditable(true);
							textTransactionBuyerAddr.setEditable(true);
							
							textTransactionBuyerName.setText("");
							textTransactionBuyerHeight.setText("");
							textTransactionBuyerWeight.setText("");
							textTransactionBuyerEye.setText("");
							textTransactionBuyerHair.setText("");
							textTransactionBuyerAddr.setText("");
						}
					} catch (Exception err) {
						// make text boxes fillable
					}
				}
			});
			textTransactionBuyerSIN.setColumns(10);
			textTransactionBuyerSIN.setBounds(12, 143, 114, 19);
			tabTransaction.add(textTransactionBuyerSIN);
			
			JLabel label_15 = new JLabel("Name");
			label_15.setBounds(158, 126, 114, 15);
			tabTransaction.add(label_15);
			
			textTransactionBuyerName = new JTextField();
			textTransactionBuyerName.setEditable(false);
			textTransactionBuyerName.setColumns(10);
			textTransactionBuyerName.setBounds(158, 143, 114, 19);
			tabTransaction.add(textTransactionBuyerName);
			
			JLabel label_16 = new JLabel("Height");
			label_16.setBounds(302, 126, 70, 15);
			tabTransaction.add(label_16);
			
			textTransactionBuyerHeight = new JTextField();
			textTransactionBuyerHeight.setEditable(false);
			textTransactionBuyerHeight.setColumns(10);
			textTransactionBuyerHeight.setBounds(302, 143, 114, 19);
			tabTransaction.add(textTransactionBuyerHeight);
			
			JLabel lblbuyerInformation = new JLabel("Buyer Information:-----------------------------------------------------");
			lblbuyerInformation.setBounds(12, 99, 404, 15);
			tabTransaction.add(lblbuyerInformation);
			
			JLabel label_18 = new JLabel("Weight");
			label_18.setBounds(12, 174, 70, 15);
			tabTransaction.add(label_18);
			
			textTransactionBuyerWeight = new JTextField();
			textTransactionBuyerWeight.setEditable(false);
			textTransactionBuyerWeight.setColumns(10);
			textTransactionBuyerWeight.setBounds(12, 190, 114, 19);
			tabTransaction.add(textTransactionBuyerWeight);
			
			JLabel label_19 = new JLabel("Eye Color");
			label_19.setBounds(158, 174, 70, 15);
			tabTransaction.add(label_19);
			
			textTransactionBuyerEye = new JTextField();
			textTransactionBuyerEye.setEditable(false);
			textTransactionBuyerEye.setColumns(10);
			textTransactionBuyerEye.setBounds(158, 190, 114, 19);
			tabTransaction.add(textTransactionBuyerEye);
			
			JLabel label_20 = new JLabel("Hair Color");
			label_20.setBounds(302, 174, 92, 15);
			tabTransaction.add(label_20);
			
			textTransactionBuyerHair = new JTextField();
			textTransactionBuyerHair.setEditable(false);
			textTransactionBuyerHair.setColumns(10);
			textTransactionBuyerHair.setBounds(302, 190, 114, 19);
			tabTransaction.add(textTransactionBuyerHair);
			
			JRadioButton rdbtnTransactionBuyerF = new JRadioButton("F");
			rdbtnTransactionBuyerF.setEnabled(false);
			rdbtnTransactionBuyerF.setBounds(223, 235, 61, 23);
			tabTransaction.add(rdbtnTransactionBuyerF);
			
			JRadioButton rdbtnTransactionBuyerM = new JRadioButton("M");
			rdbtnTransactionBuyerM.setEnabled(false);
			rdbtnTransactionBuyerM.setBounds(158, 235, 56, 23);
			tabTransaction.add(rdbtnTransactionBuyerM);
			
			JLabel label_21 = new JLabel("Gender");
			label_21.setBounds(158, 221, 70, 15);
			tabTransaction.add(label_21);
			
			textTransactionBuyerAddr = new JTextField();
			textTransactionBuyerAddr.setEditable(false);
			textTransactionBuyerAddr.setColumns(10);
			textTransactionBuyerAddr.setBounds(12, 237, 114, 19);
			tabTransaction.add(textTransactionBuyerAddr);
			
			JLabel label_22 = new JLabel("Address");
			label_22.setBounds(12, 221, 70, 15);
			tabTransaction.add(label_22);
			
			JLabel lblsellerInformation = new JLabel("Seller Information:-----------------------------------------------------");
			lblsellerInformation.setFont(new Font("Dialog", Font.BOLD, 12));
			lblsellerInformation.setBounds(12, 268, 404, 15);
			tabTransaction.add(lblsellerInformation);
			
			JLabel label_23 = new JLabel("SIN");
			label_23.setBounds(12, 295, 114, 15);
			tabTransaction.add(label_23);
			
			textTransactionSellerSIN = new JTextField();
			textTransactionSellerSIN.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					PreparedStatement stmt;
					ResultSet rs;
					try {
						System.out.println(textTransactionSellerSIN.getText());
						stmt = m_con.prepareStatement("select * from people where sin = " +  textTransactionSellerSIN.getText());
						rs = stmt.executeQuery();
						if (m_con != null && rs.next()){
	            			System.out.println("found");
	            			textTransactionSellerName.setEditable(false);
	            			textTransactionSellerHeight.setEditable(false);
	            			textTransactionSellerWeight.setEditable(false);
	            			textTransactionSellerEye.setEditable(false);
	            			textTransactionSellerHair.setEditable(false);
	            			textTransactionSellerAddr.setEditable(false);

	            			
							textTransactionSellerName.setText(rs.getString("name"));
							textTransactionSellerHeight.setText(rs.getString("height"));
							textTransactionSellerWeight.setText(rs.getString("weight"));
							textTransactionSellerEye.setText(rs.getString("eyecolor"));
							textTransactionSellerHair.setText(rs.getString("haircolor"));
							textTransactionSellerAddr.setText(rs.getString("addr"));
	            			//genderGroup. = rs.getString("gender");
	            			//textVRegistrationOBirthday = rs.getString("birthday");
						// try looking for and retrieving user info
						}
						else {
							textTransactionSellerName.setEditable(true);
							textTransactionSellerHeight.setEditable(true);
							textTransactionSellerWeight.setEditable(true);
							textTransactionSellerEye.setEditable(true);
							textTransactionSellerHair.setEditable(true);
							textTransactionSellerAddr.setEditable(true);
							
							textTransactionSellerName.setText("");
							textTransactionSellerHeight.setText("");
							textTransactionSellerWeight.setText("");
							textTransactionSellerEye.setText("");
							textTransactionSellerHair.setText("");
							textTransactionSellerAddr.setText("");
						}
					} catch (Exception err) {
						// make text boxes fillablesad
					}
				}
				
			});
			textTransactionSellerSIN.setColumns(10);
			textTransactionSellerSIN.setBounds(12, 311, 114, 19);
			tabTransaction.add(textTransactionSellerSIN);
			
			JLabel label_24 = new JLabel("Name");
			label_24.setBounds(158, 295, 114, 15);
			tabTransaction.add(label_24);
			
			textTransactionSellerName = new JTextField();
			textTransactionSellerName.setEditable(false);
			textTransactionSellerName.setColumns(10);
			textTransactionSellerName.setBounds(158, 311, 114, 19);
			tabTransaction.add(textTransactionSellerName);
			
			JLabel label_25 = new JLabel("Height");
			label_25.setBounds(302, 295, 70, 15);
			tabTransaction.add(label_25);
			
			textTransactionSellerHeight = new JTextField();
			textTransactionSellerHeight.setEditable(false);
			textTransactionSellerHeight.setColumns(10);
			textTransactionSellerHeight.setBounds(302, 311, 114, 19);
			tabTransaction.add(textTransactionSellerHeight);
			
			JLabel label_26 = new JLabel("Weight");
			label_26.setBounds(12, 342, 70, 15);
			tabTransaction.add(label_26);
			
			textTransactionSellerWeight = new JTextField();
			textTransactionSellerWeight.setEditable(false);
			textTransactionSellerWeight.setColumns(10);
			textTransactionSellerWeight.setBounds(12, 364, 114, 19);
			tabTransaction.add(textTransactionSellerWeight);
			
			JLabel label_27 = new JLabel("Eye Color");
			label_27.setBounds(158, 342, 70, 15);
			tabTransaction.add(label_27);
			
			textTransactionSellerEye = new JTextField();
			textTransactionSellerEye.setEditable(false);
			textTransactionSellerEye.setColumns(10);
			textTransactionSellerEye.setBounds(158, 364, 114, 19);
			tabTransaction.add(textTransactionSellerEye);
			
			JLabel label_28 = new JLabel("Hair Color");
			label_28.setBounds(302, 342, 92, 15);
			tabTransaction.add(label_28);
			
			textTransactionSellerHair = new JTextField();
			textTransactionSellerHair.setEditable(false);
			textTransactionSellerHair.setColumns(10);
			textTransactionSellerHair.setBounds(302, 364, 114, 19);
			tabTransaction.add(textTransactionSellerHair);
			
			JLabel label_29 = new JLabel("Address");
			label_29.setBounds(12, 394, 70, 15);
			tabTransaction.add(label_29);
			
			textTransactionSellerAddr = new JTextField();
			textTransactionSellerAddr.setEditable(false);
			textTransactionSellerAddr.setColumns(10);
			textTransactionSellerAddr.setBounds(12, 416, 114, 19);
			tabTransaction.add(textTransactionSellerAddr);
			
			JLabel label_30 = new JLabel("Gender");
			label_30.setBounds(158, 394, 70, 15);
			tabTransaction.add(label_30);
			
			JRadioButton textTransactionSellerM = new JRadioButton("M");
			textTransactionSellerM.setEnabled(false);
			textTransactionSellerM.setBounds(158, 414, 56, 23);
			tabTransaction.add(textTransactionSellerM);
			
			JRadioButton textTransactionSellerF = new JRadioButton("F");
			textTransactionSellerF.setEnabled(false);
			textTransactionSellerF.setBounds(211, 414, 61, 23);
			tabTransaction.add(textTransactionSellerF);
			
			textTransactionPrice = new JTextField();
			textTransactionPrice.setBounds(170, 28, 114, 19);
			tabTransaction.add(textTransactionPrice);
			textTransactionPrice.setColumns(10);
			
			// On complete transaction button click:
			JButton btnAutoTransComplete = new JButton("Complete Transaction");
			btnAutoTransComplete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String transaction_id = textTransactionID.getText();
					String seller_id = textTransactionSellerSIN.getText();
					String buyer_id = textTransactionBuyerSIN.getText();
					String vehicle_id = textTransactionVSerial.getText();
					String sellDate = sellDatePicker.getJFormattedTextField().getText();
					float price;
					try {
						price = Float.parseFloat(textTransactionPrice.getText());
					} catch (NumberFormatException exc) {
						price = 0;
					}
					AutoTransaction at = new AutoTransaction(transaction_id, buyer_id, seller_id, vehicle_id, sellDate, price);
					int result = at.MakeTransaction();
					if (result==1) {
						JOptionPane.showMessageDialog(frame, "Transaction completed.");
					}
				}
			});
			btnAutoTransComplete.setBounds(128, 458, 203, 25);
			tabTransaction.add(btnAutoTransComplete);
		
		// Login to SQLPlus server on click of Login Button
		// Login button turns into logout button once successfully logged in
	    // Logout closes connection to SQLPlus
		final JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Attempt to log in
				Login();
			}
		});
		btnLogin.setBounds(388, 33, 86, 19);
		frame.getContentPane().add(btnLogin);
	}
	
	public void Login() {
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
				tabbedPane.setEnabled(true);
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
				// shouldn't be reached, unless connection has already closed
			}
			// Move to 'main' tab
			tabbedPane.setSelectedIndex(0);
			tabbedPane.setEnabled(false);
			loggedIn = false;
			btnLogin.setText("Login");
			Color white = new Color(255,255,255);
			textUserName.setBackground(white);
			textPassword.setBackground(white);
			textUserName.setText("");
			textPassword.setText("");
		}
	}
}
