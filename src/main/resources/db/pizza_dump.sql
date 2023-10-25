-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: localhost    Database: pizza
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dish_id` int NOT NULL,
  `login_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cart_to_dish_idx` (`dish_id`),
  KEY `fk_cart_to_login_idx` (`login_id`),
  CONSTRAINT `fk_cart_to_dish` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`),
  CONSTRAINT `fk_cart_to_login` FOREIGN KEY (`login_id`) REFERENCES `login` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (4,1,2);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_en` varchar(255) NOT NULL,
  `category_ru` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Pizzas','Пиццы'),(2,'Snacks','Закуски'),(3,'Salads','Салаты'),(4,'Dessert','Десерты'),(5,'Soft drinks','Безалкогольные напитки'),(6,'Alcoholic drinks','Алкогольные напитки');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name_en` varchar(45) NOT NULL,
  `name_ru` varchar(45) NOT NULL,
  `price` decimal(9,2) NOT NULL,
  `category_id` int NOT NULL,
  `time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dish_category_idx` (`category_id`),
  CONSTRAINT `fk_dish_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES (1,'Margherita','Маргарита',10.50,1,'2023-10-10 12:16:16'),(2,'Pepperoni','Пепперони',11.00,1,'2023-10-10 12:16:16'),(3,'Quattro Formaggi','Четыре сыра',12.50,1,'2023-10-10 12:16:16'),(4,'Hawaiian','Гавайская',11.50,1,'2023-10-10 12:16:16'),(5,'Vegetarian','Вегетарианская',10.00,1,'2023-10-10 12:16:16'),(6,'Margarita with vegetables','Маргарита с овощами',11.20,1,'2023-10-13 20:42:49'),(7,'Fried mozzarella sticks','Жареные палочки из моцареллы',6.50,2,'2023-10-10 12:16:16'),(8,'Garlic bread','Чесночный хлеб',5.00,2,'2023-10-10 12:16:16'),(9,'Bruschetta','Брускетта',7.25,2,'2023-10-14 14:24:02'),(10,'Stuffed mushrooms','Фаршированные грибы',8.00,2,'2023-10-10 12:16:16'),(11,'Caprese salad','Салат Капрезе',9.50,3,'2023-10-10 12:16:16'),(12,'Greek salad','Греческий салат',9.00,3,'2023-10-10 12:16:16'),(13,'Caesar salad','Салат Цезарь',8.50,3,'2023-10-10 12:16:16'),(14,'Tiramisu','Тирамису',7.50,4,'2023-10-10 12:16:16'),(15,'Cheesecake','Чизкейк',8.00,4,'2023-10-10 12:16:16'),(16,'Chocolate fondant','Шоколадный фондан',9.00,4,'2023-10-10 12:16:16'),(17,'Coca-Cola','Кока-Кола',2.00,5,'2023-10-10 12:16:16'),(18,'Pepsi','Пепси',2.20,5,'2023-10-15 13:27:04'),(19,'Sprite','Спрайт',2.00,5,'2023-10-10 12:16:16'),(20,'Red Wine','Красное вино',12.50,6,'2023-10-13 22:44:01'),(21,'White wine','Белое вино',11.50,6,'2023-10-10 12:16:16'),(22,'Beer','Пиво',3.50,6,'2023-10-10 12:16:16'),(23,'Ice Creame','Мороженное',5.89,4,'2023-10-13 22:45:51'),(24,'Crabs Salad','Крабовый салат',8.99,3,'2023-10-14 14:25:46');
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(45) NOT NULL,
  `time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (1,'cvz@ukr.net','manager','$2a$10$1Y1PNzXea52hQZNAW0d0FeW6Iadlf7hGWFZLzO.rFgiHuONHF/Xu.','ROLE_MANAGER','2023-10-11 07:31:55'),(2,'user@mail.ru','user','$2a$10$EcEIw7f.Wmiw8lpHbiFTQu76O8yc4SltaXjfBsiNJLGhKT7vhBWKm','ROLE_CUSTOMER','2023-10-11 07:32:52'),(3,'admin@gmail.com','admin','$2a$10$mSqNtzYREM0m8qwbYyl5QOG2Z2vWHxk5NDywuLIEXVKnAGYobKKj.','ROLE_MANAGER','2023-10-11 07:48:33'),(4,'root@root.ru','root','$2a$10$mSqNtzYREM0m8qwbYyl5QOG2Z2vWHxk5NDywuLIEXVKnAGYobKKj.','ROLE_CUSTOMER','2023-10-12 21:24:48'),(5,'alona@ukr.net','alona','$2a$10$q1O1K.mLbATHzkK1192lae4rQb.TtVWu/GHQ.dtPa8dmjK4blFMDi','ROLE_CUSTOMER','2023-10-13 19:19:08'),(6,'larysa@mail.com','larysa','$2a$10$TYw3l8N2V3woWABCAbYB6.rzhhy/DLVCIO03aJlnRU72vpeoVikpG','ROLE_CUSTOMER','2023-10-15 08:46:33'),(7,'vasya@mail.ru','vasya','$2a$10$jpbzDzE8KfJ0rei71XSI5O73d4TKUYUnCvbO9gAOd6h0qfSTSyciO','ROLE_CUSTOMER','2023-10-16 08:51:48');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login_id` bigint NOT NULL,
  `total_price` decimal(9,2) NOT NULL,
  `status` varchar(45) NOT NULL,
  `time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_login_idx` (`login_id`),
  CONSTRAINT `fk_order_login` FOREIGN KEY (`login_id`) REFERENCES `login` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,35.00,'DONE','2023-10-13 11:08:55'),(2,4,34.50,'DONE','2023-10-13 11:31:26'),(3,3,45.50,'DONE','2023-10-13 12:37:57'),(4,3,33.00,'DONE','2023-10-13 17:40:53'),(5,3,26.50,'DONE','2023-10-13 18:12:49'),(6,5,64.00,'DONE','2023-10-13 19:19:40'),(7,3,43.20,'DELIVERY','2023-10-13 20:46:23'),(8,3,38.50,'DELIVERY','2023-10-13 22:47:29'),(9,3,53.20,'PAYMENT_CONFIRM','2023-10-14 09:06:12'),(10,4,59.89,'COOKING','2023-10-14 14:08:32'),(11,4,66.99,'COOKING','2023-10-14 15:01:17'),(12,6,48.99,'DELIVERY','2023-10-15 08:47:56'),(13,3,36.49,'DONE','2023-10-15 12:55:15'),(14,7,67.70,'PAYMENT_CONFIRM','2023-10-16 11:34:16');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-16 22:34:53
