/*Student Name:Aastha Jain ,Student ID:014868722*/

/*Query2*/
Select b.BUISNESS_ID,bcd.BSN_CATG_ID,b.BUISNESS_NAME
from BUISNESS b ,BUISNESS_CATEGORY bcd 
where b.BSN_CATG=bcd.BSN_CATG_ID 
AND b.BUISNESS_NAME LIKE '%Coffee%'
AND bcd.BSN_CATG_NAME NOT LIKE '%Coffee%'
Order by b.BUISNESS_NAME ASC;
