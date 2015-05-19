<?php
include ('config.php');
$query = "select * from roomtype";
$result = mysql_query ( $query );
while ( $row = mysql_fetch_array ( $result ) ) {
	$data [] = array (
			'roomid' => $row ['iRoomTypeId'],
			'roomname' => $row ['vRoomTypeName'] 
	);
}
$data1 ['roomtypelist'] = $data;
echo json_encode ( $data1 );
?>