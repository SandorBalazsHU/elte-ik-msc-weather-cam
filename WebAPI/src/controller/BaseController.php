<?php

// TODO separate by http $method = $_SERVER['REQUEST_METHOD'];

class BaseController {
	/**
	 * __call magic method - Called whenever we try to call an unimplemented method of this class
	 */
	public function __call($name, $arguments) {
		$this->error(404);
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
	 * Get JSON encoded request body as array.
	 *
	 * @return array
	 */
	protected function getJsonBody(): array {
		$raw_body = file_get_contents('php://input');
		$decoded_body = json_decode($raw_body, true);
		return $decoded_body ?: [];
	}
	
	/**
	 * Get a subset of key->value pairs of an array
	 * @param array $arr array to get part of
	 * @param array $keys keys to get from original array
	 * @return array subset of key->value pairs from the original array
	 */
	protected function getAssocByKeys(array $arr, array $keys): array {
		return array_intersect_key($arr, array_flip($keys));
	}
	
	/**
	 * Add HTTP headers to response.
	 * @param array $httpHeaders
	 * @return void
	 */
	private function addHeaders(array $httpHeaders = array()) {
		if (is_array($httpHeaders) && count($httpHeaders)) {
			foreach ($httpHeaders as $httpHeader) {
				header($httpHeader);
			}
		}
	}
	
	/**
	 * Send output as HTTP response.
	 *
	 * @param string $output
	 * @param string $httpHeaders
	 */
	protected function sendOutput(string $output, $httpHeaders = array()) {
		header_remove('Set-Cookie');
		$this->addHeaders($httpHeaders);
		
		exit($output);
	}
	
	/**
	 * Send HTTP response with JSON encoded data
	 * @param array $data Data to JSON encode
	 * @param array $httpHeaders
	 * @return void
	 */
	protected function sendJson(array $data, array $httpHeaders = array()) {
		header('Content-Type: application/json');
		http_response_code(200);
		$this->addHeaders($httpHeaders);
		
		exit(json_encode($data));
	}
	
	protected function sendJsonError(array $data) {
		header('Content-Type: application/json');
		exit(json_encode($data));
	}
	
	protected function response(int $code) {
		http_response_code($code);
		$response = array("code" => $code, "type" => "success");
		
		switch ($code) {
			case 200:
				$success['message'] = 'Successfully processed request.';
				break;
		}
		
		$this->sendJsonError($response);
	}
	
	protected function error(int $code) {
		http_response_code($code);
		$response = array("code" => $code, "type" => "failure");
		
		switch ($code) {
			case 400:
				$response['message'] = 'Failed to process request.';
				break;
			case 401:
				$response['message'] = 'Failed to authenticate request.';
				break;
			case 403:
				$response['message'] = 'Failed to authorize request';
				break;
			case 404:
				$response['message'] = 'Failed to find requested resource';
				break;
			case 409:
				$response['message'] = 'Failed to register user. Username is already taken.';
				break;
			
			case 500:
				$response['message'] = 'Internal Server Error';
				break;
		}
		
		$this->sendJsonError($response);
	}
	
}
