/* Find the number of users in the database. */
SELECT COUNT(*)
FROM User;

/* Find the number of sellers from "New York", (i.e., sellers whose location is exactly the string "New York"). Pay special attention to case sensitivity. You should match the sellers from "New York" but not from "new york". */
SELECT COUNT(DISTINCT u.UserID)
FROM User AS u INNER JOIN Item AS i
ON u.UserID = i.SellerID
WHERE u.Location COLLATE latin1_general_cs = "New York";

/* Find the number of auctions belonging to exactly four categories. */
SELECT COUNT(*)
FROM Item AS t1 INNER JOIN 
(SELECT ItemID, count(Category) AS CatCount
FROM Category
GROUP BY ItemID) AS t2 
ON t1.ItemID = t2.ItemID
WHERE CatCount = 4;

/* Find the ID(s) of current (unsold) auction(s) with the highest bid. Remember that the data was captured at the point in time December 20th, 2001, one second after midnight, so you can use this time point to decide which auction(s) are current. Pay special attention to the current auctions without any bid. */
SELECT ITEMID
FROM Item 
WHERE Number_of_Bids > 0 AND
Currently = (SELECT max(Currently)
FROM Item 
WHERE Ends > UNIX_TIMESTAMP('2001-12-20 00:00:00')
AND Number_of_Bids > 0);

/* Find the number of sellers whose rating is higher than 1000. */
SELECT COUNT(DISTINCT u.UserID)
FROM User AS u INNER JOIN Item AS i
ON u.UserID = i.SellerID
WHERE u.Rating > 1000;

/* Find the number of users who are both sellers and bidders. */
SELECT COUNT(DISTINCT i.SellerID)
FROM Item AS i INNER JOIN Bid AS b
ON i.SellerID = b.UserID;

/* Find the number of categories that include at least one item with a bid of more than $100. */
SELECT COUNT(DISTINCT CATEGORY)
FROM Category AS c INNER JOIN Item AS i
ON c.ItemID = i.ItemID
WHERE Currently > 100 AND Number_of_Bids > 0;
