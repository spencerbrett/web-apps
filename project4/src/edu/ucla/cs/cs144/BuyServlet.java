package edu.ucla.cs.cs144;

import java.io.IOException;

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

            request.setAttribute("itemId", (Integer) session
                    .getAttribute("itemId"));
            request.setAttribute("itemName", (String) session
                    .getAttribute("itemName"));
            request.setAttribute("buyPrice", (Float) session
                    .getAttribute("buyPrice"));
            request.getRequestDispatcher("/WEB-INF/buyItem.jsp").forward(
                    request, response);
        } catch (Exception e) {
            response.sendRedirect("/eBay/error.html");
        }
    }
}