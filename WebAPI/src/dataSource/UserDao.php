<?php

class UserDao extends DatabaseAccessObject {
	const DEFAULT_LIMIT = 20;
	
	public function getUserById(int $user_id): array {
		try {
			return $this->selectOne("SELECT * FROM users WHERE user_id = ? LIMIT 1", [$user_id]);
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
	
	public function getUserByUname(string $username): array {
		try {
			return $this->selectOne("SELECT * FROM users WHERE username = ? LIMIT 1", [$username]);
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function insertUser(string $username, string $password): bool {
		try {
			return $this->runQuery(
				"INSERT INTO users (username, password) VALUES (?, ?)",
				[$username, $password]
			);
		} catch (Exception $e) {
			return false;
		}
	}
	
	public function updateUserById(int $id, string $username, string $password): bool {
		try {
			return $this->runQuery(
				"UPDATE users SET username=?, password=? WHERE user_id=?",
				[$username, $password, $id]
			);
		} catch (Exception $e) {
			return false;
		}
	}
	
	
}