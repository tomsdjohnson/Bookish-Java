CREATE TABLE users (
  username varchar(255) NOT NULL PRIMARY KEY,
  password varchar(255) NOT NULL,
  enabled boolean NOT NULL
);

CREATE TABLE books (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title varchar(255) NOT NULL,
  author varchar(255) NOT NULL,
  isbn varchar(14) NOT NULL
);

CREATE TABLE copies (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  due_date date,
  book_id int(11) NOT NULL,
  borrower_username varchar(255),
  FOREIGN KEY (book_id) REFERENCES books (id),
  FOREIGN KEY (borrower_username) REFERENCES users (username)
);
