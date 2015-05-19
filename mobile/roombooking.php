<?php
include ('config.php');
if (isset ( $_POST ['rtype'] ) && ($_POST ['checkindate']) && ($_POST ['checkoutdate'])) {
	$roomtype = $_POST ['rtype'];
	$checkindate = $_POST ['checkindate'];
	$checkoutdate = $_POST ['checkoutdate'];
	$query = "insert into roombookdetails(vRCheckInDate,vRCheckOutDate,)values('$tbookdate','$tbooktime','$member')";
	if (mysql_query ( $query )) {
		echo "booking successfully";
	} else
		echo "insert correct data";
}
?>