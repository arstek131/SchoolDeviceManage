<?php

	require "init.php";
	header('Content-type : bitmap; charset=utf-8');

	$NumInventario = $_POST["NumInventario"];

	if(isset($_POST["encoded_string"]))
	{
		$encoded_string = $_POST["encoded_string"];
		$image_name = $_POST["image_name"];

		$decoded_string = base64_decode($encoded_string);

		$path = 'images/'.$image_name;

		$file = fopen($path, 'wb');

		$is_written = fwrite($file, $decoded_string);
		fclose($file);

		if($is_written>0)
		{
			$query = "insert into foto(CodNumInventario, Nome, Path) values('$NumInventario', '$image_name', '$path);";
			$result = mysqli_query($con, $query);

			if($result)
			{
				echo "success";
			}
			else
			{
				echo "failed";
			}


		}


	}


?>