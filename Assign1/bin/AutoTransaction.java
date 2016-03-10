/* Note: Java has the auto commit feature. Thus, all changes made are final and can be seen in the DB instantly.
 * javac CreateToffees.java
 * java CreateToffees
 * 
 * Based off of Java/SQL tutorial from CMPUT 291 Lab by Kriti Khare
 * 9 March 2016
 * Spencer Plant
 */

package bin;

import java.util.*;
import java.sql.*; // Java package for accessing Oracle
import java.io.*; // Java package includes Console for getting password from user and printing to screen

public class AutoTransaction {
	private String Buyer;
	private String Seller;
	private String Date;
	private String Price;
	
	public AutoTransaction(String buyer, String seller, String date, String price) {
		super();
		Buyer = buyer;
		Seller = seller;
		Date = date;
		Price = price;
	}
	
	public void MakeTransaction() {
		// Do Java/SQL stuff here.
		
	}
}
