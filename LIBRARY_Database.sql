CREATE DATABASE library_management;
USE library_management;
 
CREATE TABLE User (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  ph_no VARCHAR(20)
);

CREATE TABLE Book (
  book_id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(200) NOT NULL,
  author VARCHAR(100),
  status ENUM('available', 'borrowed') DEFAULT 'available',
  copies_total INT DEFAULT 1,
  copies_available INT DEFAULT 1
);
 
CREATE TABLE BorrowRecord (
  record_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  borrow_date DATE NOT NULL,
  due_date DATE NOT NULL,
  return_date DATE,
  return_status BOOLEAN DEFAULT FALSE,
  renewed BOOLEAN DEFAULT FALSE,
  fine_incurred DECIMAL(10,2) DEFAULT 0,
  FOREIGN KEY (user_id) REFERENCES User(user_id),
  FOREIGN KEY (book_id) REFERENCES Book(book_id)
);

CREATE TABLE librarian (
    librarian_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

INSERT INTO User (name, ph_no) VALUES
('Kirthi', '9876543210'),
('Madhu', '9123456789'),
('Anushmith', '9988776655'),
('Shrinidhi','123456789'),
('Prajwal', '9876012345');
 
INSERT INTO Book (title, author, copies_total, copies_available) VALUES
('Harry Potter', 'J K Rowling', 3, 3),
('IKIGAI', 'Kector Gracia', 2, 2),
('Outliers', 'Malcom Gladwell', 1, 1),
('Design Patterns', 'Erich Gamma', 2, 2),
('Five Point Someone', 'Chetan Bhagat', 1, 1);

SELECT * FROM User;
SELECT * FROM Book;

INSERT INTO librarian (name, username, password) VALUES
('Lakshmi Priya', 'lpriya', 'password123'),
('Ashwin Rao', 'ashrao', 'securepass456');

-- Madhu borrowed "Harry Potter" today
INSERT INTO borrowrecord 
(user_id, book_id, borrow_date, due_date, return_date, return_status, renewed, fine_incurred)
VALUES 
(2, 1, CURDATE(), CURDATE() + INTERVAL 5 DAY, NULL, 0, 0, 0);

-- Kirthi returned "Outliers" 2 days late (borrowed 7 days ago)
INSERT INTO borrowrecord 
(user_id, book_id, borrow_date, due_date, return_date, return_status, renewed, fine_incurred)
VALUES 
(1, 3, CURDATE() - INTERVAL 7 DAY, CURDATE() - INTERVAL 2 DAY, CURDATE(), 1, 0, 20);

-- Anushmith borrowed "IKIGAI", renewed once, still not returned
INSERT INTO borrowrecord 
(user_id, book_id, borrow_date, due_date, return_date, return_status, renewed, fine_incurred)
VALUES 
(3, 2, CURDATE() - INTERVAL 5 DAY, CURDATE() + INTERVAL 3 DAY, NULL, 0, 1, 0);

-- Shrinidhi borrowed "Design Patterns" 4 days ago, returned today
INSERT INTO borrowrecord 
(user_id, book_id, borrow_date, due_date, return_date, return_status, renewed, fine_incurred)
VALUES 
(4, 4, CURDATE() - INTERVAL 4 DAY, CURDATE() + INTERVAL 1 DAY, CURDATE(), 1, 0, 0);

SELECT * FROM borrowrecord;
