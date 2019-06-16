-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 19 Feb 2019 pada 18.52
-- Versi server: 5.6.38
-- Versi PHP: 7.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `penduduk`
--
CREATE DATABASE IF NOT EXISTS `penduduk` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `penduduk`;

-- --------------------------------------------------------

--
-- Struktur dari tabel `penduduk`
--

CREATE TABLE `penduduk` (
  `nik_warga` varchar(16) NOT NULL,
  `nama_warga` varchar(45) NOT NULL,
  `id_jen_kel` int(5) NOT NULL,
  `tmpt_lahir` varchar(25) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `id_agama` int(5) NOT NULL,
  `id_pendidikan` int(5) NOT NULL,
  `pekerjaan` varchar(25) NOT NULL,
  `id_status_perkawinan` int(5) NOT NULL,
  `id_kewarganegaraan` int(5) NOT NULL,
  `provinsi` varchar(30) NOT NULL,
  `kode_pos` varchar(6) NOT NULL,
  `kabupaten_kota` varchar(30) NOT NULL,
  `kecamatan` varchar(30) NOT NULL,
  `desa_kelurahan` varchar(30) NOT NULL,
  `rt` varchar(4) NOT NULL,
  `rw` varchar(4) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penduduk`
--

INSERT INTO `penduduk` (`nik_warga`, `nama_warga`, `id_jen_kel`, `tmpt_lahir`, `tgl_lahir`, `id_agama`, `id_pendidikan`, `pekerjaan`, `id_status_perkawinan`, `id_kewarganegaraan`, `provinsi`, `kode_pos`, `kabupaten_kota`, `kecamatan`, `desa_kelurahan`, `rt`, `rw`, `alamat`) VALUES
('14115639467548', 'DEVI HERYANI', 2, 'KUNINGAN', '1996-04-15', 1, 7, 'FREELANCE', 2, 1, 'JAWA BARAT', '45513', 'KUNINGAN', 'KUNINGAN', 'SINDANGAGUNG', '2', '2', 'DUSUN PAHING'),
('3208090000000000', 'DIA', 2, 'KNG', '2019-02-12', 1, 2, 'KKK', 0, 1, 'B', '5', 'C', 'F', 'CIPORANG', '5', '5', 'HHG'),
('3208090101960018', 'DEJAN SUDRAJAT', 1, 'BANDUNG', '1996-01-01', 1, 5, 'FREELANCE', 2, 1, 'JAWA BARAT', '45515', 'KUNINGAN', 'KUNINGAN', 'WINDUSENGKAHAN', '9', '3', 'LINGK. SUBUR'),
('3208091601980019', 'LAILA WS', 2, 'KUNINGAN', '1998-01-16', 1, 5, '-', 2, 1, 'JAWA BARAT', '45515', 'KUNINGAN', 'KUNINGAN', 'WINDUSENGKAHAN', '9', '3', 'LINGK. SUBUR');

-- --------------------------------------------------------

--
-- Struktur dari tabel `ref_agama`
--

CREATE TABLE `ref_agama` (
  `id_agama` int(5) NOT NULL,
  `agama` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `ref_agama`
--

INSERT INTO `ref_agama` (`id_agama`, `agama`) VALUES
(0, '-'),
(1, 'ISLAM'),
(2, 'KRISTEN'),
(3, 'KATHOLIK'),
(4, 'BUDDHA'),
(5, 'HINDU'),
(6, 'KONGHUCU'),
(7, 'LAIN-LAIN');

-- --------------------------------------------------------

--
-- Struktur dari tabel `ref_jen_kel`
--

CREATE TABLE `ref_jen_kel` (
  `id_jen_kel` int(5) NOT NULL,
  `jen_kel` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `ref_jen_kel`
--

INSERT INTO `ref_jen_kel` (`id_jen_kel`, `jen_kel`) VALUES
(1, 'LAKI-LAKI'),
(2, 'PEREMPUAN');

-- --------------------------------------------------------

--
-- Struktur dari tabel `ref_kewarganegaraan`
--

CREATE TABLE `ref_kewarganegaraan` (
  `id_kewarganegaraan` int(5) NOT NULL,
  `kewarganegaraan` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `ref_kewarganegaraan`
--

INSERT INTO `ref_kewarganegaraan` (`id_kewarganegaraan`, `kewarganegaraan`) VALUES
(0, '-'),
(1, 'WNI'),
(2, 'WNA'),
(3, 'DWIKEWARGANEGARAAN');

-- --------------------------------------------------------

--
-- Struktur dari tabel `ref_pendidikan`
--

CREATE TABLE `ref_pendidikan` (
  `id_pendidikan` int(5) NOT NULL,
  `pendidikan` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `ref_pendidikan`
--

INSERT INTO `ref_pendidikan` (`id_pendidikan`, `pendidikan`) VALUES
(0, '-'),
(1, 'SD'),
(2, 'SMP'),
(3, 'SMA / SMK'),
(4, 'DIPLOMA'),
(5, 'S1'),
(6, 'S2'),
(7, 'S3');

-- --------------------------------------------------------

--
-- Struktur dari tabel `ref_status_perkawinan`
--

CREATE TABLE `ref_status_perkawinan` (
  `id_status_perkawinan` int(5) NOT NULL,
  `status_perkawinan` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `ref_status_perkawinan`
--

INSERT INTO `ref_status_perkawinan` (`id_status_perkawinan`, `status_perkawinan`) VALUES
(0, '-'),
(1, 'KAWIN'),
(2, 'BELUM KAWIN'),
(3, 'CERAI HIDUP'),
(4, 'CERAI MATI');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `penduduk`
--
ALTER TABLE `penduduk`
  ADD PRIMARY KEY (`nik_warga`),
  ADD KEY `id_jen_kel` (`id_jen_kel`),
  ADD KEY `id_agama` (`id_agama`,`id_pendidikan`,`id_status_perkawinan`,`id_kewarganegaraan`),
  ADD KEY `id_pendidikan` (`id_pendidikan`),
  ADD KEY `id_status_perkawinan` (`id_status_perkawinan`),
  ADD KEY `id_kewarganegaraan` (`id_kewarganegaraan`),
  ADD KEY `id_agama_2` (`id_agama`);

--
-- Indeks untuk tabel `ref_agama`
--
ALTER TABLE `ref_agama`
  ADD PRIMARY KEY (`id_agama`);

--
-- Indeks untuk tabel `ref_jen_kel`
--
ALTER TABLE `ref_jen_kel`
  ADD PRIMARY KEY (`id_jen_kel`);

--
-- Indeks untuk tabel `ref_kewarganegaraan`
--
ALTER TABLE `ref_kewarganegaraan`
  ADD PRIMARY KEY (`id_kewarganegaraan`);

--
-- Indeks untuk tabel `ref_pendidikan`
--
ALTER TABLE `ref_pendidikan`
  ADD PRIMARY KEY (`id_pendidikan`);

--
-- Indeks untuk tabel `ref_status_perkawinan`
--
ALTER TABLE `ref_status_perkawinan`
  ADD PRIMARY KEY (`id_status_perkawinan`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `ref_agama`
--
ALTER TABLE `ref_agama`
  MODIFY `id_agama` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `ref_jen_kel`
--
ALTER TABLE `ref_jen_kel`
  MODIFY `id_jen_kel` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `ref_kewarganegaraan`
--
ALTER TABLE `ref_kewarganegaraan`
  MODIFY `id_kewarganegaraan` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `ref_pendidikan`
--
ALTER TABLE `ref_pendidikan`
  MODIFY `id_pendidikan` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `ref_status_perkawinan`
--
ALTER TABLE `ref_status_perkawinan`
  MODIFY `id_status_perkawinan` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `penduduk`
--
ALTER TABLE `penduduk`
  ADD CONSTRAINT `penduduk_ibfk_1` FOREIGN KEY (`id_jen_kel`) REFERENCES `ref_jen_kel` (`id_jen_kel`),
  ADD CONSTRAINT `penduduk_ibfk_2` FOREIGN KEY (`id_pendidikan`) REFERENCES `ref_pendidikan` (`id_pendidikan`),
  ADD CONSTRAINT `penduduk_ibfk_3` FOREIGN KEY (`id_status_perkawinan`) REFERENCES `ref_status_perkawinan` (`id_status_perkawinan`),
  ADD CONSTRAINT `penduduk_ibfk_4` FOREIGN KEY (`id_agama`) REFERENCES `ref_agama` (`id_agama`),
  ADD CONSTRAINT `penduduk_ibfk_5` FOREIGN KEY (`id_kewarganegaraan`) REFERENCES `ref_kewarganegaraan` (`id_kewarganegaraan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
