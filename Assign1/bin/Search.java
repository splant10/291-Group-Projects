// Class to handle the search tab functionality
// March 17 - Spencer Plant

package bin;

import java.sql.*;

import javax.swing.JOptionPane;

public class Search {
	private String searchText;
	private String query;
	private String prependMessage;
	private String searchResult;
	
	private Statement stmt;

	public Search(String searchText) {
		super();
		this.searchText = searchText;
	}
	
	// This method takes the search argument, tries to determine what it is asking for,
	// then goes to the database to retrieve all relevant information. Returns a string of information.
	public String Run() {
		searchResult = "";
		try {
			stmt = GroupProject1.m_con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (Exception e) {
			return "error establishing connection.";
		}
		
		// Do regex on search string to determine what kind of search to make
		if (searchText.matches("^[0-9]{6}[-][0-9]{3}$")) {
			// We're dealing with a licence number (format '123456-789')
			prependMessage = "Searching Database for Licence Number: "+searchText+"\n";
			// Get licence_no, addr, birthday, driving class, driving_condition, and the expiring_date of a driver
			// Get violation records received by a person too

			query = "SELECT dl.licence_no, dl.class, dl.expiring_date, p.addr, p.birthday " +
					"FROM drive_licence dl, people p " +
					"WHERE dl.licence_no='"+searchText+"' AND "+
					"p.sin=dl.sin";
			try {
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					searchResult += "Driver's Licence No: "+rs.getString("licence_no")+"\n";
					searchResult += "Driver's Licence Class: "+rs.getString("class")+"\n";
					searchResult += "Driver's Licence Expiry: "+rs.getString("expiring_date")+"\n";
					searchResult += "Address: "+rs.getString("addr")+"\n";
					searchResult += "Birthday: "+rs.getString("birthday")+"\n";
					searchResult += "==========================="+"\n";
				}
			}
			catch (Exception e) {
				System.out.println(e);
			}
			
			return prependMessage + searchResult;
		}
		
		else if (searchText.matches("^[a-zA-Z]{1,40}$")) {
			// We're dealing with a given name
			prependMessage = "Searching Database for Given name: "+searchText+"\n";
			
			// Get licence_no, addr, birthday, driving class, ?driving_condition?, and the expiring_date of a driver
			query = "SELECT dl.licence_no, dl.class, dl.expiring_date, p.addr, p.birthday " +
					"FROM drive_licence dl, people p " +
					"WHERE p.name LIKE '"+searchText+"%' AND "+
					"p.sin=dl.sin";
			try {
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					searchResult += "Driver's Licence No: "+rs.getString("licence_no")+"\n";
					searchResult += "Driver's Licence Class: "+rs.getString("class")+"\n";
					searchResult += "Driver's Licence Expiry: "+rs.getString("expiring_date")+"\n";
					searchResult += "Address: "+rs.getString("addr")+"\n";
					searchResult += "Birthday: "+rs.getString("birthday")+"\n";
					searchResult += "==========================="+"\n";
				}
			}
			catch (Exception e) {
				System.out.println(e);
			}
			return prependMessage + searchResult;
		}
		
		else if (searchText.matches("^[0-9]{3}[-][0-9]{3}[-][0-9]{3}$")) {
			// Dealing with a SIN (format 123-456-789)
			prependMessage = "Searching Database for SIN: "+searchText+"\n";
			// Get sin in format the DB looks for
			String sin = searchText.substring(0, 3)+searchText.substring(4, 7)+searchText.substring(8);
			// Get violation records received by the person
			query = "select ";
			
			searchResult = prependMessage + "";
		}
		
		else if (searchText.matches("^[0-9]{1,15}$")) {
			// Dealing with serial number of a vehicle 
			// (format any string of numbers with length 1 to length 15)
			prependMessage = "Searching Database for Vehicle Serial Number: "+searchText;
			
			// Get vehicle_history, including the ?number of times that a vehicle has been changed 
			// hand?, the average price, and the number of violations it has been involved in
			/*
			 * SELECT v.serial_no, AVG(as.price), COUNT(t.vehicle_id)
			 * FROM vehicle v, auto_sale as, ticket t
			 * WHERE v.serial_no  = searchText AND
			 *       as.serial_no = v.serial_no AND
			 *       t.serial_no  = v.serial_no
			 * GROUP BY (t.vehicle_id);
			 * ????
			 */
			query = "select ";
			
			searchResult = prependMessage + "";
		}
		
		else {
			// That search was totes bogus, brah
			return "That search wasn't in an expected form";
		}
		
		return searchResult;
	}
}
