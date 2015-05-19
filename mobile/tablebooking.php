<?php
include ('config.php');
if (isset ( $_POST ['tdate'] ) && ($_POST ['ttime']) && ($_POST ['member'])) {
	$tbookdate = $_POST ['tdate'];
	$tbooktime = $_POST ['ttime'];
	$member = $_POST ['member'];
	$query = "insert into tablebookdetails(tsTableBookDate,tsTableBookTime,iUserMembers)values('$tbookdate','$tbooktime','$member')";
	if (mysql_query ( $query )) {
		echo 'booking successfully';
	} else {
		echo 'insert correct data';
	}
}
?>