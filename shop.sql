/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : shop

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 26/12/2025 16:14:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for business
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `business_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of business
-- ----------------------------
INSERT INTO `business` VALUES (9, 'aaa', '用户aaa', '15757466408', 'Android权威指南', '计算机学院教材', '99新', '5号楼', 4, '1', '/upload-images/e64944e1-1047-463c-9279-9e35dea13782-Android.png', '10', '2025-12-26 12:16:57');
INSERT INTO `business` VALUES (10, 'aaa', '用户aaa', '15757466408', 'C++程序设计', '计算机学院教材', '99新', '5号楼', 4, '1', '/upload-images/216c3093-4359-40d4-b6fb-ab30f9161104-C++.png', '20', '2025-12-26 12:24:28');
INSERT INTO `business` VALUES (11, 'aaa', '用户aaa', '15757466408', '数据结构', '计算机学院教材', '90新', '5号楼', 4, '2', '/upload-images/c7259515-82cc-4b28-88dc-c203eb19708d-数据结构.png', '12', '2025-12-26 12:28:44');
INSERT INTO `business` VALUES (12, 'aaa', '用户aaa', '15757466408', '网页设计与开发', '计算机学院教材', '99新', '5号楼', 4, '1', '/upload-images/269e19dc-823b-4c5d-869d-ae2d2d8fc0cc-网页设计与开发.png', '12', '2025-12-26 12:30:38');
INSERT INTO `business` VALUES (13, 'bbb', '用户bbb', '15757466409', '保险学', '金融专业教材', '八成新', '4号楼', 5, '1', '/upload-images/93d15003-1d5d-4f54-92b0-b99abe596216-保险学.webp', '13', '2025-12-26 12:32:24');
INSERT INTO `business` VALUES (14, 'bbb', '用户bbb', '15757466409', '投资学', '经济学院教材', '9成新', '4号楼', 5, '1', '/upload-images/ef47e6f8-c4c9-467b-b8fc-93f9b49a8578-投资学.webp', '15', '2025-12-26 12:33:02');
INSERT INTO `business` VALUES (15, 'bbb', '用户bbb', '15757466409', '证券投资技术分析', '经济学院教材', '有笔记 8成新', '4号楼', 5, '1', '/upload-images/ff4feb64-769d-42ef-a139-0132f42e165c-证券投资学.webp', '15', '2025-12-26 12:34:32');
INSERT INTO `business` VALUES (16, 'bbb', '纸崩', '15757466409', '纸崩', '我最爱看的小说', '99新', '4号楼', 7, '2', '/upload-images/28b1eb43-d763-4848-acc0-9bea8b669a46-纸崩.webp', '10', '2025-12-26 12:36:49');

-- ----------------------------
-- Table structure for favorites
-- ----------------------------
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `business_id` int(11) NOT NULL COMMENT '商品ID',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `folder_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '默认收藏夹' COMMENT '收藏夹名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `favorites_id_uindex`(`id`) USING BTREE,
  UNIQUE INDEX `uk_user_business`(`username`, `business_id`) USING BTREE,
  INDEX `idx_username`(`username`) USING BTREE,
  INDEX `idx_business_id`(`business_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of favorites
-- ----------------------------
INSERT INTO `favorites` VALUES (1, 'aaa', 13, '2025-12-26 15:25:37', '默认收藏夹');

-- ----------------------------
-- Table structure for messages
-- ----------------------------
DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送者用户名',
  `receiver_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接收者用户名',
  `business_id` int(11) NULL DEFAULT NULL COMMENT '关联商品ID',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息内容',
  `message_type` int(11) NOT NULL DEFAULT 1 COMMENT '消息类型：1-普通消息，2-系统消息，3-交易消息',
  `is_read` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已读',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `read_at` datetime NULL DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `messages_id_uindex`(`id`) USING BTREE,
  INDEX `idx_sender`(`sender_username`) USING BTREE,
  INDEX `idx_receiver`(`receiver_username`) USING BTREE,
  INDEX `idx_business_id`(`business_id`) USING BTREE,
  INDEX `idx_unread`(`receiver_username`, `is_read`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of messages
-- ----------------------------
INSERT INTO `messages` VALUES (1, 'bbb', 'aaa', 1, '你好，这本书还在吗？', 1, 1, '2024-01-10 10:00:00', '2024-01-10 10:05:00');
INSERT INTO `messages` VALUES (2, 'aaa', 'bbb', 1, '在的，可以当面交易', 1, 1, '2024-01-10 10:02:00', '2024-01-10 10:08:00');
INSERT INTO `messages` VALUES (3, 'admin', 'aaa', NULL, '您的商品已通过审核', 2, 0, '2024-01-10 09:00:00', NULL);
INSERT INTO `messages` VALUES (4, 'aaa', 'bbb', 13, '你好，我想了解一下商品\"保险学\"', 1, 0, '2025-12-26 13:12:47', NULL);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cusername` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `customername` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `customerphone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `customerposition` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `busername` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `business_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '用户aaa', 'aaa', '15757466408', '5号楼', '2025-12-26 15:24:54', 'bbb', 16);
INSERT INTO `orders` VALUES (2, '用户bbb', 'bbb', '15757466409', '4号楼', '2025-12-26 15:26:27', 'aaa', 11);

-- ----------------------------
-- Table structure for reports
-- ----------------------------
DROP TABLE IF EXISTS `reports`;
CREATE TABLE `reports`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reporter_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '举报人用户名',
  `reported_type` int(11) NOT NULL COMMENT '举报类型：1-商品，2-用户，3-消息',
  `reported_id` int(11) NOT NULL COMMENT '被举报对象ID',
  `reported_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被举报用户（如果是举报用户）',
  `reason` int(11) NOT NULL COMMENT '举报原因：1-虚假信息，2-价格欺诈，3-商品不符，4-恶意骚扰，5-其他',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '举报详细描述',
  `evidence_images` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '证据图片，多个用逗号分隔',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '处理状态：1-待处理，2-处理中，3-已处理，4-已驳回',
  `admin_remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '管理员备注',
  `processed_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人',
  `processed_at` datetime NULL DEFAULT NULL COMMENT '处理时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `reports_id_uindex`(`id`) USING BTREE,
  INDEX `idx_reporter`(`reporter_username`) USING BTREE,
  INDEX `idx_reported_type_id`(`reported_type`, `reported_id`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reports
-- ----------------------------
INSERT INTO `reports` VALUES (2, 'aaa', 2, 3, 'ccc', 4, '恶意骚扰，发送不当消息', NULL, 3, '111', '111', '2025-10-10 00:04:58', '2024-01-18 20:15:00');
INSERT INTO `reports` VALUES (4, 'aaa', 1, 7, '用户aaa', 2, '价格欺诈价格欺诈', '', 3, '111', '111', '2025-10-10 16:10:08', '2025-10-10 16:09:54');
INSERT INTO `reports` VALUES (5, 'aaa', 1, 7, '用户aaa', 2, '价格欺诈价格欺诈', '', 4, '11', '11', '2025-10-10 16:23:02', '2025-10-10 16:14:09');
INSERT INTO `reports` VALUES (6, 'aaa', 1, 7, '用户aaa', 2, '价格欺诈价格欺诈', '', 4, '1', '1', '2025-10-10 16:27:33', '2025-10-10 16:27:26');
INSERT INTO `reports` VALUES (7, 'bbb', 1, 1, 'aaa', 5, 'aaa', '', 4, '1', '1', '2025-10-10 16:29:37', '2025-10-10 16:29:17');
INSERT INTO `reports` VALUES (8, 'aaa', 1, 7, '用户aaa', 2, '价格欺诈价格欺诈', '', 3, '1', '1', '2025-10-10 16:31:03', '2025-10-10 16:30:55');
INSERT INTO `reports` VALUES (9, 'aaa', 1, 7, '用户aaa', 2, '价格欺诈价格欺诈', '', 3, '1', '1', '2025-10-10 16:31:29', '2025-10-10 16:31:18');
INSERT INTO `reports` VALUES (14, 'aaa', 1, 3, '用户aaa', 2, '11', '', 4, '11', '11', '2025-10-10 21:39:52', '2025-10-10 18:08:37');
INSERT INTO `reports` VALUES (15, 'aaa', 1, 2, '1', 2, '111', '', 1, NULL, NULL, NULL, '2025-10-10 21:16:17');

-- ----------------------------
-- Table structure for reviews
-- ----------------------------
DROP TABLE IF EXISTS `reviews`;
CREATE TABLE `reviews`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `reviewer_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评价者用户名',
  `reviewed_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '被评价者用户名',
  `business_id` int(11) NOT NULL COMMENT '商品ID',
  `rating` int(11) NOT NULL COMMENT '评分：1-5星',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评价内容',
  `review_type` int(11) NOT NULL COMMENT '评价类型：1-买家评价卖家，2-卖家评价买家',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_anonymous` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否匿名评价',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `reviews_id_uindex`(`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE,
  INDEX `idx_reviewer`(`reviewer_username`) USING BTREE,
  INDEX `idx_reviewed`(`reviewed_username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reviews
-- ----------------------------
INSERT INTO `reviews` VALUES (1, 1, 'bbb', 'aaa', 1, 5, '交易很顺利，商品质量很好，卖家很诚信！', 1, '2024-01-15 14:30:00', 0);
INSERT INTO `reviews` VALUES (2, 1, 'aaa', 'bbb', 1, 5, '买家很爽快，交易愉快！', 2, '2024-01-15 14:35:00', 0);
INSERT INTO `reviews` VALUES (3, 416766, 'aaa', 'default_seller', 5, 5, '111', 1, '2025-10-10 19:10:56', 0);
INSERT INTO `reviews` VALUES (4, 82314, 'aaa', 'default_seller', 5, 3, '2222', 1, '2025-10-10 19:11:03', 1);
INSERT INTO `reviews` VALUES (5, 409559, 'test01', 'default_seller', 3, 3, '商家态度很好', 1, '2025-10-17 15:21:26', 0);
INSERT INTO `reviews` VALUES (6, 662990, 'test01', 'default_seller', 3, 5, '非常nice', 1, '2025-10-17 15:21:49', 1);
INSERT INTO `reviews` VALUES (7, 845637, 'aaa', 'default_seller', 6, 5, '很好', 1, '2025-11-17 00:42:51', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `avatarUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_uindex`(`id`) USING BTREE,
  UNIQUE INDEX `user_username_uindex`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'aaa', '111', '/upload-images/56b26b66-25ed-49ee-8406-7217bd899c36-avatar.jpg', 1, '2024-01-09 21:49:31');
INSERT INTO `user` VALUES (2, 'admin', '123456', '/upload-images/f5937df6-cf4f-421c-a84c-2ba6f314ba79-avatar.jpg', 1, '2024-01-09 21:51:31');
INSERT INTO `user` VALUES (3, 'bbb', '111', '/upload-images/7f0ac850-b741-43b0-8fbb-88bdb62c6179-avatar.jpg', 1, '2024-01-09 21:51:31');

SET FOREIGN_KEY_CHECKS = 1;
