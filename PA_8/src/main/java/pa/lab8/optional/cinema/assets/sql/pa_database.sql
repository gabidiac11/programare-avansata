-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 21, 2021 at 11:06 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pa_database`
--

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `name` varchar(255) NOT NULL,
  `genre_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `job`
--

CREATE TABLE `job` (
  `imdb_title_id` varchar(255) NOT NULL,
  `imdb_name_id` varchar(255) NOT NULL,
  `job` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `job`
--

INSERT INTO `job` (`imdb_title_id`, `imdb_name_id`, `job`) VALUES
('tt0000009', 'nm0063086', ''),
('tt0000009', 'nm0183823', ''),
('tt0000009', 'nm1309758', ''),
('tt0000009', 'nm0085156', ''),
('tt0000574', 'nm0846887', ''),
('tt0000574', 'nm0846894', ''),
('tt0000574', 'nm3002376', ''),
('tt0000574', 'nm0170118', ''),
('tt0000574', 'nm0846879', ''),
('tt0000574', 'nm0317210', 'producer'),
('tt0000574', 'nm0425854', 'producer'),
('tt0000574', 'nm0846911', 'producer'),
('tt0000574', 'nm2421834', ''),
('tt0000574', 'nm0675239', ''),
('tt0001892', 'nm0003425', ''),
('tt0001892', 'nm0699637', ''),
('tt0001892', 'nm0375839', ''),
('tt0001892', 'nm0016799', ''),
('tt0001892', 'nm0300487', ''),
('tt0001892', 'nm2131092', 'screenplay'),
('tt0001892', 'nm0423762', ''),
('tt0001892', 'nm0005869', ''),
('tt0001892', 'nm0282348', ''),
('tt0001892', 'nm2325688', ''),
('tt0002101', 'nm0306947', ''),
('tt0002101', 'nm0801774', ''),
('tt0002101', 'nm0276160', ''),
('tt0002101', 'nm0733482', ''),
('tt0002101', 'nm0309130', ''),
('tt0002101', 'nm0765026', 'adapted from the play by'),
('tt0002101', 'nm0182557', ''),
('tt0002101', 'nm1950505', ''),
('tt0002101', 'nm0397513', ''),
('tt0002101', 'nm0906610', ''),
('tt0002130', 'nm0660139', ''),
('tt0002130', 'nm0685283', ''),
('tt0002130', 'nm0209738', ''),
('tt0002130', 'nm3942815', ''),
('tt0002130', 'nm0078205', ''),
('tt0002130', 'nm0655824', ''),
('tt0002130', 'nm0019604', 'poem \"La Divina Commedia\"'),
('tt0002130', 'nm1374692', ''),
('tt0002130', 'nm1376296', ''),
('tt0002130', 'nm1376180', ''),
('tt0002199', 'nm0087381', ''),
('tt0002199', 'nm0245769', ''),
('tt0002199', 'nm0310155', ''),
('tt0002199', 'nm0391220', ''),
('tt0002199', 'nm0646058', ''),
('tt0002199', 'nm0391228', ''),
('tt0002199', 'nm0605017', ''),
('tt0002199', 'nm0014707', ''),
('tt0002199', 'nm0897085', ''),
('tt0002199', 'nm0446092', ''),
('tt0002423', 'nm0624470', ''),
('tt0002423', 'nm0417837', ''),
('tt0002423', 'nm0509573', ''),
('tt0002423', 'nm0903235', ''),
('tt0002423', 'nm0523932', ''),
('tt0002423', 'nm0266183', ''),
('tt0002423', 'nm0473134', 'writer'),
('tt0002423', 'nm0203452', 'producer'),
('tt0002423', 'nm0005882', '');

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE `movie` (
  `title` varchar(255) NOT NULL,
  `release_date` varchar(255) NOT NULL,
  `duration` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `movie_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `movie_genre`
--

CREATE TABLE `movie_genre` (
  `movie_id` varchar(255) NOT NULL,
  `genre_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `persons`
--

CREATE TABLE `persons` (
  `imdb_name_id` varchar(100) NOT NULL,
  `name` varchar(255) NOT NULL,
  `birth_details` text DEFAULT NULL,
  `date_of_birth` text DEFAULT NULL,
  `death_details` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`genre_id`);

--
-- Indexes for table `movie`
--
ALTER TABLE `movie`
  ADD PRIMARY KEY (`movie_id`);

--
-- Indexes for table `persons`
--
ALTER TABLE `persons`
  ADD PRIMARY KEY (`imdb_name_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `genre`
--
ALTER TABLE `genre`
  MODIFY `genre_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=861;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
