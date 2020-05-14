/*
Navicat MySQL Data Transfer

Source Server         : 本地连接
Source Server Version : 50728
Source Host           : localhost:3306
Source Database       : engineering_certification

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2020-05-14 21:22:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `type` varchar(100) NOT NULL COMMENT '字典类型',
  `code` varchar(100) NOT NULL COMMENT '字典码',
  `value` varchar(1000) NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典表';

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';

-- ----------------------------
-- 初始化菜单数据
-- ----------------------------
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('1', '0', '系统管理', NULL, NULL, '0', 'fa fa-cog', '2');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('2', '0', '用户管理', 'modules/sys/user.html', NULL, '1', 'fa fa-user', '1');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('3', '1', '角色管理', 'modules/sys/role.html', NULL, '1', 'fa fa-user-secret', '2');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('4', '1', '菜单管理', 'modules/sys/menu.html', NULL, '1', 'fa fa-th-list', '3')；
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('5', '2', '查看', NULL, 'sys:user:list,sys:user:info', '2', NULL, '0');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('6', '2', '新增', NULL, 'sys:user:save,sys:role:select', '2', NULL, '0');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('7', '2', '修改', NULL, 'sys:user:update,sys:role:select', '2', NULL, '0');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('8', '2', '删除', NULL, 'sys:user:delete', '2', NULL, '0');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('9', '3', '查看', NULL, 'sys:role:list,sys:role:info', '2', NULL, '0');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('10', '3', '新增', NULL, 'sys:role:save,sys:menu:perms', '2', NULL, '0');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('11', '3', '修改', NULL, 'sys:role:update,sys:menu:perms', '2', NULL, '0');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('12', '3', '删除', NULL, 'sys:role:delete', '2', NULL, '0');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('13', '4', '查看', NULL, 'sys:menu:list,sys:menu:info', '2', NULL, '0');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('14', '4', '新增', NULL, 'sys:menu:save,sys:menu:select', '2', NULL, '0');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('15', '4', '修改', NULL, 'sys:menu:update,sys:menu:select', '2', NULL, '0');
INSERT INTO `engineering_certification`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('16', '4', '删除', NULL, 'sys:menu:delete', '2', NULL, '0');


-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名(用户登录的学号/工号)',
  `name` varchar(50) NOT NULL COMMENT '用户姓名',
  `password` varchar(100) NOT NULL COMMENT '密码（初始密码：123456）',
  `salt` varchar(20) NOT NULL COMMENT '盐（密码加密验证）',
  `school_name` varchar(50) DEFAULT '' COMMENT '学校名称',
  `school_code` varchar(100) DEFAULT '' COMMENT '学校简码',
  `college_name` varchar(50) DEFAULT '' COMMENT '学院名称',
  `department` varchar(20) DEFAULT '' COMMENT '系名',
  `major` varchar(20) DEFAULT '' COMMENT '专业',
  `job_number` varchar(50) DEFAULT NULL COMMENT '工号/学号',
  `id_number` varchar(20) DEFAULT '' COMMENT '身份证号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8673 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- ----------------------------
-- 初始化用户信息 账号：administrator 密码：123456
-- 超级管理员拥有超级权限
-- ----------------------------
INSERT INTO `engineer`.`sys_user` (`user_id`, `username`, `name`, `password`, `salt`, `school_name`, `school_code`, `college_name`, `department`, `major`, `job_number`, `id_number`, `email`, `mobile`, `status`, `create_time`) VALUES ('1', 'administrator', '超级管理员', '5f9c50b9d370e553b076ecf20870baab6dff1d061fb15868b62ca17f04b70a16', 'YzcmCZNvbXocrsz9dm8e', NULL, '', '', '', '', 'administrator', '', 'root@binhan.vip', '', '1', '2020-11-01 11:11:11');


-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Table structure for tra_course_capability_list
-- ----------------------------
DROP TABLE IF EXISTS `tra_course_capability_list`;
CREATE TABLE `tra_course_capability_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `course_id` bigint(20) DEFAULT NULL,
  `course_number` varchar(20) DEFAULT '' COMMENT '课程编号',
  `capability` double(11,2) DEFAULT NULL COMMENT '能力',
  `weight` double(11,2) DEFAULT NULL COMMENT '权重',
  `course_name` varchar(20) DEFAULT '' COMMENT '课程名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `training_id` bigint(20) NOT NULL COMMENT '培养方案ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='课程能力清单';

-- ----------------------------
-- Table structure for tra_course_info
-- ----------------------------
DROP TABLE IF EXISTS `tra_course_info`;
CREATE TABLE `tra_course_info` (
  `course_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `course_number` varchar(20) DEFAULT '' COMMENT '课程编号',
  `course_name` varchar(20) DEFAULT '' COMMENT '课程名称',
  `theoretical_hours` int(11) DEFAULT NULL COMMENT '理论学时',
  `practical_hours` int(11) DEFAULT NULL COMMENT '实践学时',
  `credit` int(11) DEFAULT NULL COMMENT '学分',
  `course_big_categories` varchar(20) DEFAULT NULL COMMENT '课程大类',
  `course_small_categories` varchar(20) DEFAULT NULL COMMENT '课程小类',
  `elective_mode` varchar(10) DEFAULT NULL COMMENT '选修方式',
  `start_time` varchar(50) DEFAULT NULL COMMENT '起始时间',
  `end_time` varchar(50) DEFAULT NULL COMMENT '终止时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `training_id` bigint(20) NOT NULL COMMENT '培养方案ID',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='课程信息';

-- ----------------------------
-- Table structure for tra_major_capability
-- ----------------------------
DROP TABLE IF EXISTS `tra_major_capability`;
CREATE TABLE `tra_major_capability` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `standard_capacity_number` varchar(20) DEFAULT '' COMMENT '标准能力编号',
  `support_weight` int(11) DEFAULT NULL COMMENT '支撑权重',
  `capacity_number` varchar(20) NOT NULL DEFAULT '' COMMENT '能力编号（防止出现1.2.1这类编号所以使用varchar）',
  `parent_node` varchar(20) DEFAULT '' COMMENT '父节点，对应能力编号',
  `capability_description` text COMMENT '能力描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `training_id` bigint(20) NOT NULL COMMENT '培养方案ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='专业能力';

-- ----------------------------
-- Table structure for tra_major_capability_target
-- ----------------------------
DROP TABLE IF EXISTS `tra_major_capability_target`;
CREATE TABLE `tra_major_capability_target` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` int(11) NOT NULL COMMENT '编号',
  `title` varchar(20) DEFAULT '' COMMENT '标题',
  `description` text COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `training_id` bigint(20) NOT NULL COMMENT '培养方案ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='专业培养目标';

-- ----------------------------
-- Table structure for tra_standard_capability
-- ----------------------------
DROP TABLE IF EXISTS `tra_standard_capability`;
CREATE TABLE `tra_standard_capability` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `standard` varchar(50) DEFAULT NULL COMMENT '认证标准',
  `version` varchar(20) DEFAULT '' COMMENT '版本',
  `Issued_by` varchar(20) DEFAULT '' COMMENT '发布单位',
  `capacity_number` varchar(20) NOT NULL DEFAULT '' COMMENT '能力编号（防止出现1.2.1这类编号所以使用varchar）',
  `parent_node` varchar(20) DEFAULT '' COMMENT '父节点，对应能力编号',
  `capability_description` text COMMENT '能力描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `training_id` bigint(20) NOT NULL COMMENT '培养方案ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='标准能力';

-- ----------------------------
-- Table structure for tra_training_goal_support_list
-- ----------------------------
DROP TABLE IF EXISTS `tra_training_goal_support_list`;
CREATE TABLE `tra_training_goal_support_list` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `training_objectives` varchar(20) NOT NULL COMMENT '培养目标',
  `graduation_requirements` int(11) DEFAULT NULL COMMENT '毕业要求',
  `weight` double(11,2) DEFAULT NULL COMMENT '权重',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `training_id` bigint(20) NOT NULL COMMENT '培养方案ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COMMENT='培养目标支撑清单';

-- ----------------------------
-- Table structure for tra_training_program
-- ----------------------------
DROP TABLE IF EXISTS `tra_training_program`;
CREATE TABLE `tra_training_program` (
  `training_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `school_name` varchar(50) DEFAULT '' COMMENT '学校名称',
  `college_name` varchar(50) DEFAULT '' COMMENT '学院名称',
  `department` varchar(20) DEFAULT '' COMMENT '系名',
  `major` varchar(20) DEFAULT '' COMMENT '专业',
  `major_code` varchar(50) DEFAULT '' COMMENT '专业代码',
  `major_abbreviation` varchar(20) DEFAULT NULL COMMENT '专业简称',
  `discipline_category` varchar(50) DEFAULT '' COMMENT '学科门类',
  `training_program` varchar(50) DEFAULT '' COMMENT '培养方案',
  `version` varchar(20) DEFAULT '' COMMENT '版本',
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`training_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='培养方案';
