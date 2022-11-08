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
	 * Get querystring params.
	 *
	 * @return array
	 */
	protected function getQueryStringParams(): array {
		parse_str($_SERVER['QUERY_STRING'], $query);
		return $query;
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
	
	private function addHeaders($httpHeaders = array()) {
		if (is_array($httpHeaders) && count($httpHeaders)) {
			foreach ($httpHeaders as $httpHeader) {
				header($httpHeader);
			}
		}
	}
	
}
