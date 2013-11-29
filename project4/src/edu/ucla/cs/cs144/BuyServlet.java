package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.PrintWriter;

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

//        try {
            HttpSession session = request.getSession(false);
            if(session == null){
                response.sendRedirect("/eBay/keywordSearch.html");
                return;
            }
            PrintWriter out = response.getWriter();
            out.println("Item ID: " + (Integer) session.getAttribute("itemId"));
            out.println("Item Name: " + (String) session.getAttribute("itemName"));
            out.println("Buy Price: " + (Float) session.getAttribute("buyPrice"));
//        } catch (Exception e) {
//            response.sendRedirect("/eBay/error.html");
//        }
    }
}