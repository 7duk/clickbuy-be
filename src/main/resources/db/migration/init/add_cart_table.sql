use sideproject;
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `account_id` int NOT NULL,
                             `item_id` int NOT NULL,
                             `quantity` int NOT NULL,
                             `is_deleted` int NOT NULL DEFAULT (0),
                             `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             `updated_at` datetime,
                             `created_by` int NOT NULL DEFAULT 1,
                             `updated_by` int,
                             PRIMARY KEY (`id`),
                             CONSTRAINT `FK_IDT_CART` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
                             CONSTRAINT `FK_USER_CART` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ;