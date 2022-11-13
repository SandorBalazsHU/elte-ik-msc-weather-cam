<?php

// TODO separate by http $method = $_SERVER['REQUEST_METHOD'];

class BaseController {
	/**
	 * __call magic method - Called whenever we try to call an unimplemented method of this class
	 */
	public function __call($name, $arguments) {
		$this->sendOutput('', array('HTTP/1.1 404 Not Found'));
	}
	
	/**
	 * Get URI elements.
	 *
	 * @return array
	 */
	protected function getUriSegments(): array {
		$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
		return explode('/', $uri);
	}
	
	protected function getRequestMethod(): string {
		$method = $_SERVER['REQUEST_METHOD'];
		return strtoupper($method);
	}
	
	/**
	 * Get query string params.
	 *
	 * @return array
	 */
	protected function getQueryStringParams(): array {
		parse_str($_SERVER['QUERY_STRING'], $query);
		return $query;
	}
	
	/**
	 * Get query string params.
	 *
	 * @return array
	 */
	protected function getJsonBody(): array {
		$raw_body = file_get_contents('php://input');
		$decoded_body = json_decode($raw_body, true);
		return $decoded_body ?: [];
	}
	
	/**
	 * Send API output.
	 *
	 * @param mixed $data
	 * @param string $httpHeaders
	 */
	protected function sendOutput($data, $httpHeaders = array()) {
		header_remove('Set-Cookie');
		$this->addHeaders($httpHeaders);
		
		echo $data;
		exit;
	}
	
	protected function sendJson($data, $httpHeaders = array()) {
		header('Content-Type: application/json');
		header('HTTP/1.1 200 OK');
		$this->addHeaders($httpHeaders);
		
		echo json_encode($data);
	}
	
	protected function sendJsonError($data) {
		header('Content-Type: application/json');
		echo json_encode($data);
	}
	
	private function addHeaders($httpHeaders = array()) {
		if (is_array($httpHeaders) && count($httpHeaders)) {
			foreach ($httpHeaders as $httpHeader) {
				header($httpHeader);
			}
		}
	}
	
	function error(int $code) {
		http_response_code($code);
		$error_msg = array("code" => $code, "type" => "failure");
		
		switch ($code) {
			case 401:
				$error_msg['message'] = 'Failed to authenticate request.';
				break;
			case 403:
				$error_msg['message'] = 'Failed to authorize request';
				break;
			
			case 500:
				$error_msg['message'] = 'Internal Server Error';
				break;
		}
		
		$this->sendJsonError($error_msg);
		exit;
	}

}
