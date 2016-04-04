#!/bin/bash
javac -cp ".:lib/db.jar" Main.java MyDatabase.java
java -cp ".:lib/db.jar" Main MyDatabase
