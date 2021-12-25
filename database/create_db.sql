CREATE DATABASE `cloud` CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `cloud`;

CREATE TABLE `user` (
    `user_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户编号',
    `user_name` VARCHAR(64) NOT NULL UNIQUE COMMENT '用户名',
    `user_password` BINARY(16) NOT NULL COMMENT '用户密码',
    `user_email` VARCHAR(255) NOT NULL UNIQUE COMMENT '用户邮箱',
    `user_phone` CHAR(11) COMMENT '用户手机号',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `file` (
  `file_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '文件编号',
  `file_type` VARCHAR(16) NOT NULL COMMENT '文件名',
  `md5` BINARY(16) NOT NULL COMMENT '文件md5',
  `path` VARCHAR(255) NOT NULL COMMENT '文件路径',
  `size` BIGINT NOT NULL COMMENT '文件大小（字节）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_file` (
  `file_id` INT NOT NULL COMMENT '文件编号',
  `user_id` INT NOT NULL COMMENT '用户编号',
  `file_name` VARCHAR(255) NOT NULL COMMENT '文件名',
  `folder_id` INT NOT NULL COMMENT '所属文件夹编号',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`file_id`, `user_id`, `folder_id`),
  FOREIGN KEY (`file_id`) REFERENCES file(`file_id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`user_id`),
  FOREIGN KEY (`folder_id`) REFERENCES file(`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `file_share` (
  `folder_id` INT NOT NULL COMMENT '所属文件夹编号',
  `file_id` INT NOT NULL COMMENT '文件编号',
  `share_user_role` INT NOT NULL COMMENT '被分享者用户角色（0仅查看，1编辑者，2管理员）',
  `share_user` INT NOT NULL COMMENT '被分享者用户编号',
  `create_user` INT NOT NULL COMMENT '创建者用户编号',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`folder_id`, `file_id`, `share_user`, `create_user`),
  FOREIGN KEY (`folder_id`) REFERENCES file(`file_id`),
  FOREIGN KEY (`file_id`) REFERENCES file(`file_id`),
  FOREIGN KEY (`share_user`) REFERENCES user(`user_id`),
  FOREIGN KEY (`create_user`) REFERENCES user(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `log` (
  `log_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '日志编号',
  `user_id` INT NOT NULL COMMENT '用户编号',
  `log_content` VARCHAR(255) NOT NULL COMMENT '日志内容',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES user(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `file` (`file_id`, `file_type`, `md5`, `path`, `size`) VALUES (0, 'folder', 0x0, '', 0);
