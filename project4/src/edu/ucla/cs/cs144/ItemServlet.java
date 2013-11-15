package edu.ucla.cs.cs144;

import java.io.IOException;

import javax.naming.ConfigurationException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ItemServlet extends HttpServlet implements Servlet {

    public ItemServlet() {
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String itemId = request.getParameter("itemId");

        String itemData = AuctionSearchClient.getXMLDataForItemId(itemId);

//        try {
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
//                    .newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(itemData);
//            
//            NodeList nList = doc.getElementsByTagName("Item");
//            
//            Item myItem = new Item();
//            
//        } catch (ParserConfigurationException e) {
//            System.out.println(e);
//        } catch (SAXException e) {
//            System.out.println(e);
//        }

        request.setAttribute("itemData", itemData);
        request.getRequestDispatcher("itemResult.jsp").forward(request,
                response);
    }
}
