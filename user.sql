DROP TABLE IF EXISTS `tm_member_identity`;
CREATE TABLE `tm_member_identity` (
  `member_id` VARCHAR(16) NOT NULL COMMENT '会员ID',
  `identity_id` VARCHAR(64) NOT NULL COMMENT '登录名',
  `identity_type` TINYINT(1) NOT NULL  COMMENT '登录名类型',
  `pid_id` INT(10) NOT NULL COMMENT '用户类型，1学生，2老师',
  partner_id VARCHAR(12) NOT NULL COMMENT '代理方',
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE INDEX `et_member_identity` (`identity_id`,`identity_type`,`pid_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '会员表';

CREATE TABLE `tm_member` (
  `member_id` VARCHAR(16) NOT NULL COMMENT '会员ID',
  `member_name` VARCHAR(64) NOT NULL COMMENT '登录名',
  `type` TINYINT(1) NOT NULL  COMMENT '1学生，2老师',
  `pid_id` INT(10) NOT NULL COMMENT '用户类型',
  real_name VARCHAR(64) DEFAULT NULL COMMENT'真实姓名',
  cert_no VARCHAR(64) COMMENT '证件号',
  teacher_type VARCHAR(18) COMMENT '教学类型',
  demo VARCHAR(256) COMMENT '个人介绍',
  wechat  VARCHAR(256) COMMENT '微信号',
  alipay VARCHAR(256) COMMENT '支付宝账号',
  lable VARCHAR(256) COMMENT '标签',
  `password` VARCHAR(256) COMMENT '密码',
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '会员信息表';

CREATE TABLE `tm_course` (
  `member_id` VARCHAR(16) NOT NULL COMMENT '会员ID',
  `course_id` VARCHAR(64) NOT NULL COMMENT '课程id',
  `course_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '课程时间',
  teacher_type VARCHAR(18) COMMENT '教学类型',
  demo VARCHAR(256) COMMENT '课程介绍',
  lable VARCHAR(256) COMMENT '标签',
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`course_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '课程表';
