USE homework1;

INSERT INTO developers (first_name, last_name)
VALUES ('Ivan', 'Dulin');
INSERT INTO developers (first_name, last_name)
VALUES ('Eugene', 'Sklyarov');
INSERT INTO developers (first_name, last_name)
VALUES ('Vasya', 'Pupkin');
INSERT INTO developers (first_name, last_name)
VALUES ('Yelena', 'Neznaykina');

INSERT INTO skills (name)
VALUES ('Java');
INSERT INTO skills (name)
VALUES ('Android');
INSERT INTO skills (name)
VALUES ('C++');
INSERT INTO skills (name)
VALUES ('PHP');

INSERT INTO developers_skills (developer_id, skill_id)
VALUES (1, 3);
INSERT INTO developers_skills (developer_id, skill_id)
VALUES (1, 4);
INSERT INTO developers_skills (developer_id, skill_id)
VALUES (2, 1);
INSERT INTO developers_skills (developer_id, skill_id)
VALUES (2, 2);
INSERT INTO developers_skills (developer_id, skill_id)
VALUES (3, 1);
INSERT INTO developers_skills (developer_id, skill_id)
VALUES (4, 1);

INSERT INTO projects (name)
VALUES ('Tetris');
INSERT INTO projects (name)
VALUES ('Mario');
INSERT INTO projects (name)
VALUES ('Calculator');

INSERT INTO projects_developers (project_id, developer_id)
VALUES (1, 1);
INSERT INTO projects_developers (project_id, developer_id)
VALUES (1, 3);
INSERT INTO projects_developers (project_id, developer_id)
VALUES (1, 2);
INSERT INTO projects_developers (project_id, developer_id)
VALUES (2, 2);
INSERT INTO projects_developers (project_id, developer_id)
VALUES (3, 4);

INSERT INTO companies (name)
VALUES ('GoIT');
INSERT INTO companies (name)
VALUES ('EPAM');

INSERT INTO companies_developers (company_id, developer_id)
VALUES (1, 1);
INSERT INTO companies_developers (company_id, developer_id)
VALUES (1, 2);
INSERT INTO companies_developers (company_id, developer_id)
VALUES (1, 3);
INSERT INTO companies_developers (company_id, developer_id)
VALUES (2, 2);
INSERT INTO companies_developers (company_id, developer_id)
VALUES (2, 4);

INSERT INTO companies_projects (company_id, project_id)
VALUES (1, 1);
INSERT INTO companies_projects (company_id, project_id)
VALUES (2, 2);
INSERT INTO companies_projects (company_id, project_id)
VALUES (2, 3);

INSERT INTO customers (first_name, last_name)
VALUES ('Buratino', 'Karlione');
INSERT INTO customers (first_name, last_name)
VALUES ('Sergey', 'Burkatovskiy');
INSERT INTO customers (first_name, last_name)
VALUES ('Ivan', 'Popov');

INSERT INTO customers_projects (customer_id, project_id)
VALUES (1, 1);
INSERT INTO customers_projects (customer_id, project_id)
VALUES (2, 2);
INSERT INTO customers_projects (customer_id, project_id)
VALUES (3, 3);
