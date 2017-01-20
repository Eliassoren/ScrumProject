CREATE TABLE absence
(
	    user_id INT(11) NOT NULL,
	    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	    CONSTRAINT `PRIMARY` PRIMARY KEY (user_id, date),
	    CONSTRAINT absence_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (user_id)
);
CREATE INDEX user_id ON absence (user_id);
CREATE TABLE availability
(
	    user_id INT(11) NOT NULL,
	    date DATETIME NOT NULL,
	    CONSTRAINT `PRIMARY` PRIMARY KEY (user_id, date),
	    CONSTRAINT availability_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (user_id)
);
CREATE TABLE department
(
	    department_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	    department_name VARCHAR(11) NOT NULL,
	    phone INT(11) NOT NULL
);
CREATE TABLE overtime
(
	    overtime_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	    shift_id INT(11) NOT NULL,
	    start TIME NOT NULL,
	    end TIME NOT NULL,
	    CONSTRAINT overtime_ibfk_1 FOREIGN KEY (shift_id) REFERENCES shift (shift_id)
);
CREATE INDEX overtime_ibfk_1 ON overtime (shift_id);
CREATE TABLE shift
(
	    shift_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	    date DATE NOT NULL,
	    start TIME NOT NULL,
	    end TIME NOT NULL,
	    department_id INT(11) NOT NULL,
	    user_category_id INT(11) NOT NULL,
	    tradeable TINYINT(1) NOT NULL,
	    responsible_user INT(11) NOT NULL,
	    CONSTRAINT shift_ibfk_1 FOREIGN KEY (department_id) REFERENCES department (department_id)
);
CREATE INDEX shift_ibfk_1 ON shift (department_id);
CREATE TABLE user
(
	    user_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	    first_name VARCHAR(255) NOT NULL,
	    last_name VARCHAR(255) NOT NULL,
	    password VARCHAR(255) NOT NULL,
	    admin_rights TINYINT(1) DEFAULT '0' NOT NULL,
	    user_category_id INT(11) NOT NULL,
	    mobile INT(11),
	    address VARCHAR(60),
	    email VARCHAR(60) NOT NULL,
	    token VARCHAR(255),
	    expired TIMESTAMP,
	    active TINYINT(1) NOT NULL,
	    CONSTRAINT user_ibfk_1 FOREIGN KEY (user_category_id) REFERENCES user_category (user_category_id)
);
CREATE UNIQUE INDEX email ON user (email);
CREATE INDEX user_category_id ON user (user_category_id);
CREATE TABLE user_category
(
	    type VARCHAR(255) NOT NULL,
	    user_category_id INT(11) PRIMARY KEY NOT NULL
);
CREATE TABLE user_shift
(
	    user_id INT(11) NOT NULL,
	    shift_id INT(11) NOT NULL,
	    CONSTRAINT `PRIMARY` PRIMARY KEY (user_id, shift_id),
	    CONSTRAINT user_shift_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (user_id),
	    CONSTRAINT user_shift_ibfk_2 FOREIGN KEY (shift_id) REFERENCES shift (shift_id)
);
CREATE INDEX shift_id ON user_shift (shift_id);
