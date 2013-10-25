Project 2 partners:

Name: Daniel Daskalov
UID: 504079134

Name: Spencer Brett
UID: 704071250

Part B answers:

1. Relations

Items(item_id, item_name, current_bid, first_bid, num_bids, Buy_price, seller_id, started, ends, description) -> key: item_id

Users(user_id, rating, location, country) -> key: user_id

Categories(category_name, item_id) -> key: category_name

Bids(item_id, user_id, time, amount) -> key: item_id, user_id, time

2. FDs

Item_ID -> Name, Currently, Buy_price, First_bid, Number of bids, user_id, rating, location, country, started, ends, description
User_ID -> rating, location, country
Item_Id, User_ID, Time -> amount

3. Check of FDs

R1(item_id, item_name, current_bid, first_bid, Buy_price, num_bids, seller_id, seller_rating, seller_location, seller_country, started, ends, description)
R2(item_id, item_name, current_bid, first_bid, num_bids, seller_id, started, ends, description)
R3(seller_id, seller_rating, seller_location, seller_country)


