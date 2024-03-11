CREATE DATABASE IF NOT EXISTS lab05;

USE lab05;

CREATE TABLE IF NOT EXISTS product (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255),
    price INT
    );

CREATE TABLE IF NOT EXISTS user (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    email VARCHAR(255),
    fullname VARCHAR(255),
    password VARCHAR(255)
    );
USE lab05;

-- Thêm 5 người dùng
INSERT INTO user (email, fullname, password) VALUES
                                                 ('admin@example.com', 'Admin', 'admin123'), -- Admin
                                                 ('user1@example.com', 'User 1', 'password1'),
                                                 ('user2@example.com', 'User 2', 'password2'),
                                                 ('user3@example.com', 'User 3', 'password3'),
                                                 ('user4@example.com', 'User 4', 'password4');

-- Thêm 10 sản phẩm
INSERT INTO product (name, price) VALUES
                                      ('Product 1', 100),
                                      ('Product 2', 200),
                                      ('Product 3', 300),
                                      ('Product 4', 400),
                                      ('Product 5', 500),
                                      ('Product 6', 600),
                                      ('Product 7', 700),
                                      ('Product 8', 800),
                                      ('Product 9', 900),
                                      ('Product 10', 1000);


