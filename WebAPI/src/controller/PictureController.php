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
		$request_headers = getallheaders();
		if ($method == 'POST'
			&& isset($uri[3]) && $uri[3] == 'pictures'
			&& empty($uri[4])
		) {
			$this->savePicture($request_headers, $params, $body);
		}
	}
	
	private function sendImage(string $file_contents) {
		$this->sendOutput($file_contents, ["Content-type: image/jpeg"]);
	}
	
	#region get
	
	protected function get(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		if (!isset($uri[3])) {
			$this->error(404);
		}
		
		if (is_numeric($uri[3]) && isset($uri[4]) && $uri[4] == 'pictures') {
			$station_id = $uri[3];
			
			// check user access to station
			$user_stations = $this->stationDao->getStationIdsOfUser($user_id);
			if (!in_array($station_id, $user_stations)) {
				$this->error(404);
			}
			
			if (empty($uri[5])) {
				$this->getPictureByFilter($station_id, $params);
			}
			
			if (in_array($uri[5], ['first', 'latest'])) {
				$absolute = $uri[5];
				$this->getPictureAbsolute($station_id, $absolute);
			}
			
			if (is_numeric($uri[5])) {
				$picture_id = $uri[5];
				$this->getPictureById($station_id, $picture_id);
			}
		}
	}
	
	private function getPictureByFilter(int $station_id, array $params) {
		// TODO implement method
	}
	
	private function getPictureAbsolute(int $station_id, string $absolute) {
		switch ($absolute) {
			case 'first':
				$picture_data = $this->pictureDao->getFirstFileInfoOfStations($station_id);
				break;
			case 'latest':
				$picture_data = $this->pictureDao->getLatestFileInfoOfStations($station_id);
				break;
			default:
				$this->error(404);
				return;
		}
		
		if (empty($picture_data)) {
			$this->error(404);
		}
		
		$this->sendPictureByFilename($picture_data['filename']);
	}
	
	private function getPictureById(int $station_id, int $picture_id) {
		$picture_data = $this->pictureDao->getFileInfoById($picture_id);
		
		if (empty($picture_data)) {
			$this->error(404);
		}
		if ($picture_data['station_id'] != $station_id) {
			$this->error(403);
		}
		
		$this->sendPictureByFilename($picture_data['filename']);
	}
	
	private function sendPictureByFilename($filename) {
		$picture = $this->pictureDao->getPictureByFilename($filename);
		if ($picture == DatabaseAccessObject::EMPTY_RESULT) {
			$this->error(404);
		}
		$this->sendImage($picture);
	}
	
	#endregion
	#------------------------------------------------------------------------------------------------------------------
	#region post
	
	protected function post(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		// Implement post() method here
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
	
	#endregion
	#------------------------------------------------------------------------------------------------------------------
	#region put
	
	protected function put(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		// Implement put() method here
	}
	
	#endregion
	#------------------------------------------------------------------------------------------------------------------
	#region delete
	
	protected function delete(array $uri, array $params, array $body, int $user_id, string $jwt_token) {
		// Implement delete() method here
	}
	
	#endregion
	
}