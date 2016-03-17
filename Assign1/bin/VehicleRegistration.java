// Class for vehicle registrations - we get a VehicleRegistration object
// Spencer Plant, Ali Mirza
// March 15 2016

package bin;

import java.sql.*;

public class VehicleRegistration extends GroupProject1 {
	public  String serial_no;
	private String maker;
	private String model;
	private String year;
	private String color;
	private String type_id;
	
	public VehicleRegistration(String serial_no, String maker, String model, String year, String color, String type_id){
		this.serial_no = serial_no;
		this.maker = maker;
		this.model = model;
		this.year = year;
		this.color = color;
		this.type_id = type_id;
	}
	
	public void Run() {
		// Do Java/SQL stuff here.
		
	//  change the following parameters to connect to other databases

       try {
        	if (GroupProject1.m_con != null) {
        		PreparedStatement stmt = GroupProject1.m_con.prepareStatement("insert into vehicle values ( '" + serial_no + "', '" + maker + "', '" + model + "', '" + year + "', '" + color + "', " + type_id + ")" );

                  // execute the insert statement
                  stmt.executeUpdate();
                  System.out.println( "the execution succeeds");
                  //GroupProject1.m_con.commit();
                  //stmt.clearParameters();
                // Set the first parameter 
        	}
        } catch( Exception ex ) { 
            System.out.println( ex.getMessage());
        }
	}
	
}
