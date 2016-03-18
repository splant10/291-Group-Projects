// Class to handle the search tab functionality
// March 17 - Spencer Plant

package bin;

import java.sql.*;

public class Search {
	private String searchText;

	public Search(String searchText) {
		super();
		this.searchText = searchText;
	}
	
	public String Run() {
		// Do regex on search string to determine what kind of search to make
		// Check to see if licence number in the format '123456-789'
		if (searchText.matches("^[0-9]{6}[-][0-9]{3}$")) {
			// We're dealing with a licence number
		}
		else if (searchText.matches("^[a-zA-Z]{1,40}$")) {
			// We're dealing with a given name
			
		}
		else if (searchText.matches("^[0-9]{9}$|^[0-9]{3}[-][0-9]{3}[-][0-9]{3}$")) {
			// Dealing with a SIN (format either 123456789 or 123-456-789)
		}
		else if (searchText.matches("^[0-9]{1,15}$")) {
			// Dealing with serial number of a vehicle 
			// (format any string of numbers with length 1 to length 15)
		}
		else {
			// That search was totes bogus, brah
			
		}
		
		// return searchResult
	}
}
