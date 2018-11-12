CREATE DATABASE  IF NOT EXISTS `teachassistant` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `teachassistant`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: teachassistant
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `task_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `comment_user_id` int(11) NOT NULL,
  `comment_content` varchar(200) DEFAULT NULL,
  `comment_date` datetime DEFAULT NULL,
  PRIMARY KEY (`task_id`,`user_id`,`comment_user_id`),
  KEY `comment_user_id_idx` (`user_id`),
  KEY `comment_user_id2_idx` (`comment_user_id`),
  CONSTRAINT `comment_task_id` FOREIGN KEY (`task_id`) REFERENCES `homework` (`task_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `homework` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_user_id2` FOREIGN KEY (`comment_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `constrction`
--

DROP TABLE IF EXISTS `constrction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `constrction` (
  `task_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `construction_name` varchar(45) DEFAULT NULL,
  `download_time` datetime DEFAULT NULL,
  PRIMARY KEY (`task_id`,`course_id`),
  KEY `c_course_id_idx` (`course_id`),
  CONSTRAINT `c_course_id` FOREIGN KEY (`course_id`) REFERENCES `task` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `c_task_id` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(45) DEFAULT NULL,
  `course_desc` varchar(200) DEFAULT NULL,
  `is_course_end` tinyint(4) DEFAULT '0',
  `isPublic` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `course_id_UNIQUE` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `course_member`
--

DROP TABLE IF EXISTS `course_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_member` (
  `course_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `personal_id` varchar(45) NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  `permission` int(11) DEFAULT NULL,
  PRIMARY KEY (`course_id`,`user_id`),
  KEY `cm_user_id_idx` (`user_id`),
  KEY `cm_personal_id_idx` (`personal_id`),
  CONSTRAINT `cm_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cm_personal_id` FOREIGN KEY (`personal_id`) REFERENCES `user` (`personal_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cm_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `homework`
--

DROP TABLE IF EXISTS `homework`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `homework` (
  `task_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `sent_date` datetime NOT NULL,
  `file_name` varchar(45) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `file_link` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`task_id`,`user_id`,`course_id`),
  KEY `h_user_id_idx` (`user_id`),
  KEY `h_course_id_idx` (`course_id`),
  CONSTRAINT `h_course_id` FOREIGN KEY (`course_id`) REFERENCES `task` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `h_task_id` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `h_user_id` FOREIGN KEY (`user_id`) REFERENCES `course_member` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recource`
--

DROP TABLE IF EXISTS `recource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL,
  `resource_name` varchar(45) NOT NULL,
  `resource_size` varchar(45) NOT NULL,
  `upload_time` datetime NOT NULL,
  PRIMARY KEY (`resource_id`),
  UNIQUE KEY `resource_id_UNIQUE` (`resource_id`),
  KEY `resource_course_id_idx` (`course_id`),
  CONSTRAINT `resource_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sc`
--

DROP TABLE IF EXISTS `sc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc` (
  `course_id` int(11) NOT NULL,
  `stu_id` varchar(45) NOT NULL,
  PRIMARY KEY (`course_id`,`stu_id`),
  CONSTRAINT `sc_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sign_in`
--

DROP TABLE IF EXISTS `sign_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sign_in` (
  `sign_in_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_sign` tinyint(4) DEFAULT '0',
  `sign_time` datetime DEFAULT NULL,
  PRIMARY KEY (`sign_in_id`,`course_id`),
  KEY `sign_in_course_id_idx` (`course_id`),
  KEY `sign_in_user_id_idx` (`user_id`),
  CONSTRAINT `sign_in_course_id` FOREIGN KEY (`course_id`) REFERENCES `course_member` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sign_in_user_id` FOREIGN KEY (`user_id`) REFERENCES `course_member` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `summary`
--

DROP TABLE IF EXISTS `summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summary` (
  `course_id` int(11) NOT NULL,
  `summary_name` varchar(45) DEFAULT NULL,
  `download_time` datetime DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  CONSTRAINT `s_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `task_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `task_name` varchar(45) NOT NULL,
  `task_desc` varchar(200) DEFAULT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `last_receive_time` datetime DEFAULT NULL,
  `accept_type` int(11) DEFAULT '2',
  `is_task_end` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`task_id`,`course_id`),
  KEY `task_course_id_idx` (`course_id`),
  CONSTRAINT `task_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(45) NOT NULL,
  `personal_id` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `personal_mail` varchar(45) DEFAULT NULL,
  `work_mail` varchar(45) DEFAULT NULL,
  `work_mail_pwd` varchar(45) DEFAULT NULL,
  `school` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `openid_UNIQUE` (`openid`),
  KEY `personal_id_INDEX` (`personal_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1012 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-12 15:18:43
