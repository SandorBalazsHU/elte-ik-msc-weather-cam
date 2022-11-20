<?php

class StationDao extends DatabaseAccessObject {
	
	public function getStationById(int $station_id): array {
		try {
			return $this->selectOne(
				"SELECT * FROM stations WHERE station_id = ? LIMIT 1",
				[$station_id]
			);
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function getStationByApiKey(string $api_key): array {
		try {
			return $this->selectOne(
				"SELECT * FROM stations WHERE api_key = ? LIMIT 1",
				[$api_key]
			);
		} catch (Exception $e) {
			return array();
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
	
	public function isApiKeyUnique($api_key): bool {
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
	
}