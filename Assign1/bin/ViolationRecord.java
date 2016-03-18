package bin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViolationRecord {
	private String ticket_no;
	private String violator_no;
	private String vehicle_id;
	private String office_no;
	private String vtype;
	private String vdate;
	private String place;
	private String description;

	public ViolationRecord(String ticket_no, String violator_no, String vehicle_id, String office_no, String vtype, String vdate,
			String place, String description) {
		this.ticket_no = ticket_no;
		this.violator_no = violator_no;
		this.vehicle_id = vehicle_id;
		this.office_no = office_no;
		this.vtype = vtype;
		this.vdate = vdate;
		this.place = place;
		this.description = description;
	}
	
	public void Run() {
		try {
        	if (GroupProject1.m_con != null) {
        		// Create a statement object.
    	      	// Changed to reflect changes made in the result set and to make these changes permanent to the database too
        		Statement stmt = GroupProject1.m_con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        		if (violator_no.equals("")) {
        			//find primary owner
        			PreparedStatement query = GroupProject1.m_con.prepareStatement("select * from owner where is_primary_owner = 'y' and vehicle_id = '" +  vehicle_id  + "'"); 
        			ResultSet rs = query.executeQuery();
        			if (rs.next()) {
        				violator_no = rs.getString("owner_id");
        			}else {
        				System.out.println("no primary owner found for this vehicle id");
        			}
        		}
        		if (!ticket_no.equals("")){
        			System.out.println(ticket_no);
		        	String insertStatement = "insert into ticket values (" + ticket_no + ", '" + violator_no + "', '"+ vehicle_id + "','" + office_no +"', '"+ vtype +"', '"+vdate+ "', '"+place+"', '"+description+"')";
            		stmt.executeUpdate(insertStatement);
        		} else {
        			throw new Exception();
        		}
        		
        		
        		
        	}
        } catch( Exception ex ) { 
            System.out.println( ex.getMessage());
            //JOptionPane.showMessageDialog(frame, "Error. Check your licence registration info!");
        }
	}

}
