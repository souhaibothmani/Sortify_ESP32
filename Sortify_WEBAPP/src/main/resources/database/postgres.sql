CREATE TABLE trash (
                       id SERIAL PRIMARY KEY,
                       time TIMESTAMP NOT NULL,
                       material VARCHAR(50) NOT NULL
);

--Sample data

INSERT INTO trash (time, material)
VALUES ('2024-10-16 14:00', 'Plastic'),
       ('2024-10-16 15:30', 'Metal'),
       ('2024-10-16 16:45', 'Glass');


--Retrieve data
SELECT *
FROM trash;


