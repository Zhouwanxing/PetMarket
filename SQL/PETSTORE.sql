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

Date: 2017-10-30 18:53:02
*/


-- ----------------------------
-- Table structure for PETSTORE
-- ----------------------------
DROP TABLE "SCOTT"."PETSTORE";
CREATE TABLE "SCOTT"."PETSTORE" (
"S_ID" NUMBER NOT NULL ,
"S_NAME" VARCHAR2(20 BYTE) NOT NULL ,
"S_PASSWORD" VARCHAR2(20 BYTE) NOT NULL ,
"BALANCE" NUMBER NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "SCOTT"."PETSTORE"."S_ID" IS '宠物商店id';
COMMENT ON COLUMN "SCOTT"."PETSTORE"."S_NAME" IS '宠物商店名字';
COMMENT ON COLUMN "SCOTT"."PETSTORE"."S_PASSWORD" IS '密码';
COMMENT ON COLUMN "SCOTT"."PETSTORE"."BALANCE" IS '宠物商店结余';

-- ----------------------------
-- Records of PETSTORE
-- ----------------------------
INSERT INTO "SCOTT"."PETSTORE" VALUES ('540', '尼玛', '123456', '0');
INSERT INTO "SCOTT"."PETSTORE" VALUES ('501', '哈密哈密', '123456', '738');
INSERT INTO "SCOTT"."PETSTORE" VALUES ('502', '巴拉巴拉', '123456', '150');
INSERT INTO "SCOTT"."PETSTORE" VALUES ('503', '天猫', '123456', '0');
INSERT INTO "SCOTT"."PETSTORE" VALUES ('520', '京东', '123456', '0');

-- ----------------------------
-- Indexes structure for table PETSTORE
-- ----------------------------

-- ----------------------------
-- Uniques structure for table PETSTORE
-- ----------------------------
ALTER TABLE "SCOTT"."PETSTORE" ADD UNIQUE ("S_NAME");

-- ----------------------------
-- Checks structure for table PETSTORE
-- ----------------------------
ALTER TABLE "SCOTT"."PETSTORE" ADD CHECK ("S_ID" IS NOT NULL);
ALTER TABLE "SCOTT"."PETSTORE" ADD CHECK ("S_NAME" IS NOT NULL);
ALTER TABLE "SCOTT"."PETSTORE" ADD CHECK ("S_PASSWORD" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table PETSTORE
-- ----------------------------
ALTER TABLE "SCOTT"."PETSTORE" ADD PRIMARY KEY ("S_ID");
