/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : user

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-08-25 13:25:14
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of construction
-- ----------------------------
INSERT INTO `construction` VALUES ('1', 'abc', '2', '作业附件201707220533.zip', '50.25K', '2017-07-22 00:00:00');
INSERT INTO `construction` VALUES ('2', 'abc', '2', '作业附件201707220543.zip', '50.25K', '2017-07-22 00:00:00');
INSERT INTO `construction` VALUES ('3', 'abc', '2', '作业附件201707220548.zip', '50.25K', '2017-07-22 00:00:00');
INSERT INTO `construction` VALUES ('4', 'abc', '2', '作业附件201707220551.zip', '50.25K', '2017-07-22 00:00:00');
INSERT INTO `construction` VALUES ('5', 'abc', '2', '作业附件201708081316.zip', '50.25K', '2017-08-08 00:00:00');
INSERT INTO `construction` VALUES ('6', 'abc', '2', '作业附件201708081323.zip', '50.25K', '2017-08-08 00:00:00');
INSERT INTO `construction` VALUES ('7', 'abc', '2', '作业附件201708081326.zip', '50.25K', '2017-08-08 00:00:00');
INSERT INTO `construction` VALUES ('8', 'abc', '1', '作业附件201708151128.zip', '22.63K', '2017-08-15 00:00:00');
INSERT INTO `construction` VALUES ('9', 'abc', '1', '作业附件201708151130.zip', '22.63K', '2017-08-15 00:00:00');
INSERT INTO `construction` VALUES ('10', 'abc', '1', '作业附件201708151135.zip', '22.63K', '2017-08-15 00:00:00');
INSERT INTO `construction` VALUES ('11', 'abc', '1', '作业附件201708151138.zip', '22.63K', '2017-08-15 00:00:00');
INSERT INTO `construction` VALUES ('12', 'abc', '1', '作业附件201708151416.zip', '22.63K', '2017-08-15 00:00:00');
INSERT INTO `construction` VALUES ('13', 'abc', '1', '作业附件201708151418.zip', '22.63K', '2017-08-15 00:00:00');
INSERT INTO `construction` VALUES ('14', 'abc', '2', '作业附件201708161555.zip', '25.83K', '2017-08-16 00:00:00');
INSERT INTO `construction` VALUES ('15', 'abc', '3', '作业附件201708221927.zip', '27.42K', '2017-08-22 00:00:00');
INSERT INTO `construction` VALUES ('16', 'abc', '3', '作业附件201708222129.zip', '27.48K', '2017-08-22 00:00:00');
INSERT INTO `construction` VALUES ('17', 'abc', '3', '作业附件201708232147.zip', '27.52K', '2017-08-23 00:00:00');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', 'droid开发', '-1', null, 'feirty');
INSERT INTO `course` VALUES ('2', 'ndroid开发', '7', null, 'feirty');

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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of email
-- ----------------------------
INSERT INTO `email` VALUES ('1', null, '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军', '17.07.20-03:25', '', null, null);
INSERT INTO `email` VALUES ('2', null, '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军', '17.07.20-03:25', '', null, null);
INSERT INTO `email` VALUES ('3', '<tencent_4A3DEF717E8FB9B50BB2B8B3@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军', '17.07.20-03:25', '', '收发作业用例规约.docx', null);
INSERT INTO `email` VALUES ('4', '<tencent_4A3DEF717E8FB9B50BB2B8B3@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军', '17.07.20-03:25', '', '收发作业用例规约.docx', null);
INSERT INTO `email` VALUES ('5', '<tencent_4A3DEF717E8FB9B50BB2B8B3@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军', '17.07.20-03:25', '', '收发作业用例规约.docx', null);
INSERT INTO `email` VALUES ('6', null, '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('7', null, '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('8', null, '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('9', null, '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('10', null, '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('11', null, '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('12', null, '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('13', '<tencent_4418593134CB3E57356F219B@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('14', '<tencent_4418593134CB3E57356F219B@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('15', '<tencent_4418593134CB3E57356F219B@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('16', '<tencent_4418593134CB3E57356F219B@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('17', '<tencent_4418593134CB3E57356F219B@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('18', '<tencent_4418593134CB3E57356F219B@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('19', '<tencent_31FAED445A4573F4358AF165@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903201-asasdas', '17.08.23-22:39', '', 'user_image.jpg', null);
INSERT INTO `email` VALUES ('20', '<tencent_4418593134CB3E57356F219B@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('21', '<tencent_31FAED445A4573F4358AF165@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903201-asasdas', '17.08.23-22:39', '', 'user_image.jpg', null);
INSERT INTO `email` VALUES ('22', '<tencent_4418593134CB3E57356F219B@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('23', '<tencent_31FAED445A4573F4358AF165@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903201-asasdas', '17.08.23-22:39', '', 'user_image.jpg', null);
INSERT INTO `email` VALUES ('24', '<tencent_4418593134CB3E57356F219B@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('25', '<tencent_31FAED445A4573F4358AF165@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903201-asasdas', '17.08.23-22:39', '', 'user_image.jpg', null);
INSERT INTO `email` VALUES ('26', '<tencent_509F523A4A517A6E30E5CC22@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903201-黄海新', '17.08.23-22:48', '', 'tao_user_2.png', null);
INSERT INTO `email` VALUES ('27', '<tencent_4418593134CB3E57356F219B@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('28', '<tencent_31FAED445A4573F4358AF165@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903201-asasdas', '17.08.23-22:39', '', 'user_image.jpg', null);
INSERT INTO `email` VALUES ('29', '<tencent_509F523A4A517A6E30E5CC22@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903201-黄海新', '17.08.23-22:48', '', 'tao_user_2.png', null);
INSERT INTO `email` VALUES ('30', '<tencent_4418593134CB3E57356F219B@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.08.19-15:43', '', 'export2003_a.xls', null);
INSERT INTO `email` VALUES ('31', '<tencent_31FAED445A4573F4358AF165@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903201-asasdas', '17.08.23-22:39', '', 'user_image.jpg', null);
INSERT INTO `email` VALUES ('32', '<tencent_509F523A4A517A6E30E5CC22@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903201-黄海新', '17.08.23-22:48', '', 'tao_user_2.png', null);

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
  `file_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('1', 'abc', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('2', 'abc', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('3', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('4', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('5', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('6', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '0');
INSERT INTO `homework` VALUES ('7', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('8', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '0');
INSERT INTO `homework` VALUES ('9', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('10', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '0');
INSERT INTO `homework` VALUES ('11', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('12', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '0');
INSERT INTO `homework` VALUES ('13', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('14', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '0');
INSERT INTO `homework` VALUES ('15', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('16', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '0');
INSERT INTO `homework` VALUES ('17', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('18', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '0');
INSERT INTO `homework` VALUES ('19', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('20', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '0');
INSERT INTO `homework` VALUES ('21', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('22', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '0');
INSERT INTO `homework` VALUES ('23', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('24', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '0');
INSERT INTO `homework` VALUES ('25', '1414080903229', '收发作业用例规约(老师修改).docx', '31.73K', '17-07-12 16:41', '0');
INSERT INTO `homework` VALUES ('26', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '0');
INSERT INTO `homework` VALUES ('27', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '1');
INSERT INTO `homework` VALUES ('28', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '1');
INSERT INTO `homework` VALUES ('29', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '1');
INSERT INTO `homework` VALUES ('30', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '1');
INSERT INTO `homework` VALUES ('31', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '1');
INSERT INTO `homework` VALUES ('32', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '1');
INSERT INTO `homework` VALUES ('33', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '1');
INSERT INTO `homework` VALUES ('34', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '1');
INSERT INTO `homework` VALUES ('35', '1414080903229', '收发作业用例规约.docx', '26.37K', '17-07-20 03:25', '1');
INSERT INTO `homework` VALUES ('36', '1414080903229', 'export2003_a.xls', '5.50K', '17-08-19 15:43', '1');
INSERT INTO `homework` VALUES ('37', '1414080903229', 'export2003_a.xls', '5.50K', '17-08-19 15:43', '1');
INSERT INTO `homework` VALUES ('38', '1414080903229', 'export2003_a.xls', '5.50K', '17-08-19 15:43', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mailbyuser
-- ----------------------------
INSERT INTO `mailbyuser` VALUES ('1', 'abc', '1050416617@qq.com', 'jwovgwaypwrebecd', 'abc', '1050416617@qq.com', '惠州学院');
INSERT INTO `mailbyuser` VALUES ('7', 'bb', '1050416617@qq.com', 'jwovgwaypwrebecd', 'feirty', '111@qq.com', 'bb');
INSERT INTO `mailbyuser` VALUES ('10', 'bb', '1050416617@qq.com', 'jwovgwaypwrebecd', 'feirty', '1050416617@qq.com', 'bb');
INSERT INTO `mailbyuser` VALUES ('11', 'bb', '1050416617@qq.com', 'jwovgwaypwrebecd', 'feirty', '1050416617@qq.com', 'bb');
INSERT INTO `mailbyuser` VALUES ('12', 'bb', '1050416617@qq.com', 'jwovgwaypwrebecd', 'feirty', '1050416617@qq.com', 'bb');
INSERT INTO `mailbyuser` VALUES ('13', 'bb', '1050416617@qq.com', 'jwovgwaypwrebecd', 'feirty', '1050416617@qq.com', 'bb');
INSERT INTO `mailbyuser` VALUES ('14', 'hzu', '1050416617@qq.com', 'jwovgwaypwrebecd', 'feirty', '100@qq.com', 'hzu');

-- ----------------------------
-- Table structure for `makework`
-- ----------------------------
DROP TABLE IF EXISTS `makework`;
CREATE TABLE `makework` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `work_number` varchar(50) NOT NULL,
  `work_content` varchar(200) NOT NULL,
  `arrange_time` varchar(20) NOT NULL,
  `teacher_name` varchar(10) NOT NULL,
  PRIMARY KEY (`id`,`work_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of makework
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', 'aaa', '1414080903229', null, '1050416617@qq.com', null, 'abc', 'huizx');
INSERT INTO `student` VALUES ('4', null, '1414080903201', null, null, 'ndroid开发', 'feirty', null);
INSERT INTO `student` VALUES ('5', null, '1414080903202', null, null, 'ndroid开发', 'feirty', null);
INSERT INTO `student` VALUES ('6', null, '1414080903229', null, null, 'ndroid开发', 'feirty', null);
INSERT INTO `student` VALUES ('7', null, '1414080903204', null, null, 'ndroid开发', 'feirty', null);
INSERT INTO `student` VALUES ('8', null, '1414080903205', null, null, 'ndroid开发', 'feirty', null);
INSERT INTO `student` VALUES ('9', null, '1414080903206', null, null, 'ndroid开发', 'feirty', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'abc', '123', 'teacher');
INSERT INTO `user` VALUES ('2', 'mxj', '123', 'student');
INSERT INTO `user` VALUES ('9', '123', '123', 'student');
INSERT INTO `user` VALUES ('10', 'test', '123', 'student');
INSERT INTO `user` VALUES ('11', 'abab', '147258', 'student');
INSERT INTO `user` VALUES ('12', 'aaa', '123', 'student');
INSERT INTO `user` VALUES ('13', 'feirty', '147258qwe', 'teacher');
