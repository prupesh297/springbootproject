CREATE DATABASE employeedb;

USE employeedb;

CREATE TABLE employee
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    gender VARCHAR(255),
    department VARCHAR(255),
    dob DATE
);
insert into employee values(1,'Amar','Male','HR', '1990-02-02');