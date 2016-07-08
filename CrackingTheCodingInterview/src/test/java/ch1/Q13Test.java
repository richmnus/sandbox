package ch1;
import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import ch1.Q11;

public class Q13Test {

	@Test
	public void testPermutation() {
		assertEquals("Two inputs are permutations", true, new Q13().isPerm("hello", "hello"));
	}

	@Test
	public void testNotPermutation() {
		assertEquals("Inputs are not permutations", false, new Q13().isPerm("world", "wrdol1"));
	}

}
