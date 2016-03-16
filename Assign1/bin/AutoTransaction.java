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

// 
public class AutoTransaction extends GroupProject1 {
	private String BuyerSIN;
	private String SellerSIN;
	private String Date;
	private String Price;
	
	public AutoTransaction(String buyerSin, String sellerSin, String date, String price) {
		super();
		BuyerSIN = buyerSin;
		SellerSIN = sellerSin;
		Date = date;
		Price = price;
	}
	
	public void MakeTransaction() {
		// Do Java/SQL stuff here.
		
	}
}
