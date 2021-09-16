CREATE TABLE `User` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'user table',
  `username` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `friends` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `User_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1631592419540 DEFAULT CHARSET=utf8mb4;
INSERT INTO `User`(id, username, realname, bio, friends) VALUES(1627761019072, 'jerry', 'jerry', 'edit my bio while adding a friend', '[1627761038716]');
INSERT INTO `User`(id, username, realname, bio, friends) VALUES(1627761038716, 'neko', 'neko', 'registered via api', NULL);
INSERT INTO `User`(id, username, realname, bio, friends) VALUES(1627761152411, 'randomguy', 'randomguy', 'registered via api', NULL);
INSERT INTO `User`(id, username, realname, bio, friends) VALUES(1627761504126, 'doge', 'doge', 'registered via api', NULL);

CREATE TABLE `Credential` (
  `id` bigint(20) DEFAULT NULL,
  `pwdHash` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO Credential(id, pwdHash) VALUES(1627761019072, '123456');
INSERT INTO Credential(id, pwdHash) VALUES(1627761038716, '233233');
INSERT INTO Credential(id, pwdHash) VALUES(1627761152411, '654321');
INSERT INTO Credential(id, pwdHash) VALUES(1627761504126, '666666');

CREATE TABLE `Todo` (
  `id` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `helper` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO Todo(id, userId, title, note, `date`, helper) VALUES(1627761460691, 1627761019072, 'new todo', 'some content here...', '2021-08-01 03:57:40.0', NULL);
INSERT INTO Todo(id, userId, title, note, `date`, helper) VALUES(1627761702477, 1627761019072, 'yet another todo111', 'good helper111', '2021-08-01 04:01:42.0', NULL);
INSERT INTO Todo(id, userId, title, note, `date`, helper) VALUES(1627761711192, 1627761019072, 'yet another todo', 'good helper', '2021-08-01 04:01:51.0', '[1627761504126]');

CREATE TABLE `Moment` (
  `id` bigint(15) NOT NULL ,
  `userId` bigint(15) NOT NULL ,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
  `content` varchar(300) NOT NULL,
  `praiseUserIdList` json DEFAULT NULL,
  `pictureList` json DEFAULT NULL ,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
INSERT INTO Moment(id, userId, `date`, content, praiseUserIdList, pictureList) VALUES(1631626299719, 1627761038716, '2021-09-14 21:31:39.0', 'hahahah-121', NULL, NULL);

-- 展示一下表的别名
CREATE TABLE `moment_comment` (
  `id` bigint(15) NOT NULL ,
  `userId` bigint(15) NOT NULL ,
  `momentId` bigint(15) NOT NULL ,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
  `content` varchar(1000) NOT NULL ,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `momentId` (`momentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
INSERT INTO moment_comment(id, userId, momentId, `date`, content) VALUES(1631692319371, 1627761504126, 1631626299719, '2021-09-15 15:52:15.0', 'this is my comment-1');
INSERT INTO moment_comment(id, userId, momentId, `date`, content) VALUES(1631705299160, 1627761504126, 1631626299719, '2021-09-15 19:28:23.0', 'this is my comment-2');
