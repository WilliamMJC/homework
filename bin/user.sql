/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : user

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-06 13:11:45
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
  `course_name` varchar(20) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of construction
-- ----------------------------
INSERT INTO `construction` VALUES ('6', 'feirty', '2', 'Android开发第1作业', '12.46K', 'Android开发', '2017-10-28 23:24:00');
INSERT INTO `construction` VALUES ('11', 'feirty', '1', '大学物理第1次作业', '9.10K', '大学物理', '2017-11-01 13:56:00');
INSERT INTO `construction` VALUES ('12', 'feirty', '1', '大学物理第1次作业', '9.10K', '大学物理', '2017-11-04 00:00:00');
INSERT INTO `construction` VALUES ('13', 'feirty', '1', '大学物理第1次作业', '9.10K', '大学物理', '2017-11-04 00:00:00');
INSERT INTO `construction` VALUES ('14', 'feirty', '1', '大学物理第1次作业', '9.10K', '大学物理', '2017-11-04 00:00:00');
INSERT INTO `construction` VALUES ('15', 'feirty', '1', '大学物理第1次作业', '9.10K', '大学物理', '2017-11-04 00:00:00');
INSERT INTO `construction` VALUES ('16', 'feirty', '1', '大学物理第1次作业', '9.10K', '大学物理', '2017-11-04 00:00:00');
INSERT INTO `construction` VALUES ('17', 'feirty', '1', '大学物理第1次作业', '9.10K', '大学物理', '2017-11-04 00:00:00');
INSERT INTO `construction` VALUES ('18', 'feirty', '1', '大学物理第1次作业', '9.10K', '大学物理', '2017-11-04 00:00:00');
INSERT INTO `construction` VALUES ('19', 'feirty', '1', '大学物理第1次作业', '9.10K', '大学物理', '2017-11-04 00:00:00');
INSERT INTO `construction` VALUES ('20', 'feirty', '1', '大学物理第1次作业', '9.10K', '大学物理', '2017-11-04 00:00:00');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `stu_number` int(11) DEFAULT NULL,
  `school` varchar(20) DEFAULT NULL,
  `tea_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('2', 'Android开发', '7', null, 'feirty');
INSERT INTO `course` VALUES ('5', '大学物理', '11', null, 'feirty');

-- ----------------------------
-- Table structure for `email`
-- ----------------------------
DROP TABLE IF EXISTS `email`;
CREATE TABLE `email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail_messageid` varchar(60) DEFAULT NULL,
  `mail_from` varchar(60) DEFAULT NULL,
  `mail_to` varchar(60) DEFAULT NULL,
  `mail_subject` varchar(50) DEFAULT NULL,
  `mail_sentdata` varchar(50) DEFAULT NULL,
  `mail_content` varchar(50) DEFAULT NULL,
  `mail_attachment_name` varchar(50) DEFAULT NULL,
  `mail_attachments_size` varchar(10) DEFAULT NULL,
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
  `No` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(60) DEFAULT NULL,
  `stu_id` varchar(20) DEFAULT NULL,
  `file_name` varchar(30) DEFAULT NULL,
  `file_size` varchar(10) DEFAULT NULL,
  `file_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `course_name` varchar(20) DEFAULT NULL,
  `teacher_name` varchar(10) DEFAULT NULL,
  `file_number` int(11) DEFAULT NULL,
  `submit_state` varchar(10) DEFAULT NULL,
  `receive_state` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`No`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('3', null, '1414080903202', null, null, '2017-11-02 22:45:20', 'Android开发', 'feirty', '1', '已提交', null);

-- ----------------------------
-- Table structure for `mail_identfy`
-- ----------------------------
DROP TABLE IF EXISTS `mail_identfy`;
CREATE TABLE `mail_identfy` (
  `identifying_mail` varchar(20) NOT NULL,
  `identfying_code` varchar(10) DEFAULT NULL,
  `identfying_code_number` int(11) DEFAULT NULL,
  `identfying_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`identifying_mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mail_identfy
-- ----------------------------
INSERT INTO `mail_identfy` VALUES ('274623281@qq.com', '01075', '1', '2017-11-05 18:07:45');

-- ----------------------------
-- Table structure for `makework`
-- ----------------------------
DROP TABLE IF EXISTS `makework`;
CREATE TABLE `makework` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `work_name` varchar(50) DEFAULT NULL,
  `work_content` varchar(200) DEFAULT NULL,
  `course_name` varchar(20) DEFAULT NULL,
  `work_number` int(11) DEFAULT NULL,
  `submitteds` int(11) DEFAULT NULL,
  `students` int(11) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `teacher_name` varchar(10) NOT NULL,
  `receive_state` varchar(10) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`teacher_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of makework
-- ----------------------------
INSERT INTO `makework` VALUES ('5', 'Android-2:网络编程', '1.测试测试\n2.测试测试', 'Android开发', '1', '0', '7', '2017-11-06 12:19:29', 'feirty', '未收取', '2017-11-06 00:00:00');
INSERT INTO `makework` VALUES ('6', '大学物理-1:搭建环境', '测试测试', '大学物理', '1', '1', '11', '2017-11-04 20:27:11', 'feirty', '已收取', '2017-11-04 00:00:00');
INSERT INTO `makework` VALUES ('7', 'Android-3:test', '1.text.\n2.text.', 'Android开发', '3', '0', '7', '2017-11-06 11:44:58', 'feirty', '未收取', '2017-11-06 00:00:00');

-- ----------------------------
-- Table structure for `school`
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES ('1', '惠州学院');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `number` varchar(20) NOT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `course` varchar(10) DEFAULT NULL,
  `teacher` varchar(10) DEFAULT NULL,
  `school` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('4', 'mxj', '1414080903229', '1050416617@qq.com', 'Android开发', 'feirty', null);
INSERT INTO `student` VALUES ('5', 'p', '1414080903202', '1425145@qq.com', 'Android开发', 'feirty', 'hzu');
INSERT INTO `student` VALUES ('6', null, '1414080903229', null, 'Android开发', 'feirty', null);
INSERT INTO `student` VALUES ('7', null, '1414080903204', null, 'Android开发', 'feirty', null);
INSERT INTO `student` VALUES ('8', 'q', '1414080903205', '1552666@qq.com', 'Android开发', 'feirty', 'hzu');
INSERT INTO `student` VALUES ('9', 'c', '1414080903206', '15566@qq.com', 'Android开发', 'feirty', 'hzu');
INSERT INTO `student` VALUES ('10', null, '1414080903201', null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('11', null, '1414080903202', null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('12', null, '1414080903203', null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('13', null, '1414080903204', null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('14', null, '1414080903205', null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('15', null, '1414080903206', null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('16', null, '1414080903207', null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('17', null, '1414080903208', null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('18', null, '1414080903209', null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('19', null, '1414080903210', null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('20', null, '1414080903229', null, '大学物理', 'feirty', null);

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `user_teacher` varchar(20) NOT NULL,
  `mail_name` varchar(50) NOT NULL,
  `mail_pwd` varchar(50) NOT NULL,
  `nickname` varchar(10) NOT NULL,
  `peasonmail` varchar(50) NOT NULL,
  `school` varchar(50) NOT NULL,
  PRIMARY KEY (`uid`,`user_teacher`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', 'abc', '1050416617@qq.com', 'jwovgwaypwrebecd', 'abc', '1050416617@qq.com', '惠州学院');
INSERT INTO `teacher` VALUES ('10', 'feirty', '1050416617@qq.com', 'jwovgwaypwrebecd', 'feirty', '1050416617@qq.com', 'bb');
INSERT INTO `teacher` VALUES ('14', 'hzu', '1050416617@qq.com', 'jwovgwaypwrebecd', 'feirty', '100@qq.com', 'hzu');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(12) NOT NULL,
  `mail_name` varchar(30) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'abc', '123', null, 'teacher');
INSERT INTO `user` VALUES ('2', 'mxj', '123', null, 'student');
INSERT INTO `user` VALUES ('13', 'feirty', '147258qwe', null, 'teacher');
INSERT INTO `user` VALUES ('14', 'p', '123', null, 'student');
INSERT INTO `user` VALUES ('1000', 'admin', '147258', null, 'amdin');
INSERT INTO `user` VALUES ('1001', 'test', '123', '274623281@qq.com', 'teacher');
