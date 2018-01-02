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
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `sort_order` int(11) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` varchar(32) NOT NULL,
  `close_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `order_no` bigint(20) DEFAULT NULL,
  `payment` decimal(19,2) DEFAULT NULL,
  `payment_time` datetime DEFAULT NULL,
  `payment_type` int(11) NOT NULL,
  `postage` int(11) NOT NULL,
  `send_time` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `current_price` decimal(19,2) DEFAULT NULL,
  `order_no` bigint(20) DEFAULT NULL,
  `product_id` varchar(32) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_url` varchar(255) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `total_price` decimal(19,2) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
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
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;