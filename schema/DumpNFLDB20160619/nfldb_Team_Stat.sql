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
-- Table structure for table `Team_Stat`
--

DROP TABLE IF EXISTS `Team_Stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Team_Stat` (
  `id` int(11) NOT NULL,
  `won` int(11) DEFAULT NULL,
  `lost` int(11) DEFAULT NULL,
  `tied` int(11) DEFAULT NULL,
  `percent` float DEFAULT NULL,
  `points_forced` int(11) DEFAULT NULL,
  `points_allowed` int(11) DEFAULT NULL,
  `net_pints` int(11) DEFAULT NULL,
  `touchdowns` int(11) DEFAULT NULL,
  `home_record` varchar(5) DEFAULT NULL,
  `road_record` varchar(5) DEFAULT NULL,
  `division_record` varchar(5) DEFAULT NULL,
  `conference_record` varchar(5) DEFAULT NULL,
  `Player_Statscol` varchar(45) DEFAULT NULL,
  `overall_percent` float DEFAULT NULL,
  `streak` varchar(45) DEFAULT NULL,
  `last_five` varchar(5) DEFAULT NULL,
  `Team_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Player_Stats_Team1_idx` (`Team_id`),
  CONSTRAINT `fk_Player_Stats_Team1` FOREIGN KEY (`Team_id`) REFERENCES `Team` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Team_Stat`
--

LOCK TABLES `Team_Stat` WRITE;
/*!40000 ALTER TABLE `Team_Stat` DISABLE KEYS */;
/*!40000 ALTER TABLE `Team_Stat` ENABLE KEYS */;
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
