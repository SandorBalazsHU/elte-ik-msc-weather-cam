<?php

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
			if (!isset($uri[3])) code404();
			
			require PROJECT_ROOT_PATH . "/controller/MeasurementController.php";
			require PROJECT_ROOT_PATH . "/controller/PictureController.php";
			require PROJECT_ROOT_PATH . "/controller/StationController.php";
			
			$measurementController = new MeasurementController();
			$pictureController = new PictureController();
			$stationController = new StationController();
			
			if (is_numeric($uri[3])) {
				if (!isset($uri[4])) code404();

				switch ($uri[4]) {
					case 'measurements':
						$measurementController->processRequest();
						exit;
					case 'pictures':
						$pictureController->processRequest();
						exit;
					default:
						$stationController->processRequest();
						exit;
					}
			}

			switch ($uri[3]) {
				case 'pictures':
					if (!isset($uri[4])) {
						$pictureController->processRequest();
					} else if ($uri[4] == 'storage') {
						$stationController->processRequest();
					} else {
						code404();
					}
					exit;
				case 'measurements':
					if (!isset($uri[4])) {
						$measurementController->processRequest();
					} else if ($uri[4] == 'storage') {
						$stationController->processRequest();
					} else {
						code404();
					}
					exit;
				case 'status':
					$stationController->processRequest();
				default:
					code404();
			}
			exit;
		default:
			code404();
	}
} else {
	code404();
}

function code404() {
	http_response_code(404);
	exit;
}
