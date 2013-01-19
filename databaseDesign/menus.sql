/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50083
Source Host           : localhost:3306
Source Database       : heroku_blog_db

Target Server Type    : MYSQL
Target Server Version : 50083
File Encoding         : 65001

Date: 2012-12-13 18:52:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `menus`
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus` (
  `id` int(11) NOT NULL auto_increment,
  `menu_label` varchar(225) NOT NULL,
  `url_path` varchar(225) NOT NULL,
  `is_login` bit(1) NOT NULL,
  `create_at` datetime default NULL,
  `update_at` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` VALUES ('1', '发表博客', 'article/new.html', '', '2012-12-13 18:15:08', '2012-12-13 18:15:11');
INSERT INTO `menus` VALUES ('2', '修改密码', 'change_password.html', '', '2012-12-13 18:16:44', '2012-12-13 18:16:46');
INSERT INTO `menus` VALUES ('3', '设&nbsp;&nbsp;&nbsp;置', 'set.html', '', '2012-12-13 18:17:57', '2012-12-13 18:18:00');
