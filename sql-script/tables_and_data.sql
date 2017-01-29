-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 29, 2017 at 08:27 PM
-- Server version: 5.5.54-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `g_scrum01`
--

-- --------------------------------------------------------

--
-- Table structure for table `absence`
--

CREATE TABLE IF NOT EXISTS `absence` (
  `user_id` int(11) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`user_id`,`start_time`,`end_time`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `absence`
--

INSERT INTO `absence` (`user_id`, `start_time`, `end_time`) VALUES
(5, '2017-01-01 02:00:00', '2017-01-01 06:00:00'),
(6, '2016-12-31 23:00:00', '2017-01-01 14:00:00'),
(6, '2017-01-25 00:00:00', '2017-01-25 05:00:00'),
(8, '2017-01-16 05:00:00', '2017-01-16 07:00:00'),
(8, '2017-01-19 06:00:00', '2017-01-19 17:00:00'),
(8, '2017-01-24 00:00:00', '2017-01-24 02:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `availability`
--

CREATE TABLE IF NOT EXISTS `availability` (
  `user_id` int(11) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`user_id`,`start_time`,`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `availability`
--

INSERT INTO `availability` (`user_id`, `start_time`, `end_time`) VALUES
(5, '2017-01-24 23:00:00', '2017-01-25 22:59:00'),
(6, '2017-01-23 07:00:00', '2017-01-23 14:00:00'),
(6, '2017-01-24 07:00:00', '2017-01-24 14:00:00'),
(6, '2017-01-25 21:00:00', '2017-01-26 04:00:00'),
(6, '2017-01-26 21:00:00', '2017-01-27 04:00:00'),
(6, '2017-01-30 21:00:00', '2017-01-31 04:00:00'),
(6, '2017-01-31 21:00:00', '2017-02-01 04:00:00'),
(6, '2017-02-01 21:00:00', '2017-02-02 04:00:00'),
(8, '2017-01-22 14:00:00', '2017-01-22 19:00:00'),
(8, '2017-01-24 06:00:00', '2017-01-24 15:00:00'),
(8, '2017-01-24 23:00:00', '2017-01-26 23:00:00'),
(8, '2017-02-07 14:00:00', '2017-02-07 21:00:00'),
(8, '2017-02-08 14:00:00', '2017-02-08 21:00:00'),
(8, '2017-02-09 14:00:00', '2017-02-09 21:00:00'),
(10, '2017-01-23 09:00:00', '2017-01-24 09:00:00'),
(14, '2017-01-25 14:00:00', '2017-01-25 21:00:00'),
(14, '2017-01-26 14:00:00', '2017-01-26 21:00:00'),
(14, '2017-01-27 14:00:00', '2017-01-27 21:00:00'),
(14, '2017-02-02 21:00:00', '2017-02-03 04:00:00'),
(14, '2017-02-03 21:00:00', '2017-02-04 04:00:00'),
(15, '2017-01-26 14:00:00', '2017-01-26 21:00:00'),
(15, '2017-01-27 14:00:00', '2017-01-27 21:00:00'),
(15, '2017-01-28 14:00:00', '2017-01-28 21:00:00'),
(15, '2017-01-28 21:00:00', '2017-01-29 04:00:00'),
(15, '2017-01-29 21:00:00', '2017-01-30 04:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `changeover`
--

CREATE TABLE IF NOT EXISTS `changeover` (
  `new_user_id` int(11) DEFAULT NULL,
  `shift_id` int(11) NOT NULL,
  `approved` tinyint(4) NOT NULL DEFAULT '0',
  UNIQUE KEY `shift_id` (`shift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `changeover`
--

INSERT INTO `changeover` (`new_user_id`, `shift_id`, `approved`) VALUES
(8, 10, 0),
(8, 89, 0);

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE IF NOT EXISTS `department` (
  `department_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(11) NOT NULL,
  `phone` int(11) NOT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`department_id`, `department_name`, `phone`) VALUES
(1, 'Nord', 90920932),
(2, 'Sør', 90920933),
(3, 'Øst', 90920934),
(4, 'Vest', 90920935);

-- --------------------------------------------------------

--
-- Table structure for table `overtime`
--

CREATE TABLE IF NOT EXISTS `overtime` (
  `overtime_id` int(11) NOT NULL AUTO_INCREMENT,
  `shift_id` int(11) NOT NULL,
  `start` time NOT NULL,
  `end` time NOT NULL,
  `approved` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`overtime_id`),
  KEY `overtime_ibfk_1` (`shift_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `overtime`
--

INSERT INTO `overtime` (`overtime_id`, `shift_id`, `start`, `end`, `approved`) VALUES
(15, 10, '15:00:00', '19:00:00', 1),
(16, 13, '15:00:00', '19:00:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `shift`
--

CREATE TABLE IF NOT EXISTS `shift` (
  `shift_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `start` time NOT NULL,
  `end` time NOT NULL,
  `department_id` int(11) NOT NULL,
  `user_category_id` int(11) NOT NULL,
  `tradeable` tinyint(1) NOT NULL,
  `responsible_user` int(11) NOT NULL,
  PRIMARY KEY (`shift_id`),
  KEY `shift_ibfk_1` (`department_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=147 ;

--
-- Dumping data for table `shift`
--

INSERT INTO `shift` (`shift_id`, `date`, `start`, `end`, `department_id`, `user_category_id`, `tradeable`, `responsible_user`) VALUES
(1, '2017-01-01', '08:00:00', '16:00:00', 1, 1, 1, 0),
(2, '2017-01-01', '08:00:00', '16:00:00', 1, 1, 1, 0),
(3, '2017-01-01', '08:00:00', '15:00:00', 1, 1, 0, 0),
(4, '2017-01-01', '16:00:00', '23:59:00', 1, 1, 1, 0),
(5, '2017-01-01', '16:00:00', '23:00:00', 1, 1, 1, 0),
(6, '2017-01-04', '02:00:00', '08:00:00', 1, 1, 0, 0),
(7, '2017-01-15', '02:00:00', '16:00:00', 1, 1, 1, 0),
(8, '2017-02-16', '07:00:00', '16:00:00', 1, 1, 1, 0),
(9, '2017-03-17', '07:00:00', '16:00:00', 1, 1, 0, 0),
(10, '2017-05-02', '07:00:00', '16:00:00', 1, 1, 0, 0),
(11, '2017-05-06', '07:00:00', '16:00:00', 1, 1, 1, 0),
(12, '2017-01-01', '08:00:00', '16:00:00', 1, 1, 1, 0),
(13, '2017-01-01', '08:00:00', '16:00:00', 1, 1, 0, 0),
(14, '2017-01-01', '08:00:00', '15:00:00', 1, 1, 1, 0),
(15, '2017-01-01', '16:00:00', '23:59:00', 1, 2, 1, 0),
(16, '2017-01-01', '16:00:00', '23:00:00', 1, 2, 1, 0),
(17, '2017-01-04', '02:00:00', '08:00:00', 1, 1, 1, 0),
(18, '2017-01-15', '02:00:00', '16:00:00', 1, 1, 0, 0),
(19, '2017-02-16', '07:00:00', '16:00:00', 1, 1, 1, 0),
(20, '2017-03-17', '07:00:00', '16:00:00', 1, 1, 0, 0),
(21, '2017-05-02', '07:00:00', '16:00:00', 1, 1, 1, 0),
(22, '2017-05-06', '07:00:00', '16:00:00', 1, 1, 1, 0),
(23, '2017-01-01', '08:00:00', '16:00:00', 1, 2, 1, 0),
(24, '2017-01-02', '08:00:00', '16:00:00', 1, 1, 1, 0),
(25, '2017-01-02', '08:00:00', '16:00:00', 1, 1, 1, 0),
(26, '2017-01-12', '08:00:00', '16:00:00', 1, 1, 1, 0),
(27, '2017-01-12', '08:00:00', '16:00:00', 1, 1, 1, 0),
(28, '2017-01-24', '08:00:00', '16:00:00', 1, 1, 1, 0),
(29, '2017-01-02', '08:00:00', '15:00:00', 1, 1, 1, 0),
(30, '2017-01-03', '08:00:00', '15:00:00', 1, 1, 1, 0),
(31, '2017-01-24', '08:00:00', '15:00:00', 1, 1, 0, 0),
(47, '2017-01-23', '08:00:00', '16:00:00', 1, 1, 0, 0),
(48, '2017-01-25', '08:00:00', '16:00:00', 1, 1, 0, 0),
(49, '2017-01-25', '08:00:00', '16:00:00', 1, 1, 0, 0),
(50, '2017-01-23', '08:00:00', '16:00:00', 1, 1, 0, 0),
(51, '2017-01-26', '08:00:00', '16:00:00', 1, 1, 0, 0),
(52, '2017-01-26', '08:00:00', '16:00:00', 1, 1, 0, 0),
(53, '2017-01-27', '08:00:00', '16:00:00', 1, 1, 0, 0),
(54, '2017-01-27', '08:00:00', '16:00:00', 1, 1, 0, 0),
(55, '2017-01-04', '00:00:00', '08:00:00', 1, 1, 0, 0),
(56, '2017-01-07', '00:00:00', '08:00:00', 1, 1, 0, 0),
(57, '2017-01-06', '00:00:00', '08:00:00', 1, 1, 0, 0),
(58, '2017-01-05', '00:00:00', '08:00:00', 1, 1, 0, 0),
(59, '2017-01-16', '08:00:00', '16:00:00', 1, 1, 0, 0),
(60, '2017-01-20', '08:00:00', '16:00:00', 1, 1, 0, 0),
(61, '2017-01-19', '08:00:00', '16:00:00', 1, 1, 0, 0),
(62, '2017-01-18', '08:00:00', '16:00:00', 1, 1, 0, 0),
(63, '2017-01-17', '08:00:00', '16:00:00', 1, 1, 0, 0),
(64, '2017-01-21', '08:00:00', '16:00:00', 1, 1, 0, 0),
(65, '2017-01-16', '16:00:00', '23:59:00', 1, 1, 0, 0),
(66, '2017-01-20', '16:00:00', '23:59:00', 1, 1, 0, 0),
(67, '2017-01-19', '16:00:00', '23:59:00', 1, 1, 0, 0),
(68, '2017-01-18', '16:00:00', '23:59:00', 1, 1, 0, 0),
(69, '2017-01-17', '16:00:00', '23:59:00', 1, 1, 0, 0),
(70, '2017-01-21', '16:00:00', '23:59:00', 1, 1, 0, 0),
(71, '2017-01-24', '00:00:00', '08:00:00', 1, 1, 0, 0),
(72, '2017-01-29', '00:00:00', '08:00:00', 1, 1, 0, 0),
(73, '2017-01-28', '00:00:00', '08:00:00', 1, 1, 0, 0),
(74, '2017-01-27', '00:00:00', '08:00:00', 1, 1, 0, 0),
(75, '2017-01-26', '00:00:00', '08:00:00', 1, 1, 0, 0),
(76, '2017-01-25', '00:00:00', '08:00:00', 1, 1, 0, 0),
(77, '2017-02-13', '08:00:00', '16:00:00', 1, 1, 0, 0),
(78, '2017-02-15', '08:00:00', '16:00:00', 1, 1, 0, 0),
(79, '2017-02-15', '08:00:00', '16:00:00', 1, 1, 0, 0),
(80, '2017-02-14', '08:00:00', '16:00:00', 1, 1, 0, 0),
(81, '2017-02-14', '08:00:00', '16:00:00', 1, 1, 0, 0),
(82, '2017-02-13', '08:00:00', '16:00:00', 1, 1, 0, 0),
(83, '2017-02-02', '16:00:00', '23:59:00', 1, 1, 0, 0),
(84, '2017-02-01', '16:00:00', '23:59:00', 1, 1, 0, 0),
(85, '2017-02-01', '16:00:00', '23:59:00', 1, 1, 0, 0),
(86, '2017-01-31', '16:00:00', '23:59:00', 1, 1, 0, 0),
(87, '2017-02-02', '16:00:00', '23:59:00', 1, 1, 0, 0),
(88, '2017-01-31', '16:00:00', '23:59:00', 1, 1, 0, 0),
(89, '2017-02-03', '16:00:00', '23:59:00', 1, 1, 0, 0),
(90, '2017-02-03', '16:00:00', '23:59:00', 1, 1, 0, 0),
(91, '2017-02-03', '00:00:00', '08:00:00', 1, 1, 1, 0),
(92, '2017-01-25', '00:00:00', '08:00:00', 1, 1, 1, 0),
(93, '2017-01-26', '00:00:00', '08:00:00', 1, 1, 1, 0),
(94, '2017-01-26', '00:00:00', '08:00:00', 1, 2, 1, 0),
(95, '2017-01-27', '00:00:00', '08:00:00', 1, 1, 1, 0),
(96, '2017-01-28', '00:00:00', '08:00:00', 1, 1, 1, 0),
(97, '2017-08-24', '00:00:00', '08:00:00', 1, 1, 1, 0),
(98, '2017-08-24', '00:00:00', '08:00:00', 1, 1, 1, 0),
(99, '2017-08-24', '16:00:00', '23:59:00', 1, 1, 1, 0),
(100, '2017-01-28', '00:00:00', '08:00:00', 1, 1, 1, 0),
(101, '2017-01-27', '00:00:00', '08:00:00', 1, 1, 1, 0),
(102, '2017-01-27', '16:00:00', '23:59:00', 1, 1, 1, 0),
(103, '2017-01-23', '16:00:00', '23:59:00', 1, 1, 1, 0),
(104, '2017-08-19', '00:00:00', '08:00:00', 1, 1, 1, 0),
(105, '2017-08-19', '08:00:00', '16:00:00', 1, 1, 1, 0),
(106, '2017-08-19', '16:00:00', '23:59:00', 1, 1, 1, 0),
(107, '2017-08-19', '08:00:00', '16:00:00', 1, 1, 1, 0),
(108, '2017-08-20', '08:00:00', '16:00:00', 1, 1, 1, 0),
(109, '2017-08-20', '00:00:00', '08:00:00', 1, 1, 1, 0),
(110, '2017-08-19', '16:00:00', '23:59:00', 1, 1, 1, 0),
(111, '2017-08-19', '16:00:00', '23:59:00', 1, 1, 1, 0),
(112, '2017-08-19', '16:00:00', '23:59:00', 1, 1, 1, 0),
(113, '2017-01-24', '16:00:00', '23:59:00', 1, 1, 1, 0),
(114, '2017-01-30', '16:00:00', '23:59:00', 1, 1, 1, 0),
(115, '2017-01-30', '16:00:00', '23:59:00', 1, 1, 1, 0),
(116, '2017-01-24', '16:00:00', '23:59:00', 1, 1, 1, 0),
(117, '2017-01-24', '16:00:00', '23:59:00', 1, 1, 1, 0),
(118, '2017-01-23', '16:00:00', '23:59:00', 1, 1, 1, 0),
(119, '2017-01-23', '16:00:00', '23:59:00', 1, 1, 1, 0),
(120, '2017-01-23', '08:00:00', '16:00:00', 1, 1, 1, 0),
(121, '2017-01-25', '16:00:00', '23:59:00', 1, 1, 1, 0),
(122, '2017-01-28', '16:00:00', '23:59:00', 1, 1, 1, 0),
(123, '2017-01-29', '16:00:00', '23:59:00', 1, 2, 0, 0),
(124, '2017-01-29', '16:00:00', '23:59:00', 1, 2, 0, 0),
(125, '2017-01-28', '16:00:00', '23:59:00', 1, 2, 0, 0),
(126, '2017-01-29', '16:00:00', '23:59:00', 1, 2, 0, 0),
(127, '2017-01-23', '08:00:00', '16:00:00', 1, 2, 0, 0),
(128, '2017-01-23', '16:00:00', '23:59:00', 1, 2, 0, 0),
(129, '2017-01-23', '16:00:00', '23:59:00', 1, 1, 0, 0),
(130, '2017-01-25', '16:00:00', '23:59:00', 1, 1, 1, 0),
(131, '2017-01-23', '16:00:00', '23:59:00', 1, 1, 0, 0),
(132, '2017-01-23', '16:00:00', '23:59:00', 1, 1, 0, 0),
(133, '2017-01-27', '16:00:00', '23:59:00', 1, 1, 0, 0),
(134, '2017-01-27', '16:00:00', '23:59:00', 1, 1, 0, 0),
(135, '2017-01-27', '16:00:00', '23:59:00', 1, 1, 0, 0),
(136, '2017-01-27', '16:00:00', '23:59:00', 1, 1, 0, 0),
(137, '2017-01-27', '16:00:00', '23:59:00', 1, 1, 0, 0),
(138, '2017-01-29', '00:00:00', '08:00:00', 1, 1, 0, 0),
(139, '2017-01-28', '00:00:00', '08:00:00', 1, 1, 0, 0),
(140, '2017-01-27', '00:00:00', '08:00:00', 1, 1, 0, 0),
(141, '2017-01-25', '00:00:00', '08:00:00', 1, 1, 0, 0),
(142, '2017-01-26', '00:00:00', '08:00:00', 1, 1, 0, 0),
(143, '2017-01-18', '08:00:00', '16:00:00', 1, 1, 0, 0),
(144, '2017-03-18', '08:00:00', '16:00:00', 1, 1, 1, 0),
(145, '2017-03-08', '08:00:00', '16:00:00', 1, 1, 1, 0),
(146, '2017-01-24', '08:00:00', '16:00:00', 1, 1, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `admin_rights` tinyint(1) NOT NULL DEFAULT '0',
  `user_category_id` int(11) NOT NULL,
  `mobile` int(11) DEFAULT NULL,
  `address` varchar(60) DEFAULT NULL,
  `email` varchar(60) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `expired` timestamp NULL DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `work_percent` int(11) DEFAULT NULL,
  `department_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `token` (`token`),
  KEY `user_category_id` (`user_category_id`),
  KEY `department_id` (`department_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=22 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `password`, `admin_rights`, `user_category_id`, `mobile`, `address`, `email`, `token`, `expired`, `active`, `work_percent`, `department_id`) VALUES
(5, 'Simen', 'Slettesen', '27cb3294abf5628171bc9bf288eb5b585e67674c4323177a63b77ddca816fdfe', 0, 1, 12345678, 'Adressegata 1', 'simen@internett.no', 'mujbntlt0coq58aas53krn1k7r', '2017-02-05 16:29:50', 1, 100, 1),
(6, 'Amanda', 'Admin', 'e4b8ba73802b09eac9348d84032bb675fb37273f8eff86d0c3b46e96973e1895', 1, 1, 12345678, 'Adressegata 2', 'eposten@internett.no', 'im87lmm3g9j1jgei33f5qqig83', '2017-02-05 19:11:20', 1, 100, 1),
(7, 'Anna', 'Alfsen', '9ea62f19aa05293318fe34313be0c1465bf83d9230343ac4fceb72d7609f9016', 0, 2, 81549300, 'Bakkeveien 2', 'anna@alfsen.no', '9fqgaal7k17ss3h996o613ei8b', '2017-02-03 17:08:40', 1, 100, 1),
(8, 'Siri', 'Sirisen', 'de3e28a5931ae43ae0941c6784c825d9383d24bc834631c8b42ad795d63be206', 0, 1, 12345678, 'Adressegata 1', 'epost@internett.no', 'ofh2d05pb2ilp3vddcvb97k4gt', '2017-02-05 16:01:06', 1, 100, 1),
(10, 'Test', 'Brukersen', 'f3b5b0b7ecc50e13c61d8b5885584be23f514f3420b4af97021c4c032d2e7c5d', 0, 1, 12345678, 'Testveien 1', 'roger.holten@gmail.com', 'gveos8vng7523umjhl6fptmh4c', '2017-02-02 12:46:14', 1, 100, 1),
(11, 'Simen', 'Kjosavik', '27cb3294abf5628171bc9bf288eb5b585e67674c4323177a63b77ddca816fdfe', 1, 1, 12345678, 'Nedre 123', 'erik_kjosavik@hotmail.com', 'jjfvhmra2vcpfne3pqmh87hl1a', '2017-02-01 12:05:07', 1, 40, 1),
(12, 'Kjøttmeis', 'Kjøttsen', '27cb3294abf5628171bc9bf288eb5b585e67674c4323177a63b77ddca816fdfe', 1, 2, 12345678, 'Nedre 123', 'kjottmeis@internett.no', NULL, NULL, 1, 40, 1),
(13, 'Erik', 'Kjusavik', '27cb3294abf5628171bc9bf288eb5b585e67674c4323177a63b77ddca816fdfe', 1, 2, 12345678, 'Nedre 123', 'kjusavik@internett.no', NULL, NULL, 1, 40, 1),
(14, 'Simen', 'Testtest', '27cb3294abf5628171bc9bf288eb5b585e67674c4323177a63b77ddca816fdfe', 1, 1, 12345678, 'Vei 1', 'a@b.no', '3heo117s2sciq8m1f7p2j5gp8f', '2017-02-05 16:53:43', 1, 100, 1),
(15, 'Simen', 'Brukersen', '27cb3294abf5628171bc9bf288eb5b585e67674c4323177a63b77ddca816fdfe', 0, 1, 12345678, 'Adressegata 1', 'simenb@internett.no', 'jqqsu61ocoaf2orqq72iupig4u', '2017-02-03 09:45:59', 1, 100, 1),
(16, 'Håvard', 'Bakken', '4dcc1581bc4dc5a17df788db86b38a5ae028d9d1a4c5ccfafcf1d8f75fca2cf8', 0, 1, 0, 'Bakkeveien 2', 'dsfsdfs@fsdfk.com', NULL, NULL, 1, 30, 1),
(18, 'Vegard', 'Stenvik', 'b97997c83742207cfc084bd98e611f056f51d725ba458ee06b5ff882d0e2e96c', 0, 1, 0, 'Øvre Bakklandet 64', 'vegardks@gmail.com', NULL, NULL, 1, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_category`
--

CREATE TABLE IF NOT EXISTS `user_category` (
  `type` varchar(255) NOT NULL,
  `user_category_id` int(11) NOT NULL,
  PRIMARY KEY (`user_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_category`
--

INSERT INTO `user_category` (`type`, `user_category_id`) VALUES
('Hjelpepleier', 1),
('Sykepleier', 2),
('Assistent', 3);

-- --------------------------------------------------------

--
-- Table structure for table `user_shift`
--

CREATE TABLE IF NOT EXISTS `user_shift` (
  `user_id` int(11) NOT NULL,
  `shift_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`shift_id`),
  KEY `shift_id` (`shift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_shift`
--

INSERT INTO `user_shift` (`user_id`, `shift_id`) VALUES
(7, 3),
(7, 6),
(10, 10),
(8, 13),
(8, 26),
(8, 31),
(8, 47),
(5, 48),
(8, 49),
(5, 50),
(5, 51),
(8, 52),
(8, 53),
(5, 54),
(5, 55),
(5, 56),
(5, 57),
(5, 58),
(10, 59),
(10, 60),
(10, 61),
(10, 62),
(10, 63),
(10, 64),
(8, 65),
(8, 66),
(8, 67),
(8, 69),
(8, 70),
(5, 71),
(5, 72),
(5, 73),
(5, 74),
(5, 75),
(5, 76),
(10, 77),
(8, 78),
(10, 79),
(8, 80),
(10, 81),
(8, 82),
(5, 83),
(10, 84),
(5, 85),
(10, 86),
(10, 87),
(5, 88),
(5, 89),
(10, 90),
(8, 91),
(6, 127),
(6, 128),
(6, 129),
(6, 130),
(10, 132),
(6, 133),
(8, 134),
(13, 135),
(12, 136),
(11, 137),
(6, 138),
(13, 139),
(13, 140),
(13, 141),
(13, 142),
(8, 144),
(7, 146);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `absence`
--
ALTER TABLE `absence`
  ADD CONSTRAINT `absence_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `availability`
--
ALTER TABLE `availability`
  ADD CONSTRAINT `availability_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `changeover`
--
ALTER TABLE `changeover`
  ADD CONSTRAINT `changeover_shift_shift_id_fk` FOREIGN KEY (`shift_id`) REFERENCES `shift` (`shift_id`);

--
-- Constraints for table `overtime`
--
ALTER TABLE `overtime`
  ADD CONSTRAINT `overtime_ibfk_1` FOREIGN KEY (`shift_id`) REFERENCES `shift` (`shift_id`);

--
-- Constraints for table `shift`
--
ALTER TABLE `shift`
  ADD CONSTRAINT `shift_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_category_id`) REFERENCES `user_category` (`user_category_id`),
  ADD CONSTRAINT `user_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`);

--
-- Constraints for table `user_shift`
--
ALTER TABLE `user_shift`
  ADD CONSTRAINT `user_shift_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `user_shift_ibfk_2` FOREIGN KEY (`shift_id`) REFERENCES `shift` (`shift_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
