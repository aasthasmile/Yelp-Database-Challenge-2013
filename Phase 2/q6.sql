/*Student Name:Aastha Jain ,Student ID:014868722*/

/*Query6*/

Select Y.USER_ID,Y.F_NAME,B.ADDRESS,COUNT(*)
  FROM USER_YELP Y, REVIEWS R ,BUISNESS B 
  WHERE Y.USER_ID =R.AUTHOR_ID
  AND B.BUISNESS_ID=R.B_ID
  group by y.USER_ID, y.F_NAME, b.ADDRESS;
  
  
  
  