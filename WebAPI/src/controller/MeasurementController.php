<?php
require_once PROJECT_ROOT_PATH . "/dataSource/MeasurementDao.php";
require_once PROJECT_ROOT_PATH . "/dataSource/UserDao.php";
require_once PROJECT_ROOT_PATH . "/dataSource/StationDao.php";

class MeasurementController extends BaseController {
	private MeasurementDao $measurementDao;
	private UserDao $userDao;
	private StationDao $stationDao;
	
	public function __construct() {
		parent::__construct();
		
		$this->measurementDao = new MeasurementDao();
		$this->userDao = new UserDao();
		$this->stationDao = new StationDao();
	}
	
	protected function processNoJwt(string $method, array $uri, array $params, array $body) {
		$request_headers = getallheaders();
		if ($method == 'POST' && isset($uri[3]) && $uri[3] == 'measurements') {
			$this->saveMeasurement($request_headers, $body);
		}
	}
	
	#region get
	
	protected function get(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		if (!isset($uri[3]) || !is_numeric($uri[3])) {
			$this->error(404);
		}
		$station_id = $uri[3];
		
		if (!isset($uri[4]) || $uri[4] != 'measurements') {
			$this->error(404);
		}
		
		if (empty($uri[5])) {
			$this->getByFilter($params, $station_id);
		}
		
		if (is_numeric($uri[5])) {
			$measurement_id = $uri[5];
			$this->getById($measurement_id);
		}
		
		switch ($uri[5]) {
			case 'first':
				$this->getFirst($station_id);
				break;
			case 'latest':
				$this->getLatest($station_id);
				break;
		}
	}
	
	private function getByFilter(array $params, int $station_id) {
		// TODO check filters
		if (empty($params['station_id'])) {
			$params['station_id'] = $station_id;
		}
		if ($params['station_id'] != $station_id) {
			$this->error(403);
		}
		$result = $this->measurementDao->getMultipleMeasurements($params);
		if (isset($params['offset'])) {
			if (count($result) != 1) {
				$this->error(400);
			}
			
			// get adjacent records by given offset
			$offset = $params['offset'];
			$measurement_id = $result[0]['measurement_id'];
			$station_id = $result[0]['station_id'];
			$result = $this->measurementDao->getWithOffset($measurement_id, $station_id, $offset);
		}
		
		if (empty($result)) {
			$this->error(404);
		} else {
			$this->sendJson($result);
		}
	}
	
	private function getById(int $measurement_id) {
		$result = $this->measurementDao->getMeasurementById($measurement_id);
		
		if (empty($result)) {
			$this->error(404);
		} else {
			$this->sendJson($result);
		}
	}
	
	private function getFirst(int $station_id) {
		$result = $this->measurementDao->getFirstMeasurement($station_id);
		
		if (empty($result)) {
			$this->error(404);
		} else {
			$this->sendJson($result);
		}
	}
	
	private function getLatest(int $station_id) {
		$result = $this->measurementDao->getLatestMeasurement($station_id);
		
		if (empty($result)) {
			$this->error(404);
		} else {
			$this->sendJson($result);
		}
	}
	
	#endregion
	#------------------------------------------------------------------------------------------------------------------
	#region post
	
	protected
	function post(
		array $uri, array $params, array $body, int $user_id, string $jwt_token
	) {
		$this->error(404);
	}
	
	private
	function saveMeasurement(
		array $request_headers, array $body
	) {
		if (!isset($request_headers['api-key'])) {
			$this->error(403);
		}
		
		$api_key = $request_headers['api-key'];
		// TODO documentation unclear
	}
	
	#endregion
	#------------------------------------------------------------------------------------------------------------------
	#region put
	
	protected
	function put(
		array $uri, array $params, array $body, int $user_id, string $jwt_token
	) {
		$this->error(404);
	}
	
	#endregion
	#------------------------------------------------------------------------------------------------------------------
	#region delete
	
	protected
	function delete(
		array $uri, array $params, array $body, int $user_id, string $jwt_token
	) {
		$this->error(404);
	}
	
	#endregion
	
}