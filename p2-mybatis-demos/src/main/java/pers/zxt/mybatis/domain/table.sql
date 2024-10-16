CREATE TABLE `students`  (
    `id` int NOT NULL,
    `name` varchar(128) NOT NULL COMMENT '姓名',
    `age` int DEFAULT NULL COMMENT '年龄',
    `grade` varchar(128) DEFAULT NULL COMMENT '年级',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES (1001, '张三', 7, '一年级');
INSERT INTO `students` VALUES (1002, '李四', 7, '一年级');
INSERT INTO `students` VALUES (1003, '小明', 8, '二年级');
INSERT INTO `students` VALUES (1004, '小华', 8, '二年级');
INSERT INTO `students` VALUES (1005, '小红', 8, '二年级');