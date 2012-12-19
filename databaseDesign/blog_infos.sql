/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50083
Source Host           : localhost:3306
Source Database       : heroku_blog_db

Target Server Type    : MYSQL
Target Server Version : 50083
File Encoding         : 65001

Date: 2012-12-19 17:42:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `blog_infos`
-- ----------------------------
DROP TABLE IF EXISTS `blog_infos`;
CREATE TABLE `blog_infos` (
  `id` int(11) NOT NULL auto_increment,
  `head` varchar(255) NOT NULL,
  `descriptions` varchar(225) NOT NULL,
  `about` longtext NOT NULL,
  `associate_email` varchar(60) NOT NULL,
  `access_num` bigint(20) NOT NULL,
  `update_at` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_infos
-- ----------------------------
INSERT INTO `blog_infos` VALUES ('3', '飘痕', '心诚则灵', '<h2 style=\"margin:0 0 5px 10px;\">Welcome you access my zone!</h2>\r\n<p style=\"margin:0 0 5px 15px;\">In building...</p>\r\n<p style=\"margin:0 0 5px 15px;\">Connect with me: <a style=\"color:red;\" href=\"https://twitter.com/lzqwebsoft\">Twitter</a></p>\r\n<p style=\"margin:0 0 5px 15px;\">本站点使用Heroku云平台建立，仅用于学习Java。</p>\r\n<p style=\"margin:0 0 5px 15px;\">基于Spring3.0 MVC 与 Hibernate3.6。</p>\r\n<hr style=\"margin-bottom:5px;\" />\r\n<p style=\"font-size:12px;margin-bottom:20px;text-align:center;\">Copyright © 2012,Powered by <a style=\"color:red;\" href=\"http://www.heroku.com\">Heroku</a></p>', 'lzqwebsoft@gmail.com', '0', '2012-12-19 17:26:32');
