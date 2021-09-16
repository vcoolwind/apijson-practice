CREATE DATABASE `m_bizx_db`;
use m_bizx_db;
CREATE TABLE `vip_user` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT ,
  `sex` tinyint(2) NOT NULL DEFAULT '0' ,
  `name` varchar(20) NOT NULL ,
  `tag` varchar(45) DEFAULT NULL,
  `head` varchar(300) DEFAULT 'https://raw.githubusercontent.com/TommyLemon/StaticResources/master/APIJSON_Logo.png',
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1631766710536 DEFAULT CHARSET=utf8 ;

CREATE DATABASE `m_bizy_db`;
use m_bizy_db;
CREATE TABLE `vip_user` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT ,
  `sex` tinyint(2) NOT NULL DEFAULT '0' ,
  `name` varchar(20) NOT NULL ,
  `tag` varchar(45) DEFAULT NULL,
  `head` varchar(300) DEFAULT 'https://raw.githubusercontent.com/TommyLemon/StaticResources/master/APIJSON_Logo.png',
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1631766710536 DEFAULT CHARSET=utf8 ;
