CREATE TABLE IF NOT EXISTS Player (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(256) DEFAULT NULL,
  age INT DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO Player (name, age) VALUES
  ("Jimmy Page", 74),
  ("Slash", 53),
  ("John Mayer", 39);