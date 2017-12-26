/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50555
Source Host           : localhost:3306
Source Database       : store

Target Server Type    : MYSQL
Target Server Version : 50555
File Encoding         : 65001

Date: 2017-12-26 15:35:55
*/

SET FOREIGN_KEY_CHECKS=0;


CREATE DATABASE IF NOT EXISTS `store`  DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE store;

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `current_price` decimal(19,2) DEFAULT NULL,
  `order_no` bigint(20) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_url` varchar(255) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `total_price` decimal(19,2) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `version_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_item
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` varchar(32) NOT NULL,
  `category_id` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `main_image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `sub_images` varchar(255) DEFAULT NULL,
  `subtitle` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `role` int(11) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `version_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
