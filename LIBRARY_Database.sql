create database library_management;
use library_management;

create table User(
user_id int primary key auto_increment,
name varchar(100) not null,
ph_no varchar(20) not null
);
describe user;

create table Book(
book_id int primary key auto_increment,
title varchar(200) not null,
author varchar(100) not null,
status ENUM('available','borrowed') default 'available',
copies_total int default 1,
copies_available int default 1
);
describe book;

create table BorrowRecord(
record_id int primary key auto_increment,
user_id int not null,
book_id int not null,
borrow_date date not null,
due_date date not null,
return_date date,
return_status boolean default false,
renewed boolean default false,
fine_incurred decimal(10,2) default 0,
FOREIGN KEY (user_id) REFERENCES User(user_id),
FOREIGN KEY (book_id) REFERENCES Book(book_id)
);
describe borrowrecord;


create table librarian(
librarian_id int primary key auto_increment,
name varchar(100) not null,
username varchar(50) unique not null,
password varchar(100) not null
);
describe librarian;

insert into User (name, ph_no) values
('Kirthi', '9876543210'),
('Madhu', '9123456789'),
('Anushmith', '9988776655'),
('Shrinidhi','123456789'),
('Prajwal', '9876012345');

insert into Book (title, author, copies_total, copies_available) values
('Harry Potter', 'J K Rowling', 3, 3),
('IKIGAI', 'Kector Gracia', 2, 2),
('Outliers', 'Malcom Gladwell', 1, 1),
('Design Patterns', 'Erich Gamma', 2, 2),
('Five Point Someone', 'Chetan Bhagat', 1, 1);

insert into librarian (name, username, password) values
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

select * from user;
select * from book;
select * from librarian;
select * from borrowrecord;