<?php
include ('config.php');
if (isset ( $_POST ['user'] ) && isset ( $_POST ['pass'] )) {
	$username = $_POST ['user'];
	$password = $_POST ['pass'];
	$query = "select * from userdetail where vUserEmail='$username' && vUserPass='$password'";
	$result = mysql_query ( $query );
	// $row=mysql_fetch_array($result);
	if ($row = mysql_fetch_array ( $result )) {
		$data ['status'] = true;
		$data ['message'] = 'Login Success';
		$data ['userid'] = $row ['iUserId'];
		$data ['fname'] = $row ['vUserFName'];
		$data ['lname'] = $row ['vUserLName'];
		$data ['email'] = $row ['vUserEmail'];
		echo json_encode ( $data );
	} else {
		$data ['status'] = false;
		$data ['message'] = 'Incorrect data';
		echo json_encode ( $data );
	}
} else {
	$data ['status'] = false;
	$data ['message'] = 'invalid';
	echo json_encode ( $data );
}
?>