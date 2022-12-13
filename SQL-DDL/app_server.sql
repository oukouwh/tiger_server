/*
 Navicat Premium Data Transfer

 Source Server         : jhipster
 Source Server Type    : MySQL
 Source Server Version : 80023 (8.0.23)
 Source Host           : localhost:3306
 Source Schema         : app_server

 Target Server Type    : MySQL
 Target Server Version : 80023 (8.0.23)
 File Encoding         : 65001

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `address_line_1` varchar(255) NOT NULL,
  `address_line_2` varchar(255) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of customer
-- ----------------------------
BEGIN;
INSERT INTO `customer` (`id`, `first_name`, `last_name`, `gender`, `email`, `phone`, `address_line_1`, `address_line_2`, `city`, `country`) VALUES (1, 'Chelsey', 'Wyman', 'OTHER', 'sssendnen@gmail.com', '1-383-473-2680', 'experiences', 'Sahara', 'Kassandrabury', 'Russian Federation');
INSERT INTO `customer` (`id`, `first_name`, `last_name`, `gender`, `email`, `phone`, `address_line_1`, `address_line_2`, `city`, `country`) VALUES (2, 'Lora', 'Baumbach', 'OTHER', 'nJR1@8:.ial', '391.849.2894 x02683', 'Handcrafted', 'Steel', 'O\'Keefeshire', 'Finland');
INSERT INTO `customer` (`id`, `first_name`, `last_name`, `gender`, `email`, `phone`, `address_line_1`, `address_line_2`, `city`, `country`) VALUES (3, 'Raymond', 'Hammes', 'MALE', 'mV@^)hl4K.)k', '(257) 372-6977', 'Lilangeni', 'Chair Directives', 'New Roderick', 'Niger');
INSERT INTO `customer` (`id`, `first_name`, `last_name`, `gender`, `email`, `phone`, `address_line_1`, `address_line_2`, `city`, `country`) VALUES (4, 'Taya', 'Bahringer', 'MALE', 'tMYs@UiL+.[rA(+', '477-281-5154', 'Towels AGP', 'connect', 'Port Darienbury', 'South Georgia and the South Sandwich Islands');
INSERT INTO `customer` (`id`, `first_name`, `last_name`, `gender`, `email`, `phone`, `address_line_1`, `address_line_2`, `city`, `country`) VALUES (5, 'Rey', 'Morissette', 'FEMALE', '(`x@M/F},Y.5\'-II', '1-402-596-0494 x88295', 'Ball up', 'connecting', 'Colthaven', 'Equatorial Guinea');
INSERT INTO `customer` (`id`, `first_name`, `last_name`, `gender`, `email`, `phone`, `address_line_1`, `address_line_2`, `city`, `country`) VALUES (6, 'Susie', 'Pfeffer', 'FEMALE', '#@:i.#', '(829) 608-1947 x6880', 'Future Stand-alone', 'Ramp Cambridgeshire', 'Paterson', 'Norway');
INSERT INTO `customer` (`id`, `first_name`, `last_name`, `gender`, `email`, `phone`, `address_line_1`, `address_line_2`, `city`, `country`) VALUES (7, 'Benton', 'Schulist', 'OTHER', 'Iv^M@vx\'.s-', '951.575.0853 x7031', 'Specialist optical monitor', 'digital Strategist full-range', 'Port Peggiefort', 'Antarctica (the territory South of 60 deg S)');
INSERT INTO `customer` (`id`, `first_name`, `last_name`, `gender`, `email`, `phone`, `address_line_1`, `address_line_2`, `city`, `country`) VALUES (8, 'Cathryn', 'Dooley', 'MALE', 'lWeVM@^d0.pV', '(467) 864-2260', 'program Jordan', 'deposit', 'Janetville', 'Bahrain');
INSERT INTO `customer` (`id`, `first_name`, `last_name`, `gender`, `email`, `phone`, `address_line_1`, `address_line_2`, `city`, `country`) VALUES (9, 'Rose', 'Bashirian', 'FEMALE', 'kOi]5L@<IVj7J.Us', '1-596-645-7305 x9240', 'grey parsing payment', 'Global', 'Lianaland', 'Benin');
INSERT INTO `customer` (`id`, `first_name`, `last_name`, `gender`, `email`, `phone`, `address_line_1`, `address_line_2`, `city`, `country`) VALUES (10, 'Destiny', 'Waelchi', 'OTHER', 'rwozk(@SQ9.X,}9V', '1-579-270-4986 x050', 'Tome', 'Health redundant Grocery', 'New Katrinamouth', 'Falkland Islands (Malvinas)');
COMMIT;

-- ----------------------------
-- Table structure for jhi_authority
-- ----------------------------
DROP TABLE IF EXISTS `jhi_authority`;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of jhi_authority
-- ----------------------------
BEGIN;
INSERT INTO `jhi_authority` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `jhi_authority` (`name`) VALUES ('ROLE_USER');
COMMIT;

-- ----------------------------
-- Table structure for jhi_user
-- ----------------------------
DROP TABLE IF EXISTS `jhi_user`;
CREATE TABLE `jhi_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(191) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of jhi_user
-- ----------------------------
BEGIN;
INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `image_url`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`) VALUES (1, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', '', b'1', 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL);
INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `image_url`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`) VALUES (2, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', b'1', 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL);
COMMIT;

-- ----------------------------
-- Table structure for jhi_user_authority
-- ----------------------------
DROP TABLE IF EXISTS `jhi_user_authority`;
CREATE TABLE `jhi_user_authority` (
  `user_id` bigint NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of jhi_user_authority
-- ----------------------------
BEGIN;
INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`) VALUES (1, 'ROLE_ADMIN');
INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`) VALUES (1, 'ROLE_USER');
INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`) VALUES (2, 'ROLE_USER');
COMMIT;

-- ----------------------------
-- Table structure for quotation
-- ----------------------------
DROP TABLE IF EXISTS `quotation`;
CREATE TABLE `quotation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quotation_no` varchar(255) NOT NULL,
  `quotation_name` varchar(100) NOT NULL,
  `quotation_date` date NOT NULL,
  `work_start` date NOT NULL,
  `work_end` date NOT NULL,
  `delivery_items` varchar(255) NOT NULL,
  `delivery_date` date NOT NULL,
  `acceptance_date` date NOT NULL,
  `payments_terms` varchar(255) NOT NULL,
  `pay_flag` varchar(255) NOT NULL,
  `quotation_expiration_date` date NOT NULL,
  `total_amount` decimal(21,2) NOT NULL,
  `customer_charge` varchar(255) DEFAULT NULL,
  `accuracy` varchar(255) DEFAULT NULL,
  `mail_send_date` date DEFAULT NULL,
  `post_send_date` date DEFAULT NULL,
  `send_flag` varchar(255) DEFAULT NULL,
  `sales_staff` varchar(255) DEFAULT NULL,
  `sales_office` varchar(255) DEFAULT NULL,
  `update_count` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='見積書表\\nquotationNo  見積書番号 String\\nquotationName 見積書名  String\\nquotationDate 見積書日付 LocalDate\\nworkStart 作業开始时间 LocalDate\\nworkEnd 作業終了期間  LocalDate\\ndeliveryItems 納入物件 String\\ndeliveryDate 納入日 LocalDate\\nacceptanceDate 検収予定日 LocalDate\\npaymentsTerms 支払条件 PayMaster\\npayFlag 精算フラグ    PayFlag  Yあり、Nなし\\nquotationExpirationDate 見積有効期限 LocalDate\\ntotalAmount 合計金額  BigDecimal\\n合計金額 = SUM(明細.金額=明細.数量×明細.単価)  ： totalAmount = SUM(standardPrice*count)\\n明細.追加時間単価=明細.単価÷上限時間  ：  additionalPrice = standardPrice/upperLimitHour\\n明細.控除時間単価=明細.単価÷下限時間 ：  deductionPrice = standardPrice/lowerLimitHour\\n↓↓↓↓↓↓↓↓非表示字段，数据库字段可以插入空值↓↓↓↓↓↓↓↓\\ncustomerCharge 顧客担当 String\\naccuracy 受注確度 OrderAccuracy\\nmailSendDate メール送付日 LocalDate\\npostSendDate 見積郵送日 LocalDate\\nsendFlag 送信フラグ SendFlag\\nsalesStaff 営業担当 String\\nsalesOffice 営業事務 String\\nupdateCount 更新回数 Long';

-- ----------------------------
-- Records of quotation
-- ----------------------------
BEGIN;
INSERT INTO `quotation` (`id`, `quotation_no`, `quotation_name`, `quotation_date`, `work_start`, `work_end`, `delivery_items`, `delivery_date`, `acceptance_date`, `payments_terms`, `pay_flag`, `quotation_expiration_date`, `total_amount`, `customer_charge`, `accuracy`, `mail_send_date`, `post_send_date`, `send_flag`, `sales_staff`, `sales_office`, `update_count`) VALUES (1, '001', '見積書名前', '2022-12-11', '2022-12-12', '2022-12-11', 'Market Uzbekistan', '2022-12-11', '2022-12-12', 'C', 'N', '2022-12-11', 944411912.00, 'Savings Team-oriented', 'B', '2022-12-11', '2022-12-11', 'C', 'Usability SMS Books', 'Grocery', 41530);
INSERT INTO `quotation` (`id`, `quotation_no`, `quotation_name`, `quotation_date`, `work_start`, `work_end`, `delivery_items`, `delivery_date`, `acceptance_date`, `payments_terms`, `pay_flag`, `quotation_expiration_date`, `total_amount`, `customer_charge`, `accuracy`, `mail_send_date`, `post_send_date`, `send_flag`, `sales_staff`, `sales_office`, `update_count`) VALUES (2, '002', 'vortals', '2022-12-11', '2022-12-11', '2022-12-11', 'Grocery user-centric Bedfordshire', '2022-12-11', '2022-12-11', 'C', 'Y', '2022-12-12', 709940071.00, 'ADP Avon', 'C', '2022-12-11', '2022-12-11', 'D', 'Response niches Chips', 'Specialist COM client-server', 58612);
INSERT INTO `quotation` (`id`, `quotation_no`, `quotation_name`, `quotation_date`, `work_start`, `work_end`, `delivery_items`, `delivery_date`, `acceptance_date`, `payments_terms`, `pay_flag`, `quotation_expiration_date`, `total_amount`, `customer_charge`, `accuracy`, `mail_send_date`, `post_send_date`, `send_flag`, `sales_staff`, `sales_office`, `update_count`) VALUES (3, '003', 'Dollar', '2022-12-11', '2022-12-11', '2022-12-11', 'high-level', '2022-12-11', '2022-12-11', 'A', 'Y', '2022-12-11', 362854393.00, 'payment', 'C', '2022-12-11', '2022-12-11', 'D', 'Practical proactive Garden', 'Account Extended bluetooth', 24510);
INSERT INTO `quotation` (`id`, `quotation_no`, `quotation_name`, `quotation_date`, `work_start`, `work_end`, `delivery_items`, `delivery_date`, `acceptance_date`, `payments_terms`, `pay_flag`, `quotation_expiration_date`, `total_amount`, `customer_charge`, `accuracy`, `mail_send_date`, `post_send_date`, `send_flag`, `sales_staff`, `sales_office`, `update_count`) VALUES (4, '004', 'hacking', '2022-12-11', '2022-12-11', '2022-12-11', 'standardization', '2022-12-11', '2022-12-11', 'E', 'Y', '2022-12-11', 540436676.00, 'aggregate', 'D', '2022-12-11', '2022-12-11', 'A', 'Internal Licensed', 'Baby bus applications', 57026);
INSERT INTO `quotation` (`id`, `quotation_no`, `quotation_name`, `quotation_date`, `work_start`, `work_end`, `delivery_items`, `delivery_date`, `acceptance_date`, `payments_terms`, `pay_flag`, `quotation_expiration_date`, `total_amount`, `customer_charge`, `accuracy`, `mail_send_date`, `post_send_date`, `send_flag`, `sales_staff`, `sales_office`, `update_count`) VALUES (5, '005', 'deliver', '2022-12-11', '2022-12-11', '2022-12-11', 'Shirt archive Practical', '2022-12-11', '2022-12-11', 'E', 'N', '2022-12-11', 500983151.00, 'Quality-focused Buckinghamshire ivory', 'B', '2022-12-12', '2022-12-11', 'C', 'deposit', 'Graphic backing Dollar', 81282);
INSERT INTO `quotation` (`id`, `quotation_no`, `quotation_name`, `quotation_date`, `work_start`, `work_end`, `delivery_items`, `delivery_date`, `acceptance_date`, `payments_terms`, `pay_flag`, `quotation_expiration_date`, `total_amount`, `customer_charge`, `accuracy`, `mail_send_date`, `post_send_date`, `send_flag`, `sales_staff`, `sales_office`, `update_count`) VALUES (6, '006', 'cross-platform', '2022-12-11', '2022-12-11', '2022-12-12', 'International', '2022-12-11', '2022-12-11', 'D', 'N', '2022-12-11', 717661814.00, 'Implementation interfaces', 'A', '2022-12-11', '2022-12-11', 'A', 'Hampshire hack Executive', 'override', 16103);
INSERT INTO `quotation` (`id`, `quotation_no`, `quotation_name`, `quotation_date`, `work_start`, `work_end`, `delivery_items`, `delivery_date`, `acceptance_date`, `payments_terms`, `pay_flag`, `quotation_expiration_date`, `total_amount`, `customer_charge`, `accuracy`, `mail_send_date`, `post_send_date`, `send_flag`, `sales_staff`, `sales_office`, `update_count`) VALUES (7, '007', 'transform Russian Investor', '2022-12-11', '2022-12-11', '2022-12-11', 'Frozen', '2022-12-11', '2022-12-11', 'A', 'Y', '2022-12-11', 273315189.00, 'Guinea-Bissau Palladium hacking', 'D', '2022-12-11', '2022-12-11', 'A', 'Ball indigo', 'invoice evolve', 67911);
INSERT INTO `quotation` (`id`, `quotation_no`, `quotation_name`, `quotation_date`, `work_start`, `work_end`, `delivery_items`, `delivery_date`, `acceptance_date`, `payments_terms`, `pay_flag`, `quotation_expiration_date`, `total_amount`, `customer_charge`, `accuracy`, `mail_send_date`, `post_send_date`, `send_flag`, `sales_staff`, `sales_office`, `update_count`) VALUES (8, '008', 'TEST', '2022-12-11', '2022-12-11', '2022-12-11', 'bypassing compress', '2022-12-11', '2022-12-11', 'B', 'Y', '2022-12-11', 897575722.00, 'Thailand payment bypass', 'A', '2022-12-11', '2022-12-11', 'B', 'Manat Chicken', 'Kids', 62020);
INSERT INTO `quotation` (`id`, `quotation_no`, `quotation_name`, `quotation_date`, `work_start`, `work_end`, `delivery_items`, `delivery_date`, `acceptance_date`, `payments_terms`, `pay_flag`, `quotation_expiration_date`, `total_amount`, `customer_charge`, `accuracy`, `mail_send_date`, `post_send_date`, `send_flag`, `sales_staff`, `sales_office`, `update_count`) VALUES (9, '009', 'sticky', '2022-12-11', '2022-12-11', '2022-12-11', 'dynamic drive Digitized', '2022-12-11', '2022-12-11', 'A', 'Y', '2022-12-11', 840224800.00, 'ivory mint', 'C', '2022-12-12', '2022-12-11', 'D', 'capacitor Cambridgeshire', 'Rubber grey Grocery', 28402);
COMMIT;

-- ----------------------------
-- Table structure for quotationitem
-- ----------------------------
DROP TABLE IF EXISTS `quotationitem`;
CREATE TABLE `quotationitem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quotation_no` varchar(255) NOT NULL,
  `quotation_item_no` bigint NOT NULL,
  `worker_name` varchar(255) NOT NULL,
  `standard_price` decimal(21,2) NOT NULL,
  `count` bigint NOT NULL,
  `upper_limit_hour` decimal(21,2) NOT NULL,
  `lower_limit_hour` decimal(21,2) NOT NULL,
  `additional_price` decimal(21,2) NOT NULL,
  `deduction_price` decimal(21,2) DEFAULT NULL,
  `memo` varchar(255) NOT NULL,
  `update_count` bigint DEFAULT NULL,
  `quotation_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_quotationitem__quotation_id` (`quotation_id`),
  CONSTRAINT `fk_quotationitem__quotation_id` FOREIGN KEY (`quotation_id`) REFERENCES `quotation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='見積書明細\\nquotationNo  見積書番号 绑定\\nquotationItemNo 見積書明細No\\nworkerName 作業者\\nstandardPrice 月額基準単価\\ncount 数量\\nupperLimitHour 上限時間\\nlowerLimitHour 下限時間\\nadditionalPrice 追加単価\\ndeductionPrice 控除単価\\nmemo 備考\\nupdateCount 更新回数  非表示';

-- ----------------------------
-- Records of quotationitem
-- ----------------------------
BEGIN;
INSERT INTO `quotationitem` (`id`, `quotation_no`, `quotation_item_no`, `worker_name`, `standard_price`, `count`, `upper_limit_hour`, `lower_limit_hour`, `additional_price`, `deduction_price`, `memo`, `update_count`, `quotation_id`) VALUES (1, 'Unbranded azure challenge', 31421, 'Bedfordshire Mobility', 883146210.00, 34829, 246.00, 271.00, 76593206.00, 625604156.00, 'Mills', 56904, 1);
INSERT INTO `quotationitem` (`id`, `quotation_no`, `quotation_item_no`, `worker_name`, `standard_price`, `count`, `upper_limit_hour`, `lower_limit_hour`, `additional_price`, `deduction_price`, `memo`, `update_count`, `quotation_id`) VALUES (2, 'Berkshire frictionless PNG', 18348, 'Usability FTP', 356948284.00, 11104, 299.00, 666.00, 849463313.00, 553395758.00, 'connect', 45245, 2);
INSERT INTO `quotationitem` (`id`, `quotation_no`, `quotation_item_no`, `worker_name`, `standard_price`, `count`, `upper_limit_hour`, `lower_limit_hour`, `additional_price`, `deduction_price`, `memo`, `update_count`, `quotation_id`) VALUES (3, 'markets', 76084, 'port customer', 548821249.00, 86952, 777.00, 737.00, 454320846.00, 20795356.00, 'Buckinghamshire transmitting', 55757, 3);
INSERT INTO `quotationitem` (`id`, `quotation_no`, `quotation_item_no`, `worker_name`, `standard_price`, `count`, `upper_limit_hour`, `lower_limit_hour`, `additional_price`, `deduction_price`, `memo`, `update_count`, `quotation_id`) VALUES (4, 'Intelligent up', 36555, 'Groves sky Product', 832152495.00, 10558, 291.00, 871.00, 33927834.00, 423563471.00, 'turquoise recontextualize Down-sized', 71108, 4);
INSERT INTO `quotationitem` (`id`, `quotation_no`, `quotation_item_no`, `worker_name`, `standard_price`, `count`, `upper_limit_hour`, `lower_limit_hour`, `additional_price`, `deduction_price`, `memo`, `update_count`, `quotation_id`) VALUES (5, 'access Loan hacking', 9772, 'PCI Industrial', 765591186.00, 68688, 56.00, 333.00, 391258801.00, 984855732.00, 'Account Music', 82191, 5);
INSERT INTO `quotationitem` (`id`, `quotation_no`, `quotation_item_no`, `worker_name`, `standard_price`, `count`, `upper_limit_hour`, `lower_limit_hour`, `additional_price`, `deduction_price`, `memo`, `update_count`, `quotation_id`) VALUES (6, 'transmitter drive Table', 20806, 'Fantastic Plastic Lek', 916526978.00, 72406, 295.00, 741.00, 881717618.00, 539650442.00, 'payment', 8497, 6);
INSERT INTO `quotationitem` (`id`, `quotation_no`, `quotation_item_no`, `worker_name`, `standard_price`, `count`, `upper_limit_hour`, `lower_limit_hour`, `additional_price`, `deduction_price`, `memo`, `update_count`, `quotation_id`) VALUES (7, 'Directives embrace', 45574, 'Cheese bypassing Account', 908132500.00, 81196, 986.00, 330.00, 845691497.00, 130362587.00, 'indigo turquoise', 78897, 7);
INSERT INTO `quotationitem` (`id`, `quotation_no`, `quotation_item_no`, `worker_name`, `standard_price`, `count`, `upper_limit_hour`, `lower_limit_hour`, `additional_price`, `deduction_price`, `memo`, `update_count`, `quotation_id`) VALUES (8, 'Analyst salmon', 75011, 'Loan bypass', 468564053.00, 37528, 419.00, 840.00, 890970581.00, 774035032.00, 'Comoros Borders', 61724, 8);
INSERT INTO `quotationitem` (`id`, `quotation_no`, `quotation_item_no`, `worker_name`, `standard_price`, `count`, `upper_limit_hour`, `lower_limit_hour`, `additional_price`, `deduction_price`, `memo`, `update_count`, `quotation_id`) VALUES (9, 'Peso Dynamic', 30781, 'Reduced', 164242517.00, 69837, 423.00, 846.00, 999027492.00, 515757008.00, 'Internal Micronesia', 69437, 9);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
