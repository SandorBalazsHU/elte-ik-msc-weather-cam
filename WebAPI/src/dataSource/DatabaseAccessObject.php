<?php

class DatabaseAccessObject {
	public const EMPTY_RESULT = 'EMPTY_RESULT';
	protected mysqli $connection;
	
	/**
	 * @throws Exception
	 */
	public function __construct() {
		try {
			$this->connection = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE_NAME);
			
			if (mysqli_connect_errno()) {
				throw new Exception("Could not connect to database.");
			}
		} catch (Exception $e) {
			throw new Exception($e->getMessage());
		}
	}
	
	/**
	 * @throws Exception
	 */
	public function select($query = "", $params = []): array {
		try {
			$stmt = $this->executeStatement($query, $params);
			$result = $stmt->get_result()->fetch_all(MYSQLI_ASSOC);
			$stmt->close();
			
			return $result;
		} catch (Exception $e) {
			throw new Exception($e->getMessage());
		}
	}
	
	/**
	 * @throws Exception
	 */
	public function selectOne($query = "", $params = []): array {
		try {
			$stmt = $this->executeStatement($query, $params);
			$result = $stmt->get_result()->fetch_object();
			$stmt->close();
			
			return $result == null ? [] : get_object_vars($result);
		} catch (Exception $e) {
			throw new Exception($e->getMessage());
		}
	}
	
	/**
	 * @throws Exception
	 */
	public function selectValue($query = "", $params = []) {
		try {
			$stmt = $this->executeStatement($query, $params);
			$result = $stmt->get_result()->fetch_array();
			$stmt->close();
			
			return $result[0] ?? DatabaseAccessObject::EMPTY_RESULT;
		} catch (Exception $e) {
			throw new Exception($e->getMessage());
		}
	}
	
	public function runQuery($query = "", $params = []): bool {
		try {
			$stmt = $this->executeStatement($query, $params);
			$error = $stmt->error;
			$stmt->close();
			
			return empty($error);
		} catch (Exception $e) {
			throw new Exception($e->getMessage());
		}
	}
	
	/**
	 * @throws Exception
	 */
	private function executeStatement(string $query = "", array $params = []): mysqli_stmt {
		try {
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
		} catch (Exception $e) {
			throw new Exception($e->getMessage());
		}
	}
}
