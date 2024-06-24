CREATE DATABASE IF NOT EXISTS `learning_log` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE User IF NOT EXISTS 'manager'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON `learning_log`.* TO 'manager'@'localhost';
