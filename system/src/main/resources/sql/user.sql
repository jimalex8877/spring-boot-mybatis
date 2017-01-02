CREATE TABLE user
(
    id INT(11) PRIMARY KEY NOT NULL COMMENT '主键' AUTO_INCREMENT,
    userName VARCHAR(10) NOT NULL COMMENT '用户名',
    age INT(3) NOT NULL COMMENT '年龄',
    sex VARCHAR(1) NOT NULL COMMENT '性别'
);

INSERT INTO test.user (userName, age, sex) VALUES ('张三', 35, 'M');
INSERT INTO test.user (userName, age, sex) VALUES ('李四', 40, 'M');
INSERT INTO test.user (userName, age, sex) VALUES ('王五', 32, 'M');
INSERT INTO test.user (userName, age, sex) VALUES ('马六', 25, 'W');
INSERT INTO test.user (userName, age, sex) VALUES ('江明', 25, 'M');
INSERT INTO test.user (userName, age, sex) VALUES ('吴霞', 37, 'W');
INSERT INTO test.user (userName, age, sex) VALUES ('赵梅', 37, 'W');
INSERT INTO test.user (userName, age, sex) VALUES ('吕叶', 20, 'W');
INSERT INTO test.user (userName, age, sex) VALUES ('何进', 50, 'M');
INSERT INTO test.user (userName, age, sex) VALUES ('石头', 21, 'M');