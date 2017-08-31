USE homework1;

ALTER TABLE projects
  ADD cost FLOAT;

UPDATE projects
SET cost = 80000
WHERE id = 1;

UPDATE projects
SET cost = 50000
WHERE id = 2;

UPDATE projects
SET cost = 20000
WHERE id = 3;