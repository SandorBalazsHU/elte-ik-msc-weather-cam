<?php
function f_rand($min=0,$max=1,$mul=1000000){
    if ($min>$max) return false;
    return mt_rand($min*$mul,$max*$mul)/$mul;
}
$array = array(
    "time" => date("h:i:s"),
    "date" => date("Y.m.d."),
	"temp" => f_rand(-30,50,10),
	"pressure" => f_rand(700,1500,10),
	"humidity" => f_rand(0,100,10),
	
);

echo(json_encode($array));
?>