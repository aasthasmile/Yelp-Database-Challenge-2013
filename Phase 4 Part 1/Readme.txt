Now, you need to write some queries for the assignment in order to answer the following questions:
1. Find all the states that have a city called BOSTON.

2. Each city may have several zip codes. Find the city in each state with the largest number of zip codes. 
Then print those cities along with their states using the city population for ordering (the cities should be ordered by their population.)

3. MongoDB can query spatial information. Notice that for each zip code we use the latitude and longitude to store the location of the 
center of each zip code area. Now, consider the following location: [-72, 42] and the range (circle) of radius 2 around this point. Write 
a query to find all the states that intersect this range (circle). Then, you should return the total population and the number of cities 
for each of these states. Rank the states based on number of cities.
Hint: you may have to use a 2-dimensional index and the geoNear command.
See: http://docs.mongodb.org/manual/reference/command/geoNear/ and http://docs.mongodb.org/manual/core/2d/

4. Consider a rectangular area with corners: [ -80 , 30 ] , [ -90 , 30 ] , [ -90 , 40 ] and [ -80 , 40 ].
Write a query to find the top 10 largest cities in this area. You have to use the geoWithin command:
http://docs.mongodb.org/manual/reference/operator/geoWithin/
