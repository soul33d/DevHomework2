USE homework1;

SELECT pccus_sum.*
FROM (SELECT
        pccus_sum.company_id,
        min(pccus_sum.customer_sum) AS min
      FROM (SELECT
              pccus.company_id,
              pccus.customer_id,
              sum(pccus.cost) AS customer_sum
            FROM (SELECT
                    p.*,
                    c.id   AS company_id,
                    cus.id AS customer_id
                  FROM projects p
                    JOIN companies_projects cp
                      ON p.id = cp.project_id
                    JOIN companies c
                      ON c.id = cp.company_id
                    JOIN customers_projects cus_p
                      ON cus_p.project_id = p.id
                    JOIN customers cus
                      ON cus.id = cus_p.customer_id) AS pccus
            GROUP BY pccus.company_id, pccus.customer_id) AS pccus_sum
      GROUP BY pccus_sum.company_id) AS pccus_min
  JOIN ((SELECT
           pccus.company_id,
           pccus.company_name,
           pccus.customer_id,
           pccus.customer_first_name,
           pccus.customer_last_name,
           sum(pccus.cost) AS customer_sum
         FROM (SELECT
                 p.*,
                 c.id           AS company_id,
                 c.name         AS company_name,
                 cus.id         AS customer_id,
                 cus.first_name AS customer_first_name,
                 cus.last_name  AS customer_last_name
               FROM projects p
                 JOIN companies_projects cp
                   ON p.id = cp.project_id
                 JOIN companies c
                   ON c.id = cp.company_id
                 JOIN customers_projects cus_p
                   ON cus_p.project_id = p.id
                 JOIN customers cus
                   ON cus.id = cus_p.customer_id) AS pccus
         GROUP BY pccus.company_id, pccus.customer_id) AS pccus_sum)
    ON pccus_min.min = pccus_sum.customer_sum
WHERE pccus_sum.company_id = pccus_min.company_id;