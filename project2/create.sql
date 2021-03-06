CREATE TABLE IF NOT EXISTS Item (
ItemID INT UNSIGNED NOT NULL PRIMARY KEY,
Name VARCHAR(128) NOT NULL,
Currently DECIMAL(8,2) NOT NULL,
First_Bid DECIMAL(8,2) NOT NULL,
Buy_price DECIMAL(8,2),
Number_of_Bids INT NOT NULL,
Started TIMESTAMP NOT NULL,
Ends TIMESTAMP NOT NULL,
Description VARCHAR(4000),
SellerID VARCHAR(32) NOT NULL,
FOREIGN KEY (SellerID) REFERENCES Users(UserID) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS User (
UserID VARCHAR(32) NOT NULL PRIMARY KEY,
Rating INT NOT NULL,
Location VARCHAR(128),
Country VARCHAR(64));

CREATE TABLE IF NOT EXISTS Category (
Category VARCHAR(128) NOT NULL,
ItemID INT UNSIGNED NOT NULL,
UNIQUE KEY `cat_key` (Category,ItemID),
FOREIGN KEY(ItemID) REFERENCES Item(ItemID) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS Bid (
UserID VARCHAR(32) NOT NULL,
ItemID INT UNSIGNED NOT NULL,
Time TIMESTAMP NOT NULL,
Amount DECIMAL(8,2) NOT NULL,
FOREIGN KEY(ItemID) REFERENCES Item(ItemID) ON DELETE CASCADE,
FOREIGN KEY(UserID) REFERENCES User(UserID) ON DELETE CASCADE,
UNIQUE KEY `bid_key` (UserID, ItemID, Time));


