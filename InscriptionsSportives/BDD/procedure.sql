DELIMITER |
CREATE PROCEDURE Competitions(IN candi INT)      

BEGIN
    SELECT NomCompet
    FROM Compétition co, Candidat ca, Inscrire i
	WHERE ca.IdCandidat=i.IdCandidat
	AND co.IdCompet=i.IdCompet
	AND ca.IdCandidat=candi;
END|


DELIMITER |
CREATE PROCEDURE Candidatname(IN candi INT)      

BEGIN
    SELECT Nom
    FROM Personne
	WHERE IdCandidat=candi;
END|


DELIMITER |
CREATE PROCEDURE CandidatInscrit()      

BEGIN
    SELECT NomCompet
    FROM Compétition co, Candidat ca, Inscrire i
	WHERE ca.IdCandidat=i.IdCandidat
	AND co.IdCompet=i.IdCompet;
END|


DELIMITER |
CREATE PROCEDURE NomCompetition(IN competi INT)      

BEGIN
    SELECT NomCompet
    FROM Compétition
	WHERE IdCompet=competi;
END|


DELIMITER |
CREATE PROCEDURE DateCloture(IN dateClo INT)      

BEGIN
    SELECT DateCloture
    FROM Compétition
	WHERE IdCompet=dateClo;
END|


DELIMITER |
CREATE PROCEDURE Membres(IN equipe VARCHAR)      

BEGIN
    SELECT IdCandidat, Nom, Prenom, Mail
    FROM Personne p, Equipe e, Candidat c
	WHERE p.IdCandidat=e.IdCandidat
	AND NomEquipe=equipe;
END|


DELIMITER |
CREATE PROCEDURE Candidats()      

BEGIN
    SELECT Nom, Prenom
    FROM Personne;
END|


DELIMITER |
CREATE PROCEDURE Competitions()      

BEGIN
    SELECT NomCompet
    FROM Compétition;
END|


DELIMITER |
CREATE PROCEDURE Equipes(IN equipes INT)      

BEGIN
    SELECT NomEquipe
    FROM Personne p, Equipe e, Candidat c
	WHERE p.IdCandidat=e.IdCandidat
	AND IdCandidat=equipes;
END|


DELIMITER |
CREATE PROCEDURE Mail(IN mail INT)      

BEGIN
    SELECT Mail
    FROM Personne
	WHERE IdCandidat=mail;
END|


DELIMITER |
CREATE PROCEDURE Prenom(IN prenom INT)      

BEGIN
    SELECT prenom
    FROM Personne
	WHERE IdCandidat=prenom;
END|