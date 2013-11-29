package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.ConfigurationException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        try {
            String itemId = request.getParameter("itemId");

            if (itemId.isEmpty()) {
                response.sendRedirect("/eBay/getItem.html");
                return;
            }

            Item myItem = null;
            String itemData = AuctionSearchClient.getXMLDataForItemId(itemId);
            if (!itemData.isEmpty()) {
                myItem = new Item(itemData);
                myItem.sortBids();
                if (myItem.getBuy_Price() != 0) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("itemId", myItem.getItemID());
                    session.setAttribute("itemName", myItem.getName());
                    session.setAttribute("buyPrice", myItem.getBuy_Price());
                }
            }

            request.setAttribute("itemData", myItem);
            request.getRequestDispatcher("/WEB-INF/itemResult.jsp").forward(
                    request, response);
        } catch (Exception e) {
            response.sendRedirect("/eBay/error.html");
        }
    }
}