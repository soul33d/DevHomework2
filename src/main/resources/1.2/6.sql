USE homework1;

SELECT
  lpd.project_id,
  avg(d.salary) AS avg_developer_salary
FROM developers d
  JOIN (SELECT *
        FROM projects_developers pd
          JOIN (SELECT *
                FROM projects p2
                  JOIN (SELECT min(p.cost) AS min_cost
                        FROM projects p) AS p3
                    ON p2.cost = p3.min_cost) AS p4
            ON pd.project_id = p4.id) AS lpd
    ON d.id = lpd.developer_id
GROUP BY lpd.project_id;