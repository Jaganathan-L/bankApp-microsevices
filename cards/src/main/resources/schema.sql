CREATE TABLE  IF NOT EXISTS `cards`(
    `card_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `mobile_number` VARCHAR(15) NOT NULL,
    `card_number` VARCHAR(100) NOT NULL,
    `card_type` VARCHAR(100) NOT NULL,
    `total_limit`  INT NOT NULL,
    `amount_used` INT NOT NULL,
    `available_amount` INT NOT NULL,
    `created_at` date  NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` VARCHAR(20) DEFAULT NULL
);