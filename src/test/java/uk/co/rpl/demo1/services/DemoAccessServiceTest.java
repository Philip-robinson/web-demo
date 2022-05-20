package uk.co.rpl.demo1.services;

import uk.co.rpl.webdemo.services.DemoAccessService;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import org.junit.jupiter.api.AfterEach;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.web.client.RestTemplateBuilder;
import uk.co.rpl.webdemo.Configurator;

/**
 *
 * @author philip
 */
public class DemoAccessServiceTest {

    private MockWebServer server;
    private Configurator config;

    private DemoAccessService inst;

    @BeforeEach
    public void startup() {
        config = mock(Configurator.class);
        server = new MockWebServer();
        when(config.getBase()).thenReturn(server.url("/api").toString());

        var template = new RestTemplateBuilder().build();
        inst = new DemoAccessService(config, template);
    }

    @AfterEach
    public void teardown() throws IOException {
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
                .setHeader("Content-Type",
                           "application/json;charset=UTF-8")
        );
        var user = inst.getMainUser();
        assertThat(user).extracting("surname", "forename").
                containsExactly("SMithson", "GEORGE");
    }

    @Test
    public void testGetAllUsers() {
        System.out.println("getMainUser");
        String body = """
                      [{
                        "firstName": "GEORGE",
                        "lastName": "SMithson"
                      }, {
                        "firstName": "Jean",
                        "lastName": "Brickhouse"
                      }]
                      """;

        server.enqueue(new MockResponse()
                .setBody(body)
                .setResponseCode(200)
                .setHeader("Content-Type",
                           "application/json;charset=UTF-8")
        );
        var users = inst.getAllUsers();
        assertThat(users).asList().hasSize(2).
                extracting("surname", "forename").
                containsExactly(tuple("SMithson", "GEORGE"),
                                tuple("Brickhouse", "Jean"));
    }

}
