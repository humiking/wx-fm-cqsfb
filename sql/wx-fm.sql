#==========1.1wx-fm-cqsfb 20180305===============================
ALTER TABLE wx_fm_list ADD `publish_status` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '发布状态：0：未发布  5：已发布   10：已下架';
ALTER TABLE wx_fm_list ADD `weight` INT(11) DEFAULT '0' COMMENT '权重:值越大越靠前 请输入9999以内的数字';
ALTER TABLE wx_fm_list ADD `create_time` BIGINT(20) DEFAULT NULL COMMENT '创建时间';
ALTER TABLE wx_fm_list ADD `update_time` BIGINT(20) DEFAULT NULL COMMENT '更新时间';
ALTER TABLE wx_fm_list ADD `publish_time` BIGINT(20) DEFAULT NULL COMMENT '发布时间';


ALTER TABLE wx_mp3url_list ADD `status` TINYINT(4) DEFAULT '0' COMMENT '状态：0:正常 1:删除';
ALTER TABLE wx_mp3url_list ADD `create_time` BIGINT(20) DEFAULT NULL COMMENT '创建时间';
ALTER TABLE wx_mp3url_list ADD `update_time` BIGINT(20) DEFAULT NULL COMMENT '更新时间';

alter table wx_fm_list drop `mp3Url_id` ;
ALTER TABLE wx_fm_list DROP `focus_status`;
ALTER TABLE wx_fm_list DROP `user_id`;
#==========1.1wx-fm-cqsfb 20180224===============================
DROP TABLE IF EXISTS `wx_mp3url_list`;

CREATE TABLE `wx_mp3url_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fm_id` bigint(20) NOT NULL COMMENT '电台ID',
  `map3Url` varchar(200) DEFAULT NULL COMMENT '电台请求链接',
  `duration` bigint(20) DEFAULT NULL COMMENT '当前音频的时长',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='电台请求URL列表';

#==========1.1wx-fm-cqsfb 20180224===============================
DROP TABLE IF EXISTS `wx_fm_user`;

CREATE TABLE `wx_fm_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `PASSWORD` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `NAME` varchar(45) DEFAULT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `roleid` varchar(255) DEFAULT NULL COMMENT '角色id',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `createtime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `weixin_account` varchar(100) DEFAULT NULL COMMENT '微信账号',
  `weixin_name` varchar(45) DEFAULT NULL COMMENT '微信关联姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户列表';

#==========1.1wx-fm-cqsfb 20180224===============================
DROP TABLE IF EXISTS `wx_fm_list`;

CREATE TABLE `wx_fm_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `mp3Url_id` varchar(200) DEFAULT NULL COMMENT 'MP3的播放地址_id',
  `NAME` varchar(100) DEFAULT NULL COMMENT '歌曲名称',
  `poster` varchar(200) DEFAULT NULL COMMENT '海报的请求地址',
  `artistor_name` varchar(100) DEFAULT NULL COMMENT '艺术家的名称',
  `total_duration` bigint(20) DEFAULT NULL COMMENT '总计时长',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0:正常 -1:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='电台列表';

ALTER TABLE wx_fm_list DROP user_id;
#==========1.1wx-fm-cqsfb 20180224===============================
CREATE TABLE `wx_banner` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   `weight` tinyint(4) DEFAULT '1' COMMENT '权重：数字从高到低，权重从大到小',
   `img_url` varchar(256) NOT NULL COMMENT '图片url',
   `create_time` bigint(20) DEFAULT '0' COMMENT '创建时间',
   `update_time` bigint(20) DEFAULT '0' COMMENT '更新时间',
   `status` tinyint(4) DEFAULT '0' COMMENT '状态：0:正常 -1:删除',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='轮播图';
 
 