<?php
header("Content-type: image/gif");

$content = file_get_contents('this_is_fine.jpg');
echo base64_encode($content);