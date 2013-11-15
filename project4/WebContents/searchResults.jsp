<%@ page import="edu.ucla.cs.cs144.SearchResult" %>
<html>

<head>
	<title>Search Results</title>
</head>

<body>
<form action="/eBay/search" method="GET">
	<input type="text" name="q">
        <input type="submit" value="Search">
        <input type="hidden" name="numResultsToSkip" value="0">
        <input type="hidden" name="numResultsToReturn" value="20">
</form>
<% String query = request.getParameter("q");
   String numToSkip = request.getParameter("numResultsToSkip");
   String numToReturn = request.getParameter("numResultsToReturn");
   Integer numResultsToSkip = new Integer(numToSkip);
   Integer numResultsToReturn = new Integer(numToReturn);
   SearchResult[] results = (SearchResult[]) request.getAttribute("searchResults"); %>
<h2>Search Results for "<%= query %>":</h2>
<% for (SearchResult result : results) { %>
	<a href="/eBay/item?itemId=<%= result.getItemId() %>"><%= result.getItemId() %></a>: <%= result.getName() %><br>
<% } %>
<br>
<% if (numResultsToSkip == 0) { %>
	&lt;- Prev Page
<% } else { %>
	<a href="/eBay/search?q=<%= query %>&amp;numResultsToSkip=<%= numResultsToSkip-20 %>&amp;numResultsToReturn=20">&lt;- Prev Page</a>
<% } %>
 | 
<% if (results.length < 20) { %>
	Next Page -&gt;
<% } else { %>
	<a href="/eBay/search?q=<%= query %>&amp;numResultsToSkip=<%= numResultsToSkip+20 %>&amp;numResultsToReturn=20">Next Page -&gt;</a>
<% } %>
</body>

</html>
