package oauth;

import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;

public class OAuth2Test {

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
    public final void testAuthorize() {
        OAuth2 oAuth2 = new OAuth2();
        AuthorizationCodeFlow mockedFlow = mock(AuthorizationCodeFlow.class);
        oAuth2.authorize(mockedFlow, "richm");
    }

}
