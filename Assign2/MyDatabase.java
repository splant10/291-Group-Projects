import java.io.FileNotFoundException;
import java.util.Random;


import com.sleepycat.db.*;

public class MyDatabase {
	// to specify the file name for the table
	private static final String btreeLoc = "tmp/ahmirza_db/DB_BTREE";
	private static final String hashLoc = "tmp/ahmirza_db/DB_HASH";
	// number of records to populate
	private static final int NO_RECORDS = 1000;
	
	private DatabaseConfig dbConfig;
	private Database BTREE;
	private Database HASH;
	
	public MyDatabase(String db_type_option) {
		dbConfig = new DatabaseConfig();
		setDatabaseType(db_type_option);
	}
	
	private void setDatabaseType(String db_type_option) {
		if (db_type_option == null || (Integer.valueOf(db_type_option) < 0 && Integer.valueOf(db_type_option) > 4)) {
			System.out.println("Please enter correct number of argument " + db_type_option);
		}
		
		switch (Integer.valueOf(db_type_option)) {
			case 1:
				dbConfig.setType(DatabaseType.BTREE);
				break;
			case 2:
				dbConfig.setType(DatabaseType.HASH);
				break;
			case 3:
				break;
		}
		
		try {
			//if (db_type_option.equals("1")) {
				BTREE = new Database(btreeLoc, null, dbConfig);
				System.out.println(btreeLoc + " has been created");
			//} else {
			//	BTREE = new Database(hashLoc, null, dbConfig);
			//	System.out.println(hashLoc + " has been created");
			//}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}



	public void create() {
		dbConfig.setAllowCreate(true);
		try {
			BTREE = new Database(btreeLoc, null, dbConfig);
			HASH = new Database(hashLoc, null, dbConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* populate the new database with NO_RECORDS records */
		populateTable(BTREE,NO_RECORDS);
		System.out.println("1000 records inserted into" + btreeLoc);
		
		
	}
	
	/*
	 *  To poplate the given table with nrecs records
	 */
	static void populateTable(Database BTREE, int nrecs ) {
		int range;
			DatabaseEntry kdbt, ddbt;
		String s;

		/*  
		 *  generate a random string with the length between 64 and 127,
		 *  inclusive.
		 *
		 *  Seed the random number once and once only.
		 */
		Random random = new Random(1000000);

		try {
			for (int i = 0; i < nrecs; i++) {

				/* to generate a key string */
				range = 64 + random.nextInt( 64 );
				s = "";
				for ( int j = 0; j < range; j++ ) 
				  s+=(new Character((char)(97+random.nextInt(26)))).toString();




				/* to create a DBT for key */
				kdbt = new DatabaseEntry(s.getBytes());
				kdbt.setSize(s.length()); 

				// to print out the key/data pair
				System.out.println(s+"\n");	

				/* to generate a data string */
				range = 64 + random.nextInt( 64 );
				s = "";
				for ( int j = 0; j < range; j++ ) 
				  s+=(new Character((char)(97+random.nextInt(26)))).toString();
						// to print out the key/data pair
						// System.out.println(s);	
						// System.out.println("");
				
				/* to create a DBT for data */
				ddbt = new DatabaseEntry(s.getBytes());
				ddbt.setSize(s.length()); 

				/* to insert the key/data pair into the database */
				BTREE.putNoOverwrite(null, kdbt, ddbt);
			}
		}
		catch (DatabaseException dbe) {
			System.err.println("Populate the table: "+dbe.toString());
			System.exit(1);
		}
	}

	// Returns the value for the specified key
	public String getValue(String key) {
		try {
			// Create two DatabaseEntry instances:
			// theKey is used to perform the search
			// theData will hold the value associated to the key, if found
			DatabaseEntry theKey = new DatabaseEntry(key.getBytes());
			DatabaseEntry theData = new DatabaseEntry();
		 
			// Call get() to query the database
			if (BTREE.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				System.out.println("\nSUCCESS");
				String foundData = new String(theData.getData());
				return foundData;
			} 
		} catch (Exception e) {
			// Exception handling
			e.printStackTrace();
		}
		return null;

	}	
	
	//For testing purposes only
	public void put(String key, String data) {
		try {
			DatabaseEntry theKey = new DatabaseEntry(key.getBytes());
			DatabaseEntry theData = new DatabaseEntry(data.getBytes());
			BTREE.put(null, theKey, theData);
		} catch (Exception e) {
			// Exception handling
			e.printStackTrace();
		}
	}
	
	// Returns the key for the specified value
	public String getKey(String data) {
		Cursor myCursor = null;
		 
		try {
			myCursor = BTREE.openCursor(null, null);
		 
			DatabaseEntry foundKey = new DatabaseEntry();
			DatabaseEntry foundData = new DatabaseEntry();
		 
			// Retrieve records with calls to getNext() until the return status is not OperationStatus.SUCCESS
			while (myCursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				String keyString = new String(foundKey.getData());
				String dataString = new String(foundData.getData());
				if (dataString.equals(data)) {
					return keyString;
				}
			}
		} catch (DatabaseException de) {
			System.err.println("Error reading from database: " + de);
		} finally {
			try {
				if (myCursor != null) {
					myCursor.close();
				}
			} catch(DatabaseException dbe) {
				System.err.println("Error closing cursor: " + dbe.toString());
			}
		}
		
		return null;
	}
	
	// for testing purposes only
	public void printAll() {
		Cursor myCursor = null;
		 
		try {
			myCursor = BTREE.openCursor(null, null);
		 
			DatabaseEntry foundKey = new DatabaseEntry();
			DatabaseEntry foundData = new DatabaseEntry();
		 
			// Retrieve records with calls to getNext() until the return status is not OperationStatus.SUCCESS
			while (myCursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				String keyString = new String(foundKey.getData());
				String dataString = new String(foundData.getData());
				System.out.println("Key| Data : " + keyString + " | " + dataString + "");
			}
		} catch (DatabaseException de) {
			System.err.println("Error reading from database: " + de);
		} finally {
			try {
				if (myCursor != null) {
					myCursor.close();
				}
			} catch(DatabaseException dbe) {
				System.err.println("Error closing cursor: " + dbe.toString());
			}
		}
		
	}

}
