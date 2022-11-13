<?php

const PROJECT_ROOT_PATH = __DIR__ . "/../";
 
require_once PROJECT_ROOT_PATH . "/include/config-log.php";
require_once PROJECT_ROOT_PATH . "/include/config-db.php";
require_once PROJECT_ROOT_PATH . "/include/config-jwt.php";
require_once PROJECT_ROOT_PATH . "/controller/BaseController.php";
require_once PROJECT_ROOT_PATH . "/controller/JwtHandler.php";
require_once PROJECT_ROOT_PATH . "/dataSource/DatabaseAccessObject.php";

// Autoload composer dependencies
require 'vendor/autoload.php';
