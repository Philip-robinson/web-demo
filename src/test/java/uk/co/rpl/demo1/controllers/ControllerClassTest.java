package uk.co.rpl.demo1.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.co.rpl.demo1.User;
import uk.co.rpl.demo1.services.DemoAccessService;

/**
 *
 * @author philip
 */
public class ControllerClassTest {
    
    private MockMvc mvc;

    private ControllerClass inst;
    private DemoAccessService service;

    private static final String FIRST="first-name-gjjgjggj";
    private static final String LAST="last-name-8oiglg";

    @BeforeEach
    public void setUp() {
        service = mock(DemoAccessService.class);
        inst = new ControllerClass(service);
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
