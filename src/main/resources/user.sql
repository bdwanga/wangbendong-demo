CREATE TABLE user (
  user_id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '�û�id',
  user_name varchar(100) DEFAULT NULL COMMENT '�û���',
  user_password varchar(100) DEFAULT NULL COMMENT '����',
  user_token varchar(100) DEFAULT NULL COMMENT '����',
  user_nick varchar(100) DEFAULT NULL COMMENT '�ǳ�',
  user_org varchar(100) DEFAULT NULL COMMENT '�û���λ',
  user_phone varchar(15) DEFAULT NULL COMMENT '�ֻ���',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8