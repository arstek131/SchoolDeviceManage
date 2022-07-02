-- phpMyAdmin SQL Dump
-- version 4.1.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Lug 03, 2017 alle 21:01
-- Versione del server: 5.6.33-log
-- PHP Version: 5.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `my_appqr`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `aula`
--

CREATE TABLE IF NOT EXISTS `aula` (
  `IdA` int(11) NOT NULL,
  `Tipo` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `aula`
--

INSERT INTO `aula` (`IdA`, `Tipo`) VALUES
(1, 'Normale'),
(4, 'Laboratorio'),
(6, 'Laboratorio'),
(12, 'Normale'),
(32, 'Laboratorio'),
(45, 'Laboratorio'),
(46, 'Normale'),
(53, 'Laboratorio'),
(58, 'Laboratorio'),
(71, 'Laboratorio'),
(72, 'Normale'),
(80, 'Laboratorio'),
(866, 'Laboratorio'),
(997, 'Laboratorio');

-- --------------------------------------------------------

--
-- Struttura della tabella `categoria`
--

CREATE TABLE IF NOT EXISTS `categoria` (
  `IdC` int(11) NOT NULL AUTO_INCREMENT,
  `Codice` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `Descrizione` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdC`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=9 ;

--
-- Dump dei dati per la tabella `categoria`
--

INSERT INTO `categoria` (`IdC`, `Codice`, `Descrizione`) VALUES
(1, 'A', 'Nessuna al momento'),
(2, 'B', 'Nessuna al momento'),
(3, 'C', 'Nessuna al momento'),
(4, 'D', 'Nessuna al momento'),
(5, 'E', 'Nessuna al momento'),
(6, 'F', 'Nessuna al momento'),
(7, 'G', 'Nessuna al momento'),
(8, 'H', 'Nessuna al momento');

-- --------------------------------------------------------

--
-- Struttura della tabella `cespite`
--

CREATE TABLE IF NOT EXISTS `cespite` (
  `NumInventario` int(11) NOT NULL,
  `Nome` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Descrizione` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'Nessuna descrizione',
  `Foto` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `QrCode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `DtCatalogazione` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CodIdC` int(11) NOT NULL,
  `CodIdA` int(11) NOT NULL,
  `CodUser_id` int(11) NOT NULL,
  PRIMARY KEY (`NumInventario`),
  KEY `CodIdC` (`CodIdC`),
  KEY `CodIdA` (`CodIdA`),
  KEY `CodUser_id` (`CodUser_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `cespite`
--

INSERT INTO `cespite` (`NumInventario`, `Nome`, `Descrizione`, `Foto`, `QrCode`, `DtCatalogazione`, `CodIdC`, `CodIdA`, `CodUser_id`) VALUES
(12, 'oi', 'funzionante', '', '121498576730', '2017-06-27 15:19:36', 1, 6, 71),
(24, 'hdjdnd', '', '', '241497795275', '2017-06-18 14:15:48', 6, 58, 63),
(373, 'o', '', '', '3731497095324', '2017-06-10 11:49:48', 5, 997, 63),
(746, 'stampante', '', '', '7461498722540', '2017-06-29 07:54:34', 1, 4, 73),
(835, 'cvuh', '', '', '8351498717447', '2017-06-29 06:24:38', 1, 4, 63),
(859, 'gnyn', '', '', '8591497392480', '2017-06-13 22:26:35', 1, 12, 63),
(3767, 'tastiera', 'difettosa', '', '37671498577494', '2017-06-27 15:33:00', 5, 4, 72),
(5655, 'fgy', 'fghhhccgh', '', '56551497635140', '2017-06-16 17:46:20', 1, 866, 63),
(8426, 'yu', 'funzionante', '', '84261498579219', '2017-06-27 16:01:47', 3, 4, 63),
(8653, 'yiu', '', '', '86531497120370', '2017-06-10 18:46:26', 1, 80, 66),
(43434, 'jsks', '', '', '434341497120252', '2017-06-10 18:44:33', 4, 46, 66),
(45454, 'fjfjd', '', '', '454541498551948', '2017-06-27 08:26:13', 1, 12, 63),
(55668, 'hh', '', '', '556681498717447', '2017-06-29 06:34:18', 1, 4, 63),
(123456, 'stampante', '', '', '1234561498722540', '2017-06-29 07:50:22', 1, 4, 73),
(284563, 'banco', '', '', '2845631497119411', '2017-06-10 18:31:55', 4, 1, 66),
(75764949, 'monitor', 'buono stato', NULL, '757649491496441726', '2017-06-02 22:16:15', 5, 72, 39);

-- --------------------------------------------------------

--
-- Struttura della tabella `foto`
--

CREATE TABLE IF NOT EXISTS `foto` (
  `CodNumInventario` int(11) NOT NULL,
  `Nome` varchar(255) DEFAULT NULL,
  `Path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CodNumInventario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `prestito`
--

CREATE TABLE IF NOT EXISTS `prestito` (
  `Data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CodUser` int(11) NOT NULL,
  `CodNumInventario` int(11) NOT NULL,
  PRIMARY KEY (`CodUser`,`CodNumInventario`),
  KEY `CodUser` (`CodUser`),
  KEY `CodNumInventario` (`CodNumInventario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `ruolo`
--

CREATE TABLE IF NOT EXISTS `ruolo` (
  `IdR` int(11) NOT NULL,
  `Nome` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`IdR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `ruolo`
--

INSERT INTO `ruolo` (`IdR`, `Nome`) VALUES
(0, 'Docente'),
(1, 'Bidello'),
(2, 'Personale ATA');

-- --------------------------------------------------------

--
-- Struttura della tabella `scansione`
--

CREATE TABLE IF NOT EXISTS `scansione` (
  `Data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CodUser` int(11) NOT NULL,
  `CodNumInventario` int(11) NOT NULL,
  PRIMARY KEY (`CodUser`,`CodNumInventario`),
  KEY `CodUser` (`CodUser`),
  KEY `CodNumInventario` (`CodNumInventario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `scansione`
--

INSERT INTO `scansione` (`Data`, `CodUser`, `CodNumInventario`) VALUES
('2017-06-27 08:30:41', 63, 24),
('2017-06-27 16:02:57', 63, 8426),
('2017-06-29 06:35:47', 63, 55668),
('2017-06-17 21:11:02', 63, 284563),
('2017-06-16 21:08:58', 63, 75764949),
('2017-06-10 13:02:49', 64, 75764949),
('2017-06-17 20:43:50', 68, 284563),
('2017-06-29 07:55:03', 73, 55668);

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `CodIdR` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `CodIdR` (`CodIdR`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=74 ;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`user_id`, `name`, `username`, `password`, `CodIdR`) VALUES
(39, 'Arslan', 'l', 'l', 0),
(63, 'v', 'c', 'LK09ZUQfjEWnBhyah8VNXg==\n', 0),
(64, 'fariz', 'farro', 'LK09ZUQfjEWnBhyah8VNXg==\n', 2),
(65, 'rpv', 'p', 'LK09ZUQfjEWnBhyah8VNXg==\n', 2),
(66, 'rehan', 'bho', 'LK09ZUQfjEWnBhyah8VNXg==\n', 0),
(67, 'v', 'v', 'LK09ZUQfjEWnBhyah8VNXg==\n', 2),
(68, 'gjhi', 'u', 'yy7m98PdS/LxdH6XI32Z6g==\n', 2),
(69, 'Sebastiano', 'Manfredini', 'AfayZ8ZH8E3uyhoqBFkz4w==\n', 1),
(70, 'Javeria ', 'Ali', '/xtB8YyXkHZe7EVEmOw07g==\n', 0),
(71, 'Arslan', 'Ars', 'LK09ZUQfjEWnBhyah8VNXg==\n', 0),
(72, 'polo', 'marco', 'LK09ZUQfjEWnBhyah8VNXg==\n', 0),
(73, 'alessandra', 'avellani', 'BtEUbt11hMWZemOvJ+dXYA==\n', 0);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `cespite`
--
ALTER TABLE `cespite`
  ADD CONSTRAINT `cespite_ibfk_1` FOREIGN KEY (`CodIdC`) REFERENCES `categoria` (`IdC`),
  ADD CONSTRAINT `cespite_ibfk_3` FOREIGN KEY (`CodUser_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `cespite_ibfk_4` FOREIGN KEY (`CodIdA`) REFERENCES `aula` (`IdA`);

--
-- Limiti per la tabella `foto`
--
ALTER TABLE `foto`
  ADD CONSTRAINT `foto_ibfk_1` FOREIGN KEY (`CodNumInventario`) REFERENCES `cespite` (`NumInventario`);

--
-- Limiti per la tabella `prestito`
--
ALTER TABLE `prestito`
  ADD CONSTRAINT `prestito_ibfk_1` FOREIGN KEY (`CodUser`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `prestito_ibfk_2` FOREIGN KEY (`CodNumInventario`) REFERENCES `cespite` (`NumInventario`);

--
-- Limiti per la tabella `scansione`
--
ALTER TABLE `scansione`
  ADD CONSTRAINT `scansione_ibfk_1` FOREIGN KEY (`CodUser`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `scansione_ibfk_2` FOREIGN KEY (`CodNumInventario`) REFERENCES `cespite` (`NumInventario`);

--
-- Limiti per la tabella `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`CodIdR`) REFERENCES `ruolo` (`IdR`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
