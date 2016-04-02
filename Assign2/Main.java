/*
 *  A sample program to create an Database, and then 
 *  populate the db with 1000 records, using Berkeley DB
 *  
 *  Author: Prof. Li-Yan Yuan, University of Alberta
 * 
 *  A directory named "/tmp/my_db" must be created before testing this program.
 *  You may replace my_db with user_db, where user is your user name, 
 *  as required.
 * 
 *  Modified on March 30, 2007 for Berkeley DB 4.3.28
 *
 *  Modified on April 2, 2016 for Project 2 of CMPUT 291 by Spencer Plant
 */
import com.sleepycat.db.*;
import java.io.*;
import java.util.*;


public class Main{
	
	private static final int CREATE_POPULATE_DATABASE_OPTION = 1;
	private static final int RETRIEVE_RECORD_WITH_GIVEN_KEY_OPTION = 2;
	private static final int RETRIEVE_RECORD_WITH_GIVEN_DATA_OPTION = 3;
	private static final int RETRIEVE_RECORDS_WITH_GIVEN_RANGE_OF_KEY_OPTION = 4;
	private static final int DESTROY_DATABASE_OPTION = 5;
	private static final int QUIT_OPTION = 6;

	/*
	 *  the main function
	 */
	public static void main(String[] args) {
		
		while(true) {
			//TO-DO: read the db_type_option from command line
			String db_type_option = "1";

			MyDatabase myDatabase = new MyDatabase(db_type_option);

			Scanner in = new Scanner(System.in);
			
			printMenu();
		
			String option = in.nextLine();
			
			switch(Integer.valueOf(option)){

			case CREATE_POPULATE_DATABASE_OPTION:
				myDatabase.create();
				break;
			case RETRIEVE_RECORD_WITH_GIVEN_KEY_OPTION:
				System.out.println("Please enter the key and press enter");
				String key = in.nextLine();
				String value = myDatabase.getValue(key);
				System.out.println(value);
				break;
			case RETRIEVE_RECORD_WITH_GIVEN_DATA_OPTION:
				System.out.println("Please enter the value and press enter");
				String value1 = in.nextLine();
				String key1 = myDatabase.getKey(value1);
				System.out.println(key1);
				break;
			case RETRIEVE_RECORDS_WITH_GIVEN_RANGE_OF_KEY_OPTION:
				System.out.println("Please enter a list of keys to retrieve seperated by space");
				String keys[] = in.nextLine().split(" ");
				String values[] = new String[keys.length];
				
				for (int i = 0; i < keys.length; i++){
					values[i] = myDatabase.getKey(keys[i]);
					System.out.println("key: " +  keys[i] + " value: " + values[i]);
				}

				break;
			case DESTROY_DATABASE_OPTION:
				break;
			case QUIT_OPTION:
				System.exit(1);
				break;
			}
		}
	}

	private static void printMenu() {
		System.out.println("\n1 Create and populate a database\n"
				+ "2 Retrieve records with a given key\n"
				+ "3 Retrieve records with a given data\n"
				+ "4. Retrieve records with a given range of key values\n"
				+ "5. Destroy the database\n"
				+ "6. Quit\n");
		
	}
}
