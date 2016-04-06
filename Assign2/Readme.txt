To compile the program:

./build

To compile and run the program:

./build-run.sh

To run the program:

./mydbtest

Note: if you're not allowed permission to run these scripts, give the commands:

chmod +x build
chmod +x build-run.sh
chmod +x mydbtest

NOTE: SAMPLE_TABLE (in MyDatabase.java) variable may need to be changed to work on your account??

To change the type of database system used:

vim mydbtest

and change the parameter given to one of "btree", "hash", or "indexfile"
