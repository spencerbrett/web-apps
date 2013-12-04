package edu.ucla.cs.cs144;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConfirmServlet extends HttpServlet implements Servlet {

    public ConfirmServlet() {
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        try {
            String path = "http://" + request.getServerName() + ":8080"
                    + request.getContextPath();
            String securePath = "https://" + request.getServerName() + ":8443"
                    + request.getContextPath();

            if (!request.isSecure()) {
                response.sendRedirect(path + "/keywordSearch.html");
                return;
            }

            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect(path + "/keywordSearch.html");
                return;
            }

            String creditCard = request.getParameter("creditCard");
            String itemId = request.getParameter("itemId");
            creditCard = maskCardNumber(creditCard);

            Map<Integer, ItemPurchaseData> itemHistory = (HashMap<Integer, ItemPurchaseData>) session
                    .getAttribute("itemHistory");
            if (itemHistory == null) {
                response.sendRedirect(path + "/keywordSearch.html");
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
            request.setAttribute("card", creditCard);
            request.setAttribute("time", new Date());

            itemHistory.remove(Integer.parseInt(itemId));
            session.setAttribute("itemHistory", itemHistory);
            
            request.getRequestDispatcher("/WEB-INF/purchaseConfirmation.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/eBay/error.html");
        }
    }

    // Assumption that length has been validated before
    private String maskCardNumber(String card) {
        String lastFour = card.substring(card.length() - 4);
        char[] array = new char[card.length()];
        Arrays.fill(array, '*');
        return new String(array) + lastFour;
    }
}