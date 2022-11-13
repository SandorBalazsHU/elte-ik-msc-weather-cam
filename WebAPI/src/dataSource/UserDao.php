<?php

class UserDao extends DatabaseAccessObject {
	const DEFAULT_LIMIT = 20;
	
	public function getUsers(int $limit = self::DEFAULT_LIMIT): array {
		try {
			return $this->select("SELECT * FROM users");
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function getUser($username, $password): array {
		try {
			return $this->selectOne(
				"SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1",
				[$username, $password]
			);
		} catch (Exception $e) {
			return array();
		}
		
	}
	
}