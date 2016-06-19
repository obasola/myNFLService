-- MySQL dump 10.13  Distrib 5.6.28, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: nfldb
-- ------------------------------------------------------
-- Server version	5.6.28-0ubuntu0.15.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Player_Stat`
--

DROP TABLE IF EXISTS `Player_Stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Player_Stat` (
  `id` int(11) NOT NULL,
  `player_type` varchar(2) DEFAULT NULL,
  `rushing_attempts` int(11) DEFAULT NULL,
  `passing_attempts` int(11) DEFAULT NULL,
  `passing_completions` float DEFAULT NULL,
  `yards_per_carry` float DEFAULT NULL,
  `yards_per_pass` float DEFAULT NULL,
  `yards_per_catch` float DEFAULT NULL,
  `touchdowns` int(11) DEFAULT NULL,
  `nbr_interceptions` int(11) DEFAULT NULL,
  `nbr_tackles` float DEFAULT NULL,
  `nbr_sacks` float DEFAULT NULL,
  `nbr_assists` float DEFAULT NULL,
  `Player_id` int(11) NOT NULL,
  `opponent` varchar(45) DEFAULT NULL,
  `game_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Player_Stat_Player1_idx` (`Player_id`),
  CONSTRAINT `fk_Player_Stat_Player1` FOREIGN KEY (`Player_id`) REFERENCES `Player` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Player_Stat`
--

LOCK TABLES `Player_Stat` WRITE;
/*!40000 ALTER TABLE `Player_Stat` DISABLE KEYS */;
/*!40000 ALTER TABLE `Player_Stat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-19 13:39:09
