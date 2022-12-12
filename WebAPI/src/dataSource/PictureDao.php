<?php

class PictureDao extends DatabaseAccessObject {
	private const IMAGE_PATH = "../files/";
	
	public function insertPicture(int $station_id): int {
		try {
			$result = $this->runQuery(
				"INSERT INTO images (station_id) VALUES (?)",
				[$station_id]
			);
			
			if ($result) {
				return $this->connection->insert_id;
			} else {
				return DatabaseAccessObject::INSERTION_FAILED;
			}
		} catch (Exception $e) {
			return DatabaseAccessObject::INSERTION_FAILED;
		}
	}
	
	public function setPictureSaved(int $picture_id, string $filename): bool {
		try {
			return $this->runQuery(
				"UPDATE images SET filename=?, file_saved=TRUE WHERE id=?",
				[$filename, $picture_id]
			);
		} catch (Exception $e) {
			return false;
		}
	}
	
	public function savePictureOnFilesystem(string $filename, string $raw_file_contents): bool {
		if (!is_dir(self::IMAGE_PATH)) {
			$dir_created = mkdir(self::IMAGE_PATH);
			
			if (!$dir_created) {
				// TODO some error msg
				return false;
			}
		}
		
		$file = fopen("../files/$filename", 'a');
		if (!$file) {
			// TODO some error msg
			return false;
		}
		
		$write_result = fwrite($file, $raw_file_contents);
		if (!$write_result) {
			// TODO some error msg
			return false;
		}
		
		return fclose($file);
	}
	
	public function getFileInfoById(int $picture_id): array {
		try {
			$result = $this->selectRow(
				"SELECT * FROM images WHERE id = ?",
				[$picture_id]
			);
			
			return $result == DatabaseAccessObject::EMPTY_RESULT ? [] : $result;
		} catch (Exception $e) {
			return [];
		}
	}
	
	public function getFirstFileInfoOfStations(int $station_id): array {
		try {
			return $this->selectRow(
				"SELECT * FROM images WHERE station_id=? ORDER BY id",
				[$station_id]
			);
		} catch (Exception $e) {
			return [];
		}
	}
	
	public function getLatestFileInfoOfStations(int $station_id): array {
		try {
			return $this->selectRow(
				"SELECT * FROM images WHERE station_id=? ORDER BY id DESC",
				[$station_id]
			);
		} catch (Exception $e) {
			return [];
		}
	}
	
	public function getPictureByFilename($filename): string {
		$content = file_get_contents(self::IMAGE_PATH . $filename);
		return $content ?: DatabaseAccessObject::EMPTY_RESULT;
	}
	
}