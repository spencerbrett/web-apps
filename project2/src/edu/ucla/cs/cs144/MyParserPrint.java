/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


class MyParserPrint {
    
    static final String columnSeparator = "|*|";
    static DocumentBuilder builder;
    
    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);
        
        /* Fill in code here (you will probably need to write auxiliary
            methods). */
        
        
        /**************************************************************/
        
        //recursiveDescent(doc, 0);
        parseItems(doc);
    }
    /* parse all the items from the initial doc object */
    public static void parseItems(Node doc) {
    	Element[] myAuctions = getElementsByTagNameNR((Element) doc.getLastChild(), "Item");
    	List<Item> myItems = new ArrayList<Item>();
    	for(Element e : myAuctions) {
    		myItems.add(buildItem(e));
    	}
    	for(Item i : myItems){
    		System.out.println(i.printItem());
    	}
    }
    public static Item buildItem(Element domItem) {
    	Item myItem = new Item();
		DateFormat df = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
		User seller = new User();
		
    	myItem.setItemId(Integer.parseInt(domItem.getAttributeNode("ItemID").getNodeValue()));
    	myItem.setName(getElementTextByTagNameNR(domItem,"Name"));
    	for(Element e : getElementsByTagNameNR(domItem,"Category")){
    		myItem.insertCategory(getElementText(e));
    	}
    	myItem.setCurrentBid(Double.parseDouble(strip(getElementTextByTagNameNR(domItem,"Currently"))));
    	if(getElementTextByTagNameNR(domItem,"Buy_Price") != ""){
        	myItem.setBuyPrice(Double.parseDouble(strip(getElementTextByTagNameNR(domItem,"Buy_Price"))));
    	}
    	myItem.setFirstBid(Double.parseDouble(strip(getElementTextByTagNameNR(domItem,"First_Bid"))));
    	myItem.setNumBids(Integer.parseInt(getElementTextByTagNameNR(domItem,"Number_of_Bids")));
    	for(Element e : getElementsByTagNameNR(getElementByTagNameNR(domItem, "Bids"),"Bid")) {
    		Bid b = new Bid();
    		User u = new User();
    		Element bidder = getElementByTagNameNR(e, "Bidder");
    		
    		u.setUserId(bidder.getAttributeNode("UserID").getNodeValue());
    		u.setRating(Integer.parseInt(bidder.getAttributeNode("Rating").getNodeValue()));
    		u.setLocation(getElementTextByTagNameNR(bidder,"Location"));
    		u.setCountry(getElementTextByTagNameNR(bidder,"Country"));
    		
    		b.setBidder(u);
    		try {
				b.setPostingTime(df.parse(getElementTextByTagNameNR(e,"Time")));
			} catch (ParseException exception) {
				// TODO Auto-generated catch block
				exception.printStackTrace();
				System.err.println("Could not parse date.");
				System.exit(1);
			}
    		b.setAmount(Double.parseDouble(strip(getElementTextByTagNameNR(e,"Amount"))));
    		myItem.insertBid(b);
    	}
    	
    	seller.setLocation(getElementTextByTagNameNR(domItem,"Location"));
    	seller.setCountry(getElementTextByTagNameNR(domItem,"Country"));
    	seller.setUserId(getElementByTagNameNR(domItem,"Seller").getAttributeNode("UserID").getNodeValue());
    	seller.setRating(Integer.parseInt(getElementByTagNameNR(domItem,"Seller").getAttributeNode("Rating").getNodeValue()));
    	myItem.setSeller(seller);
    	
    	try {
			myItem.setStarted(df.parse(getElementTextByTagNameNR(domItem,"Started")));
			myItem.setEnds(df.parse(getElementTextByTagNameNR(domItem,"Ends")));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.err.println("Could not parse date.");
			System.exit(1);
		}
    	String description = getElementTextByTagNameNR(domItem,"Description");
    	if(description.length()>4000){
    		description = description.substring(0, 3999);
    	}
    	myItem.setDescription(description);
    	return myItem;
    }
    public static void recursiveDescent(Node n, int level) {
        // adjust indentation according to level
        for(int i=0; i<4*level; i++)
            System.out.print(" ");
        
        // dump out node name, type, and value  
        String ntype = typeName[n.getNodeType()];
        String nname = n.getNodeName();
        String nvalue = n.getNodeValue();
        
        System.out.println("Type = " + ntype + ", Name = " + nname + ", Value = " + nvalue);
        
        // dump out attributes if any
        org.w3c.dom.NamedNodeMap nattrib = n.getAttributes();
        if(nattrib != null && nattrib.getLength() > 0)
            for(int i=0; i<nattrib.getLength(); i++)
                recursiveDescent(nattrib.item(i),  level+1);
        
        // now walk through its children list
        org.w3c.dom.NodeList nlist = n.getChildNodes();
        
        for(int i=0; i<nlist.getLength(); i++)
            recursiveDescent(nlist.item(i), level+1);
    }  
    
    public static void main (String[] args) {
        /*if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }*/
        
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. 
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }*/
        File f = new File("items-0.xml");
        processFile(f);
    }
}
