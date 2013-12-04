package edu.ucla.cs.cs144;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BuyServlet extends HttpServlet implements Servlet {

    public BuyServlet() {
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect("/eBay/keywordSearch.html");
                return;
            }

            String itemId = request.getParameter("itemId");
            Map<Integer, ItemPurchaseData> itemHistory = (HashMap<Integer, ItemPurchaseData>) session
                    .getAttribute("itemHistory");
            if (itemHistory == null) {
                response.sendRedirect("/eBay/keywordSearch.html");
                return;
            }

            ItemPurchaseData itemData = null;
            if (!itemHistory.containsKey(Integer.parseInt(itemId))) {
                response.sendRedirect("/eBay/keywordSearch.html");
                return;
            } else {
                itemData = itemHistory.get(Integer.parseInt(itemId));
            }

            request.setAttribute("itemId", itemData.getItemId());
            request.setAttribute("itemName", itemData.getItemName());
            request.setAttribute("buyPrice", itemData.getBuyPrice());
            request.getRequestDispatcher("/WEB-INF/buyItem.jsp").forward(
                    request, response);
        } catch (Exception e) {
            response.sendRedirect("/eBay/error.html");
        }
    }
}