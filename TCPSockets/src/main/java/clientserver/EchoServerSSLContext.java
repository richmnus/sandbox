package clientserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
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

public class EchoServerSSLContext {

    private static final boolean isSsl = true;
    private static final boolean isNeedClientAuth = false;
    private static final int port = 3002;
    private static final boolean debug = false;

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        // Set security properties
        if (isSsl) {
            // These properties can also be set in the VM arguments, or by using
            // -D
            // on the command line (use forward slash for Windows paths)
            System.setProperty("javax.net.ssl.keyStore", "serverkeystore.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "kyanite");

            // The trustStore properties need only be set so that this server
            // can authenticate the client with which it is trying to
            // communicate
            if (isNeedClientAuth) {
                System.setProperty("javax.net.ssl.trustStore",
                        "servertruststore.jks");
                System.setProperty("javax.net.ssl.trustStorePassword",
                        "kyanite");
            }

            // Dump SSL debug info to the console
            if (debug) {
                System.setProperty("javax.net.debug", "ssl");
            }
        }

        // Create a new server socket
        try {
            if (isSsl) {

                // Alternate way of creating server socket by using SSLContext
                // and not having to set the system properties
                SSLContext context;
                KeyManagerFactory keyMgrFactory;
                KeyStore keyStore;
                char[] storepass = "kyanite".toCharArray();
                char[] keypass = "kyanite".toCharArray();
                String storename = "serverkeystore.jks";

                context = SSLContext.getInstance("SSL_TLS");
                keyMgrFactory = KeyManagerFactory.getInstance("IbmX509");
                FileInputStream fin = new FileInputStream(storename);
                keyStore = KeyStore.getInstance("JKS");
                keyStore.load(fin, storepass);

                keyMgrFactory.init(keyStore, keypass);
                context.init(keyMgrFactory.getKeyManagers(), null, null);
                SSLServerSocketFactory ssf = context.getServerSocketFactory();

                serverSocket = ssf.createServerSocket(port);
                ((SSLServerSocket) serverSocket)
                        .setNeedClientAuth(isNeedClientAuth);
            } else {
                serverSocket = new ServerSocket(port);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Wait for a client to connect and create a new client socket
        // The client socket has its remote ip address and port set to the
        // client's, so is used to communicate with the client
        try {
            if (isSsl) {
                clientSocket = (SSLSocket) serverSocket.accept();
            } else {
                clientSocket = serverSocket.accept();
            }
            InetAddress iNetAddr = clientSocket.getInetAddress();
            System.out.printf(
                    "Host address %s:%d connected on local port %d\n",
                    iNetAddr.getHostAddress(), clientSocket.getPort(),
                    clientSocket.getLocalPort());

        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }

        // Input stream
        try {
            in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Output stream
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputLine;

        // Echo test
        try {
            while ((inputLine = in.readLine()) != null) {
                System.out.printf("Received: %s\n", inputLine);
                out.println(inputLine);
                if (inputLine.equals("quit")) {
                    break;
                }
            }
            in.close();
            out.close();
            serverSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
