<?php
include ('config.php');
$query = "select * from event";
$result = mysql_query ( $query );
while ( $row = mysql_fetch_array ( $result ) ) {
	$data [] = array (
			'eventid' => $row ['iEventId'],
			'eventname' => $row ['vEventType'] 
	);
}
$data1 ['eventlist'] = $data;
echo json_encode ( $data1 );
?>