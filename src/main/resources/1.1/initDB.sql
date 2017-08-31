CREATE DATABASE homework1;
USE homework1;
CREATE TABLE developers (
  id         INT AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name  VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE TABLE skills (
  id   INT AUTO_INCREMENT,
  name VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE TABLE developers_skills (
  developer_id INT NOT NULL,
  skill_id     INT NOT NULL,
  PRIMARY KEY (developer_id, skill_id),
  FOREIGN KEY (developer_id) REFERENCES developers (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (skill_id) REFERENCES skills (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE projects (
  id   INT AUTO_INCREMENT,
  name VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE TABLE projects_developers (
  project_id   INT NOT NULL,
  developer_id INT NOT NULL,
  PRIMARY KEY (project_id, developer_id),
  FOREIGN KEY (project_id) REFERENCES projects (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (developer_id) REFERENCES developers (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE companies (
  id   INT AUTO_INCREMENT,
  name VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE TABLE companies_developers (
  company_id   INT NOT NULL,
  developer_id INT NOT NULL,
  PRIMARY KEY (company_id, developer_id),
  FOREIGN KEY (company_id) REFERENCES companies (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (developer_id) REFERENCES developers (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE companies_projects (
  company_id INT NOT NULL,
  project_id INT NOT NULL,
  PRIMARY KEY (company_id, project_id),
  FOREIGN KEY (company_id) REFERENCES companies (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (project_id) REFERENCES projects (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE customers (
  id         INT AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name  VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE TABLE customers_projects (
  customer_id INT NOT NULL,
  project_id  INT NOT NULL,
  PRIMARY KEY (customer_id, project_id),
  FOREIGN KEY (customer_id) REFERENCES customers (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (project_id) REFERENCES projects (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
