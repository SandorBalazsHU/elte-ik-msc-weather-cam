<?php

require __DIR__ . "/include/bootstrap.php";
require_once PROJECT_ROOT_PATH . "/controller/BaseController.php";

$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
$uri = explode('/', $uri);

print_r($uri);

if (isset($uri[1]) && $uri[1] == 'api' && isset($uri[2])) {
    switch ($uri[2]) {
        case 'user':
            require PROJECT_ROOT_PATH . "/controller/UserController.php";
			$userController = new UserController();
			$userController->processRequest();
			exit;
        case 'stations':
            // TODO use implement StationController
//			exit;
        default:
            $baseController = new BaseController();
            $baseController->error404();
    }
} else {
    $baseController = new BaseController();
    $baseController->error404();
}

if ((isset($uri[2]) && $uri[2] != 'user') || !isset($uri[3])) {
}

require PROJECT_ROOT_PATH . "/controller/BaseController.php";

$objFeedController = new BaseController();
$strMethodName = $uri[3] . 'Action';
$objFeedController->{$strMethodName}();
