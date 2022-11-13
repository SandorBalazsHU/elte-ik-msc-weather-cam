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
	
	public function getUserByUnameAndPassword($username, $password): array {
		try {
			return $this->selectOne(
				"SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1",
				[$username, $password]
			);
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function getUserById($user_id): array {
		try {
			return $this->selectOne("SELECT * FROM users WHERE user_id = ? LIMIT 1", [$user_id]);
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function getUserByUname($username): array {
		try {
			return $this->selectOne("SELECT * FROM users WHERE username = ? LIMIT 1", [$username]);
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function insertUser($username, $password): bool {
		try {
			return $this->runQuery(
				"INSERT INTO users (username, password) VALUES (?, ?)",
				[$username, $password]
			);
		} catch (Exception $e) {
			return false;
		}
	}
	
}