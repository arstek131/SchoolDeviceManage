<?php

	require "init.php";

	$NumInventario = $_POST["NumInventario"];

	$sql = "select QrCode from cespite where NumInventario = '".$NumInventario."';";
	//$sql = "select QrCode from cespite where NumInventario = '49464';";
	$result = mysqli_query($con, $sql);

	if(mysqli_num_rows($result)==0)
	{
		$code = "reg_failed";
		$message = "Cespite inesistente!";

		$err = array("code"=>$code, "message"=>$message);
		echo json_encode($err);
	}
	else
	{
		$rowResult = $result->fetch_all(MYSQLI_ASSOC)[0];
		$QrValue = $rowResult[QrCode];
		echo json_encode(array('code'=>"success", 'message'=>$QrValue));
		//echo $QrValue;
	}

	

?>