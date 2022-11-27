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
		// TODO: Implement get() method.
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
		
		$key_does_not_exist = $this->stationDao->isApiKeyUnique($api_key);
		if ($key_does_not_exist) {
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