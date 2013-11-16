package edu.ucla.cs.cs144;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class Item {
    private int ItemID;

    private int Number_of_Bids;

    private float Currently, First_Bid, Buy_Price;

    private String Started, Ends;

    private String Name, Description, SellerID;

    private List<String> CategoryList;

    private List<Bid> Bids;

    private User seller;

    public Item() {
        CategoryList = new ArrayList<String>();
        Bids = new ArrayList<Bid>();
    }

    public Item(String xml) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(
                    new ByteArrayInputStream(xml.getBytes("utf-8"))));

            doc.getDocumentElement().normalize();
            Element domItem = doc.getDocumentElement();

            this.ItemID = Integer.parseInt(domItem.getAttributeNode("ItemID")
                    .getNodeValue());
            this.Name = MyParser.getElementTextByTagNameNR(domItem, "Name");
            this.CategoryList = new ArrayList<String>();
            for (Element e : MyParser.getElementsByTagNameNR(domItem,
                    "Category")) {
                this.CategoryList.add(MyParser.getElementText(e));
            }
            this.Currently = Float.parseFloat(MyParser.strip(MyParser
                    .getElementTextByTagNameNR(domItem, "Currently")));
            if (MyParser.getElementTextByTagNameNR(domItem, "Buy_Price") != "") {
                this.Buy_Price = Float.parseFloat(MyParser.strip(MyParser
                        .getElementTextByTagNameNR(domItem, "Buy_Price")));
            }
            this.First_Bid = Float.parseFloat(MyParser.strip(MyParser
                    .getElementTextByTagNameNR(domItem, "First_Bid")));
            this.Number_of_Bids = Integer.parseInt(MyParser
                    .getElementTextByTagNameNR(domItem, "Number_of_Bids"));
            this.Bids = new ArrayList<Bid>();
            for (Element e : MyParser.getElementsByTagNameNR(MyParser
                    .getElementByTagNameNR(domItem, "Bids"), "Bid")) {
                this.Bids.add(buildBid(e));
            }
            this.seller = new User();
            this.seller.setLocation(MyParser.getElementTextByTagNameNR(domItem,
                    "Location"));
            this.seller.setCountry(MyParser.getElementTextByTagNameNR(domItem,
                    "Country"));
            this.seller.setUserId(MyParser.getElementByTagNameNR(domItem,
                    "Seller").getAttributeNode("UserID").getNodeValue());
            this.seller.setRating(Integer.parseInt(MyParser
                    .getElementByTagNameNR(domItem, "Seller").getAttributeNode(
                            "Rating").getNodeValue()));

            this.Started = MyParser.getElementTextByTagNameNR(domItem,
                    "Started");
            this.Ends = MyParser.getElementTextByTagNameNR(domItem, "Ends");

            this.Description = MyParser.getElementTextByTagNameNR(domItem,
                    "Description");
            if (this.Description.length() > 4000) {
                this.Description = this.Description.substring(0, 4000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bid buildBid(Element e) {
        Bid b = new Bid();
        Element bidder = MyParser.getElementByTagNameNR(e, "Bidder");
        User u = buildUser(bidder);
        b.setBidder(u);
        try {
            b.setTime(MyParser.sdf.parse(MyParser.getElementTextByTagNameNR(e,
                    "Time")));
        } catch (ParseException exception) {
            exception.printStackTrace();
            System.err.println("ERROR: Could not parse date.");
            System.exit(1);
        }
        b.setAmount(Float.parseFloat(MyParser.strip(MyParser
                .getElementTextByTagNameNR(e, "Amount"))));
        return b;
    }

    private User buildUser(Element e) {
        User u = new User();
        u.setUserId(e.getAttributeNode("UserID").getNodeValue());
        u.setRating(Integer.parseInt(e.getAttributeNode("Rating")
                .getNodeValue()));
        u.setLocation(MyParser.getElementTextByTagNameNR(e, "Location"));
        u.setCountry(MyParser.getElementTextByTagNameNR(e, "Country"));
        return u;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public List<Bid> getBidList() {
        return Bids;
    }

    public void setBidList(List<Bid> bidList) {
        Bids = bidList;
    }

    public void addToBidList(Bid myBid) {
        Bids.add(myBid);
    }

    public List<String> getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        CategoryList = categoryList;
    }

    public void addToCatList(String myCategory) {
        CategoryList.add(myCategory);
    }

    public float getCurrently() {
        return Currently;
    }

    public void setCurrently(float currently) {
        Currently = currently;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEnds() {
        return Ends;
    }

    public void setEnds(String ends) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date endsDate = null;
        try {
            endsDate = sdf.parse(ends);
        } catch (java.text.ParseException e) {
            System.err.println(e);
        }
        SimpleDateFormat newDateFormat = new SimpleDateFormat(
                "MMM-dd-yy HH:mm:ss");
        String formattedEnds = newDateFormat.format(endsDate);
        Ends = formattedEnds;
    }

    public float getFirst_Bid() {
        return First_Bid;
    }

    public void setFirst_Bid(float first_Bid) {
        First_Bid = first_Bid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getNumber_of_Bids() {
        return Number_of_Bids;
    }

    public void setNumber_of_Bids(int number_of_Bids) {
        Number_of_Bids = number_of_Bids;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String getSellerID() {
        return SellerID;
    }

    public void setSellerID(String sellerID) {
        SellerID = sellerID;
    }

    public String getStarted() {
        return Started;
    }

    public void setStarted(String started) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date startedDate = null;
        try {
            startedDate = sdf.parse(started);
        } catch (java.text.ParseException e) {
            System.err.println(e);
        }
        SimpleDateFormat newDateFormat = new SimpleDateFormat(
                "MMM-dd-yy HH:mm:ss");
        String formattedStarted = newDateFormat.format(startedDate);
        Started = formattedStarted;
    }

    public float getBuy_Price() {
        return Buy_Price;
    }

    public void setBuy_Price(float buy_Price) {
        Buy_Price = buy_Price;
    }

    public String generateXML() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        xml += "<Item ItemID=\"" + ItemID + "\">\n";
        xml += "  <Name>" + StringEscapeUtils.escapeXml(Name) + "</Name>\n";
        for (String cat : CategoryList) {
            xml += "  <Category>" + StringEscapeUtils.escapeXml(cat)
                    + "</Category>\n";
        }
        xml += "  <Currently>$" + String.format("%.2f", Currently)
                + "</Currently>\n";
        xml += "  <First_Bid>$" + String.format("%.2f", First_Bid)
                + "</First_Bid>\n";
        xml += "  <Number_of_Bids>" + Number_of_Bids + "</Number_of_Bids>\n";
        if (Bids.isEmpty()) {
            xml += "  <Bids/>\n";
        } else {
            xml += "  <Bids>\n";
            for (Bid b : Bids) {
                xml += "    <Bid>\n";
                xml += "      <Bidder UserID=\"" + b.getBidder().getUserId()
                        + "\" Rating=\"" + b.getBidder().getRating() + "\">\n";
                if (b.getBidder().getLocation() != null) {
                    xml += "        <Location>"
                            + StringEscapeUtils.escapeXml(b.getBidder()
                                    .getLocation()) + "</Location>\n";
                }
                if (b.getBidder().getCountry() != null) {
                    xml += "        <Country>"
                            + StringEscapeUtils.escapeXml(b.getBidder()
                                    .getCountry()) + "</Country>\n";
                }
                xml += "      </Bidder>\n";
                xml += "      <Time>" + b.getTime() + "</Time>\n";
                xml += "      <Amount>" + b.getAmount() + "</Amount>\n";
                xml += "    </Bid>\n";
            }
            xml += "  </Bids>\n";
        }
        xml += "  <Location>"
                + StringEscapeUtils.escapeXml(seller.getLocation())
                + "</Location>\n";
        xml += "  <Country>" + StringEscapeUtils.escapeXml(seller.getCountry())
                + "</Country>\n";
        // Check to make sure this date is formatted correctly
        xml += "  <Started>" + Started + "</Started>\n";
        xml += "  <Ends>" + Ends + "</Ends>\n";
        xml += "  <Seller UserID=\""
                + StringEscapeUtils.escapeXml(seller.getUserId())
                + "\" Rating=\"" + seller.getRating() + "\"/>\n";
        xml += "  <Description>" + StringEscapeUtils.escapeXml(Description)
                + "</Description>\n";
        xml += "</Item>\n";
        return xml;
    }
}
