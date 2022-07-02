<?php

	require "init.php";


	$name = $_POST["name"];
	$username = $_POST["username"];
	$password = $_POST["password"];
    $ruolo = $_POST["ruolo"];

    $sql = "select * from user where username like '".$username."';";

	$result = mysqli_query($con, $sql);

	if(mysqli_num_rows($result)>0)
	{
		$code = "reg_failed";
		$message = "Utente gia  esistente!";

		$err = array("code"=>$code, "message"=>$message);
		echo json_encode($err);
	}
	else
	{
        $sql2 = "select IdR from ruolo where ruolo.Nome = '".$ruolo."';";
        $result2 = mysqli_query($con, $sql2);
        $row2 = $result2->fetch_all(MYSQLI_ASSOC)[0];
        $value2=$row2[IdR];
        

        $result = mysqli_query($con, $sql);
		$sql = mysqli_prepare($con, "INSERT INTO user (name, username, password, CodIdR) VALUES (?, ?, ?, ?)");
		mysqli_stmt_bind_param($sql, "sssi", $name, $username, $password, $value2);
		mysqli_stmt_execute($sql);
		$code = "reg_success";
		$message = "Registrazione avvenuta con successo!";

		$err = array("code"=>$code, "message"=>$message);
		echo json_encode($err);

        
	}


?>