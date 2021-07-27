-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 04, 2021 at 01:41 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parking_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `cars`
--

CREATE TABLE `cars` (
  `car_id` int(128) NOT NULL,
  `car_plate` varchar(12) NOT NULL,
  `car_brand` varchar(60) NOT NULL,
  `customer_id` int(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL,
  `full_name` varchar(128) NOT NULL,
  `VAT_number` int(11) NOT NULL,
  `adress` varchar(128) NOT NULL,
  `zip_code` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `emergent_parked`
--

CREATE TABLE `emergent_parked` (
  `parked_id` int(11) NOT NULL,
  `plate` varchar(11) NOT NULL,
  `owner_name` varchar(128) NOT NULL,
  `owner_adress` varchar(128) NOT NULL,
  `entrance_time` text NOT NULL,
  `exit_time` text NOT NULL,
  `emergentPricelist_id` int(11) NOT NULL,
  `parking_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `emergent_pricelist`
--

CREATE TABLE `emergent_pricelist` (
  `emergentPricelist_id` int(11) NOT NULL,
  `description` varchar(128) NOT NULL,
  `price` varchar(11) NOT NULL,
  `price_hour` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `parking_spaces`
--

CREATE TABLE `parking_spaces` (
  `parking_id` int(128) NOT NULL,
  `description` varchar(128) NOT NULL,
  `floor` int(5) NOT NULL,
  `spaces` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `price_list`
--

CREATE TABLE `price_list` (
  `pricelist_id` int(11) NOT NULL,
  `description` varchar(50) NOT NULL,
  `price` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `regular_parked`
--

CREATE TABLE `regular_parked` (
  `parkedCar_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `car_id` int(11) NOT NULL,
  `pricelist_id` int(11) NOT NULL,
  `parking_id` int(11) NOT NULL,
  `entrance_date` text NOT NULL,
  `estimatedExit_date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(128) NOT NULL,
  `user_name` varchar(128) NOT NULL,
  `user_password` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `user_password`) VALUES
(1, 'admin', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`car_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `emergent_parked`
--
ALTER TABLE `emergent_parked`
  ADD PRIMARY KEY (`parked_id`),
  ADD KEY `emergentPricelist_id` (`emergentPricelist_id`),
  ADD KEY `parking_id` (`parking_id`);

--
-- Indexes for table `emergent_pricelist`
--
ALTER TABLE `emergent_pricelist`
  ADD PRIMARY KEY (`emergentPricelist_id`);

--
-- Indexes for table `parking_spaces`
--
ALTER TABLE `parking_spaces`
  ADD PRIMARY KEY (`parking_id`);

--
-- Indexes for table `price_list`
--
ALTER TABLE `price_list`
  ADD PRIMARY KEY (`pricelist_id`);

--
-- Indexes for table `regular_parked`
--
ALTER TABLE `regular_parked`
  ADD PRIMARY KEY (`parkedCar_id`),
  ADD KEY `car_id` (`car_id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `parking_id` (`parking_id`),
  ADD KEY `pricelist_id` (`pricelist_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cars`
--
ALTER TABLE `cars`
  MODIFY `car_id` int(128) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `emergent_parked`
--
ALTER TABLE `emergent_parked`
  MODIFY `parked_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `emergent_pricelist`
--
ALTER TABLE `emergent_pricelist`
  MODIFY `emergentPricelist_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `parking_spaces`
--
ALTER TABLE `parking_spaces`
  MODIFY `parking_id` int(128) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `price_list`
--
ALTER TABLE `price_list`
  MODIFY `pricelist_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `regular_parked`
--
ALTER TABLE `regular_parked`
  MODIFY `parkedCar_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(128) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cars`
--
ALTER TABLE `cars`
  ADD CONSTRAINT `cars_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`);

--
-- Constraints for table `emergent_parked`
--
ALTER TABLE `emergent_parked`
  ADD CONSTRAINT `emergent_parked_ibfk_1` FOREIGN KEY (`emergentPricelist_id`) REFERENCES `emergent_pricelist` (`emergentPricelist_id`),
  ADD CONSTRAINT `emergent_parked_ibfk_2` FOREIGN KEY (`parking_id`) REFERENCES `parking_spaces` (`parking_id`);

--
-- Constraints for table `regular_parked`
--
ALTER TABLE `regular_parked`
  ADD CONSTRAINT `regular_parked_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`),
  ADD CONSTRAINT `regular_parked_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  ADD CONSTRAINT `regular_parked_ibfk_3` FOREIGN KEY (`parking_id`) REFERENCES `parking_spaces` (`parking_id`),
  ADD CONSTRAINT `regular_parked_ibfk_4` FOREIGN KEY (`pricelist_id`) REFERENCES `price_list` (`pricelist_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
