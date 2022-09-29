CREATE DATABASE `fd_cms` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE USER 'fd_cms'@'%' IDENTIFIED BY 'FuDan!123';
GRANT ALL ON fd_cms.* TO 'fd_cms'@'%';
FLUSH PRIVILEGES;

USE `fd_cms`;

DROP TABLE IF EXISTS `fd_cms`.`cms_user`;
CREATE TABLE `fd_cms`.`cms_user`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `type`        int         NOT NULL DEFAULT 0 COMMENT '用户类型',
    `stu_id`      varchar(32) NOT NULL DEFAULT '' COMMENT '学号',
    `role_id`     int         NOT NULL COMMENT '权限身份',
    `name`        varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
    `telephone`   varchar(32) NOT NULL DEFAULT '' COMMENT '手机',
    `email`       varchar(32) NOT NULL DEFAULT '' COMMENT '邮箱',
    `mentor`      varchar(32) NOT NULL DEFAULT '' COMMENT '导师',
    `leader`      varchar(32) NOT NULL DEFAULT '' COMMENT '汇报人',
    `study_type`  tinyint     NOT NULL DEFAULT '0' COMMENT '就读类型',
    `keshuo`      tinyint     NOT NULL DEFAULT '0' COMMENT '是否科硕',
    `enroll_year` year                 DEFAULT NULL COMMENT '入学年份',
    `enroll_date` date                 DEFAULT NULL COMMENT '入学时间',
    `papers`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '论文',
    `patents`     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '专利',
    `services`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '服务',
    `projects`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '项目',
    `status`      tinyint     NOT NULL DEFAULT '0' COMMENT '状态',
    `deleted`     tinyint     NOT NULL DEFAULT '0' COMMENT '删除状态',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `modify_time` datetime             DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_stu_id` (`stu_id`) USING BTREE,
    KEY `idx_type` (`type`) USING BTREE,
    KEY `idx_role_id` (`role_id`) USING BTREE,
    KEY `idx_name` (`name`) USING BTREE,
    KEY `idx_telephone` (`telephone`) USING BTREE,
    KEY `idx_email` (`email`) USING BTREE,
    KEY `idx_mentor` (`mentor`) USING BTREE,
    KEY `idx_leader` (`leader`) USING BTREE,
    KEY `idx_study_type` (`study_type`) USING BTREE,
    KEY `idx_keshuo` (`keshuo`) USING BTREE,
    KEY `idx_enroll_year` (`enroll_year`) USING BTREE,
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户';

INSERT INTO `fd_cms`.`cms_user`
(`type`, `stu_id`, `name`, `role_id`, `enroll_year`, `enroll_date`, `create_time`)
VALUES ('0', '07175', '杨珉', 10, NULL, NULL, CURRENT_TIMESTAMP),
       ('0', '35004', '杨哲慜', 10, NULL, NULL, CURRENT_TIMESTAMP),
       ('0', '34083', '张源', 10, NULL, NULL, CURRENT_TIMESTAMP),
       ('0', '04278', '朱东来', 10, NULL, NULL, CURRENT_TIMESTAMP),
       ('0', '04795', '张谧', 10, NULL, NULL, CURRENT_TIMESTAMP),
       ('0', '41274', '张磊', 10, NULL, NULL, CURRENT_TIMESTAMP),
       ('0', 'BH6114', '张晓寒', 10, NULL, NULL, CURRENT_TIMESTAMP),
       ('10', '19110240038', '游小钰', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '20110240040', '戴嘉润', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20110240046', '廉轲轲', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20110240048', '谈心', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20110240049', '肖浩宇', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '17302010026', '邓朋', 0, '2017', '2017-09-01', CURRENT_TIMESTAMP),
       ('10', '17212010064', '刘昕伟', 0, '2017', '2017-09-01', CURRENT_TIMESTAMP),
       ('10', '14110240013', '张季博宁', 0, '2014', '2014-09-01', CURRENT_TIMESTAMP),
       ('10', '17212010067', '乔颖', 0, '2017', '2017-09-01', CURRENT_TIMESTAMP),
       ('10', '18210240204', '魏诗萌', 0, '2018', '2018-09-01', CURRENT_TIMESTAMP),
       ('10', '18212010007', '贺瑞', 0, '2018', '2018-09-01', CURRENT_TIMESTAMP),
       ('10', '18212010023', '乔丹', 0, '2018', '2018-09-01', CURRENT_TIMESTAMP),
       ('10', '18212010031', '王寒蕊', 0, '2018', '2018-09-01', CURRENT_TIMESTAMP),
       ('10', '18212010040', '武多才', 0, '2018', '2018-09-01', CURRENT_TIMESTAMP),
       ('10', '18212010044', '许朝智', 0, '2018', '2018-09-01', CURRENT_TIMESTAMP),
       ('10', '18212010069', '邵凯强', 0, '2018', '2018-09-01', CURRENT_TIMESTAMP),
       ('10', '18212010076', '许海龙', 0, '2018', '2018-09-01', CURRENT_TIMESTAMP),
       ('10', '18212010079', '张怀恩', 0, '2018', '2018-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240040', '刘安聪', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240042', '张智搏', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240047', '施游堃', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240049', '杨森', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240051', '汪海沛', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240054', '花楠', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240059', '钟明', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240146', '朱家明', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240158', '杨悉瑜', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240159', '林奕帆', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240180', '王子雯', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19210240213', '邓瑞祺', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '19212010062', '张卓', 0, '2019', '2019-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240039', '刘壮', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240041', '叶瀚', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240046', '曹家俊', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240052', '盛钡娜', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240056', '肖起凡', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240058', '陈晓婷', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240099', '卢苇', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240108', '吕海铭', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240111', '吴子成', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240134', '孙心乾', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240143', '宓晨远', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240152', '张函歌', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240155', '张嘉熙', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240167', '张煜坤', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240176', '徐梓航', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240215', '李驰', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240234', '毛硕', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240238', '汪扬', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240241', '洪文正', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240280', '罗天涵', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240329', '陈彦君', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20210240353', '颜一帆', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20212010021', '杜小林', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20212010046', '龙京奇', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20212010055', '叶浩祺', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20212010088', '梁洁', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20212010115', '袁明豪', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP),
       ('10', '20212010122', '赵钰迪', 0, '2020', '2020-09-01', CURRENT_TIMESTAMP);

DROP TABLE IF EXISTS `fd_cms`.`cms_user_account`;
CREATE TABLE `fd_cms`.`cms_user_account`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `stu_id`      varchar(32) NOT NULL COMMENT '学号',
    `salt`        varchar(32) NOT NULL COMMENT '盐',
    `password`    varchar(64) NOT NULL COMMENT '密码',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_stu_id` (`stu_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户帐户';

INSERT INTO `fd_cms`.`cms_user_account`(`stu_id`, `salt`, `password`, `create_time`)
VALUES ('07175', '07175', MD5('07175123456'), CURRENT_TIMESTAMP),
       ('35004', '35004', MD5('35004123456'), CURRENT_TIMESTAMP),
       ('34083', '34083', MD5('34083123456'), CURRENT_TIMESTAMP),
       ('04278', '04278', MD5('04278123456'), CURRENT_TIMESTAMP),
       ('04795', '04795', MD5('04795123456'), CURRENT_TIMESTAMP),
       ('41274', '41274', MD5('41274123456'), CURRENT_TIMESTAMP),
       ('BH6114', 'BH6114', MD5('BH6114123456'), CURRENT_TIMESTAMP),
       ('19110240038', '19110240038', MD5('19110240038123456'), CURRENT_TIMESTAMP),
       ('20110240040', '20110240040', MD5('20110240040123456'), CURRENT_TIMESTAMP),
       ('20110240046', '20110240046', MD5('20110240046123456'), CURRENT_TIMESTAMP),
       ('20110240048', '20110240048', MD5('20110240048123456'), CURRENT_TIMESTAMP),
       ('20110240049', '20110240049', MD5('20110240049123456'), CURRENT_TIMESTAMP),
       ('17302010026', '17302010026', MD5('17302010026123456'), CURRENT_TIMESTAMP),
       ('17212010064', '17212010064', MD5('17212010064123456'), CURRENT_TIMESTAMP),
       ('14110240013', '14110240013', MD5('14110240013123456'), CURRENT_TIMESTAMP),
       ('17212010067', '17212010067', MD5('17212010067123456'), CURRENT_TIMESTAMP),
       ('18210240204', '18210240204', MD5('18210240204123456'), CURRENT_TIMESTAMP),
       ('18212010007', '18212010007', MD5('18212010007123456'), CURRENT_TIMESTAMP),
       ('18212010023', '18212010023', MD5('18212010023123456'), CURRENT_TIMESTAMP),
       ('18212010031', '18212010031', MD5('18212010031123456'), CURRENT_TIMESTAMP),
       ('18212010040', '18212010040', MD5('18212010040123456'), CURRENT_TIMESTAMP),
       ('18212010044', '18212010044', MD5('18212010044123456'), CURRENT_TIMESTAMP),
       ('18212010069', '18212010069', MD5('18212010069123456'), CURRENT_TIMESTAMP),
       ('18212010076', '18212010076', MD5('18212010076123456'), CURRENT_TIMESTAMP),
       ('18212010079', '18212010079', MD5('18212010079123456'), CURRENT_TIMESTAMP),
       ('19210240040', '19210240040', MD5('19210240040123456'), CURRENT_TIMESTAMP),
       ('19210240042', '19210240042', MD5('19210240042123456'), CURRENT_TIMESTAMP),
       ('19210240047', '19210240047', MD5('19210240047123456'), CURRENT_TIMESTAMP),
       ('19210240049', '19210240049', MD5('19210240049123456'), CURRENT_TIMESTAMP),
       ('19210240051', '19210240051', MD5('19210240051123456'), CURRENT_TIMESTAMP),
       ('19210240054', '19210240054', MD5('19210240054123456'), CURRENT_TIMESTAMP),
       ('19210240059', '19210240059', MD5('19210240059123456'), CURRENT_TIMESTAMP),
       ('19210240146', '19210240146', MD5('19210240146123456'), CURRENT_TIMESTAMP),
       ('19210240158', '19210240158', MD5('19210240158123456'), CURRENT_TIMESTAMP),
       ('19210240159', '19210240159', MD5('19210240159123456'), CURRENT_TIMESTAMP),
       ('19210240180', '19210240180', MD5('19210240180123456'), CURRENT_TIMESTAMP),
       ('19210240213', '19210240213', MD5('19210240213123456'), CURRENT_TIMESTAMP),
       ('19212010062', '19212010062', MD5('19212010062123456'), CURRENT_TIMESTAMP),
       ('20210240039', '20210240039', MD5('20210240039123456'), CURRENT_TIMESTAMP),
       ('20210240041', '20210240041', MD5('20210240041123456'), CURRENT_TIMESTAMP),
       ('20210240046', '20210240046', MD5('20210240046123456'), CURRENT_TIMESTAMP),
       ('20210240052', '20210240052', MD5('20210240052123456'), CURRENT_TIMESTAMP),
       ('20210240056', '20210240056', MD5('20210240056123456'), CURRENT_TIMESTAMP),
       ('20210240058', '20210240058', MD5('20210240058123456'), CURRENT_TIMESTAMP),
       ('20210240099', '20210240099', MD5('20210240099123456'), CURRENT_TIMESTAMP),
       ('20210240108', '20210240108', MD5('20210240108123456'), CURRENT_TIMESTAMP),
       ('20210240111', '20210240111', MD5('20210240111123456'), CURRENT_TIMESTAMP),
       ('20210240134', '20210240134', MD5('20210240134123456'), CURRENT_TIMESTAMP),
       ('20210240143', '20210240143', MD5('20210240143123456'), CURRENT_TIMESTAMP),
       ('20210240152', '20210240152', MD5('20210240152123456'), CURRENT_TIMESTAMP),
       ('20210240155', '20210240155', MD5('20210240155123456'), CURRENT_TIMESTAMP),
       ('20210240167', '20210240167', MD5('20210240167123456'), CURRENT_TIMESTAMP),
       ('20210240176', '20210240176', MD5('20210240176123456'), CURRENT_TIMESTAMP),
       ('20210240215', '20210240215', MD5('20210240215123456'), CURRENT_TIMESTAMP),
       ('20210240234', '20210240234', MD5('20210240234123456'), CURRENT_TIMESTAMP),
       ('20210240238', '20210240238', MD5('20210240238123456'), CURRENT_TIMESTAMP),
       ('20210240241', '20210240241', MD5('20210240241123456'), CURRENT_TIMESTAMP),
       ('20210240280', '20210240280', MD5('20210240280123456'), CURRENT_TIMESTAMP),
       ('20210240329', '20210240329', MD5('20210240329123456'), CURRENT_TIMESTAMP),
       ('20210240353', '20210240353', MD5('20210240353123456'), CURRENT_TIMESTAMP),
       ('20212010021', '20212010021', MD5('20212010021123456'), CURRENT_TIMESTAMP),
       ('20212010046', '20212010046', MD5('20212010046123456'), CURRENT_TIMESTAMP),
       ('20212010055', '20212010055', MD5('20212010055123456'), CURRENT_TIMESTAMP),
       ('20212010088', '20212010088', MD5('20212010088123456'), CURRENT_TIMESTAMP),
       ('20212010115', '20212010115', MD5('20212010115123456'), CURRENT_TIMESTAMP),
       ('20212010122', '20212010122', MD5('20212010122123456'), CURRENT_TIMESTAMP);

DROP TABLE IF EXISTS `fd_cms`.`cms_article_category`;
CREATE TABLE `fd_cms`.`cms_article_category`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `tag`         int          NOT NULL COMMENT '标签',
    `name`        varchar(128) NOT NULL COMMENT '名称',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_tag` (`tag`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='文章分类';

INSERT INTO `fd_cms`.`cms_article_category`(`tag`, `name`, `create_time`)
VALUES ('0', '推荐论文管理', CURRENT_TIMESTAMP),
       ('1', '培养方案管理', CURRENT_TIMESTAMP);

DROP TABLE IF EXISTS `fd_cms`.`cms_article`;
CREATE TABLE `fd_cms`.`cms_article`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `category_id`  bigint       NOT NULL COMMENT '分类id',
    `category_tag` int          NOT NULL COMMENT '标签',
    `title`        varchar(128) NOT NULL COMMENT '名称',
    `content`      longtext     NOT NULL COMMENT '内容',
    `create_time`  datetime     NOT NULL COMMENT '创建时间',
    `modify_time`  datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`) USING BTREE,
    KEY `idx_category_tag` (`category_tag`) USING BTREE,
    KEY `idx_title` (`title`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='文章';


DROP TABLE IF EXISTS `fd_cms`.`cms_bulletin`;
CREATE TABLE `fd_cms`.`cms_bulletin`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`       varchar(128) NOT NULL COMMENT '名称',
    `content`     longtext     NOT NULL COMMENT '内容',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='通知';

DROP TABLE IF EXISTS `fd_cms`.`cms_bulletin_state`;
CREATE TABLE `cms_bulletin_state`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`     bigint   NOT NULL COMMENT '用户id',
    `bulletin_id` bigint   NOT NULL COMMENT '通知id',
    `read`        tinyint  NOT NULL DEFAULT '0' COMMENT '是否已读',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `modify_time` datetime          DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_bulletin_id_user_id` (`bulletin_id`, `user_id`) USING BTREE,
    KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户通知状态';

DROP TABLE IF EXISTS `fd_cms`.`cms_seminar`;
CREATE TABLE `fd_cms`.`cms_seminar`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `speaker_id`  bigint       NOT NULL COMMENT '演讲用户id',
    `date`        date         NOT NULL COMMENT '演讲时间',
    `theme`       varchar(128) NOT NULL COMMENT '演讲主题',
    `link`        varchar(256) NOT NULL COMMENT '演讲资源保存链接地址',
    `description` longtext COMMENT '介绍与描述',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='演讲';

DROP TABLE IF EXISTS `fd_cms`.`cms_recorder`;
CREATE TABLE `fd_cms`.`cms_recorder`
(
    `id`                 bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `date`               date         NOT NULL COMMENT '演讲时间',
    `recorder1_id`       bigint       NOT NULL DEFAULT 0 COMMENT '辅读人员1用户id',
    `recorder1_file`     varchar(128) NULL COMMENT '辅读人员1文件名',
    `recorder1_type`     varchar(128) NULL COMMENT '辅读人员1文件类型',
    `recorder1_content`  longblob COMMENT '辅读人员1记录内容',
    `recorder2_id`       bigint       NOT NULL DEFAULT 0 COMMENT '辅读人员2用户id',
    `recorder2_file`     varchar(128) NULL COMMENT '辅读人员2文件名',
    `recorder2_type`     varchar(128) NULL COMMENT '辅读人员2文件类型',
    `recorder2_content`  longblob COMMENT '辅读人员2记录内容',
    `summarizer_id`      bigint       NOT NULL DEFAULT 0 COMMENT '记录人员用户id',
    `summarizer_file`    varchar(128) NULL COMMENT '记录人员文件名',
    `summarizer_type`    varchar(128) NULL COMMENT '记录人员文件类型',
    `summarizer_content` longblob COMMENT '记录人员记录内容',
    `create_time`        datetime     NOT NULL COMMENT '创建时间',
    `modify_time`        datetime              DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_date` (`date`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='演讲记录安排';

DROP TABLE IF EXISTS `fd_cms`.`cms_device`;
CREATE TABLE `fd_cms`.`cms_device`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `type`           bigint       NOT NULL COMMENT '设备类型',
    `model`          varchar(128) NOT NULL COMMENT '设备型号',
    `name`           varchar(256) NOT NULL COMMENT '设备名称',
    `principal`      varchar(32)  NOT NULL COMMENT '负责人姓名',
    `inventory`      int          NOT NULL COMMENT '库存',
    `inventory_unit` varchar(32)  NOT NULL COMMENT '库存单位',
    `deleted`        tinyint      NOT NULL DEFAULT 0 COMMENT '删除状态',
    `create_time`    datetime     NOT NULL COMMENT '创建时间',
    `modify_time`    datetime              DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='设备';

DROP TABLE IF EXISTS `fd_cms`.`cms_device_allocation`;
CREATE TABLE `fd_cms`.`cms_device_allocation`
(
    `id`              bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`         bigint   NOT NULL COMMENT '用户id',
    `device_id`       bigint   NOT NULL COMMENT '设备id',
    `inventory_usage` int      NOT NULL COMMENT '使用库存',
    `status`          tinyint  NOT NULL DEFAULT 0 COMMENT '状态',
    `create_time`     datetime NOT NULL COMMENT '创建时间',
    `modify_time`     datetime          DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_device_id` (`device_id`) USING BTREE,
    KEY `idx_user_id_create_time` (`user_id`, `create_time`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='设备分配';

DROP TABLE IF EXISTS `fd_cms`.`cms_study_plan`;
CREATE TABLE `cms_study_plan`
(
    `id`             bigint      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `enroll_year`    year        NOT NULL COMMENT '入学年份',
    `reference_date` date        NOT NULL COMMENT '基准日期',
    `name`           varchar(32) NOT NULL COMMENT '名称',
    `version`        bigint      NOT NULL DEFAULT '0' COMMENT '版本',
    `create_time`    datetime    NOT NULL COMMENT '创建时间',
    `modify_time`    datetime             DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_enroll_year` (`enroll_year`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='培养方案';

DROP TABLE IF EXISTS `fd_cms`.`cms_study_plan_stage`;
CREATE TABLE `fd_cms`.`cms_study_plan_stage`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `plan_id`     bigint   NOT NULL COMMENT '培养方案id',
    `term`        int      NOT NULL COMMENT '学期',
    `index`       int      NOT NULL COMMENT '阶段序号',
    `end_date`    date     NOT NULL COMMENT '节点日期',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_plan_id_term_index` (`plan_id`, `term`, `index`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='培养方案阶段';

DROP TABLE IF EXISTS `fd_cms`.`cms_study_plan_work`;
CREATE TABLE `fd_cms`.`cms_study_plan_work`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `plan_id`       bigint       NOT NULL COMMENT '培养方案id',
    `plan_stage_id` bigint       NOT NULL COMMENT '培养方案阶段id',
    `work_type`     int          NOT NULL COMMENT '任务类型',
    `index`         int          NOT NULL COMMENT '任务序号',
    `name`          varchar(256) NOT NULL COMMENT '名称',
    `create_time`   datetime     NOT NULL COMMENT '创建时间',
    `modify_time`   datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `uk_plan_id` (`plan_id`) USING BTREE,
    UNIQUE KEY `uk_item_id_work_type_index` (`plan_stage_id`, `work_type`, `index`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='培养方案任务';

DROP TABLE IF EXISTS `fd_cms`.`cms_study_plan_allocation`;
CREATE TABLE `fd_cms`.`cms_study_plan_allocation`
(
    `id`           bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`      bigint   NOT NULL COMMENT '用户id',
    `plan_id`      bigint   NOT NULL COMMENT '培养方案id',
    `plan_version` bigint   NOT NULL COMMENT '培养方案版本',
    `deleted`      bigint   NOT NULL DEFAULT 0 COMMENT '删除标记',
    `create_time`  datetime NOT NULL COMMENT '创建时间',
    `modify_time`  datetime          DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_id_plan_id_deleted` (`user_id`, `plan_id`, `deleted`) USING BTREE,
    KEY `uk_plan_id` (`plan_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='培养方案分配';

DROP TABLE IF EXISTS `fd_cms`.`cms_study_plan_item`;
CREATE TABLE `fd_cms`.`cms_study_plan_item`
(
    `id`                   bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`              bigint   NOT NULL COMMENT '用户id',
    `plan_id`              bigint   NOT NULL COMMENT '培养方案id',
    `plan_stage_id`        bigint   NOT NULL COMMENT '培养方案阶段id',
    `plan_work_id`         bigint   NOT NULL COMMENT '培养方案任务id',
    `plan_work_start_date` date     NOT NULL COMMENT '培养方案任务开始日期',
    `plan_work_end_date`   date     NOT NULL COMMENT '培养方案任务结束日期',
    `plan_work_delay`      int      NOT NULL DEFAULT 0 COMMENT '培养方案任务延期天数',
    `finished`             tinyint  NOT NULL DEFAULT 0 COMMENT '是否完成',
    `finished_date`        date              DEFAULT NULL COMMENT '任务完成日期',
    `remark`               text COMMENT '备注',
    `deleted`              bigint   NOT NULL DEFAULT 0 COMMENT '删除标记',
    `create_time`          datetime NOT NULL COMMENT '创建时间',
    `modify_time`          datetime          DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_plan_id` (`user_id`, `plan_id`, `deleted`) USING BTREE,
    UNIQUE KEY `uk_uid_pid_deleted_stage_id_work_id` (`plan_id`, `user_id`, `deleted`, `plan_stage_id`, `plan_work_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='培养方案分配的工作项目';