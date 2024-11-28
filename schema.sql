CREATE DATABASE IF NOT EXISTS ifsales;

USE ifsales;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);

select * from users;