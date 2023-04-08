USE `employee_directory`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `users`
--
-- NOTE: The passwords are encrypted using BCrypt



INSERT INTO `users` 
VALUES 
('saurabh','{bcrypt}$2a$10$h5G0Ll1/UcpuF0Ifz15ORunkaGFnheT7zcgmWgFidmWebQms8xzWS',1),
('ranbir','{bcrypt}$2a$10$cyGLOTMm8v/GBsr5N4msnehFsYxrm31vQxSYaKqISq53tvEDE9ehO',1),
('abhijeet','{bcrypt}$2a$10$2QGPlKJVXqtL4e5AHqeC9unTpQwUmYgeoHMULhK2YAMYfF7ZhEfQK',1);


--
-- Table structure for table `authorities`
--

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities4_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities4_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `authorities`
--

INSERT INTO `authorities` 
VALUES 
('saurabh','ROLE_EMPLOYEE'),
('ranbir','ROLE_EMPLOYEE'),
('ranbir','ROLE_MANAGER'),
('abhijeet','ROLE_EMPLOYEE'),
('abhijeet','ROLE_MANAGER'),
('abhijeet','ROLE_ADMIN');