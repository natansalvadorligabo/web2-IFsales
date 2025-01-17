CREATE DATABASE IF NOT EXISTS ifsales;

USE ifsales;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS salespersons;
CREATE TABLE salespersons (
    salesperson_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(16) NOT NULL,
    active BOOL NOT NULL
);

insert into salespersons values (1, 'Caua Rufino', 'caua@gmail.com', '(16) 99633-7792', 1);
insert into salespersons values (2, 'Nathan Ligabo', 'nathzinho@gmail.com', '(16) 99522-7194', 0);
insert into salespersons values (3, 'Giovana Trevizan', 'trevizan.gio@gmail.com', '(16) 92334-7142', 0);
insert into salespersons values (4, 'Igor filipi', 'f.igor@gmail.com', '(16) 99331-4564', 0);
insert into salespersons values (5, 'Tonhao recordista', 'recordista@gmail.com', '(16) 99999-9999', 1);

select * from users;
select * from salespersons;