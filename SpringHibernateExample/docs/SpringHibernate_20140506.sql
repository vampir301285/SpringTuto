/*
SQLyog Community v9.60 Beta2
MySQL - 5.6.16 : Database - springhibernate
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`springhibernate` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `springhibernate`;

/*Table structure for table `access_group` */

DROP TABLE IF EXISTS `access_group`;

CREATE TABLE `access_group` (
  `GroupId` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  PRIMARY KEY (`GroupId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `access_group` */

insert  into `access_group`(`GroupId`,`Name`) values (1,'ADMIN'),(2,'USER');

/*Table structure for table `activity` */

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `ActivityId` int(11) NOT NULL AUTO_INCREMENT,
  `StartTime` datetime DEFAULT NULL,
  `EndTime` datetime DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `UserId` int(11) NOT NULL,
  PRIMARY KEY (`ActivityId`),
  KEY `fk_Activity_User` (`UserId`),
  CONSTRAINT `fk_Activity_User` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `activity` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `UserId` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `HashedPassword` varchar(255) DEFAULT NULL,
  `RecActive` tinyint(1) NOT NULL DEFAULT '1',
  `RecUpdatedBy` int(11) DEFAULT '1',
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `USER_USERNAME_UNIQUE` (`Username`),
  KEY `fk_USER_USER1` (`RecUpdatedBy`),
  CONSTRAINT `fk_USER_USER1` FOREIGN KEY (`RecUpdatedBy`) REFERENCES `user` (`UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`UserId`,`Username`,`FirstName`,`LastName`,`HashedPassword`,`RecActive`,`RecUpdatedBy`) values (1,'admin','Admin','Ngo','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,1),(2,'user','User','Ngo','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,1),(5,'mngo','Minh Duc','Ngo','32d3be7783992165f250da0e679e28b49dcdd51718ba6f9fbe1d5639697a0186',1,1),(6,'qanh','Quynh Anh','Nguyen','32d3be7783992165f250da0e679e28b49dcdd51718ba6f9fbe1d5639697a0186',1,5),(7,'dsadas','ads','ads','32d3be7783992165f250da0e679e28b49dcdd51718ba6f9fbe1d5639697a0186',1,5),(9,'quynhanh','Quynh Anh','Nguyen','32d3be7783992165f250da0e679e28b49dcdd51718ba6f9fbe1d5639697a0186',1,5),(10,'usern','The first name','The surname','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,5),(11,'converter','Converter','Faces Converter','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,5),(12,'ert','adas','asd','5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8',1,5),(14,'adsasd','Minh Duc','Ngo','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,5),(15,'dantri','dantri.com.vn','Bao dan tri','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1,5),(16,'asdsad','asdsa','','e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855',1,5);

/*Table structure for table `user_access_group` */

DROP TABLE IF EXISTS `user_access_group`;

CREATE TABLE `user_access_group` (
  `UserId` int(11) NOT NULL,
  `GroupId` int(11) NOT NULL,
  PRIMARY KEY (`UserId`,`GroupId`),
  KEY `fk_USER_has_ACCESS_GROUP_ACCESS_GROUP1` (`GroupId`),
  CONSTRAINT `fk_USER_has_ACCESS_GROUP_ACCESS_GROUP1` FOREIGN KEY (`GroupId`) REFERENCES `access_group` (`GroupId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_has_ACCESS_GROUP_USER1` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user_access_group` */

insert  into `user_access_group`(`UserId`,`GroupId`) values (1,1),(5,1),(7,1),(9,1),(11,1),(1,2),(2,2),(6,2),(7,2),(9,2),(10,2),(11,2),(12,2),(14,2),(15,2);

/*Table structure for table `user_history` */

DROP TABLE IF EXISTS `user_history`;

CREATE TABLE `user_history` (
  `UserHistoryId` int(11) NOT NULL AUTO_INCREMENT,
  `Field` varchar(45) NOT NULL,
  `DataFrom` varchar(1024) DEFAULT NULL,
  `DataTo` varchar(1024) DEFAULT NULL,
  `RecCreated` datetime DEFAULT NULL,
  `RecCreatedBy` int(11) NOT NULL,
  `ReferenceId` int(11) NOT NULL,
  PRIMARY KEY (`UserHistoryId`),
  KEY `fk_USER_HISTORY_UserId` (`ReferenceId`),
  KEY `fk_USER_HISTORY_RecCreatedBy` (`RecCreatedBy`),
  CONSTRAINT `fk_USER_HISTORY_RecCreatedBy` FOREIGN KEY (`RecCreatedBy`) REFERENCES `user` (`UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_HISTORY_UserId` FOREIGN KEY (`ReferenceId`) REFERENCES `user` (`UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `user_history` */

insert  into `user_history`(`UserHistoryId`,`Field`,`DataFrom`,`DataTo`,`RecCreated`,`RecCreatedBy`,`ReferenceId`) values (1,'USER','mngo','ADDED','2014-04-11 15:51:51',1,5),(2,'USER','qanh','ADDED','2014-04-11 15:52:44',5,6),(3,'USER','dsadas','ADDED','2014-05-06 14:52:02',5,7),(4,'USER','quynhanh','ADDED','2014-05-06 15:05:32',5,9),(5,'USER','usern','ADDED','2014-05-22 13:41:39',5,10),(6,'USER','converter','ADDED','2014-05-23 13:59:17',5,11),(7,'USER','ert','ADDED','2014-05-23 14:00:54',5,12),(8,'USER','adsasd','ADDED','2014-05-23 14:09:14',5,14),(9,'USER','dantri','ADDED','2014-05-23 14:10:12',5,15),(10,'USER','asdsad','ADDED','2014-05-23 17:00:32',5,16);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
