/*Student Name:Aastha Jain ,Student ID:014868722*/

/*query7*/

DROP VIEW USEFUL_VOTES;
/

CREATE VIEW useful_votes AS
  SELECT CONCAT(CONCAT(y.F_NAME, ' '), y.L_NAME) AS USERNAME, r.author_id as USERID, sum(r.VOTES_USEFUL) AS sum1
  from REVIEWS r 
  INNER JOIN USER_YELP y ON y.USER_ID=r.AUTHOR_ID
  GROUP BY r.author_id, y.F_NAME, y.L_NAME; 
/
Select USERNAME,USERID,SUM1
from USEFUL_VOTES
where SUM1=(Select Max(SUM1)
            from USEFUL_VOTES);
            
