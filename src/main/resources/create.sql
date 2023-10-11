CREATE TABLE `category` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `category_en` varchar(255) NOT NULL,
                            `category_ru` varchar(255) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `dish` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `name_en` varchar(45) NOT NULL,
                        `name_ru` varchar(45) NOT NULL,
                        `price` decimal(9,2) NOT NULL,
                        `category_id` bigint NOT NULL,
                        `time` timestamp NOT NULL,
                        PRIMARY KEY (`id`),
                        KEY `fk_dish_category_idx` (`category_id`),
                        CONSTRAINT `fk_dish_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `login` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `email` varchar(255) NOT NULL,
                         `login` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `role` varchar(45) NOT NULL,
                         `time` timestamp NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `orders` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `login_id` bigint NOT NULL,
                          `total_price` decimal(9,2) NOT NULL,
                          `status` varchar(45) NOT NULL,
                          `time` timestamp NOT NULL,
                          PRIMARY KEY (`id`),
                          KEY `fk_order_login_idx` (`login_id`),
                          CONSTRAINT `fk_order_login` FOREIGN KEY (`login_id`) REFERENCES `login` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `cart` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `dish_id` bigint NOT NULL,
                        `login_id` bigint NOT NULL,
                        PRIMARY KEY (`id`),
                        KEY `fk_cart_to_dish_idx` (`dish_id`),
                        KEY `fk_cart_to_login_idx` (`login_id`),
                        CONSTRAINT `fk_cart_to_dish` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`),
                        CONSTRAINT `fk_cart_to_login` FOREIGN KEY (`login_id`) REFERENCES `login` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8