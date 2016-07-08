package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Proxy {

    private static final int inboundPort = 3128;
    private static final String onwardHost = "localhost";
    private static final int onwardPort = 4444;
    private static final boolean isSsl = true;
    private static final boolean isNeedClientAuth = false;

    private static final String inboundKeyStorePath = "inboundkeystore.jks";
    private static final String inboundKeyStorePwd = "kyanite";
    private static final String inboundKeyPwd = "kyanite";
    private static final String inboundTrustStorePath = "truststore.jks";
    private static final String inboundTrustStorePwd = "kyanite";

    private static final String outboundKeyStorePath = "outboundkeystore.jks";
    private static final String outboundKeyStorePwd = "kyanite";
    private static final String outboundKeyPwd = "kyanite";
    private static final String outboundTrustStorePath = "truststore.jks";
    private static final String outboundTrustStorePwd = "kyanite";

    public Proxy() {
        run();
    }

    public void run() {
        ServerSocket serverSocket = null;
        Socket inboundClientSocket = null;
        Socket outboundClientSocket = null;
        PrintWriter inboundOut = null;
        InputStream inboundIs = null;
        BufferedReader inboundIn = null;
        PrintWriter outboundOut = null;
        InputStream outboundIs = null;
        BufferedReader outboundIn = null;

        // Create a new client socket for the outbound connection
        try {
            if (isSsl) {
                // Key manager
                KeyManagerFactory keyMgrFactory = KeyManagerFactory
                        .getInstance("IbmX509");
                FileInputStream fin = new FileInputStream(outboundKeyStorePath);
                KeyStore keyStore = KeyStore.getInstance("JKS");
                keyStore.load(fin, outboundKeyStorePwd.toCharArray());
                keyMgrFactory.init(keyStore, outboundKeyPwd.toCharArray());

                // Trust manager
                TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs,
                            String authType) {
                    }

                    @Override
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs,
                            String authType) {
                    }
                } };

                // Create the socket
                SSLContext sslContext = SSLContext.getInstance("SSL_TLS");
                sslContext.init(keyMgrFactory.getKeyManagers(), trustAllCerts,
                        null);
                SSLSocketFactory sf = sslContext.getSocketFactory();
                outboundClientSocket = sf.createSocket(onwardHost, onwardPort);
            } else {
                outboundClientSocket = new Socket(onwardHost, onwardPort);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Setup input and output streams for the outbound connection
        try {
            outboundIs = outboundClientSocket.getInputStream();
            outboundIn = new BufferedReader(new InputStreamReader(outboundIs));
            outboundOut = new PrintWriter(
                    outboundClientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Create a new server socket for the inbound connection
        try {
            if (isSsl) {
                // Key manager
                KeyManagerFactory keyMgrFactory = KeyManagerFactory
                        .getInstance("IbmX509");
                FileInputStream fin = new FileInputStream(inboundKeyStorePath);
                KeyStore keyStore = KeyStore.getInstance("JKS");
                keyStore.load(fin, inboundKeyStorePwd.toCharArray());
                keyMgrFactory.init(keyStore, inboundKeyPwd.toCharArray());

                // Trust manager
                TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs,
                            String authType) {
                    }

                    @Override
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs,
                            String authType) {
                    }
                } };

                // Create the socket
                SSLContext sslContext = SSLContext.getInstance("SSL_TLS");
                sslContext.init(keyMgrFactory.getKeyManagers(), trustAllCerts,
                        null);
                SSLServerSocketFactory ssf = sslContext
                        .getServerSocketFactory();
                serverSocket = ssf.createServerSocket(inboundPort);
                ((SSLServerSocket) serverSocket)
                        .setNeedClientAuth(isNeedClientAuth);
            } else {
                serverSocket = new ServerSocket(inboundPort);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.printf("Proxy ready\n");

        // Wait for an inbound client to connect and create a new client socket
        // The client socket has its remote ip address and port set to the
        // client's, so is used to communicate with the client
        try {
            if (isSsl) {
                inboundClientSocket = (SSLSocket) serverSocket.accept();
            } else {
                inboundClientSocket = serverSocket.accept();
            }
            InetAddress iNetAddr = inboundClientSocket.getInetAddress();
            System.out.printf(
                    "Inbound host address %s:%d connected on local port %d\n",
                    iNetAddr.getHostAddress(), inboundClientSocket.getPort(),
                    inboundClientSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Setup input and output streams for the inbound connection
        try {
            inboundIs = inboundClientSocket.getInputStream();
            inboundIn = new BufferedReader(new InputStreamReader(inboundIs));
            inboundOut = new PrintWriter(inboundClientSocket.getOutputStream(),
                    true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Proxy processing
        try {
            String inputLine;
            while (true) {
                inputLine = inboundIn.readLine();
                System.out.printf("Received: %s on the inbound side\n",
                        inputLine);
                outboundOut.println(inputLine);
                inputLine = outboundIn.readLine();
                System.out.printf("Received: %s on the outbound side\n",
                        inputLine);
                inboundOut.println(inputLine);
                // In my echo test, quit when the line "quit" is echoed back
                // from the server
                if (inputLine.equals("quit")) {
                    break;
                }
            }
            inboundIn.close();
            inboundOut.close();
            outboundIn.close();
            outboundOut.close();
            serverSocket.close();
            inboundClientSocket.close();
            outboundClientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        new Proxy();
    }

}
