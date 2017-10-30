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

Date: 2017-10-30 18:52:46
*/


-- ----------------------------
-- Table structure for PET
-- ----------------------------
DROP TABLE "SCOTT"."PET";
CREATE TABLE "SCOTT"."PET" (
"P_ID" NUMBER NOT NULL ,
"P_NAME" VARCHAR2(50 BYTE) NOT NULL ,
"TYPENAME" VARCHAR2(20 BYTE) NOT NULL ,
"HEALTH" NUMBER NOT NULL ,
"LOVE" NUMBER NULL ,
"BIRTHDAY" DATE NOT NULL ,
"OWNER_ID" NUMBER NULL ,
"PRICE" NUMBER NOT NULL ,
"STORE_ID" NUMBER NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "SCOTT"."PET"."P_ID" IS '宠物id';
COMMENT ON COLUMN "SCOTT"."PET"."P_NAME" IS '宠物名';
COMMENT ON COLUMN "SCOTT"."PET"."TYPENAME" IS '宠物类型';
COMMENT ON COLUMN "SCOTT"."PET"."HEALTH" IS '健康值';
COMMENT ON COLUMN "SCOTT"."PET"."LOVE" IS '爱心指数';
COMMENT ON COLUMN "SCOTT"."PET"."BIRTHDAY" IS '出生日期';
COMMENT ON COLUMN "SCOTT"."PET"."OWNER_ID" IS '宠物主人id';
COMMENT ON COLUMN "SCOTT"."PET"."PRICE" IS '宠物价格';
COMMENT ON COLUMN "SCOTT"."PET"."STORE_ID" IS '宠物所属商店';

-- ----------------------------
-- Records of PET
-- ----------------------------
INSERT INTO "SCOTT"."PET" VALUES ('3', 'tom', '猫', '100', '0', TO_DATE('2017-08-28 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), null, '500', '501');
INSERT INTO "SCOTT"."PET" VALUES ('4', '旺财', '狗', '90', '15', TO_DATE('2017-08-21 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '4', '1000', null);
INSERT INTO "SCOTT"."PET" VALUES ('5', '旺财1', '猫', '70', '210', TO_DATE('2017-08-30 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '7', '100', null);
INSERT INTO "SCOTT"."PET" VALUES ('6', '旺财2', '猫', '80', '10', TO_DATE('2017-08-30 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '7', '100', null);
INSERT INTO "SCOTT"."PET" VALUES ('41', '哈密呱', '青蛙', '100', '15', TO_DATE('2017-08-30 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '61', '20', null);
INSERT INTO "SCOTT"."PET" VALUES ('21', '汉马', '驴', '70', '25', TO_DATE('2017-08-29 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '41', '150', null);
INSERT INTO "SCOTT"."PET" VALUES ('62', '狸猫', '狗', '100', '0', TO_DATE('2017-08-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), null, '300', '503');
INSERT INTO "SCOTT"."PET" VALUES ('9', '天猫', '马', '100', '0', TO_DATE('2017-08-28 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), null, '500', '502');

-- ----------------------------
-- Indexes structure for table PET
-- ----------------------------

-- ----------------------------
-- Uniques structure for table PET
-- ----------------------------
ALTER TABLE "SCOTT"."PET" ADD UNIQUE ("P_NAME");

-- ----------------------------
-- Checks structure for table PET
-- ----------------------------
ALTER TABLE "SCOTT"."PET" ADD CHECK ("P_ID" IS NOT NULL);
ALTER TABLE "SCOTT"."PET" ADD CHECK ("P_NAME" IS NOT NULL);
ALTER TABLE "SCOTT"."PET" ADD CHECK ("TYPENAME" IS NOT NULL);
ALTER TABLE "SCOTT"."PET" ADD CHECK ("HEALTH" IS NOT NULL);
ALTER TABLE "SCOTT"."PET" ADD CHECK ("BIRTHDAY" IS NOT NULL);
ALTER TABLE "SCOTT"."PET" ADD CHECK ("PRICE" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table PET
-- ----------------------------
ALTER TABLE "SCOTT"."PET" ADD PRIMARY KEY ("P_ID");

-- ----------------------------
-- Foreign Key structure for table "SCOTT"."PET"
-- ----------------------------
ALTER TABLE "SCOTT"."PET" ADD FOREIGN KEY ("OWNER_ID") REFERENCES "SCOTT"."PETOWNER" ("U_ID");
ALTER TABLE "SCOTT"."PET" ADD FOREIGN KEY ("STORE_ID") REFERENCES "SCOTT"."PETSTORE" ("S_ID");
