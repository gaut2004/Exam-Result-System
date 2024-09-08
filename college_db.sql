CREATE DATABASE college_db;
USE college_db;
CREATE TABLE students (
    roll_number VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100),
    department VARCHAR(50),
    dob DATE
);
CREATE TABLE results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(20),
    subject VARCHAR(50),
    obtained_marks INT,
    total_marks INT,
    grade VARCHAR(2),
    FOREIGN KEY (roll_number) REFERENCES students(roll_number)
);
CREATE TABLE revaluation_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(20),
    subject VARCHAR(50),
    FOREIGN KEY (roll_number) REFERENCES students(roll_number)
);
USE college_db;
