CREATE TABLE trash (
                       id SERIAL PRIMARY KEY,
                       time TIMESTAMP NOT NULL,
                       material VARCHAR(50)
);

--Sample data

INSERT INTO trash (time, material)
VALUES ('2024-10-16 14:00', 'Plastic'),
       ('2024-10-16 15:30', 'Metal'),
       ('2024-10-16 16:45', 'Glass');


--Retrieve data
SELECT * FROM trash;



DROP TABLE trash;

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL, -- Store hashed passwords
                       role VARCHAR(50) DEFAULT 'USER'
);

-- Sample data
INSERT INTO users (username, password, role)
VALUES ('admin', 'hashed_password_here', 'ADMIN'),
       ('user', 'hashed_password_here', 'USER');


