use sideproject;

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `created_by` int NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `is_deleted` int DEFAULT 0 ,
  `updated_by` int DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
    `fullname` varchar(255),
  `refresh_token` longtext DEFAULT NULL,
  `created_at` varbinary(255) NOT NULL,
  `updated_at` varbinary(255) DEFAULT NULL,
  `role` ENUM('GUEST', 'ADMIN', 'USER') NOT NULL,
  PRIMARY KEY (`id`)
);
SET time_zone = 'Asia/Ho_Chi_Minh';

insert into sideproject.account(username,password,role,email,created_at,created_by,fullname)
values
    ('admin','$2a$12$3joiORYYNK1qhbMk7q/PDeqji.MedyFBG.qxLzgl9.eFPyL0MZzWW','ADMIN','ducnguyen.12965@gmail.com',NOW(),1,"Nguyen A"),
    ('user1','$2a$12$3joiORYYNK1qhbMk7q/PDeqji.MedyFBG.qxLzgl9.eFPyL0MZzWW','USER','duccris1105@gmail.com',NOW(),1,"Nguyen B");



