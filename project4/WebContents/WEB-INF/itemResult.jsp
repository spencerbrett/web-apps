<%@ page import="edu.ucla.cs.cs144.Item" %>
<%@ page import="edu.ucla.cs.cs144.User" %>
<%@ page import="edu.ucla.cs.cs144.Bid" %>
<html>

<head>
<title>Item Data</title>
</head>

<body>
<form action="/eBay/item" method="GET">
        <input type="text" name="itemId">
        <input type="submit" value="Search">
</form>
<% Item item = (Item) request.getAttribute("itemData"); %>
<h2>Item data:</h2>
ID: <%= item.getItemID() %><br>
Name: <%= item.getName() %><br>
Category: <% for (String cat : item.getCategoryList()) { %>
<%= cat %>, 
<% } %><br>
Description: <%= item.getDescription() %><br>
Seller: <%= item.getSeller().getUserId() %><br>
Seller Rating: <%= item.getSeller().getRating() %><br>
Location: <%= item.getSeller().getLocation() %><br>
Country: <%= item.getSeller().getCountry() %><br>
Started: <%= item.getStarted() %><br>
Ends: <%= item.getEnds() %><br>
<% if(item.getBuy_Price() != 0) { %>
Buy Price: $<%= item.getBuy_Price() %><br>
<% } %>
First Bid: $<%= item.getFirst_Bid() %><br>
Current Bid: $<%= item.getCurrently() %><br>
Number of bids: <%= item.getNumber_of_Bids() %><br>
<% if(item.getNumber_of_Bids() > 0) { %>
Bids:<br>
--------------------------------------------------------------------------------------------------------------------------------<br>
<% } %>
<% for (Bid current : item.getBidList()) { %>
Bidder: <%= current.getBidder().getUserId() %> Rating: <%= current.getBidder().getRating() %>
<% if (current.getBidder().getLocation() != null) { %>
Location: <%= current.getBidder().getLocation() %> 
<% } %>
<% if (current.getBidder().getCountry() != null) { %>
Country: <%= current.getBidder().getCountry() %> 
<% } %>Time: <%= current.getTime() %> Amount: <%= current.getAmount() %><br>
<% } %>
</body>

</html>
