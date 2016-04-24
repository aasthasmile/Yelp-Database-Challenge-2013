/*Student Name:Aastha Jain ,Student ID:014868722*/

/*query3*/
SELECT y.buisness_id,
  y.buisness_name,
  y.review_count
FROM buisness_category x
JOIN
  (SELECT *
  FROM buisness b1
  JOIN
    (SELECT buisness.states,MAX(Review_Count) AS maxcount
    FROM buisness
    GROUP BY buisness.states
    ) b2
  ON b1.states = b2.states
  AND b1.review_count = b2.maxcount
  ) y ON x.BSN_CATG_ID   = y.bsn_catg
AND x.BSN_CATG_NAME         = 'Bars';