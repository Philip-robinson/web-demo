package uk.co.rpl.demo1.services;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.AfterEach;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.web.client.RestTemplateBuilder;
import uk.co.rpl.demo1.Configurator;

/**
 *
 * @author philip
 */
public class DemoAccessServiceTest {
    
    private MockWebServer server;
    private Configurator config;

    private DemoAccessService inst;

    @BeforeEach
    public void startup(){
        config = mock(Configurator.class);
        server = new MockWebServer();
        when(config.getBase()).thenReturn(server.url("/api").toString());

        var template = new RestTemplateBuilder().build();
        inst = new DemoAccessService(config, template);
    }

    @AfterEach
    public void teardown() throws IOException{
        server.shutdown();
    }
    /**
     * Test of getMainUser method, of class DemoAccessService.
     */
    @Test
    public void testGetMainUser() {
        System.out.println("getMainUser");
        String body = """
                      {
                        "firstName": "GEORGE",
                        "lastName": "SMithson"
                      }
                      """;

        server.enqueue(new MockResponse()
                        .setBody(body)
                        .setResponseCode(200)
                        .setHeader("Content-Type", "application/json;charset=UTF-8"));
        var user = inst.getMainUser();
        assertThat(user).extracting("surname", "forename").
                containsExactly("SMithson", "GEORGE");
    }
    
}
