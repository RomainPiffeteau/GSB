----------------------------------------------------------------------------------------------------------------------------------------------
--CREATION DE LA BASE------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------
USE master
GO
----------------------------------------------------------------------------------------------------------------------------------------------
CREATE DATABASE gsbjm
GO
----------------------------------------------------------------------------------------------------------------------------------------------
--CREATION DES TABLES-----------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------
USE gsbjm
GO
----------------------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE forme(
	identifiant INT NOT NULL IDENTITY,
	nom VARCHAR(50),
	CONSTRAINT pk_forme PRIMARY KEY (identifiant)
)
GO
----------------------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE medicament(
	identifiant INT NOT NULL IDENTITY,
	nom VARCHAR(50) UNIQUE,
	dateBrevet datetime,
	dateAMM datetime,
	dateRetrait datetime,
	idForme INT NOT NULL,
	CONSTRAINT pk_medicament PRIMARY KEY (identifiant),
	CONSTRAINT fk_medicament_forme FOREIGN KEY (idForme) REFERENCES forme (identifiant)
)
GO
----------------------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE effet(
    identifiant INT NOT NULL IDENTITY,
    grade INT,
    description VARCHAR(50),
    CONSTRAINT pk_effet PRIMARY KEY (identifiant)
)
GO

----------------------------------------------------------------------------------------------------------------------------------------------
--INSERTION DES TUPLES DANS LA BASE-------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO forme (nom) VALUES ('Comprimé');
INSERT INTO forme (nom) VALUES ('Gélule');
INSERT INTO forme (nom) VALUES ('Solution');
INSERT INTO forme (nom) VALUES ('Poudre');
----------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('RETROVIR','12/5/1995','27/11/1996',NULL,2,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('ALKERAN','16/2/1997','21/11/2001',NULL,3,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('EPIVIR',NULL,'18/4/2010',NULL,3,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('FLOLAN',NULL,'16/1/2003',NULL,4,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('HYCAMTIN',NULL,'5/10/2000',NULL,4,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('ESKAZOLE','7/2/2002','4/10/2008',NULL,1,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('VENTOLINE',NULL,'5/2/2013',NULL,3,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('ZIAGEN','3/3/2001','15/10/2006',NULL,1,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('ZEFFIX',NULL,'11/2/2004',NULL,1,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('TELZIR','15/3/2009',NULL,NULL,1,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('ATRIANCE','14/5/2003','25/11/2009',NULL,3,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('VOLIBRIS','17/8/2004','6/8/2008',NULL,1,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('LAMIVUDINE',NULL,'9/1/2009',NULL,1,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('TAFINLAR','23/3/2007','18/4/2008',NULL,2,1);
INSERT INTO medicament (nom,dateBrevet,dateAMM,dateRetrait,idForme) VALUES ('MEDIATOR','28/7/1967','12/1/1971',NULL,1,1);
----------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO effet (grade,description) VALUES (1,'Pas d''effet');
GO
----------------------------------------------------------------------------------------------------------------------------------------------
--CREATION DES PROCEDURES STOCKEES ET TRIGGERS----------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------------------------------------------------------------
--CREATION DES UTILISATEURS ET DES DROITS--------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------
EXEC sp_addlogin 'JeanMedicament', 'zouzou', 'gsbjm'
GO
EXEC sp_grantdbaccess 'JeanMedicament'
GO

REVOKE SELECT,INSERT,UPDATE,DELETE TO JeanMedicament
GRANT SELECT,INSERT,UPDATE ON medicament TO JeanMedicament
GRANT SELECT ON forme TO JeanMedicament
GRANT SELECT,INSERT ON effet TO JeanMedicament
GO