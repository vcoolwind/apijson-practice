-- 创建数据库，以时间为准
-- CREATE DATABASE `todo_demo_db`;

DROP TABLE IF EXISTS `Access`;
CREATE TABLE `Access` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT,
  `debug` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否为调试表，只允许在开发环境使用，测试和线上环境禁用',
  `name` varchar(50) NOT NULL COMMENT '实际表名，例如 apijson_user',
  `alias` varchar(20) DEFAULT NULL COMMENT '外部调用的表别名，例如 User',
  `get` varchar(100) NOT NULL DEFAULT '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]' COMMENT '允许 get 的角色列表，例如 ["LOGIN", "CONTACT", "CIRCLE", "OWNER"]\n用 JSON 类型不能设置默认值，反正权限对应的需求是明确的，也不需要自动转 JSONArray。\nTODO: 直接 LOGIN,CONTACT,CIRCLE,OWNER 更简单，反正是开发内部用，不需要复杂查询。',
  `head` varchar(100) NOT NULL DEFAULT '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]' COMMENT '允许 head 的角色列表，例如 ["LOGIN", "CONTACT", "CIRCLE", "OWNER"]',
  `gets` varchar(100) NOT NULL DEFAULT '["LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]' COMMENT '允许 gets 的角色列表，例如 ["LOGIN", "CONTACT", "CIRCLE", "OWNER"]',
  `heads` varchar(100) NOT NULL DEFAULT '["LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]' COMMENT '允许 heads 的角色列表，例如 ["LOGIN", "CONTACT", "CIRCLE", "OWNER"]',
  `post` varchar(100) NOT NULL DEFAULT '["OWNER", "ADMIN"]' COMMENT '允许 post 的角色列表，例如 ["LOGIN", "CONTACT", "CIRCLE", "OWNER"]',
  `put` varchar(100) NOT NULL DEFAULT '["OWNER", "ADMIN"]' COMMENT '允许 put 的角色列表，例如 ["LOGIN", "CONTACT", "CIRCLE", "OWNER"]',
  `delete` varchar(100) NOT NULL DEFAULT '["OWNER", "ADMIN"]' COMMENT '允许 delete 的角色列表，例如 ["LOGIN", "CONTACT", "CIRCLE", "OWNER"]',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `detail` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `alias_UNIQUE` (`alias`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='权限配置(必须)';
INSERT INTO Access
(id, debug, name, alias, `get`, head, gets, heads, post, put, `delete`, `date`, detail)
VALUES(1, 0, 'Access', NULL, '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '[]', '[]', '[]', '2019-07-21 12:21:36.0', NULL);
INSERT INTO Access
(id, debug, name, alias, `get`, head, gets, heads, post, put, `delete`, `date`, detail)
VALUES(2, 0, 'Function', NULL, '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '[]', '[]', '[]', '2018-11-29 00:38:15.0', '???ܱ?????Ҫ');


DROP TABLE IF EXISTS `Function`;
CREATE TABLE `Function` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT,
  `userId` bigint(15) NOT NULL COMMENT '管理员用户Id',
  `name` varchar(50) NOT NULL COMMENT '方法名',
  `arguments` varchar(100) DEFAULT NULL COMMENT '参数列表，每个参数的类型都是 String。\n用 , 分割的字符串 比 [JSONArray] 更好，例如 array,item ，更直观，还方便拼接函数。',
  `demo` json NOT NULL COMMENT '可用的示例。\nTODO 改成 call，和返回值示例 back 对应。',
  `detail` varchar(1000) NOT NULL COMMENT '详细描述',
  `type` varchar(50) NOT NULL DEFAULT 'Object' COMMENT '返回值类型。TODO RemoteFunction 校验 type 和 back',
  `version` tinyint(4) NOT NULL DEFAULT '0' COMMENT '允许的最低版本号，只限于GET,HEAD外的操作方法。\nTODO 使用 requestIdList 替代 version,tag,methods',
  `tag` varchar(20) DEFAULT NULL COMMENT '允许的标签.\nnull - 允许全部\nTODO 使用 requestIdList 替代 version,tag,methods',
  `methods` varchar(50) DEFAULT NULL COMMENT '允许的操作方法。\nnull - 允许全部\nTODO 使用 requestIdList 替代 version,tag,methods',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `back` varchar(45) DEFAULT NULL COMMENT '返回值示例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='远程函数。强制在启动时校验所有demo是否能正常运行通过';
INSERT INTO `Function`
(id, userId, name, arguments, demo, detail, `type`, version, tag, methods, `date`, back)
VALUES(1, 0, 'countArray', 'array', '{"array": [1, 2, 3]}', '', 'Object', 0, NULL, NULL, '2018-10-13 16:23:23.0', NULL);
INSERT INTO `Function`
(id, userId, name, arguments, demo, detail, `type`, version, tag, methods, `date`, back)
VALUES(2, 0, 'isContain', 'array,value', '{"array": [1, 2, 3], "value": 2}', '', 'Object', 0, NULL, NULL, '2018-10-13 16:23:23.0', NULL);
INSERT INTO `Function`
(id, userId, name, arguments, demo, detail, `type`, version, tag, methods, `date`, back)
VALUES(3, 0, 'getFromArray', 'array,position', '{"array": [1, 2, 3], "result()": "getFromArray(array,1)"}', '', 'Object', 0, NULL, NULL, '2018-10-13 16:30:31.0', NULL);
INSERT INTO `Function`
(id, userId, name, arguments, demo, detail, `type`, version, tag, methods, `date`, back)
VALUES(4, 0, 'getFromObject', 'object,key', '{"key": "id", "object": {"id": 1}}', '', 'Object', 0, NULL, NULL, '2018-10-13 16:30:31.0', NULL);


DROP TABLE IF EXISTS `Request`;
CREATE TABLE `Request` (
  `id` bigint(15) NOT NULL COMMENT '唯一标识',
  `version` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'GET,HEAD可用任意结构访问任意开放内容，不需要这个字段。\n其它的操作因为写入了结构和内容，所以都需要，按照不同的version选择对应的structure。\n\n自动化版本管理：\nRequest JSON最外层可以传  “version”:Integer 。\n1.未传或 <= 0，用最新版。 “@order”:”version-“\n2.已传且 > 0，用version以上的可用版本的最低版本。 “@order”:”version+”, “version{}”:”>={version}”',
  `method` varchar(10) DEFAULT 'GETS' COMMENT '只限于GET,HEAD外的操作方法。',
  `tag` varchar(20) NOT NULL COMMENT '标签',
  `structure` json NOT NULL COMMENT '结构。\nTODO 里面的 PUT 改为 UPDATE，避免和请求 PUT 搞混。',
  `detail` varchar(10000) DEFAULT NULL COMMENT '详细说明',
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请求参数校验配置(必须)。\n最好编辑完后删除主键，这样就是只读状态，不能随意更改。需要更改就重新加上主键。\n\n每次启动服务器时加载整个表到内存。\n这个表不可省略，model内注解的权限只是客户端能用的，其它可以保证即便服务端代码错误时也不会误删数据。';
INSERT INTO apijson_todo_demo.Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(1, 1, 'POST', 'default_placeholder', '{"Default": {"MUST": "xxx", "REFUSE": "id", "UNIQUE": "username"}}', '仅仅是为了占位', '2021-07-29 02:15:40.0');

