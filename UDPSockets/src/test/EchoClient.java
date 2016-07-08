package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class EchoClient {

    private final static boolean isSsl = false;
    private String host = "localhost";
    private int port = 4445;

    public static void main(String[] args) {
        new EchoClient();
    }

    public EchoClient() {
        DatagramSocket socket = null;
        InetAddress inetAddress = null;
        byte[] buf = new byte[256];
        DatagramPacket packet = null;
        
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            inetAddress = InetAddress.getByName(host);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(
                System.in));
        String userInput;
        try {
            while (true) {
                // Get keyboard input and send it
                userInput = stdIn.readLine();
                buf = userInput.getBytes();
                packet = new DatagramPacket(buf, buf.length, inetAddress,
                        port);
                socket.send(packet);

                // Get response & display it
                socket.receive(packet);
                String rxStr = new String(packet.getData(), 0, packet.getLength());
                System.out.printf("Received: %s\n", rxStr);
                
                if (userInput.equals("quit")) {
                    stdIn.close();
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        socket.close();
    }
}
