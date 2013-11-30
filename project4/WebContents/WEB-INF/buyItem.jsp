<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Item purchase preview page">
    <meta name="author" content="Spencer Brett & Daniel Daskalov">

    <title>Purchase preview</title>

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
       DecimalFormat df = new DecimalFormat("$###,###.00");
       String path = "https://" + request.getServerName() + ":8443" + request.getContextPath() + "/confirm"; %>
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
            <li><a class="button active" href="index.html">Home</a></li>
            <li><a class="button" href="keywordSearch.html">Keyword Search</a></li>
            <li><a class="button" href="getItem.html">ID Search</a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container -->
    </nav>

    <div class="container">

      <div class="row">
        <div class="col-lg-12">
          <h2>Item Purchase Preview</h2>
          <p><b>ID:</b> <%= itemId %></p>
          <p><b>Name:</b> <%= itemName %></p>
          <p><b>Buy price:</b> <%= df.format(buyPrice) %></p>
          <p><b>Enter a credit card number:</b></p>
          <form class="form-inline" role="form" action="<%= path %>" method="POST">
            <div class="form-group">
              <label class="sr-only" for="creditCard">#</label>
              <input class="form-control" type="text" id="creditCard" name="creditCard" placeholder="#" autocomplete="off">
            </div>
            <input class="btn btn-primary" type="submit" value="Buy">
          </form>
        </div>
      </div>

    </div><!-- /.container -->

  </body>
</html>
