<?php

class UserController extends BaseController {
	private UserDao $userDao;
	private StationDao $stationDao;
	private JwtHandler $jwt;
	
	public function __construct() {
		require_once PROJECT_ROOT_PATH . "/dataSource/UserDao.php";
		$this->userDao = new UserDao();
		
		require_once PROJECT_ROOT_PATH . "/dataSource/StationDao.php";
		$this->stationDao = new StationDao();
		
		$this->jwt = new JwtHandler();
	}
	
	public function processRequest() {
		$method = $this->getRequestMethod();
		$uri = $this->getUriSegments();
		$params = $this->getQueryStringParams();
		$body = $this->getJsonBody();
		
		// endpoints that are used without a JWT token
		if ($method == 'GET' && isset($uri[3]) && $uri[3] == 'login') {
			$this->login($body);
		}
		if ($method == 'POST' && isset($uri[3]) && $uri[3] == 'register') {
			$this->register($body);
		}
		
		// jwt validation
		$request_headers = getallheaders();
		if (!isset($request_headers['Authorization'])) {
			$this->error(403);
		}
		$user_id = $this->jwt->validate($request_headers['Authorization']);
		
		switch ($method) {
			case 'GET':
				$this->get($uri, $params, $body, $user_id);
				break;
			case 'POST':
				$this->post($uri, $params);
				break;
			case 'PUT':
				$this->put($uri, $params, $body, $user_id);
				break;
			case 'DELETE':
				$this->delete($uri, $params, $user_id);
				break;
		}
		
		$this->error(404);
	}
	
	#region get
	
	private function get(array $uri, array $params, array $body, int $user_id) {
		if (!isset($uri[3])) {
			$this->getUser($user_id);
			return;
		}
		
		switch ($uri[3]) {
			case 'logout':
				// TODO implement endpoint
				break;
			case 'stations':
				// TODO implement endpoint
				break;
		}
	}
	
	private function login($body) {
		if (empty($body['username']) || empty($body['password'])) {
			$this->error(403);
		}
		
		$user = $this->userDao->getUserByUnameAndPassword($body['username'], $body['password']);
		
		if (empty($user)) {
			$this->error(401);
			return;
		}
		
		try {
			$token = $this->jwt->getToken($user['user_id']);
		} catch (Exception $e) {
			$this->error(500);
			return;
		}
		
		exit($token->toString());
	}
	
	private function getUser(int $user_id) {
		$user = $this->userDao->getUserById($user_id);
		if (empty($user)) {
			$this->error(500);
		}
		
		$this->sendJson($user);
	}
	
	#endregion
	#region post
	
	private function post($uri, $params) {
		switch ($uri[3]) {
			case 'stations':
				// TODO implement endpoint
				break;
		}
	}
	
	private function register(array $body) {
		if (empty($body['username']) || empty($body['password'])) {
			$this->error(400);
		}
		
		$username = $body['username'];
		$password = $body['password'];
		
		$existing_user = $this->userDao->getUserByUname($username);
		if (!empty($existing_user)) {
			$this->error(409);
		}
		
		$result = $this->userDao->insertUser($username, $password);
		
		if ($result) {
			$this->response(200);
		} else {
			$this->error(500);
		}
	}
	
	# endregion
	#region put
	
	private function put(array $uri, array $params, array $body, int $user_id) {
		if ($uri[3] == 'stations' && is_numeric($uri[4])) {
			$this->generateKeyForStation($uri, $user_id);
		} else {
			$this->updateUser($body, $user_id);
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
			$this->error(500);
		}
	}
	
	private function generateApiKey(): string {
		$api_key = "";
		try {
			$api_key = str_shuffle(MD5(microtime()));
			
			
		} catch (Exception $e) {
			$this->error(500);
		}
		return $api_key;
	}
	
	/**
	 * PUT /user
	 */
	private function updateUser(array $body, int $user_id) {
		if (empty($body['username']) || empty($body['password'])) {
			$this->error(400);
		}
		
		$username = $body['username'];
		$password = $body['password'];
		
		$existing_user = $this->userDao->getUserByUname($username);
		if (empty($existing_user)) {
			$this->error(404);
		}
		
		$result = $this->userDao->updateUserById($user_id, $username, $password);
		
		if ($result) {
			$this->response(200);
		} else {
			$this->error(500);
		}
	}
	
	# endregion
	#region delete
	
	private function delete(array $uri, array $params, int $user_id) {
		// TODO delete user and its stations, measurements and images
	}
	
	# endregion
	
}