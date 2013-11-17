<%@ page import="edu.ucla.cs.cs144.SearchResult" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Basic Search Page">
    <meta name="author" content="Spencer Brett & Daniel Daskalov">

    <title>eBay Search</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="icon" type="image/png" href="favicon.ico">
    <!-- Add custom CSS here -->
    <style>
	body {margin-top: 60px;}
    </style>
  </head>

  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="keywordSearch.html">eBay Search</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav">
            <li><a class="button" href="index.html">Home</a></li>
            <li><a class="button active" href="keywordSearch.html">Keyword Search</a></li>
            <li><a class="button" href="getItem.html">ID Search</a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container -->
    </nav>

    <div class="container">

      <div class="row">
        <div class="col-lg-12">
          <form class="form-inline" role="form" action="/eBay/search" method="GET">
            <div class="form-group">
              <label class="sr-only" for="q">Query</label>
              <input class="form-control" type="text" id="q" name="q" placeholder="Search">
            </div>
            <input class="btn btn-default" type="submit" value="Search">
            <input type="hidden" name="numResultsToSkip" value="0">
            <input type="hidden" name="numResultsToReturn" value="20">
          </form>
        
       <% String query = request.getParameter("q");
          String numToSkip = request.getParameter("numResultsToSkip");
          String numToReturn = request.getParameter("numResultsToReturn");
          Integer numResultsToSkip = new Integer(numToSkip);
          Integer numResultsToReturn = new Integer(numToReturn);
          SearchResult[] results = (SearchResult[]) request.getAttribute("searchResults"); %>
        
          <h2>Search Results for &quot;<%= query %>&quot;:</h2>
          <div class="list-group">
            <% for (SearchResult result : results) { %>
              <a href="/eBay/item?itemId=<%= result.getItemId() %>" class="list-group-item"><%= result.getName() %></a>
            <% } %>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-12">
          <ul class="pager">
            <% if (numResultsToSkip == 0) { %>
              <li class="previous disabled"><a href="#">&larr; Prev</a></li>
            <% } else { %>
              <li class="previous"><a href="/eBay/search?q=<%= query %>&amp;numResultsToSkip=<%= numResultsToSkip-20 %>&amp;numResultsToReturn=20">&larr; Prev</a></li>
            <% } %>

            <% if (results.length < 20) { %>
              <li class="next disabled"><a href="#">Next &rarr;</a></li>
            <% } else { %>
              <li class="next"><a href="/eBay/search?q=<%= query %>&amp;numResultsToSkip=<%= numResultsToSkip+20 %>&amp;numResultsToReturn=20">Next &rarr;</a></li>
            <% } %>
          </ul>
        </div>
      </div>
    </div><!-- /.container -->

    <!-- Bootstrap core JavaScript -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/bootstrap.js"></script>
  </body>
</html>
