package edu.ucla.cs.cs144;

import java.util.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

import java.util.ArrayList;
import java.util.Date;
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

        if (numResultsToReturn < 0 || numResultsToSkip < 0) {
            return new SearchResult[0];
        }

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

        if (numResultsToReturn < 0 || numResultsToSkip < 0) {
            return new SearchResult[0];
        }

        init();
        SearchResult[] formattedResults = null;
        try {
            conn = DbManager.getConnection(true);
            Set<SearchResult> results = getAdvancedResults(constraints);
            formattedResults = formatResults(results, numResultsToSkip,
                    numResultsToReturn);

            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return formattedResults;
    }

    public String getXMLDataForItemId(String itemId) {
        // Cast ItemID as an Integer
        int ItemId = Integer.parseInt(itemId);

        // Build Item object from DB tables using ItemId
        Item myItem = constructItemFromDB(ItemId);

        // Construct XML as String using Item data and following Item Schema
        String xmlData = myItem.generateXML();

        return xmlData;
    }

    public Item constructItemFromDB(int ItemId) {
        Item myItem = new Item();
        try {
            conn = DbManager.getConnection(true);
            PreparedStatement getItemData = conn
                    .prepareStatement("SELECT Name, Currently, First_Bid, Number_of_Bids, Started, Ends, SellerID, Description "
                            + "FROM Item WHERE ItemID = ?");
            PreparedStatement getItemCategories = conn
                    .prepareStatement("SELECT Category FROM Category WHERE ItemID = ?");
            PreparedStatement getSellerData = conn
                    .prepareStatement("SELECT * FROM User WHERE UserID = ?");
            PreparedStatement getItemBids = conn
                    .prepareStatement("SELECT u.UserID, u.Rating, u.Location, u.Country, b.Time, b.Amount "
                            + "FROM User AS u "
                            + "INNER JOIN Bid AS b "
                            + "ON u.UserID = b.UserID " + "WHERE b.ItemID = ?");

            // set up variables for getting item data
            myItem.setItemID(ItemId);

            // Get Item data and load it into prepared variables
            getItemData.setInt(1, ItemId);
            ResultSet itemResultSet = getItemData.executeQuery();
            if (!itemResultSet.next()) {
                return null;
            }
            myItem.setName(itemResultSet.getString(1));
            myItem.setCurrently(itemResultSet.getFloat(2));
            myItem.setFirst_Bid(itemResultSet.getFloat(3));
            myItem.setNumber_of_Bids(itemResultSet.getInt(4));
            myItem.setStarted(itemResultSet.getString(5));
            myItem.setEnds(itemResultSet.getString(6));
            myItem.setDescription(itemResultSet.getString(8));

            // Get Categories and load them into Categories List
            getItemCategories.setInt(1, ItemId);
            ResultSet catResultSet = getItemCategories.executeQuery();

            while (catResultSet.next()) {
                String cat = catResultSet.getString(1);
                myItem.addToCatList(cat);
            }

            // Get Bids and load them into Bid List
            getItemBids.setInt(1, ItemId);
            ResultSet bidResultSet = getItemBids.executeQuery();
            while (bidResultSet.next()) {
                User myBidder = new User();
                Bid myBid = new Bid();
                myBidder.setUserID(bidResultSet.getString(1));
                myBidder.setRating(bidResultSet.getInt(2));
                myBidder.setLocation(bidResultSet.getString(3));
                myBidder.setCountry(bidResultSet.getString(4));
                myBid.setBidder(myBidder);
                myBid.setTime(bidResultSet.getString(5));
                myBid.setAmount(bidResultSet.getFloat(6));
                myItem.addToBidList(myBid);
            }

            // Set up variables for getting Seller Data
            User mySeller = new User();

            // Get Seller data and load it into prepared variables
            getSellerData.setString(1, itemResultSet.getString(7));
            ResultSet sellerResultSet = getSellerData.executeQuery();
            if (!sellerResultSet.next()) {
                throw new SQLException("Item is required to have Seller.");
            }
            mySeller.setUserID(sellerResultSet.getString(1));
            mySeller.setRating(sellerResultSet.getInt(2));
            mySeller.setLocation(sellerResultSet.getString(3));
            mySeller.setCountry(sellerResultSet.getString(4));
            myItem.setSeller(mySeller);
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return myItem;
    }

    public String echo(String message) {
        return message;
    }

    private Set<SearchResult> getAdvancedResults(SearchConstraint[] constraints)
            throws SQLException {

        int numBidders = 0;
        int numNonBidders = 0;
        
        // Check for Bidder constraints and SQL contraints.
        // Extract the Bidder constraints.
        List<SearchConstraint> bidders = new ArrayList<SearchConstraint>();
        for (SearchConstraint constraint : constraints) {
            if (constraint.getFieldName().equals(FieldName.BidderId)) {
                numBidders++;
                bidders.add(constraint);
            } else if (constraint.getFieldName().equals(FieldName.BuyPrice)
                    || constraint.getFieldName().equals(FieldName.EndTime)
                    || constraint.getFieldName().equals(FieldName.SellerId)) {
                numNonBidders++;
            }
        }

        // If we have Bidder constraints we need a join in the DB.
        boolean needsJoin = false;
        if (numBidders > 0)
            needsJoin = true;

        // Initialize query strings.
        String luceneQuery = null;
        String sqlQuery = null;
        if (numNonBidders + numBidders > 0) {
            sqlQuery = constructSqlQuery(null, null, null, needsJoin);
        }

        // Loop through constraints and build up the queries.
        int sqlConstraints = 0;
        for (SearchConstraint constraint : constraints) {
            if (constraint.getFieldName().equals(FieldName.ItemName)) {
                luceneQuery = constructLuceneQuery(luceneQuery, "name",
                        formatLuceneQueryString(constraint.getValue()));
            } else if (constraint.getFieldName().equals(FieldName.Category)) {
                luceneQuery = constructLuceneQuery(luceneQuery, "categories",
                        formatLuceneQueryString(constraint.getValue()));
            } else if (constraint.getFieldName().equals(FieldName.Description)) {
                luceneQuery = constructLuceneQuery(luceneQuery, "description",
                        formatLuceneQueryString(constraint.getValue()));
            } else if (constraint.getFieldName().equals(FieldName.SellerId)) {
                sqlQuery = constructSqlQuery(sqlQuery, "Item.SellerID", "'"
                        + constraint.getValue().replace("\'", "\\\'") + "'", needsJoin);
                if (sqlConstraints > 0) {
                    sqlQuery = sqlQuery + " AND ";
                }
                sqlConstraints++;
            } else if (constraint.getFieldName().equals(FieldName.BuyPrice)) {
                sqlQuery = constructSqlQuery(sqlQuery, "Item.Buy_price", "'"
                        + constraint.getValue() + "'", needsJoin);
                if (sqlConstraints > 0) {
                    sqlQuery = sqlQuery + " AND ";
                }
                sqlConstraints++;
            } else if (constraint.getFieldName().equals(FieldName.EndTime)) {
                // Make sure end time is formatted correctly.
                SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
                Date queryDate = null;
                try {
                    queryDate = sdf.parse(constraint.getValue());
                } catch (java.text.ParseException e) {
                    System.out.println(e);
                }
                SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = newDateFormat.format(queryDate);
                sqlQuery = constructSqlQuery(sqlQuery, "Item.Ends", "'"
                        + formattedDate + "'", needsJoin);
                if (sqlConstraints > 0) {
                    sqlQuery = sqlQuery + " AND ";
                }
                sqlConstraints++;
            } else if (constraint.getFieldName().equals(FieldName.BidderId)
                    && numBidders < 2) {
                sqlQuery = constructSqlQuery(sqlQuery, "Bid.UserID", "'"
                        + constraint.getValue().replace("\'", "\\\'") + "'", needsJoin);
                if (sqlConstraints > 0) {
                    sqlQuery = sqlQuery + " AND ";
                }
                sqlConstraints++;
            }
        }

        // If we have multiple Bidder constraints we need to format a special query.
        if (numBidders > 1) {
            if (numNonBidders != 0) {
                sqlQuery = sqlQuery + " AND (";
            } else {
                sqlQuery = sqlQuery + "(";
            }
            for (int i = 0; i < bidders.size(); i++) {
                sqlQuery = sqlQuery + "Bid.UserID='"
                        + bidders.get(i).getValue() + "'";
                if (i != bidders.size() - 1) {
                    sqlQuery = sqlQuery + " OR ";
                }
            }
            sqlQuery = sqlQuery + ") GROUP BY Item.ItemID HAVING COUNT(*)>1";
        }

        return performAdvancedSearch(luceneQuery, sqlQuery);
    }

    private Set<SearchResult> performAdvancedSearch(String luceneQuery,
            String sqlQuery) throws SQLException {

        Set<SearchResult> results = null;
        try {
            if (luceneQuery == null && sqlQuery != null) {
                Set<SearchResult> sqlResults = extractSqlResults(sqlQuery);
                return sqlResults;
            } else if (luceneQuery != null && sqlQuery == null) {
                Hits hits = performSearch(luceneQuery);
                results = extractLuceneResults(hits);
            } else {
                Hits hits = performSearch(luceneQuery);
                results = extractLuceneResults(hits);
                Set<SearchResult> sqlResults = extractSqlResults(sqlQuery);
                results.retainAll(sqlResults);
            }
        } catch (ParseException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        return results;
    }

    private Set<SearchResult> extractSqlResults(String query)
            throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Set<SearchResult> sqlResults = new LinkedHashSet<SearchResult>();

        while (rs.next()) {
            Integer ItemId;
            ItemId = rs.getInt(1);
            sqlResults
                    .add(new SearchResult(ItemId.toString(), rs.getString(2)));
        }

        return sqlResults;
    }

    private Set<SearchResult> extractLuceneResults(Hits hits)
            throws IOException {

        int size = 0;
        if (hits != null) {
            size = hits.length();
        }
        Set<SearchResult> luceneResults = new LinkedHashSet<SearchResult>();
        for (int i = 0; i < size; i++) {
            Document doc = hits.doc(i);
            luceneResults.add(new SearchResult(doc.get("id"), doc.get("name")));
        }
        return luceneResults;
    }
    
    private String formatLuceneQueryString(String value) {
        String formattedValue = QueryParser.escape(value);
        formattedValue = "(" + formattedValue.replace(" ", " AND ") + ")";
        return formattedValue;
    }

    private String constructSqlQuery(String query, String column, String value,
            boolean needsJoin) {
        if (query == null && needsJoin) {
            query = "SELECT Item.ItemID, Name FROM Item JOIN Bid ON Item.ItemID = Bid.ItemID"
                    + " WHERE ";
        } else if (query == null && !needsJoin) {
            query = "SELECT ItemID, Name FROM Item WHERE ";
        } else {
            query = query + column + "=" + value;
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
        if (queryString == null) {
            return null;
        }
        Query query = parser.parse(queryString);
        Hits hits = searcher.search(query);
        return hits;
    }

    private SearchResult[] formatResults(Set<SearchResult> results,
            int numToSkip, int numToReturn) {

        int size = 0;
        int endCondition = 0;
        if (numToReturn == 0 || results.size() < numToReturn + numToSkip) {
            size = results.size() - numToSkip;
            endCondition = results.size();
        } else {
            size = numToReturn;
            endCondition = numToReturn + numToSkip;
        }
        if (size < 0) {
            size = 0;
            endCondition = 0;
        }

        SearchResult[] arrayResults = results.toArray(new SearchResult[results
                .size()]);
        SearchResult[] formatedResults = new SearchResult[size];

        for (int i = numToSkip; i < endCondition; i++) {
            formatedResults[i - numToSkip] = arrayResults[i];
        }

        return formatedResults;
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
        if (size < 0) {
            size = 0;
            endCondition = 0;
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
