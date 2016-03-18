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

	public Search(String searchText) {
		super();
		this.searchText = searchText;
	}
	
	// This method takes the search argument, tries to determine what it is asking for,
	// then goes to the database to retrieve all relevant information. Returns a string of information.
	public String Run() {
		// Do regex on search string to determine what kind of search to make
		if (searchText.matches("^[0-9]{6}[-][0-9]{3}$")) {
			// We're dealing with a licence number (format '123456-789')
			prependMessage = "Searching Database for Licence Number: "+searchText;
			
			// Get licence_no, addr, birthday, driving class, driving_condition, and the expiring_date of a driver
			// Get violation records received by a person too
			/*
			 * SELECT dl.licence_no, dl.class, dl.expiring_date,
			 * 		  p.addr, p.birthday, ?t.v_type?
			 * FROM   drive_licence dl, people p, ticket t
			 * WHERE  dl.licence_no=searchText AND
			 * 		  dl.sin=p.sin AND
			 * 		  p.sin=t.violator_no
			 */
			query = "select "; 
			
			searchResult = prependMessage + "";
		}
		
		else if (searchText.matches("^[a-zA-Z]{1,40}$")) {
			// We're dealing with a given name
			prependMessage = "Searching Database for Given name: "+searchText;
			
			// Get licence_no, addr, birthday, driving class, ?driving_condition?, and the expiring_date of a driver
			/*
			 * SELECT dl.licence_no, dl.class, dl.expiring_date,
			 * 		  p.addr, p.birthday
			 * FROM   drive_licence dl, people p
			 * WHERE  p.name LIKE searchText+'%' AND
			 * 		  p.sin=dl.sin
			 */
			query = "select "; 
			
			searchResult = prependMessage + "";
		}
		
		else if (searchText.matches("^[0-9]{3}[-][0-9]{3}[-][0-9]{3}$")) {
			// Dealing with a SIN (format 123-456-789)
			prependMessage = "Searching Database for SIN: "+searchText;
			
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
