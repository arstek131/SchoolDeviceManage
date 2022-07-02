<?php
	require "init.php";
	header("Content-type: application/json");

	$qrcode = $_POST["qrcode"];
	$Username = $_POST["Username"];
    $CodUser_id = $_POST["CodUser_id"];
    $NumInventario = $_POST["NumInventario"];

  $sql = "select cespite.*, aula.Tipo, categoria.Codice as A_H, categoria.Descrizione as Descrizione_Categoria, user.name, ruolo.Nome as Ruolo
          from cespite
          INNER JOIN aula ON CodIdA = IdA 
          INNER JOIN categoria ON CodIdC = IdC
          INNER JOIN user ON CodUser_id = user_id
          INNER JOIN ruolo ON CodIdR = IdR
          where QrCode = '$qrcode';";

	$result = mysqli_query($con, $sql);
	$response = array();
	
	if(mysqli_num_rows($result)==0) // il qr non esiste nel db
	{
		$response["data"] = NULL;
		$response["error_code"] = "1"; 
	}
	else
	{
		$row = $result->fetch_all(MYSQLI_ASSOC)[0];
		$response["data"] = $row;
		$response["error_code"] = "0";

		//controllo per la ricerca dell'utente CodUser_id (da mettere poi in scansione)
    	 $sql4User = "select user_id from user where user.username = '".$Username."';";
    	 $result4User = mysqli_query($con, $sql4User);
         //print_r ($result4User);
    	 $row4User = $result4User->fetch_all(MYSQLI_ASSOC)[0];
    	 $value4User = $row4User[user_id];

    	 //controllo per la ricerca del NumInventario (da mettere poi in scansione)
    	 $sql5NumInventario = "select NumInventario from cespite where QrCode = '$qrcode';";
    	 $result5NumInventario = mysqli_query($con, $sql5NumInventario);
    	 $row5NumInventario = $result5NumInventario->fetch_all(MYSQLI_ASSOC)[0];
    	 $value5NumInventario = $row5NumInventario[NumInventario];


		$sqlScansione = mysqli_prepare($con, "INSERT INTO scansione (CodUser, CodNumInventario) VALUES (?, ?)");
		mysqli_stmt_bind_param($sqlScansione, "ii", $value4User, $value5NumInventario);
		mysqli_stmt_execute($sqlScansione);
	}

	echo json_encode($response);
?>