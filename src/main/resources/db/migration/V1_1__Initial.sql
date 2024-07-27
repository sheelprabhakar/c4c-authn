DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant` (
  `id` VARCHAR(36) NOT NULL,
  `short_name` VARCHAR(45) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `mobile` VARCHAR(15) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `area` VARCHAR(255) NULL,
  `pin` VARCHAR(7) NULL,
  `landmark` VARCHAR(45) NULL,
  `city_id` MEDIUMINT UNSIGNED NOT NULL,
  `picture_url` VARCHAR(2048) NULL,
  `latitude` DECIMAL(10,8) NULL,
  `longitude` DECIMAL(10,8) NULL,
  `active` TINYINT NOT NULL DEFAULT 1,
   `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `created_at` TIMESTAMP NULL,
  `updated_at` TIMESTAMP NULL,
  `created_by` VARCHAR(255) NULL,
  `updated_by` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `short_name_UNIQUE` (`short_name` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE,
  CONSTRAINT `tenant_fk_citi_id`
      FOREIGN KEY (`city_id`)
          REFERENCES `cities` (`id`)
          ON DELETE NO ACTION
          ON UPDATE NO ACTION);


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` VARCHAR(36) NOT NULL,
  `tenant_id` varchar(36) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `middle_name` VARCHAR(50) NULL DEFAULT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `user_name` VARCHAR(50) NOT NULL,
  `mobile` VARCHAR(15) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password_hash` VARCHAR(64) NOT NULL,
  `otp` VARCHAR(64) NULL DEFAULT NULL,
  `otp_at` DATETIME NULL DEFAULT NULL,
  `last_login` DATETIME NULL DEFAULT NULL,
  `intro` TINYTEXT NULL DEFAULT NULL,
  `profile` TEXT NULL DEFAULT NULL,
  `is_locked` TINYINT NOT NULL DEFAULT 0,

  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `created_by` VARCHAR(255) NULL,
  `updated_by` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_username` (`user_name` ASC),
  UNIQUE INDEX `uq_mobile` (`mobile` ASC),
  UNIQUE INDEX `uq_email` (`email` ASC) ,
  CONSTRAINT `fk_tenant_user_tenant_id`
                 FOREIGN KEY (`tenant_id`)
                     REFERENCES `tenant` (`id`)
                     ON DELETE CASCADE
                     ON UPDATE NO ACTION);

DROP TABLE IF EXISTS `role`;
  CREATE TABLE `role` (
    `id` VARCHAR(36) NOT NULL,
    `tenant_id` varchar(36) NOT NULL,
    `name` VARCHAR(50) NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `created_at` DATETIME NULL,
    `updated_at` DATETIME NULL,
    `created_by` VARCHAR(255) NULL,
    `updated_by` VARCHAR(255) NULL,
     PRIMARY KEY (`id`),
     CONSTRAINT `fk_tenant_role_tenant_id`
                     FOREIGN KEY (`tenant_id`)
                     REFERENCES `tenant` (`id`)
                     ON DELETE CASCADE
                     ON UPDATE NO ACTION);

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `role_id` VARCHAR(36) NOT NULL,
  `user_id` VARCHAR(36) NOT NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `created_by` VARCHAR(255) NULL,
  `updated_by` VARCHAR(255) NULL,
  PRIMARY KEY (`role_id`, `user_id`),
  INDEX `fk_user_user_role_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_role_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_user_role`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

DROP TABLE IF EXISTS `user_token`;
CREATE TABLE `user_token` (
  `user_id` VARCHAR(36) NOT NULL,
  `tenant_id` varchar(36) NOT NULL,
  `access_token` VARCHAR(4096) NOT NULL,
  `refresh_token` VARCHAR(4096) NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_token_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tenant_user_token_tenant_id`
           FOREIGN KEY (`tenant_id`)
           REFERENCES `tenant` (`id`)
           ON DELETE CASCADE
           ON UPDATE NO ACTION);

INSERT INTO `tenant` (`id`, `short_name`, `name`, `email`, `phone`, `mobile`, `address`, `area`, `pin`, `city_id`, `active`, `created_by`) VALUES ('fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'SYSTEM', 'System Tenant', 'system@c4c.com', '9899098990', '9899098990', 'cloud', 'cloud', '201301', '1', '1', 'SYSTEM');

INSERT INTO `role` (`id`, `tenant_id`, `name`, `is_deleted`, `created_by`, `updated_by`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'SUPER_ADMIN', '0', 'SYSTEM', 'SYSTEM');
INSERT INTO `role` (`id`, `tenant_id`, `name`, `is_deleted`, `created_by`, `updated_by`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'ADMIN', '0', 'SYSTEM', 'SYSTEM');
INSERT INTO `role` (`id`,`tenant_id`, `name`, `is_deleted`, `created_by`, `updated_by`) VALUES ('e78fd037-8b92-47a0-a7f1-d2e16cf31738', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'USER', '0', 'SYSTEM', 'SYSTEM');

INSERT INTO `user` (`id`,`tenant_id`, `first_name`, `middle_name`, `last_name`, `user_name`, `mobile`, `email`, `password_hash`, `is_locked`, `is_deleted`, `created_by`, `updated_by`) VALUES ('bc5a1ff0-cab9-44f6-98f6-fe988e1c0afc', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'admin', 'a', 'User', 'admin', '9899098990', 'sheel.prabhakar@gmail.com', '$2a$12$NL54bmIzc2qe9BgHFMCVleKQ/mUYvq7Bv7jIUODO3.jCshcUs0l0q', '0', '0', 'SYSTEM', 'SYSTEM');

INSERT INTO `user_role` (`role_id`, `user_id`, `is_deleted`, `created_by`, `updated_by`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', 'bc5a1ff0-cab9-44f6-98f6-fe988e1c0afc', '0', 'SYSTEM', 'SYSTEM');
INSERT INTO `user_role` (`role_id`, `user_id`, `is_deleted`, `created_by`, `updated_by`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', 'bc5a1ff0-cab9-44f6-98f6-fe988e1c0afc', '0', 'SYSTEM', 'SYSTEM');

DROP TABLE IF EXISTS `attribute_resource`;
  CREATE TABLE `attribute_resource` (
    `id` VARCHAR(36) NOT NULL,
    `tenant_id` varchar(36) NOT NULL,
    `attribute_name` VARCHAR(50) NOT NULL,
    `path` VARCHAR(4096) NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `created_at` DATETIME NULL,
    `updated_at` DATETIME NULL,
    `created_by` VARCHAR(255) NULL,
    `updated_by` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_tenant_attribute_resource_tenant_id`
                 FOREIGN KEY (`tenant_id`)
                 REFERENCES `tenant` (`id`)
                 ON DELETE CASCADE
                 ON UPDATE NO ACTION);