/*
 Navicat MySQL Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : carmanage

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 22/12/2017 13:08:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'root', 'admin', NULL, NULL, '');
INSERT INTO `admin` VALUES (2, 'a', 'b', 'c', NULL, NULL);
INSERT INTO `admin` VALUES (5, '嗲带我', '123456', '1749597640@qq.com', 'hahaha', 'aab');
INSERT INTO `admin` VALUES (6, 'd', 'wed', 'ewde', NULL, NULL);
INSERT INTO `admin` VALUES (7, 'ef', 'ew', 'ewc', NULL, NULL);
INSERT INTO `admin` VALUES (8, '3', '2', 'd2', NULL, NULL);
INSERT INTO `admin` VALUES (9, 'ce', 'cd', 'csd', NULL, NULL);
INSERT INTO `admin` VALUES (10, 'da', 'ada', 'sa', NULL, NULL);
INSERT INTO `admin` VALUES (11, 'zzk', '1k3k5k', '786197140@qq.com', NULL, NULL);
INSERT INTO `admin` VALUES (12, 'dwq', 'sa', '786197140@qq.com', NULL, NULL);
INSERT INTO `admin` VALUES (13, 'qwe', 'dsd', '786197140@qq.com', NULL, NULL);
INSERT INTO `admin` VALUES (14, 'qwe', 'dsd', '786197140@q.com', NULL, NULL);
INSERT INTO `admin` VALUES (15, 'Cwj', '1k3k5k', '1749597640@qq.com', NULL, NULL);

-- ----------------------------
-- Table structure for broadcast
-- ----------------------------
DROP TABLE IF EXISTS `broadcast`;
CREATE TABLE `broadcast`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of broadcast
-- ----------------------------
INSERT INTO `broadcast` VALUES (2, '哈哈哈哈', '2017-12-21');
INSERT INTO `broadcast` VALUES (3, '呆呆', '2017-12-21');

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` int(10) NOT NULL,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES (3, 'che11', 213, NULL);
INSERT INTO `car` VALUES (4, 'che11', 213, NULL);
INSERT INTO `car` VALUES (5, 'che1321', 213, NULL);
INSERT INTO `car` VALUES (6, 'Ceej', 2113, 'dawdada');
INSERT INTO `car` VALUES (7, 'eqda', 1231, 'dawa');
INSERT INTO `car` VALUES (8, 'eqda', 1231, 'dawa');
INSERT INTO `car` VALUES (9, 'eqda', 1231, 'dawa');
INSERT INTO `car` VALUES (10, 'eqda', 1231, 'dawa');
INSERT INTO `car` VALUES (11, 'eqda', 1231, 'dawa');
INSERT INTO `car` VALUES (12, 'eqda', 1231, 'dawa');
INSERT INTO `car` VALUES (13, 'eqda', 1231, 'dawa');
INSERT INTO `car` VALUES (14, 'eqda', 1231, 'dawa');
INSERT INTO `car` VALUES (15, 'eqda', 1231, 'dawa');
INSERT INTO `car` VALUES (16, 'dadaw', 312, 'dadaw');
INSERT INTO `car` VALUES (17, 'dadaw', 312, NULL);
INSERT INTO `car` VALUES (18, '1314dada', 131, 'adawdwada');
INSERT INTO `car` VALUES (19, 'adaw', 911, NULL);
INSERT INTO `car` VALUES (20, '一辆三轮车2号', 121, '还没有实现');

-- ----------------------------
-- Table structure for car_in_order
-- ----------------------------
DROP TABLE IF EXISTS `car_in_order`;
CREATE TABLE `car_in_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) NULL DEFAULT NULL,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NULL DEFAULT NULL,
  `status` int(11) NOT NULL COMMENT '0:未审核；1：未完成；2：已租入；3：审核不通过',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car_in_order
-- ----------------------------
INSERT INTO `car_in_order` VALUES (1, 3, 2, 2, 2);
INSERT INTO `car_in_order` VALUES (5, 16, 3, 4, 2);

-- ----------------------------
-- Table structure for car_out_order
-- ----------------------------
DROP TABLE IF EXISTS `car_out_order`;
CREATE TABLE `car_out_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) NULL DEFAULT NULL,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NULL DEFAULT NULL,
  `status` int(11) NOT NULL COMMENT '0:未审核；1：未租出；2：已租出；3：审核不通过',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car_out_order
-- ----------------------------
INSERT INTO `car_out_order` VALUES (1, 7, 2, 2, 2);
INSERT INTO `car_out_order` VALUES (2, 8, 2, NULL, 3);
INSERT INTO `car_out_order` VALUES (3, 9, 2, NULL, 1);
INSERT INTO `car_out_order` VALUES (4, 10, 2, 5, 2);
INSERT INTO `car_out_order` VALUES (5, 11, 2, 5, 2);
INSERT INTO `car_out_order` VALUES (6, 12, 2, NULL, 1);
INSERT INTO `car_out_order` VALUES (7, 13, 2, NULL, 1);
INSERT INTO `car_out_order` VALUES (8, 14, 2, NULL, 1);
INSERT INTO `car_out_order` VALUES (9, 15, 2, NULL, 3);
INSERT INTO `car_out_order` VALUES (10, 18, 2, NULL, 1);
INSERT INTO `car_out_order` VALUES (12, 20, 2, NULL, 3);

-- ----------------------------
-- Table structure for park
-- ----------------------------
DROP TABLE IF EXISTS `park`;
CREATE TABLE `park`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price_per_day` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL COMMENT '0:未租出；1：已租出',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of park
-- ----------------------------
INSERT INTO `park` VALUES (5, '分啊', 40, 0);
INSERT INTO `park` VALUES (6, '中山公园车位3', 20, 0);
INSERT INTO `park` VALUES (8, 'adaw', 12313, 0);

-- ----------------------------
-- Table structure for park_order
-- ----------------------------
DROP TABLE IF EXISTS `park_order`;
CREATE TABLE `park_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `parkid` int(11) NOT NULL,
  `startdate` date NOT NULL,
  `enddate` date NULL DEFAULT NULL,
  `status` int(11) NOT NULL COMMENT '0:租用1：已租完',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of park_order
-- ----------------------------
INSERT INTO `park_order` VALUES (12, 4, 10, '2017-12-17', '2017-12-17', 1);
INSERT INTO `park_order` VALUES (13, 4, 11, '2017-12-17', '2017-12-17', 1);
INSERT INTO `park_order` VALUES (15, 4, 11, '2017-12-20', '2017-12-21', 1);
INSERT INTO `park_order` VALUES (16, 2, 5, '2017-12-21', '2017-12-21', 1);
INSERT INTO `park_order` VALUES (17, 2, 6, '2017-12-21', '2017-12-21', 1);
INSERT INTO `park_order` VALUES (18, 3, 8, '2017-12-21', '2017-12-21', 1);
INSERT INTO `park_order` VALUES (19, 3, 8, '2017-12-21', '2017-12-21', 1);
INSERT INTO `park_order` VALUES (20, 3, 8, '2017-12-21', '2017-12-21', 1);
INSERT INTO `park_order` VALUES (21, 3, 11, '2017-12-21', '2017-12-21', 1);
INSERT INTO `park_order` VALUES (22, 3, 11, '2017-12-14', '2017-12-21', 1);
INSERT INTO `park_order` VALUES (23, 3, 11, '2017-12-21', '2017-12-21', 1);
INSERT INTO `park_order` VALUES (24, 3, 11, '2017-12-21', '2017-12-21', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `money` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'aas', '123456', '1749597640@qq.com', '楼上是gay1', NULL, 281);
INSERT INTO `user` VALUES (2, 'Ben', '321314', '341131@qq.com', '哈哈哈', NULL, 241);
INSERT INTO `user` VALUES (3, 'Sam', '214141', '1749597640@qq.com', 'ruaruarua', NULL, -20111);
INSERT INTO `user` VALUES (4, 'Dog', 'a21dwa', '690385702@qq.com', '哈哈哈', NULL, 3975);
INSERT INTO `user` VALUES (5, 'daw', '123141', '113131@qq.com', 'dada', NULL, 123);
INSERT INTO `user` VALUES (6, '大大', '99812', '8827@qq.com', '达到啊达到', NULL, 412);

SET FOREIGN_KEY_CHECKS = 1;
