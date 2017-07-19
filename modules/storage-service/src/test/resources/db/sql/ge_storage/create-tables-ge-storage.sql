------------------ Création de la table storage ------------------
CREATE TABLE storage
(
	storageId varchar(18) NOT NULL, 								--Storage id
	originalfilename varchar(100),									--Storage original file name
	tray varchar(20) NOT NULL,									--Storage status
	path varchar(255) NOT NULL,										--Storage relative path
	dateCreation timestamp(6) DEFAULT current_timestamp NOT NULL,	--Storage date
	referenceId varchar(18),										--Storage id reference,
  	CONSTRAINT storage_pkey PRIMARY KEY (storageId) 
);

