<html>

<head>
<title>Item Data</title>
</head>

<body>
<form action="/eBay/item" method="GET">
        <input type="text" name="itemId">
        <input type="submit" value="Search">
</form>
<h2>Item data:</h2>
<%= request.getAttribute("itemData") %>
</body>

</html>
