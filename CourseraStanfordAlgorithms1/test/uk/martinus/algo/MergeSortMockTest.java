package uk.martinus.algo;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class MergeSortMockTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void test() {
		// mock creation
		List mockedList = mock(List.class);

		// using mock object - it does not throw any "unexpected interaction"
		// exception
		mockedList.add("one");
		mockedList.clear();

		// selective, explicit, highly readable verification
		verify(mockedList).add("one");
		verify(mockedList).clear();
	}

	@Test
	public final void test1() {

		// you can mock concrete classes, not only interfaces
		LinkedList mockedList = mock(LinkedList.class);

		// stubbing appears before the actual execution
		when(mockedList.get(0)).thenReturn("first");

		// the following prints "first"
		System.out.println(mockedList.get(0));

		// the following prints "null" because get(999) was not stubbed
		System.out.println(mockedList.get(999));
	}

	// Demonstrates the return of multiple values
	@Test
	public void testMoreThanOneReturnValue() {
		Iterator i = mock(Iterator.class);
		when(i.next()).thenReturn("Mockito").thenReturn("rocks");
		String result = i.next() + " " + i.next();
		// assert
		assertEquals("Mockito rocks", result);
	}

	// this test demonstrates how to return values based on the input
	@Test
	public void testReturnValueDependentOnMethodParameter() {
		Comparable c = mock(Comparable.class);
		when(c.compareTo("Mockito")).thenReturn(1);
		when(c.compareTo("Eclipse")).thenReturn(2);
		// assert
		assertEquals(1, c.compareTo("Mockito"));
	}

	// this test demonstrates how to return values independent of the input
	// value

	@Test
	public void testReturnValueInDependentOnMethodParameter() {
		Comparable c = mock(Comparable.class);
		when(c.compareTo(anyInt())).thenReturn(-1);
		// assert
		assertEquals(-1, c.compareTo(9));
	}

	// return a value based on the type of the provide parameter

	@Test
	public void testReturnValueBasedOnTypeOfParameter() {
		Comparable c = mock(Comparable.class);
		when(c.compareTo(isA(Todo.class))).thenReturn(0);
		// assert
		Todo todo = new Todo(5);
		assertEquals(todo, c.compareTo(new Todo(1)));
	}

	class Todo {
		Todo(int n) {
		}
	}

}
