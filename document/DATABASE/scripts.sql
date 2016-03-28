CREATE TABLE `article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(1) NOT NULL,
  `title` varchar(90) NOT NULL,
  `content` text NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 ;

CREATE TABLE `category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `index` smallint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 ;

CREATE TABLE `cart` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL,
  `buy_number` mediumint(4) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`uid`, `goods_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 ;

CREATE TABLE `coupon` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `amount` smallint(4) NOT NULL,
  `descr` varchar(90) NOT NULL,
  `expira_time` datetime NULL,
  `used` varchar(1) NOT NULL,
  `used_time` datetime NULL,
  `used_order_no` varchar(30) NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  INDEX (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 ;

CREATE TABLE `delivery_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `province` varchar(30) NOT NULL,
  `city` varchar(60) NOT NULL,
  `county` varchar(30) NOT NULL,
  `detailed_address` varchar(200) NOT NULL,
  `consignee_name` varchar(30) NOT NULL,
  `consignee_phone` varchar(15) NOT NULL,
  `defaul` varchar(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NULL,
  PRIMARY KEY (`id`),
  INDEX (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 ;

CREATE TABLE `favorite` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL,
  `title` varchar(90) NOT NULL,
  `picture` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  INDEX (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 ;

CREATE TABLE `goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(90) NOT NULL,
  `main_picture` varchar(255) NOT NULL,
  `pictures` text NOT NULL,
  `category_id` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `inventory` mediumint(4) NOT NULL,
  `status` varchar(1) NOT NULL,
  `promotion` varchar(1) NOT NULL,
  `promotion_params` text NULL,
  `descr` text NULL,
  `product_params` text NULL,
  `sort` smallint(4) NOT NULL,
  `sales_volume` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 ;

CREATE TABLE `member` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) NULL,
  `password` varchar(16) NULL,
  `wechat_id` varchar(60) NULL,
  `nickname` varchar(60) NOT NULL,
  `avatar` varchar(255) NOT NULL,
  `gender` varchar(3) NULL,
  `birthday` varchar(10) NULL,
  `my_promote_code` varchar(60) NOT NULL,
  `use_promote_code` varchar(60) NULL,
  `last_login_time` datetime NULL,
  `last_login_ip` varchar(60) NULL,
  `register_time` datetime NOT NULL,
  `update_time` datetime NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`phone`),
  UNIQUE (`wechat_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=10000 ;

CREATE TABLE `message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `title` varchar(90) NOT NULL,
  `content` text NOT NULL,
  `read` varchar(1) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  INDEX (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 ;

CREATE TABLE `orders` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `order_no` varchar(30) NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `realpay_amount` decimal(10,2) NOT NULL,
  `deduction_amount` decimal(10,2) NOT NULL,
  `coupons` text NULL,
  `leave_message` varchar(255) NULL,
  `status` varchar(1) NOT NULL,
  `delivery_address` text NOT NULL,
  `create_time` datetime NOT NULL,
  `pay_time` datetime NULL,
  `delivery_time` datetime NULL,
  `receive_time` datetime NULL,
  `apply_refund_time` datetime NULL,
  `refund_complete_time` datetime NULL,
  `close_time` datetime NULL,
  `update_time` datetime NULL,
  PRIMARY KEY (`id`),
  INDEX (`uid`),
  INDEX (`status`),
  UNIQUE (`order_no`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=10000 ;

CREATE TABLE `order_goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `order_no` varchar(30) NOT NULL,
  `buy_number` mediumint(4) NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `goods_id` int(11) NOT NULL,
  `title` varchar(90) NOT NULL,
  `picture` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  INDEX (`order_no`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 ;

