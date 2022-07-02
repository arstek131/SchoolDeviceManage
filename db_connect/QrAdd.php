<?php

	require "init.php";


	$NumInventario = $_POST["NumInventario"];
    $NomeCespite = $_POST["NomeCespite"];
    $Descrizione = $_POST["Descrizione"];
    $Foto = $_POST["Foto"];
    $QrCode = $_POST["QrCode"];
    $Categoria = $_POST["Categoria"];
    $Aula = $_POST["Aula"];
    $TipoAula = $_POST["TipoAula"];
    $Username = $_POST["Username"];
    $CodUser_id = $_POST["CodUser_id"];

    $sql = "select * from cespite where NumInventario like '".$NumInventario."';";
	$result = mysqli_query($con, $sql);

	if(mysqli_num_rows($result)>0)
	{
		$code = "reg_failed";
		$message = "Cespite già presente!";

		$err = array("code"=>$code, "message"=>$message);
		echo json_encode($err);
	}
    else
    {
    	//controllo per la ricerca dell'id della categoria FK-> CodIdC
    	 $sql2Categoria = "select IdC from categoria where categoria.Codice = '".$Categoria."';";
    	 $result2Categoria = mysqli_query($con, $sql2Categoria);
    	 $row2Categoria = $result2Categoria->fetch_all(MYSQLI_ASSOC)[0];
    	 $value2Categoria = $row2Categoria[IdC];

    	 //controllo per la ricerca dell'aula FK-> CodIdA
    	 $sql3Aula = "select IdA from aula where aula.IdA = '".$Aula."';";
    	 $result3Aula = mysqli_query($con, $sql3Aula);
    	 if(mysqli_num_rows($result3Aula)==0)
    	 {
    	 	$sqlInserAula = mysqli_prepare($con, "INSERT INTO aula (IdA, Tipo) VALUES (?, ?)");
    	 	mysqli_stmt_bind_param($sqlInserAula, "is", $Aula, $TipoAula);
    	 	mysqli_stmt_execute($sqlInserAula);
    	 }

    	 //controllo per la ricerca dell'utente CodUser_id
    	 $sql4User = "select user_id from user where user.username = '".$Username."';";
    	 $result4User = mysqli_query($con, $sql4User);
         //print_r ($result4User);
    	 $row4User = $result4User->fetch_all(MYSQLI_ASSOC)[0];
    	 $value4User = $row4User[user_id];



    	$sql = mysqli_prepare($con, "INSERT INTO cespite (NumInventario, Nome, Descrizione, Foto, QrCode, CodIdC, CodIdA, CodUser_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        mysqli_stmt_bind_param($sql, "issssiii", $NumInventario, $NomeCespite, $Descrizione, $Foto, $QrCode, $value2Categoria, $Aula, $value4User);

		mysqli_stmt_execute($sql);
        $code = "reg_success";
		$message = "Registrazione avvenuta con successo!";

		$err = array("code"=>$code, "message"=>$message);
		echo json_encode($err);
    }

?>