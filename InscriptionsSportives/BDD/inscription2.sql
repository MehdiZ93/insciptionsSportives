--
-- Base de données :  `inscription2`
--

-- --------------------------------------------------------

--
-- Structure de la table `appartenir`
--

CREATE TABLE IF NOT EXISTS `appartenir` (
  `IdCandidat` int(11) NOT NULL,
  `IdCandidat_1` int(11) NOT NULL,
  PRIMARY KEY (`IdCandidat`,`IdCandidat_1`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `candidat`
--

CREATE TABLE IF NOT EXISTS `candidat` (
  `IdCandidat` int(11) NOT NULL,
  PRIMARY KEY (`IdCandidat`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `competition`
--

CREATE TABLE IF NOT EXISTS `competition` (
  `IdCompet` int(11) NOT NULL AUTO_INCREMENT,
  `NomCompet` varchar(25) DEFAULT NULL,
  `DateCompet` date DEFAULT NULL,
  `EnEquipe` tinyint(1) DEFAULT NULL,
  `DateCloture` date DEFAULT NULL,
  PRIMARY KEY (`IdCompet`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `competition`
--

INSERT INTO `competition` (`IdCompet`, `NomCompet`, `DateCompet`, `EnEquipe`, `DateCloture`) VALUES
(1, 'Paris', NULL, 0, '2016-04-12');

-- --------------------------------------------------------

--
-- Structure de la table `equipe`
--

CREATE TABLE IF NOT EXISTS `equipe` (
  `NomEquipe` varchar(25) DEFAULT NULL,
  `IdCandidat` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IdCandidat`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `equipe`
--

INSERT INTO `equipe` (`NomEquipe`, `IdCandidat`) VALUES
('basket', 1),
('Polo', 2);

-- --------------------------------------------------------

--
-- Structure de la table `inscrire`
--

CREATE TABLE IF NOT EXISTS `inscrire` (
  `IdCandidat` int(11) NOT NULL,
  `IdCompet` int(11) NOT NULL,
  PRIMARY KEY (`IdCandidat`,`IdCompet`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE IF NOT EXISTS `personne` (
  `Nom` varchar(25) DEFAULT NULL,
  `Prenom` varchar(25) DEFAULT NULL,
  `Mail` varchar(25) DEFAULT NULL,
  `IdCandidat` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IdCandidat`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `personne`
--

INSERT INTO `personne` (`Nom`, `Prenom`, `Mail`, `IdCandidat`) VALUES
('titi', 'toto', 'toto@titi.fr', 1),
('henin', 'jerome', 'jer5@live.fr', 2);