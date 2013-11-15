package edu.ucla.cs.cs144;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Item {
    public String temp;
    
    private int itemId;

    private int numberOfBids;

    private float currently, firstBid;

    private String started, ends;

    private String name, description;

    private List<String> categoryList;

    private List<Bid> bids;

    private User seller;

    public Item() {
        categoryList = new ArrayList<String>();
        bids = new ArrayList<Bid>();
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public List<Bid> getBidList() {
        return bids;
    }

    public void setBidList(List<Bid> bidList) {
        bids = bidList;
    }

    public void addToBidList(Bid myBid) {
        bids.add(myBid);
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public void addToCatList(String myCategory) {
        categoryList.add(myCategory);
    }

    public float getCurrently() {
        return currently;
    }

    public void setCurrently(float currently) {
        this.currently = currently;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public float getFirstBid() {
        return firstBid;
    }

    public void setFirstBid(float firstBid) {
        this.firstBid = firstBid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(int numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public void parseXML(String xmlDocument) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlDocument);

            NodeList nodes = doc.getChildNodes();
            Node item = nodes.item(0);
            doc.getDocumentElement().normalize();
            Element eItem = doc.getDocumentElement();
            temp = eItem.getAttribute("ItemID");

            itemId = new Integer(eItem.getAttribute("ItemID"));
//            numberOfBids = new Integer(eItem.getElementsByTagName(
//                    "Number_of_Bids").item(0).getTextContent());
//            currently = new Float(eItem.getElementsByTagName("Currently").item(
//                    0).getTextContent());
//            firstBid = new Float(eItem.getElementsByTagName("First_Bid")
//                    .item(0).getTextContent());
//            started = eItem.getElementsByTagName("Started").item(0)
//                    .getTextContent();
//            ends = eItem.getElementsByTagName("Ends").item(0).getTextContent();
//            name = eItem.getElementsByTagName("Name").item(0).getTextContent();
//            description = eItem.getElementsByTagName("Description").item(0)
//                    .getTextContent();
//
//            Node seller = eItem.getElementsByTagName("Seller").item(0);
//            Element eSeller = (Element) seller;
//            User mySeller = new User();
//            mySeller.setUserId(eSeller.getAttribute("UserID"));
//            mySeller.setRating(new Integer(eSeller.getAttribute("Rating")));
//            mySeller.setCountry(eItem.getElementsByTagName("Country").item(0)
//                    .getTextContent());
//            mySeller.setLocation(eItem.getElementsByTagName("Location").item(0)
//                    .getTextContent());
//            this.seller = mySeller;
//
//            NodeList categories = eItem.getElementsByTagName("Category");
//            for (int i = 0; i < categories.getLength(); i++) {
//                categoryList.add(categories.item(i).getTextContent());
//            }
//
//            NodeList bidsList = eItem.getElementsByTagName("Bids").item(0)
//                    .getChildNodes();
//            for (int i = 0; i < bidsList.getLength(); i++) {
//                bids.add(parseBid(bidsList.item(i)));
//            }
        } catch (ParserConfigurationException e) {
            System.out.println(e);
        } catch (SAXException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    private Bid parseBid(Node node) {
        Element eNode = (Element) node;
        Bid myBid = new Bid();
        myBid.setAmount(new Float(eNode.getElementsByTagName("Amount").item(0)
                .getTextContent()));
        myBid.setTime(eNode.getElementsByTagName("Time").item(0)
                .getTextContent());

        User myBidder = new User();
        Node bidder = eNode.getElementsByTagName("Bidder").item(0);
        Element eBidder = (Element) bidder;
        myBidder.setUserId(eBidder.getAttribute("UserID"));
        myBidder.setRating(new Integer(eBidder.getAttribute("Rating")));
        myBidder.setCountry(eBidder.getElementsByTagName("Country").item(0)
                .getTextContent());
        myBidder.setLocation(eBidder.getElementsByTagName("Location").item(0)
                .getTextContent());
        myBid.setBidder(myBidder);

        return myBid;
    }
}