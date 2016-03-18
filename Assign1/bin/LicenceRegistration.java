package bin;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class LicenceRegistration {

		
		private String licence_no;
		private String sin;
		private String licence_class;
		private String photo;
		private String issue_date;
		private String expiry_date;
		private Person owner;
		
		public LicenceRegistration(String licence_no, String sin, String licence_class, String photo, String issue_date, String expiry_date, Person owner) {
			this.licence_no = licence_no;
			this.sin = sin;
			this.licence_class = licence_class;
			this.photo = photo;
			this.issue_date = issue_date;
			this.expiry_date = expiry_date;
			this.owner = owner;
		}
		
		
		public void Run() {
			try {
	        	if (GroupProject1.m_con != null) {
	        		// Create a statement object.
	    	      	// Changed to reflect changes made in the result set and to make these changes permanent to the database too
	        		Statement stmt = GroupProject1.m_con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	        		if (owner != null) {
	        			//Register new owner
	        			String insertStatement = "insert into people values ('" + owner.getSin() + "', '" + owner.getName() + "', " + owner.getHeight() + "," + owner.getWeight() + ", '" + owner.getEyecolor() + "', '" + owner.getHaircolor() + "', '" + owner.getAddr() + "' , '" + "m" + "', '" + "11-Nov-1999" + "')";
	        			System.out.println("helloooooo");
	        			stmt.executeUpdate(insertStatement);
	        		}
	        		if (!licence_no.equals("")){
	        			System.out.println(licence_no);
			        	String insertStatement = "insert into drive_licence values ('" + licence_no+ "', '" +sin+"', '"+ licence_class+"', "+photo+", '"+ issue_date +"', '"+expiry_date+"')";
	        			System.out.println("helloo");
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
