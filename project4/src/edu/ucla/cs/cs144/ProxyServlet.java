package edu.ucla.cs.cs144;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyServlet extends HttpServlet implements Servlet {

    public ProxyServlet() {
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
      
        String query = request.getParameter("query");
        String charset = "UTF-8";

        URL url = new URL("http://google.com/complete/search?output=toolbar&q="
                + URLEncoder.encode(query, charset));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Charset", charset);
        conn.setRequestProperty("Content-type", "text/xml");

        StringBuffer content = new StringBuffer();
        BufferedReader googleResponse = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = googleResponse.readLine()) != null) {
          content.append(line + "\n");
        }
        
        conn.disconnect();
        
        response.setContentType("text/xml;charset=UTF-8");
        out.write(content.toString());
    }
}
