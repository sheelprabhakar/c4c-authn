DROP TABLE IF EXISTS `tenants`;
CREATE TABLE `tenants` (
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


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` VARCHAR(36) NOT NULL,
  `tenant_id` varchar(36) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `middle_name` VARCHAR(50) NULL DEFAULT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `user_name` VARCHAR(255) NOT NULL,
  `mobile` VARCHAR(15) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password_hash` VARCHAR(64) NULL DEFAULT NULL,
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
  UNIQUE INDEX `uq_tenant_username` (`tenant_id`, `user_name` ASC),
  UNIQUE INDEX `uq_tenant_mobile` (`tenant_id`, `mobile` ASC),
  UNIQUE INDEX `uq_tenant_email` (`tenant_id`, `email` ASC) ,
  CONSTRAINT `fk_tenant_user_tenant_id`
                 FOREIGN KEY (`tenant_id`)
                     REFERENCES `tenants` (`id`)
                     ON DELETE CASCADE
                     ON UPDATE NO ACTION);

DROP TABLE IF EXISTS `clients`;
CREATE TABLE `clients` (
  `id` VARCHAR(36) NOT NULL,
  `tenant_id` varchar(36) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `client_id` VARCHAR(255) NOT NULL,
  `client_secret` VARCHAR(1024) NOT NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `created_by` VARCHAR(255) NULL,
  `updated_by` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_tenants_clients_name` (`tenant_id`, `name` ASC),
  UNIQUE INDEX `uq_clients_client_id` (`client_id` ASC),
  CONSTRAINT `fk_tenant_clients_tenant_id`
                 FOREIGN KEY (`tenant_id`)
                     REFERENCES `tenants` (`id`)
                     ON DELETE CASCADE
                     ON UPDATE NO ACTION);

DROP TABLE IF EXISTS `roles`;
  CREATE TABLE `roles` (
    `id` VARCHAR(36) NOT NULL,
    `tenant_id` varchar(36) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `created_at` DATETIME NULL,
    `updated_at` DATETIME NULL,
    `created_by` VARCHAR(255) NULL,
    `updated_by` VARCHAR(255) NULL,
     PRIMARY KEY (`id`),
     UNIQUE INDEX `uq_tenants_role_name` (`tenant_id`, `name` ASC),
     CONSTRAINT `fk_tenant_role_tenant_id`
                     FOREIGN KEY (`tenant_id`)
                     REFERENCES `tenants` (`id`)
                     ON DELETE CASCADE
                     ON UPDATE NO ACTION);

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_id` VARCHAR(36) NOT NULL,
  `role_id` VARCHAR(36) NOT NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `created_by` VARCHAR(255) NULL,
  `updated_by` VARCHAR(255) NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_user_user_role_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_role_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `roles` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_user_role`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

DROP TABLE IF EXISTS `client_roles`;
CREATE TABLE `client_roles` (
  `client_id` VARCHAR(36) NOT NULL,
  `role_id` VARCHAR(36) NOT NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `created_by` VARCHAR(255) NULL,
  `updated_by` VARCHAR(255) NULL,
  PRIMARY KEY (`client_id`, `role_id`),
  INDEX `fk_client_client_role_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_role_client_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `roles` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_client_client_role`
    FOREIGN KEY (`client_id`)
    REFERENCES `clients` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

DROP TABLE IF EXISTS `oauth_tokens`;
CREATE TABLE `oauth_tokens` (
  `id` VARCHAR(36) NOT NULL,
  `user_id` VARCHAR(36) NULL,
  `client_id` VARCHAR(36) NULL,
  `tenant_id` varchar(36) NOT NULL,
  `access_token` VARCHAR(4096) NOT NULL,
  `refresh_token` VARCHAR(4096) NULL,
  `expiry_time` DATETIME NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_oauth_token_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_oauth_token_client_id`
     FOREIGN KEY (`client_id`)
     REFERENCES `clients` (`id`)
     ON DELETE CASCADE
     ON UPDATE CASCADE,
  CONSTRAINT `fk_tenant_oauth_token_tenant_id`
           FOREIGN KEY (`tenant_id`)
           REFERENCES `tenants` (`id`)
           ON DELETE CASCADE
           ON UPDATE NO ACTION);

INSERT INTO `tenants` (`id`, `short_name`, `name`, `email`, `phone`, `mobile`, `address`, `area`, `pin`, `city_id`, `active`, `created_by`) VALUES ('fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'SYSTEM', 'System Tenant', 'system@c4c.com', '9899098990', '9899098990', 'cloud', 'cloud', '201301', '1', '1', 'SYSTEM');

INSERT INTO `roles` (`id`, `tenant_id`, `name`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'SUPER_ADMIN', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `roles` (`id`, `tenant_id`, `name`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'ADMIN', '0', 'SYSTEM', 'SYSTEM',NOW(), NOW());
INSERT INTO `roles` (`id`,`tenant_id`, `name`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('e78fd037-8b92-47a0-a7f1-d2e16cf31738', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'USER', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `roles` (`id`,`tenant_id`, `name`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('2bf6c5a3-4be5-49f2-874c-a7e719ab2399', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'CLIENT_USER', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());


INSERT INTO `users` (`id`,`tenant_id`, `first_name`, `middle_name`, `last_name`, `user_name`, `mobile`, `email`, `password_hash`, `is_locked`, `is_deleted`, `created_by`, `updated_by`) VALUES ('bc5a1ff0-cab9-44f6-98f6-fe988e1c0afc', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'admin', 'a', 'User', 'admin@c4c.com', '9899098990', 'admin@c4c.com', '$2a$12$NL54bmIzc2qe9BgHFMCVleKQ/mUYvq7Bv7jIUODO3.jCshcUs0l0q', '0', '0', 'SYSTEM', 'SYSTEM');
INSERT INTO `clients` (`id`, `tenant_id`, `name`, `client_id`, `client_secret`, `is_deleted`, `created_at`, `updated_at`, `created_by`, `updated_by`)
VALUES ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'Client A', 'bsmdRGn_n6smbClUCwXjzQOkw7iqyalAicsmzoIX5MU', 'XQTseITIpfBhtR9l54ztHY/huzfLW9pVakc7jq1KhydYi785y0wioCjmpiHH6sVDcMQTMMlIqe3HWkKII/VAjzdQwB+t0AxoBH/Uwo1lVEtt5BXltIbn5n+XKSSihDfcIraDhnzbWeAWQ+8urOuKBUt5mEPCGNiJ1DSvJpETZN3ogA==', 0, NOW(), NOW(), 'SYSTEM', 'SYSTEM');

INSERT INTO `user_roles` (`role_id`, `user_id`, `is_deleted`, `created_by`, `updated_by`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', 'bc5a1ff0-cab9-44f6-98f6-fe988e1c0afc', '0', 'SYSTEM', 'SYSTEM');
INSERT INTO `user_roles` (`role_id`, `user_id`, `is_deleted`, `created_by`, `updated_by`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', 'bc5a1ff0-cab9-44f6-98f6-fe988e1c0afc', '0', 'SYSTEM', 'SYSTEM');

DROP TABLE IF EXISTS `rest_acls`;
  CREATE TABLE `rest_acls` (
    `id` VARCHAR(36) NOT NULL,
    `tenant_id` varchar(36) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `path` VARCHAR(1024) NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `created_at` DATETIME NULL,
    `updated_at` DATETIME NULL,
    `created_by` VARCHAR(255) NULL,
    `updated_by` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_tenant_rest_acls_tenant_id`
                 FOREIGN KEY (`tenant_id`)
                 REFERENCES `tenants` (`id`)
                 ON DELETE CASCADE
                 ON UPDATE NO ACTION);

INSERT INTO `rest_acls` (`id`, `tenant_id`, `name`, `path`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('bfa5a7d2-07df-4687-9f83-b23956492c6f', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'Tenant Management', '/v1/api/tenant/**', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `rest_acls` (`id`, `tenant_id`, `name`, `path`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('8f5a8cd7-78b7-4b64-be48-7b608eaed419', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'User Management', '/v1/api/user/**', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `rest_acls` (`id`, `tenant_id`, `name`, `path`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('8b033b7f-0dd9-4832-9b4f-d1490bd34b61', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'Role Management', '/v1/api/role/**', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `rest_acls` (`id`, `tenant_id`, `name`, `path`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('055300f8-0879-420f-b21b-6fd7983d47f3', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'Lookup Management', '/v1/api/lookup/**', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `rest_acls` (`id`, `tenant_id`, `name`, `path`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('70b7fb87-815c-4ee2-b336-ba7746e8380f', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'Role Rest ACL', '/v1/api/role/restAcl/**', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `rest_acls` (`id`, `tenant_id`, `name`, `path`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('67aa293b-ae31-412b-a73c-c11902c9c51a', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'Rest ACL Management', '/v1/api/restAcl/**', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `rest_acls` (`id`, `tenant_id`, `name`, `path`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('c6872643-e248-4005-89d9-c74a821c8cfe', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'User Role', '/v1/api/user/role/**', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `rest_acls` (`id`, `tenant_id`, `name`, `path`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('72b4fea9-0cb9-4aec-a120-222a99261de0', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'Dashboard', '/v1/api/dashboard/**', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `rest_acls` (`id`, `tenant_id`, `name`, `path`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('91e34f74-8d70-491e-87e4-a0faed3d4ad8', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'Client Management', '/v1/api/client/**', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `rest_acls` (`id`, `tenant_id`, `name`, `path`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('642efdd3-ac33-4cf9-a187-f43c71a27714', 'fe9f8f3c-6447-4fb1-a9ba-6856bccd3d9b', 'Policy Read', '/v1/api/**/policy/**', '0', 'SYSTEM', 'SYSTEM', NOW(), NOW());

DROP TABLE IF EXISTS `role_rest_acls`;
CREATE TABLE `role_rest_acls` (
  `role_id` VARCHAR(36) NOT NULL,
  `rest_acl_id` VARCHAR(36) NOT NULL,
  `can_create` TINYINT NULL DEFAULT 0,
  `can_read` TINYINT NULL DEFAULT 0,
  `can_update` TINYINT NULL DEFAULT 0,
  `can_delete` TINYINT NULL DEFAULT 0,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `created_by` VARCHAR(255) NULL,
  `updated_by` VARCHAR(255) NULL,
  PRIMARY KEY (`role_id`, `rest_acl_id`),
  INDEX `fk_rest_acls_role_rest_acl_id` (`rest_acl_id` ASC) VISIBLE,
  CONSTRAINT `fk_rest_acls_role_rest_acls`
    FOREIGN KEY (`role_id`)
    REFERENCES `roles` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_role_role_rest_acls`
    FOREIGN KEY (`rest_acl_id`)
    REFERENCES `rest_acls` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', 'bfa5a7d2-07df-4687-9f83-b23956492c6f', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', '8f5a8cd7-78b7-4b64-be48-7b608eaed419', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', '8b033b7f-0dd9-4832-9b4f-d1490bd34b61', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', '055300f8-0879-420f-b21b-6fd7983d47f3', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', '70b7fb87-815c-4ee2-b336-ba7746e8380f', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', '67aa293b-ae31-412b-a73c-c11902c9c51a', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', 'c6872643-e248-4005-89d9-c74a821c8cfe', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', '72b4fea9-0cb9-4aec-a120-222a99261de0', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', '91e34f74-8d70-491e-87e4-a0faed3d4ad8', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('52a05765-a8e0-4fd7-b95b-3b14b52634f5', '642efdd3-ac33-4cf9-a187-f43c71a27714', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());

INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', 'bfa5a7d2-07df-4687-9f83-b23956492c6f', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', '8f5a8cd7-78b7-4b64-be48-7b608eaed419', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', '8b033b7f-0dd9-4832-9b4f-d1490bd34b61', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', '055300f8-0879-420f-b21b-6fd7983d47f3', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', '70b7fb87-815c-4ee2-b336-ba7746e8380f', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', '67aa293b-ae31-412b-a73c-c11902c9c51a', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', 'c6872643-e248-4005-89d9-c74a821c8cfe', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', '72b4fea9-0cb9-4aec-a120-222a99261de0', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', '91e34f74-8d70-491e-87e4-a0faed3d4ad8', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('02ec9264-bdf8-4c56-971c-d4ab699e24e6', '642efdd3-ac33-4cf9-a187-f43c71a27714', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());

INSERT INTO `role_rest_acls` (`role_id`, `rest_acl_id`, `can_create`, `can_read`, `can_update`, `can_delete`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES ('2bf6c5a3-4be5-49f2-874c-a7e719ab2399', '642efdd3-ac33-4cf9-a187-f43c71a27714', '1', '1', '1', '1', '1', 'SYSTEM', 'SYSTEM', NOW(), NOW());
