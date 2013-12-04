Team Members:

Name: Spencer Brett
SID: 704071250

Name: Daniel Daskalov
SID: 504079134

Submission for project 5

NOTE: We are using our last grace period day for this submission.

Answers to questions:

Q1: We use SSL for (4)->(5) and (5)->(6).

Q2: We use sessions that store the required item data. We store all items with
    a buy price in the session to avoid a bug mentioned later in the readme.
    The buy forms provide the item ID to be bought only and the server gets
    the required data from the session.

Q3: We use the meta tag "viewport" which sets the page width to the device
    screen width. This makes the page not scroll horizontally.

Q4: We use a css media query that engages if the screen is less than 500px.
    This sets the width of the box to 100% (relative to the size set from
    the viewport) so it fills the screen of the device. The non mobile
    version uses 300px as width.

Additional Notes:

Our website gets a session for the visitor if they visit an item page where
the item has a buy price, otherwise they don't get a session for any other
items since nothing can be done with them except view. In a real implementation
we would also have sessions for the other possible transactions.

The session keeps track of all items with a buy price that the user has visited
in the current session. This is to avoid the problem of opening an item page
then opening another overriding the session data and then going back and
purchasing the first item. Since the data is overridden the user will actually
get the page to buy the second item. In our implementation the forms provide
the item ID to be bought and then the server gets the ascociated data from the
session information for that item. The user still can't compromise the data of
the item. The worst they can do is change the flow of the website by trying to
buy a different item from the one they clicked on but they can't change the 
price of that second item.

We also chose to display the credit card number on the confirmation page by
only showing the last 4 digits and using * for the rest as most websites do.
We asked on Piazza and we were told that is OK. We still use https for the
credit card transactions.

We do a form verification for the credit card number so it conforms to the
limit of 12-18 characters judging by a google search on possible card numbers,
and we only allow the characters 0-9 with no spaces or dashes for simplicity.

We used twitter bootstrap for styling for most of our website (except things we
were not allowed to like the suggestion dropdown). Since bootstrap
automatically adjsuts to mobile we had to disable it for our keywordSearch.html
page. We commented out the twitter css and didn't want to try to get it
consistent manually so for project 5 the keywordSearch.html page is not really
styled. We haven't made the rest of the pages for mobile but
since they use bootsrtap they pretty much work with minor exceptions.
