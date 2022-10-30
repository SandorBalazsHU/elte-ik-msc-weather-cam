<?php

class UserDao extends DatabaseAccessObject {
	const DEFAULT_LIMIT = 20;
	
	public function getUsers(int $limit = self::DEFAULT_LIMIT) {
		try {
			return $this->select("SELECT * FROM users LIMIT ?", ["i", $limit]);
		} catch (Exception $e) {
			return array();
		}
	}
	
}