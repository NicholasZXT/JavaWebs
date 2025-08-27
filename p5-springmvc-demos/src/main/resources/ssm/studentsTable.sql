/*
 Navicat Premium Data Transfer

 Source Server         : MySQL-Local
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : springdb

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 10/04/2023 09:53:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students`  (
                             `id` int NOT NULL,
                             `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
                             `age` int NULL DEFAULT NULL COMMENT '年龄',
                             `grade` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '年级',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES (1001, '张三', 7, '一年级');
INSERT INTO `students` VALUES (1002, '李四', 7, '一年级');
INSERT INTO `students` VALUES (1003, '小明', 8, '二年级');
INSERT INTO `students` VALUES (1004, '小华', 8, '二年级');
INSERT INTO `students` VALUES (1005, '小红', 8, '二年级');

SET FOREIGN_KEY_CHECKS = 1;
