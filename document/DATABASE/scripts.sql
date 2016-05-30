CREATE TABLE `properties` (
  `name` varchar(90) NOT NULL COMMENT '配置Key',
  `content` text NOT NULL COMMENT '配置Value',
  `descr` varchar(255) NULL COMMENT '备注',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置信息';


CREATE TABLE `member` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) DEFAULT NULL COMMENT '登录手机号（需要绑定手机号）',
  `wechat_id` varchar(60) DEFAULT NULL COMMENT '微信id',
  `nickname` varchar(60) NOT NULL COMMENT '用户昵称',
  `avatar` varchar(255) NOT NULL COMMENT '用户头像',
  `level` varchar(10) NOT NULL COMMENT '会员级别',
  `gender` char(1) DEFAULT NULL COMMENT '性别',
  `birthday` varchar(10) DEFAULT NULL COMMENT '生日',
  `my_promote_code` varchar(20) NULL COMMENT '推广码',
  `use_promote_code` varchar(20) DEFAULT NULL COMMENT '上级分销商推广码',
  `parent_uid` int(11) DEFAULT NULL COMMENT '上级分销商用户id',
  `deleted` char(1) NOT NULL COMMENT '是否逻辑删除',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(60) DEFAULT NULL COMMENT '最后一次登录IP',
  `register_time` datetime NOT NULL COMMENT '用户注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`phone`),
  UNIQUE KEY (`wechat_id`),
  UNIQUE KEY (`my_promote_code`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='会员信息';


CREATE TABLE `certified_info` (
  `uid` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `phone` varchar(15) NOT NULL COMMENT '手机号',
  `id_car_no` varchar(18) NOT NULL COMMENT '身份证号码',
  `idcar_image1` varchar(255) NOT NULL COMMENT '身份证正面照片',
  `idcar_image0` varchar(255) NOT NULL COMMENT '身份证背面照片',
  `status` tinyint(4) NOT NULL COMMENT '审核状态',
  `auditlog` varchar(300) NULL COMMENT '审核失败原因',
  `audit_time` datetime NULL COMMENT '审核时间',
  `create_time` datetime NOT NULL COMMENT '提交时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户认证信息';


CREATE TABLE `shipping_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '所属用户id',
  `province` varchar(30) NOT NULL COMMENT '省',
  `city` varchar(30) NOT NULL COMMENT '市',
  `county` varchar(30) NULL COMMENT '区/县',
  `detailed_address` varchar(240) NOT NULL COMMENT '详细地址',
  `consignee_name` varchar(30) NOT NULL COMMENT '收货人姓名',
  `consignee_phone` varchar(15) NOT NULL COMMENT '收货人电话',
  `postcode` char(6) DEFAULT NULL COMMENT '邮政编码',
  `defaul` char(1) NOT NULL COMMENT '是否默认地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户收货地址';


CREATE TABLE `distribution_relation` (
  `uid` int(11) NOT NULL COMMENT '所属用户id',
  `lower_uid` int(11) NOT NULL COMMENT '下级分销用户id',
  `level` tinyint(4) NOT NULL COMMENT '级别',
  `create_time` datetime DEFAULT NULL COMMENT '成为分销商时间',
  UNIQUE KEY (`id`,`lower_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分销商关系';


CREATE TABLE `cash_account` (
  `uid` int(11) NOT NULL COMMENT '所属用户id',
  `balance` decimal(10,2) NOT NULL COMMENT '会员账户余额',
  `commission` decimal(10,2) NOT NULL COMMENT '佣金账户金额',
  `total_sales` decimal(10,2) NOT NULL COMMENT '销售总额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资金账户信息';


CREATE TABLE `cash_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '所属用户id',
  `cash_type` tinyint(4) NOT NULL COMMENT '资金类型',
  `amount` decimal(10,2) NOT NULL COMMENT '变动金额',
  `balance` decimal(10,2) NOT NULL COMMENT '会员账户余额',
  `commission` decimal(10,2) NOT NULL COMMENT '佣金账户金额',
  `type` tinyint(4) NOT NULL COMMENT '会员充值，订单支付，订单退款，佣金提现，佣金提现退款，佣金结算',
  `content` varchar(255) NOT NULL COMMENT '内容明细',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户资金变动记录';


CREATE TABLE `commission_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '所属用户id',
  `lower_uid` int(11) NOT NULL COMMENT '产生佣金下级分销商id',
  `order_no` varchar(30) NOT NULL COMMENT '产生佣金的订单',
  `order_amount` decimal(10,2) NOT NULL COMMENT '产生佣金的订单金额',
  `commission_amount` decimal(10,2) NOT NULL COMMENT '佣金金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='佣金收入记录';


CREATE TABLE `withdraw_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '所属用户id',
  `amount` decimal(10,2) NOT NULL COMMENT '提现金额',
  `balance` decimal(10,2) NOT NULL COMMENT '提现后的佣金余额',
  `channel` varchar(20) NOT NULL COMMENT '提现渠道',
  `status` tinyint(4) NOT NULL COMMENT '提现状态',
  `auditlog` varchar(60) DEFAULT NULL COMMENT '审核日志',
  `apply_time` datetime NOT NULL COMMENT '申请提现时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `complete_time` datetime DEFAULT NULL COMMENT '提现完成时间，包含提现成功与提现失败',
  PRIMARY KEY (`id`),
  KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提现记录';


CREATE TABLE `message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '所属用户id',
  `title` varchar(90) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户消息';


CREATE TABLE `goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(90) NOT NULL COMMENT '商品标题',
  `main_image` varchar(255) NOT NULL COMMENT '商品主图片',
  `images` text NOT NULL COMMENT '商品图片数组',
  `category_id` int(11) NOT NULL COMMENT '所属类目',
  `price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `origin_price` decimal(10,2) NOT NULL COMMENT '商品原始价格',
  `inventory` mediumint(9) NOT NULL COMMENT '商品库存',
  `shelves` char(1) NOT NULL COMMENT '是否上架',
  `type` tinyint(4) NOT NULL COMMENT '商品类型（普通商品、会员充值卡）',
  `promotion` char(1) NOT NULL COMMENT '是否促销商品',
  `promotion_params` text COMMENT '促销配置',
  `freight_id` int(11) NOT NULL COMMENT '运费模板id',
  `descr` text NOT NULL COMMENT '商品描述',
  `product_params` text NULL COMMENT '产品参数',
  `sort` smallint(6) NOT NULL COMMENT '商品排序',
  `sales_volume` int(11) NOT NULL COMMENT '商品销量',
  `deleted` char(1) NOT NULL COMMENT '是否逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息';


CREATE TABLE `cart_goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '所属用户id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `buy_number` mediumint(9) NOT NULL COMMENT '购买数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`uid`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车商品';


CREATE TABLE `favorite_goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '所属用户id',
  `goods_id` int(11) NOT NULL COMMENT '收藏的商品ID',
  `title` varchar(90) NOT NULL COMMENT '商品标题',
  `image` varchar(255) NOT NULL COMMENT '商品图片',
  `price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`uid`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户商品收藏';


CREATE TABLE `orders` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '所属用户id',
  `order_no` varchar(30) NOT NULL COMMENT '订单编号',
  `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `deduction_amount` decimal(10,2) NOT NULL COMMENT '优惠劵抵扣金额',
  `freight` mediumint(9) NOT NULL COMMENT '运费',
  `total_weight` DECIMAL(10,1) NOT NULL COMMENT '商品总重量',
  `discounts` text COMMENT '订单优惠信息',
  `coupons` text COMMENT '使用的优惠劵',
  `leave_message` varchar(300) DEFAULT NULL COMMENT '买家留言',
  `status` tinyint(4) NOT NULL COMMENT '订单状态',
  `refund_status` tinyint(4) NOT NULL COMMENT '退款状态',
  `shipping_address` varchar(600) NOT NULL COMMENT '收货地址信息',
  `consignee_name` varchar(30) NOT NULL COMMENT '收货人姓名',
  `consignee_phone` varchar(15) NOT NULL COMMENT '收货人电话',
  `postcode` char(6) DEFAULT NULL COMMENT '邮政编码',
  `delivery_company` varchar(60) DEFAULT NULL COMMENT '发货物流公司',
  `delivery_code` varchar(60) DEFAULT NULL COMMENT '发货物流公司代码',
  `delivery_number` varchar(20) DEFAULT NULL COMMENT '发货物流单号',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `pay_time` datetime DEFAULT NULL COMMENT '订单支付时间',
  `confirm_delivery_time` datetime DEFAULT NULL COMMENT '订单确认收货时间',
  `apply_refund_time` datetime DEFAULT NULL COMMENT '订单申请退款时间',
  `refund_complete_time` datetime DEFAULT NULL COMMENT '订单退款完成时间',
  `refund_reason` varchar(300) DEFAULT NULL COMMENT '订单申请退款原因',
  `close_time` datetime DEFAULT NULL COMMENT '订单关闭时间',
  `closelog` varchar(60) DEFAULT NULL COMMENT '订单关闭日志',
  `timeout_close_time` datetime DEFAULT NULL COMMENT '超时未支付自动关闭时间',
  `create_time` datetime NOT NULL COMMENT '订单创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`order_no`),
  KEY (`uid`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息';


CREATE TABLE `order_goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(30) NOT NULL COMMENT '所属订单',
  `buy_number` mediumint(9) NOT NULL COMMENT '购买数量',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `title` varchar(90) NOT NULL COMMENT '商品标题',
  `image` varchar(255) NOT NULL COMMENT '商品图片',
  `price` decimal(10,2) NOT NULL COMMENT '商品购买价格',
  `origin_price` decimal(10,2) NOT NULL COMMENT '商品原始价格',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品';


CREATE TABLE `payment_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(30) NOT NULL COMMENT '所属订单',
  `amount` decimal(10,2) NOT NULL COMMENT '订单金额',
  `trade_no` varchar(80) NULL COMMENT '所属订单',
  `ip` varchar(30) NOT NULL COMMENT '支付IP',
  `pay_status` char(4) NOT NULL COMMENT '支付状态',
  `deleted` char(1) NOT NULL COMMENT '是否逻辑删除',
  `create_time` datetime NOT NULL COMMENT '订单创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  INDEX (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '支付记录';

-- ======================================================================================
/*
CREATE TABLE `article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL COMMENT '类型',
  `title` varchar(90) DEFAULT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章';

CREATE TABLE `category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '上级类目id',
  `name` varchar(30) NOT NULL COMMENT '类目名称',
  `sort` smallint(4) NOT NULL COMMENT '显示顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='商品类目';

CREATE TABLE `coupon` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '所属用户id',
  `amount` smallint(6) NOT NULL COMMENT '金额',
  `descr` varchar(90) NOT NULL COMMENT '优惠劵描述',
  `expira_time` datetime DEFAULT NULL COMMENT '过期时间',
  `used` char(1) NOT NULL COMMENT '是否已经使用',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  `used_order_no` varchar(30) DEFAULT NULL COMMENT '使用的订单号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户优惠券';

CREATE TABLE `payment_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(30) NOT NULL COMMENT '所属订单',
  `pay_type` char(4) NOT NULL COMMENT '支付类型',
  `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `pay_time` datetime NOT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`),
  INDEX (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000 COMMENT '订单支付信息';
*/
