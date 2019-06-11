SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `links`
-- ----------------------------
DROP TABLE IF EXISTS `links`;
CREATE TABLE `links` (
  `id` int(11) NOT NULL auto_increment,
  `linkname` varchar(128) NOT NULL COMMENT '链接名称',
  `path` varchar(225) NOT NULL COMMENT '链接URL',
  `remark` varchar(225) NULL COMMENT '备注',
  `create_at` datetime default NULL,
  `update_at` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;