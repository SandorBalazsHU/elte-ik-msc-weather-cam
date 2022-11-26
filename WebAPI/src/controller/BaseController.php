<?php

abstract class BaseController extends ResponseHandler {
	protected JwtHandler $jwt;
	
	public function __construct() {
		$this->jwt = new JwtHandler();
	}
	
	public function processRequest() {
		$method = $this->getRequestMethod();
		$uri = $this->getUriSegments();
		$params = $this->getQueryStringParams();
		$body = $this->getJsonBody();
		
		// process endpoints that are used without a JWT token
		$this->processNoJwt($method, $uri, $params, $body);
		
		// jwt validation
		$request_headers = getallheaders();
		if (!isset($request_headers['Authorization'])) {
			$this->error(403);
		}
		$jwt_token = $request_headers['Authorization'];
		$user_id = $this->jwt->validate($jwt_token);
		
		switch ($method) {
			case 'GET':
				$this->get($uri, $params, $body, $user_id, $jwt_token);
				break;
			case 'POST':
				$this->post($uri, $params, $body, $user_id, $jwt_token);
				break;
			case 'PUT':
				$this->put($uri, $params, $body, $user_id, $jwt_token);
				break;
			case 'DELETE':
				$this->delete($uri, $params, $body, $user_id, $jwt_token);
				break;
		}
		
		$this->error(404);
	}
	
	/**
	 * Processes endpoints that are used without a JWT token
	 * @return void
	 */
	abstract protected function processNoJwt(string $method, array $uri, array $params, array $body);
	
	abstract protected function get(array $uri, array $params, array $body, int $user_id, string $jwt_token);
	
	abstract protected function post(array $uri, array $params, array $body, int $user_id, string $jwt_token);
	
	abstract protected function put(array $uri, array $params, array $body, int $user_id, string $jwt_token);
	
	abstract protected function delete(array $uri, array $params, array $body, int $user_id, string $jwt_token);
	
	
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
	
	
}
