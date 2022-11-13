<?php

use Lcobucci\JWT\Token\Builder;
use Lcobucci\JWT\JwtFacade;
use Lcobucci\JWT\Signer\Hmac\Sha256;
use Lcobucci\JWT\Signer\Key\InMemory;
use Lcobucci\JWT\Token\RegisteredClaims;
use Lcobucci\JWT\Encoding\ChainedFormatter;
use Lcobucci\JWT\Encoding\JoseEncoder;


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
		
		if (empty($user)) {
			// TODO send failure response according to API docs
			$this->sendJson($body);
			return;
		}
		
		try {
			$token = JwtHandler::getToken(['uid' => $user['user_id']]);
		} catch (Exception $e) {
			// TODO return internal server error
			return;
		}
		
		echo $token->toString();
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