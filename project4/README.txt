Team Members:

Name: Spencer Brett
SID: 704071250

Name: Daniel Daskalov
SID: 504079134

Submission for project 4 part B

NOTE: We are using two grace period days for this submission.

We have some additions from part A that invlove more styling of the item result
page and some other additions like an error page.

If we get no result for the whole address string from the google API for
geocoding we only check the country. If that also doesn't work we display a
default world map with no markers.

The dropdown was mostly build using the tutorial provided on the project
page plus the example for google suggest in the notes.

We chose not to include the type ahead functionality of the tutorial because
it seemed to contain a race condition and a fast typist would find it
annoying and inconvenient. We also added some improvements to the dropdown
like cycling back to top or bottom when using the arrow keys and others.

We disabled Firefox's autocomplete through an HTML option so it is not required
to disable the setting in the browser options.

We are also not displaying the item IDs in the search results page for
a better look. We display the item ID in the item information page. We also
don't display the country and location of the bidders to create a less
cluttered look and because a person viewing the page doesn't really need to
know that information unless he is the seller and our website doesn't have
authentication and login.

We used tweeter bootstrap for the rest of the styling.
