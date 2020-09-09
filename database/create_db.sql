CREATE DATABASE `cloud` CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `cloud`;

CREATE TABLE `user` (
    `user_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_name` VARCHAR(64) NOT NULL UNIQUE,
    `user_password` BINARY(16) NOT NULL,
    `user_email` VARCHAR(255) NOT NULL UNIQUE,
    `user_phone` CHAR(11),
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `file` (
  `file_id` INT PRIMARY KEY AUTO_INCREMENT,
  `file_type` VARCHAR(16) NOT NULL,
  `md5` BINARY(16) NOT NULL,
  `path` VARCHAR(255) NOT NULL,
  `size` BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_file` (
  `file_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `file_name` VARCHAR(255) NOT NULL,
  `folder_id` INT NOT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`file_id`, `user_id`, `folder_id`),
  FOREIGN KEY (`file_id`) REFERENCES file(`file_id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`user_id`),
  FOREIGN KEY (`folder_id`) REFERENCES file(`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `file_share` (
  `file_id` INT NOT NULL,
  `file_name` VARCHAR(255) NOT NULL,
  `share_user` INT NOT NULL,
  `create_user` INT NOT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`file_id`, `share_user`, `create_user`),
  FOREIGN KEY (`file_id`) REFERENCES file(`file_id`),
  FOREIGN KEY (`share_user`) REFERENCES user(`user_id`),
  FOREIGN KEY (`create_user`) REFERENCES user(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `file` (`file_id`, `file_type`, `md5`, `path`, `size`) VALUES (0, 'folder', 0x0, '', 0);
