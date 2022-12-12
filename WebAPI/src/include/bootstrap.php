<?php

const PROJECT_ROOT_PATH = __DIR__ . "/../";
 
require_once PROJECT_ROOT_PATH . "include/config-log.php";
require_once PROJECT_ROOT_PATH . "include/config-db.php";
require_once PROJECT_ROOT_PATH . "include/config-jwt.php";

require_once PROJECT_ROOT_PATH . "ResponseHandler.php";
require_once PROJECT_ROOT_PATH . "dataSource/DatabaseAccessObject.php";
require_once PROJECT_ROOT_PATH . "controller/BaseController.php";
require_once PROJECT_ROOT_PATH . "controller/JwtHandler.php";

// Autoload composer dependencies
//require_once PROJECT_ROOT_PATH . '../vendor/autoload.php';
require_once 'vendor/autoload.php';
