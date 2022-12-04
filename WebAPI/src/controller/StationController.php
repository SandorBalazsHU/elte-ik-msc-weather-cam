<?php
require_once PROJECT_ROOT_PATH . "/dataSource/StationDao.php";

class StationController extends BaseController {
	private StationDao $stationDao;
	
	public function __construct() {
		parent::__construct();
		
		$this->stationDao = new StationDao();
	}
	
	protected function processNoJwt(string $method, array $uri, array $params, array $body) {
		$request_headers = getallheaders();
		
		if ($method == 'PUT'
			&& isset($uri[3]) && $uri[3] == 'status'
			&& isset($uri[4]) && is_numeric($uri[4])
		) {
			$status_code = $uri[4];
			$this->updateStatus($request_headers, $status_code);
		}
	}
	
	protected function get(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		if (!isset($uri[3]) || !isset($uri[4])) {
			$this->error(404);
		}
		
		if (is_numeric($uri[3])) {
			$station_id = $uri[3];
			
			switch ($uri[4]) {
				case 'api':
					$this->getApiKey($station_id);
					break;
				case 'ping':
					$this->getStatus($station_id);
					break;
			}
			return;
		}
		
		switch ($uri[3] . "/" . $uri[4]) {
			case 'pictures/storage':
				$this->getPictureStorage();
				break;
			case 'measurements/storage':
				$this->getDbStorage();
				break;
		}
	}
	
	private function getApiKey(int $station_id) {
		$api_key = $this->stationDao->getApiKeyById($station_id);
		$sub = substr($api_key, 4, strlen($api_key) - 8);
		$hidden_st = str_replace($sub, str_repeat('*', strlen($sub)), $api_key);
		
		if (empty($hidden_st)) {
			$this->error(400);
		} else $this->sendJson($hidden_st);
	}
	
	private function getStatus(int $station_id) {
		// TODO implement endpoint
	}
	
	private function getPictureStorage() {
		$storage = $this->stationDao->getSystemStorage();
		
		if (empty($storage)) {
			$this->error(400);
		} else {
			$this->sendJson($storage);
		}
	}
	
	private function getDbStorage() {
		$storage = $this->stationDao->getSystemStorage();
		
		if (empty($storage)) {
			$this->error(400);
		} else {
			$this->sendJson($storage);
		}
	}
	
	
	protected function post(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		$this->error(404);
	}
	
	protected function put(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		$this->error(404);
	}
	
	private function updateStatus(array $request_headers, int $status_code) {
		if (empty($request_headers['api_key'])) {
			$this->error(403);
		}
		$api_key = $request_headers['api_key'];
		
		$key_exists = $this->stationDao->apiKeyExists($api_key);
		if (!$key_exists) {
			$this->error(403);
		}
		
		$result = $this->stationDao->updateStatusByApiKey($api_key, $status_code);
		
		if ($result) {
			$this->response(200);
		} else {
			$this->error(400);
		}
	}
	
	protected function delete(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		$this->error(404);
	}
	
}