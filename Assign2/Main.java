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


public class Main {
	
	private static final int CREATE_POPULATE_DATABASE_OPTION = 1;
	private static final int RETRIEVE_RECORD_WITH_GIVEN_KEY_OPTION = 2;
	private static final int RETRIEVE_RECORD_WITH_GIVEN_DATA_OPTION = 3;
	private static final int RETRIEVE_RECORDS_WITH_GIVEN_RANGE_OF_KEY_OPTION = 4;
	private static final int DESTROY_DATABASE_OPTION = 5;
	private static final int QUIT_OPTION = 6;

	private static List<Integer> keys = new ArrayList<Integer>(); // Need to change this to account for string keys
	private static List<String> values = new ArrayList<String>();

	private static File outputDirectory = new File("./tmp/ahmirza_db/");

	/*
	 *  the main function
	 */
	public static void main(String[] args) {
		boolean quit = false;
		
		String BTREE_option = "1";
		String HASHT_option = "2";
		String INDEX_option = "3";

		String db_type_input = args[0];
		String db_type_option = null;
		
		if (Objects.equals(db_type_input, "btree")) {
			db_type_option = BTREE_option;
			System.out.println("BTREE option provided");
		} else if (Objects.equals(db_type_input, "hash")) {
			db_type_option = HASHT_option;
			System.out.println("HASH option provided");
		} else if (Objects.equals(db_type_input, "indexfile")) {
			db_type_option = INDEX_option;
			System.out.println("INDEX option provided");
		} else {
			System.out.println("Please provide a proper argument");
			System.exit(1);
		}

		long start;
		long end;
		long microseconds;

		MyDatabase myDatabase = new MyDatabase(db_type_option);

		Scanner in = new Scanner(System.in);
		
		while (!quit) {
			printMenu();
			
			String option = in.nextLine();
			try {
				switch(Integer.valueOf(option)){
				case CREATE_POPULATE_DATABASE_OPTION:
					myDatabase.create();
					myDatabase.populateTable();
					System.out.println("1000 records inserted into "+db_type_input);
					break;
				case RETRIEVE_RECORD_WITH_GIVEN_KEY_OPTION:
					System.out.println("Please enter the key and press enter");
					String key = in.nextLine();

					start = System.nanoTime();
					keys.add(Integer.parseInt(key));
					values.add(myDatabase.getValue(key));
					end = System.nanoTime();
					microseconds = (end-start)/1000;

					printResults(microseconds);

					keys.clear();
					values.clear();
					break;
				case RETRIEVE_RECORD_WITH_GIVEN_DATA_OPTION:
					System.out.println("Please enter the value and press enter");
					String value1 = in.nextLine();
					start = System.nanoTime();
					String key1 = myDatabase.getKey(value1);
					end = System.nanoTime();
					microseconds = (end-start)/1000;
					System.out.println(key1);
					System.out.println("It took "+microseconds+" microseconds");

					values.clear();
					break;
				case RETRIEVE_RECORDS_WITH_GIVEN_RANGE_OF_KEY_OPTION:
					System.out.println("Please enter lower limit and upper limit of keys to retrieve, seperated by space");
					String keysString[] = in.nextLine().split(" ");
					assert keysString.length == 2;
					if (Integer.parseInt(keysString[0]) > Integer.parseInt(keysString[1])) {
						System.out.println("Please ensure the lower bound is on the left, and the upper bound on the right");
					}
					// Need to change this to account for string keys
					int keyBounds[] = new int[2];
					for (int i = 0; i < 2; ++i) {
						keyBounds[i] = Integer.parseInt(keysString[i]);
					}

					start = System.nanoTime();
					for (int i = keyBounds[0]; i < keyBounds[1]; i++) {
						keys.add(i);
						values.add(myDatabase.getValue(String.valueOf(i)));
					}
					end = System.nanoTime();
					microseconds = (end-start)/1000;

					printResults(microseconds);

					keys.clear();
					values.clear();
					break;
				case DESTROY_DATABASE_OPTION:
					clearResults(outputDirectory);
					System.out.println("Database destroyed");
					break;
				case QUIT_OPTION:
					quit = true;
					clearResults(outputDirectory);
					// need to delete "answers" file
					break;
				default:
					System.out.println("That's not a valid option");
				}
			} catch (Exception e) {
				System.out.println("That's not a valid option");
			}
		}
	}

	private static void printMenu() {
		System.out.println("\n1. Create and populate a database\n"
				+ "2. Retrieve records with a given key\n"
				+ "3. Retrieve records with a given data\n"
				+ "4. Retrieve records with a given range of key values\n"
				+ "5. Destroy the database\n"
				+ "6. Quit\n");
		
	}

	// From http://stackoverflow.com/questions/7768071/how-to-delete-directory-content-in-java
	// Answered by NCode
	// Retrieved April 4 2016 by Spencer Plant
	public static void clearResults(File folder) {
		File[] files = folder.listFiles();
		if(files!=null) { //some JVMs return null for empty dirs
			for(File f: files) {
				f.delete();
			}
		}
		try {
			new File ("./answers").delete();
		} catch (Exception e) {}
	}

	public static void printResults(long time) {
		int count = values.size();

		if (count == 1) {
			System.out.println("\nThere was 1 result\n");
		} else {
			System.out.println("\nThere were "+count+" results\n");
		}
		
		for (int i = 0; i < count; ++i) {
			System.out.println("Key:  "+keys.get(i));
			System.out.println("Data: "+values.get(i)+"\n");
		}

		System.out.println("It took "+time+" microseconds");
	}
}
