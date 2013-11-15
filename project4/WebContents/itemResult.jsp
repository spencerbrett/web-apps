<%@ page import="edu.ucla.cs.cs144.Item" %>
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
Item ID: <%= item.getItemId() %>
</body>

</html>
