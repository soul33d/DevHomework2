USE homework1;

SELECT
  pro.*,
  sum(dev.salary) AS sum
FROM projects pro
  JOIN projects_developers pd
    ON pro.id = pd.project_id
  JOIN developers dev
    ON dev.id = pd.developer_id
GROUP BY pro.id
ORDER BY sum DESC
LIMIT 1;