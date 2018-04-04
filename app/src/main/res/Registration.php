<?php

	$db_name = "mysql:host=localhost;dbname=aloalo";
	$mysql_user = "root";
	$mysql_password = "";
	$nom = $_POST['nom'];
	$prenom = $_POST['prenom'];
	$pseudo = $_POST['pseudo'];
	$mail = $_POST['mail'];
	$telephone = $_POST['telephone'];
	$motdepasse = $_POST['motdepasse'];


	/* Il faudra intégrer ensuite un autre contrôle de données ici */

	/* Simple requete d'insertion à la base de données */

	try{
		$connection = new PDO($db_name, $mysql_user, $mysql_password);
		$query = $connection -> prepare("insert into users(nom, prenom, pseudo, mail, telephone, motdepasse) values ( :nom , :prenom, :pseudo, :mail, :telephone, :motdepasse)");


		$query-> bindParam(':nom', $nom);
		$query-> bindParam(':prenom', $prenom);
		$query-> bindParam(':pseudo', $pseudo);
		$query-> bindParam(':mail', $mail);
		$query-> bindParam(':telephone', $telephone);
		$query-> bindParam(':motdepasse', $motdepasse);


		$queryok = $query -> execute();

  		 	if($queryok){
  		 		echo "true";
  		 	}else{
  		 		echo "false";
  		 	}

	    }catch(PDOException $e){
		   echo "failure";
	    }
?>