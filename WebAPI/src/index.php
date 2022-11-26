<?php

require __DIR__ . "/include/bootstrap.php";

$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
$uri = explode('/', $uri);

$response_handler = new ResponseHandler();

if (isset($uri[1]) && $uri[1] == 'api' && isset($uri[2])) {
	switch ($uri[2]) {
		case 'user':
			require PROJECT_ROOT_PATH . "/controller/UserController.php";
			$userController = new UserController();
			$userController->processRequest();
			exit;
		case 'stations':
			if (!isset($uri[3]))
				$response_handler->error(404);
			
			require PROJECT_ROOT_PATH . "/controller/MeasurementController.php";
			require PROJECT_ROOT_PATH . "/controller/PictureController.php";
			require PROJECT_ROOT_PATH . "/controller/StationController.php";
			
			$measurementController = new MeasurementController();
			$pictureController = new PictureController();
			$stationController = new StationController();
			
			if (is_numeric($uri[3])) {
				if (!isset($uri[4]))
					$response_handler->error(404);
				
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
						$response_handler->error(404);
					}
					exit;
				case 'measurements':
					if (!isset($uri[4])) {
						$measurementController->processRequest();
					} else if ($uri[4] == 'storage') {
						$stationController->processRequest();
					} else {
						$response_handler->error(404);
					}
					exit;
				case 'status':
					$stationController->processRequest();
					exit;
				default:
					$response_handler->error(404);
			}
			exit;
		default:
			$response_handler->error(404);
	}
} else {
	$response_handler->error(404);
}

