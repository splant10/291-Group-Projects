import java.io.FileNotFoundException;
import java.util.Random;

import com.sleepycat.db.Database;
import com.sleepycat.db.DatabaseConfig;
import com.sleepycat.db.DatabaseEntry;
import com.sleepycat.db.DatabaseException;
import com.sleepycat.db.DatabaseType;

public class MyDatabase {
	// to specify the file name for the table
    private static final String SAMPLE_TABLE = "tmp/ahmirza_db/sample_table";
    // number of records to populate
    private static final int NO_RECORDS = 1000;
	
	private DatabaseConfig dbConfig;
	private Database my_table;
	
	public MyDatabase(String db_type_option) {
		dbConfig = new DatabaseConfig();
		setDatabaseType(db_type_option);
	}
	
	private void setDatabaseType(String db_type_option) {
    	if (db_type_option == null || Integer.valueOf(db_type_option) > 0 && Integer.valueOf(db_type_option) < 4) {
    		System.out.println("Please enter correct number of argument");
    	}
		
		switch (Integer.valueOf(db_type_option)){
    	case 1:
    		dbConfig.setType(DatabaseType.BTREE);
    		break;
    	case 2:
    		dbConfig.setType(DatabaseType.HASH);
    		break;
    	case 3:
    		break;
    	}
	}

	public void create() {
		dbConfig.setAllowCreate(true);
		try {
			my_table = new Database(SAMPLE_TABLE, null, dbConfig);
			System.out.println(SAMPLE_TABLE + " has been created");
			/* populate the new database with NO_RECORDS records */
    	    populateTable(my_table,NO_RECORDS);
    	    System.out.println("1000 records inserted into" + SAMPLE_TABLE);
		} catch (FileNotFoundException | DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
     *  To pouplate the given table with nrecs records
     */
    static void populateTable(Database my_table, int nrecs ) {
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
                // System.out.println(s);	

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
                my_table.putNoOverwrite(null, kdbt, ddbt);
            }
        }
        catch (DatabaseException dbe) {
            System.err.println("Populate the table: "+dbe.toString());
            System.exit(1);
        }
    }

    // Returns the value for the specified key
	public String getValue(String key) {
		/* to create a DBT for key */
		//DatabaseEntry kdbt = new DatabaseEntry(key.getBytes());
		//kdbt.setSize(key.length());
		//my_table.get(null, kdbt, kdbt, null);
		return null;
	}
	
	// Returns the key for the specified value
	public String getKey(String value) {
		// TODO Auto-generated method stub
		return null;
	}

}
