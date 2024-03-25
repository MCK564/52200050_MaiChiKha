CREATE DATABASE LAB07;

USE LAB07;

CREATE TABLE student (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         age INT,
                         ielts DOUBLE
);
