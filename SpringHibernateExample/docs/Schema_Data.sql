CREATE DATABASE  IF NOT EXISTS `springhibernate` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `springhibernate`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: springhibernate
-- ------------------------------------------------------
-- Server version	5.6.16

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
-- Dumping data for table `access_group`
--

LOCK TABLES `access_group` WRITE;
/*!40000 ALTER TABLE `access_group` DISABLE KEYS */;
REPLACE INTO `access_group` (`GroupId`, `Name`) VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `access_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`UserId`, `Username`, `FirstName`, `LastName`, `HashedPassword`, `RecActive`, `RecUpdatedBy`) VALUES (1,'admin','Admin','Ngo','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,1),(2,'user','User','Ngo','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,1),(5,'mngo','Minh Duc','Ngo','32d3be7783992165f250da0e679e28b49dcdd51718ba6f9fbe1d5639697a0186',1,1),(6,'qanh','Quynh Anh','Nguyen','32d3be7783992165f250da0e679e28b49dcdd51718ba6f9fbe1d5639697a0186',1,5),(7,'dsadas','ads','ads','32d3be7783992165f250da0e679e28b49dcdd51718ba6f9fbe1d5639697a0186',1,5),(9,'quynhanh','Quynh Anh','Nguyen','32d3be7783992165f250da0e679e28b49dcdd51718ba6f9fbe1d5639697a0186',1,5),(10,'usern','The first name','The surname','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,5),(11,'converter','Converter','Faces Converter','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,5),(12,'ert','adas','asd','5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8',1,5),(14,'adsasd','Minh Duc','Ngo','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,5),(15,'dantri','dantri.com.vn','Bao dan tri','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,5),(16,'asdsad','asdsa','','e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855',1,5);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `USER_AINS` AFTER INSERT ON `USER` FOR EACH ROW
BEGIN
	INSERT INTO USER_HISTORY (Field, DataFrom, DataTo, RecCreatedBy, ReferenceId)
	VALUES('USER', NEW.Username, 'ADDED', NEW.RecUpdatedBy, NEW.UserId);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping data for table `user_access_group`
--

LOCK TABLES `user_access_group` WRITE;
/*!40000 ALTER TABLE `user_access_group` DISABLE KEYS */;
REPLACE INTO `user_access_group` (`UserId`, `GroupId`) VALUES (1,1),(5,1),(7,1),(9,1),(11,1),(1,2),(2,2),(6,2),(7,2),(9,2),(10,2),(11,2),(12,2),(14,2),(15,2);
/*!40000 ALTER TABLE `user_access_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_history`
--

LOCK TABLES `user_history` WRITE;
/*!40000 ALTER TABLE `user_history` DISABLE KEYS */;
REPLACE INTO `user_history` (`UserHistoryId`, `Field`, `DataFrom`, `DataTo`, `RecCreated`, `RecCreatedBy`, `ReferenceId`) VALUES (1,'USER','mngo','ADDED','2014-04-11 15:51:51',1,5),(2,'USER','qanh','ADDED','2014-04-11 15:52:44',5,6),(3,'USER','dsadas','ADDED','2014-05-06 14:52:02',5,7),(4,'USER','quynhanh','ADDED','2014-05-06 15:05:32',5,9),(5,'USER','usern','ADDED','2014-05-22 13:41:39',5,10),(6,'USER','converter','ADDED','2014-05-23 13:59:17',5,11),(7,'USER','ert','ADDED','2014-05-23 14:00:54',5,12),(8,'USER','adsasd','ADDED','2014-05-23 14:09:14',5,14),(9,'USER','dantri','ADDED','2014-05-23 14:10:12',5,15),(10,'USER','asdsad','ADDED','2014-05-23 17:00:32',5,16);
/*!40000 ALTER TABLE `user_history` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-26 10:43:39
