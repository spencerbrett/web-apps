<%@ page import="edu.ucla.cs.cs144.SearchResult" %>
<html>

<head>
	<title>Search Results</title>
</head>

<body>
<h2>Search Results for <%= request.getParameter("q") %>:</h2>
<% for (SearchResult result : (SearchResult[]) request.getAttribute("searchResults")) { %>
	<%= result.getName() %><br>
<% } %>
</body>

</html>
