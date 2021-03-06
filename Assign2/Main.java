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

	private static List<String> values = new ArrayList<String>();
	private static List<KeyValue> results = new ArrayList<KeyValue>();

	private static File outputDirectory = new File("./tmp/ahmirza_db/");

	private static Writer writer;

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
					String retVal = myDatabase.getValue(key);
					if (retVal != null) {
						KeyValue kv = new KeyValue();
						kv.AddKey(key);
						kv.AddValue(retVal);
						results.add(kv);
					}
					end = System.nanoTime();
					
					microseconds = (end-start)/1000;
					printResults(microseconds); // print, checking to see if any values were found

					results.clear();
					break;
				case RETRIEVE_RECORD_WITH_GIVEN_DATA_OPTION:
					System.out.println("Please enter the value and press enter");
					String valueToSearch = in.nextLine();
					List<String> retKeys = new ArrayList<String>();

					start = System.nanoTime();
					retKeys = myDatabase.getKeys(valueToSearch);
					end = System.nanoTime();
					if (retKeys != null) {
						KeyValue kv = new KeyValue();
						kv.keys = retKeys;
						kv.AddValue(valueToSearch);
						results.add(kv);						
					}

					microseconds = (end-start)/1000;
					printResults(microseconds);

					break;
				case RETRIEVE_RECORDS_WITH_GIVEN_RANGE_OF_KEY_OPTION:
					System.out.println("Please enter lower limit and upper limit of keys to retrieve, seperated by space");
					String keysString[] = in.nextLine().split(" ");
					assert keysString.length == 2;

					// start progressive range search based on keys
					start = System.nanoTime();
					for (int i = 0; i < 2; ++i) {
						results = myDatabase.getRangeBetweenKeys(keysString[0], keysString[1]);
					}
					end = System.nanoTime();
					microseconds = (end-start)/1000;

					printResults(microseconds);

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
				System.out.println("That is not a valid option");
				e.printStackTrace();
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
		int count = results.size();

		if (count == 0) {
			System.out.println("\nThere were no results");
		} else if (count == 1) {
			System.out.println("\nThere was 1 result:");
		} else {
			System.out.println("\nThere were "+count+" results:");
		}
		/*
		for (int i = 0; i < count; ++i) { 									// for each result
			System.out.println("\n");							
			for (int m = 0; m < results.get(i).keys.size(); ++m) { 			// for each key in a result
				System.out.println("Key:  "+results.get(i).keys.get(m));
				for (int j = 0; j < results.get(i).values.size(); ++j) { 	// for each value corresponding to that key
					System.out.println("Data: "+results.get(i).values.get(j));
				}
			}
		}
		*/
		System.out.println("\nIt took "+time+" microseconds");
	}

	private static void printToFile(String key, String data) {
		if (writer == null) {
			try {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./answers"), "utf-8"));
			} catch (Exception e) {
				writer = null;
			}
		}
		
		try {
			writer.write("Key:  "+key+"\n");
			writer.write("Data: "+data+"\n\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}