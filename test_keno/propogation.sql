CREATE DATABASE `keno` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `rates` (
  `id` bigint(20) NOT NULL,
  `balls` varchar(255) DEFAULT NULL,
  `calculated_date` datetime DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `player_id` varchar(255) DEFAULT NULL,
  `rate` int(11) DEFAULT NULL,
  `round` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `win` int(11) DEFAULT NULL,
  `uuid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


