/*
Navicat Oracle Data Transfer
Oracle Client Version : 11.2.0.1.0

Source Server         : oracle11g
Source Server Version : 110200
Source Host           : :1521
Source Schema         : SCOTT

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2017-10-30 18:52:54
*/


-- ----------------------------
-- Table structure for PETOWNER
-- ----------------------------
DROP TABLE "SCOTT"."PETOWNER";
CREATE TABLE "SCOTT"."PETOWNER" (
"U_ID" NUMBER NOT NULL ,
"U_NAME" VARCHAR2(10 BYTE) NOT NULL ,
"U_PASSWORD" VARCHAR2(10 BYTE) NOT NULL ,
"MONEY" NUMBER NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "SCOTT"."PETOWNER"."U_ID" IS '宠物主人id';
COMMENT ON COLUMN "SCOTT"."PETOWNER"."U_NAME" IS '主人名';
COMMENT ON COLUMN "SCOTT"."PETOWNER"."U_PASSWORD" IS '密码';
COMMENT ON COLUMN "SCOTT"."PETOWNER"."MONEY" IS '主人元宝';

-- ----------------------------
-- Records of PETOWNER
-- ----------------------------
INSERT INTO "SCOTT"."PETOWNER" VALUES ('2', '小明', '123456', '60');
INSERT INTO "SCOTT"."PETOWNER" VALUES ('6', '小王', '123456', '200');
INSERT INTO "SCOTT"."PETOWNER" VALUES ('61', '周', '456789', '900');
INSERT INTO "SCOTT"."PETOWNER" VALUES ('41', '小赵', '123456', '730');
INSERT INTO "SCOTT"."PETOWNER" VALUES ('81', 'zhouweani', '123456', '1000');
INSERT INTO "SCOTT"."PETOWNER" VALUES ('4', '小黄', '123456', '140');
INSERT INTO "SCOTT"."PETOWNER" VALUES ('7', '老王', '123456', '542');
INSERT INTO "SCOTT"."PETOWNER" VALUES ('21', '黄迈', '123456', '1000');

-- ----------------------------
-- Indexes structure for table PETOWNER
-- ----------------------------

-- ----------------------------
-- Uniques structure for table PETOWNER
-- ----------------------------
ALTER TABLE "SCOTT"."PETOWNER" ADD UNIQUE ("U_NAME");

-- ----------------------------
-- Checks structure for table PETOWNER
-- ----------------------------
ALTER TABLE "SCOTT"."PETOWNER" ADD CHECK ("U_ID" IS NOT NULL);
ALTER TABLE "SCOTT"."PETOWNER" ADD CHECK ("U_NAME" IS NOT NULL);
ALTER TABLE "SCOTT"."PETOWNER" ADD CHECK ("U_PASSWORD" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table PETOWNER
-- ----------------------------
ALTER TABLE "SCOTT"."PETOWNER" ADD PRIMARY KEY ("U_ID");
