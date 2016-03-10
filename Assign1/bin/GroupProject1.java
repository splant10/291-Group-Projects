package bin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class GroupProject1 {

	private JFrame frame;
	private JTextField textUserName;
	private JPasswordField textPassword;
	private JTextField textOwner;
	private JTextField textSeller;
	private JTextField textDate;
	private JTextField textPrice;

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
		
		textUserName = new JTextField();
		textUserName.setBounds(12, 33, 221, 19);
		frame.getContentPane().add(textUserName);
		textUserName.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(12, 12, 100, 15);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(245, 12, 100, 15);
		frame.getContentPane().add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(245, 33, 229, 19);
		frame.getContentPane().add(textPassword);
		
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
		
		textOwner = new JTextField();
		textOwner.setBounds(12, 27, 209, 19);
		tabTransaction.add(textOwner);
		textOwner.setColumns(10);
		
		textSeller = new JTextField();
		textSeller.setBounds(233, 27, 212, 19);
		tabTransaction.add(textSeller);
		textSeller.setColumns(10);
		
		JLabel lblBuyer = new JLabel("Buyer");
		lblBuyer.setBounds(12, 12, 70, 15);
		tabTransaction.add(lblBuyer);
		
		JLabel lblSeller = new JLabel("Seller");
		lblSeller.setBounds(235, 12, 70, 15);
		tabTransaction.add(lblSeller);
		
		textDate = new JTextField();
		textDate.setBounds(12, 79, 209, 19);
		tabTransaction.add(textDate);
		textDate.setColumns(10);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(12, 64, 70, 15);
		tabTransaction.add(lblDate);
		
		textPrice = new JTextField();
		textPrice.setBounds(12, 129, 114, 19);
		tabTransaction.add(textPrice);
		textPrice.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(12, 110, 70, 15);
		tabTransaction.add(lblPrice);
		
		JButton btnCompleteTransaction = new JButton("Complete Transaction");
		btnCompleteTransaction.setBounds(106, 221, 233, 61);
		tabTransaction.add(btnCompleteTransaction);
	}
}
