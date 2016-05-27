Project Details:

The Yelp data is available in JSON format. The original Yelp dataset includes 42,153 businesses, 252,898 users, 
and 1,125,458 reviews from Phoenix (AZ), Las Vegas (NV), Madison (WI) in United States and Waterloo (ON) and
Edinburgh (ON) in Canada. (http://www.yelp.com/dataset_challenge/). In your project you will use a smaller and 
simplified dataset. This simplified dataset includes only 20,544 businesses, the reviews that are written for those
businesses only, and the users that wrote those reviews.

Part 1
- Download the Yelp dataset from Camino. Look at each JSON file and understand what information the
JSON objects provide. Pay attention to the data items in JSON objects that you will need for your
application (For example, categories, attributes,…etc.)

- You may have to modify your database design from Homework 2 to model the database for the described
application scenario on page-1. Your database schema doesn’t necessarily need to include all the data items
provided in the JSON files. Your schema should be precise but yet complete. It should be designed in such a
way that all queries/data retrievals on/from the database run efficiently and effectively.

- Produce DDL SQL statements for creating the corresponding tables in a relational DBMS. Note the
constraints, including key constraints, referential integrity constraints, not NULL constraints, etc. needed for
the relational schema to capture and enforce the semantics of your ER design.

- Populate your database with the Yelp data. Generate INSERT statements for your tables and run those to
insert data into your DB.


References:
1. Yelp Dataset Challenge, http://www.yelp.com/dataset_challenge/
2. Samples for users of the Yelp Academic Database,https://github.com/Yelp/dataset-examples