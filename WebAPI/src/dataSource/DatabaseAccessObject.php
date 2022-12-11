<?php

class DatabaseAccessObject {
	public const EMPTY_RESULT = 'EMPTY_RESULT';
	public const VALUE_NOT_FOUND = -1;
	public const INSERTION_FAILED = -2;
	protected mysqli $connection;
	
	/**
	 * @throws Exception
	 */
	public function __construct() {
		$this->connection = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE_NAME);
		
		if (mysqli_connect_errno()) {
			throw new Exception("Could not connect to database.");
		}
	}
	
	public function __destruct() {
		$this->connection->close();
	}
	
	/**
	 * @throws Exception
	 */
	protected function select($query = "", $params = []): array {
		$stmt = $this->executeStatement($query, $params);
		$result = $stmt->get_result()->fetch_all(MYSQLI_ASSOC);
		$stmt->close();
		
		return $result;
	}
	
	/**
	 * @throws Exception
	 */
	protected function selectOne($query = "", $params = []): array {
		$stmt = $this->executeStatement($query, $params);
		$result = $stmt->get_result()->fetch_object();
		$stmt->close();
		
		return $result == null ? [] : get_object_vars($result);
	}
	
	/**
	 * @throws Exception
	 */
	protected function selectValue($query = "", $params = []) {
		$stmt = $this->executeStatement($query, $params);
		$result = $stmt->get_result()->fetch_array();
		$stmt->close();
		
		return $result[0] ?? DatabaseAccessObject::EMPTY_RESULT;
	}
	
	/**
	 * @throws Exception
	 */
	protected function selectKeys($query = "", $params = []): array {
		$query_result = $this->select($query, $params);
		return array_keys($query_result);
	}
	
	/**
	 * @throws Exception
	 */
	protected function runQuery($query = "", $params = []): bool {
		$stmt = $this->executeStatement($query, $params);
		$error = $stmt->error;
		$stmt->close();
		
		return empty($error);
	}
	
	/**
	 * @throws Exception
	 */
	private function executeStatement(string $query = "", array $params = []): mysqli_stmt {
		$stmt = $this->connection->prepare($query);
		if ($stmt === false) {
			throw new Exception("Unable to create prepared statement: " . $query);
		}
		
		if ($params) {
			// all the params are bind as string - this should not cause problems, bc MySQL handles it (except order by)
			$stmt->bind_param(str_repeat("s", count($params)), ...$params);
		}
		
		$stmt->execute();
		return $stmt;
	}
	
}
