CREATE TABLE user (
  user_id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  user_name varchar(100) DEFAULT NULL COMMENT '用户名',
  user_password varchar(100) DEFAULT NULL COMMENT '密码',
  user_token varchar(100) DEFAULT NULL COMMENT '令牌',
  user_nick varchar(100) DEFAULT NULL COMMENT '昵称',
  user_org varchar(100) DEFAULT NULL COMMENT '用户单位',
  user_phone varchar(15) DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8