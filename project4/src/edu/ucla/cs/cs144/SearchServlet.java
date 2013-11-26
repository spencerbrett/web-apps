package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {

    public SearchServlet() {
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        try {
            String searchQuery = request.getParameter("q");

            if (searchQuery.isEmpty()) {
                response.sendRedirect("/eBay/keywordSearch.html");
                return;
            }

            Integer numResultsToSkip = new Integer(request
                    .getParameter("numResultsToSkip"));
            Integer numResultsToReturn = new Integer(request
                    .getParameter("numResultsToReturn"));

            SearchResult[] basicSearchResult = AuctionSearchClient.basicSearch(
                    searchQuery, numResultsToSkip, numResultsToReturn);

            request.setAttribute("searchResults", basicSearchResult);
            request.getRequestDispatcher("/WEB-INF/searchResults.jsp").forward(
                    request, response);
        } catch (Exception e) {
            response.sendRedirect("/eBay/error.html");
        }
    }
}
