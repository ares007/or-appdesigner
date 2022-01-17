-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 06 dec 2021 om 13:11
-- Serverversie: 10.4.21-MariaDB
-- PHP-versie: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mydb`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `design`
--

CREATE TABLE `design` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `display_device` varchar(45) NOT NULL,
  `safe_space` int(11) DEFAULT NULL,
  `display_safe_space` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `design`
--

INSERT INTO `design` (`id`, `name`, `display_device`, `safe_space`, `display_safe_space`) VALUES
(1, 'Openremote', '', 32, 1),
(2, 'dontShow', '', 32, 1);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `page`
--

CREATE TABLE `page` (
  `id` int(11) NOT NULL,
  `design_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `is_homepage` tinyint(4) DEFAULT NULL,
  `in_navigation` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `page`
--

INSERT INTO `page` (`id`, `design_id`, `name`, `is_homepage`, `in_navigation`) VALUES
(1, 1, '2', 0, 1);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `widgets`
--

CREATE TABLE `widgets` (
  `id` int(11) NOT NULL,
  `label` varchar(45) NOT NULL,
  `page_id` int(11) NOT NULL,
  `position_x` int(3) NOT NULL,
  `postition_y` int(3) NOT NULL,
  `height` int(3) NOT NULL,
  `width` int(3) NOT NULL,
  `asset_type` varchar(45) NOT NULL,
  `font_size` int(11) DEFAULT NULL,
  `color` varchar(7) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `widgets`
--

INSERT INTO `widgets` (`id`, `label`, `page_id`, `position_x`, `postition_y`, `height`, `width`, `asset_type`, `font_size`, `color`) VALUES
(1, 'Temperature', 1, 1, 1, 1, 1, 'test', 18, '#000000');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `widget_values`
--

CREATE TABLE `widget_values` (
  `id` int(11) NOT NULL,
  `asset` varchar(45) NOT NULL,
  `time` datetime DEFAULT NULL,
  `value` varchar(45) DEFAULT NULL,
  `measurement` varchar(10) DEFAULT NULL,
  `widget_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `widget_values`
--

INSERT INTO `widget_values` (`id`, `asset`, `time`, `value`, `measurement`, `widget_id`) VALUES
(1, '1', '2021-12-06 11:23:48', '25', 'C', 1);

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `design`
--
ALTER TABLE `design`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Indexen voor tabel `page`
--
ALTER TABLE `page`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `widgets`
--
ALTER TABLE `widgets`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `widget_values`
--
ALTER TABLE `widget_values`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `page`
--
ALTER TABLE `page`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT voor een tabel `widgets`
--
ALTER TABLE `widgets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT voor een tabel `widget_values`
--
ALTER TABLE `widget_values`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
