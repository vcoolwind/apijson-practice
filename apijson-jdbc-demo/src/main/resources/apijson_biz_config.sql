-- 设置每个业务表每个角色的访问权限
INSERT INTO Access
(id, debug, name, alias, `get`, head, gets, heads, post, put, `delete`, `date`, detail)
VALUES(100, 0, 'User', NULL, '["LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["UNKNOWN","LOGIN","OWNER", "ADMIN"]', '["OWNER", "ADMIN"]', '["OWNER", "ADMIN"]', '2021-07-28 22:02:41.0', '?û???????Ϣ?');
INSERT INTO Access
(id, debug, name, alias, `get`, head, gets, heads, post, put, `delete`, `date`, detail)
VALUES(101, 0, 'Credential', NULL, '[]', '[]', '["UNKNOWN","OWNER", "ADMIN"]', '["OWNER", "ADMIN"]', '["UNKNOWN","LOGIN","OWNER", "ADMIN"]', '["OWNER", "ADMIN"]', '["ADMIN"]', '2021-07-28 22:04:01.0', '');
INSERT INTO Access
(id, debug, name, alias, `get`, head, gets, heads, post, put, `delete`, `date`, detail)
VALUES(102, 0, 'Todo', NULL, '["LOGIN","CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["LOGIN","OWNER", "ADMIN"]', '["LOGIN","CIRCLE","OWNER","ADMIN"]', '["OWNER", "ADMIN"]', '2021-07-28 22:02:41.0', '');
INSERT INTO Access
(id, debug, name, alias, `get`, head, gets, heads, post, put, `delete`, `date`, detail)
VALUES(103, 0, 'Moment', NULL, '["LOGIN","CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["UNKNOWN", "LOGIN", "CONTACT", "CIRCLE", "OWNER", "ADMIN"]', '["LOGIN","OWNER", "ADMIN"]', '["LOGIN","CIRCLE","OWNER","ADMIN"]', '["OWNER", "ADMIN"]', '2021-07-28 22:02:41.0', '');

-- 设置每个tag的请求提交规则
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(2, 1, 'POST', 'api_register', '{"User": {"MUST": "username,realname", "REFUSE": "id", "UNIQUE": "username"}, "Credential": {"MUST": "pwdHash", "UPDATE": {"id@": "User/id"}}}', '', '2021-07-29 02:15:40.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(3, 1, 'PUT', 'User', '{"REFUSE": "username", "UPDATE": {"@role": "OWNER"}}', '', '2021-07-29 20:49:20.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(4, 1, 'POST', 'Todo', '{"MUST": "title", "UPDATE": {"@role": "OWNER"}, "REFUSE": "id"}', '', '2021-07-29 21:18:50.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(5, 1, 'PUT', 'Todo', '{"Todo":{ "MUST":"id","REFUSE": "userId", "UPDATE": {"checkCanPutx-()": "isUserCanPutTodo(id)"}} }', '', '2021-07-29 22:05:57.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(6, 1, 'DELETE', 'Todo', '{"MUST": "id", "REFUSE": "!", "INSERT": {"@role": "OWNER"}}', '', '2021-07-29 22:10:32.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(8, 1, 'PUT', 'helper+', '{"Todo": {"MUST": "id,helper+", "INSERT": {"@role": "OWNER"}}}', '', '2021-07-30 05:46:34.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(9, 1, 'PUT', 'helper-', '{"Todo": {"MUST": "id,helper-", "INSERT": {"@role": "OWNER"}}}', '', '2021-07-30 05:46:34.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(10, 1, 'POST', 'Todo:[]', '{"Todo[]": [{"MUST": "title", "REFUSE": "id"}], "UPDATE": {"@role": "OWNER"}}', '', '2021-08-01 12:51:31.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(11, 1, 'PUT', 'Todo:[]', '{"Todo[]":[{ "MUST":"id","REFUSE": "userId", "UPDATE": {"checkCanPut-()": "isUserCanPutTodo(id)"}}] }', '', '2021-08-01 12:51:31.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(12, 1, 'PUT', 'Todo[]', '{"Todo":{ "MUST":"id{}","REFUSE": "userId", "UPDATE": {"checkCanPut-()": "isUserCanPutTodo(id)"}} }', '', '2021-08-01 12:51:31.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(13, 1, 'POST', 'Moment', '{"MUST": "content", "UPDATE": {"@role": "OWNER"}, "REFUSE": "id"}', 'add Moment', '2021-07-29 21:18:50.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(14, 1, 'PUT', 'Moment', '{"Moment":{ "MUST":"id","REFUSE": "userId", "UPDATE": {"checkCanPut-()": "isUserCanPutMoment(id)"}} }', 'edit Moment', '2021-07-29 22:05:57.0');
INSERT INTO Request
(id, version, `method`, tag, `structure`, detail, `date`)
VALUES(15, 1, 'DELETE', 'Moment', '{"MUST": "id", "REFUSE": "!", "INSERT": {"@role": "OWNER"}}', 'delete Moment', '2021-07-29 22:10:32.0');

-- 注册函数可被调用
INSERT INTO `Function`
(id, userId, name, arguments, demo, detail, `type`, version, tag, methods, `date`, back)
VALUES(16, 0, 'isUserCanPutTodo', 'todoId', '{"todoId": 123}', '', 'Object', 0, NULL, NULL, '2021-07-28 20:04:27.0', NULL);
