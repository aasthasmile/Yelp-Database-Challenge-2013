Student Name: Aastha Jain
Student ID: 014868722

Recommend to run on Oracle 12cR2.


Table created:
USER_YELP
BUISNESS
BUISNESS_CATEGORY
REVIEWS
FRIEND
PHOTO
USER_FRIEND 
USER_COMPLIMENTS_FRIENDS 
PROVIDE_USER_REVIEWS 
VOTES_USER_REVIEWS 
BELONG_T0_REVIEW_BUISNESS 
BELONG_TO_PHOTO_BUISNESS 
MARKED_USER_PHOTO 


File description:
createdb.sql: create the table above and do the basic insert
drop.sql: drop all the table and object

q1.sql. 1. Count the number of businesses in California (“CA”).

q2.sql  2. Find every business in California that has the word “Coffee” (case-sensitive) in its name
but is not classified as a coffee place (i.e. has no word “Coffee” in any of its categories).
List the business id, bid, and name in ascending bid order.

q3.sql 3.For each state, give the most popular bar in that state. “Popular” means that it has the highest
count of reviews, among all establishments that have one category as “Bars”, in said state.
Sort by state name (increasing). In the remote case of a tie in first place, list all bars, in
ascending bid order. More specifically, for each bar, print its bid (i.e. business id), name,
number of reviews and state.

q4.sql 4.List the top 10 businesses in San Jose, California for breakfastor brunch. “Top” means
that it has the highest average number of stars among all businesses that have one category
as “Breakfast & Brunch”. Sort by their scores and break ties with number of reviews
received (decreasing), then bid (increasing). For each business, print its bid (business id),
name, average number of stars, and number of reviews.

q5.sql 5.List users that have been to more than 5 distinct states. Order by number of states traveled
to (decreasing), break ties with uid (user id, increasing). For each user, list his/her uid,
name and number of states traveled to.

q6.sql 6. List the top 5 burger restaurants (i.e. have the highest average number of stars) in San
Jose,California. “Top” means that it has the highest score among all businesses in San
Jose, California that have one category as “Burgers”. Sort by score (highest, first) and
break ties with number of reviews by travelers (decreasing), then bid (increasing). For
each restaurant, print its bid, name, score, and numberof traveler reviews received.

q7.sql  7.Find the user with the highest sum of the ’useful’ votes that his/her reviews have
attracted. If there is a tie in first place, list allsuch users, ordered by uid (i.e. user id).
For each user, print his/her uid (i.e. userid), name, and usefulness count.

q8.sql 8.List California businesses that have more than 10 reviews and all of them are “5 stars”.
Order by the number of reviews received (decreasing), and bid (i.e. business id,
increasing). For each business, print bid, name and number of reviews.

q9.sql 9.Find the businesses that got more than 50 5-star reviews from users that have only voted
once. For each business, print its bid (i.e. business id), name and the number of reviews
from such users. Order by the number of reviews received (decreasing), then business name
(increasing) then bid (increasing).

q10.sql 10.Find the businesses whose average rating was raised by more than 1 stars from May 2011 to
June 2011. Order your results by the magnitude o f the jump (largest, first), then business
names (increasing) and id (increasing).Print the first 10 if there are more than 10. For
each business, print its bid (i.e.business id), name and jump magnitude.



