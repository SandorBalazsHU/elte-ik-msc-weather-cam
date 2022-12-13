<?php
require_once PROJECT_ROOT_PATH . "/dataSource/UserDao.php";
require_once PROJECT_ROOT_PATH . "/dataSource/StationDao.php";
require_once PROJECT_ROOT_PATH . "/dataSource/MeasurementDao.php";
require_once PROJECT_ROOT_PATH . "/dataSource/PictureDao.php";
require_once PROJECT_ROOT_PATH . "/dataSource/JwtDao.php";

class UserController extends BaseController {
	private UserDao $userDao;
	private StationDao $stationDao;
	private MeasurementDao $measurementDao;
	private PictureDao $pictureDao;
	private JwtDao $jwtDao;
	
	public function __construct() {
		parent::__construct();
		
		$this->userDao = new UserDao();
		$this->stationDao = new StationDao();
		$this->measurementDao = new MeasurementDao();
		$this->pictureDao = new PictureDao();
		$this->jwtDao = new JwtDao();
	}
	
	protected function processNoJwt(string $method, array $uri, array $params, array $body) {
		if ($method == 'GET' && isset($uri[3]) && $uri[3] == 'login') {
			$this->login($params);
		}
		if ($method == 'POST' && isset($uri[3]) && $uri[3] == 'register') {
			$this->register($params);
		}
	}
	
	#region get
	
	protected function get(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		if (!isset($uri[3])) {
			$this->getUser($user_id);
			return;
		}
		
		switch ($uri[3]) {
			case 'logout':
				$this->logout($jwt_token);
				break;
			case 'stations':
				$this->getStations($user_id);
				break;
		}
	}
	
	private function login($params) {
		if (empty($params['username']) || empty($params['password'])) {
			$this->error(403);
		}
		
		$user = $this->userDao->getUserByUnameAndPassword($params['username'], $params['password']);
		
		if (empty($user)) {
			$this->error(401);
			return;
		}
		
		try {
			$token = $this->jwt->getToken($user['user_id']);
		} catch (Exception $e) {
			$this->error(400);
			return;
		}
		
		exit($token->toString());
	}
	
	private function getUser(int $user_id) {
		$user = $this->userDao->getUserById($user_id);
		if (empty($user)) {
			$this->error(400);
		}
		
		$this->sendJson($user);
	}
	
	private function logout(string $jwt_token) {
		$expiration = $this->jwt->getExpiration($jwt_token) ?:
			(new DateTime())->modify('+1 day');
		$result = $this->jwtDao->blacklistToken($jwt_token, $expiration);
		
		if ($result) {
			$this->response(200);
		} else {
			$this->error(400);
		}
	}
	
	private function getStations(int $user_id) {
		$result = $this->stationDao->getStationsOfUser($user_id);
		
		if ($result) {
			$this->sendJson($result);
		} else {
			$this->error(404);
		}
	}
	
	#endregion
	#------------------------------------------------------------------------------------------------------------------
	#region post
	
	protected function post(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		switch ($uri[3]) {
			case 'stations':
				if (empty($uri[4])) $this->error(404);
				$this->createStation($uri, $user_id, $uri[4]);
				break;
		}
	}
	
	private function register(array $params) {
		if (empty($params['username']) || empty($params['password'])) {
			$this->error(400);
		}
		
		$username = $params['username'];
		$password = $params['password'];
		
		$existing_user = $this->userDao->getUserByUname($username);
		if (!empty($existing_user)) {
			$this->error(409);
		}
		
		$result = $this->userDao->insertUser($username, $password);
		
		if ($result) {
			$this->response(200);
		} else {
			$this->error(400);
		}
	}
	
	private function createStation(array $uri, int $user_id, string $station_name, int $depth = 0) {
		if ($depth >= 10) $this->error(500);
		
		$api_key = $this->generateApiKey();
		$is_unique = $this->stationDao->isApiKeyUnique($api_key);
		
		if (!$is_unique) {
			$this->createStation($uri, $user_id, $station_name, $depth + 1);
		}
		
		if ($this->stationDao->createStation($station_name, $api_key, $user_id)) {
			$result = $this->stationDao->getStationByApiKey($api_key);
			
			if ($result) {
				$output = $this->getAssocByKeys($result, ['station_id', 'api_key']);
				$this->sendJson($output);
			} else {
				$this->error(400);
			}
		} else {
			$this->error(400);
		}
	}
	
	# endregion
	#------------------------------------------------------------------------------------------------------------------
	#region put
	
	protected function put(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		if ($uri[3] == 'stations' && is_numeric($uri[4])) {
			$this->generateKeyForStation($uri, $user_id);
		} else {
			$this->updateUser($params, $user_id);
		}
	}
	
	/**
	 * PUT /user/stations/{station_id}
	 */
	private function generateKeyForStation(array $uri, int $user_id, int $depth = 0) {
		if ($depth >= 10) $this->error(500);
		
		$station_id = (int)$uri[4];
		
		$station = $this->stationDao->getStationById($station_id);
		if (empty($station)) {
			$this->error(404);
		}
		if ($station['owner'] != $user_id) {
			$this->error(403);
		}
		
		$api_key = $this->generateApiKey();
		$is_unique = $this->stationDao->isApiKeyUnique($api_key);
		
		if (!$is_unique) {
			$this->generateKeyForStation($uri, $user_id, $depth + 1);
		}
		
		$result = $this->stationDao->updateApiKeyById($station_id, $api_key);
		if ($result) {
			$this->sendJson(['station_id' => $station_id, 'api_key' => $api_key]);
		} else {
			$this->error(400);
		}
	}
	
	private function generateApiKey(): string {
		$api_key = "";
		try {
			$api_key = str_shuffle(MD5(microtime()));
		} catch (Exception $e) {
			$this->error(400);
		}
		return $api_key;
	}
	
	/**
	 * PUT /user
	 */
	private function updateUser(array $params, int $user_id) {
		if (empty($params['username']) || empty($params['password'])) {
			$this->error(400);
		}
		
		$username = $params['username'];
		$password = $params['password'];
		
		$existing_user = $this->userDao->getUserByUname($username);
		if (empty($existing_user)) {
			$this->error(404);
		}
		
		$result = $this->userDao->updateUserById($user_id, $username, $password);
		
		if ($result) {
			$this->response(200);
		} else {
			$this->error(400);
		}
	}
	
	# endregion
	#------------------------------------------------------------------------------------------------------------------
	#region delete
	
	protected function delete(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		$user_data = $this->userDao->getUserById($user_id);
		if (empty($user_data)) {
			$this->error(404);
		}
		
		$result = true;
		
		$stations = $this->stationDao->getStationIdsOfUser($user_id);
		foreach ($stations as $station_id) {
			$result &= $this->measurementDao->deleteMeasurementsOfStation($station_id);
			$result &= $this->pictureDao->deletePicturesOfStation($station_id);
		}
		$result &= $this->stationDao->deleteStationsOfUser($user_id);
		
		$result &= $this->userDao->deleteUserById($user_id);
		
		if ($result) {
			$this->response(200);
		} else {
			$this->response(400);
		}
	}
	
	# endregion
}