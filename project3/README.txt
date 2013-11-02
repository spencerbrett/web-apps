Partner 1:
Spencer Brett
704071250

Partner 2:
Daniel Daskalov
504079134


This example contains a simple utility class to simplify opening database
connections in Java applications, such as the one you will write to build
your Lucene index. 

To build and run the sample code, use the "run" ant target inside
the directory with build.xml by typing "ant run".

The default storage engine for MySQL is MyISAM, but I decided I wanted to use
foreign key on certain tables for data consistency. Therefore, I needed to modify
the storage engine for our tables to be InnoDB to support foreign keys. In the 
InnoDB storage engine, only BTree indexes are supported.

We created 4 indexes split between 2 of our tables in order to enhance the 
performance of the queries in our to be developed search page. First, we have
an index on SellerID in the item table to support querying for the seller.
Then, we have an index on the end time in the item table for that query.
Next, we have an index on the buy price. Finally, we have an index on
the ItemID in the Bid table for querying bidders for a certain item.
