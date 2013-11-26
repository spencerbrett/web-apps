<%@ page import="edu.ucla.cs.cs144.Item" %>
<%@ page import="edu.ucla.cs.cs144.User" %>
<%@ page import="edu.ucla.cs.cs144.Bid" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.ArrayList" %>

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
        var latlng = new google.maps.LatLng(2.2,3.5);
        var myOptions = {
          zoom: 1,
          center: latlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map_canvas"),
            myOptions);

        geocoder = new google.maps.Geocoder();
        var address = "<%=geolocation%>";
        geocoder.geocode( { 'address': address}, function(results, status) {
          if (status == google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
              map: map,
              position: results[0].geometry.location
            });
            if (results[0].geometry.viewport) 
              map.fitBounds(results[0].geometry.viewport);
          } else {
            var country = "<%=item.getSeller().getCountry()%>";
            geocoder.geocode( { 'address': country}, function(results, status) {
              if (status == google.maps.GeocoderStatus.OK) {
                map.setCenter(results[0].geometry.location);
                var marker = new google.maps.Marker({
                  map: map,
                  position: results[0].geometry.location
                });
                if (results[0].geometry.viewport) 
                  map.fitBounds(results[0].geometry.viewport);
              } else {
                // If no country or location results use default settings: world map
              }
            });
          }
        });
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
      <div class="search">
        <div class="row">
          <div class="col-lg-12">
            <form class="form-inline" role="form" action="/eBay/item" method="GET">
	            <div class="form-group">
                <label class="sr-only" for="itemId">Query</label>
	              <input class="form-control" type="text" id="itemId" name="itemId" placeholder="Search">
	            </div>
              <input class="btn btn-default" type="submit" value="Search">
            </form>
          </div>
        </div>
      </div>

      <div id="item-panel">
        <div id="map_canvas" class="container"></div>
        <div id="item-info">
          <% if (item == null) { %>
            <h2>Item not found<h2>
          <% } else { %>
              <% DecimalFormat df = new DecimalFormat("$###,###.00") ;%>
              <span><h3><b><%= item.getName() %> </b></h3></span>(Item #<%= item.getItemID() %>)</span>
              <p><% for (String cat : item.getCategoryList()) { %>
                            |<span style="font-style:italic"> <%= cat %> </span>| 
              <% } %></p>
              <p>by <a href="#"><%= item.getSeller().getUserId() %></a> (<%= item.getSeller().getRating() %>) from <%= item.getSeller().getLocation() %>, <%= item.getSeller().getCountry() %></p>
              <hr />
              <p><h4>
              <% if(item.getBuy_Price() != 0) { %>
                Buy Price: <%= df.format(item.getBuy_Price())%> | 
              <% } %>
              First Bid: <%= df.format(item.getFirst_Bid()) %> | 
              Current: <%= df.format(item.getCurrently()) %></h4> </p>
              <p>Started at <b><%= item.getStarted() %></b> | 
              Ends at <b><%= item.getEnds() %></b></p>
              <hr />
              <p><h4><b>Description</b></h4> </p>
              <p><%= item.getDescription() %></p>
              <hr />
              <p><h4><b>Bids (<%= item.getNumber_of_Bids() %>)</b></h4>

              <% if(item.getNumber_of_Bids() > 0) { %>
              <table>
                <tbody>
                  <tr>
                    <th align="left" width="300px">Bidder</th>
                    <th align="center" width="150px"> Time </th>
                    <th align="right" width="150px">Amount</th>
                  </tr>
              <% for (Bid current : item.getBidList()) { %>
                  <tr>
                    <td align="left" width="300px"><a href="#"><%= current.getBidder().getUserId() %></a> (<%= current.getBidder().getRating() %>)</td>
                    <td align="center" width="150px"><%= current.getTime() %></td>
                    <td align="center" width="150px"><%= df.format(current.getAmount()) %></td>
                  </tr>
              <% } %>
                </tbody>
              </table>
            <% } %>
          <% } %>
        </div>
      </div><!-- /.item-panel -->
    </div><!-- /.container -->

    <!-- Bootstrap core JavaScript -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/bootstrap.js"></script>
  </body>
</html>
