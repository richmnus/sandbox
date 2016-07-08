package clientserver;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;

public class CipherSuites {

    public static void main(String[] args) {
        try {

            // Key store details
            char[] storepass = "kyanite".toCharArray();
            char[] keypass = "kyanite".toCharArray();
            String storename = "serverkeystore.jks";

            // Registered security providers
            Provider[] securityProviders = Security.getProviders();
            System.out
                    .printf("Registered security providers for this JVM:\n\n");
            for (Provider provider : securityProviders) {
                System.out.printf("%s\n", provider.getName());
                System.out.printf("%s\n\n", provider.getInfo());
            }

            // Cipher suites
            String[] secureSocketProtocols = { "SSL", "TLS" };
            for (String protocol : secureSocketProtocols) {
                SSLContext sslContext = SSLContext.getInstance(protocol);
                KeyManagerFactory keyMgrFactory = KeyManagerFactory
                        .getInstance("IbmX509");
                FileInputStream fin = new FileInputStream(storename);
                KeyStore keyStore = KeyStore.getInstance("JKS");
                keyStore.load(fin, storepass);

                keyMgrFactory.init(keyStore, keypass);
                sslContext.init(keyMgrFactory.getKeyManagers(), null, null);
                SSLServerSocketFactory ssf = sslContext
                        .getServerSocketFactory();

                String[] supportedCipherSuites = ssf.getSupportedCipherSuites();
                String[] defaultCipherSuites = ssf.getDefaultCipherSuites();

                System.out
                        .printf("SSLContext requested for the secure socket protocol: %s\n",
                                protocol);
                for (String str : supportedCipherSuites) {
                    System.out.printf("Supported cipher suite: %s\n", str);
                }
                for (String str : defaultCipherSuites) {
                    System.out.printf("Default cipher suite: %s\n", str);
                }
                System.out
                        .printf("Total %d supported cipher suites, %d default cipher suites\n\n",
                                supportedCipherSuites.length,
                                defaultCipherSuites.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
