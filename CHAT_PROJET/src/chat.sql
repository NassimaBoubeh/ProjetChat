-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3307
-- Généré le : jeu. 12 jan. 2023 à 19:50
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `chat`
--

-- --------------------------------------------------------

--
-- Structure de la table `amis`
--

CREATE TABLE `amis` (
  `pseudo_clt` varchar(1000) NOT NULL,
  `pseudo_ami` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `amis`
--

INSERT INTO `amis` (`pseudo_clt`, `pseudo_ami`) VALUES
('', 'chaima'),
('', 'salwa'),
('nassima', 'chaima'),
('nassima', 'salwa'),
('chaima', 'chaima'),
('chaima', 'dad'),
('chaima', 'nassima'),
('nassima', 'dad'),
('dad', 'chaima'),
('dad', 'nassima'),
('salwa', 'adam'),
('adam', 'salwa'),
('manal', 'nassima'),
('manal', 'adam'),
('manal', 'chaima'),
('salwa', 'dad'),
('youssef', 'chaima'),
('youssef', 'nassima'),
('youssef', 'adam'),
('nassima', 'adam'),
('dad', 'salwa'),
('salwa', 'nassima'),
('nassima', 'manal'),
('mina', 'chaima'),
('mina', 'mina');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `login` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`login`, `password`) VALUES
('adam', '0000'),
('chaima', '0000'),
('dad', '0000'),
('manal', '0000'),
('mina', '123'),
('nassima', '0000'),
('salwa', '0000'),
('youssef', '0000');

-- --------------------------------------------------------

--
-- Structure de la table `groupe`
--

CREATE TABLE `groupe` (
  `admin` varchar(100) NOT NULL,
  `ami` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `groupe`
--

INSERT INTO `groupe` (`admin`, `ami`) VALUES
('nassima', 'chaima'),
('nassima', 'dad'),
('dad', 'chaima'),
('chaima', 'dad'),
('chaima', 'nassima'),
('dad', 'nassima'),
('mina', 'mina'),
('mina', 'chaima');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`login`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
