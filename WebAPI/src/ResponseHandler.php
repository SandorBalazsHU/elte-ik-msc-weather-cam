<?php

/**
 * HTTP response handler
 */
class ResponseHandler {
	
	/**
	 * __call magic method - Called whenever we try to call an unimplemented method of this class
	 */
	public function __call($name, $arguments) {
		$this->error(404);
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
	 * @param mixed $data Data to JSON encode
	 * @param array $httpHeaders
	 * @return void
	 */
	protected function sendJson($data, array $httpHeaders = array()) {
		header('Content-Type: application/json');
		http_response_code(200);
		$this->addHeaders($httpHeaders);
		
		exit(json_encode($data));
	}
	
	protected function sendJsonError(array $data) {
		header('Content-Type: application/json');
		exit(json_encode($data));
	}
	
	public function response(int $code) {
		http_response_code($code);
		$response = array("code" => $code, "type" => "success");
		
		switch ($code) {
			case 200:
				$response['message'] = 'Successfully processed request.';
				break;
		}
		
		$this->sendJsonError($response);
	}
	
	public function error(int $code) {
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