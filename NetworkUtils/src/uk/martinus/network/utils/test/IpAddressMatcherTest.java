package uk.martinus.network.utils.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.martinus.network.utils.IpAddressMatcher;

public class IpAddressMatcherTest {

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
    public void testMatches() {
        String[] ipv4s = { "192.168.1.10", // Dotted decimal
                "192.168.266", // Dotted decimal
                "192.11010314", // Dotted decimal
                "3232235786", // Decimal
                "0xc0.0xa8.0x1.0xa", // Dotted hex
                "0xc0.0xa8.0x10a", // Dotted hex
                "0xc0.0xa8010a", // Dotted hex
                "0xc0a8010a", // Hex
                "0300.0250.01.012", // Dotted octal
                "0300.0250.0412", // Dotted octal
                "0300.052000412", // Dotted octal
                "030052000412", // Octal
                "0300.168.0x10a" // Dotted mixed formats
        };
        IpAddressMatcher exactMatcher = new IpAddressMatcher("192.168.1.10");
        for (String ipv4 : ipv4s) {
            assertTrue(exactMatcher.matches(ipv4));
        }
        IpAddressMatcher rangeMatcher = new IpAddressMatcher("192.168.1.10/24");
        assertTrue(rangeMatcher.matches("192.168.1.10"));
        assertTrue(rangeMatcher.matches("192.168.1.0"));
        assertTrue(rangeMatcher.matches("192.168.1.255"));
        assertFalse(rangeMatcher.matches("192.168.0.10"));
        assertFalse(rangeMatcher.matches("192.168.2.10"));
        String[] ipv6s = { "2000::1900:1800",
                "::1",
                "2000::"
        };
        IpAddressMatcher ipv6ExactMatcher1 = new IpAddressMatcher("2000::1900:1800");
        assertTrue(ipv6ExactMatcher1.matches("2000:0:0:0:0:0:1900:1800"));
        IpAddressMatcher ipv6ExactMatcher2 = new IpAddressMatcher("::1");
        assertTrue(ipv6ExactMatcher2.matches("0:0:0:0:0:0:0:1"));
        IpAddressMatcher ipv6ExactMatcher3 = new IpAddressMatcher("2000::");
        assertTrue(ipv6ExactMatcher3.matches("2000:0:0:0:0:0:0:0"));
        IpAddressMatcher ipv6ExactMatcher4 = new IpAddressMatcher("::ffff:192.168.0.1");
        assertTrue(ipv6ExactMatcher4.matches("192.168.0.1"));
        IpAddressMatcher ipv6ExactMatcher5 = new IpAddressMatcher("192.168.0.1");
        assertTrue(ipv6ExactMatcher5.matches("::ffff:192.168.0.1"));
    }

}
