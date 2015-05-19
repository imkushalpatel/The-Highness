<?php
include 'config.php';
if (isset ( $_POST ['email'] )) {
	$email = $_POST ['email'];
	$query = "select * from userdetail where vUserEmail='$email'";
	$result = mysql_query ( $query );
	if ($row = mysql_fetch_array ( $result )) {
		
		$data ['status'] = false;
		echo json_encode ( $data );
	} else {
		$data ['status'] = true;
		echo json_encode ( $data );
	}
} else {
	$data ['status'] = false;
	echo json_encode ( $data );
}
?>