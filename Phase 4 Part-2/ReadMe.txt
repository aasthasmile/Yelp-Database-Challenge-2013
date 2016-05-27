Howework-4 Part-2 NoSQL
Student Name: Aastha Jain
Student ID: 014868722
Database:mongodb

Use Mongodb 3.2.2 version

Step-1
Import data to mongodb using Command prompt:
mongoimport --db yelpdb --collection business --file e:/zips.json
mongoimport --db yelpdb --collection review --file e:/zips.json
mongoimport --db yelpdb --collection user --file e:/zips.json
mongoimport --db yelpdb --collection checkin--file e:/zips.json

Step-2
Run "mongod" command to start connection to localhost on 27017.

Step-3
Add these External Jars to Eclipse
1.mongo-java-driver 3.2.2.jar
2.bson-3.0.4-sources.jar
3.json-simple-1.1.jar


Step-4 
Run hw4.java
