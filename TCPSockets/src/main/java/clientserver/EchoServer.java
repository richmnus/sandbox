package clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class EchoServer {

    // Options
    private boolean isSsl = false;
    private boolean isNeedClientAuth = false;
    private int port = 3003;
    private boolean debug = true;

    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Thread worker;

    private void server() {

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
                SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory
                        .getDefault();
                serverSocket = (SSLServerSocket) sslServerSocketFactory
                        .createServerSocket(port);
                ((SSLServerSocket) serverSocket)
                        .setNeedClientAuth(isNeedClientAuth);
            } else {
                serverSocket = new ServerSocket(port);
            }
        } catch (IOException e) {
            System.err.println(e);
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
                    "%s: Host address %s:%d connected on local port %d\n", this
                            .getClass().getName(), iNetAddr.getHostAddress(),
                    clientSocket.getPort(), clientSocket.getLocalPort());

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
                System.out.printf("%s: Received %s\n", this.getClass()
                        .getName(), inputLine);
                out.println(inputLine);
                if (inputLine.equals("quit")) {
                    System.out.printf("%s: Server stopping...\n", this.getClass()
                            .getName());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void start() {
        worker = new Thread(new Runnable() {
            @Override
            public void run() {
                server();
            }
        });
        worker.start();
    }

    public void close() {
        try {
            in.close();
            serverSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.close();
    }

    public static void main(String[] args) {
        EchoServer server = new EchoServer();
        server.server();
    }

    public boolean isSsl() {
        return isSsl;
    }

    public void setSsl(boolean isSsl) {
        this.isSsl = isSsl;
    }

    public boolean isNeedClientAuth() {
        return isNeedClientAuth;
    }

    public void setNeedClientAuth(boolean isNeedClientAuth) {
        this.isNeedClientAuth = isNeedClientAuth;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
