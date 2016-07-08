package oauth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;

public class OAuth2 {

    public static Credential createCredentialWithAccessTokenOnly(
            HttpTransport transport, JsonFactory jsonFactory,
            TokenResponse tokenResponse) {
        return new Credential(BearerToken.authorizationHeaderAccessMethod())
                .setFromTokenResponse(tokenResponse);
    }

    public static Credential createCredentialWithRefreshToken(
            HttpTransport transport, JsonFactory jsonFactory,
            TokenResponse tokenResponse) {
        return new Credential.Builder(
                BearerToken.authorizationHeaderAccessMethod())
                .setTransport(transport)
                .setJsonFactory(jsonFactory)
                .setTokenServerUrl(
                        new GenericUrl("https://server.example.com/token"))
                .setClientAuthentication(
                        new BasicAuthentication("s6BhdRkqt3",
                                "7Fjfp0ZBr1KtDRbnfVdmIw")).build()
                .setFromTokenResponse(tokenResponse);
    }

    public static HttpResponse executeGet(HttpTransport transport,
            JsonFactory jsonFactory, String accessToken, GenericUrl url)
            throws IOException {
        Credential credential = new Credential(
                BearerToken.authorizationHeaderAccessMethod())
                .setAccessToken(accessToken);
        HttpRequestFactory requestFactory = transport
                .createRequestFactory(credential);
        return requestFactory.buildGetRequest(url).execute();
    }

    public void authorizeFlow() {
        Credential.AccessMethod method = new Credential.AccessMethod() {

            @Override
            public void intercept(HttpRequest httprequest, String s)
                    throws IOException {
                // TODO Auto-generated method stub

            }

            @Override
            public String getAccessTokenFromRequest(HttpRequest httprequest) {
                // TODO Auto-generated method stub
                return null;
            }

        };
        HttpTransport transport = new HttpTransport() {

            @Override
            protected LowLevelHttpRequest buildRequest(String arg0, String arg1)
                    throws IOException {
                // TODO Auto-generated method stub
                return null;
            }

        };
        JsonFactory jsonFactory = new JsonFactory() {

            @Override
            public JsonGenerator createJsonGenerator(Writer arg0)
                    throws IOException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public JsonGenerator createJsonGenerator(OutputStream arg0,
                    Charset arg1) throws IOException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public JsonParser createJsonParser(InputStream arg0)
                    throws IOException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public JsonParser createJsonParser(String arg0) throws IOException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public JsonParser createJsonParser(Reader arg0) throws IOException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public JsonParser createJsonParser(InputStream arg0, Charset arg1)
                    throws IOException {
                // TODO Auto-generated method stub
                return null;
            }

        };
        GenericUrl tokenServerUrl = new GenericUrl();
        HttpExecuteInterceptor clientAuthentication = new HttpExecuteInterceptor() {

            @Override
            public void intercept(HttpRequest arg0) throws IOException {
                // TODO Auto-generated method stub

            }

        };
        String clientId = "";
        String userId = "richm";
        String authorizationServerEncodedUrl = "https://accounts.google.com/o/oauth2/auth?"
                + "redirect_uri=https%3A%2F%2Fdevelopers.google.com%2Foauthplayground&"
                + "response_type=code&"
                + "client_id=407408718192.apps.googleusercontent.com&"
                + "scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar.readonly&"
                + "approval_prompt=force&" + "access_type=offline";
        try {
            authorizationServerEncodedUrl = URLEncoder.encode("",
                    StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // This exception should never be thrown
            e.printStackTrace();
        }
        AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(method,
                transport, jsonFactory, tokenServerUrl, clientAuthentication,
                clientId, authorizationServerEncodedUrl).build();
        Credential credential = authorize(flow, "rjm620712@gmail.com");
    }

    public Credential authorize(AuthorizationCodeFlow flow, String userId) {

        Credential credential = null;
        try {
            credential = flow.loadCredential(userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Credential is null if not found in the data store
        if (credential != null) {
            return credential;
        }

        // No credential so continue
        String state = "xyz"; // Usually optional in the flow
        String redirectUri = "https://client.example.com/rd";
        String redirectUrl = flow.newAuthorizationUrl().setState(state)
                .setRedirectUri(redirectUri).build();
        // Use redirectUrl to redirect the user agent
        // Provider returns with a new authorization code
        String authorizationCode = "4/A3Jw-8UxnYpTqSgw6AWaHHbVOOJWP2L3DqbX8Qe3S-s";
        // Request a token based on the returned authorization code
        TokenResponse tokenResponse;
        try {
            String tokenRequestUri = "https://client.example.com/rd";
            tokenResponse = flow.newTokenRequest(authorizationCode)
                    .setRedirectUri(tokenRequestUri).execute();
            credential = flow.createAndStoreCredential(tokenResponse, userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credential;
    }

}
