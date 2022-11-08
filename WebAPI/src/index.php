<?php

require __DIR__ . "/include/log-config.php";
require __DIR__ . "/include/bootstrap.php";

$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
$uri = explode('/', $uri);

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
			http_response_code(404);
	}
} else {
	http_response_code(404);
}
