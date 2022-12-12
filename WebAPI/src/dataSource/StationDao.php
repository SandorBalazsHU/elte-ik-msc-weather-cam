<?php

class StationDao extends DatabaseAccessObject {
	
	public function getStationById(int $station_id): array {
		try {
			return $this->selectRow(
				"SELECT * FROM stations WHERE station_id = ? LIMIT 1",
				[$station_id]
			);
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function getStationByApiKey(string $api_key): array {
		try {
			return $this->selectRow(
				"SELECT * FROM stations WHERE api_key = ? LIMIT 1",
				[$api_key]
			);
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function getStationIdByApiKey(string $api_key): int {
		try {
			return $this->selectValue(
				"SELECT station_id FROM stations WHERE api_key = ? LIMIT 1",
				[$api_key]
			);
		} catch (Exception $e) {
			return parent::VALUE_NOT_FOUND;
		}
	}
	
	public function getStationsOfUser(int $user_id): array {
		try {
			return $this->select(
				"SELECT station_id, station_name FROM stations WHERE owner = ?",
				[$user_id]
			);
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function getApiKeyById(int $station_id): string {
		try {
			return $this->selectValue(
				"SELECT api_key FROM stations WHERE station_id = ?",
				[$station_id]
			);
		} catch (Exception $e) {
			return '';
		}
	}
	
	public function getStationIdsOfUser(int $user_id): array {
		try {
			return $this->selectCol(
				"SELECT station_id FROM stations WHERE owner = ?",
				[$user_id]
			);
		} catch (Exception $e) {
			return [];
		}
	}
	
	public function getSystemStorage(): array {
		exec("df / | grep / | cut -f3,4 -d' '", $output);
		$storage = explode(' ', $output[0]);
		
		return array(
			'used_storage' => $storage[0],
			'total_storage' => $storage[1]
		);
	}
	
	public function createStation(string $station_name, string $api_key, int $owner): bool {
		try {
			return $this->runQuery(
				"INSERT INTO stations (station_name, api_key, owner) VALUES (?, ?, ?)",
				[$station_name, $api_key, $owner]
			);
		} catch (Exception $e) {
			return false;
		}
	}
	
	public function updateApiKeyById(int $station_id, string $api_key): bool {
		try {
			return $this->runQuery(
				"UPDATE stations SET api_key=? WHERE station_id=?",
				[$api_key, $station_id]
			);
		} catch (Exception $e) {
			return false;
		}
	}
	
	public function isApiKeyUnique(string $api_key): bool {
		try {
			$result = $this->selectValue(
				"SELECT COUNT(*) FROM stations WHERE api_key=?",
				[$api_key]
			);
			return $result != DatabaseAccessObject::EMPTY_RESULT && $result == 0;
		} catch (Exception $e) {
			return false;
		}
	}
	
	public function apiKeyExists(string $api_key): bool {
		try {
			$result = $this->selectRow(
				"SELECT * FROM stations WHERE api_key=?",
				[$api_key]
			);
			return !empty($result);
		} catch (Exception $e) {
			return false;
		}
	}
	
	public function updateStatusByApiKey($api_key, int $status_code): bool {
		try {
			return $this->runQuery(
				"UPDATE stations SET status=? WHERE api_key=?",
				[$status_code, $api_key]
			);
		} catch (Exception $e) {
			return false;
		}
	}
	
	public function deleteStationsOfUser(int $user_id): bool {
		try {
			return $this->runQuery(
				"DELETE FROM stations WHERE owner=?",
				[$user_id]
			);
		} catch (Exception $e) {
			return false;
		}
	}
	
}