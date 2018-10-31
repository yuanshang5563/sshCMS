CREATE TABLE `core_menu` (
  `menu_id` varchar(32) NOT NULL,
  `icon` varchar(32) DEFAULT NULL,
  `menu_name` varchar(2048) DEFAULT NULL,
  `menu_type` int(11) DEFAULT NULL,
  `menu_url` varchar(2048) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `parent_menu_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`menu_id`),
  KEY `FKA1587E1FDE0FB054` (`parent_menu_id`),
  CONSTRAINT `FKA1587E1FDE0FB054` FOREIGN KEY (`parent_menu_id`) REFERENCES `core_menu` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `core_user` (
  `user_id` varchar(32) NOT NULL,
  `address` varchar(256) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `comment_context` varchar(1024) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `core_menu` VALUES ('402881ea66cb04390166cb043d380001', 'icon-menu-node', '用户管理', 1, '', 2, 'ff8080814bab3360014bab3362590001');
INSERT INTO `core_menu` VALUES ('402881ea66cb04390166cb043d380002', 'icon-menu-leaf', '用户列表', 0, 'userAction.do?method=getUserList', 4, '402881ea66cb04390166cb043d380001');
INSERT INTO `core_menu` VALUES ('ff8080814bab3360014bab3362590001', 'icon-menu-node', '管理系统', 1, '', 0, NULL);
INSERT INTO `core_menu` VALUES ('ff8080814bab3360014bab3362590002', 'icon-menu-node', '菜单管理', 1, '', 1, 'ff8080814bab3360014bab3362590001');
INSERT INTO `core_menu` VALUES ('ff8080814bab3360014bab3362590003', 'icon-menu-leaf', '菜单列表', 0, 'system/menuAction.do?method=getMenuList', 3, 'ff8080814bab3360014bab3362590002');

INSERT INTO `core_user` VALUES ('402881ea66cb03d80166cb03dbcd0001', 'test 1231231231', 25, '2018-11-1', 'test comment', 'test email', 'test pass', NULL, 'testUser');
INSERT INTO `core_user` VALUES ('402881ea66cb07300166cb0734390001', 'test 1231231231', 25, '2018-11-1', 'test comment', 'test email', 'test pass', NULL, 'testUser');
INSERT INTO `core_user` VALUES ('402881ea66cb6a7a0166cb72bc1b0002', '1', 11, '2018-11-6', '1', '1', '1', 0, '测试用户');
