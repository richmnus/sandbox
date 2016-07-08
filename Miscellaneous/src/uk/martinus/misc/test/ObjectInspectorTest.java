package uk.martinus.misc.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.martinus.misc.ObjectInspector;

public class ObjectInspectorTest {

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
    public void test() {
        List<String> testObject = new ArrayList<>();
        ObjectInspector inspector = new ObjectInspector(testObject);
        String result = inspector.inspect();
        System.out.println(result);
        assertTrue("Unexpected result:\n", result.equals("expectedoutput"));
    }

}
