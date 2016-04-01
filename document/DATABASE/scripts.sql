CREATE TABLE `article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(1) NOT NULL COMMENT '类型',
  `title` varchar(90) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 COMMENT '文章';

CREATE TABLE `category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NULL COMMENT '上级类目id',
  `name` varchar(30) NOT NULL COMMENT '类目名称',
  `index` smallint(4) NOT NULL COMMENT '显示顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 COMMENT '商品类目';

CREATE TABLE `cart` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '',
  `goods_id` int(11) NOT NULL COMMENT '',
  `buy_number` mediumint(4) NOT NULL COMMENT '',
  `create_time` datetime NOT NULL COMMENT '',
  PRIMARY KEY (`id`),
  UNIQUE (`uid`, `goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 COMMENT '';

CREATE TABLE `coupon` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '',
  `amount` smallint(4) NOT NULL COMMENT '',
  `descr` varchar(90) NOT NULL COMMENT '',
  `expira_time` datetime NULL COMMENT '',
  `used` varchar(1) NOT NULL COMMENT '',
  `used_time` datetime NULL COMMENT '',
  `used_order_no` varchar(30) NULL COMMENT '',
  `create_time` datetime NOT NULL COMMENT '',
  PRIMARY KEY (`id`),
  INDEX (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 COMMENT '';

CREATE TABLE `delivery_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '',
  `province` varchar(30) NOT NULL COMMENT '',
  `city` varchar(60) NOT NULL COMMENT '',
  `county` varchar(30) NOT NULL COMMENT '',
  `detailed_address` varchar(150) NOT NULL COMMENT '',
  `consignee_name` varchar(30) NOT NULL COMMENT '',
  `consignee_phone` varchar(15) NOT NULL COMMENT '',
  `defaul` varchar(1) NOT NULL COMMENT '',
  `create_time` datetime NOT NULL COMMENT '',
  `update_time` datetime NULL COMMENT '',
  PRIMARY KEY (`id`),
  INDEX (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 COMMENT '';

CREATE TABLE `favorite` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '',
  `goods_id` int(11) NOT NULL COMMENT '',
  `title` varchar(90) NOT NULL COMMENT '',
  `picture` varchar(255) NOT NULL COMMENT '',
  `price` decimal(10,2) NOT NULL COMMENT '',
  `create_time` datetime NOT NULL COMMENT '',
  PRIMARY KEY (`id`),
  INDEX (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 COMMENT '';

CREATE TABLE `goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(90) NOT NULL COMMENT '',
  `main_picture` varchar(255) NOT NULL COMMENT '',
  `pictures` text NOT NULL COMMENT '',
  `category_id` int(11) NOT NULL COMMENT '',
  `price` decimal(10,2) NOT NULL COMMENT '',
  `inventory` mediumint(4) NOT NULL COMMENT '',
  `status` varchar(1) NOT NULL COMMENT '',
  `promotion` varchar(1) NOT NULL COMMENT '',
  `promotion_params` text NULL COMMENT '',
  `descr` text NULL COMMENT '',
  `product_params` text NULL COMMENT '',
  `sort` smallint(4) NOT NULL COMMENT '',
  `sales_volume` int(11) NOT NULL COMMENT '',
  `create_time` datetime NOT NULL COMMENT '',
  `update_time` datetime NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 COMMENT '';

CREATE TABLE `member` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) NULL COMMENT '',
  `password` varchar(16) NULL COMMENT '',
  `wechat_id` varchar(60) NULL COMMENT '',
  `nickname` varchar(60) NOT NULL COMMENT '',
  `avatar` varchar(255) NOT NULL COMMENT '',
  `gender` varchar(3) NULL COMMENT '',
  `birthday` varchar(10) NULL COMMENT '',
  `my_promote_code` varchar(60) NOT NULL COMMENT '',
  `use_promote_code` varchar(60) NULL COMMENT '',
  `last_login_time` datetime NULL COMMENT '',
  `last_login_ip` varchar(60) NULL COMMENT '',
  `register_time` datetime NOT NULL COMMENT '',
  `update_time` datetime NULL COMMENT '',
  PRIMARY KEY (`id`),
  UNIQUE (`phone`),
  UNIQUE (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=10000 COMMENT '';

CREATE TABLE `message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '',
  `title` varchar(90) NOT NULL COMMENT '',
  `content` text NOT NULL COMMENT '',
  `read` varchar(1) NOT NULL COMMENT '',
  `create_time` datetime NOT NULL COMMENT '',
  PRIMARY KEY (`id`),
  INDEX (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 COMMENT '';

CREATE TABLE `orders` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '',
  `order_no` varchar(30) NOT NULL COMMENT '',
  `total_amount` decimal(10,2) NOT NULL COMMENT '',
  `realpay_amount` decimal(10,2) NOT NULL COMMENT '',
  `deduction_amount` decimal(10,2) NOT NULL COMMENT '',
  `coupons` text NULL COMMENT '',
  `leave_message` varchar(255) NULL COMMENT '',
  `status` varchar(1) NOT NULL COMMENT '',
  `delivery_address` varchar(255) NOT NULL COMMENT '',
  `consignee_name` varchar(30) NOT NULL COMMENT '',
  `consignee_phone` varchar(15) NOT NULL COMMENT '',
  `create_time` datetime NOT NULL COMMENT '',
  `pay_time` datetime NULL COMMENT '',
  `delivery_time` datetime NULL COMMENT '',
  `receive_time` datetime NULL COMMENT '',
  `apply_refund_time` datetime NULL COMMENT '',
  `refund_complete_time` datetime NULL COMMENT '',
  `close_time` datetime NULL COMMENT '',
  `update_time` datetime NULL COMMENT '',
  PRIMARY KEY (`id`),
  INDEX (`uid`),
  INDEX (`status`),
  UNIQUE (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=10000 COMMENT '';

CREATE TABLE `order_goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '',
  `order_no` varchar(30) NOT NULL COMMENT '',
  `buy_number` mediumint(4) NOT NULL COMMENT '',
  `total_price` decimal(10,2) NOT NULL COMMENT '',
  `goods_id` int(11) NOT NULL COMMENT '',
  `title` varchar(90) NOT NULL COMMENT '',
  `picture` varchar(255) NOT NULL COMMENT '',
  `price` decimal(10,2) NOT NULL COMMENT '',
  `create_time` datetime NOT NULL COMMENT '',
  PRIMARY KEY (`id`),
  INDEX (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 COMMENT '';

CREATE TABLE `properties` (
  `name` varchar(200) NOT NULL COMMENT '',
  `content` text NOT NULL COMMENT '',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
