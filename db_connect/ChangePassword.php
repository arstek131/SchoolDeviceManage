<?php
	require "init.php";


	$Username = $_POST["Username"];
	$Password = $_POST["Password"];
	$OldPassword = $_POST["OldPassword"];
	

/*
	$Username = "arsone";
	$Password = "lovepizza";
*/

	$sqlGetPW = "SELECT user.password from user
			where user.username = '".$Username."';";
			

	$resultsqlGetPW = mysqli_query($con, $sqlGetPW);
	$rowsqlGetPW = $resultsqlGetPW->fetch_all(MYSQLI_ASSOC)[0];
	$valuesqlGetPW = $rowsqlGetPW[password];
	//echo $valuesqlGetPW;

	if($OldPassword != $valuesqlGetPW)
	{
		$code = "reg_failed";
		$message = "La password attuale è sbagliata!";
		$err = array("code"=>$code, "message"=>$message);
		echo json_encode($err);
	}

	else
	{
		if($valuesqlGetPW == $Password)
		{
			$code = "reg_failed";
			$message = "La nuova password deve essere diversa dalla vecchia!";
			$err = array("code"=>$code, "message"=>$message);
			echo json_encode($err);
		}

		else
		{
			$sql = mysqli_prepare($con, "UPDATE user SET password = '".$Password."' WHERE username = '".$Username."'");

	        mysqli_stmt_bind_param($sql, "ss", $Password, $Username);

			mysqli_stmt_execute($sql);
	        $code = "reg_success";
			$message = "Modifica avvenuta con successo!";

			$err = array("code"=>$code, "message"=>$message);
			echo json_encode($err);
		}
	}

	

