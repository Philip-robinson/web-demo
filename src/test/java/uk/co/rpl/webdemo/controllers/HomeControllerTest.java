package uk.co.rpl.webdemo.controllers;

import uk.co.rpl.webdemo.controllers.HomeController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.co.rpl.webdemo.dto.User;
import uk.co.rpl.webdemo.services.DemoAccessService;

/**
 *
 * @author philip
 */
public class HomeControllerTest {
    
    private MockMvc mvc;

    private HomeController inst;
    private DemoAccessService service;

    private static final String FIRST="first-name-gjjgjggj";
    private static final String LAST="last-name-8oiglg";

    @BeforeEach
    public void setUp() {
        service = mock(DemoAccessService.class);
        inst = new HomeController(service);
        mvc = MockMvcBuilders.standaloneSetup(inst).
                build();
    }
    
    /**
     * Test of start method, of class ControllerClass.
     */
    @Test
    public void testStartMainUserSuccess() throws Exception {
        System.out.println("start");
        User user = mock(User.class);
        when(user.getForename()).thenReturn(FIRST);
        when(user.getSurname()).thenReturn(LAST);
        when(service.getMainUser()).thenReturn(user);

        mvc.perform(get("/"))
            .andExpect(status().isOk());
    }
}
