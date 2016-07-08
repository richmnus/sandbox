package uk.co.martinus.compass.test;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.co.martinus.compass.RotationVector;

public class RVTest {

	@Test
	public void testGetStep() {
		// General position 1
		assertEquals(5, RotationVector.getStep(20, 10));
		assertEquals(50, RotationVector.getStep(20, -80));
		assertEquals(-85, RotationVector.getStep(20, -170));
		assertEquals(-75, RotationVector.getStep(20, 170));

		// General position 2
		assertEquals(85, RotationVector.getStep(-20, 170));
		assertEquals(-25, RotationVector.getStep(-20, 30));
		assertEquals(25, RotationVector.getStep(-20, -70));

		// Around North (0) and South (-180)
		assertEquals(-1, RotationVector.getStep(0, 1));
		assertEquals(-1, RotationVector.getStep(-1, 0));
		assertEquals(1, RotationVector.getStep(0, -1));
		assertEquals(1, RotationVector.getStep(1, 0));
		assertEquals(-1, RotationVector.getStep(-180, -179));
		assertEquals(-1, RotationVector.getStep(179, -180));
		assertEquals(1, RotationVector.getStep(-180, 179));
		assertEquals(1, RotationVector.getStep(-179, -180));
	}

}
