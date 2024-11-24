-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 22, 2024 at 06:22 AM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `keuangan`
--

-- --------------------------------------------------------

--
-- Table structure for table `dompet`
--

CREATE TABLE `dompet` (
  `id` int(11) NOT NULL,
  `saldo` decimal(15,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `dompet`
--

INSERT INTO `dompet` (`id`, `saldo`) VALUES
(1, '2300000.00');

-- --------------------------------------------------------

--
-- Table structure for table `pemasukan`
--

CREATE TABLE `pemasukan` (
  `id` int(11) NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL,
  `jumlah` decimal(15,2) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `kategori` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pemasukan`
--

INSERT INTO `pemasukan` (`id`, `keterangan`, `jumlah`, `tanggal`, `kategori`) VALUES
(1, 'Gaji Bulanan', '1000000.00', '2024-11-22', 'Gaji'),
(3, 'Tunjangan Hari Raya', '2000000.00', '2024-11-22', 'Hadiah');

--
-- Triggers `pemasukan`
--
DELIMITER $$
CREATE TRIGGER `after_insert_pemasukan` AFTER INSERT ON `pemasukan` FOR EACH ROW BEGIN
    UPDATE dompet 
    SET saldo = saldo + NEW.jumlah;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `pengeluaran`
--

CREATE TABLE `pengeluaran` (
  `id` int(11) NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL,
  `jumlah` decimal(15,2) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `kategori` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pengeluaran`
--

INSERT INTO `pengeluaran` (`id`, `keterangan`, `jumlah`, `tanggal`, `kategori`) VALUES
(1, 'Makan KFC', '200000.00', '2024-11-22', 'Makanan'),
(2, 'Kebutuhan Dadakn', '300000.00', '2024-11-21', 'Lainnya');

--
-- Triggers `pengeluaran`
--
DELIMITER $$
CREATE TRIGGER `after_insert_pengeluaran` AFTER INSERT ON `pengeluaran` FOR EACH ROW BEGIN
    UPDATE dompet SET saldo = saldo - NEW.jumlah;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dompet`
--
ALTER TABLE `dompet`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pemasukan`
--
ALTER TABLE `pemasukan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pengeluaran`
--
ALTER TABLE `pengeluaran`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dompet`
--
ALTER TABLE `dompet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `pemasukan`
--
ALTER TABLE `pemasukan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `pengeluaran`
--
ALTER TABLE `pengeluaran`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
