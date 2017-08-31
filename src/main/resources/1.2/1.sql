USE homework1;

ALTER TABLE developers
  ADD salary FLOAT;

UPDATE developers
SET salary = 1900.21
WHERE id = 1;

UPDATE developers
SET salary = 5000.5
WHERE id = 2;

UPDATE developers
SET salary = 3000
WHERE id = 3;

UPDATE developers
SET salary = 1000
WHERE id = 4;