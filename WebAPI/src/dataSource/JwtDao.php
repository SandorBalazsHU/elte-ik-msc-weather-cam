<?php

class JwtDao extends DatabaseAccessObject {
	
	public function blacklistToken(string $jwt_token, DateTimeInterface $date_time): bool {
		try {
			return $this->runQuery(
				"INSERT INTO jwt_blacklist (token, expiration) VALUES (?, ?)",
				[$jwt_token, $date_time->format(DATE_FORMAT)]
			);
		} catch (Exception $e) {
			return false;
		}
	}
	
	public function isTokenBlacklisted(string $jwt_token): bool {
		try {
			$result = $this->selectRow(
				"SELECT * FROM jwt_blacklist WHERE token=? LIMIT 1",
				[$jwt_token]
			);
			return !empty($result);
		} catch (Exception $e) {
			return false;
		}
	}
	
}