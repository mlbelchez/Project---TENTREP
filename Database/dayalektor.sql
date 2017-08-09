-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 05, 2017 at 10:21 PM
-- Server version: 10.1.8-MariaDB
-- PHP Version: 5.6.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dayalektor`
--

-- --------------------------------------------------------

--
-- Table structure for table `answer`
--

CREATE TABLE `answer` (
  `user_iduser` int(11) NOT NULL,
  `forum_idforum` int(11) NOT NULL,
  `forum_user_iduser` int(11) NOT NULL,
  `forum_dialect_iddialect` int(11) NOT NULL,
  `answer` varchar(45) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `answer`
--

INSERT INTO `answer` (`user_iduser`, `forum_idforum`, `forum_user_iduser`, `forum_dialect_iddialect`, `answer`, `date_time`) VALUES
(2, 1, 1, 2, 'Answer1', '2017-08-09 17:27:39');

-- --------------------------------------------------------

--
-- Table structure for table `bookmark`
--

CREATE TABLE `bookmark` (
  `idbookmark` int(11) NOT NULL,
  `library_translation_idtranslation` int(11) NOT NULL,
  `library_word_idword` int(11) NOT NULL,
  `library_word_dialect_iddialect` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bookmark`
--

INSERT INTO `bookmark` (`idbookmark`, `library_translation_idtranslation`, `library_word_idword`, `library_word_dialect_iddialect`) VALUES
(1, 1, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `dialect`
--

CREATE TABLE `dialect` (
  `iddialect` int(11) NOT NULL,
  `dialect` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dialect`
--

INSERT INTO `dialect` (`iddialect`, `dialect`) VALUES
(1, 'Bicolano'),
(2, 'Bisaya');

-- --------------------------------------------------------

--
-- Table structure for table `favorite`
--

CREATE TABLE `favorite` (
  `idfavorite` int(11) NOT NULL,
  `answer_user_iduser` int(11) NOT NULL,
  `answer_forum_idforum` int(11) NOT NULL,
  `answer_forum_user_iduser` int(11) NOT NULL,
  `answer_forum_dialect_iddialect` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `favorite`
--

INSERT INTO `favorite` (`idfavorite`, `answer_user_iduser`, `answer_forum_idforum`, `answer_forum_user_iduser`, `answer_forum_dialect_iddialect`) VALUES
(1, 2, 1, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `forum`
--

CREATE TABLE `forum` (
  `idforum` int(11) NOT NULL,
  `question` varchar(100) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `user_iduser` int(11) NOT NULL,
  `dialect_iddialect` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `forum`
--

INSERT INTO `forum` (`idforum`, `question`, `status`, `date_time`, `user_iduser`, `dialect_iddialect`) VALUES
(1, 'Question1', 'Status', '2017-08-09 08:21:09', 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `library`
--

CREATE TABLE `library` (
  `translation_idtranslation` int(11) NOT NULL,
  `word_idword` int(11) NOT NULL,
  `word_dialect_iddialect` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `library`
--

INSERT INTO `library` (`translation_idtranslation`, `word_idword`, `word_dialect_iddialect`) VALUES
(1, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `translation`
--

CREATE TABLE `translation` (
  `idtranslation` int(11) NOT NULL,
  `translated_word` varchar(45) DEFAULT NULL,
  `voice` varchar(45) DEFAULT NULL,
  `definition` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `translation`
--

INSERT INTO `translation` (`idtranslation`, `translated_word`, `voice`, `definition`) VALUES
(1, 'translatedWord1', NULL, 'Definition1');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `iduser` int(11) NOT NULL,
  `username` varchar(25) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(25) NOT NULL,
  `nationality` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`iduser`, `username`, `email`, `password`, `nationality`) VALUES
(1, 'username1', 'email1@gmail.com', 'password1', 'Filipino'),
(2, 'username2', 'email2@gmail.com', 'password2', 'Filipino');

-- --------------------------------------------------------

--
-- Table structure for table `user_has_dialect`
--

CREATE TABLE `user_has_dialect` (
  `user_iduser` int(11) NOT NULL,
  `dialect_iddialect` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `word`
--

CREATE TABLE `word` (
  `idword` int(11) NOT NULL,
  `orginal_word` varchar(45) NOT NULL,
  `word_detail` varchar(45) DEFAULT NULL,
  `dialect_iddialect` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `word`
--

INSERT INTO `word` (`idword`, `orginal_word`, `word_detail`, `dialect_iddialect`) VALUES
(1, 'OriginalWord1', 'WordDetail1', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answer`
--
ALTER TABLE `answer`
  ADD PRIMARY KEY (`user_iduser`,`forum_idforum`,`forum_user_iduser`,`forum_dialect_iddialect`),
  ADD KEY `fk_user_has_forum_forum1_idx` (`forum_idforum`,`forum_user_iduser`,`forum_dialect_iddialect`),
  ADD KEY `fk_user_has_forum_user1_idx` (`user_iduser`);

--
-- Indexes for table `bookmark`
--
ALTER TABLE `bookmark`
  ADD PRIMARY KEY (`idbookmark`),
  ADD KEY `fk_bookmark_library1_idx` (`library_translation_idtranslation`,`library_word_idword`,`library_word_dialect_iddialect`);

--
-- Indexes for table `dialect`
--
ALTER TABLE `dialect`
  ADD PRIMARY KEY (`iddialect`),
  ADD UNIQUE KEY `dialect_UNIQUE` (`dialect`);

--
-- Indexes for table `favorite`
--
ALTER TABLE `favorite`
  ADD PRIMARY KEY (`idfavorite`),
  ADD KEY `fk_favorite_answer1_idx` (`answer_user_iduser`,`answer_forum_idforum`,`answer_forum_user_iduser`,`answer_forum_dialect_iddialect`);

--
-- Indexes for table `forum`
--
ALTER TABLE `forum`
  ADD PRIMARY KEY (`idforum`,`user_iduser`,`dialect_iddialect`),
  ADD KEY `fk_forum_user1_idx` (`user_iduser`),
  ADD KEY `fk_forum_dialect1_idx` (`dialect_iddialect`);

--
-- Indexes for table `library`
--
ALTER TABLE `library`
  ADD PRIMARY KEY (`translation_idtranslation`,`word_idword`,`word_dialect_iddialect`),
  ADD KEY `fk_translation_has_word_word1_idx` (`word_idword`,`word_dialect_iddialect`),
  ADD KEY `fk_translation_has_word_translation1_idx` (`translation_idtranslation`);

--
-- Indexes for table `translation`
--
ALTER TABLE `translation`
  ADD PRIMARY KEY (`idtranslation`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`iduser`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`);

--
-- Indexes for table `user_has_dialect`
--
ALTER TABLE `user_has_dialect`
  ADD PRIMARY KEY (`user_iduser`,`dialect_iddialect`),
  ADD KEY `fk_user_has_dialect_dialect1_idx` (`dialect_iddialect`),
  ADD KEY `fk_user_has_dialect_user1_idx` (`user_iduser`);

--
-- Indexes for table `word`
--
ALTER TABLE `word`
  ADD PRIMARY KEY (`idword`,`dialect_iddialect`),
  ADD KEY `fk_word_dialect1_idx` (`dialect_iddialect`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `answer`
--
ALTER TABLE `answer`
  MODIFY `user_iduser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `bookmark`
--
ALTER TABLE `bookmark`
  MODIFY `idbookmark` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `dialect`
--
ALTER TABLE `dialect`
  MODIFY `iddialect` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `favorite`
--
ALTER TABLE `favorite`
  MODIFY `idfavorite` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `forum`
--
ALTER TABLE `forum`
  MODIFY `idforum` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `library`
--
ALTER TABLE `library`
  MODIFY `translation_idtranslation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `translation`
--
ALTER TABLE `translation`
  MODIFY `idtranslation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `iduser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `user_has_dialect`
--
ALTER TABLE `user_has_dialect`
  MODIFY `user_iduser` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `word`
--
ALTER TABLE `word`
  MODIFY `idword` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `answer`
--
ALTER TABLE `answer`
  ADD CONSTRAINT `fk_user_has_forum_forum1` FOREIGN KEY (`forum_idforum`,`forum_user_iduser`,`forum_dialect_iddialect`) REFERENCES `forum` (`idforum`, `user_iduser`, `dialect_iddialect`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_has_forum_user1` FOREIGN KEY (`user_iduser`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `bookmark`
--
ALTER TABLE `bookmark`
  ADD CONSTRAINT `fk_bookmark_library1` FOREIGN KEY (`library_translation_idtranslation`,`library_word_idword`,`library_word_dialect_iddialect`) REFERENCES `library` (`translation_idtranslation`, `word_idword`, `word_dialect_iddialect`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `favorite`
--
ALTER TABLE `favorite`
  ADD CONSTRAINT `fk_favorite_answer1` FOREIGN KEY (`answer_user_iduser`,`answer_forum_idforum`,`answer_forum_user_iduser`,`answer_forum_dialect_iddialect`) REFERENCES `answer` (`user_iduser`, `forum_idforum`, `forum_user_iduser`, `forum_dialect_iddialect`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `forum`
--
ALTER TABLE `forum`
  ADD CONSTRAINT `fk_forum_dialect1` FOREIGN KEY (`dialect_iddialect`) REFERENCES `dialect` (`iddialect`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_forum_user1` FOREIGN KEY (`user_iduser`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `library`
--
ALTER TABLE `library`
  ADD CONSTRAINT `fk_translation_has_word_translation1` FOREIGN KEY (`translation_idtranslation`) REFERENCES `translation` (`idtranslation`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_translation_has_word_word1` FOREIGN KEY (`word_idword`,`word_dialect_iddialect`) REFERENCES `word` (`idword`, `dialect_iddialect`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_has_dialect`
--
ALTER TABLE `user_has_dialect`
  ADD CONSTRAINT `fk_user_has_dialect_dialect1` FOREIGN KEY (`dialect_iddialect`) REFERENCES `dialect` (`iddialect`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_has_dialect_user1` FOREIGN KEY (`user_iduser`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `word`
--
ALTER TABLE `word`
  ADD CONSTRAINT `fk_word_dialect1` FOREIGN KEY (`dialect_iddialect`) REFERENCES `dialect` (`iddialect`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
