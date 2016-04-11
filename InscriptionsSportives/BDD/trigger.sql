DROP trigger deletecandi;

DELIMITER @
CREATE TRIGGER deletecandi(IN candi INT) After delete
ON Candidat FOR EACH ROW
BEGIN
	DELETE FROM 'candidat'
	WHERE IdCandidat=candi;
END @
DELIMITER ;


DROP trigger setNom;

DELIMITER @
CREATE TRIGGER setNom(IN candi INT, IN newname VARCHAR) After update
ON candidat FOR EACH ROW
BEGIN
	update Candidat
	SET nom = newname
	where IdCandidat=candi;
END @
DELIMITER ;


DROP trigger deletCompet;

DELIMITER @
CREATE TRIGGER deletCompet(IN compet INT) After delete
ON compétition FOR EACH ROW
BEGIN
	DELETE FROM 'compétition'
	where IdCompet=compet;
END @
DELIMITER ;


DROP trigger Datecloture;

DELIMITER @
CREATE TRIGGER DateCloture(IN dateclo INT, IN compet INT) After update
ON Compétition FOR EACH ROW
BEGIN
	update Compétition
	SET DateCloture = dateclo
	where IdCompet=compet;
END @
DELIMITER ;


DROP trigger deletCandidat;

DELIMITER @
CREATE TRIGGER deletCandidat(IN candi INT) After delete
ON Candidat FOR EACH ROW
BEGIN
	DELETE FROM 'Candidat'
	where IdCandidat=candi;
END @
DELIMITER ;


DROP trigger setMail;

DELIMITER @
CREATE TRIGGER setMail(IN mail VARCHAR, IN nom INT) After update
ON Personne FOR EACH ROW
BEGIN
	update Personne
	SET Mail = mail
	where IdCandidat=nom;
END @
DELIMITER ;


DROP trigger setPrenom;

DELIMITER @
CREATE TRIGGER setPrenom(IN prenom VARCHAR, IN nom INT) After update
ON Personne FOR EACH ROW
BEGIN
	update Personne
	SET prenom = prenom
	where IdCandidat=nom;
END @
DELIMITER ;