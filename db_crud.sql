-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 02 Jan 2022 pada 07.42
-- Versi server: 10.4.14-MariaDB
-- Versi PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_crud`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_alamat`
--

CREATE TABLE `tb_alamat` (
  `id_alamat` int(11) NOT NULL,
  `id_customer` int(11) NOT NULL,
  `namapengirim` varchar(255) NOT NULL,
  `kodepos` varchar(10) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_alamat`
--

INSERT INTO `tb_alamat` (`id_alamat`, `id_customer`, `namapengirim`, `kodepos`, `alamat`) VALUES
(147, 107, 'Raisa Andriana', '25256', 'Jl. Kalumpang no. 2, Bandar Buat. Padang'),
(149, 113, 'Nanda', '', ''),
(148, 111, 'Teten', '25143', 'Jl. Dr. Sutomo No.140a, Kubu Marapalam, Kec. Padang Tim., Kota Padang, Sumatera Barat 25143, Indonesia'),
(136, 96, 'Teten Dwi', '252316', 'Jl. Kalumpang No 18, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_customer`
--

CREATE TABLE `tb_customer` (
  `id_customer` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nohp` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `foto_customer` varchar(500) NOT NULL DEFAULT 'person.png'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_customer`
--

INSERT INTO `tb_customer` (`id_customer`, `username`, `email`, `nohp`, `password`, `foto_customer`) VALUES
(107, 'ninas', 'ninas@gmail.com', '083176272374', '202cb962ac59075b964b07152d234b70', '6vtu7s6q44149ze6mc8u.jpg'),
(96, 'Teten', 'teten@gmail.com', '083176272374', '827ccb0eea8a706c4c34a16891f84e7b', 'u33kmj89cc89ee0jpvzd.jpg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_item`
--

CREATE TABLE `tb_item` (
  `id_item` int(11) NOT NULL,
  `id_kategori` int(11) NOT NULL,
  `nama_item` varchar(255) NOT NULL,
  `harga_item` int(11) NOT NULL,
  `stock` int(11) DEFAULT NULL,
  `foto_item` varchar(255) NOT NULL DEFAULT 'no_img.png',
  `deskripsi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_item`
--

INSERT INTO `tb_item` (`id_item`, `id_kategori`, `nama_item`, `harga_item`, `stock`, `foto_item`, `deskripsi`) VALUES
(72, 1, 'Lorem ipsum', 12000, 12, '58n0bnai13zexeqp28sr.jpg', 'Hidangan dalgona coffee berasal dari campurkan kopi instan, gula, dan air panas dengan jumlah perbandingan yang sama. Campuran itu kemudian dikocok sehingga menjadi krim dan kemudian ditambahkan ke susu dingin atau panas.'),
(73, 2, 'Coca Cola', 6500, 180, 'vsqw3pike7wqyfp5q145.jpg', 'Minuman dingin (doftdrink) yang mengandung karbonasi.'),
(76, 3, 'Pancake', 18000, 488, '3skf6kfii6wyqzcwtw92.jpg', 'Topping Pancake paling favorit adalah Es krim, nutella, campuran buah-buahan, dan madu.'),
(77, 4, 'Roti Daging', 28000, 484, 'ig9gru87n2wjhbc0s212.jpg', 'Isian roti berupa daging sapi atau daging ayam, dengan tambahan saus, saledri, dan mayones.'),
(79, 2, 'Teh Es', 6000, 497, 'jxhx6p93s1hgarwk5w1t.jpg', ''),
(80, 3, 'Muffin Coklat', 12000, 482, '2j1y3jh34u55dps63z5i.jpg', ''),
(82, 2, 'Aqua', 5000, 7, '5dqqgd4gxc5t9ppmdc2d.jpg', 'Air Mineral AQUA Diproses Secara Higenis & Tetap Terjaga Kemurniannya.'),
(83, 4, 'Donat', 5000, 489, '5bm5541kgvx044tw8vu9.jpg', 'Topping donat yang tersedia Tiramisu, Coklat, Blueberry, Kacang coklat, Oreo, dan lain-lain.'),
(89, 1, 'Sup Iga', 35000, 489, '8xdvnfc9cx7bmdw7xupj.jpg', 'Olahan iga'),
(90, 5, 'Pudding Buah', 35000, 495, 'xaphcnhw8rf52h8u8zmt.jpg', 'Berbagai variasi buah tersedia'),
(92, 6, 'Salad', 20000, 499, 'n8wqvmx9pjfyei2efs0m.jpg', 'lezatos'),
(97, 1, 'makanan', 6000, 56, 'no_img.png', 'enaak'),
(100, 10, 'French Fries ', 5000, 269, '6kjpc36v0ikwvi3qybeb.jpg', 'Saus sambal '),
(101, 7, 'French Fries', 5000, 6, '8wfn1905qrmnth6iznmn.jpg', 'Saus sambal');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_kategori`
--

CREATE TABLE `tb_kategori` (
  `id_kategori` int(11) NOT NULL,
  `nama_kategori` varchar(255) NOT NULL,
  `foto_kategori` varchar(255) NOT NULL DEFAULT 'no_img.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_kategori`
--

INSERT INTO `tb_kategori` (`id_kategori`, `nama_kategori`, `foto_kategori`) VALUES
(1, 'Makanan', 'img_makanan.png'),
(2, 'Minuman', 'img_minum.png'),
(3, 'Snack', 'img_snack.png'),
(4, 'Roti', 'img_roti3.png'),
(5, 'Buah', 'img_buah.png'),
(6, 'Sayur', 'img_sayur.png'),
(7, 'Ciki', 'an07be5kr1q0nvvueys7.jpg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_keranjang`
--

CREATE TABLE `tb_keranjang` (
  `id_keranjang` int(11) NOT NULL,
  `id_customer` int(11) NOT NULL,
  `id_item` int(11) NOT NULL,
  `banyak_item` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_keranjang`
--

INSERT INTO `tb_keranjang` (`id_keranjang`, `id_customer`, `id_item`, `banyak_item`) VALUES
(168, 96, 76, 1),
(167, 96, 73, 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_konfirmasi_bayar`
--

CREATE TABLE `tb_konfirmasi_bayar` (
  `id_konfirmasi` int(11) NOT NULL,
  `id_order` varchar(255) NOT NULL,
  `total_bayar` int(11) NOT NULL,
  `bukti` varchar(255) NOT NULL,
  `tgl_bayar` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_konfirmasi_bayar`
--

INSERT INTO `tb_konfirmasi_bayar` (`id_konfirmasi`, `id_order`, `total_bayar`, `bukti`, `tgl_bayar`) VALUES
(1, '300421842', 28000, 'COD', '30-04-21'),
(2, '300421597', 28000, 'COD', '30-04-21'),
(3, '300421361', 28000, 'COD', '30-04-21'),
(4, '300421718', 28000, 'ukcmqu4663uv3fbygjgy.png', '30-04-21'),
(5, '010521253', 28000, 'a972mxbue44xn6act55t.png', '01-05-21'),
(6, '010521253', 28000, 'uhfv4ubida3jqns30wai.png', '01-05-21'),
(7, '010521253', 0, 'f1ess1byhs8appt288y9.png', '01-05-21'),
(8, '01052167', 16500, '3jmz4iq9uktzg0fwig5p.png', '01-05-21'),
(9, '010521253', 0, 'wphx5e5k7eq2w3usvfum.png', '03-05-21'),
(10, '010521253', 0, '1mvycuc8m7twgk736je5.png', '03-05-21'),
(11, '010521253', 10000, 'e0rrwdtduxb79rma3qfs.png', '03-05-21'),
(12, '010521253', 10000, '51mp0s817mwtr9z487q5.png', '03-05-21'),
(13, '010521253', 10000, 's2ykmqawuydxrzz0vrh1.png', '03-05-21'),
(14, '010521253', 10000, '1sdrs3iyeh62k07kezwx.png', '03-05-21'),
(15, '010521253', 10000, '74yqjbgwiku9819x38xe.png', '03-05-21'),
(16, '010521253', 10000, '3nzf25f6crbss5qf7p83.png', '03-05-21'),
(17, '010521253', 10000, '46scz1xzn5hqs3xejdkc.png', '03-05-21'),
(18, '010521253', 10000, 'xjp22i0vaum01zdryjnb.png', '03-05-21'),
(19, '010521253', 10000, 're9k9d8rqqc8e0s1saie.png', '03-05-21'),
(20, '010521253', 10000, 'x2kx4n2gv2hm728dcn0v.png', '03-05-21'),
(21, '010521253', 10000, 'ju2dhkcc21g1ww1t8gjh.png', '03-05-21'),
(22, '010521253', 10000, 'fhk2y60u5m88zsz6ysts.png', '03-05-21'),
(23, '010521253', 10000, '5dsds5tbirwip1arknc2.png', '03-05-21'),
(24, '010521253', 10000, '2za7w222z19w9yk2a5jf.png', '03-05-21'),
(25, '010521253', 12000, '5yjg6sxy8mbttby3ip05.png', '03-05-21'),
(26, '010521253', 12000, '6eu4vy660r2uykxg472r.png', '03-05-21'),
(27, '010521253', 12000, '6gktfx5bz963k92574wk.png', '03-05-21'),
(28, '210521336', 16500, 'COD', '21-05-21');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_order`
--

CREATE TABLE `tb_order` (
  `id` int(11) NOT NULL,
  `id_order` varchar(255) NOT NULL,
  `status_order` varchar(255) NOT NULL,
  `tgl_order` varchar(50) NOT NULL,
  `jam_order` varchar(50) NOT NULL,
  `id_customer` int(11) NOT NULL,
  `metode_pembayaran` varchar(255) NOT NULL,
  `ongkir` int(11) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `total_bayar` int(11) NOT NULL,
  `kodepos` varchar(50) NOT NULL,
  `alamat_penerima` text NOT NULL,
  `nama_penerima` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_order`
--

INSERT INTO `tb_order` (`id`, `id_order`, `status_order`, `tgl_order`, `jam_order`, `id_customer`, `metode_pembayaran`, `ongkir`, `total_harga`, `total_bayar`, `kodepos`, `alamat_penerima`, `nama_penerima`) VALUES
(1, '300421842', 'Dibatalkan', '30-04-21', '11:22:36', 96, 'COD', 10000, 18000, 28000, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(2, '300421597', 'Dibatalkan', '30-04-21', '11:28:48', 96, 'COD', 10000, 18000, 28000, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(3, '300421361', 'Dibatalkan', '30-04-21', '11:30:05', 96, 'COD', 10000, 18000, 28000, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(4, '30042137', 'Dibatalkan', '30-04-21', '11:31:51', 96, 'Transfer Bank', 10000, 54000, 64000, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(5, '300421718', 'Dibatalkan', '30-04-21', '11:38:34', 96, 'Transfer Bank', 10000, 18000, 28000, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(6, '010521684', 'Pembayaran Tidak Diterima', '01-05-21', '22:05:09', 96, 'COD', 10000, 78500, 88500, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(7, '01052140', 'Pembayaran Tidak Diterima', '01-05-21', '22:05:18', 96, 'Transfer Bank', 10000, 6500, 16500, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(8, '010521551', 'Pembayaran Tidak Diterima', '01-05-21', '22:05:49', 96, 'Transfer Bank', 10000, 12000, 22000, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(9, '010521773', 'Pembayaran Tertunda', '01-05-21', '22:07:56', 96, 'Transfer Bank', 10000, 6500, 16500, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(10, '010521868', 'Dibatalkan', '01-05-21', '22:28:32', 96, 'Transfer Bank', 10000, 28000, 38000, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(11, '010521253', 'Segera Dikirim', '02-05-21', '08:30:12', 96, 'Transfer Bank', 10000, 18000, 28000, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(12, '01052167', 'Selesai', '01-05-21', '23:13:00', 96, 'Transfer Bank', 10000, 6500, 16500, '25231', 'Jl. Kalumpang No 8, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten'),
(13, '210521336', 'Selesai', '21-05-21', '18:32:59', 96, 'COD', 10000, 6500, 16500, '252316', 'Jl. Kalumpang No 18, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten Dwi'),
(14, '21052140', 'Pembayaran Tertunda', '21-05-21', '19:57:18', 96, 'Transfer Bank', 10000, 36000, 46000, '252316', 'Jl. Kalumpang No 18, Bandar Buat, Padang, Sumatera Barat 25231, Indonesia', 'Teten Dwi');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_order_detail`
--

CREATE TABLE `tb_order_detail` (
  `id_order_detail` int(11) NOT NULL,
  `id_order` varchar(255) NOT NULL,
  `id_item` int(11) NOT NULL,
  `banyak_item` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_order_detail`
--

INSERT INTO `tb_order_detail` (`id_order_detail`, `id_order`, `id_item`, `banyak_item`) VALUES
(1, '300421842', 72, 1),
(2, '300421597', 72, 1),
(3, '300421361', 76, 1),
(4, '30042137', 72, 3),
(5, '300421718', 76, 1),
(6, '010521684', 73, 1),
(7, '010521684', 72, 4),
(8, '01052140', 73, 1),
(9, '010521551', 80, 1),
(10, '010521773', 73, 1),
(11, '010521868', 77, 1),
(12, '010521253', 76, 1),
(13, '01052167', 73, 1),
(14, '210521336', 73, 1),
(15, '21052140', 76, 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_status_order`
--

CREATE TABLE `tb_status_order` (
  `id_status` int(11) NOT NULL,
  `id_order` int(11) NOT NULL,
  `status_order` varchar(255) NOT NULL,
  `tgl_status` varchar(50) NOT NULL,
  `jam_status` time NOT NULL,
  `keterangan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_status_order`
--

INSERT INTO `tb_status_order` (`id_status`, `id_order`, `status_order`, `tgl_status`, `jam_status`, `keterangan`) VALUES
(1, 300421842, 'Dibatalkan penjual', '30-04-21', '11:25:50', 'lama'),
(2, 300421842, 'Dibatalkan penjual', '30-04-21', '11:25:50', 'lama'),
(3, 300421597, 'Dibatalkan pembeli', '30-04-21', '11:29:13', 'lama '),
(4, 300421597, 'Dibatalkan pembeli', '30-04-21', '11:29:13', 'lama '),
(5, 300421361, 'Dibatalkan pembeli', '30-04-21', '11:30:36', 'lama'),
(6, 300421361, 'Dibatalkan pembeli', '30-04-21', '11:30:36', 'lama'),
(7, 30042137, 'Dibatalkan pembeli', '30-04-21', '11:32:07', 'lama '),
(8, 300421718, 'Dibatalkan penjual', '30-04-21', '11:39:35', 'habis'),
(9, 300421718, 'Dibatalkan penjual', '30-04-21', '11:39:35', 'habis'),
(10, 10521684, 'Segera Dikirim', '01-05-21', '22:05:09', NULL),
(11, 1052140, 'Pembayaran Tertunda', '01-05-21', '22:05:18', NULL),
(12, 10521551, 'Pembayaran Tertunda', '01-05-21', '22:05:49', NULL),
(13, 10521773, 'Pembayaran Tertunda', '01-05-21', '22:07:56', NULL),
(14, 10521868, 'Dibatalkan pembeli', '01-05-21', '23:13:31', 'lama'),
(15, 10521253, 'Pembayaran Tertunda', '01-05-21', '22:41:26', NULL),
(16, 10521253, 'Segera Dikirim', '01-05-21', '22:42:11', NULL),
(17, 10521253, 'Segera Dikirim', '01-05-21', '22:43:14', NULL),
(18, 1052167, 'Selesai', '01-05-21', '23:13:40', NULL),
(19, 1052167, 'Selesai', '01-05-21', '23:13:40', NULL),
(20, 10521253, 'Segera Dikirim', '03-05-21', '11:21:19', NULL),
(21, 10521253, 'Segera Dikirim', '03-05-21', '11:26:42', NULL),
(22, 10521253, 'Segera Dikirim', '03-05-21', '11:28:22', NULL),
(23, 10521253, 'Segera Dikirim', '03-05-21', '11:28:37', NULL),
(24, 10521253, 'Segera Dikirim', '03-05-21', '11:36:19', NULL),
(25, 10521253, 'Segera Dikirim', '03-05-21', '11:37:24', NULL),
(26, 10521253, 'Segera Dikirim', '03-05-21', '11:38:01', NULL),
(27, 10521253, 'Segera Dikirim', '03-05-21', '11:40:04', NULL),
(28, 210521336, 'Selesai', '21-05-21', '18:33:18', NULL),
(29, 210521336, 'Selesai', '21-05-21', '18:33:18', NULL),
(30, 21052140, 'Pembayaran Tertunda', '21-05-21', '19:57:18', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_user`
--

CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` text NOT NULL,
  `foto_user` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_user`
--

INSERT INTO `tb_user` (`id`, `username`, `email`, `password`, `foto_user`) VALUES
(24, 'Teten', 'teten@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', '7t4jwvmd67vsp4967d5w.jpg'),
(1, 'Lala', 'lala@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', '4m7p7a1va9jq6m85kuse.jpg'),
(2, 'admin', 'budi@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', 'ngcc0vn769uzc23rcgy9.jpg');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_alamat`
--
ALTER TABLE `tb_alamat`
  ADD PRIMARY KEY (`id_alamat`);

--
-- Indeks untuk tabel `tb_customer`
--
ALTER TABLE `tb_customer`
  ADD PRIMARY KEY (`id_customer`);

--
-- Indeks untuk tabel `tb_item`
--
ALTER TABLE `tb_item`
  ADD PRIMARY KEY (`id_item`);

--
-- Indeks untuk tabel `tb_kategori`
--
ALTER TABLE `tb_kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indeks untuk tabel `tb_keranjang`
--
ALTER TABLE `tb_keranjang`
  ADD PRIMARY KEY (`id_keranjang`);

--
-- Indeks untuk tabel `tb_konfirmasi_bayar`
--
ALTER TABLE `tb_konfirmasi_bayar`
  ADD PRIMARY KEY (`id_konfirmasi`);

--
-- Indeks untuk tabel `tb_order`
--
ALTER TABLE `tb_order`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `tb_order_detail`
--
ALTER TABLE `tb_order_detail`
  ADD PRIMARY KEY (`id_order_detail`);

--
-- Indeks untuk tabel `tb_status_order`
--
ALTER TABLE `tb_status_order`
  ADD PRIMARY KEY (`id_status`);

--
-- Indeks untuk tabel `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_alamat`
--
ALTER TABLE `tb_alamat`
  MODIFY `id_alamat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=150;

--
-- AUTO_INCREMENT untuk tabel `tb_customer`
--
ALTER TABLE `tb_customer`
  MODIFY `id_customer` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=108;

--
-- AUTO_INCREMENT untuk tabel `tb_item`
--
ALTER TABLE `tb_item`
  MODIFY `id_item` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT untuk tabel `tb_kategori`
--
ALTER TABLE `tb_kategori`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `tb_keranjang`
--
ALTER TABLE `tb_keranjang`
  MODIFY `id_keranjang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=169;

--
-- AUTO_INCREMENT untuk tabel `tb_konfirmasi_bayar`
--
ALTER TABLE `tb_konfirmasi_bayar`
  MODIFY `id_konfirmasi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT untuk tabel `tb_order`
--
ALTER TABLE `tb_order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT untuk tabel `tb_order_detail`
--
ALTER TABLE `tb_order_detail`
  MODIFY `id_order_detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT untuk tabel `tb_status_order`
--
ALTER TABLE `tb_status_order`
  MODIFY `id_status` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT untuk tabel `tb_user`
--
ALTER TABLE `tb_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
