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

/*Data for the table `access_group` */

insert  into `access_group`(`GroupId`,`Name`) values (1,'ADMIN'),(2,'USER');

/*Data for the table `activity` */

/*Data for the table `user` */

insert  into `user`(`UserId`,`Username`,`FirstName`,`LastName`,`HashedPassword`,`RecActive`) values (1,'admin','Admin','','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1),(2,'user','User',NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1),(3,'mngo','Minh Duc','Ngo','32d3be7783992165f250da0e679e28b49dcdd51718ba6f9fbe1d5639697a0186',1);

/*Data for the table `user_access_group` */

insert  into `user_access_group`(`UserId`,`GroupId`) values (1,1),(3,1),(1,2),(2,2),(3,2);

/*Data for the table `user_history` */

insert  into `user_history`(`UserHistoryId`,`Field`,`DataFrom`,`DataTo`,`RecCreated`,`RecCreatedBy`,`UserId`) values (1,'USER','mngo','ADDED','2014-04-11 14:18:25',1,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
