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