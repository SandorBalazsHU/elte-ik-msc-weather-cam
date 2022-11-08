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
		
		if (count($uri) == 3 && $method == 'GET') {
			$result = $this->dao->getUsers();
			$this->sendJson($result);
		}
	}
}