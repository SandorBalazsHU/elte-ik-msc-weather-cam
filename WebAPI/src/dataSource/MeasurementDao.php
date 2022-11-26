<?php

class MeasurementDao extends DatabaseAccessObject {
	
	public function getMeasurementById(int $measurement_id): array {
		try {
			return $this->selectOne(
				"SELECT * FROM measurements WHERE measurement_id = ? LIMIT 1",
				[$measurement_id]
			);
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function getFirstMeasurement(int $station_id): array {
		try {
			$sql = "SELECT * FROM measurements
        			WHERE station_id = ? ORDER BY timestamp ASC LIMIT 1";
			return $this->selectOne($sql, [$station_id]);
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function getLatestMeasurement(int $station_id): array {
		try {
			$sql = "SELECT * FROM measurements
        			WHERE station_id = ? ORDER BY timestamp DESC LIMIT 1";
			return $this->selectOne($sql, [$station_id]);
		} catch (Exception $e) {
			return array();
		}
	}
	
	
	public function getMultipleMeasurements(array $filter): array {
		$filter_options = ['station_id', 'measurement_id', 'date_begin', 'date_end'];
		$filter = array_intersect_key($filter, array_flip($filter_options));
		
		$query_filter = 'TRUE';
		if (isset($filter['station_id'])) {
			$query_filter .= ' AND station_id = ?';
		}
		if (isset($filter['measurement_id'])) {
			$query_filter .= ' AND measurement_id = ?';
		}
		if (isset($filter['date_begin'])) {
			$query_filter .= ' AND timestamp > ?';
		}
		if (isset($filter['date_end'])) {
			$query_filter .= ' AND timestamp < ?';
		}
		
		// sort filters by interpretation order
		uksort($filter, function ($a, $b) use ($filter_options) {
			$a_pos = array_search($a, array_values($filter_options));
			$b_pos = array_search($b, array_values($filter_options));

			if ($a_pos == $b_pos) return 0;
			return $a_pos < $b_pos ? -1 : 1;
		});
		
		try {
			$sql = "SELECT * FROM measurements WHERE $query_filter";
			return $this->select($sql, array_values($filter));
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function getWithOffset(int $measurement_id, int $station_id, int $offset): array {
		try {
			if ($offset >= 0) {
				$measurement_filter = 'measurement_id >= ?';
			} else {
				$measurement_filter = 'measurement_id <= ?';
			}
			$sql_order = $offset >= 0 ? 'ASC' : 'DESC';
			$sql_limit = 'LIMIT ' . (abs($offset) + 1);
			
			$sql = "SELECT * FROM measurements
        			WHERE station_id=? AND $measurement_filter
        			ORDER BY timestamp $sql_order $sql_limit";
			return $this->select($sql, [$station_id, $measurement_id]);
		} catch (Exception $e) {
			return array();
		}
	}
	
	public function insertMeasurement(
		string $station_id,
		string $temperature,
		string $pressure,
		string $humidity,
		string $battery,
		string $timestamp
	): bool {
		try {
			return $this->runQuery(
				"INSERT INTO measurements
					(station_id, temperature, pressure, humidity, battery, timestamp)
					VALUES (?, ?, ?, ?, ?, ?)",
				[
					$station_id, $temperature ?: 'NULL', $pressure ?: 'NULL',
					$humidity ?: 'NULL', $battery ?: 'NULL', $timestamp ?: 'NULL'
				]
			);
		} catch (Exception $e) {
			return false;
		}
	}
	
}