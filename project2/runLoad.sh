#!/bin/bash

# Run the drop.sql batch file to drop existing tables
# Inside the drop.sql, you should check whether the table exists. Drop them Only if they exist.
mysql CS144 < drop.sql

# Run the create.sql batch file to create the database and tables
mysql CS144 < create.sql

# Compile and run the parser the generate the appropriate load files
ant clean
ant 
ant run-all

# If the Java code does not handle duplicate removal, do this now
sort -u Category.dat > Category.dat.tmp
sort -u User.dat > User.dat.tmp
sort -u Item.dat > Item.dat.tmp
sort -u Bid.dat > Bid.dat.tmp

# Run the load.sql batch file to load the data
mysql CS144 < load.sql

# Remove all temporary files
rm ./*.dat*
