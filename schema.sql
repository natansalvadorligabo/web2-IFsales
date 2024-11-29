CREATE DATABASE IF NOT EXISTS ifsales;

USE ifsales;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS salesPersons;
CREATE TABLE salesPersons (
    salesPerson_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(16) NOT NULL,
    active BOOL NOT NULL
);

select * from users;
select * from salesPersons;