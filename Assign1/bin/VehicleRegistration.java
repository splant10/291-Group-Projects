// Class for vehicle registrations - we get a VehicleRegistration object
// Spencer Plant, Ali Mirza
// March 15 2016

package bin;

import java.sql.*;

import javax.swing.JOptionPane;

public class VehicleRegistration extends GroupProject1 {
	private  String serial_no;
	private String maker;
	private String model;
	private String year;
	private String color;
	private int type_id;
	
	public VehicleRegistration(String serial_no, String maker, String model, String year, String color, int type_id){
		this.serial_no = serial_no;
		this.maker = maker;
		this.model = model;
		this.year = year;
		this.color = color;
		this.type_id = type_id;
	}
	
	public void Run() {
		// Do Java/SQL stuff here.
       try {
        	if (GroupProject1.m_con != null) {
        		// Create a statement object.
    	      	// Changed to reflect changes made in the result set and to make these changes permanent to the database too
        		Statement stmt = m_con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        		
        		if (!serial_no.equals("")){
        			String insertStatement = "insert into vehicle values ( '" 
		        		+ serial_no + "', '" 
		        		+ maker + "', '" 
		        		+ model + "', '" 
		        		+ year + "', '" 
		        		+ color + "', " 
		        		+ type_id + ")";
        			
            		// stmt = GroupProject1.m_con.prepareStatement(insertStatement);
            		stmt.executeUpdate(insertStatement);
        		} else {
        			throw new Exception();
        		}
        	}
        } catch( Exception ex ) { 
            System.out.println( ex.getMessage());
            JOptionPane.showMessageDialog(frame, "Error. Check your vehicle registration info!");
        }
       
	}
	
}
