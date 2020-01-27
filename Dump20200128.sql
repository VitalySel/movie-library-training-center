-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: cinemadb
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actor` (
  `idactors` int(11) NOT NULL,
  `country` longtext,
  `date_birth` varchar(25) DEFAULT NULL,
  `name` varchar(25) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idactors`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actors_genres`
--

DROP TABLE IF EXISTS `actors_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actors_genres` (
  `actor_idactors` int(11) NOT NULL,
  `genre_idgenres` int(11) NOT NULL,
  KEY `FKt5xfqxdh51k2sx85n1uekpip0` (`genre_idgenres`),
  KEY `FKbb1uj6wfd3geep6m6uf04rw17` (`actor_idactors`),
  CONSTRAINT `FKbb1uj6wfd3geep6m6uf04rw17` FOREIGN KEY (`actor_idactors`) REFERENCES `actor` (`idactors`),
  CONSTRAINT `FKt5xfqxdh51k2sx85n1uekpip0` FOREIGN KEY (`genre_idgenres`) REFERENCES `genres` (`idgenres`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actors_genres`
--

LOCK TABLES `actors_genres` WRITE;
/*!40000 ALTER TABLE `actors_genres` DISABLE KEYS */;
/*!40000 ALTER TABLE `actors_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genres` (
  `idgenres` int(11) NOT NULL,
  `genre_name` varchar(25) NOT NULL,
  PRIMARY KEY (`idgenres`),
  UNIQUE KEY `UK_11hq5auj6f9viwt8hr86j714d` (`genre_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1),(1),(1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `idmovies` int(11) NOT NULL,
  `budget` varchar(255) DEFAULT NULL,
  `description` longtext,
  `duration` varchar(255) DEFAULT NULL,
  `movie_name` varchar(255) NOT NULL,
  `poster` varchar(255) DEFAULT NULL,
  `release_date` varchar(255) DEFAULT NULL,
  `movies` int(11) NOT NULL,
  PRIMARY KEY (`idmovies`),
  KEY `FKjgh4bv2cqrvy41c7okinjhkwb` (`movies`),
  CONSTRAINT `FKjgh4bv2cqrvy41c7okinjhkwb` FOREIGN KEY (`movies`) REFERENCES `producer` (`idproducers`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_actors`
--

DROP TABLE IF EXISTS `movie_actors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_actors` (
  `movie_idmovies` int(11) NOT NULL,
  `actor_idactors` int(11) NOT NULL,
  PRIMARY KEY (`movie_idmovies`,`actor_idactors`),
  KEY `FKmmvjwtf7h6k996b51vdpxfivt` (`actor_idactors`),
  CONSTRAINT `FKjyofiwy05tbyiquhbk14oi0yh` FOREIGN KEY (`movie_idmovies`) REFERENCES `movie` (`idmovies`),
  CONSTRAINT `FKmmvjwtf7h6k996b51vdpxfivt` FOREIGN KEY (`actor_idactors`) REFERENCES `actor` (`idactors`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_actors`
--

LOCK TABLES `movie_actors` WRITE;
/*!40000 ALTER TABLE `movie_actors` DISABLE KEYS */;
/*!40000 ALTER TABLE `movie_actors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_genres`
--

DROP TABLE IF EXISTS `movie_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_genres` (
  `movie_idmovies` int(11) NOT NULL,
  `genres_idgenres` int(11) NOT NULL,
  KEY `FK5o4gr96w9tl88sjfysohmmcv5` (`genres_idgenres`),
  KEY `FKlads2ho5h5fhffr8lgl59wdht` (`movie_idmovies`),
  CONSTRAINT `FK5o4gr96w9tl88sjfysohmmcv5` FOREIGN KEY (`genres_idgenres`) REFERENCES `genres` (`idgenres`),
  CONSTRAINT `FKlads2ho5h5fhffr8lgl59wdht` FOREIGN KEY (`movie_idmovies`) REFERENCES `movie` (`idmovies`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_genres`
--

LOCK TABLES `movie_genres` WRITE;
/*!40000 ALTER TABLE `movie_genres` DISABLE KEYS */;
/*!40000 ALTER TABLE `movie_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producer`
--

DROP TABLE IF EXISTS `producer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producer` (
  `idproducers` int(11) NOT NULL,
  `producer_country` varchar(255) DEFAULT NULL,
  `producer_date` varchar(255) DEFAULT NULL,
  `producer_name` varchar(255) NOT NULL,
  `producer_photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idproducers`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producer`
--

LOCK TABLES `producer` WRITE;
/*!40000 ALTER TABLE `producer` DISABLE KEYS */;
/*!40000 ALTER TABLE `producer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producers_genres`
--

DROP TABLE IF EXISTS `producers_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producers_genres` (
  `producer_idproducers` int(11) NOT NULL,
  `genres_idgenres` int(11) NOT NULL,
  KEY `FK7475xjlslqma5w3bu7blf0e5l` (`genres_idgenres`),
  KEY `FK2iy5jwvfufpeftq5rcg9vl4w4` (`producer_idproducers`),
  CONSTRAINT `FK2iy5jwvfufpeftq5rcg9vl4w4` FOREIGN KEY (`producer_idproducers`) REFERENCES `producer` (`idproducers`),
  CONSTRAINT `FK7475xjlslqma5w3bu7blf0e5l` FOREIGN KEY (`genres_idgenres`) REFERENCES `genres` (`idgenres`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producers_genres`
--

LOCK TABLES `producers_genres` WRITE;
/*!40000 ALTER TABLE `producers_genres` DISABLE KEYS */;
/*!40000 ALTER TABLE `producers_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usr`
--

DROP TABLE IF EXISTS `usr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usr` (
  `id` bigint(20) NOT NULL,
  `activationCode` varchar(255) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usr`
--

LOCK TABLES `usr` WRITE;
/*!40000 ALTER TABLE `usr` DISABLE KEYS */;
/*!40000 ALTER TABLE `usr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usr_role`
--

DROP TABLE IF EXISTS `usr_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usr_role` (
  `usr_id` bigint(20) NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FK9ffk6ts9njcytrt8ft17fvr3p` (`usr_id`),
  CONSTRAINT `FK9ffk6ts9njcytrt8ft17fvr3p` FOREIGN KEY (`usr_id`) REFERENCES `usr` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usr_role`
--

LOCK TABLES `usr_role` WRITE;
/*!40000 ALTER TABLE `usr_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `usr_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-28  1:18:52
