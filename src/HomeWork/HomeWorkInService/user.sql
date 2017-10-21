/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : user

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-21 22:39:18
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of construction
-- ----------------------------
INSERT INTO `construction` VALUES ('2', 'feirty', '3', '大学物理作业附件201710181745.zip', '19.69K', null, '2017-10-18 17:46:00');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `stu_number` int(11) DEFAULT NULL,
  `works_number` int(11) DEFAULT NULL,
  `school` varchar(20) DEFAULT NULL,
  `tea_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('2', 'Android开发', '7', '1', null, 'feirty');
INSERT INTO `course` VALUES ('5', '大学物理', '11', '1', null, 'feirty');

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
) ENGINE=InnoDB AUTO_INCREMENT=1501 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of email
-- ----------------------------
INSERT INTO `email` VALUES ('1216', '<tencent_21005182B2E1921FC12A146C41879D4DCD08@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验3', '17.09.10-23:31', '(1)nihaoadsdd(2)deffsfeerterr', '《专业实践》报告封面.docx', null);
INSERT INTO `email` VALUES ('1217', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1218', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1219', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1220', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1221', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1222', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1223', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1224', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1225', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1226', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1227', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1228', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1229', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1230', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1231', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1232', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1233', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1234', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1235', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1236', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1237', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1238', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1239', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1240', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1241', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1242', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1243', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1244', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1245', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1246', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1247', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1248', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1249', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1250', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1251', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1252', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1253', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1254', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1255', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1256', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1257', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1258', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1259', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1260', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1261', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1262', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1263', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1264', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1265', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1266', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1267', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1268', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1269', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1270', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1271', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1272', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1273', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1274', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1275', '<tencent_BCE14ED332009AA04238DB87964A355BDB06@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验6', '17.09.14-11:24', '1414080903229-麦贤军-实验6', 'ExportExcel.java', null);
INSERT INTO `email` VALUES ('1276', '<tencent_F486CB7F4CA890B16D1DB2363101BC0B4A0A@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验9', '17.09.25-17:21', '看到了吗', '整理.txt', null);
INSERT INTO `email` VALUES ('1277', '<tencent_D329FCE2635AA5904DFA70ACA91120529005@qq.com>', '×¨゛沙__<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.09-13:35', '', '1414080903229-麦贤军.docx', null);
INSERT INTO `email` VALUES ('1278', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1279', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1280', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1281', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1282', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1283', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1284', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1285', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1286', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1287', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1288', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1289', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1290', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1291', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1292', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1293', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1294', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1295', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1296', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1297', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1298', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1299', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1300', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1301', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1302', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1303', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1304', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1305', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1306', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1307', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1308', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1309', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1310', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1311', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1312', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1313', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1314', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1315', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1316', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1317', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1318', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1319', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1320', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1321', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1322', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1323', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1324', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1325', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1326', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1327', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1328', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1329', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1330', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1331', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1332', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1333', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1334', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1335', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1336', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1337', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1338', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1339', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1340', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1341', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1342', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1343', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1344', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1345', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1346', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1347', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1348', null, null, null, '[任务]实验一要求', '17.10.09-10:41', '实验一要求', null, null);
INSERT INTO `email` VALUES ('1349', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1350', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1351', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1352', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1353', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1354', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1355', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1356', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1357', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1358', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1359', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1360', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1361', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1362', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1363', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1364', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1365', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1366', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1367', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1368', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1369', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1370', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1371', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1372', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1373', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1374', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1375', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1376', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1377', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1378', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1379', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1380', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1381', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1382', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1383', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1384', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1385', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1386', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1387', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1388', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1389', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1390', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1391', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1392', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1393', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1394', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1395', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1396', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1397', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1398', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1399', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1400', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1401', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1402', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1403', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1404', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1405', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1406', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1407', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1408', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1409', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1410', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1411', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1412', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1413', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1414', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1415', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1416', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1417', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1418', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1419', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1420', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1421', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1422', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1423', null, null, null, 'dsfsfsdf', '17.10.17-22:11', 'sfsdfdfsdfsd', null, null);
INSERT INTO `email` VALUES ('1424', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1425', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1426', null, null, null, 'dsfsfsdf', '17.10.17-22:11', 'sfsdfdfsdfsd', null, null);
INSERT INTO `email` VALUES ('1427', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1428', null, null, null, 'fgdfgf', '17.10.17-22:16', 'fgdfgdfg', null, null);
INSERT INTO `email` VALUES ('1429', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1430', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1431', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1432', null, null, null, 'dsfsfsdf', '17.10.17-22:11', 'sfsdfdfsdfsd', null, null);
INSERT INTO `email` VALUES ('1433', null, null, null, 'fgdfgf', '17.10.17-22:16', 'fgdfgdfg', null, null);
INSERT INTO `email` VALUES ('1434', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1435', null, null, null, 'dsfsfsdf', '17.10.17-22:11', 'sfsdfdfsdfsd', null, null);
INSERT INTO `email` VALUES ('1436', null, null, null, 'fgdfgf', '17.10.17-22:16', 'fgdfgdfg', null, null);
INSERT INTO `email` VALUES ('1437', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1438', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1439', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1440', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1441', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1442', null, null, null, 'dsfsfsdf', '17.10.17-22:11', 'sfsdfdfsdfsd', null, null);
INSERT INTO `email` VALUES ('1443', null, null, null, 'fgdfgf', '17.10.17-22:16', 'fgdfgdfg', null, null);
INSERT INTO `email` VALUES ('1444', null, null, null, 'fabuzuoy', '17.10.16-15:16', 'sdxcxcxc', null, null);
INSERT INTO `email` VALUES ('1445', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1446', null, null, null, 'dsfsfsdf', '17.10.17-22:11', 'sfsdfdfsdfsd', null, null);
INSERT INTO `email` VALUES ('1447', null, null, null, 'fgdfgf', '17.10.17-22:16', 'fgdfgdfg', null, null);
INSERT INTO `email` VALUES ('1448', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1449', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1450', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1451', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1452', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1453', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1454', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1455', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1456', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1457', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1458', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1459', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1460', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1461', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1462', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1463', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1464', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1465', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1466', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1467', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1468', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1469', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1470', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1471', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1472', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1473', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1474', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1475', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1476', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1477', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1478', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1479', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1480', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1481', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1482', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1483', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1484', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1485', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1486', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1487', '<tencent_D59C8F329514B6DDC3A2A64E004EC1F5F007@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1488', '<tencent_A43ADCEF8359E58B4DE110BA890E40469005@qq.com>', '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1489', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1490', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1491', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1492', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1493', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1494', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1495', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1496', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1497', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1498', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);
INSERT INTO `email` VALUES ('1499', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验1', '17.10.12-20:29', '', '1414080903229-麦贤军-实验1.docx', null);
INSERT INTO `email` VALUES ('1500', null, '麦贤军<silen.ce@foxmail.com>', null, '1414080903229-麦贤军-实验2', '17.10.12-20:29', '', '1414080903229-麦贤军-实验2.docx', null);

-- ----------------------------
-- Table structure for `homework`
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework` (
  `id` varchar(50) NOT NULL,
  `stu_id` varchar(20) NOT NULL,
  `file_name` varchar(30) DEFAULT NULL,
  `file_size` varchar(10) DEFAULT NULL,
  `file_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `course_name` varchar(20) DEFAULT NULL,
  `file_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('145', '1414080903229', '1414080903229-麦贤军-实验1.docx', '11.58K', '2017-10-12 20:29:00', '大学物理', '1');
INSERT INTO `homework` VALUES ('146', '1414080903229', '1414080903229-麦贤军-实验2.docx', '11.56K', '2017-10-12 20:29:00', '大学物理', '1');
INSERT INTO `homework` VALUES ('147', '1414080903229', '1414080903229-麦贤军-实验1.docx', '11.58K', '2017-10-12 20:29:00', '大学物理', '1');
INSERT INTO `homework` VALUES ('148', '1414080903229', '1414080903229-麦贤军-实验2.docx', '11.56K', '2017-10-12 20:29:00', '大学物理', '1');

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
  `start_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT NULL,
  `teacher_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of makework
-- ----------------------------
INSERT INTO `makework` VALUES ('1', '[课程:Android开发]201710181719', 'adwdsfwerww', null, null, '2017-10-18 17:22:00', '2017-10-21 22:20:00', 'feirty');
INSERT INTO `makework` VALUES ('2', '201710181805', '201710181806', 'Android开发', null, '2017-10-18 18:06:00', '2017-10-19 10:05:00', 'feirty');
INSERT INTO `makework` VALUES ('3', '2017110181806', '201710181806', '大学物理', null, '2017-10-18 18:07:00', '2017-10-19 10:06:00', 'feirty');

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
  `worksnumber` int(11) DEFAULT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `course` varchar(10) DEFAULT NULL,
  `teacher` varchar(10) DEFAULT NULL,
  `school` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('4', 'mxj', '1414080903229', null, '1050416617@qq.com', 'Android开发', 'feirty', null);
INSERT INTO `student` VALUES ('5', 'p', '1414080903202', null, '1425145@qq.com', 'Android开发', 'feirty', 'hzu');
INSERT INTO `student` VALUES ('6', null, '1414080903229', null, null, 'Android开发', 'feirty', null);
INSERT INTO `student` VALUES ('7', null, '1414080903204', null, null, 'Android开发', 'feirty', null);
INSERT INTO `student` VALUES ('8', 'q', '1414080903205', null, '1552666@qq.com', 'Android开发', 'feirty', 'hzu');
INSERT INTO `student` VALUES ('9', 'c', '1414080903206', null, '15566@qq.com', 'Android开发', 'feirty', 'hzu');
INSERT INTO `student` VALUES ('10', null, '1414080903201', null, null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('11', null, '1414080903202', null, null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('12', null, '1414080903203', null, null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('13', null, '1414080903204', null, null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('14', null, '1414080903205', null, null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('15', null, '1414080903206', null, null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('16', null, '1414080903207', null, null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('17', null, '1414080903208', null, null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('18', null, '1414080903209', null, null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('19', null, '1414080903210', null, null, '大学物理', 'feirty', null);
INSERT INTO `student` VALUES ('20', null, '1414080903229', null, null, '大学物理', 'feirty', null);

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
  `type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'abc', '123', 'teacher');
INSERT INTO `user` VALUES ('2', 'mxj', '123', 'student');
INSERT INTO `user` VALUES ('10', 'test', '123', 'student');
INSERT INTO `user` VALUES ('11', 'abab', '147258', 'student');
INSERT INTO `user` VALUES ('12', 'aaa', '123', 'student');
INSERT INTO `user` VALUES ('13', 'feirty', '147258qwe', 'teacher');
INSERT INTO `user` VALUES ('14', 'p', '123', 'student');
INSERT INTO `user` VALUES ('15', 'q', '123', 'student');
INSERT INTO `user` VALUES ('16', 'c', '123', 'student');
INSERT INTO `user` VALUES ('17', 'lm001', '123', '0');
INSERT INTO `user` VALUES ('18', 'demo', '123', 'teacher');
INSERT INTO `user` VALUES ('19', '1', '1', 'student');
INSERT INTO `user` VALUES ('20', '2', '2', 'student');
INSERT INTO `user` VALUES ('21', '3', '3', 'student');
INSERT INTO `user` VALUES ('22', '4', '4', 'student');
INSERT INTO `user` VALUES ('23', '5', '5', 'student');
INSERT INTO `user` VALUES ('24', '6', '6', 'student');
INSERT INTO `user` VALUES ('25', '7', '7', 'student');
INSERT INTO `user` VALUES ('26', '8', '8', 'student');
INSERT INTO `user` VALUES ('27', '9', '9', 'student');
