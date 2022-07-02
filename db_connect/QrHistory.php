<?php
	require "init.php";

	$Username = $_POST["Username"];


	//query for get user ID from android app
	$sqlGetID= "SELECT user.user_id from user
				INNER JOIN scansione ON user_id = CodUser
				where user.username = '".$Username."';";

	$resultsqlGetID = mysqli_query($con, $sqlGetID);
	//print_r ($resultsqlGetID);
	$rowsqlGetID = $resultsqlGetID->fetch_all(MYSQLI_ASSOC)[0];
	$valuesqlGetID = $rowsqlGetID[user_id];
	//echo $valuesqlGetID;
	



	$sql =  "SELECT CodNumInventario, cespite.Nome, cespite.DtCatalogazione, cespite.CodIdA, user.username
	from scansione
	INNER JOIN cespite ON CodNumInventario = NumInventario
	INNER JOIN user ON CodUser_id = user_id
	where CodUser = $valuesqlGetID";

/*
 		
		$rows = array();
		while($r = mysqli_fetch_assoc($sql)) 
		{
		    $rows[] = $r;
		}
		print json_encode($rows);
	*/


	if ($result = mysqli_query($con, $sql)) 
	{
	    while ($row = mysqli_fetch_assoc($result))
	    {
	       $resultarray[] = $row;
	    }

	  echo json_encode(array('dates'=>$resultarray));
	}
		


	