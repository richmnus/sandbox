package clientserver;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clientserver.EchoClient;
import clientserver.EchoServer;

public class ClientServerTest {

    private EchoServer server;
    private EchoClient client;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        server = new EchoServer();
        server.setSsl(false);
        server.setNeedClientAuth(false);
        server.setPort(3002);
        client = new EchoClient();
        client.setSsl(false);
        client.setNeedClientAuth(false);
        client.setHost("localhost");
        client.setPort(3002);
    }

    @After
    public void tearDown() throws Exception {
        server.close();
        client.close();
    }

    @Test
    public final void testClient() {
        String response;
        server.start();
        client.start();
        response = client.sendReceiveBlocking("hello");
        assertArrayEquals(response.toCharArray(), "hello".toCharArray());
        response = client.sendReceiveBlocking("hello again");
        assertArrayEquals(response.toCharArray(), "hello again".toCharArray());
        client.sendReceiveBlocking("quit");
    }

}
