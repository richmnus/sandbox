package ej.test;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ej.ch6.AnnotationRm;
import ej.ch6.Sample;

public class AnnotationRmTest {

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
    public final void testMethod1() {
        Class<Sample> testClass = null;
        try {
            testClass = (Class<Sample>) Class.forName("ej.ch6.Sample");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AnnotationRm.class)) {
                System.out.printf("Annotated method %s found\n",
                        method.getName());
            }
        }
    }

}
