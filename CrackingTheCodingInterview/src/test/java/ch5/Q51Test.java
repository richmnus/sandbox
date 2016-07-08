package ch5;

import static org.junit.Assert.*;

import org.junit.Test;

public class Q51Test {

	@Test
	public void testInsertOneBinaryIntoAnother() {
		int result = Q51.insertInto(0b11001, 0b110111100011, 2, 6);
		assertEquals("Result is 0b110111100011", 0b110111100111, result);
	}

}
