package ch8.q82;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch8.q82.Respondant;
import ch8.q82.Employee.EmployeeType;
import ch8.q82.Respondant.CallStatus;

public class Q82Test {

    Respondant joan;
    Respondant fred;
    Respondant bill;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        joan = new Respondant("Joan", EmployeeType.DIRECTOR, null,
                CallStatus.IDLE);
        fred = new Respondant("Fred", EmployeeType.MANAGER, joan,
                CallStatus.IDLE);
        bill = new Respondant("Bill", EmployeeType.RESPONDENT, fred,
                CallStatus.IDLE);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testBillIsFree() {
        System.out.println(bill.dispatchCall());
    }

    @Test
    public final void testBillIsBusyFredIsFree() {
        bill.setStatus(CallStatus.INCALL);
        System.out.println(bill.dispatchCall());
    }

    @Test
    public final void testBillIsBusyFredIsBusyJoanIsFree() {
        bill.setStatus(CallStatus.INCALL);
        fred.setStatus(CallStatus.INCALL);
        System.out.println(bill.dispatchCall());
    }

}
