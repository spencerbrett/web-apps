<%@ page import="edu.ucla.cs.cs144.Item" %>
<%@ page import="edu.ucla.cs.cs144.User" %>
<%@ page import="edu.ucla.cs.cs144.Bid" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Basic Search Page">
    <meta name="author" content="Spencer Brett & Daniel Daskalov">

    <title>Item Data</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="icon" type="image/png" href="favicon.ico">
    <!-- Add custom CSS here -->
    <style>
	body {margin-top: 60px;}
    </style>

    <% Item item = (Item) request.getAttribute("itemData"); %>
    <% String geolocation = item.getSeller().getLocation() + " " + item.getSeller().getCountry(); %>

    <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
    <script type="text/javascript">
      function initialize() {
        var address = "<%=geolocation%>";
        geocoder.geocode( { 'address': address}, function(results, status) {
          if (status == google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
              map: map,
              position: results[0].geometry.location
            });
          } else {
            document.writer("Geocode was not successful for the following reason: " + status);
          }
        });
        var latlng = new google.maps.LatLng(34.063509,-118.44541);
        var myOptions = {
          zoom: 14, // default is 8  
          center: latlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map_canvas"),
            myOptions);
      }
    </script>
  </head>

  <body onload="initialize()">
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
          <form class="form-inline" role="form" action="/eBay/item" method="GET">
	    <div class="form-group">
	      <label class="sr-only" for="itemId">Query</label>
	      <input class="form-control" type="text" id="itemId" name="itemId" placeholder="Search">
	    </div>
            <input class="btn btn-default" type="submit" value="Search">
          </form>

          <% if (item == null) { %>
            <h2>Item not found<h2>
          <% } else { %>
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
                -------------------------------------------------------------------------------------------------------------------------------<br>
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
          <% } %>
        </div>
      </div>
      <div id="map_canvas" class="container" style="width:500px; height:500px">
      </div>
    </div><!-- /.container -->

    <!-- Bootstrap core JavaScript -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/bootstrap.js"></script>
  </body>
</html>
