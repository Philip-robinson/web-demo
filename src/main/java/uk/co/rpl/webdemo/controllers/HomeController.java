package uk.co.rpl.webdemo.controllers;

import uk.co.rpl.webdemo.services.DemoAccessService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
@Slf4j
public class HomeController {
    private final DemoAccessService service;

    @GetMapping(value="/")
    public String start(Model model){
        log.info("Received a request for / so getting main user");
        var user = service.getMainUser();
        log.info("Got main user {}", user);
        model.addAttribute("name", user.getForename());
        model.addAttribute("lastName", user.getSurname());
        var users = service.getAllUsers();
        log.info("Got user list: {}", users);
        model.addAttribute("allUsers", users);
        return "home";
    }

}
