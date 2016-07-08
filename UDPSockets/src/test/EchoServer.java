package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EchoServer {

    private static final boolean isSsl = false;
    private int rxPort = 4445;
    
    public static void main(String[] args) throws IOException {
        new EchoServer();
    }

    public EchoServer() {
        try {
            new EchoServerThread().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class EchoServerThread extends Thread {

        private DatagramSocket socket = null;

        public EchoServerThread() throws IOException {
            this("EchoServerThread");
        }

        public EchoServerThread(String name) throws IOException {
            super(name);
            socket = new DatagramSocket(rxPort);
        }

        public void run() {
            while (true) {
                try {
                    byte[] buf = new byte[256];

                    // Receive request
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);

                    // Send the response to the client
                    InetAddress remoteAddr = packet.getAddress();
                    int remotePort = packet.getPort();
                    packet = new DatagramPacket(buf, buf.length, remoteAddr, remotePort);
                    socket.send(packet);
                    
                    // Test for end condition
                    String str = new String(buf);
                    if (str.startsWith("quit")) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket.close();
        }
    }

}
