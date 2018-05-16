/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50631
Source Host           : localhost:3306
Source Database       : email_homework

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2018-05-13 09:43:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `uuid` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `formUuid` varchar(255) DEFAULT NULL,
  `homeworkUuid` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `toUuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('7AcjxumTopPHcGp', '00', '2018-05-03 16:22:09', '1', '3obFjOMkTlq1g5T', 'VASLsDwSWwGUBDf', '2018-05-03 16:22:09', '0');
INSERT INTO `comment` VALUES ('aFZFuCN4FbZSzrB', 'aaa', '2018-05-04 17:04:16', '1', '3obFjOMkTlq1g5T', 'FKgByiPukFHhiOQ', '2018-05-04 17:04:16', '0');
INSERT INTO `comment` VALUES ('ahlO4VMW4fwL1xQ', '你好', '2018-05-03 10:55:44', '1', '3obFjOMkTlq1g5T', 'rKMoJ0jjOjwDhK0', '2018-05-03 10:55:44', '0');
INSERT INTO `comment` VALUES ('E6yTRCvAXVxADJi', 'ddd', '2018-05-02 16:39:08', '1', '3obFjOMkTlq1g5T', 'VASLsDwSWwGUBDf', '2018-05-02 16:39:08', '0');
INSERT INTO `comment` VALUES ('QkTqFiJa44C24T2', '你好', '2018-05-03 15:48:39', '1', '45445dfgfdggf', 'rKMoJ0jjOjwDhK0', '2018-05-03 15:48:39', '3obFjOMkTlq1g5T');
INSERT INTO `comment` VALUES ('Qx7pXRlhv9VxJjk', 'gggg', '2018-05-02 16:39:12', '1', '3obFjOMkTlq1g5T', 'VASLsDwSWwGUBDf', '2018-05-02 16:39:12', '0');
INSERT INTO `comment` VALUES ('rLmdG0dBMlE53JR', 'nihaoma1', '2018-05-02 16:38:13', '1', '3obFjOMkTlq1g5T', 'VASLsDwSWwGUBDf', '2018-05-02 16:38:13', '0');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `uuid` varchar(255) NOT NULL,
  `courseName` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `personNum` varchar(255) DEFAULT NULL,
  `teacherUuid` varchar(255) DEFAULT NULL,
  `updateTime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('PO8VihjRgGO5Ple', 'Java web开发', '2018-04-28 23:32:38', '1', '2018-04-28 23:32:38', null, '3obFjOMkTlq1g5T', null);

-- ----------------------------
-- Table structure for `homework`
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework` (
  `uuid` varchar(255) NOT NULL,
  `context` varchar(255) DEFAULT NULL,
  `courseUuid` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `submittedNum` varchar(255) DEFAULT NULL,
  `teacherUuid` varchar(255) DEFAULT NULL,
  `times` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updateTime` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `fileSize` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('FKgByiPukFHhiOQ', 'java web开发实验2', 'PO8VihjRgGO5Ple', '2018-04-29 18:22:49', '1', '2018-05-12 11:26:29', '0', '3obFjOMkTlq1g5T', '2', 'java web开发实验2', '2018-05-12 11:26:29', '0', null, null);
INSERT INTO `homework` VALUES ('rKMoJ0jjOjwDhK0', 'java web开发实验1', 'PO8VihjRgGO5Ple', '2018-04-29 18:22:32', '1', '2018-05-12 04:16:26', '1', '3obFjOMkTlq1g5T', '1', 'java web开发实验1', '2018-05-12 04:16:26', '1', '作业1', '641');
INSERT INTO `homework` VALUES ('VASLsDwSWwGUBDf', 'java web开发实验3', 'PO8VihjRgGO5Ple', '2018-04-29 18:39:43', '1', '2018-05-12 11:26:29', '0', '3obFjOMkTlq1g5T', '3', 'java web开发实验3', '2018-05-12 11:26:29', '0', null, null);

-- ----------------------------
-- Table structure for `homework_student_rel`
-- ----------------------------
DROP TABLE IF EXISTS `homework_student_rel`;
CREATE TABLE `homework_student_rel` (
  `uuid` varchar(255) NOT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `homeworkUuid` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `studentNo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework_student_rel
-- ----------------------------
INSERT INTO `homework_student_rel` VALUES ('9O3J3yw0Ez3NkLh', '2018-05-12 04:16:25', '1', 'rKMoJ0jjOjwDhK0', '2018-05-12 04:16:25', '1414080903229');

-- ----------------------------
-- Table structure for `like_rel`
-- ----------------------------
DROP TABLE IF EXISTS `like_rel`;
CREATE TABLE `like_rel` (
  `uuid` varchar(50) NOT NULL,
  `delFlag` varchar(5) DEFAULT NULL,
  `oprTime` varchar(20) DEFAULT NULL,
  `createTime` varchar(20) DEFAULT NULL,
  `homeworkUuid` varchar(50) DEFAULT NULL,
  `userUuid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of like_rel
-- ----------------------------
INSERT INTO `like_rel` VALUES ('5Z9qn1p5Pu1aPqd', '1', '2018-05-12 11:18:00', '2018-05-12 11:18:00', 'VASLsDwSWwGUBDf', '3obFjOMkTlq1g5T');
INSERT INTO `like_rel` VALUES ('CV54z8WofISvuiS', '1', '2018-05-04 16:59:28', '2018-05-04 16:59:28', 'FKgByiPukFHhiOQ', '3obFjOMkTlq1g5T');
INSERT INTO `like_rel` VALUES ('Q1A2WKzNQ0xfNod', '1', '2018-05-04 13:46:45', '2018-05-04 13:46:45', 'VASLsDwSWwGUBDf', '45445dfgfdggf');
INSERT INTO `like_rel` VALUES ('Qmwce6Ibgwfh29k', '1', '2018-05-12 04:18:35', '2018-05-12 04:18:35', 'rKMoJ0jjOjwDhK0', '3obFjOMkTlq1g5T');
INSERT INTO `like_rel` VALUES ('tR1dZXnpmfNdnKs', '1', '2018-05-03 15:46:09', '2018-05-03 15:46:09', 'rKMoJ0jjOjwDhK0', '45445dfgfdggf');
INSERT INTO `like_rel` VALUES ('XDUZfHGahv4C3Cu', '1', '2018-05-03 15:46:05', '2018-05-03 15:46:05', 'FKgByiPukFHhiOQ', '45445dfgfdggf');

-- ----------------------------
-- Table structure for `matched`
-- ----------------------------
DROP TABLE IF EXISTS `matched`;
CREATE TABLE `matched` (
  `uuid` varchar(255) NOT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `formUuid` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `toUuid` varchar(255) DEFAULT NULL,
  `belongUuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of matched
-- ----------------------------
INSERT INTO `matched` VALUES ('7eBwF08MVUHaAXe', '2018-05-04 11:35:16', '1', '45445dfgfdggf', '2018-05-04 11:35:16', '3obFjOMkTlq1g5T', '45445dfgfdggf');
INSERT INTO `matched` VALUES ('eRW16c4oj988qIB', '2018-05-04 11:35:16', '1', '45445dfgfdggf', '2018-05-04 11:35:16', '3obFjOMkTlq1g5T', '3obFjOMkTlq1g5T');

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `uuid` varchar(255) NOT NULL,
  `belongUuid` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `formUuid` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `toUuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('0K9f3KfMans2Ku8', '3obFjOMkTlq1g5T', '你好', '2018-05-04 12:39:19', '1', '45445dfgfdggf', '2018-05-04 12:39:19', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('0YZg1dGPT6fmkmO', '3obFjOMkTlq1g5T', '面', '2018-05-04 12:28:32', '1', '45445dfgfdggf', '2018-05-04 12:28:32', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('4UTnotGb6e0bT6w', '3obFjOMkTlq1g5T', '你好', '2018-05-04 13:26:45', '1', '45445dfgfdggf', '2018-05-04 13:26:45', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('5BxGAx6H02TBJ3n', '45445dfgfdggf', '你好', '2018-05-04 13:26:33', '1', '45445dfgfdggf', '2018-05-04 13:26:33', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('5vgiRhIEsVjTw42', '45445dfgfdggf', 'nihao', '2018-05-12 04:20:16', '1', '3obFjOMkTlq1g5T', '2018-05-12 04:20:16', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('6UVfdmHGBUlXaJE', '3obFjOMkTlq1g5T', '你好', '2018-05-12 09:49:19', '1', '3obFjOMkTlq1g5T', '2018-05-12 09:49:19', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('7Jq8XIJDFo1BUqm', '45445dfgfdggf', '你买吗？', '2018-05-04 13:54:36', '1', '45445dfgfdggf', '2018-05-04 13:54:36', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('837xZt7E3vDR6Ky', '45445dfgfdggf', '面', '2018-05-04 12:28:32', '1', '45445dfgfdggf', '2018-05-04 12:28:32', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('848ULyNnIrkOzCb', '3obFjOMkTlq1g5T', '很好', '2018-05-04 12:32:21', '1', '45445dfgfdggf', '2018-05-04 12:32:21', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('8ALceHOSqQAgg3P', '3obFjOMkTlq1g5T', 'jjs1', '2018-05-04 12:30:24', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:24', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('8iwMiv55vbegbIj', '3obFjOMkTlq1g5T', 'woheha', '2018-05-04 12:22:46', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:22:46', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('9Wq21OrWOwySrYR', '3obFjOMkTlq1g5T', 'zenmele\n', '2018-05-12 04:24:01', '1', '3obFjOMkTlq1g5T', '2018-05-12 04:24:01', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('9YWRt6IhUENrsEy', '45445dfgfdggf', 'woheha', '2018-05-04 12:22:46', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:22:46', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('A6PLl7YrrDkYofj', '3obFjOMkTlq1g5T', '你好', '2018-05-04 12:31:14', '1', '45445dfgfdggf', '2018-05-04 12:31:14', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('a7lkd7dUcycp6Lm', '45445dfgfdggf', '你好', '2018-05-04 12:31:14', '1', '45445dfgfdggf', '2018-05-04 12:31:14', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('aQK9HpEpt2O3aUE', '3obFjOMkTlq1g5T', 'ttt', '2018-05-04 12:30:51', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:51', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('Aqw3R4ZnY3Jxxix', '45445dfgfdggf', '你咋那', '2018-05-12 04:24:13', '1', '45445dfgfdggf', '2018-05-12 04:24:13', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('Ay5Q77EI83kRf9H', '45445dfgfdggf', 'zenmele\n', '2018-05-12 04:24:01', '1', '3obFjOMkTlq1g5T', '2018-05-12 04:24:01', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('b5hKpIWcv70iTQl', '3obFjOMkTlq1g5T', '你好', '2018-05-12 09:49:32', '1', '3obFjOMkTlq1g5T', '2018-05-12 09:49:32', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('bfAI0mKJJUE98cd', '3obFjOMkTlq1g5T', '我是教师', '2018-05-12 04:24:39', '1', '3obFjOMkTlq1g5T', '2018-05-12 04:24:39', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('BqhtbukkjA3YL1y', '3obFjOMkTlq1g5T', '你好', '2018-05-04 13:26:33', '1', '45445dfgfdggf', '2018-05-04 13:26:33', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('BrehwELA7GITAVu', '45445dfgfdggf', 'sss', '2018-05-12 04:20:28', '1', '3obFjOMkTlq1g5T', '2018-05-12 04:20:28', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('C2azhxmtc5xePE2', '45445dfgfdggf', 'wohenh', '2018-05-04 12:39:30', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:39:30', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('E3AnFX9SBuuOLCS', '45445dfgfdggf', '很好', '2018-05-04 12:32:21', '1', '45445dfgfdggf', '2018-05-04 12:32:21', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('EoE4m8VdTnPmrMn', '45445dfgfdggf', '你好', '2018-05-12 09:49:19', '1', '3obFjOMkTlq1g5T', '2018-05-12 09:49:19', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('EoEzpT7niMXI2nF', '3obFjOMkTlq1g5T', '你好', '2018-05-04 13:28:11', '1', '45445dfgfdggf', '2018-05-04 13:28:11', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('eOylBGiWBadYf0s', '45445dfgfdggf', '很好', '2018-05-04 12:32:43', '1', '45445dfgfdggf', '2018-05-04 12:32:43', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('fcn6DStvNeTC008', '45445dfgfdggf', '你好', '2018-05-04 12:31:08', '1', '45445dfgfdggf', '2018-05-04 12:31:08', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('G6fPC5zI7DW7CrA', '45445dfgfdggf', 'jjs1', '2018-05-04 12:30:24', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:24', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('gaSy0RvbH3V7VnQ', '45445dfgfdggf', '你好', '2018-05-04 12:28:45', '1', '45445dfgfdggf', '2018-05-04 12:28:45', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('gXiKOkLOGxHyRT3', '45445dfgfdggf', 'fff', '2018-05-04 12:32:04', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:32:04', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('HDrfofKkuaRqGcc', '3obFjOMkTlq1g5T', 'ttt', '2018-05-04 12:30:54', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:54', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('HGgwK7a8QCV7Qpv', '3obFjOMkTlq1g5T', 'sss', '2018-05-12 04:20:28', '1', '3obFjOMkTlq1g5T', '2018-05-12 04:20:28', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('IjG2eosP1S23kSH', '45445dfgfdggf', '我婆婆肉肉肉肉肉是行尸走肉公婆婆欣赏欣赏一下自有颜如玉所以', '2018-05-04 12:33:39', '1', '45445dfgfdggf', '2018-05-04 12:33:39', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('iQpi2F2zxs5yPcE', '3obFjOMkTlq1g5T', 'ttt', '2018-05-04 12:30:46', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:46', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('IVPf19XZOUz4in1', '45445dfgfdggf', 'nihao', '2018-05-04 13:41:43', '1', '3obFjOMkTlq1g5T', '2018-05-04 13:41:43', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('j1sEahNVNDPAQu8', '3obFjOMkTlq1g5T', 'hhh', '2018-05-04 13:44:59', '1', '3obFjOMkTlq1g5T', '2018-05-04 13:44:59', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('k7Rdqgf7w5LE0Bd', '45445dfgfdggf', '你好', '2018-05-04 12:31:11', '1', '45445dfgfdggf', '2018-05-04 12:31:11', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('KfwdogjOFzxwZAp', '45445dfgfdggf', 'ttt', '2018-05-04 12:30:46', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:46', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('KHlhppnBXbhRnkP', '45445dfgfdggf', '你好', '2018-05-04 13:28:11', '1', '45445dfgfdggf', '2018-05-04 13:28:11', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('kzNQ6mM5FK5FRNe', '45445dfgfdggf', '你好', '2018-05-04 12:39:19', '1', '45445dfgfdggf', '2018-05-04 12:39:19', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('L22dX9bgMz4WLCS', '3obFjOMkTlq1g5T', '我婆婆肉肉肉肉肉是行尸走肉公婆婆欣赏欣赏一下自有颜如玉所以', '2018-05-04 12:33:39', '1', '45445dfgfdggf', '2018-05-04 12:33:39', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('LCulLxZnEpeLkND', '45445dfgfdggf', '你在哪？', '2018-05-04 15:53:56', '1', '45445dfgfdggf', '2018-05-04 15:53:56', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('LP6AniJAEO21jsU', '45445dfgfdggf', '你好', '2018-05-12 09:49:32', '1', '3obFjOMkTlq1g5T', '2018-05-12 09:49:32', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('lTeYsKb8JoUTHjF', '3obFjOMkTlq1g5T', 'ttt', '2018-05-04 12:30:52', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:52', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('m6mkeHhrD5Dn1fy', '3obFjOMkTlq1g5T', '你好', '2018-05-04 12:31:11', '1', '45445dfgfdggf', '2018-05-04 12:31:11', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('nMWWtampKQuJ6RV', '45445dfgfdggf', '你好', '2018-05-04 13:26:45', '1', '45445dfgfdggf', '2018-05-04 13:26:45', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('o7WeRBfy4LKERvj', '3obFjOMkTlq1g5T', '你好', '2018-05-04 12:31:08', '1', '45445dfgfdggf', '2018-05-04 12:31:08', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('Obwm18JEMAy21Mq', '3obFjOMkTlq1g5T', 'wohenh', '2018-05-04 12:39:30', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:39:30', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('OGFdAhXZYp0QlM2', '3obFjOMkTlq1g5T', 'nihao', '2018-05-12 04:20:16', '1', '3obFjOMkTlq1g5T', '2018-05-12 04:20:16', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('OpArA3idjKunx21', '45445dfgfdggf', 'ttt', '2018-05-04 12:30:51', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:51', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('P1ySwb7qQ9O8Fhy', '45445dfgfdggf', '我是教师', '2018-05-12 04:24:39', '1', '3obFjOMkTlq1g5T', '2018-05-12 04:24:39', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('pfiXS9jkXSfz5e2', '45445dfgfdggf', 'ttt', '2018-05-04 12:30:52', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:52', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('PPlb6vNk7t7ZeD9', '3obFjOMkTlq1g5T', 'nihao', '2018-05-04 13:41:42', '1', '3obFjOMkTlq1g5T', '2018-05-04 13:41:42', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('tBwI9wx1uLUqDPG', '3obFjOMkTlq1g5T', '你好', '2018-05-04 12:28:45', '1', '45445dfgfdggf', '2018-05-04 12:28:45', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('TnwRRugsVCJxaWr', '3obFjOMkTlq1g5T', '很好', '2018-05-04 12:32:43', '1', '45445dfgfdggf', '2018-05-04 12:32:43', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('TSp3vfB0P94H4It', '3obFjOMkTlq1g5T', 'ttt', '2018-05-04 12:30:54', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:54', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('TWdKFbDGrjg57zb', '45445dfgfdggf', '我是学生', '2018-05-12 04:24:48', '1', '45445dfgfdggf', '2018-05-12 04:24:48', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('uCBlX4pifJZI345', '45445dfgfdggf', '2222', '2018-05-04 12:33:16', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:33:16', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('ut6GWXzcmyblEYd', '3obFjOMkTlq1g5T', '你买吗？', '2018-05-04 13:54:36', '1', '45445dfgfdggf', '2018-05-04 13:54:36', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('vjphKMYT4XQaCVm', '3obFjOMkTlq1g5T', '2222', '2018-05-04 12:33:16', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:33:16', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('vrz4BQqdz5BKnUa', '45445dfgfdggf', 'ttt', '2018-05-04 12:30:54', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:54', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('xgCB3V6Zfi2LJxa', '45445dfgfdggf', 'ttt', '2018-05-04 12:30:54', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:30:54', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('xhN1uO4tYnrO6Pr', '3obFjOMkTlq1g5T', '我是学生', '2018-05-12 04:24:48', '1', '45445dfgfdggf', '2018-05-12 04:24:48', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('XpvMQx91akpjDKq', '45445dfgfdggf', 'wozaijia', '2018-05-04 17:02:57', '1', '3obFjOMkTlq1g5T', '2018-05-04 17:02:57', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('xqrgBrHoHAqRTep', '45445dfgfdggf', 'hhh', '2018-05-04 13:44:59', '1', '3obFjOMkTlq1g5T', '2018-05-04 13:44:59', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('YhTIdOGLsEeWD9m', '3obFjOMkTlq1g5T', '你在哪？', '2018-05-04 15:53:56', '1', '45445dfgfdggf', '2018-05-04 15:53:56', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('YU2MylS4HdbLSrF', '3obFjOMkTlq1g5T', 'wozaijia', '2018-05-04 17:02:57', '1', '3obFjOMkTlq1g5T', '2018-05-04 17:02:57', '1', '45445dfgfdggf');
INSERT INTO `message` VALUES ('z22Ds1gxbYsWz0g', '3obFjOMkTlq1g5T', '你好你好', '2018-05-04 12:29:02', '1', '45445dfgfdggf', '2018-05-04 12:29:02', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('Z9prEOU6xfZGMPt', '45445dfgfdggf', '你好你好', '2018-05-04 12:29:02', '1', '45445dfgfdggf', '2018-05-04 12:29:02', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('zms4nFfcjvLosCZ', '3obFjOMkTlq1g5T', '你咋那', '2018-05-12 04:24:13', '1', '45445dfgfdggf', '2018-05-12 04:24:13', '1', '3obFjOMkTlq1g5T');
INSERT INTO `message` VALUES ('zWwixjkV6Zq0FJm', '3obFjOMkTlq1g5T', 'fff', '2018-05-04 12:32:04', '1', '3obFjOMkTlq1g5T', '2018-05-04 12:32:04', '1', '45445dfgfdggf');

-- ----------------------------
-- Table structure for `school`
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `uuid` varchar(255) NOT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `schoolName` varchar(255) DEFAULT NULL,
  `schoolState` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES ('NgyElGcak2uJ85d', '2018-04-25 10:35:42', '1', null, '刷新数据', null);
INSERT INTO `school` VALUES ('Qd6r94Cwax0SUaM', '2018-04-27 16:27:32', '1', null, '111', null);
INSERT INTO `school` VALUES ('T3gQ6SuDLz9IaGd', '2018-04-25 10:36:28', '1', null, '惠州学院', null);
INSERT INTO `school` VALUES ('UbFMRe4Gbpb5nLv', '2018-04-25 02:03:52', '1', null, '惠州学院', null);

-- ----------------------------
-- Table structure for `school_user_rel`
-- ----------------------------
DROP TABLE IF EXISTS `school_user_rel`;
CREATE TABLE `school_user_rel` (
  `uuid` varchar(255) NOT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `schoolUuid` varchar(255) DEFAULT NULL,
  `updateTime` varchar(255) DEFAULT NULL,
  `userUuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school_user_rel
-- ----------------------------

-- ----------------------------
-- Table structure for `statistics`
-- ----------------------------
DROP TABLE IF EXISTS `statistics`;
CREATE TABLE `statistics` (
  `uuid` varchar(255) NOT NULL,
  `allNum` varchar(255) DEFAULT NULL,
  `courseUuid` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `homeworkUuid` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `submittedNum` varchar(255) DEFAULT NULL,
  `teacherUuid` varchar(255) DEFAULT NULL,
  `courseName` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `fileSize` varchar(255) DEFAULT NULL,
  `formEmail` varchar(255) DEFAULT NULL,
  `homeworkName` varchar(255) DEFAULT NULL,
  `mailContent` varchar(255) DEFAULT NULL,
  `mailTitle` varchar(255) DEFAULT NULL,
  `receiveTime` varchar(255) DEFAULT NULL,
  `sendTime` varchar(255) DEFAULT NULL,
  `studentNo` varchar(255) DEFAULT NULL,
  `teacherName` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `userUuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of statistics
-- ----------------------------
INSERT INTO `statistics` VALUES ('IzNieogMQaFjQAu', null, null, '2018-05-12 11:19:21', '1', 'rKMoJ0jjOjwDhK0', null, null, '3obFjOMkTlq1g5T', null, '新建文本文档.txt', '873.00B', '从不属于我\'\'落幕成花祭<13450507986@qq.com>', 'java web开发实验1', '', 'Java web开发-1414080903229麦贤军-作业1', '2018-05-12 11:19:21', '18-04-29 22:51', '1414080903229', 'feirty', null, null);
INSERT INTO `statistics` VALUES ('q9PL3HnVWrUxlh7', null, null, '2018-05-05 18:14:46', '1', 'rKMoJ0jjOjwDhK0', null, null, '3obFjOMkTlq1g5T', null, '新建文本文档.txt', '873.00B', '从不属于我\'\'落幕成花祭<13450507986@qq.com>', 'java web开发实验1', '', 'Java web开发-1414080903229麦贤军-作业1', '2018-05-05 18:14:46', '18-04-29 22:51', '1414080903229', 'feirty', null, null);
INSERT INTO `statistics` VALUES ('YsSTjPxwxUY8L2Y', null, null, '2018-05-05 18:12:16', '1', 'rKMoJ0jjOjwDhK0', null, null, '3obFjOMkTlq1g5T', null, '新建文本文档.txt', '873.00B', '从不属于我\'\'落幕成花祭<13450507986@qq.com>', 'java web开发实验1', '', 'Java web开发-1414080903229麦贤军-作业1', '2018-05-05 18:12:16', '18-04-29 22:51', '1414080903229', 'feirty', null, null);

-- ----------------------------
-- Table structure for `student_course_rel`
-- ----------------------------
DROP TABLE IF EXISTS `student_course_rel`;
CREATE TABLE `student_course_rel` (
  `uuid` varchar(255) NOT NULL,
  `courseUuid` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `studentUuid` varchar(255) DEFAULT NULL,
  `studentNo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_course_rel
-- ----------------------------
INSERT INTO `student_course_rel` VALUES ('3580OKtjEqcp6EV', 'PO8VihjRgGO5Ple', '2018-04-28 23:32:38', '1', '2018-04-28 23:32:38', null, '1414080903230');
INSERT INTO `student_course_rel` VALUES ('DzKN8umw3ozP8SD', 'PO8VihjRgGO5Ple', '2018-04-28 23:32:38', '1', '2018-04-28 23:32:38', null, '1414080903231');
INSERT INTO `student_course_rel` VALUES ('vurXqjy4YKEoQiv', 'PO8VihjRgGO5Ple', '2018-04-28 23:32:38', '1', '2018-04-28 23:32:38', null, '1414080903229');

-- ----------------------------
-- Table structure for `student_email`
-- ----------------------------
DROP TABLE IF EXISTS `student_email`;
CREATE TABLE `student_email` (
  `uuid` varchar(255) NOT NULL,
  `attachmentName` varchar(255) DEFAULT NULL,
  `attachmentSize` varchar(255) DEFAULT NULL,
  `context` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `homeworkUuid` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `sendTime` varchar(255) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `studentUuid` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_email
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_account`
-- ----------------------------
DROP TABLE IF EXISTS `sys_account`;
CREATE TABLE `sys_account` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `loginName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=40254352242430 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_account
-- ----------------------------
INSERT INTO `sys_account` VALUES ('40254352242421', '111111', 'admin');
INSERT INTO `sys_account` VALUES ('40254352242422', '222', '111');
INSERT INTO `sys_account` VALUES ('40254352242423', '222', '111');
INSERT INTO `sys_account` VALUES ('40254352242424', '222', '111');
INSERT INTO `sys_account` VALUES ('40254352242425', '222', '111');
INSERT INTO `sys_account` VALUES ('40254352242426', '222', '111');
INSERT INTO `sys_account` VALUES ('40254352242427', '222', '111');
INSERT INTO `sys_account` VALUES ('40254352242428', '222', '111');
INSERT INTO `sys_account` VALUES ('40254352242429', '222', '111');

-- ----------------------------
-- Table structure for `teacher_course_rel`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_course_rel`;
CREATE TABLE `teacher_course_rel` (
  `uuid` varchar(255) NOT NULL,
  `courseUuid` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `teacherUuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_course_rel
-- ----------------------------
INSERT INTO `teacher_course_rel` VALUES ('phvd4HJ3OsEplVy', 'PO8VihjRgGO5Ple', '2018-04-28 23:32:38', '1', '2018-04-28 23:32:38', '3obFjOMkTlq1g5T');

-- ----------------------------
-- Table structure for `user_account`
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
  `uuid` varchar(255) NOT NULL,
  `codeTimes` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `emailPwd` varchar(255) DEFAULT NULL,
  `lastCodeTime` varchar(255) DEFAULT NULL,
  `loginName` varchar(255) DEFAULT NULL,
  `loginPwd` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `oprTime` varchar(255) DEFAULT NULL,
  `privEmail` varchar(255) DEFAULT NULL,
  `profileUrl` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `userState` varchar(255) DEFAULT NULL,
  `userType` varchar(255) DEFAULT NULL,
  `verifyCode` varchar(255) DEFAULT NULL,
  `studentNo` varchar(255) DEFAULT NULL,
  `schoolUuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_account
-- ----------------------------
INSERT INTO `user_account` VALUES ('1931TqGeuuD6ofS', null, '2018-04-22 14:51:38', '1', null, null, null, '14555', 'b59c67bf196a4758191e42f76670ceba', null, '2018-04-22 14:51:38', '54454551515@qq.com', null, null, null, '1', null, null, null, null);
INSERT INTO `user_account` VALUES ('2554881546465', null, '2018-04-22 13:51:08', '1', null, null, null, 'lianyaj', '698d51a19d8a121ce581499d7b701668', null, '2018-04-22 13:51:08', '1050@qq.com', null, null, null, '1', null, null, null, null);
INSERT INTO `user_account` VALUES ('2554881546465000', null, '2018-04-22 13:56:12', '1', null, null, null, 'ss', '6512bd43d9caa6e02c990b0a82652dca', null, '2018-04-22 13:56:12', '1444@qq.com', null, null, null, '1', null, null, null, null);
INSERT INTO `user_account` VALUES ('3obFjOMkTlq1g5T', null, '2018-04-23 12:50:21', '1', '1563547684@qq.com', 'lksczjbmcefpfjbi', null, 'feirty', '698d51a19d8a121ce581499d7b701668', null, '2018-04-27 14:30:18', '1050416617@qq.com', null, null, null, '1', '2', null, null, 'T3gQ6SuDLz9IaGd');
INSERT INTO `user_account` VALUES ('45445dfgfdggf', null, '2018-04-23 12:50:21', '1', null, null, null, 'student', '698d51a19d8a121ce581499d7b701668', null, '2018-04-27 16:27:33', '5545454@qq.com', null, null, null, '1', '1', null, '1414080903229', 'Qd6r94Cwax0SUaM');
INSERT INTO `user_account` VALUES ('rmMWmWXNkv3ifKy', null, '2018-04-22 14:50:29', '1', null, null, null, '111', '698d51a19d8a121ce581499d7b701668', null, '2018-04-22 14:50:29', '111@qq.com', null, null, null, '1', null, null, null, null);
