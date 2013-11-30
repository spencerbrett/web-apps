<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Item purchase confirmation page">
    <meta name="author" content="Spencer Brett & Daniel Daskalov">

    <title>Purchase confirmation</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="icon" type="image/png" href="favicon.ico">
    <!-- Add custom CSS here -->
    <style>
	body {margin-top: 60px;}
    </style>
    <% Integer itemId = (Integer) request.getAttribute("itemId");
       String itemName = (String) request.getAttribute("itemName");
       Float buyPrice = (Float) request.getAttribute("buyPrice");
       String creditCard = (String) request.getAttribute("card");
       Date transactionDate = (Date) request.getAttribute("time");
       SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
       String time = sdf.format(transactionDate);
       DecimalFormat df = new DecimalFormat("$###,###.00");
       String returnPath = "http://" + request.getServerName() + ":8080" + request.getContextPath(); %>
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
          <a class="navbar-brand" href="<%= returnPath + "keywordSearch.html" %>">eBay Search</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav">
            <li><a class="button active" href="<%= returnPath + "index.html" %>">Home</a></li>
            <li><a class="button" href="<%= returnPath + "keywordSearch.html" %>">Keyword Search</a></li>
            <li><a class="button" href="<%= returnPath + "getItem.html" %>">ID Search</a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container -->
    </nav>

    <div class="container">

      <div class="row">
        <div class="col-lg-12">
          <h2>Purchase Confirmation</h2>
          <p><b>ID:</b> <%= itemId %></p>
          <p><b>Name:</b> <%= itemName %></p>
          <p><b>Buy price:</b> <%= df.format(buyPrice) %></p>
          <p><b>credit card number:</b> <%= creditCard %></p>
          <p><b>Transaction time:</b> <%= time %></p>
          <a class="btn btn-primary" href="<%= returnPath + "/keywordSearch.html" %>">Back to search</a>
        </div>
      </div>

    </div><!-- /.container -->

  </body>
</html>
