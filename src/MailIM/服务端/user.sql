/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : user

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-07-30 00:07:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `construction`
-- ----------------------------
DROP TABLE IF EXISTS `construction`;
CREATE TABLE `construction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_name` varchar(20) NOT NULL,
  `number` bigint(11) NOT NULL,
  `zipname` varchar(30) NOT NULL,
  `zipsize` varchar(10) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of construction
-- ----------------------------
INSERT INTO `construction` VALUES ('1', 'abc', '2', '作业附件201707220533.zip', '50.25K', '2017-07-22 00:00:00');
INSERT INTO `construction` VALUES ('2', 'abc', '2', '作业附件201707220543.zip', '50.25K', '2017-07-22 00:00:00');
INSERT INTO `construction` VALUES ('3', 'abc', '2', '作业附件201707220548.zip', '50.25K', '2017-07-22 00:00:00');
INSERT INTO `construction` VALUES ('4', 'abc', '2', '作业附件201707220551.zip', '50.25K', '2017-07-22 00:00:00');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `sut_number` int(11) DEFAULT NULL,
  `school` varchar(20) DEFAULT NULL,
  `tea_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------

-- ----------------------------
-- Table structure for `email`
-- ----------------------------
DROP TABLE IF EXISTS `email`;
CREATE TABLE `email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `messageid` varchar(30) DEFAULT NULL,
  `from` varchar(30) DEFAULT NULL,
  `to` varchar(30) DEFAULT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `sentdata` varchar(50) DEFAULT NULL,
  `content` varchar(50) DEFAULT NULL,
  `attachment_name` varchar(50) DEFAULT NULL,
  `attachments_size` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of email
-- ----------------------------

-- ----------------------------
-- Table structure for `homework`
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` varchar(20) NOT NULL,
  `file_name` varchar(30) DEFAULT NULL,
  `file_size` varchar(10) DEFAULT NULL,
  `file_time` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`,`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('1', 'abc', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('2', 'abc', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('3', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('4', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('5', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('6', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25');
INSERT INTO `homework` VALUES ('7', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('8', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25');
INSERT INTO `homework` VALUES ('9', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('10', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25');
INSERT INTO `homework` VALUES ('11', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('12', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25');
INSERT INTO `homework` VALUES ('13', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('14', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25');
INSERT INTO `homework` VALUES ('15', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('16', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25');
INSERT INTO `homework` VALUES ('17', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('18', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25');
INSERT INTO `homework` VALUES ('19', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41');
INSERT INTO `homework` VALUES ('20', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25');

-- ----------------------------
-- Table structure for `mailbyuser`
-- ----------------------------
DROP TABLE IF EXISTS `mailbyuser`;
CREATE TABLE `mailbyuser` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `user_teacher` varchar(20) NOT NULL,
  `mail_name` varchar(50) NOT NULL,
  `mail_pwd` varchar(50) NOT NULL,
  `nickname` varchar(10) NOT NULL,
  `peasonmail` varchar(50) NOT NULL,
  `school` varchar(50) NOT NULL,
  PRIMARY KEY (`uid`,`user_teacher`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mailbyuser
-- ----------------------------
INSERT INTO `mailbyuser` VALUES ('1', 'abc', '1050416617@qq.com', 'jwovgwaypwrebecd', 'abc', '1050416617@qq.com', '惠州学院');

-- ----------------------------
-- Table structure for `school`
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school
-- ----------------------------

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `number` varchar(20) NOT NULL,
  `worksnumber` int(11) DEFAULT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `course` varchar(10) DEFAULT NULL,
  `teacher` varchar(10) DEFAULT NULL,
  `school` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '123', '1414080903229', null, '1050416617@qq.com', null, 'abc', '惠州学院');
INSERT INTO `student` VALUES ('2', null, '1414080903299', null, null, null, null, null);
INSERT INTO `student` VALUES ('3', null, '1414080903888', null, null, null, null, null);

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(12) NOT NULL,
  `type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'abc', '123', 'teacher');
INSERT INTO `user` VALUES ('2', 'mxj', '123', 'student');
INSERT INTO `user` VALUES ('9', '123', '123', 'student');
