package edu.ucla.cs.cs144;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;

public class Indexer {

    private IndexWriter indexWriter = null;

    public IndexWriter getIndexWriter(boolean create) throws IOException {
        if (indexWriter == null) {
            indexWriter = new IndexWriter(System.getenv("LUCENE_INDEX")
                    + "/ebayIndex", new StandardAnalyzer(), create);
        }
        return indexWriter;
    }

    public void closeIndexWriter() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
    }

    /** Creates a new instance of Indexer */
    public Indexer() {
    }

    public void rebuildIndexes() {

        Connection conn = null;

        // create a connection to the database to retrieve Items from MySQL
        try {
            // Erase existing index
            getIndexWriter(true);

            conn = DbManager.getConnection(true);
            PreparedStatement getItems = conn
                    .prepareStatement("SELECT ItemID, Name, Description FROM Item");
            PreparedStatement getItemCategories = conn
                    .prepareStatement("SELECT Category FROM Category WHERE ItemID = ?");

            Integer itemId;
            String name, description;

            ResultSet itemResultSet = getItems.executeQuery();

            while (itemResultSet.next()) {
                itemId = itemResultSet.getInt(1);
                name = itemResultSet.getString(2);
                description = itemResultSet.getString(3);

                getItemCategories.setInt(1, itemId);
                ResultSet catResultSet = getItemCategories.executeQuery();

                String categories = null;

                while (catResultSet.next()) {
                    if (categories == null) {
                        categories = catResultSet.getString(1);
                    } else {
                        categories += ", " + catResultSet.getString(1);
                    }
                }

                createIndexForEntry(itemId, name, description, categories);
            }

            closeIndexWriter();
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private void createIndexForEntry(Integer itemId, String name,
            String description, String categories) throws IOException {
        IndexWriter writer = getIndexWriter(false);
        Document doc = new Document();
        doc.add(new Field("id", itemId.toString(), Field.Store.YES,
                Field.Index.NO));
        doc
                .add(new Field("name", name, Field.Store.YES,
                        Field.Index.TOKENIZED));
        doc.add(new Field("categories", categories, Field.Store.YES,
                Field.Index.TOKENIZED));
        doc.add(new Field("description", description, Field.Store.YES,
                Field.Index.TOKENIZED));
        String fullSearchableText = name + " " + categories + " " + description;
        doc.add(new Field("content", fullSearchableText, Field.Store.NO,
                Field.Index.TOKENIZED));
        writer.addDocument(doc);
    }

    public static void main(String args[]) {
        Indexer idx = new Indexer();
        idx.rebuildIndexes();
    }
}
