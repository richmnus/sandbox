package ch1;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Q11Test {

	@Test
	public void testDuplicatedCharacters() {
		assertEquals("Duplicated characters", true, new Q11().hasDuplicate("hello"));
		assertEquals("Duplicated characters", true, new Q11().hasDuplicate("thequickbrownfox"));
	}

	@Test
	public void testNoDuplicatedCharacters() {
		assertEquals("No duplicated characters", false, new Q11().hasDuplicate("world"));
		assertEquals("No duplicated characters", false, new Q11().hasDuplicate("stand"));
	}

}
