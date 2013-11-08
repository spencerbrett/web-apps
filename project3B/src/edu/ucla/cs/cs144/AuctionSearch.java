package edu.ucla.cs.cs144;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.LucenePackage;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.text.SimpleDateFormat;

import edu.ucla.cs.cs144.DbManager;
import edu.ucla.cs.cs144.SearchConstraint;
import edu.ucla.cs.cs144.SearchResult;

public class AuctionSearch implements IAuctionSearch {

    private Connection conn = null;

    private IndexSearcher searcher = null;

    private QueryParser parser = null;

    private void init() {
        try {
            searcher = new IndexSearcher(System.getenv("LUCENE_INDEX")
                    + "/ebayIndex");
            parser = new QueryParser("content", new StandardAnalyzer());
        } catch (CorruptIndexException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /*
     * You will probably have to use JDBC to access MySQL data Lucene
     * IndexSearcher class to lookup Lucene index. Read the corresponding
     * tutorial to learn about how to use these.
     * 
     * Your code will need to reference the directory which contains your Lucene
     * index files. Make sure to read the environment variable $LUCENE_INDEX
     * with System.getenv() to build the appropriate path.
     * 
     * You may create helper functions or classes to simplify writing these
     * methods. Make sure that your helper functions are not public, so that
     * they are not exposed to outside of this class.
     * 
     * Any new classes that you create should be part of edu.ucla.cs.cs144
     * package and their source files should be placed at src/edu/ucla/cs/cs144.
     * 
     */

    public SearchResult[] basicSearch(String query, int numResultsToSkip,
            int numResultsToReturn) {

        init();
        SearchResult[] results = null;
        try {
            Hits hits = performSearch(query);
            results = parseHits(hits, numResultsToSkip, numResultsToReturn);
        } catch (IOException e) {
            System.out.println(e);
        } catch (ParseException e) {
            System.out.println(e);
        }

        return results;
    }

    public SearchResult[] advancedSearch(SearchConstraint[] constraints,
            int numResultsToSkip, int numResultsToReturn) {

        init();
        try {
            conn = DbManager.getConnection(true);
            getAdvancedResults(constraints);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return new SearchResult[0];
    }

    public String getXMLDataForItemId(String itemId) {
        // TODO: Your code here!
        String xmlData;
        try {
        	conn = DbManager.getConnection(true);
            PreparedStatement getItemData = conn.prepareStatement(
            	"SELECT Name, Currently, First_Bid, Number_of_Bids, Started, Ends, Description"+
            	 "FROM Item WHERE ItemID = ?");
            PreparedStatement getItemCategories = conn.prepareStatement(
            	"SELECT Category FROM Category WHERE ItemID = ?");
            PreparedStatement getSellerData = conn.prepareStatement(
            	"SELECT * FROM User WHERE UserID = ?");
            PreparedStatement getItemBids = conn.prepareStatement(
            	"SELECT u.UserID, u.Rating, u.Location, u.Country, b.Time, b.Amount"+
				 "FROM User AS u"+ 
				 "INNER JOIN Bid AS b"+
				 "ON u.UserID = b.UserID"+
   				 "WHERE b.ItemID = ?");

            //cast function argument as integer to set for queries
            int ItemId = Integer.parseInt(itemId);

            //set up variables for getting item data
            Item myItem = new Item();
            
            // Get Item data and load it into prepared variables
            getItemData.setInt(1, ItemId);
            ResultSet itemResultSet = getItemData.executeQuery();
            myItem.setName(itemResultSet.getString(1));
            myItem.setCurrently(itemResultSet.getFloat(2));
            myItem.setFirst_Bid(itemResultSet.getFloat(3));
            myItem.setNumber_of_Bids(itemResultSet.getInt(4));
            myItem.setStarted(itemResultSet.getDate(5));
            myItem.setEnds(itemResultSet.getDate(6));
            myItem.setDescription(itemResultSet.getString(7));

            //Get Categories and load them into Categories String in XML format
            String Categories = "";
            getItemCategories.setInt(1, ItemId);
            ResultSet catResultSet = getItemCategories.executeQuery();

            while(catResultSet.next()){
            	Categories += "  <Category>"+catResultSet.getString(1)+"</Category>\n";
            }

            //Get Seller Data
            

        }
        catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    public String echo(String message) {
        return message;
    }

    private void getAdvancedResults(SearchConstraint[] constraints) {
        boolean needsJoin = false;
        for (SearchConstraint constraint : constraints) {
            if (constraint.getFieldName().equals(FieldName.BidderId)) {
                needsJoin = true;
            }
        }

        String luceneQuery = null;
        String sqlQuery = null;

        for (SearchConstraint constraint : constraints) {
            if (constraint.getFieldName().equals(FieldName.ItemName)) {
                luceneQuery = constructLuceneQuery(luceneQuery, "name",
                        constraint.getValue());
            } else if (constraint.getFieldName().equals(FieldName.Category)) {
                luceneQuery = constructLuceneQuery(luceneQuery, "categories",
                        constraint.getValue());
            } else if (constraint.getFieldName().equals(FieldName.Description)) {
                luceneQuery = constructLuceneQuery(luceneQuery, "description",
                        constraint.getValue());
            } else if (constraint.getFieldName().equals(FieldName.SellerId)) {
                sqlQuery = constructSqlQuery(sqlQuery, "Item.SellerId",
                        constraint.getValue(), needsJoin);
            } else if (constraint.getFieldName().equals(FieldName.BuyPrice)) {
                sqlQuery = constructSqlQuery(sqlQuery, "Item.Buy_price",
                        constraint.getValue(), needsJoin);
            } else if (constraint.getFieldName().equals(FieldName.EndTime)) {
                sqlQuery = constructSqlQuery(sqlQuery, "Item.Ends", constraint
                        .getValue(), needsJoin);
            } else if (constraint.getFieldName().equals(FieldName.BidderId)) {
                sqlQuery = constructSqlQuery(sqlQuery, "Bid.UserId", constraint
                        .getValue(), needsJoin);
            }
        }

        System.out.println("Lucene Query: " + luceneQuery);
        System.out.println("SQL Query: " + sqlQuery);
    }

    private String constructSqlQuery(String query, String column, String value,
            boolean needsJoin) {
        if (query == null && needsJoin) {
            query = "SELECT ItemId, Name FROM Item JOIN Bid on Item.ItemId = Bid.ItemId"
                    + " WHERE " + column + "=" + value;
        } else if (query == null && !needsJoin) {
            query = "SELECT ItemId, Name FROM Item WHERE " + column + "="
                    + value;
        } else {
            query = query + " AND " + column + "=" + value;
        }
        return query;
    }

    private String constructLuceneQuery(String query, String field, String value) {
        if (query == null) {
            query = field + ":" + value;
        } else {
            query = query + " AND " + field + ":" + value;
        }

        return query;
    }

    private Hits performSearch(String queryString) throws IOException,
            ParseException {
        Query query = parser.parse(queryString);
        Hits hits = searcher.search(query);
        return hits;
    }

    private SearchResult[] parseHits(Hits hits, int numToSkip, int numToReturn)
            throws IOException {

        int size = 0;
        int endCondition = 0;
        if (numToReturn == 0 || hits.length() < numToReturn + numToSkip) {
            size = hits.length() - numToSkip;
            endCondition = hits.length();
        } else {
            size = numToReturn;
            endCondition = numToReturn + numToSkip;
        }
        SearchResult[] results = new SearchResult[size];

        for (int i = numToSkip; i < endCondition; i++) {
            Document doc = hits.doc(i);
            results[i - numToSkip] = new SearchResult(doc.get("id"), doc
                    .get("name"));
        }

        return results;
    }
}
