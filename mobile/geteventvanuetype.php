<?php
include ('config.php');
$query = "select * from eventlocation";
$result = mysql_query ( $query );
while ( $row = mysql_fetch_array ( $result ) ) {
	$data [] = array (
			'vanueid' => $row ['iLocationId'],
			'vanuename' => $row ['vLocationName'] 
	);
}
$data1 ['vanuelist'] = $data;
echo json_encode ( $data1 );
?>