-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 26, 2026 at 07:36 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_sikursus`
--
CREATE DATABASE IF NOT EXISTS `db_sikursus` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `db_sikursus`;

-- --------------------------------------------------------

--
-- Table structure for table `instruktur`
--

CREATE TABLE `instruktur` (
  `id_instruktur` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `keahlian` varchar(120) NOT NULL,
  `no_hp` varchar(25) DEFAULT NULL,
  `email` varchar(120) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `instruktur`
--

INSERT INTO `instruktur` (`id_instruktur`, `nama`, `keahlian`, `no_hp`, `email`) VALUES
(1, 'Dina Pratama', 'Java Desktop', '081211110001', 'dina@example.test'),
(2, 'Rizal Maulana', 'Data Science', '081211110002', 'rizal@example.test');

-- --------------------------------------------------------

--
-- Table structure for table `kelas`
--

CREATE TABLE `kelas` (
  `id_kelas` int(11) NOT NULL,
  `id_kursus` int(11) NOT NULL,
  `id_instruktur` int(11) NOT NULL,
  `nama_kelas` varchar(100) NOT NULL,
  `tanggal_mulai` date NOT NULL,
  `kapasitas` int(11) NOT NULL DEFAULT 20
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `kelas`
--

INSERT INTO `kelas` (`id_kelas`, `id_kursus`, `id_instruktur`, `nama_kelas`, `tanggal_mulai`, `kapasitas`) VALUES
(1, 1, 1, 'Java-A', '2026-11-10', 25),
(2, 2, 2, 'DS-A', '2026-11-12', 20);

-- --------------------------------------------------------

--
-- Table structure for table `kursus`
--

CREATE TABLE `kursus` (
  `id_kursus` int(11) NOT NULL,
  `kode_kursus` varchar(20) NOT NULL,
  `nama_kursus` varchar(120) NOT NULL,
  `level` enum('BASIC','INTERMEDIATE','ADVANCED') NOT NULL,
  `biaya` decimal(12,2) NOT NULL CHECK (`biaya` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `kursus`
--

INSERT INTO `kursus` (`id_kursus`, `kode_kursus`, `nama_kursus`, `level`, `biaya`) VALUES
(1, 'JAVA-BSC', 'Java Desktop Fundamental', 'BASIC', 500000.00),
(2, 'DS-BSC', 'Data Science Fundamental', 'BASIC', 650000.00);

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

CREATE TABLE `pembayaran` (
  `id_pembayaran` int(11) NOT NULL,
  `id_pendaftaran` int(11) NOT NULL,
  `tanggal_bayar` date NOT NULL,
  `nominal` decimal(12,2) NOT NULL CHECK (`nominal` > 0),
  `metode` enum('TUNAI','TRANSFER','QRIS') NOT NULL,
  `status` enum('MENUNGGU','LUNAS','BATAL') NOT NULL DEFAULT 'MENUNGGU'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `pembayaran`
--

INSERT INTO `pembayaran` (`id_pembayaran`, `id_pendaftaran`, `tanggal_bayar`, `nominal`, `metode`, `status`) VALUES
(1, 1, '2026-11-03', 500000.00, 'TRANSFER', 'LUNAS'),
(2, 2, '2026-11-04', 650000.00, 'QRIS', 'LUNAS');

-- --------------------------------------------------------

--
-- Table structure for table `pendaftaran`
--

CREATE TABLE `pendaftaran` (
  `id_pendaftaran` int(11) NOT NULL,
  `id_peserta` int(11) NOT NULL,
  `id_kelas` int(11) NOT NULL,
  `tanggal_daftar` date NOT NULL,
  `status` enum('DAFTAR','AKTIF','SELESAI','BATAL') NOT NULL DEFAULT 'DAFTAR'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `pendaftaran`
--

INSERT INTO `pendaftaran` (`id_pendaftaran`, `id_peserta`, `id_kelas`, `tanggal_daftar`, `status`) VALUES
(1, 1, 1, '2026-11-01', 'AKTIF'),
(2, 2, 2, '2026-11-02', 'AKTIF');

-- --------------------------------------------------------

--
-- Table structure for table `peserta`
--

CREATE TABLE `peserta` (
  `id_peserta` int(11) NOT NULL,
  `nim` varchar(30) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jenis_kelamin` enum('L','P') NOT NULL,
  `prodi` varchar(100) NOT NULL,
  `no_hp` varchar(25) DEFAULT NULL,
  `email` varchar(120) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `peserta`
--

INSERT INTO `peserta` (`id_peserta`, `nim`, `nama`, `jenis_kelamin`, `prodi`, `no_hp`, `email`) VALUES
(1, '252001', 'Ahmad Fauzan', 'L', 'Informatika', '081234567801', 'ahmad@example.test'),
(2, '252002', 'Siti Rahmah', 'P', 'Informatika', '081234567802', 'siti@example.test'),
(4, '29240098', 'Patimah', 'P', 'if', '082362884733', 'patima13@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` enum('ADMIN','OPERATOR') NOT NULL DEFAULT 'OPERATOR',
  `aktif` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `username`, `password_hash`, `role`, `aktif`) VALUES
(1, 'admin', '120000:D5NpEN+NaKqvbMvg/DYuyA==:6P5j/mjMDxyzw+av6aU74gQ6YUpI5lmn5QDsmCTP9DY=', 'ADMIN', 1),
(2, 'operator', '120000:LEugeib1vR3VzQ2WYS5rPw==:VduZ22JVce9NmkNKrk0RANSusCke2N2Cz8dlViiBlYg=', 'OPERATOR', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `instruktur`
--
ALTER TABLE `instruktur`
  ADD PRIMARY KEY (`id_instruktur`);

--
-- Indexes for table `kelas`
--
ALTER TABLE `kelas`
  ADD PRIMARY KEY (`id_kelas`),
  ADD KEY `id_kursus` (`id_kursus`),
  ADD KEY `id_instruktur` (`id_instruktur`);

--
-- Indexes for table `kursus`
--
ALTER TABLE `kursus`
  ADD PRIMARY KEY (`id_kursus`),
  ADD UNIQUE KEY `kode_kursus` (`kode_kursus`);

--
-- Indexes for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`id_pembayaran`),
  ADD KEY `id_pendaftaran` (`id_pendaftaran`);

--
-- Indexes for table `pendaftaran`
--
ALTER TABLE `pendaftaran`
  ADD PRIMARY KEY (`id_pendaftaran`),
  ADD UNIQUE KEY `uk_peserta_kelas` (`id_peserta`,`id_kelas`),
  ADD KEY `id_kelas` (`id_kelas`);

--
-- Indexes for table `peserta`
--
ALTER TABLE `peserta`
  ADD PRIMARY KEY (`id_peserta`),
  ADD UNIQUE KEY `nim` (`nim`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `instruktur`
--
ALTER TABLE `instruktur`
  MODIFY `id_instruktur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `kelas`
--
ALTER TABLE `kelas`
  MODIFY `id_kelas` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `kursus`
--
ALTER TABLE `kursus`
  MODIFY `id_kursus` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `id_pembayaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `pendaftaran`
--
ALTER TABLE `pendaftaran`
  MODIFY `id_pendaftaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `peserta`
--
ALTER TABLE `peserta`
  MODIFY `id_peserta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `kelas`
--
ALTER TABLE `kelas`
  ADD CONSTRAINT `kelas_ibfk_1` FOREIGN KEY (`id_kursus`) REFERENCES `kursus` (`id_kursus`),
  ADD CONSTRAINT `kelas_ibfk_2` FOREIGN KEY (`id_instruktur`) REFERENCES `instruktur` (`id_instruktur`);

--
-- Constraints for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD CONSTRAINT `pembayaran_ibfk_1` FOREIGN KEY (`id_pendaftaran`) REFERENCES `pendaftaran` (`id_pendaftaran`);

--
-- Constraints for table `pendaftaran`
--
ALTER TABLE `pendaftaran`
  ADD CONSTRAINT `pendaftaran_ibfk_1` FOREIGN KEY (`id_peserta`) REFERENCES `peserta` (`id_peserta`),
  ADD CONSTRAINT `pendaftaran_ibfk_2` FOREIGN KEY (`id_kelas`) REFERENCES `kelas` (`id_kelas`);
--
-- Database: `kamildha_space`
--
CREATE DATABASE IF NOT EXISTS `kamildha_space` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `kamildha_space`;

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `kamar_id` int(11) NOT NULL,
  `nomor_hp` varchar(20) NOT NULL,
  `tanggal_masuk` date NOT NULL,
  `lama_sewa` varchar(50) NOT NULL,
  `total_bayar` decimal(15,2) NOT NULL,
  `metode_pembayaran` varchar(50) NOT NULL,
  `bukti_pembayaran` varchar(255) DEFAULT NULL,
  `status_pembayaran` varchar(20) DEFAULT 'Pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`id`, `user_id`, `kamar_id`, `nomor_hp`, `tanggal_masuk`, `lama_sewa`, `total_bayar`, `metode_pembayaran`, `bukti_pembayaran`, `status_pembayaran`, `created_at`) VALUES
(25, 2, 18, '085261384670', '2026-06-10', '4 bulan', 500000.00, 'Mandiri', '1780818073_ks.png', 'Sukses', '2026-06-07 07:41:13');

-- --------------------------------------------------------

--
-- Table structure for table `faq`
--

CREATE TABLE `faq` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `subjek` varchar(255) DEFAULT NULL,
  `pesan` text DEFAULT NULL,
  `lampiran` varchar(255) DEFAULT NULL,
  `tanggal` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `kamar_kost`
--

CREATE TABLE `kamar_kost` (
  `id` int(11) NOT NULL,
  `kost_id` int(11) NOT NULL,
  `nomor_kamar` varchar(10) NOT NULL,
  `status` enum('Tersedia','Dipesan','Terisi') DEFAULT 'Tersedia',
  `fasilitas` text DEFAULT NULL,
  `ukuran` varchar(50) DEFAULT NULL,
  `harga` decimal(10,0) DEFAULT NULL,
  `deskripsi` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kamar_kost`
--

INSERT INTO `kamar_kost` (`id`, `kost_id`, `nomor_kamar`, `status`, `fasilitas`, `ukuran`, `harga`, `deskripsi`) VALUES
(1, 3, '01', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(2, 3, '02', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(3, 3, '03', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(4, 3, '04', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(5, 3, '05', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(6, 3, '06', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(7, 3, '07', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(8, 3, '08', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(9, 3, '09', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(10, 3, '10', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(11, 3, '11', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(12, 3, '12', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(13, 3, '13', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(14, 3, '14', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(15, 3, '15', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(16, 3, '16', 'Tersedia', 'Tempat Tidur, Kasur, Wifi, Listrik', '3 x 4 Meter', 350000, 'Kamar nyaman dan tenang'),
(17, 2, '01', 'Tersedia', 'Kasur, Wifi, Listrik', '3 x 4 Meter', 500000, 'Kamar untuk putra'),
(18, 2, '02', 'Tersedia', 'Kasur, Wifi, Listrik', '3 x 4 Meter', 500000, 'Kamar untuk putra'),
(19, 2, '03', 'Tersedia', 'Kasur, Wifi, Listrik', '3 x 4 Meter', 500000, 'Kamar untuk putra'),
(20, 2, '04', 'Tersedia', 'Kasur, Wifi, Listrik', '3 x 4 Meter', 500000, 'Kamar untuk putra'),
(21, 2, '05', 'Tersedia', 'Kasur, Wifi, Listrik', '3 x 4 Meter', 500000, 'Kamar untuk putra'),
(22, 4, '01', 'Tersedia', 'Kasur, Sprei, Bantal, Lemari, Wifi, Listrik', '3 x 4 Meter', 750000, 'Kamar nyaman dan lengkap'),
(23, 4, '02', 'Tersedia', 'Kasur, Sprei, Bantal, Lemari, Wifi, Listrik', '3 x 4 Meter', 750000, 'Kamar nyaman dan lengkap'),
(24, 4, '03', 'Tersedia', 'Kasur, Sprei, Bantal, Lemari, Wifi, Listrik', '3 x 4 Meter', 750000, 'Kamar nyaman dan lengkap'),
(25, 4, '04', 'Tersedia', 'Kasur, Sprei, Bantal, Lemari, Wifi, Listrik', '3 x 4 Meter', 750000, 'Kamar nyaman dan lengkap'),
(26, 4, '05', 'Tersedia', 'Kasur, Sprei, Bantal, Lemari, Wifi, Listrik', '3 x 4 Meter', 750000, 'Kamar nyaman dan lengkap'),
(27, 4, '06', 'Tersedia', 'Kasur, Sprei, Bantal, Lemari, Wifi, Listrik', '3 x 4 Meter', 750000, 'Kamar nyaman dan lengkap'),
(28, 4, '07', 'Tersedia', 'Kasur, Sprei, Bantal, Lemari, Wifi, Listrik', '3 x 4 Meter', 750000, 'Kamar nyaman dan lengkap'),
(29, 4, '08', 'Tersedia', 'Kasur, Sprei, Bantal, Lemari, Wifi, Listrik', '3 x 4 Meter', 750000, 'Kamar nyaman dan lengkap'),
(30, 4, '09', 'Tersedia', 'Kasur, Sprei, Bantal, Lemari, Wifi, Listrik', '3 x 4 Meter', 750000, 'Kamar nyaman dan lengkap'),
(31, 4, '10', 'Tersedia', 'Kasur, Sprei, Bantal, Lemari, Wifi, Listrik', '3 x 4 Meter', 750000, 'Kamar nyaman dan lengkap'),
(32, 1, '01', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(33, 1, '02', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(34, 1, '03', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(35, 1, '04', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(36, 1, '05', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(37, 1, '06', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(38, 1, '07', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(39, 1, '08', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(40, 1, '09', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(41, 1, '10', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(42, 1, '11', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(43, 1, '12', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(44, 1, '13', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(45, 1, '14', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(46, 1, '15', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(47, 1, '16', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(48, 1, '17', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(49, 1, '18', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(50, 1, '19', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(51, 1, '20', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(52, 1, '21', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(53, 1, '22', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(54, 1, '23', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman'),
(55, 1, '24', 'Tersedia', 'Tempat tidur, Kasur, Lemari, Cermin, Gantungan baju, Kamar mandi dalam, Wifi, Listrik', '3 x 4 Meter', 450000, 'Kamar lengkap dan nyaman');

-- --------------------------------------------------------

--
-- Table structure for table `kost`
--

CREATE TABLE `kost` (
  `id` int(11) NOT NULL,
  `nama_kost` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kost`
--

INSERT INTO `kost` (`id`, `nama_kost`) VALUES
(1, 'Kost Syakura 2'),
(2, 'Kost Putra M&C'),
(3, 'Kost Naura'),
(4, 'Kost Farrel');

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran_kost`
--

CREATE TABLE `pembayaran_kost` (
  `id` int(11) NOT NULL,
  `kost_id` int(11) NOT NULL,
  `nama_layanan` varchar(50) NOT NULL,
  `nomor_tujuan` varchar(50) NOT NULL,
  `atas_nama` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pembayaran_kost`
--

INSERT INTO `pembayaran_kost` (`id`, `kost_id`, `nama_layanan`, `nomor_tujuan`, `atas_nama`) VALUES
(1, 1, 'BCA', '1234567890', 'Pemilik Kost Syakura 1'),
(2, 1, 'Dana', '085261384670', 'Kost Syakura 1'),
(3, 2, 'Mandiri', '9876543210', 'Pemilik Kost Putra M&C'),
(4, 2, 'Dana', '082223181654', 'Kost Putra M&C'),
(5, 3, 'BNI', '1122334455', 'Pemilik Kost Naura'),
(6, 3, 'Dana', '082210872512', 'Kost Naura'),
(7, 4, 'BRI', '5566778899', 'Pemilik Kost Farrel'),
(8, 4, 'Dana', '081263782538', 'Kost Farrel');

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `kost_id` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  `komentar` text NOT NULL,
  `lampiran` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`id`, `user_id`, `kost_id`, `rating`, `komentar`, `lampiran`, `created_at`) VALUES
(2, 2, 1, 5, 'indah cantik', '[\"1780819734_edit_0_kost.png\"]', '2026-06-07 08:06:02');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `telepon` varchar(20) DEFAULT NULL,
  `fotoprofil` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `level` varchar(50) DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `nama`, `telepon`, `fotoprofil`, `created_at`, `level`) VALUES
(1, 'kamildhaspace@google.com', '04101330', 'Muhamad Ilham', '085362801399', NULL, '2026-05-27 23:16:36', 'admin'),
(2, 'isyafitri057@gmail.com', '24', 'indah safitri', '085362801399', NULL, '2026-05-27 23:07:28', 'user'),
(3, 'syafitriindah885@gmail.com', '24', 'Indah Syafitri', '085261384670', NULL, '2026-05-31 02:35:22', 'user'),
(4, 'nisa145@gmail.com', 'nisa123', 'nisa', '081234567812', NULL, '2026-06-24 11:10:20', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_bookings_user` (`user_id`),
  ADD KEY `fk_bookings_kamar` (`kamar_id`);

--
-- Indexes for table `faq`
--
ALTER TABLE `faq`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_faq_user` (`user_id`);

--
-- Indexes for table `kamar_kost`
--
ALTER TABLE `kamar_kost`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kamar_kost_ibfk_1` (`kost_id`);

--
-- Indexes for table `kost`
--
ALTER TABLE `kost`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pembayaran_kost`
--
ALTER TABLE `pembayaran_kost`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kost_id` (`kost_id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_reviews_user` (`user_id`),
  ADD KEY `fk_reviews_kost` (`kost_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `faq`
--
ALTER TABLE `faq`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `kamar_kost`
--
ALTER TABLE `kamar_kost`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `kost`
--
ALTER TABLE `kost`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `pembayaran_kost`
--
ALTER TABLE `pembayaran_kost`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `fk_bookings_kamar` FOREIGN KEY (`kamar_id`) REFERENCES `kamar_kost` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_bookings_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `faq`
--
ALTER TABLE `faq`
  ADD CONSTRAINT `fk_faq_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `kamar_kost`
--
ALTER TABLE `kamar_kost`
  ADD CONSTRAINT `kamar_kost_ibfk_1` FOREIGN KEY (`kost_id`) REFERENCES `kost` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `pembayaran_kost`
--
ALTER TABLE `pembayaran_kost`
  ADD CONSTRAINT `pembayaran_kost_ibfk_1` FOREIGN KEY (`kost_id`) REFERENCES `kost` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `fk_reviews_kost` FOREIGN KEY (`kost_id`) REFERENCES `kost` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_reviews_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
--
-- Database: `phpmyadmin`
--
CREATE DATABASE IF NOT EXISTS `phpmyadmin` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `phpmyadmin`;

-- --------------------------------------------------------

--
-- Table structure for table `pma__bookmark`
--

CREATE TABLE `pma__bookmark` (
  `id` int(10) UNSIGNED NOT NULL,
  `dbase` varchar(255) NOT NULL DEFAULT '',
  `user` varchar(255) NOT NULL DEFAULT '',
  `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `query` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- --------------------------------------------------------

--
-- Table structure for table `pma__central_columns`
--

CREATE TABLE `pma__central_columns` (
  `db_name` varchar(64) NOT NULL,
  `col_name` varchar(64) NOT NULL,
  `col_type` varchar(64) NOT NULL,
  `col_length` text DEFAULT NULL,
  `col_collation` varchar(64) NOT NULL,
  `col_isNull` tinyint(1) NOT NULL,
  `col_extra` varchar(255) DEFAULT '',
  `col_default` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Central list of columns';

-- --------------------------------------------------------

--
-- Table structure for table `pma__column_info`
--

CREATE TABLE `pma__column_info` (
  `id` int(5) UNSIGNED NOT NULL,
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `column_name` varchar(64) NOT NULL DEFAULT '',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `mimetype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `transformation` varchar(255) NOT NULL DEFAULT '',
  `transformation_options` varchar(255) NOT NULL DEFAULT '',
  `input_transformation` varchar(255) NOT NULL DEFAULT '',
  `input_transformation_options` varchar(255) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Column information for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__designer_settings`
--

CREATE TABLE `pma__designer_settings` (
  `username` varchar(64) NOT NULL,
  `settings_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Settings related to Designer';

--
-- Dumping data for table `pma__designer_settings`
--

INSERT INTO `pma__designer_settings` (`username`, `settings_data`) VALUES
('root', '{\"snap_to_grid\":\"off\",\"angular_direct\":\"direct\",\"relation_lines\":\"true\"}');

-- --------------------------------------------------------

--
-- Table structure for table `pma__export_templates`
--

CREATE TABLE `pma__export_templates` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL,
  `export_type` varchar(10) NOT NULL,
  `template_name` varchar(64) NOT NULL,
  `template_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved export templates';

-- --------------------------------------------------------

--
-- Table structure for table `pma__favorite`
--

CREATE TABLE `pma__favorite` (
  `username` varchar(64) NOT NULL,
  `tables` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Favorite tables';

-- --------------------------------------------------------

--
-- Table structure for table `pma__history`
--

CREATE TABLE `pma__history` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL DEFAULT '',
  `db` varchar(64) NOT NULL DEFAULT '',
  `table` varchar(64) NOT NULL DEFAULT '',
  `timevalue` timestamp NOT NULL DEFAULT current_timestamp(),
  `sqlquery` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='SQL history for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__navigationhiding`
--

CREATE TABLE `pma__navigationhiding` (
  `username` varchar(64) NOT NULL,
  `item_name` varchar(64) NOT NULL,
  `item_type` varchar(64) NOT NULL,
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Hidden items of navigation tree';

-- --------------------------------------------------------

--
-- Table structure for table `pma__pdf_pages`
--

CREATE TABLE `pma__pdf_pages` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `page_nr` int(10) UNSIGNED NOT NULL,
  `page_descr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='PDF relation pages for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__recent`
--

CREATE TABLE `pma__recent` (
  `username` varchar(64) NOT NULL,
  `tables` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Recently accessed tables';

--
-- Dumping data for table `pma__recent`
--

INSERT INTO `pma__recent` (`username`, `tables`) VALUES
('root', '[{\"db\":\"db_sikursus\",\"table\":\"users\"},{\"db\":\"db_sikursus\",\"table\":\"peserta\"},{\"db\":\"db_sikursus\",\"table\":\"instruktur\"},{\"db\":\"kamildha_space\",\"table\":\"users\"},{\"db\":\"dbukm\",\"table\":\"pendaftaran_ukm\"},{\"db\":\"orbit_ukm\",\"table\":\"peserta\"},{\"db\":\"orbit_ukm\",\"table\":\"admin\"},{\"db\":\"dbukm\",\"table\":\"ukm\"},{\"db\":\"dbukm\",\"table\":\"pengurus_ukm\"},{\"db\":\"dbukm\",\"table\":\"mahasiswa\"}]');

-- --------------------------------------------------------

--
-- Table structure for table `pma__relation`
--

CREATE TABLE `pma__relation` (
  `master_db` varchar(64) NOT NULL DEFAULT '',
  `master_table` varchar(64) NOT NULL DEFAULT '',
  `master_field` varchar(64) NOT NULL DEFAULT '',
  `foreign_db` varchar(64) NOT NULL DEFAULT '',
  `foreign_table` varchar(64) NOT NULL DEFAULT '',
  `foreign_field` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Relation table';

-- --------------------------------------------------------

--
-- Table structure for table `pma__savedsearches`
--

CREATE TABLE `pma__savedsearches` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL DEFAULT '',
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `search_name` varchar(64) NOT NULL DEFAULT '',
  `search_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved searches';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_coords`
--

CREATE TABLE `pma__table_coords` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `pdf_page_number` int(11) NOT NULL DEFAULT 0,
  `x` float UNSIGNED NOT NULL DEFAULT 0,
  `y` float UNSIGNED NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table coordinates for phpMyAdmin PDF output';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_info`
--

CREATE TABLE `pma__table_info` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `display_field` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table information for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_uiprefs`
--

CREATE TABLE `pma__table_uiprefs` (
  `username` varchar(64) NOT NULL,
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL,
  `prefs` text NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Tables'' UI preferences';

-- --------------------------------------------------------

--
-- Table structure for table `pma__tracking`
--

CREATE TABLE `pma__tracking` (
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL,
  `version` int(10) UNSIGNED NOT NULL,
  `date_created` datetime NOT NULL,
  `date_updated` datetime NOT NULL,
  `schema_snapshot` text NOT NULL,
  `schema_sql` text DEFAULT NULL,
  `data_sql` longtext DEFAULT NULL,
  `tracking` set('UPDATE','REPLACE','INSERT','DELETE','TRUNCATE','CREATE DATABASE','ALTER DATABASE','DROP DATABASE','CREATE TABLE','ALTER TABLE','RENAME TABLE','DROP TABLE','CREATE INDEX','DROP INDEX','CREATE VIEW','ALTER VIEW','DROP VIEW') DEFAULT NULL,
  `tracking_active` int(1) UNSIGNED NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Database changes tracking for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__userconfig`
--

CREATE TABLE `pma__userconfig` (
  `username` varchar(64) NOT NULL,
  `timevalue` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `config_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User preferences storage for phpMyAdmin';

--
-- Dumping data for table `pma__userconfig`
--

INSERT INTO `pma__userconfig` (`username`, `timevalue`, `config_data`) VALUES
('root', '2026-11-26 06:18:37', '{\"Console\\/Mode\":\"collapse\"}');

-- --------------------------------------------------------

--
-- Table structure for table `pma__usergroups`
--

CREATE TABLE `pma__usergroups` (
  `usergroup` varchar(64) NOT NULL,
  `tab` varchar(64) NOT NULL,
  `allowed` enum('Y','N') NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User groups with configured menu items';

-- --------------------------------------------------------

--
-- Table structure for table `pma__users`
--

CREATE TABLE `pma__users` (
  `username` varchar(64) NOT NULL,
  `usergroup` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users and their assignments to user groups';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pma__central_columns`
--
ALTER TABLE `pma__central_columns`
  ADD PRIMARY KEY (`db_name`,`col_name`);

--
-- Indexes for table `pma__column_info`
--
ALTER TABLE `pma__column_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `db_name` (`db_name`,`table_name`,`column_name`);

--
-- Indexes for table `pma__designer_settings`
--
ALTER TABLE `pma__designer_settings`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_user_type_template` (`username`,`export_type`,`template_name`);

--
-- Indexes for table `pma__favorite`
--
ALTER TABLE `pma__favorite`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__history`
--
ALTER TABLE `pma__history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`,`db`,`table`,`timevalue`);

--
-- Indexes for table `pma__navigationhiding`
--
ALTER TABLE `pma__navigationhiding`
  ADD PRIMARY KEY (`username`,`item_name`,`item_type`,`db_name`,`table_name`);

--
-- Indexes for table `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  ADD PRIMARY KEY (`page_nr`),
  ADD KEY `db_name` (`db_name`);

--
-- Indexes for table `pma__recent`
--
ALTER TABLE `pma__recent`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__relation`
--
ALTER TABLE `pma__relation`
  ADD PRIMARY KEY (`master_db`,`master_table`,`master_field`),
  ADD KEY `foreign_field` (`foreign_db`,`foreign_table`);

--
-- Indexes for table `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_savedsearches_username_dbname` (`username`,`db_name`,`search_name`);

--
-- Indexes for table `pma__table_coords`
--
ALTER TABLE `pma__table_coords`
  ADD PRIMARY KEY (`db_name`,`table_name`,`pdf_page_number`);

--
-- Indexes for table `pma__table_info`
--
ALTER TABLE `pma__table_info`
  ADD PRIMARY KEY (`db_name`,`table_name`);

--
-- Indexes for table `pma__table_uiprefs`
--
ALTER TABLE `pma__table_uiprefs`
  ADD PRIMARY KEY (`username`,`db_name`,`table_name`);

--
-- Indexes for table `pma__tracking`
--
ALTER TABLE `pma__tracking`
  ADD PRIMARY KEY (`db_name`,`table_name`,`version`);

--
-- Indexes for table `pma__userconfig`
--
ALTER TABLE `pma__userconfig`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__usergroups`
--
ALTER TABLE `pma__usergroups`
  ADD PRIMARY KEY (`usergroup`,`tab`,`allowed`);

--
-- Indexes for table `pma__users`
--
ALTER TABLE `pma__users`
  ADD PRIMARY KEY (`username`,`usergroup`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__column_info`
--
ALTER TABLE `pma__column_info`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__history`
--
ALTER TABLE `pma__history`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  MODIFY `page_nr` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- Database: `pusat_beasiswa`
--
CREATE DATABASE IF NOT EXISTS `pusat_beasiswa` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `pusat_beasiswa`;
--
-- Database: `wordpress_db`
--
CREATE DATABASE IF NOT EXISTS `wordpress_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `wordpress_db`;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
