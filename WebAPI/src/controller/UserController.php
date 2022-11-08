<?php


class UserController extends BaseController {
	private UserDao $dao;
	
	public function __construct() {
		require_once PROJECT_ROOT_PATH . "/dataSource/UserDao.php";
		$this->dao = new UserDao();
	}
	
	public function processRequest() {
		$method = $this->getRequestMethod();
		$uri = $this->getUriSegments();
		$params = $this->getQueryStringParams();
		$body = $this->getJsonBody();
		
		switch ($method) {
			case 'GET':
				$this->get($uri, $params, $body);
				break;
			case 'POST':
				$this->post($uri, $params);
				break;
			case 'PUT':
				$this->put($uri, $params);
				break;
			case 'DELETE':
				$this->delete($uri, $params);
				break;
		}
	}
	
	#region get
	
	private function get($uri, $params, $body) {
		// TODO jwt validation
		if (!isset($uri[3])) {
			$this->getUser();
			return;
		}
		
		switch ($uri[3]) {
			case 'login':
				$this->login($body);
				break;
			case 'logout':
				// TODO implement endpoint
				break;
			case 'stations':
				// TODO implement endpoint
				break;
		}
	}
	
	private function getUser() {
		// TODO decode jwt token
		//  check if it's valid and which user does it belong to
		//  query and send user data
	}
	
	private function login($body) {
		$user = $this->dao->getUser($body['username'], $body['password']);
		
		// TODO debug this !!!
		if (empty($user)) {
			$this->sendJson($body);
			return;
		}
		
		// TODO generate and return jwt token
		$this->sendJson($user);
	}
	
	#endregion
	#region post
	
	private function post($uri, $params) {
		// TODO implement method
	}
	
	# endregion
	#region put
	
	private function put($uri, $params) {
		// TODO implement method
	}
	
	# endregion
	#region delete
	
	private function delete($uri, $params) {
		// TODO implement method
	}
	
	# endregion
	
}