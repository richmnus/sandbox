package clientserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class EchoClient {

    // Options
    private boolean isSsl = false;
    private boolean isNeedClientAuth = false;
    private String host = "localhost";
    private int port = 2003;
    private boolean debug = false;

    private static final String keystore = "clientkeystore.jks";
    private static final String keystorePwd = "kyanite";
    private static final String keyPwd = "kyanite";

    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    private void client() {

        // if (isSsl) {
        // // These properties can also be set in the VM arguments, or by using
        // // -D
        // // on the command line (use forward slash for Windows paths)
        //
        // // The trustStore properties must be set so that this client can
        // // authenticate the server with which it is trying to communicate
        // System.setProperty("javax.net.ssl.trustStore",
        // "clienttruststore.jks");
        // System.setProperty("javax.net.ssl.trustStorePassword", "kyanite");
        //
        // // The local keyStore properties need only be set if the server
        // // needs to authenticate this client, so that this client can send
        // // its certificate
        // if (isNeedClientAuth) {
        // System.setProperty("javax.net.ssl.keyStore", "clientkeystore.jks");
        // System.setProperty("javax.net.ssl.keyStorePassword", "kyanite");
        // }
        //
        // // Dump SSL debug info to the console
        if (debug) {
            System.setProperty("javax.net.debug", "ssl");
        }

        try {
            if (isSsl) {
                // Key manager
                KeyManagerFactory keyMgrFactory = KeyManagerFactory
                        .getInstance("IbmX509");
                FileInputStream fin = new FileInputStream(keystore);
                KeyStore keyStore = KeyStore.getInstance("JKS");
                keyStore.load(fin, keystorePwd.toCharArray());
                keyMgrFactory.init(keyStore, keyPwd.toCharArray());

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
                socket = sf.createSocket(host, port);
            } else {
                socket = new Socket(host, port);
            }
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // BufferedReader stdIn = new BufferedReader(new InputStreamReader(
        // System.in));
        // String userInput;
        //
        // try {
        // while (true) {
        // userInput = stdIn.readLine();
        // out.println(userInput);
        // byte[] bytes = userInput.getBytes();
        // System.out.printf("Actually sent ");
        // for (byte b : bytes) {
        // System.out.printf("%x ", b);
        // }
        // System.out.printf("\n");
        // System.out.printf("Echo: %s\n", in.readLine());
        // if (userInput.equals("quit")) {
        // out.close();
        // in.close();
        // stdIn.close();
        // socket.close();
        // break;
        // }
        // }
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    public void start() {
        client();
    }

    public String sendReceiveBlocking(String data) {
        out.println(data);
        byte[] bytes = data.getBytes();
        String msg = String.format("%s: Actually sent ", this.getClass()
                .getName());
        for (byte b : bytes) {
            msg += String.format("%x ", b);
        }
        System.out.println(msg);
        String received = "";
        try {
            received = in.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out
                .printf("%s: Received %s\n", this.getClass().getName(), received);
        if (data.equals("quit")) {
            close();
        }
        return received;
    }

    public void close() {
        out.close();
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public static String getKeystore() {
        return keystore;
    }

    public static String getKeystorepwd() {
        return keystorePwd;
    }

    public static String getKeypwd() {
        return keyPwd;
    }

}
