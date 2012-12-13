/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50083
Source Host           : localhost:3306
Source Database       : heroku_blog_db

Target Server Type    : MYSQL
Target Server Version : 50083
File Encoding         : 65001

Date: 2012-12-13 18:52:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL auto_increment,
  `login` varchar(225) NOT NULL,
  `password` varchar(40) NOT NULL,
  `email` varchar(60) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `last_login_on` datetime default NULL,
  `create_at` datetime default NULL,
  `update_at` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'websoft', '123456', '751939573@qq.com', '', '2012-12-13 18:48:29', '2012-12-13 16:43:03', '2012-12-13 16:43:05');
