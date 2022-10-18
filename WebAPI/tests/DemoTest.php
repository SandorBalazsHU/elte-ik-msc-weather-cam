<?php
use PHPUnit\Framework\TestCase;

final class DemoTest extends TestCase {

	public function test1() {
		$this->assertEquals(2 + 2, 4);
	}

	public function test2() {
		$this->assertTrue(! false);
	}

}
