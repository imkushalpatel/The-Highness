<?php
include ('config.php');
if (isset ( $_POST ['fname'] ) && ($_POST ['lname']) && ($_POST ['email']) && ($_POST ['pass']) && ($_POST ['mobile']) && ($_POST ['address']) && ($_POST ['gender'])) {
	$firstname = $_POST ['fname'];
	$lastname = $_POST ['lname'];
	$email = $_POST ['email'];
	$password = $_POST ['pass'];
	$address = $_POST ['address'];
	$mobile = $_POST ['mobile'];
	$gender = $_POST ['gender'];
	$query = "insert into userdetail(iUserId,vUserFName,vUserLName,vUserEmail,vUserPass,vUserAdd,iUserMob,vUserGender)values('','$firstname','$lastname','$email','$password','$address','$mobile','$gender')";
	// $result = mysql_query ( $query );
	// $row=mysql_fetch_array($result);
	if (mysql_query ( $query )) {
		$data ['status'] = true;
		echo json_encode ( $data );
	} else {
		$data ['status'] = false;
		echo json_encode ( $data );
	}
}

?>			