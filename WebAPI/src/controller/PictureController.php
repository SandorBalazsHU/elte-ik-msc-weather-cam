<?php
require_once PROJECT_ROOT_PATH . "/dataSource/PictureDao.php";
require_once PROJECT_ROOT_PATH . "/dataSource/StationDao.php";

class PictureController extends BaseController {
	private PictureDao $pictureDao;
	private StationDao $stationDao;
	
	public function __construct() {
		parent::__construct();
		
		$this->pictureDao = new PictureDao();
		$this->stationDao = new StationDao();
	}
	
	protected function processNoJwt(string $method, array $uri, array $params, array $body) {
		// TODO: Implement processNoJwt() method.
		$request_headers = getallheaders();
		if ($method == 'POST'
			&& isset($uri[3]) && $uri[3] == 'pictures'
			&& empty($uri[4])
		) {
			$this->savePicture($request_headers, $params, $body);
		}
	}
	
	protected function get(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		// TODO: Implement get() method.
	}
	
	protected function post(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		// TODO: Implement post() method.
	}
	
	private function savePicture(array $request_headers, array $params, array $body) {
		if (!isset($request_headers['api_key'])) {
			$this->error(403);
		}
		$api_key = $request_headers['api_key'];
		
		$station_id = $this->stationDao->getStationIdByApiKey($api_key);
		if ($station_id == DatabaseAccessObject::VALUE_NOT_FOUND) {
			$this->error(403);
		}
		
		$picture_id = $this->pictureDao->insertPicture($station_id);
		if ($picture_id == DatabaseAccessObject::INSERTION_FAILED) {
			$this->error(400);
		}
		
		$filename = "$picture_id.jpg";
		$raw_file_contents = file_get_contents('php://input');
		
		$picture_saved = $this->pictureDao->savePictureOnFilesystem($filename, $raw_file_contents);
		if (!$picture_saved) {
			$this->error(400);
		}
		
		$result = $this->pictureDao->setPictureSaved($picture_id, $filename);
		if ($result) {
			$this->response(200);
		} else {
			$this->error(400);
		}
	}
	
	protected function put(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		// TODO: Implement put() method.
	}
	
	protected function delete(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		// TODO: Implement delete() method.
	}
	
}